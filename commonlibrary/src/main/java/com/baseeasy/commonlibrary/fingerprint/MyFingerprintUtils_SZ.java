package com.baseeasy.commonlibrary.fingerprint;

import android.os.Handler;
import android.os.HandlerThread;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.szt.SZTandroid;
import com.szt.UsbController;

/**
 * 作者：WangZhiQiang
 * 时间：2021/4/26
 * 邮箱：sos181@163.com
 * 描述：申指 指纹录入工具类
 */
class MyFingerprintUtils_SZ {
    private boolean IsCharRuning=false;
    private boolean IsimageRuning=false;
    private boolean IsMacthRuning=false;
    private boolean IsRegisterRuning=false;
    private boolean IsSearchRuning=false;

    private int delID;


    static boolean[] fptable=new boolean[512];
    int m_FpErollCount = 0;

    private int fpcharbuf = 0;

    long ssart = System.currentTimeMillis();
    long ssend = System.currentTimeMillis();
    private Handler objHandler_fp;
    private HandlerThread thread;
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
    private UsbController m_usbBase;

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
}
