package com.john.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static android.support.v4.app.ActivityCompat.requestPermissions;
import static java.security.AccessController.getContext;

/**
 * @author wangyao
 * @package com.yongyao.wifiautoconnectiondemo
 * @describe TODO
 * @date 2018/10/31
 */
public class WIFIConnectionManager {
    Context context;
    private static final String TAG = WIFIConnectionManager.class.getName();
    private static WIFIConnectionManager sInstance = null;

    final private WifiManager mWifiManager;
    //private ConnectivityManager mConnectivityManager;
    private int networkId;
    String B,C;

    ConnectivityManager cm ;

    ArrayList<ScanResult> list;					//存放周围wifi热点对象的列表



    public WIFIConnectionManager(Context context) {
        //mWifiManager = (android.net.wifi.WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
          mWifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
      //  cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

    }
    public void setWifiEnable(Context context,boolean state){
        //首先，用Context通过getSystemService获取wifimanager
        //调用WifiManager的setWifiEnabled方法设置wifi的打开或者关闭，只需把下面的state改为布尔值即可（true:打开 false:关闭）
        mWifiManager.setWifiEnabled(state);
    }
    public static WIFIConnectionManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (WIFIConnectionManager.class) {
                if (sInstance == null) {
                    sInstance = new WIFIConnectionManager(context);
                }
            }
        }
        return sInstance;
    }


    /**
     * 尝试连接指定wifi
     *
     * @param ssid     wifi名
     * @param password 密码
     * @return 是否连接成功
     */
    public boolean connect(@NonNull String ssid, @NonNull String password) {
        Log.d(TAG, "connect() called with: ssid = [" + ssid + "], password = [" + password + "]");
        Log.d(TAG, "connect: wifi opened = " + openWifi());
        boolean isConnected = isConnected(ssid);//当前已连接至指定wifi
        Log.d(TAG, "connect: is already connected = " + isConnected);
        if (isConnected) {
            return true;
        }
        networkId = mWifiManager.addNetwork(newWifiConfig(ssid, password, true));
        boolean result = mWifiManager.enableNetwork(networkId, true);
        Log.d(TAG, "connect: network enabled = " + result);
        return result;
    }

    public boolean checkWifiIsEnable() {

        return null != mWifiManager && mWifiManager.isWifiEnabled();
    }
    public  String V() {

        assert mWifiManager != null;

        WifiInfo M = mWifiManager.getConnectionInfo();

       String s = M.getSSID();

        int networkId = M.getNetworkId();

        List<WifiConfiguration> configuredNetworks = mWifiManager.getConfiguredNetworks();

        for (WifiConfiguration wifiConfiguration : configuredNetworks) {

            if (wifiConfiguration.networkId == networkId) {

                s = wifiConfiguration.SSID;
                if (s.length() > 2 && s.charAt(0) == '"' && s.charAt(s.length() - 1) == '"') {

                 B=  s.substring(1, s.length() - 1);}

                break;

            }

        }
         return B;

    }
    public boolean ScanWifiInfo(){
        Boolean flag = false,gule=true,fin;
        assert mWifiManager != null;
        mWifiManager.getConnectionInfo();
        mWifiManager.startScan();
        list = (ArrayList<ScanResult>) mWifiManager.getScanResults();
       for (ScanResult scanResult : list) {
            if (scanResult.SSID.length() > 2 && scanResult.SSID.charAt(0) == '"' && scanResult.SSID.charAt(scanResult.SSID.length() - 1) == '"') {

                B=  scanResult.SSID.substring(1, scanResult.SSID.length() - 1);}
            if (B.equals("TPGuest-text")) {
                flag=true;
            }else {gule=false;}

        }
        if(flag==true&&gule==false)
            fin=true;
        else fin=false;
        return fin;
    }
//    public String ScanWifiInfo1(){
//        //assert mWifiManager != null;
//        mWifiManager.getConnectionInfo();
//        mWifiManager.startScan();
//        list = (ArrayList<ScanResult>) mWifiManager.getScanResults();
//        for (ScanResult scanResult : list) {
////            if (scanResult.SSID.length() > 2 && scanResult.SSID.charAt(0) == '"' && scanResult.SSID.charAt(scanResult.SSID.length() - 1) == '"') {
////
////                B=  scanResult.SSID.substring(1, scanResult.SSID.length() - 1);}
//            C =scanResult.SSID;
//            C+=C ;
//
//        }
//        return C ;
//    }


    public String getWifiList() {

        //判断wifi 是否开启
        if (mWifiManager.isWifiEnabled()) {
            Log.e(TAG, " wifi 打开");
            List<ScanResult> scanWifiList = mWifiManager.getScanResults();
            //List<ScanResult> wifiList = new ArrayList<>();
            C=null;
            if (scanWifiList != null && scanWifiList.size() > 0) {
                for (int i = 0; i < scanWifiList.size(); i++) {
                    ScanResult scanResult = scanWifiList.get(i);
                    C+=(scanResult.SSID + "---" + scanResult.BSSID + "\n");
                   // wifiList.add(scanResult);
                }

            } else {
                Log.e(TAG, "未搜索到wifi");
            }
        } else {
            Log.e(TAG, " wifi 关闭");
        }

        return C;
    }
//    public Boolean getWifiList1() {
//
//        //判断wifi 是否开启
//        if (mWifiManager.isWifiEnabled()) {
//            Log.e(TAG, " wifi 打开");
//            mWifiManager.startScan();
//            List<ScanResult> scanWifiList = mWifiManager.getScanResults();
//            //List<ScanResult> wifiList = new ArrayList<>();
//            if (scanWifiList != null && scanWifiList.size() > 0) {
//                for (int i = 0; i < scanWifiList.size(); i++) {
//                    ScanResult scanResult = scanWifiList.get(i);
//                    C = scanResult.SSID;
//                    //C+=(scanResult.SSID + "---" + scanResult.BSSID + "\n");
//                    // wifiList.add(scanResult);
//                    if (C .equals("ESP8266")) {flag= true;}
//                }
//
//            } else {
//                Log.e(TAG, "未搜索到wifi");
//            }
//        } else {
//            Log.e(TAG, " wifi 关闭");
//        }
//
//        return flag;
//    }


//    private String ScanWifiInfo(){
//        mWifiManager.startScan(); //启动扫描
//        StringBuilder scanBuilder= new StringBuilder();
//        List<ScanResult> scanResults=wifiManager.getScanResults();//搜索到的设备列表
//        for (ScanResult scanResult : scanResults) {
//            scanBuilder.append("\n设备名："+scanResult.SSID
//                    +"\n信号强度："+wifiManager.calculateSignalLevel(scanResult.level,1001)+"\n");
//        }
//        return scanBuilder; }
//public String CC(){
//
//
//    NetworkInfo networkInfo = cm.getActiveNetworkInfo();
//    String ssid = networkInfo.getExtraInfo();
//    return ssid;
//}

    /**
     * 根据wifi名与密码配置 WiFiConfiguration, 每次尝试都会先断开已有连接
     *
     * @param isClient 当前设备是作为客户端,还是作为服务端, 影响SSID和PWD
     */
    @NonNull
    private WifiConfiguration newWifiConfig(String ssid, String password, boolean isClient) {
        WifiConfiguration config = new WifiConfiguration();
        config.allowedAuthAlgorithms.clear();
        config.allowedGroupCiphers.clear();
        config.allowedKeyManagement.clear();
        config.allowedPairwiseCiphers.clear();
        config.allowedProtocols.clear();
        if (isClient) {//作为客户端, 连接服务端wifi热点时要加双引号
            config.SSID = "\"" + ssid + "\"";
            config.preSharedKey = "\"" + password + "\"";
        } else {//作为服务端, 开放wifi热点时不需要加双引号
            config.SSID = ssid;
            config.preSharedKey = password;
        }
        config.hiddenSSID = true;
        config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
        config.status = WifiConfiguration.Status.ENABLED;
        return config;
    }

    /**
     * @return 热点是否已开启
     */
    public boolean isWifiEnabled() {
        try {
            Method methodIsWifiApEnabled = WifiManager.class.getDeclaredMethod("isWifiApEnabled");
            return (boolean) methodIsWifiApEnabled.invoke(mWifiManager);
        } catch (Exception e) {
            Log.e(TAG, "isWifiEnabled: ", e);
            return false;
        }
    }

    /**
     * 是否已连接指定wifi
     */
    public boolean isConnected(String ssid) {
        WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
        if (wifiInfo == null) {
            return false;
        }
        switch (wifiInfo.getSupplicantState()) {
            case AUTHENTICATING:
            case ASSOCIATING:
            case ASSOCIATED:
            case FOUR_WAY_HANDSHAKE:
            case GROUP_HANDSHAKE:
            case COMPLETED:
                return wifiInfo.getSSID().replace("\"", "").equals(ssid);
            default:
                return false;
        }
    }

    /**
     * 打开WiFi
     * @return
     */
    public boolean openWifi() {
        boolean opened = true;
        if (!mWifiManager.isWifiEnabled()) {
            opened = mWifiManager.setWifiEnabled(true);
        }
        return opened;
    }

    /**
     * 关闭wifi
     * @return
     */
    public boolean closeWifi() {
        boolean closed = true;
        if (mWifiManager.isWifiEnabled()) {
            closed = mWifiManager.setWifiEnabled(false);
        }
        return closed;
    }

    /**
     * 断开连接
     * @return
     */
    public WIFIConnectionManager disconnect() {
        if (networkId != 0) {
            mWifiManager.disableNetwork(networkId);
        }
        mWifiManager.disconnect();
        return this;
    }

    /**
     * 是否连接过指定Wifi
     */
    @Nullable
    public WifiConfiguration everConnected(String ssid) {
        List<WifiConfiguration> existingConfigs = mWifiManager.getConfiguredNetworks();
        if (existingConfigs == null || existingConfigs.isEmpty()) {
            return null;
        }
        ssid = "\"" + ssid + "\"";
        for (WifiConfiguration existingConfig : existingConfigs) {
            if (existingConfig.SSID.equals(ssid)) {
                return existingConfig;
            }
        }
        return null;
    }

    /**
     * 获取本机的ip地址
     */
    @Nullable
    public String getLocalIp() {
        return convertIp(mWifiManager.getConnectionInfo().getIpAddress());
    }

    private String convertIp(int ipAddress) {
        if (ipAddress == 0) return null;
        return ((ipAddress & 0xff) + "." + (ipAddress >> 8 & 0xff) + "."
                + (ipAddress >> 16 & 0xff) + "." + (ipAddress >> 24 & 0xff));
    }

    public WifiManager getWifiManager() {
        return mWifiManager;
    }
}
