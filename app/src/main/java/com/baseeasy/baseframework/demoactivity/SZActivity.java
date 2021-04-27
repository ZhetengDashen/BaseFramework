package com.baseeasy.baseframework.demoactivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.baseeasy.baseframework.R;
import com.szt.IUsbConnState;
import com.szt.SZTandroid;
import com.szt.UsbController;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SZActivity extends AppCompatActivity {

    private boolean IsCharRuning=false;
    private boolean IsimageRuning=false;
    private boolean IsMacthRuning=false;
    private boolean IsRegisterRuning=false;
    private boolean IsSearchRuning=false;

    private int delID;


    static boolean[] fptable=new boolean[512];
    int m_FpErollCount = 0;

    private int fpcharbuf = 0;
    private TextView mtvMessage;

    long ssart = System.currentTimeMillis();
    long ssend = System.currentTimeMillis();
    private Handler objHandler_fp;
    private HandlerThread thread;
    private ImageView mFingerprintIv ;

    public final Boolean POWER_ZA_FP=false;
    public final Boolean POWER_ZA_CARD=false;

    public  int ZAVendorId = 0x2109;
    public  int ZAProductId = 0x7638;
    private Boolean Isroot=false;
    private int defDeviceType=2;//2 1.0  5 2.0
    private int defiCom=3;
    private int defiBaud=(57600/9600);


    SZTandroid fp = new SZTandroid();
    String TAG = "fp";
    int DEV_ADDR = 0xffffffff;
    String sdCardRoot = Environment.getExternalStorageDirectory()
            .getAbsolutePath();

    private UsbController m_usbBase;
    Button mbtnopen;


    private Spinner mySpinner;
    private ArrayAdapter<String> adapter;
    private static final String[] m={
            "usb_v1.0",
            "串口1_v1.0",
            "串口2_v1.0",
            "串口3_v1.0",
            "串口4_v1.0",
            "串口5_v1.0",
            "usb_v2.0",
            "串口1_v2.0",
            "串口2_v2.0",
            "串口3_v2.0",
            "串口4_v2.0",
            "串口5_v2.0",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_z);

        m_usbBase = new UsbController(this,m_IConnectionHandler,  ZAVendorId, ZAProductId);

        //隐藏状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initSet();


        mtvMessage = (TextView) findViewById(R.id.textView2);
        mFingerprintIv = (ImageView)findViewById(R.id.imageView1);
        mtvMessage.setText(fp.init());



        thread = new HandlerThread("MyHandlerThread");
        thread.start();
        objHandler_fp = new Handler();//

        ///特征值下载测试
        Button mbtntest = (Button) findViewById(R.id.btndownchar);
        mbtntest.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                byte[] te=new byte[512];
                int i;
                for(i=0;i<512;i++)
                {

                    te[i]=(byte)i;
                }
                fp.SZTDownChar(DEV_ADDR,fp.CHAR_BUFFER_A, te, 512);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                byte[] pte=new byte[512];
                int[] iTempletLength= new int[2];
                fp.SZTUpChar(DEV_ADDR,fp.CHAR_BUFFER_A, pte, iTempletLength);
                for(i=0;i<512;i++)
                {

                    Log.d(TAG,"== "+pte[i]);
                }
            }
        });

        mbtnopen = (Button) findViewById(R.id.btnopen);
        mbtnopen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                int status = 0;
                if (mbtnopen.getText().equals(getResources().getString(R.string.openstr))) {
                    if(Isroot){
                        status = fp.SZTOpenDeviceEx(-1, defDeviceType, defiCom, defiBaud, 0, 0);
                        fp.SZTSetImageSize(0);
                        if(status==1){
                            Toast.makeText(SZActivity.this, "打开设备成功",Toast.LENGTH_SHORT).show();
                            mbtnopen.setText(getResources().getString(R.string.closestr));
                        }else{
                            Toast.makeText(SZActivity.this, "打开设备失败",Toast.LENGTH_SHORT).show();
                        }
                    }else {
//						Toast.makeText(MainActivity.this, "defDeviceType "+defDeviceType,Toast.LENGTH_SHORT).show();
                        if((defDeviceType==2)||(defDeviceType==5))
                        {
                            m_usbBase.init();
                            Log.d(TAG, "open fd: " + m_usbBase.usbfd);
                        }
                        else
                        {
                            status = fp.SZTOpenDeviceEx(-1, defDeviceType, defiCom, defiBaud, 0, 0);
                            fp.SZTSetImageSize(0);
                            if(status==1){
                                Toast.makeText(SZActivity.this, "打开设备成功",Toast.LENGTH_SHORT).show();
                                mbtnopen.setText(getResources().getString(R.string.closestr));
                            }else{
                                Toast.makeText(SZActivity.this, "打开设备失败",Toast.LENGTH_SHORT).show();
                            }
                        }



                    }

                    Log.d(TAG, " open status: " + status);

                } else {

                    status = fp.SZTCloseDeviceEx();
                    Log.d(TAG, " close status: " + status);
                    mbtnopen.setText(getResources().getString(R.string.openstr));

                }
            }

        });


        //比对指纹
        Button mbtnmatch = (Button) findViewById(R.id.btnmacth);
        mbtnmatch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Getfpmatch();
            }
        });
        //获取特征值
        Button mbtngetchar = (Button) findViewById(R.id.btngetchar);
        mbtngetchar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Getfpchar();
            }
        });

        //获取图像
        Button mbtngetimg = (Button) findViewById(R.id.btngetimg);
        mbtngetimg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Getfpimg();


            }

        });

        Button mbtnregister = (Button) findViewById(R.id.btnregister);
        mbtnregister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Erolltask();

            }

        });

        Button mbtndelone = (Button) findViewById(R.id.btndelone);
        mbtndelone.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Delonetask();

            }

        });

        Button mbtnEmpty = (Button) findViewById(R.id.btndelall);
        mbtnEmpty.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Emptytask();

            }

        });

        Button mbtngetfpcnt = (Button) findViewById(R.id.btngetfpcnt);
        mbtngetfpcnt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                GetFPnumtask();

            }

        });

        Button mbtnsearch = (Button) findViewById(R.id.btnsearch);
        mbtnsearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                fpsearchtask();

            }

        });



    }

    //比对指纹
    public void Getfpmatch()
    {
        stopruning();
        ssart = System.currentTimeMillis();
        ssend = System.currentTimeMillis();
        fpcharbuf= 0;
        objHandler_fp.postDelayed(fpmatchTasks, 0);
        IsMacthRuning=true;
    }
    private Runnable fpmatchTasks = new Runnable() {
        @Override
        public void run()// 运行该服务执行此函数
        {
            String temp="";
            long timecount=0;
            ssend = System.currentTimeMillis();
            timecount = (ssend - ssart);
            if (timecount >10000)
            {
                temp ="读指纹等待超时"+"\r\n";
                mtvMessage.setText(temp);
                return;
            }
            int nRet = 0;
            nRet = fp.SZTGetImage(DEV_ADDR);
            if(nRet  == 0)
            {
                nRet= fp.SZTGenChar(DEV_ADDR, fpcharbuf+1);// != PS_OK) {
                if(nRet ==fp.PS_OK  )
                {
                    if(fpcharbuf!=0)
                    {	int[] iScore = { 0, 0 };
                        nRet= fp.SZTMatch(DEV_ADDR,  iScore);
                        if(nRet ==fp.PS_OK  )
                        {
                            temp ="比对成功   得分:"+iScore[0];
                            mtvMessage.setText(temp);
                        }
                        else
                        {
                            temp ="比对失败   得分:"+iScore[0];
                            mtvMessage.setText(temp);
                        }
                        return;
                    }
                    fpcharbuf=1;
                    temp ="请再次按指纹";
                    mtvMessage.setText(temp);
                    ssart = System.currentTimeMillis();
                    objHandler_fp.postDelayed(fpmatchTasks, 1000);
                }
                else
                {	temp ="特征太差，请重新录入";
                    mtvMessage.setText(temp);
                    ssart = System.currentTimeMillis();
                    objHandler_fp.postDelayed(fpmatchTasks, 1000);

                }

            }
            else if(nRet==fp.PS_NO_FINGER){
                temp = "正在读取指纹中   剩余时间:"+((10000-(ssend - ssart)))/1000 +"s";
                mtvMessage.setText(temp);
                objHandler_fp.postDelayed(fpmatchTasks, 10);
            }else if(nRet==fp.PS_GET_IMG_ERR){
                temp ="获取图像错误";
                Log.d(TAG, temp+": "+nRet);
                objHandler_fp.postDelayed(fpmatchTasks, 10);
                return;
            }
            else
            {
                temp ="设备异常";
                Log.d(TAG, temp+": "+nRet);
                mtvMessage.setText(temp);

                return;
            }
        }
    };

    //获取特征值
    public void Getfpchar()
    {
        stopruning();
        ssart = System.currentTimeMillis();
        ssend = System.currentTimeMillis();
        objHandler_fp.postDelayed(fpcharTasks, 0);
        IsCharRuning=true;

    }
    private Runnable fpcharTasks = new Runnable() {
        @Override
        public void run()// 运行该服务执行此函数
        {
            String temp="";
            long timecount=0;
            ssend = System.currentTimeMillis();
            timecount = (ssend - ssart);

            if (timecount >10000)
            {
                temp ="读指纹等待超时"+"\r\n";
                mtvMessage.setText(temp);
                return;
            }
            int nRet = 0;
            nRet = fp.SZTGetImage(DEV_ADDR);
            if(nRet  == 0)
            {
                nRet= fp.SZTGenChar(DEV_ADDR, fp.CHAR_BUFFER_A);// != PS_OK) {
                if(nRet ==fp.PS_OK)
                {
                    int[] iTempletLength = { 0, 0 };
                    byte[] pTemplet = new byte[512];
                    nRet=fp.SZTUpChar(DEV_ADDR,fp.CHAR_BUFFER_A, pTemplet, iTempletLength);
                    if(nRet ==fp.PS_OK)
                    {
                        temp="指纹特征:\r\n";
                        temp +=charToHexString(pTemplet);
                        mtvMessage.setText(temp);
                    }


                }
                else
                {	temp ="特征太差，请重新录入";
                    mtvMessage.setText(temp);
                    ssart = System.currentTimeMillis();
                    objHandler_fp.postDelayed(fpcharTasks, 1000);

                }
//				Log.d(TAG, "Gen Char fail!");
//			}
            }
            else if(nRet==fp.PS_NO_FINGER){
                temp = "正在读取指纹中   剩余时间:"+((10000-(ssend - ssart)))/1000 +"s";
                mtvMessage.setText(temp);
                objHandler_fp.postDelayed(fpcharTasks, 10);
            }else if(nRet==fp.PS_GET_IMG_ERR){
                temp ="获取图像错误";
                Log.d(TAG, temp+"1: "+nRet);
                objHandler_fp.postDelayed(fpcharTasks, 10);
                //mtvMessage.setText(temp);
                return;
            }
            else
            {
                temp ="设备异常";
                Log.d(TAG, temp+"1: "+nRet);
                mtvMessage.setText(temp);
                return;
            }

        }
    };



    //获取图像
    public void Getfpimg()
    {
        stopruning();
        ssart = System.currentTimeMillis();
        ssend = System.currentTimeMillis();
        objHandler_fp.postDelayed(fpTasks, 0);
        IsimageRuning=true;
    }
    private Runnable fpTasks = new Runnable() {
        @Override
        public void run()// 运行该服务执行此函数
        {

            String temp="";
            long timecount=0;
            ssend = System.currentTimeMillis();
            timecount = (ssend - ssart);

            if (timecount >10000)
            {
                temp ="读指纹等待超时"+"\r\n";
                mtvMessage.setText(temp);
                return;
            }
            int nRet = 0;
            nRet = fp.SZTGetImage(DEV_ADDR);
            if(nRet  == 0)
            {
                int[] len = { 0, 0 };
                byte[] Image = new byte[256 * 288];
                fp.SZTUpImage(DEV_ADDR, Image, len);
                String str = "/mnt/sdcard/test.bmp";
                fp.SZTImgData2BMP(Image, str);
                temp ="获取图像成功";
                mtvMessage.setText(temp);

                Bitmap bmpDefaultPic;
                bmpDefaultPic = BitmapFactory.decodeFile(str,null);
                mFingerprintIv.setImageBitmap(bmpDefaultPic);
            }
            else if(nRet==fp.PS_NO_FINGER){
                temp = "正在读取指纹中   剩余时间:"+((10000-(ssend - ssart)))/1000 +"s";
                mtvMessage.setText(temp);
                objHandler_fp.postDelayed(fpTasks, 100);
            }
            else if(nRet==fp.PS_GET_IMG_ERR){
                temp ="获取图像错误";
                Log.d(TAG, temp+"2: "+nRet);
                objHandler_fp.postDelayed(fpTasks, 100);
                return;
            }
            else
            {
                temp ="设备异常";
                Log.d(TAG, temp+"2: "+nRet);
                mtvMessage.setText(temp);
                return;
            }

        }
    };


    public void Erolltask()
    {
        stopruning();
        ssart = System.currentTimeMillis();
        ssend = System.currentTimeMillis();
        m_FpErollCount=0;
        objHandler_fp.postDelayed(fperollTasks, 0);
        IsRegisterRuning=true;
    }

    private Runnable fperollTasks = new Runnable() {
        @Override
        public void run()// 运行该服务执行此函数
        {
            String temp="";
            long timecount=0;
            ssend = System.currentTimeMillis();
            timecount = (ssend - ssart);

            if (timecount >10000)
            {
                temp ="读指纹等待超时"+"\r\n";
                mtvMessage.setText(temp);
                return;
            }
            int nRet = 0;
            nRet = fp.SZTGetImage(DEV_ADDR);
            if(nRet  == 0)
            {
                int[] len = { 0, 0 };
                byte[] Image = new byte[256 * 288];
                fp.SZTUpImage(DEV_ADDR, Image, len);
                String str = "/mnt/sdcard/test.bmp";
                fp.SZTImgData2BMP(Image, str);
                temp ="获取图像成功";
                mtvMessage.setText(temp);
                Bitmap bmpDefaultPic;
                bmpDefaultPic = BitmapFactory.decodeFile(str,null);
                mFingerprintIv.setImageBitmap(bmpDefaultPic);
                int w_nRet=0;
                if (m_FpErollCount == 0) {
                    w_nRet = fp.SZTGenChar(DEV_ADDR, fp.CHAR_BUFFER_A);
                    m_FpErollCount = 1;
                    temp ="\r\n-->生成特征A成功";
                    ssart = System.currentTimeMillis(); // 获取开始时间
                } else {
                    w_nRet = fp.SZTGenChar(DEV_ADDR, fp.CHAR_BUFFER_B);
                    temp ="\r\n-->生成特征B成功";
                    m_FpErollCount = 2;
                    ssart = System.currentTimeMillis(); // 获取开始时间
                }

                Log.d(TAG, "w_nRet: "+w_nRet);

                if (w_nRet == 0) {

                    mtvMessage.setText(temp);

                    if (m_FpErollCount < 2) {

                        objHandler_fp.postDelayed(fperollTasks, 500);
                        return;

                    } else {

                        if (w_nRet == 0) {

                            w_nRet = fp.SZTRegModule(DEV_ADDR);
                            int id = 0;
                            for(int i=0;i<512;i++){
                                if(!fptable[i]){
                                    id=i;
                                    fptable[i]=true;
                                    break;
                                }
                            }

                            w_nRet = fp.SZTStoreChar(DEV_ADDR, fp.CHAR_BUFFER_A,id);
                            if (w_nRet == 0) {
                                temp = "\r\n-->指纹存储成功"+ "  ID ＝ "+  id;
                                mtvMessage.setText(temp);

                            } else {
                                temp = "\r\n-->指纹存储失败，请确认指纹是否已经注册";
                                mtvMessage.setText(temp);
                            }
                        } else {
                            temp = "\r\n-->指纹存储失败";
                            mtvMessage.setText(temp);
                        }

                        mtvMessage.setText(temp);
                        // GetFBdata
                    }

                } else {
                    temp = "\r\n-->生成特征失败\r\n搜索失败";
                    mtvMessage.setText(temp);
                }

            }
            else if(nRet==fp.PS_NO_FINGER){
                temp = "正在读取指纹中   剩余时间:"+((10000-(ssend - ssart)))/1000 +"s";
                mtvMessage.setText(temp);
                objHandler_fp.postDelayed(fperollTasks, 100);
            }
            else if(nRet==fp.PS_GET_IMG_ERR){
                temp ="获取图像错误";
                Log.d(TAG, temp+"2: "+nRet);
                objHandler_fp.postDelayed(fperollTasks, 100);
                return;
            }
            else
            {
                temp ="设备异常";
                Log.d(TAG, temp+"2: "+nRet);
                mtvMessage.setText(temp);
                return;
            }

        }
    };


    public void Delonetask() {

        final EditText et=new EditText(SZActivity.this);
        et.setText("0");
        new AlertDialog.Builder(SZActivity.this).setTitle("请输入ID").setIcon(
                android.R.drawable.ic_dialog_info).setView(et).setPositiveButton("确定",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                if(et.getText().toString().isEmpty())
                {

                    Toast.makeText(SZActivity.this, "ID不能为空",Toast.LENGTH_SHORT).show();
                }else{
                    delID= Integer.parseInt(et.getText().toString());
                    ssart = System.currentTimeMillis();
                    ssend = System.currentTimeMillis();
                    objHandler_fp.postDelayed(DeloneTasks, 0);

                }


            }
        }).setNegativeButton("取消",null).show();


    }

    private Runnable DeloneTasks = new Runnable() {
        @Override
        public void run()// 运行该服务执行此函数
        {
            String temp="";
            int w_nRet = fp.SZTDelChar(DEV_ADDR,delID,1);
            if (w_nRet == 0) {
                temp = "\r\n-->删除指纹成功  ID: "+delID;

            } else {
                temp = "\r\n-->删除指纹失败  ID: "+delID;

            }
            mtvMessage.setText(temp);
        }
    };


    public void Emptytask() {

        ssart = System.currentTimeMillis();
        ssend = System.currentTimeMillis();

        objHandler_fp.postDelayed(EmptyTasks, 0);
    }

    private Runnable EmptyTasks = new Runnable() {
        @Override
        public void run()// 运行该服务执行此函数
        {
            String temp="";
            int w_nRet = fp.SZTEmpty(DEV_ADDR);
            if (w_nRet == 0) {
                temp = "\r\n-->清空指纹成功 ";

            } else {
                temp = "\r\n-->清空指纹失败";

            }
            mtvMessage.setText(temp);
        }
    };


    public void GetFPnumtask() {

        ssart = System.currentTimeMillis();
        ssend = System.currentTimeMillis();

        objHandler_fp.postDelayed(GetFPnumTasks, 0);
    }

    private Runnable GetFPnumTasks = new Runnable() {
        @Override
        public void run()// 运行该服务执行此函数
        {
            String temp="";
            int[] num= new int[2];
            int w_nRet = fp.SZTTemplateNum(DEV_ADDR,num);
            if (w_nRet == 0) {
                temp = "\r\n-->指纹总数: "+num[0];

            } else {
                temp = "\r\n-->获取指纹总数失败";

            }
            mtvMessage.setText(temp);
        }
    };

    public void fpsearchtask() {

        stopruning();
        ssart = System.currentTimeMillis();
        ssend = System.currentTimeMillis();
        objHandler_fp.postDelayed(fpsearchTasks, 0);
        IsSearchRuning=true;
    }

    private Runnable fpsearchTasks = new Runnable() {
        @Override
        public void run()// 运行该服务执行此函数
        {
            String temp="";
            int nRet = 0;
            nRet = fp.SZTGetImage(DEV_ADDR);
            if(nRet  == 0)
            {
                int[] len = { 0, 0 };
                byte[] Image = new byte[256 * 288];
                fp.SZTUpImage(DEV_ADDR, Image, len);
                String str = "/mnt/sdcard/test.bmp";
                fp.SZTImgData2BMP(Image, str);
                temp ="获取图像成功";
                mtvMessage.setText(temp);

                Bitmap bmpDefaultPic;
                bmpDefaultPic = BitmapFactory.decodeFile(str,null);
                mFingerprintIv.setImageBitmap(bmpDefaultPic);

                int w_nRet=0;
                w_nRet = fp.SZTGenChar(DEV_ADDR, fp.CHAR_BUFFER_A);

                ssart = System.currentTimeMillis(); // 获取开始时间

                if (w_nRet == 0) {
                    temp ="\r\n-->生成特征成功";
                    mtvMessage.setText(temp);

                    int[] p_pnMatchedID = new int[1];
                    w_nRet = fp.SZTSearch(DEV_ADDR, fp.CHAR_BUFFER_A,0,512,p_pnMatchedID);
                    ssend = System.currentTimeMillis();
                    if (w_nRet == 0) {
                        temp += "\r\n-->搜索成功----->ID:"
                                + p_pnMatchedID[0] + "  耗时:"
                                + Long.toString(ssend - ssart) + "ms";
                        mtvMessage.setText(temp);

                    } else {
                        temp += "\r\n-->搜索失败----->" + "  耗时:"
                                + Long.toString(ssend - ssart) + "ms";
                        mtvMessage.setText(temp);
                    }
                } else {
                    temp = "\r\n-->生成特征失败\r\n搜索失败";
                    mtvMessage.setText(temp);
                }


            }
            else if(nRet==fp.PS_NO_FINGER){
                temp = "正在读取指纹中   剩余时间:"+((10000-(ssend - ssart)))/1000 +"s";
                mtvMessage.setText(temp);
                objHandler_fp.postDelayed(fpsearchTasks, 100);
            }
            else if(nRet==fp.PS_GET_IMG_ERR){
                temp ="获取图像错误";
                Log.d(TAG, temp+"2: "+nRet);
                objHandler_fp.postDelayed(fpsearchTasks, 100);
                return;
            }
            else
            {
                temp ="设备异常";
                Log.d(TAG, temp+"2: "+nRet);
                mtvMessage.setText(temp);
                return;
            }
        }
    };




    /**
     * 在SD卡上创建目录
     */
    public File createDirOnSDCard(String dir) {
        File dirFile = new File(sdCardRoot + File.separator + dir
                + File.separator);
        Log.v("createDirOnSDCard", sdCardRoot + File.separator + dir
                + File.separator);
        dirFile.mkdirs();
        return dirFile;
    }

    /**
     * 在SD卡上创建文件
     */
    public File createFileOnSDCard(String fileName, String dir)
            throws IOException {
        File file = new File(sdCardRoot + File.separator + dir + File.separator
                + fileName);
        Log.v("createFileOnSDCard", sdCardRoot + File.separator + dir
                + File.separator + fileName);
        file.createNewFile();
        return file;
    }

    /**
     * 判断SD卡上文件是否存在
     */
    public boolean isFileExist(String fileName, String path) {
        File file = new File(sdCardRoot + path + File.separator + fileName);
        return file.exists();
    }

    /**
     * 写入数据到SD卡中
     */
    public File writeData2SDCard(String path, String fileName, InputStream data) {
        File file = null;
        OutputStream output = null;

        try {
            createDirOnSDCard(path); // 创建目录
            file = createFileOnSDCard(fileName, path); // 创建文件
            output = new FileOutputStream(file);
            byte buffer[] = new byte[2 * 1024]; // 每次写2K数据
            int temp;
            while ((temp = data.read(buffer)) != -1) {
                output.write(buffer, 0, temp);
            }
            output.flush();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                output.close(); // 关闭数据流操作
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        return file;
    }

    private final IUsbConnState m_IConnectionHandler = new IUsbConnState() {
        @Override
        public void onUsbConnected() {
            int status ;

            status = fp.SZTOpenDeviceEx(m_usbBase.usbfd, defDeviceType, defiCom, defiBaud, 0, 0);
            fp.SZTSetImageSize(0);

            if(status==1){
                Toast.makeText(SZActivity.this, "打开设备成功",
                        Toast.LENGTH_SHORT).show();
                mbtnopen.setText(getResources().getString(R.string.closestr));
            }else{
                Toast.makeText(SZActivity.this, "打开设备失败",
                        Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onUsbPermissionDenied() {

        }

        @Override
        public void onDeviceNotFound() {

        }
    };

    private static String charToHexString(byte[] val) {
        String temp="";
        for(int i=0;i<val.length;i++)
        {
            String hex = Integer.toHexString(0xff & val[i]);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            temp += hex.toUpperCase();
        }
        return temp;
    }

    private void stopruning(){
        if(IsCharRuning){
            objHandler_fp.removeCallbacks(fpcharTasks);
            IsCharRuning=false;
        }

        if(IsimageRuning){
            objHandler_fp.removeCallbacks(fpTasks);
            IsimageRuning=false;
        }

        if(IsMacthRuning){
            objHandler_fp.removeCallbacks(fpmatchTasks);
            IsMacthRuning=false;
        }

        if(IsRegisterRuning){
            objHandler_fp.removeCallbacks(fperollTasks);
            IsRegisterRuning=false;
        }

        if(IsSearchRuning){
            objHandler_fp.removeCallbacks(fpsearchTasks);
            IsSearchRuning=false;
        }


    }


    private void initSet(){

        // myTextView = (TextView)findViewById(R.id.TextView_city);
        mySpinner = (Spinner)findViewById(R.id.Spinner_city);
        //第二步：为下拉列表定义一个适配器，这里就用到里前面定义的list。
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,m);
        //第三步：为适配器设置下拉列表下拉时的菜单样式。
        // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        mySpinner.setAdapter(adapter);
        //第五步：为下拉列表设置各种事件的响应，这个事响应菜单被选中
        mySpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                /* 将所选mySpinner 的值带入myTextView 中*/
                //    myTextView.setText("您选择的是："+ adapter.getItem(arg2));
                Log.d(TAG,"选择 "+arg2+" "+ adapter.getItem(arg2));
                switch(arg2){
                    case 0:
                        defDeviceType=2;//2 3.0  5 4.0
                        defiCom=0;
                        break;
                    case 1:
                        defDeviceType=1;//2 3.0  5 4.0
                        defiCom=1;
                    case 2:
                        defDeviceType=1;//2 3.0  5 4.0
                        defiCom=2;
                        break;
                    case 3:
                        defDeviceType=1;//2 3.0  5 4.0
                        defiCom=3;
                        break;
                    case 4:
                        defDeviceType=1;//2 3.0  5 4.0
                        defiCom=4;
                        break;
                    case 5:
                        defDeviceType=1;//2 3.0  5 4.0
                        defiCom=5;
                        break;
                    case 6:
                        defDeviceType=5;//2 3.0  5 4.0
                        defiCom=0;
                        break;
                    case 7:
                        defDeviceType=4;//2 3.0  5 4.0
                        defiCom=1;
                        break;
                    case 8:
                        defDeviceType=4;//2 3.0  5 4.0
                        defiCom=2;
                        break;
                    case 9:
                        defDeviceType=4;//2 3.0  5 4.0
                        defiCom=3;
                        break;
                    case 10:
                        defDeviceType=4;//2 3.0  5 4.0
                        defiCom=4;
                        break;
                    case 11:
                        defDeviceType=4;//2 3.0  5 4.0
                        defiCom=5;
                        break;
                    default:
                        ;


                }
                /* 将mySpinner 显示*/
                arg0.setVisibility(View.VISIBLE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                //  myTextView.setText("NONE");
                arg0.setVisibility(View.VISIBLE);
            }



        });
    }

}
