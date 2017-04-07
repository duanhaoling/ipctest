package com.example.ipctest;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.ipctest.aidl.BookManagerActivity;
import com.example.ipctest.messenger.MessengerActivity;
import com.example.ipctest.provider.ProviderActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        Log.d("MainActivity", UserManager.sUserId++ + "");
        Log.d("MainActivity", "UserManager.sUserId = " + UserManager.sUserId);
    }

    private void initView() {
        findViewById(R.id.bt_messenger).setOnClickListener(this);
        findViewById(R.id.bt_book_manager).setOnClickListener(this);
        findViewById(R.id.bt_book_provider).setOnClickListener(this);
    }

    public void onclick1(View view) {
        gotoActivity(SecondActivity.class);
    }

    public void onclick2(View view) {
        gotoActivity(ThirdActivity.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_messenger:
                gotoActivity(MessengerActivity.class);
                break;
            case R.id.bt_book_manager:
                gotoActivity(BookManagerActivity.class);
                break;
            case R.id.bt_book_provider:
                gotoActivity(ProviderActivity.class);
                break;
            default:
                break;
        }
    }

    public void gotoActivity(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    public static final boolean isOPen(final Context context) {
        LocationManager locationManager
                = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        //通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        //通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }
        return false;
    }

    public static final void openGPS(Context context) {
        Intent GPSIntent = new Intent();
        GPSIntent.setClassName("com.android.settings",
                "com.android.settings.widget.SettingsAppWidgetProvider");
        GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
        GPSIntent.setData(Uri.parse("custom:3"));
        try {
            PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }
}
