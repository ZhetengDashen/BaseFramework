package com.baseeasy.commonlibrary.fingerprint;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;


import com.baseeasy.commonlibrary.R;
import com.za.finger.ZA_finger;
import com.za.finger.ZAandroid;

import java.io.DataOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.UUID;

/**
 * 作者：WangZhiQiang
 * 时间：2019/2/18
 * 邮箱：sos181@163.com
 * 描述：
 */
public class FingerprintUtils2 {
    private Activity activity;
    private boolean fpflag = false;
    private boolean fpcharflag = false;
    private boolean fpmatchflag = false;
    private boolean fperoll = false;
    private boolean fpsearch = false;
    private boolean isfpon = false;

    private int testcount = 0;

    private int fpcharbuf = 1;

    long ssart = System.currentTimeMillis();
    long ssend = System.currentTimeMillis();
    private Handler objHandler_fp;
    private HandlerThread thread;


    byte[] pTempletbase = new byte[2304];

    private int IMG_SIZE = 0;//同参数：（0:256x288 1:256x360）


    ZAandroid a6 = new ZAandroid();
    String TAG = "060";
    int DEV_ADDR = 0xffffffff;
    private Handler objHandler_3;
    String sdCardRoot = Environment.getExternalStorageDirectory()
            .getAbsolutePath();


    private int def_iCom = 3;
    private int def_iBaud = 6;
    private int usborcomtype;///0 noroot  1root
    private int defDeviceType;
    private int defiCom;
    private int defiBaud;

    private int iPageID = 0;
    Activity ahandle;
    //////////////////
    private int fpcharlen = 512;
    private int fpchcount = 2;
    private static final String[] m = {
            "060_usb_v3.0",
            "060_usb_root_v3.0",
            "060_com_v3.0",
            "060_usb_v4.0",
            "060_usb_root_v4.0",
            "060_com_v4.0",
            "050_usb_v3.0",
            "050_usb_root_v3.0",
            "050_com_v3.0",
            "050_usb_v4.0",
            "050_usb_root_v4.0",
            "050_com_v4.0",
            "test"
    };

    private static final String[] m2 = {
            "feature:512        2",
            "feature:1024       4",
            "feature:768        2",
            "feature:1536       4",
            "feature:2304       4"
    };


    public static final int opensuccess = 101;
    public static final int openfail = 102;
    public static final int usbfail = 103;

    private UsbManager mDevManager = null;
    private PendingIntent permissionIntent = null;
    private UsbInterface intf = null;
    private UsbDeviceConnection connection = null;
    private UsbDevice device = null;
    private int isusbfinshed = 0;
    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
    private final Handler m_fEvent = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String temp = null;
            switch (msg.what) {
                case opensuccess:
                    //打开设备成功
                    if(onFingerprintCallBack!=null){
                        onFingerprintCallBack.callBack(1,"打开设备成功","");
                    }

                    break;
                case openfail:
                    //打开设备失败
                    if(onFingerprintCallBack!=null){
                        onFingerprintCallBack.callBack(-1,"打开设备失败","");
                    }

                    break;
                case usbfail:
                    //Usb未准备完成
                    if(onFingerprintCallBack!=null){
                        onFingerprintCallBack.callBack(-1,"Usb未准备完成.","");
                    }
                    break;
            }
        }
    };
    private volatile static FingerprintUtils2 singleton;

    private FingerprintUtils2(Activity activity) {
        thread = new HandlerThread("MyHandlerThread");
        thread.start();
        objHandler_fp = new Handler();//
        this.activity = activity;
        usborcomtype = 0;
        defDeviceType = 2;
        defiCom = 6;
        defiBaud = def_iBaud;
    }

    public static FingerprintUtils2 getInstance(Activity activity) {
        if (singleton == null) {
            synchronized (FingerprintUtils2.class) {
                if (singleton == null) {
                    singleton = new FingerprintUtils2(activity);
                }
            }
        }
        return singleton;
    }
    OnFingerprintCallBack onFingerprintCallBack;
    public interface  OnFingerprintCallBack{
        void callBack(int code, String msg, String data);
    }
   public  void  setOnFingerprintCallBack( OnFingerprintCallBack onFingerprintCallBack){
        this.onFingerprintCallBack=onFingerprintCallBack;
   }
    //打开设备
    public void fingerprintOpen(OnFingerprintCallBack onFingerprintCallBack) {
        this.onFingerprintCallBack=onFingerprintCallBack;
        Runnable r = new Runnable() {
            public void run() {
                isusbfinshed = 3;
//							ZA_finger fppower = new ZA_finger();
//							fppower.finger_power_on();
                try {
                    thread.sleep(700);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                OpenDev();
            }
        };
        Thread s = new Thread(r);
        s.start();
    }

    //关闭设备
    public void fingerprintClose(OnFingerprintCallBack onFingerprintCallBack) {
        this.onFingerprintCallBack=onFingerprintCallBack;
        byte[] tmp = {5, 6, 7};
        //a6.ZAZBT_rev(tmp, tmp.length);
        int status = a6.ZAZCloseDeviceEx();
        ZA_finger fppower = new ZA_finger();
        fppower.finger_power_off();
        Log.e(TAG, " close status: " + status);
//      offLine(false);
        onFingerprintCallBack.callBack(0,"关闭成功","");
    }

    //对比指纹
    public void fingerprintContrast(OnFingerprintCallBack onFingerprintCallBack) {
        this.onFingerprintCallBack=onFingerprintCallBack;
        setflag(true);
        try {
            thread.sleep(500);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        fpmatchflag = false;
        objHandler_fp.removeCallbacks(fpmatchTasks);
        objHandler_fp.removeCallbacks(fpcharTasks);
        objHandler_fp.removeCallbacks(fperollTasks);
        objHandler_fp.removeCallbacks(fpsearchTasks);
        objHandler_fp.removeCallbacks(fpTasks);
        readsfpmatch();
    }

    //获取指纹特征码
    public void  fingerprintGetCode(OnFingerprintCallBack onFingerprintCallBack){
        this.onFingerprintCallBack=onFingerprintCallBack;
        setflag(true);
        try {
            thread.sleep(500);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        fpcharflag = false;
        objHandler_fp.removeCallbacks(fpmatchTasks);
        objHandler_fp.removeCallbacks(fpcharTasks);
        objHandler_fp.removeCallbacks(fperollTasks);
        objHandler_fp.removeCallbacks(fpsearchTasks);
        objHandler_fp.removeCallbacks(fpTasks);

        readsfpchar();
    }

    //获取指纹图像
    public void fingerprintImage(OnFingerprintCallBack onFingerprintCallBack){
        this.onFingerprintCallBack=onFingerprintCallBack;
        setflag(true);
        try {
            thread.sleep(500);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        fpflag = false;
        objHandler_fp.removeCallbacks(fpmatchTasks);
        objHandler_fp.removeCallbacks(fpcharTasks);
        objHandler_fp.removeCallbacks(fperollTasks);
        objHandler_fp.removeCallbacks(fpsearchTasks);
        objHandler_fp.removeCallbacks(fpTasks);

        readsfpimg();
    }



    /*-------------------------------------------*/
    private void OpenDev() {
        // TODO Auto-generated method stub

        byte[] pPassword = new byte[4];
        int status = 0;
        if (1 == usborcomtype) {
            Log.e("test", "  11----> " + System.currentTimeMillis());
            LongDunD8800_CheckEuq();
            Log.e("test", "  22----> " + System.currentTimeMillis());
            status = a6.ZAZOpenDeviceEx(-1, defDeviceType, defiCom, defiBaud, 0, 0);
            if (status == 1 && a6.ZAZVfyPwd(DEV_ADDR, pPassword) == 0) {
                status = 1;
            } else {
                status = 0;
            }
            a6.ZAZSetImageSize(IMG_SIZE);
            Toast.makeText(activity, "2", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(activity, "3", Toast.LENGTH_SHORT).show();
            device = null;
            isusbfinshed = 0;
            int fd = 0;
            isusbfinshed = getrwusbdevices();
            if (WaitForInterfaces() == false) {

                m_fEvent.sendEmptyMessage(usbfail);
                return;
            }
            fd = OpenDeviceInterfaces();
            if (fd == -1) {
                m_fEvent.sendEmptyMessage(usbfail);
                return;
            }
            Log.e(TAG, "zhw === open fd: " + fd);
            status = a6.ZAZOpenDeviceEx(fd, defDeviceType, defiCom, defiBaud, 0, 0);
            //status = a6.ZAZOpenDeviceEx(fd, 5, 3, 6, 0, 0);
            Log.e("ZAZOpenDeviceEx", "" + defDeviceType + "  " + defiCom + "   " + defiBaud + "  status " + status);
                if (status == 1 && a6.ZAZVfyPwd(DEV_ADDR, pPassword) == 0) {
                    status = 1;
                } else {
                    status = 0;
                }
            a6.ZAZSetImageSize(IMG_SIZE);
        }
        Log.e(TAG, " open status: " + status);
        if (status == 1) {

            m_fEvent.sendEmptyMessage(opensuccess);
//            	Toast.makeText(activity, activity.getResources().getString(R.string.opensuccess_str),
//            		Toast.LENGTH_SHORT).show();
////			btnopen.setText(getResources().getString(R.string.close_str));
////			String temp =getResources().getString(R.string.opensuccess_str);
////			mtvMessage.setText(temp);
        } else {
            ZA_finger fppower = new ZA_finger();
            fppower.finger_power_off();
//			Toast.makeText(activity, activity.getResources().getString(R.string.openfail_str),
//					Toast.LENGTH_SHORT).show();
            m_fEvent.sendEmptyMessage(openfail);
////			String temp =getResources().getString(R.string.openfail_str);
////			mtvMessage.setText(temp);
        }

    }

    private void setflag(boolean value) {
        fpflag = value;
        fpcharflag = value;
        fpmatchflag = value;
        fperoll = value;
        fpsearch = value;

    }

    private void readsfpmatch() {
        ssart = System.currentTimeMillis();
        ssend = System.currentTimeMillis();
        fpcharbuf = 1;
        isfpon = false;
        testcount = 0;
        objHandler_fp.postDelayed(fpmatchTasks, 0);
    }




    private void readsfpchar() {
        ssart = System.currentTimeMillis();
        ssend = System.currentTimeMillis();
        testcount = 0;
        objHandler_fp.postDelayed(fpcharTasks, 0);
    }

    private Runnable fpcharTasks = new Runnable() {
        public void run()// 运行该服务执行此函数
        {
            String temp = "";
            long timecount = 0;
            ssend = System.currentTimeMillis();
            timecount = (ssend - ssart);

            if (timecount > 10000) {
                temp = activity.getResources().getString(R.string.readfptimeout_str) + "\r\n";
                if(onFingerprintCallBack!=null){
                    onFingerprintCallBack.callBack(-1,temp,"");
                }

                return;
            }

            if (fpcharflag) {
                temp = activity.getResources().getString(R.string.stopgetchar_str) + "\r\n";
//                mtvMessage.setText(temp);
                if(onFingerprintCallBack!=null){
                    onFingerprintCallBack.callBack(-1,temp,"");
                }
                return;
            }
            int nRet = 0;
            nRet = a6.ZAZGetImage(DEV_ADDR);
            if (nRet == 0) {
                testcount = 0;
                try {
                    thread.sleep(200);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                nRet = a6.ZAZGetImage(DEV_ADDR);
            }
            if (nRet == 0) {

//	 		{
//		 		int[] len = { 0, 0 };
//				byte[] Image = new byte[256 * 360];
//				a6.ZAZUpImage(DEV_ADDR, Image, len);
//				String str = "/mnt/sdcard/test.bmp";
//				a6.ZAZImgData2BMP(Image, str);
//				temp ="获取图像成功";
//		 		mtvMessage.setText(temp);
//
//				Bitmap bmpDefaultPic;
//				bmpDefaultPic = BitmapFactory.decodeFile(str,null);
//				mFingerprintIv.setImageBitmap(bmpDefaultPic);
//	 		}
                nRet = a6.ZAZGenChar(DEV_ADDR, a6.CHAR_BUFFER_A);// != PS_OK) {
                if (nRet == a6.PS_OK) {
                    int[] iTempletLength = {0, 0};
                    byte[] pTemplet = new byte[2304];
                    //a6.ZAZSetCharLen(512);
                    nRet = a6.ZAZUpChar(DEV_ADDR, a6.CHAR_BUFFER_A, pTemplet, iTempletLength);
                    if (nRet == a6.PS_OK) {

                        //temp="指纹特征:"+iTempletLength[0] +" \r\n";
                        temp = charToHexString(pTemplet);
                        //      mtvMessage.setText(temp);
                        if(onFingerprintCallBack!=null){
                            onFingerprintCallBack.callBack(1,"获取指纹特征成功",temp);
                        }

                    }
                    nRet = a6.ZAZDownChar(DEV_ADDR, a6.CHAR_BUFFER_A, pTemplet, iTempletLength[0]);
                    if (nRet == a6.PS_OK) {
                        if(onFingerprintCallBack!=null){
                            onFingerprintCallBack.callBack(1,activity.getResources().getString(R.string.downsuccess_str),temp);
                        }
                        //      mtvMessage.setText(temp);
                    }
//	 			else
//		 		{
//	 				temp ="上传特征失败，请重新录入";
//		 			mtvMessage.setText(temp);
//		 			ssart = System.currentTimeMillis();
//				 	objHandler_fp.postDelayed(fpcharTasks, 1000);
//
//		 		}

                } else {
                    temp = activity.getResources().getString(R.string.getfailchar_str);
                    //    mtvMessage.setText(temp);
                    if(onFingerprintCallBack!=null){
                        onFingerprintCallBack.callBack(-1,temp,"");
                    }
                    ssart = System.currentTimeMillis();
                    objHandler_fp.postDelayed(fpcharTasks, 1000);

                }
//				Log.d(TAG, "Gen Char fail!");
//			}
            } else if (nRet == a6.PS_NO_FINGER) {
                temp = activity.getResources().getString(R.string.readingfp_str) + ((10000 - (ssend - ssart))) / 1000 + "s";
                //  mtvMessage.setText(temp);
                if(onFingerprintCallBack!=null){
                    onFingerprintCallBack.callBack(0,temp,"");
                }
                objHandler_fp.postDelayed(fpcharTasks, 10);
            } else if (nRet == a6.PS_GET_IMG_ERR) {
                temp = activity.getResources().getString(R.string.getimageing_str);
                if(onFingerprintCallBack!=null){
                    onFingerprintCallBack.callBack(0,temp,"");
                }
                Log.d(TAG, temp + "1: " + nRet);
                objHandler_fp.postDelayed(fpcharTasks, 10);
                //  mtvMessage.setText(temp);

                return;
            } else if (nRet == -2) {
                testcount++;
                if (testcount < 3) {
                    temp = activity.getResources().getString(R.string.readingfp_str) + ((10000 - (ssend - ssart))) / 1000 + "s";
                    isfpon = false;
                    //     mtvMessage.setText(temp);
                    if(onFingerprintCallBack!=null){
                        onFingerprintCallBack.callBack(0,temp,"");
                    }
                    objHandler_fp.postDelayed(fpmatchTasks, 10);
                } else {
                    temp = activity.getResources().getString(R.string.Communicationerr_str);
                    Log.d(TAG, temp + ": " + nRet);
                    //   mtvMessage.setText(temp);

                    return;
                }
            } else {
                temp = activity.getResources().getString(R.string.Communicationerr_str);
                Log.d(TAG, temp + "1: " + nRet);
                //   mtvMessage.setText(temp);
                if(onFingerprintCallBack!=null){
                    onFingerprintCallBack.callBack(-1,temp,"");
                }
                return;
            }

        }
    };
    private Runnable fpmatchTasks = new Runnable() {
        public void run()// 运行该服务执行此函数
        {
            String temp = "";
            long timecount = 0;
            ssend = System.currentTimeMillis();
            timecount = (ssend - ssart);

            if (timecount > 10000) {
                temp = "读指纹等待超时" + "\r\n";
                //  mtvMessage.setText(temp);
                if(onFingerprintCallBack!=null){
                    onFingerprintCallBack.callBack(-1,temp,"");
                }
                return;
            }
            if (fpmatchflag) {
                temp = "比对主动停止" + "\r\n";
                //  mtvMessage.setText(temp);
                if(onFingerprintCallBack!=null){
                    onFingerprintCallBack.callBack(-1,temp,"");
                }
                return;
            }
            int nRet = 0;
            nRet = a6.ZAZGetImage(DEV_ADDR);
            if (nRet == 0) {
                testcount = 0;
                try {
                    thread.sleep(200);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                nRet = a6.ZAZGetImage(DEV_ADDR);
            }
            if (nRet == 0) {
                if (isfpon) {
                    temp = "请拿起手指";
                    //  mtvMessage.setText(temp);
                    if(onFingerprintCallBack!=null){
                        onFingerprintCallBack.callBack(0,temp,"");
                    }
                    ssart = System.currentTimeMillis();
                    objHandler_fp.postDelayed(fpmatchTasks, 100);
                    return;
                }

                //nRet = a6.ZAZLoadChar( DEV_ADDR,2,1);
                //a6.ZAZSetCharLen(2304);
                //nRet = a6.ZAZDownChar(DEV_ADDR, 2, pTempletbase, 2304);
                nRet = a6.ZAZGenChar(DEV_ADDR, fpcharbuf);// != PS_OK) {
                if (nRet == a6.PS_OK) {
                    if (fpcharbuf != 1) {
                        int[] iScore = {0, 0};
                        nRet = a6.ZAZMatch(DEV_ADDR, iScore);
                        if (nRet == a6.PS_OK) {
                            temp = ">比对成功   得分";
                            if(onFingerprintCallBack!=null){
                                onFingerprintCallBack.callBack(1,temp,""+iScore[0]);
                            }
                            //     mtvMessage.setText(temp);
                        } else {
                            temp = "比对失败   得分" ;
                            if(onFingerprintCallBack!=null){
                                onFingerprintCallBack.callBack(-1,temp,iScore[0]+"");
                            }
                            //     mtvMessage.setText(temp);
                        }
                        return;
                    }

                    fpcharbuf = 2;
                    isfpon = true;
                    temp = "请按指纹需要比对的指纹";
                    if(onFingerprintCallBack!=null){
                        onFingerprintCallBack.callBack(-1,temp,"");
                    }
                    //   mtvMessage.setText(temp);
                    ssart = System.currentTimeMillis();
                    objHandler_fp.postDelayed(fpmatchTasks, 100);
                } else {
                    temp = "特征太差，请重新录入";
                    if(onFingerprintCallBack!=null){
                        onFingerprintCallBack.callBack(-1,temp,"");
                    }
                    //     mtvMessage.setText(temp);
                    ssart = System.currentTimeMillis();
                    objHandler_fp.postDelayed(fpmatchTasks, 1000);

                }

            } else if (nRet == a6.PS_NO_FINGER) {
                temp = "正在读取指纹中   剩余时间:" + ((10000 - (ssend - ssart))) / 1000 + "s";
                if(onFingerprintCallBack!=null){
                    onFingerprintCallBack.callBack(0,temp,"");
                }
                isfpon = false;
                //   mtvMessage.setText(temp);
                objHandler_fp.postDelayed(fpmatchTasks, 10);
            } else if (nRet == a6.PS_GET_IMG_ERR) {
                temp = "图像获取中";
                Log.d(TAG, temp + ": " + nRet);
                if(onFingerprintCallBack!=null){
                    onFingerprintCallBack.callBack(0,temp,"");
                }
                //   mtvMessage.setText(temp);
                objHandler_fp.postDelayed(fpmatchTasks, 10);
                //mtvMessage.setText(temp);
                return;
            } else if (nRet == -2) {
                testcount++;
                if (testcount < 3) {
                    temp = "正在读取指纹中   剩余时间:" + ((10000 - (ssend - ssart))) / 1000 + "s";
                    isfpon = false;
                    //    mtvMessage.setText(temp);
                    if(onFingerprintCallBack!=null){
                        onFingerprintCallBack.callBack(0,temp,"");
                    }
                    objHandler_fp.postDelayed(fpmatchTasks, 10);
                } else {
                    temp = "通讯异常";
                    Log.d(TAG, temp + ": " + nRet);
                    //   mtvMessage.setText(temp);
                    if(onFingerprintCallBack!=null){
                        onFingerprintCallBack.callBack(-1,temp,"");
                    }
                    return;
                }
            } else {
                temp = "通讯异常";
                Log.d(TAG, temp + ": " + nRet);
                //   mtvMessage.setText(temp);
                if(onFingerprintCallBack!=null){
                    onFingerprintCallBack.callBack(-1,temp,"");
                }
                return;
            }
        }
    };

    private void readsfpimg() {
        ssart = System.currentTimeMillis();
        ssend = System.currentTimeMillis();
        testcount = 0;
        objHandler_fp.postDelayed(fpTasks, 0);
    }

    private Runnable fpTasks = new Runnable() {
        public void run()// 运行该服务执行此函数
        {
            String temp = "";
            long timecount = 0;
            ssend = System.currentTimeMillis();
            timecount = (ssend - ssart);

            if (timecount > 10000) {
                temp = "读指纹等待超时" + "\r\n";
                if(onFingerprintCallBack!=null){
                    onFingerprintCallBack.callBack(-1,temp,"");
                }
//                mtvMessage.setText(temp);
                return;
            }
            if (fpflag) {
                temp = "获取图像主动停止" + "\r\n";
                if(onFingerprintCallBack!=null){
                    onFingerprintCallBack.callBack(-1,temp,"");
                }
//                mtvMessage.setText(temp);
                return;
            }
            int nRet = 0;
            nRet = a6.ZAZGetImage(DEV_ADDR);
            if (nRet == 0) {
                testcount = 0;
                int[] len = {0, 0};
                byte[] Image = new byte[256 * 360];
                a6.ZAZUpImage(DEV_ADDR, Image, len);

                String str = "/mnt/sdcard/"+ UUID.randomUUID().toString().replace("-", "")+".bmp";
                a6.ZAZImgData2BMP(Image, str);
                temp = "获取图像成功";
//                mtvMessage.setText(temp);
                if(onFingerprintCallBack!=null){
                    onFingerprintCallBack.callBack(1,temp,str);
                }
//                Bitmap bmpDefaultPic;
//                bmpDefaultPic = BitmapFactory.decodeFile(str, null);

            } else if (nRet == a6.PS_NO_FINGER) {
                temp = "正在读取指纹中   剩余时间" + ((10000 - (ssend - ssart))) / 1000 + "s";
//                mtvMessage.setText(temp);
                if(onFingerprintCallBack!=null){
                    onFingerprintCallBack.callBack(0,temp,"");
                }
                objHandler_fp.postDelayed(fpTasks, 100);
            } else if (nRet == a6.PS_GET_IMG_ERR) {
                temp = "图像获取中";
                Log.d(TAG, temp + "2: " + nRet);
                if(onFingerprintCallBack!=null){
                    onFingerprintCallBack.callBack(0,temp,"");
                }
                objHandler_fp.postDelayed(fpTasks, 100);
//                mtvMessage.setText(temp);
                return;
            } else if (nRet == -2) {
                testcount++;
                if (testcount < 3) {
                    temp = "正在读取指纹中   剩余时间:" + ((10000 - (ssend - ssart))) / 1000 + "s";
                    isfpon = false;
                    if(onFingerprintCallBack!=null){
                        onFingerprintCallBack.callBack(0,temp,"");
                    }
//                    mtvMessage.setText(temp);
                    objHandler_fp.postDelayed(fpmatchTasks, 10);
                } else {
                    temp = "通讯异常";
                    Log.d(TAG, temp + ": " + nRet);
//                    mtvMessage.setText(temp);
                    if(onFingerprintCallBack!=null){
                        onFingerprintCallBack.callBack(-1,temp,"");
                    }
                    return;
                }
            } else {
                temp = "通讯异常";
                Log.d(TAG, temp + "2: " + nRet);
                if(onFingerprintCallBack!=null){
                    onFingerprintCallBack.callBack(-1,temp,"");
                }
//                mtvMessage.setText(temp);
                return;
            }

        }
    };

    private void erollfp() {
        ssart = System.currentTimeMillis();
        ssend = System.currentTimeMillis();
        fpcharbuf = 1;
        isfpon = false;
        testcount = 0;
        objHandler_fp.postDelayed(fperollTasks, 0);
    }

    private Runnable fperollTasks = new Runnable() {
        public void run()// 运行该服务执行此函数
        {
            String temp = "";
            long timecount = 0;
            ssend = System.currentTimeMillis();
            timecount = (ssend - ssart);

            if (timecount > 10000) {
                temp = activity.getResources().getString(R.string.readfptimeout_str) + "\r\n";
                if(onFingerprintCallBack!=null){
                    onFingerprintCallBack.callBack(-1,temp,"");
                }
//                mtvMessage.setText(temp);
                return;
            }
            if (fperoll) {
                temp = activity.getResources().getString(R.string.stoperoll_str) + "\r\n";
                if(onFingerprintCallBack!=null){
                    onFingerprintCallBack.callBack(-1,temp,"");
                }
//                mtvMessage.setText(temp);
                return;
            }
            int nRet = 0;
            nRet = a6.ZAZGetImage(DEV_ADDR);
            if (nRet == 0) {
                testcount = 0;
                try {
                    thread.sleep(200);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                nRet = a6.ZAZGetImage(DEV_ADDR);
            }

            if (nRet == 0) {
                if (isfpon) {
                    temp = activity.getResources().getString(R.string.pickupfinger_str);

//                    mtvMessage.setText(temp);
                    if(onFingerprintCallBack!=null){
                        onFingerprintCallBack.callBack(0,temp,"");
                    }
                    ssart = System.currentTimeMillis();
                    objHandler_fp.postDelayed(fperollTasks, 100);
                    return;
                }
//		 		{
//			 		int[] len = { 0, 0 };
//					byte[] Image = new byte[256 * 360];
//					a6.ZAZUpImage(DEV_ADDR, Image, len);
//					String str = "/mnt/sdcard/test.bmp";
//					a6.ZAZImgData2BMP(Image, str);
//					temp ="获取图像成功";
//			 		mtvMessage.setText(temp);
//
//					Bitmap bmpDefaultPic;
//					bmpDefaultPic = BitmapFactory.decodeFile(str,null);
//					mFingerprintIv.setImageBitmap(bmpDefaultPic);
//		 		}
                nRet = a6.ZAZGenChar(DEV_ADDR, fpcharbuf);// != PS_OK) {
                if (nRet == a6.PS_OK) {
                    fpcharbuf++;
                    isfpon = true;
                    if (fpcharbuf > 2) {
                        nRet = a6.ZAZRegModule(DEV_ADDR);
                        if (nRet != a6.PS_OK) {
                            temp = activity.getResources().getString(R.string.RegModulefail_str);
                            if(onFingerprintCallBack!=null){
                                onFingerprintCallBack.callBack(-1,temp,"");
                            }
//                            mtvMessage.setText(temp);

                        } else {
                            nRet = a6.ZAZStoreChar(DEV_ADDR, 1, iPageID);
                            if (nRet == a6.PS_OK) {
                                temp = activity.getResources().getString(R.string.erollsuccess_str) ;
                                int[] iTempletLength = new int[1];
                                nRet = a6.ZAZUpChar(DEV_ADDR, 1, pTempletbase, iTempletLength);
                                //System.arraycopy(pTemplet, 0, pTempletbase, 0, 2304);
                                if(onFingerprintCallBack!=null){
                                    onFingerprintCallBack.callBack(1,temp,""+iPageID);
                                }
//                                mtvMessage.setText(temp);
                                iPageID++;
                            } else {
                                temp = activity.getResources().getString(R.string.erollfail_str);
                                if(onFingerprintCallBack!=null){
                                    onFingerprintCallBack.callBack(-1,temp,"");
                                }
                                //  mtvMessage.setText(temp);
                            }
                        }
                    } else {
                        temp = activity.getResources().getString(R.string.getfpsuccess_str);
                        //  mtvMessage.setText(temp);
                        if(onFingerprintCallBack!=null){
                            onFingerprintCallBack.callBack(1,temp,"***2");
                        }
                        ssart = System.currentTimeMillis();
                        objHandler_fp.postDelayed(fperollTasks, 500);

                    }
                } else {
                    temp = activity.getResources().getString(R.string.getfailchar_str);
                    //   mtvMessage.setText(temp);
                    if(onFingerprintCallBack!=null){
                        onFingerprintCallBack.callBack(-1,temp,""+iPageID);
                    }
                    ssart = System.currentTimeMillis();
                    objHandler_fp.postDelayed(fperollTasks, 1000);

                }

            } else if (nRet == a6.PS_NO_FINGER) {
                temp = activity.getResources().getString(R.string.readingfp_str) + ((10000 - (ssend - ssart))) / 1000 + "s";
                isfpon = false;
                if(onFingerprintCallBack!=null){
                    onFingerprintCallBack.callBack(0,temp,"");
                }
                //   mtvMessage.setText(temp);
                objHandler_fp.postDelayed(fperollTasks, 10);
            } else if (nRet == a6.PS_GET_IMG_ERR) {
                temp = activity.getResources().getString(R.string.getimageing_str);
                if(onFingerprintCallBack!=null){
                    onFingerprintCallBack.callBack(0,temp,"");
                }
                Log.d(TAG, temp + ": " + nRet);
                objHandler_fp.postDelayed(fperollTasks, 10);
                //  mtvMessage.setText(temp);
                return;
            } else if (nRet == -2) {
                testcount++;
                if (testcount < 3) {
                    temp = activity.getResources().getString(R.string.readingfp_str) + ((10000 - (ssend - ssart))) / 1000 + "s";
                    isfpon = false;
                    if(onFingerprintCallBack!=null){
                        onFingerprintCallBack.callBack(0,temp,"");
                    }
                    //    mtvMessage.setText(temp);
                    objHandler_fp.postDelayed(fpmatchTasks, 10);
                } else {
                    temp = activity.getResources().getString(R.string.Communicationerr_str);
                    Log.d(TAG, temp + ": " + nRet);
                    //  mtvMessage.setText(temp);
                    if(onFingerprintCallBack!=null){
                        onFingerprintCallBack.callBack(-1,temp,"");
                    }
                    return;
                }
            } else {
                temp = activity.getResources().getString(R.string.Communicationerr_str);
                Log.d(TAG, temp + ": " + nRet);
                //  mtvMessage.setText(temp);
                if(onFingerprintCallBack!=null){
                    onFingerprintCallBack.callBack(-1,temp,"");
                }
                return;
            }

        }
    };


    private void searchfp() {
        ssart = System.currentTimeMillis();
        ssend = System.currentTimeMillis();
        fpcharbuf = 1;
        testcount = 0;
        objHandler_fp.postDelayed(fpsearchTasks, 0);
    }

    private Runnable fpsearchTasks = new Runnable() {
        public void run()// 运行该服务执行此函数
        {
            String temp = "";
            long timecount = 0;
            int[] id_iscore = new int[1];
            ssend = System.currentTimeMillis();
            timecount = (ssend - ssart);

            if (timecount > 10000) {
                temp = activity.getResources().getString(R.string.readfptimeout_str) + "\r\n";
                //  mtvMessage.setText(temp);
                if(onFingerprintCallBack!=null){
                    onFingerprintCallBack.callBack(-1,temp,"");
                }
                return;
            }
            if (fpsearch) {
                temp = activity.getResources().getString(R.string.stopsearch_str) + "\r\n";
                //  mtvMessage.setText(temp);
                if(onFingerprintCallBack!=null){
                    onFingerprintCallBack.callBack(-1,temp,"");
                }
                return;
            }
            int nRet = 0;
            nRet = a6.ZAZGetImage(DEV_ADDR);
            if (nRet == 0) {
                testcount = 0;
                try {
                    thread.sleep(200);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                nRet = a6.ZAZGetImage(DEV_ADDR);
            }
            if (nRet == 0) {

                nRet = a6.ZAZGenChar(DEV_ADDR, fpcharbuf);// != PS_OK) {
                if (nRet == a6.PS_OK) {
                    nRet = a6.ZAZHighSpeedSearch(DEV_ADDR, 1, 0, 1000, id_iscore);
                    if (nRet == a6.PS_OK) {
                        temp = activity.getResources().getString(R.string.searchsuccess_str) + id_iscore[0];
                        if(onFingerprintCallBack!=null){
                            onFingerprintCallBack.callBack(1,temp,id_iscore+"");
                        }
                        //  mtvMessage.setText(temp);
                    } else {
                        temp = activity.getResources().getString(R.string.searchfail_str);
                        if(onFingerprintCallBack!=null){
                            onFingerprintCallBack.callBack(-1,temp,"");
                        }
                        //  mtvMessage.setText(temp);
                    }

                } else {
                    temp = activity.getResources().getString(R.string.getfailchar_str);
                    //  mtvMessage.setText(temp);
                    if(onFingerprintCallBack!=null){
                        onFingerprintCallBack.callBack(-1,temp,"");
                    }
                    ssart = System.currentTimeMillis();
                    objHandler_fp.postDelayed(fpsearchTasks, 1000);

                }

            } else if (nRet == a6.PS_NO_FINGER) {
                temp = activity.getResources().getString(R.string.readingfp_str) + ((10000 - (ssend - ssart))) / 1000 + "s";
                // mtvMessage.setText(temp);
                if(onFingerprintCallBack!=null){
                    onFingerprintCallBack.callBack(0,temp,"");
                }
                objHandler_fp.postDelayed(fpsearchTasks, 10);
            } else if (nRet == a6.PS_GET_IMG_ERR) {
                temp = activity.getResources().getString(R.string.getimageing_str);
                if(onFingerprintCallBack!=null){
                    onFingerprintCallBack.callBack(0,temp,"");
                }
                Log.d(TAG, temp + ": " + nRet);
                objHandler_fp.postDelayed(fpsearchTasks, 10);
                //  mtvMessage.setText(temp);
                return;
            } else if (nRet == -2) {
                testcount++;
                if (testcount < 3) {
                    temp = activity.getResources().getString(R.string.readingfp_str) + ((10000 - (ssend - ssart))) / 1000 + "s";
                    isfpon = false;
                    if(onFingerprintCallBack!=null){
                        onFingerprintCallBack.callBack(0,temp,"");
                    }
                    //  mtvMessage.setText(temp);
                    objHandler_fp.postDelayed(fpmatchTasks, 10);
                } else {
                    temp = activity.getResources().getString(R.string.Communicationerr_str);
                    if(onFingerprintCallBack!=null){
                        onFingerprintCallBack.callBack(-1,temp,"");
                    }
                    Log.d(TAG, temp + ": " + nRet);
                    //   mtvMessage.setText(temp);

                    return;
                }
            } else {
                temp = activity.getResources().getString(R.string.Communicationerr_str);
                Log.d(TAG, temp + ": " + nRet);
                if(onFingerprintCallBack!=null){
                    onFingerprintCallBack.callBack(-1,temp,"");
                }
                //  mtvMessage.setText(temp);

                return;
            }

        }
    };


    /*****************************************
     * 线程   end
     * ***************************************/


    private static String charToHexString(byte[] val) {
        String temp = "";
        for (int i = 0; i < val.length; i++) {
            String hex = Integer.toHexString(0xff & val[i]);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            temp += hex.toUpperCase();
        }
        return temp;
    }

    private int getrwusbdevices() {

        mDevManager = ((UsbManager) activity.getSystemService(Context.USB_SERVICE));
        permissionIntent = PendingIntent.getBroadcast(activity, 0, new Intent(ACTION_USB_PERMISSION), 0);
        IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
        activity.registerReceiver(mUsbReceiver, filter);
        //this.registerReceiver(mUsbReceiver, new IntentFilter(UsbManager.ACTION_USB_DEVICE_DETACHED));
        HashMap<String, UsbDevice> deviceList = mDevManager.getDeviceList();
        if (true) Log.e(TAG, "news:" + "mDevManager");


        for (UsbDevice tdevice : deviceList.values()) {
            Log.i(TAG, tdevice.getDeviceName() + " " + Integer.toHexString(tdevice.getVendorId()) + " "
                    + Integer.toHexString(tdevice.getProductId()));
            if (tdevice.getVendorId() == 0x2109 && (tdevice.getProductId() == 0x7638)) {
                Log.e(TAG, " 指纹设备准备好了 ");
                mDevManager.requestPermission(tdevice, permissionIntent);
                return 1;
            }
        }
        Log.e(TAG, "news:" + "mDevManager  end");
        return 2;
    }

    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            context.unregisterReceiver(mUsbReceiver);
            isusbfinshed = 0;
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                synchronized (context) {
                    device = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    Log.e("BroadcastReceiver", "3333");
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        if (device != null) {
                            if (true) Log.e(TAG, "Authorize permission " + device);
                            isusbfinshed = 1;
                        }
                    } else {
                        if (true) Log.e(TAG, "permission denied for device " + device);
                        device = null;
                        isusbfinshed = 2;

                    }
                }
            }
        }
    };

    private boolean WaitForInterfaces() {

        while (device == null || isusbfinshed == 0) {
            if (isusbfinshed == 2) break;
            if (isusbfinshed == 3) break;
        }
        if (isusbfinshed == 2)
            return false;
        if (isusbfinshed == 3)
            return false;
        return true;
    }

    private int OpenDeviceInterfaces() {
        UsbDevice mDevice = device;
        Log.d(TAG, "setDevice " + mDevice);
        int fd = -1;
        if (mDevice == null) return -1;
        connection = mDevManager.openDevice(mDevice);
        if (!connection.claimInterface(mDevice.getInterface(0), true)) return -1;

        if (mDevice.getInterfaceCount() < 1) return -1;
        intf = mDevice.getInterface(0);

        if (intf.getEndpointCount() == 0) return -1;

        if ((connection != null)) {
            if (true) Log.e(TAG, "open connection success!");
            fd = connection.getFileDescriptor();
            return fd;
        } else {
            if (true) Log.e(TAG, "finger device open connection FAIL");
            return -1;
        }
    }


    private int LongDunD8800_CheckEuq() {
        Process process = null;
        DataOutputStream os = null;

        // for (int i = 0; i < 10; i++)
        // {
        String path = "/dev/bus/usb/00*/*";
        String path1 = "/dev/bus/usb/00*/*";
        File fpath = new File(path);
        Log.d("*** LongDun D8800 ***", " check path:" + path);
        // if (fpath.exists())
        // {
        String command = "chmod 777 " + path;
        String command1 = "chmod 777 " + path1;
        Log.d("*** LongDun D8800 ***", " exec command:" + command);
        try {
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(command + "\n");
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
            return 1;
        } catch (Exception e) {
            Log.d("*** DEBUG ***", "Unexpected error - Here is what I know: " + e.getMessage());
        }
        //  }
        //  }
        return 0;
    }
}

