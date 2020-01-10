package com.baseeasy.commonlibrary.fingerprint;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


import com.baseeasy.commonlibrary.R;
import com.baseeasy.commonlibrary.config.BaseConfig;
import com.baseeasy.commonlibrary.mytool.AppUtils;
import com.baseeasy.commonlibrary.mytool.file.FileUtils;
import com.za.finger.ZA_finger;
import com.za.finger.ZAandroid;

import java.io.DataOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.UUID;


/**
 * 作者：WangZhiQiang
 * 时间：2019/3/18
 * 邮箱：sos181@163.com
 * 描述：
 */
public class MyFingerprintUtils {
    private boolean isconnect = false;

    Bitmap bmpDefaultPic;

    private boolean fpflag = false;
    private boolean fpcharflag = false;
    private boolean fpmatchflag = false;
    private boolean fperoll = false;
    private boolean fpsearch = false;
    private boolean isfpon = false;


    long ssart = System.currentTimeMillis();
    long ssend = System.currentTimeMillis();
    private Handler objHandler_fp;
    //private HandlerThread thread;


    private int testcount = 0;
    private ZAandroid a6 = new ZAandroid();
    private int fpcharbuf = 1;
    private byte[] pTempletbase = new byte[2304];
    private int IMG_SIZE = 0;//同参数：（0:256x288 1:256x360）

    private String TAG = "zazdemo";
    private int DEV_ADDR = 0xffffffff;
    private byte[] pPassword = new byte[4];
    private Handler objHandler_3;
    private int rootqx = 1;///0 noroot  1root
    private int defDeviceType = 2;
    private int defiCom = 2;
    private int defiBaud = 6;
    private boolean isshowbmp = true;

    private int iPageID = 0;
    Context ahandle;
    //////////////////
    private int fpcharlen = 512;
    private int fpchcount = 2;


    public static final int opensuccess = 101;
    public static final int openfail = 102;
    public static final int usbfail = 103;
    public static final int enbtn = 104;
    public static final int disbtn = 105;
    Activity activity;
    private UsbManager mDevManager = null;
    private PendingIntent permissionIntent = null;
    private UsbInterface intf = null;
    private UsbDeviceConnection connection = null;
    private UsbDevice device = null;
    public int isusbfinshed = 0;
    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
    private final Handler m_fEvent = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String temp = null;
            switch (msg.what) {
                case opensuccess:
                    //打开设备成功
                    if (onFingerprintCallBack != null) {
                        onFingerprintCallBack.callBack(1, "打开设备成功", "","");
                    }

                    break;
                case openfail:
                    //打开设备失败
                    if (onFingerprintCallBack != null) {
                        onFingerprintCallBack.callBack(-1, "打开设备失败", "","");
                    }

                    break;
                case usbfail:
                    //Usb未准备完成
                    if (onFingerprintCallBack != null) {
                        onFingerprintCallBack.callBack(-1, "Usb未准备完成.", "","");
                    }
                    break;
                //btnopen.setText(getResources().getString(R.string.open_str));

            }
        }
    };

    private MyFingerprintUtils(Activity activity) {

        this.activity = activity;
        objHandler_fp = new Handler();//

        //初始化基本参数
        ahandle = activity;        //页面句柄
        rootqx = 1;            //系统权限(0:not root  1:root)
        defDeviceType = 12;    //设备通讯类型(2:usb  1:串口)
    }

    private volatile static MyFingerprintUtils singleton;

    public static MyFingerprintUtils getInstance(Activity activity) {
        if (singleton == null) {
            synchronized (MyFingerprintUtils.class) {
                if (singleton == null) {
                    singleton = new MyFingerprintUtils(activity);
                }
            }
        }
        return singleton;
    }

    OnFingerprintCallBack onFingerprintCallBack;

    public interface OnFingerprintCallBack {
        void callBack(int code, String msg, String data, String signatureTag);
    }

    public void setOnFingerprintCallBack(OnFingerprintCallBack onFingerprintCallBack) {
        this.onFingerprintCallBack = onFingerprintCallBack;
    }

    //打开设备
    public void fingerprintOpen(OnFingerprintCallBack onFingerprintCallBack) {
        this.onFingerprintCallBack = onFingerprintCallBack;
        // TODO Auto-generated method stub
        byte[] pPassword = new byte[4];
//				skipshow("open");
        Runnable r = new Runnable() {
            @Override
            public void run() {
                isusbfinshed = 3;
                //ZA_finger fppower = new ZA_finger();
                //	fppower.finger_power_on();
                //	Sleep(1000);
                OpenDev();
            }
        };
        Thread s = new Thread(r);
        s.start();
    }

    //关闭设备
    public void fingerprintClose(OnFingerprintCallBack onFingerprintCallBack) {
        this.onFingerprintCallBack = onFingerprintCallBack;
        CloseDev();
    }

    //获取指纹图像
    public void fingerprintImage(OnFingerprintCallBack onFingerprintCallBack) {
        this.onFingerprintCallBack = onFingerprintCallBack;


        if (isconnect == false) {
            //打开设备失败
            if (onFingerprintCallBack != null) {
                onFingerprintCallBack.callBack(-1, "请先打开设备", "","");
            }
            return;
        }
        m_fEvent.sendMessage(m_fEvent.obtainMessage(disbtn, 0, 0));
        setflag(true);
        fpflag = false;
//        objHandler_fp.removeCallbacks(fpcharTasks);
//        objHandler_fp.removeCallbacks(fperollTasks);
//        objHandler_fp.removeCallbacks(fpsearchTasks);
        objHandler_fp.removeCallbacks(fpTasks);
        readsfpimg();
    }


    //打开设备
    private void OpenDev() {
        // TODO Auto-generated method stub
        StringBuffer stringBuffer = new StringBuffer();

        m_fEvent.sendMessage(m_fEvent.obtainMessage(disbtn, 0, 0));

        Log.i(TAG, "start Opendev");
        int status = -1;
        rootqx = 1;
        //zaz060
        defDeviceType = 2;
        //zaz050
        //defDeviceType =12;
        rootqx = 1;

        if (1 == rootqx) {
            //	skipshow("tryusbroot");
            Log.i(TAG, "use by root ");
            LongDunD8800_CheckEuq();
            status = a6.ZAZOpenDevice(-1, defDeviceType, defiCom, defiBaud, 0, 0);
            Log.i(TAG, "status =  " + status + "  (1:success other：error)");
            if (status == 0) {
                status = a6.ZAZVfyPwd(DEV_ADDR, pPassword);
                a6.ZAZSetImageSize(IMG_SIZE);
            } else {
                rootqx = 0;
            }
        }

//		if(false)
        if (0 == rootqx) {
            Log.i(TAG, "use by not root ");
            device = null;
            isusbfinshed = 0;
            int fd = 0;
            isusbfinshed = getrwusbdevices();
            //skipshow("watting a time");
            Log.i(TAG, "waiting user put root ");
            if (WaitForInterfaces() == false) {
                m_fEvent.sendMessage(m_fEvent.obtainMessage(usbfail, 0, 0));
                return;
            }
            fd = OpenDeviceInterfaces();
            if (fd == -1) {
                m_fEvent.sendMessage(m_fEvent.obtainMessage(usbfail, 0, 0));
                return;
            }
            Log.e(TAG, "open fd: " + fd);
            status = a6.ZAZOpenDevice(fd, defDeviceType, defiCom, defiBaud, 0, 0);
            Log.e("ZAZOpenDeviceEx", "" + defDeviceType + "  " + defiCom + "   " + defiBaud + "  status " + status);
            if (status == 0) {
                status = a6.ZAZVfyPwd(DEV_ADDR, pPassword);
                a6.ZAZSetImageSize(IMG_SIZE);
            }
        }
        Log.e(TAG, " open status: " + status);
        if (status == 0) {
            m_fEvent.sendMessage(m_fEvent.obtainMessage(opensuccess, 0, 0));
            isconnect = true;
        } else {
            m_fEvent.sendMessage(m_fEvent.obtainMessage(openfail, 0, 0));
            if (defDeviceType == 2) {
                defDeviceType = 12;
            } else if (defDeviceType == 12) {
                defDeviceType = 2;
            }
            isconnect = false;
        }
        m_fEvent.sendMessage(m_fEvent.obtainMessage(enbtn, 0, 0));
    }


    //关闭设备
    private void CloseDev() {
        //a6.ZAZBT_rev(tmp, tmp.length);
        m_fEvent.sendMessage(m_fEvent.obtainMessage(disbtn, 0, 0));
//		skipshow("close");
        int status = a6.ZAZCloseDeviceEx();
        ZA_finger fppower = new ZA_finger();
        fppower.finger_power_off();
        Log.e(TAG, " close status: " + status);
        //offLine(false);

        isconnect = false;
        m_fEvent.sendMessage(m_fEvent.obtainMessage(enbtn, 0, 0));
        onFingerprintCallBack.callBack(0, "关闭成功", "","");
    }

    private void setflag(boolean value) {
        fpflag = value;
        fpcharflag = value;
        fpmatchflag = value;
        fperoll = value;
        fpsearch = value;


    }

    public int getrwusbdevices() {

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
        @Override
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

    public int LongDunD8800_CheckEuq() {
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

    public boolean WaitForInterfaces() {
        int timeover = 0;
        while (device == null || isusbfinshed == 0) {
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            timeover++;
            if (isusbfinshed == 2) {
                break;
            }
            if (isusbfinshed == 3) {
                break;
            }
            if (timeover > 100) {
                isusbfinshed = 2;
                break;
            }
        }
        if (isusbfinshed == 2) {
            return false;
        }
        if (isusbfinshed == 3) {
            return false;
        }
        return true;
    }

    public int OpenDeviceInterfaces() {
        UsbDevice mDevice = device;
        Log.d(TAG, "setDevice " + mDevice);
        int fd = -1;
        if (mDevice == null) {
            return -1;
        }
        connection = mDevManager.openDevice(mDevice);
        if (!connection.claimInterface(mDevice.getInterface(0), true)) {
            return -1;
        }

        if (mDevice.getInterfaceCount() < 1) {
            return -1;
        }
        intf = mDevice.getInterface(0);

        if (intf.getEndpointCount() == 0) {
            return -1;
        }

        if ((connection != null)) {
            if (true) {
                Log.e(TAG, "open connection success!");
            }
            fd = connection.getFileDescriptor();
            return fd;
        } else {
            if (true) {
                Log.e(TAG, "finger device open connection FAIL");
            }
            return -1;
        }
    }

    //获取图像
    public void readsfpimg() {
        ssart = System.currentTimeMillis();
        ssend = System.currentTimeMillis();
        testcount = 0;
        objHandler_fp.postDelayed(fpTasks, 0);
    }

    private Runnable fpTasks = new Runnable() {
        @Override
        public void run()// 运行该服务执行此函数
        {
            m_fEvent.sendMessage(m_fEvent.obtainMessage(disbtn, 0, 0));
            String temp = "";
            long st = System.currentTimeMillis();
            long sd = System.currentTimeMillis();
            long timecount = 0;
            ssend = System.currentTimeMillis();
            timecount = (ssend - ssart);
            if (timecount > 10000) {
                temp = activity.getResources().getString(R.string.readfptimeout_str) + "\r\n";

                if (onFingerprintCallBack != null) {
                    onFingerprintCallBack.callBack(-1, temp, "","");
                }
                m_fEvent.sendMessage(m_fEvent.obtainMessage(enbtn, 0, 0));
                return;
            }
            if (fpflag) {
                temp = activity.getResources().getString(R.string.stopgetimage_str) + "\r\n";

                if (onFingerprintCallBack != null) {
                    onFingerprintCallBack.callBack(-1, temp, "","");
                }
                m_fEvent.sendMessage(m_fEvent.obtainMessage(enbtn, 0, 0));
                return;
            }
            int nRet = 0;
            int mRet = 0;
            String SignatureTag="";
            st = System.currentTimeMillis();
            nRet = a6.ZAZGetImage(DEV_ADDR);
            mRet = nRet;
            sd = System.currentTimeMillis();
            timecount = (sd - st);
            temp = activity.getResources().getString(R.string.getimagesuccess_str) + "耗时:" + timecount + "ms\r\n";
            st = System.currentTimeMillis();
            if (nRet == 0) {
                testcount = 0;
                int[] len = {0, 0};
                byte[] Image = new byte[256 * 360];
                a6.ZAZUpImage(DEV_ADDR, Image, len);
                sd = System.currentTimeMillis();
                timecount = (sd - st);


                String str = FileUtils.SDPATH + BaseConfig.FOLDER_PATH.FINGERPRINT+"/" + UUID.randomUUID().toString().replace("-", "")+".bmp";

                a6.ZAZImgData2BMP(Image, str);
                temp = "获取图像成功";
//                mtvMessage.setText(temp);
                if (mRet == 0) {
                    mRet = a6.ZAZGenChar(DEV_ADDR, a6.CHAR_BUFFER_A);// != PS_OK) {
                    if (mRet == a6.PS_OK) {
                        int[] iTempletLength = {0, 0};
                        byte[] pTemplet = new byte[512];

                        mRet = a6.ZAZUpChar(DEV_ADDR, a6.CHAR_BUFFER_A, pTemplet, iTempletLength);
                        if (mRet == a6.PS_OK) {
                            SignatureTag= charToHexString(pTemplet);
                        }
                    }
                }
                if (onFingerprintCallBack != null) {
                    onFingerprintCallBack.callBack(1, temp, str,SignatureTag);
                }

                m_fEvent.sendMessage(m_fEvent.obtainMessage(enbtn, 0, 0));
            } else if (nRet == a6.PS_NO_FINGER) {
                temp = activity.getResources().getString(R.string.readingfp_str) + ((10000 - (ssend - ssart))) / 1000 + "." + (1000 - (ssend - ssart) % 1000) + "s";
                if (onFingerprintCallBack != null) {
                    onFingerprintCallBack.callBack(0, temp, "","");
                }
                objHandler_fp.postDelayed(fpTasks, 100);
            } else if (nRet == a6.PS_GET_IMG_ERR) {
                temp = activity.getResources().getString(R.string.getimageing_str);
                if (onFingerprintCallBack != null) {
                    onFingerprintCallBack.callBack(0, temp, "","");
                }
                objHandler_fp.postDelayed(fpTasks, 100);

                return;
            } else if (nRet == -2) {
                testcount++;
                if (testcount < 3) {
                    temp = activity.getResources().getString(R.string.readingfp_str) + ((10000 - (ssend - ssart))) / 1000 + "s";
                    isfpon = false;
                    if (onFingerprintCallBack != null) {
                        onFingerprintCallBack.callBack(0, temp, "","");
                    }
                    objHandler_fp.postDelayed(fpTasks, 10);
                } else {
                    temp = activity.getResources().getString(R.string.Communicationerr_str);
                    Log.d(TAG, temp + ": " + nRet);
                    if (onFingerprintCallBack != null) {
                        onFingerprintCallBack.callBack(-1, temp, "","");
                    }
                    m_fEvent.sendMessage(m_fEvent.obtainMessage(enbtn, 0, 0));
                    return;
                }
            } else {
                temp = activity.getResources().getString(R.string.Communicationerr_str);
                Log.d(TAG, temp + "2: " + nRet);
                if (onFingerprintCallBack != null) {
                    onFingerprintCallBack.callBack(-1, temp, "","");
                }
                m_fEvent.sendMessage(m_fEvent.obtainMessage(enbtn, 0, 0));
                return;
            }

        }
    };

    private static String charToHexString(byte[] val) {
        String temp = "";
        for (int i = 0; i < val.length; i++) {
            String hex = Integer.toHexString(0xFF & val[i]);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            temp += hex.toUpperCase();
        }
        return temp;
    }
}
