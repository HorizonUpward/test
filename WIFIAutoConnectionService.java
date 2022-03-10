package com.john.myapplication;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import static android.widget.Toast.*;

/**
 * @author wangyao
 * @package com.yongyao.wifiautoconnectiondemo
 * @describe TODO
 * @date 2018/10/31
 */
public class WIFIAutoConnectionService extends Service {

    public static final String SSID = "ESP8266";
    public static final String PWD = "qwer1234";
    private static final String TAG = WIFIAutoConnectionService.class.getSimpleName();
    private static final String KEY_SSID = "KEY_SSID";
    private static final String KEY_PWD = "KEY_PWD";


    /**
     * wifi名
     */
    private String mSsid = "ESP8266";
    /**
     * 密码
     */
    private String mPwd = "qwer1234";


    /**
     * 负责不断尝试连接指定wifi
     */
    private Handler mHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            WIFIConnectionManager.getInstance(WIFIAutoConnectionService.this).connect(mSsid, mPwd);
            boolean connected =  WIFIConnectionManager.getInstance(WIFIAutoConnectionService.this).isConnected(mSsid);
            Log.d(TAG, "handleMessage: wifi connected = " + connected);
            if (!connected) {
                Log.d(TAG, "handleMessage: re-try in 5 seconds");
                mHandler.sendEmptyMessageDelayed(0, 5000);//5s循环
            }
            return true;
            
        }
    });

    /**
     * 连接指定wifi热点, 失败后5s循环
     *
     * @param context 用于启动服务的上下文
     * @param ssid    默认HUD-WIFI
     * @param pwd     (WPA加密)默认12345678
     */
    public static void start(Context context, String ssid, String pwd) {
        Intent starter = new Intent(context,WIFIAutoConnectionService.class);
        starter.putExtra(KEY_SSID, ssid).putExtra(KEY_PWD, pwd);
        context.startService(starter);
        Log.d(TAG, "start: ");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * @return always null
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mSsid = intent.getStringExtra(KEY_SSID);
        mPwd = intent.getStringExtra(KEY_PWD);
        mHandler.sendEmptyMessage(0);
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

}
