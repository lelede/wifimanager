package com.example.newtwxt2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Parcelable;
import android.util.Log;

public class WifiBroadcastReceiver extends BroadcastReceiver {

//    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public void onReceive(Context context, Intent intent) {

        if (WifiManager.SCAN_RESULTS_AVAILABLE_ACTION.equals(intent.getAction())){
//            int wifilist = intent.getIntExtra(WifiManager.EXTRA_SCAN_AVAILABLE,0);
//            Log.e("bd", String.valueOf(wifilist));
        }
//            ScanResult scanResult = new ScanResult();
//            String list = scanResult.SSID;
//            Log.e("as", String.valueOf(list));
//        }

        if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())) {//这个监听wifi的打开与关闭，与wifi的连接无关
            int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
            Log.e("ad", String.valueOf(wifiState));
            switch (wifiState) {
                case WifiManager.WIFI_STATE_DISABLED:
                    break;
                case WifiManager.WIFI_STATE_DISABLING:
                    break;
                //
            }
        }
        // 这个监听wifi的连接状态即是否连上了一个有效无线路由，当上边广播的状态是WifiManager.WIFI_STATE_DISABLING，和WIFI_STATE_DISABLED的时候，根本不会接到这个广播。
        // 在上边广播接到广播是WifiManager.WIFI_STATE_ENABLED状态的同时也会接到这个广播，当然刚打开wifi肯定还没有连接到有效的无线
        if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())) {
            Parcelable parcelableExtra = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if (null != parcelableExtra) {
                NetworkInfo networkInfo = (NetworkInfo) parcelableExtra;
                NetworkInfo.State state = networkInfo.getState();
                boolean isConnected = state== NetworkInfo.State.CONNECTED;//当然，这边可以更精确的确定状态
//                LogTag.showTAG_e(this.getClass().getSimpleName(), "isConnected"+isConnected);
                Log.e("ad",this.getClass().getSimpleName());
                if(isConnected){
                }else{

                }
            }
        }
 }
}
