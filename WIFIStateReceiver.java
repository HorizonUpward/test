package com.john.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.util.List;

/**
 * @author wangyao
 * @package com.yongyao.wifiautoconnectiondemo
 * @describe TODO
 * @date 2018/10/31
 */
public class WIFIStateReceiver extends BroadcastReceiver {

    private static final String TAG = WIFIStateReceiver.class.getName();
    private Context mContext;
    List<ScanResult> scanResults;

    public WIFIStateReceiver(Context context) {
        this.mContext = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
            return;
        }
        scanResults =  WIFIConnectionManager.getInstance(mContext).getWifiManager().getScanResults();
        for (int i = 0 ; i < scanResults.size();i++) {
            Log.e(TAG,"scanResults:----"+(scanResults.get(i)).SSID);
        }
        if (!WIFIConnectionManager.getInstance(mContext).isConnected("ESP8266")) {
            WIFIConnectionManager.getInstance(mContext).connect("ESP8266", "qwer1234");
        }
    }

}
