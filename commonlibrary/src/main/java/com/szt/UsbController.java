package com.szt;

/**
 * 作者：WangZhiQiang
 * 时间：2021/4/26
 * 邮箱：sos181@163.com
 * 描述：
 */
/*
 * UsbController.java
 * This file is part of UsbController
 *
 * Copyright (C) 2012 - Manuel Di Cerbo
 *
 * UsbController is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * UsbController is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with UsbController. If not, see <http://www.gnu.org/licenses/>.
 */



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
import android.util.Log;

import com.baseeasy.commonlibrary.fingerprint.MyFingerprintUtils;

import java.util.HashMap;
import java.util.Iterator;

//import android.os.

/**
 * (c) Neuxs-Computing GmbH Switzerland
 * @author Manuel Di Cerbo, 02.02.2012
 *
 */


public class UsbController {

    private final Context mApplicationContext;
    private final UsbManager mUsbManager;
    private final int VID;
    private final int PID;



    private boolean m_bInit = false;

    private UsbDeviceConnection m_usbConn = null;
    private UsbInterface m_usbIf = null;

    private final IUsbConnState mConnectionHandler;

    protected static final String ACTION_USB_PERMISSION = "ch.serverbox.android.USB";

    public int usbfd;
    /**
     * Activity is needed for onResult
     *
     * @param parentActivity
     */
    public UsbController(Activity parentActivity, IUsbConnState connectionHandler, int vid, int pid){
        mConnectionHandler = connectionHandler;
        mApplicationContext = parentActivity.getApplicationContext();
        mUsbManager = (UsbManager) mApplicationContext.getSystemService(Context.USB_SERVICE);
        VID = vid;
        PID = pid;
        // m_abyTransferBuf = new byte[512];
        //init();
    }

    public void init(){
        enumerate(new IPermissionListener() {
            @Override
            public void onPermissionDenied(UsbDevice d) {
                UsbManager usbman = (UsbManager) mApplicationContext.getSystemService(Context.USB_SERVICE);
                PendingIntent pi = PendingIntent.getBroadcast(mApplicationContext, 0, new Intent(ACTION_USB_PERMISSION), 0);
                mApplicationContext.registerReceiver(mPermissionReceiver, new IntentFilter(ACTION_USB_PERMISSION));
                usbman.requestPermission(d, pi);
            }
        });
    }

    public void uninit(){
        if (m_usbConn != null)
        {
            m_usbConn.releaseInterface(m_usbIf);
            m_usbConn.close();
            m_usbConn = null;
            m_bInit = false;
        }

        //stop();
    }

    public void stop()
    {
        try{
            mApplicationContext.unregisterReceiver(mPermissionReceiver);
        }catch(IllegalArgumentException e){};//bravo
    }

    public boolean IsInit(){
        return m_bInit;
    }

    private void enumerate(IPermissionListener listener) {
        boolean bFound = false;
        l("enumerating");
        HashMap<String, UsbDevice> devlist = mUsbManager.getDeviceList();
        Iterator<UsbDevice> deviter = devlist.values().iterator();

        while (deviter.hasNext()) {
            UsbDevice d = deviter.next();
            l("Found device: " + String.format("%04X:%04X", d.getVendorId(), d.getProductId()));

//            Toast.makeText(mApplicationContext, "Found device: " + String.format("%04X:%04X", d.getVendorId(), d.getProductId()), Toast.LENGTH_SHORT).show();
//			DebugManage.WriteLog2("Found device: " + String.format("%04X:%04X", d.getVendorId(), d.getProductId()));

            if (d.getVendorId() == VID && d.getProductId() == PID) {
                bFound = true;
                l("Device under: " + d.getDeviceName());
                if (!mUsbManager.hasPermission(d))
                {
//                    Toast.makeText(mApplicationContext, "enumerate, hasPermission return false" , Toast.LENGTH_SHORT).show();
                    listener.onPermissionDenied(d);
                }
                else{
//                    Toast.makeText(mApplicationContext, "enumerate, GetConnInerface start" , Toast.LENGTH_SHORT).show();
                    //startHandler(d);

                    usbfd=mUsbManager
                            .openDevice(d).getFileDescriptor();
                    Log.e(TAG, "open fd: " + usbfd);
                    mConnectionHandler.onUsbConnected();
                    // GetConnInerface(d);
                    //TestComm(d);
                    return;
                }
                break;
            }
        }
        if (bFound == false)
        {
//			Toast.makeText(mApplicationContext, "no more devices found" , Toast.LENGTH_SHORT).show();
//			DebugManage.WriteLog2("no more devices found");
            mConnectionHandler.onDeviceNotFound();
        }
    }

    private class PermissionReceiver extends BroadcastReceiver {
        private final IPermissionListener mPermissionListener;

        public PermissionReceiver(IPermissionListener permissionListener) {
            mPermissionListener = permissionListener;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            mApplicationContext.unregisterReceiver(this);
            if (intent.getAction().equals(ACTION_USB_PERMISSION)) {
                if (!intent.getBooleanExtra(
                        UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                    mPermissionListener.onPermissionDenied((UsbDevice) intent
                            .getParcelableExtra(UsbManager.EXTRA_DEVICE));

                    mConnectionHandler.onUsbPermissionDenied();
                } else {
                    l("Permission granted");
                    UsbDevice dev = (UsbDevice) intent
                            .getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if (dev != null) {
                        if (dev.getVendorId() == VID
                                && dev.getProductId() == PID) {
                            //startHandler(dev);// has new thread
                            //GetConnInerface(dev);

                            usbfd=mUsbManager
                                    .openDevice(dev).getFileDescriptor();
                            Log.e(TAG, "open fd: " + usbfd);
                            mConnectionHandler.onUsbConnected();


                            //TestComm(dev);
                        }
                    } else {

                        mConnectionHandler.onDeviceNotFound();
                    }
                }
            }
        }
    }













    // END MAIN LOOP
    private BroadcastReceiver mPermissionReceiver = new PermissionReceiver(
            new IPermissionListener() {
                @Override
                public void onPermissionDenied(UsbDevice d) {
                    l("Permission denied on " + d.getDeviceId());


                }
            });

    private static interface IPermissionListener {
        void onPermissionDenied(UsbDevice d);
    }

    public final static String TAG = "USBController";

    private void l(Object msg) {
        Log.d(TAG, ">==< " + msg.toString() + " >==<");
    }


}
