package com.example.ipctest.phonestate;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by ldh on 2017/3/28 0028.
 * 要监听android打电话和接电话，只需下面2步骤
 * 第一步，写一个Receiver继承自BroadcastReceiver
 * <p>
 * 来电action为TelephonyManager.ACTION_PHONE_STATE_CHANGED
 * <p>
 * 去电action为Intent.ACTION_NEW_OUTGOING_CALL
 * <p>
 * <p>
 * 第二步：在AndroidManifest.xml，配置写好的Receiver，并拦截相应的BroadCastAction，
 * 另外注意加上相应的权限。
 */

public class PhoneStateReceiver extends BroadcastReceiver {

    private static final String TAG = "PhoneStateReceiver";

//        private static MyPhoneStateListener phoneListener = new MyPhoneStateListener();

    private static boolean incomingFlag = false;

    private static String incoming_number = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        //如果是拨打电话
        if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
            incomingFlag = false;
            String phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            Log.i(TAG, "call OUT:" + phoneNumber);
        } else {
            //如果是来电
            TelephonyManager tm =
                    (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
            tm.listen(new MyPhoneStateListener(), PhoneStateListener.LISTEN_CALL_STATE);
            switch (tm.getCallState()) {
                case TelephonyManager.CALL_STATE_RINGING:
                    incomingFlag = true;//标识当前是来电
                    incoming_number = intent.getStringExtra("incoming_number");
                    Log.i(TAG, "RINGING :" + incoming_number);
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    if (incomingFlag) {
                        Log.i(TAG, "incoming ACCEPT :" + incoming_number);
                    }
                    break;

                case TelephonyManager.CALL_STATE_IDLE:
                    if (incomingFlag) {
                        Log.i(TAG, "incoming IDLE");
                    }
                    break;
            }
        }
    }


}

/**
 * 另一种使用方式
 */
//获取电话通讯服务
//   TelephonyManager tpm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
//创建一个监听对象，监听电话状态改变事件
//   tpm.listen(new MyPhoneStateListener(),PhoneStateListener.LISTEN_CALL_STATE);

class MyPhoneStateListener extends PhoneStateListener {
    private static final String TAG = "PhoneStateReceiver";

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        switch (state) {
            case TelephonyManager.CALL_STATE_IDLE: //空闲
                Log.i(TAG, "incoming IDLE—— MyPhoneStateListener");
                break;
            case TelephonyManager.CALL_STATE_RINGING: //来电
                Log.i(TAG, "RINGING —— MyPhoneStateListener");
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK: //摘机（正在通话中）
                Log.i(TAG, "incoming ACCEPT —— MyPhoneStateListener");
                break;
        }
    }

}

