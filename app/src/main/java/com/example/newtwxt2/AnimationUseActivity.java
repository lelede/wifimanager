package com.example.newtwxt2;


import android.Manifest;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.example.newtwxt2.kuozhan.GnShuoMing;
import com.example.newtwxt2.kuozhan.XiangQingActivity;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.example.newtwxt2.util.Utils.getContext;

public class AnimationUseActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    List<Status> realWifiList = new ArrayList<>();
    private AnimationAdapter mAnimationAdapter;
    private boolean isOpenEye = false;
    private boolean ischeck = false;
    ProgressBar pbWifiLoading;
    private static final String TAG = "MainActivity";
    private int connectType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testdd);
        pbWifiLoading = (ProgressBar) this.findViewById(R.id.pb_wifi_loading);
        hidingProgressBar();
        final SpannableStringBuilder style = new SpannableStringBuilder();//《功能声明》需要的
        style.append("分享为公共WiFi/已阅读并同意《功能声明》");
        mRecyclerView = findViewById(R.id.rs_scrollView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAnimationAdapter = new AnimationAdapter();
        mAnimationAdapter.setAnimationEnable(true);
        mRecyclerView.setAdapter(mAnimationAdapter);
        registerPermission();
        mAnimationAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                //解决item只能点击一个的问题
                RelativeLayout layout = (RelativeLayout )view.getParent();
                ImageView xiangq =layout.findViewById(R.id.diandian);
                TextView wifinm = layout.findViewById(R.id.wifi_name);
                Status status = mAnimationAdapter.getItem(position);
                wifinm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //**************************************************************************************
                        //点击名字后，进行判断如果没有密码或者连接过，直接进行连接
                        //如果有密码弹出mydialog
//                        Status wifiBean = realWifiList.get(position);
                        if(status.getState().equals(AppContants.WIFI_STATE_UNCONNECT) || status.getState().equals(AppContants.WIFI_STATE_CONNECT)){
                            String capabilities = status.getCapabilities();
                            if(WifiSupport.getWifiCipher(capabilities) == WifiSupport.WifiCipherType.WIFICIPHER_NOPASS){//无需密码
                                WifiConfiguration tempConfig  = WifiSupport.isExsits(status.getSsid(),AnimationUseActivity.this);
                                if(tempConfig == null){
                                    WifiConfiguration exsits = WifiSupport.createWifiConfig(status.getSsid(), null, WifiSupport.WifiCipherType.WIFICIPHER_NOPASS);
                                    WifiSupport.addNetWork(exsits, AnimationUseActivity.this);
                                }else{
                                    WifiSupport.addNetWork(tempConfig, AnimationUseActivity.this);
                                }
                            }else{   //需要密码，弹出输入密码dialog
                                mydialog(position);
                            }
                        }
            }
        });
                //详情页的跳转
                xiangq.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent2 = new Intent(AnimationUseActivity.this, XiangQingActivity.class);
                        Bundle bd = new Bundle();
                        bd.putString("wifiname",status.getSsid());
                        bd.putInt("wifilevel",Math.abs(status.getLevel()));
                        bd.putString("capabilities",status.getCapabilities());
                        intent2.putExtras(bd);
                        startActivity(intent2);
                    }
                });
    }

    private void mydialog(int position){
        Status status = mAnimationAdapter.getItem(position);
        Dialog mDialog ;
        mDialog = new Dialog(AnimationUseActivity.this);
        //2.填充布局
        LayoutInflater inflater = LayoutInflater.from(AnimationUseActivity.this);
        View dialogView = inflater.inflate(R.layout.dailog_layout, null);
        TextView wifiname1 = dialogView.findViewById(R.id.wifi_dialog_name);
        ImageView showwifi1 = dialogView.findViewById(R.id.showwifi);
        EditText et_password1 = dialogView.findViewById(R.id.et_password);
        TextView queding1 = dialogView.findViewById(R.id.queding);
        CheckBox fxwf1 = dialogView.findViewById(R.id.fxwf);
        //将自定义布局设置进去
        mDialog.setContentView(dialogView);
        mDialog.show();
        mDialog.setCancelable(false);
        wifiname1.setText(status.getSsid());
        //CheckBox的点击事件
        fxwf1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ischeck) {
                    ischeck = true;
                    ClickableSpan clickableSpan = new ClickableSpan() {
                        @Override
                        public void onClick(View widget) {
                            Intent intent = new Intent(AnimationUseActivity.this, GnShuoMing.class);
                            startActivity(intent);
                        }
                    };
                    style.setSpan(clickableSpan, 17, 21, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    fxwf1.setMovementMethod(LinkMovementMethod.getInstance());
                    fxwf1.setText(style);

                }else{
                    ischeck = false;
                    fxwf1.setText("分享为公共WiFi");
                }
            }
        });
        //密码可见不可见的点击事件
        showwifi1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isOpenEye) {
                    showwifi1.setSelected(true);
                    isOpenEye = true;
                    //密码可见
                    et_password1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    showwifi1.setSelected(false);
                    isOpenEye = false;
                    //密码不可见
                    et_password1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        //*********************************************************************************************************
        //确定后WiFi的连接事件
        //进行判断如果不超过8位弹出提示信息
        //超出8位或8位，对输入的密码和WiFi的密码进行判断，匹配成功进行连接，不成功弹出提示信息，连接失败
        queding1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_password1.getText() != null && et_password1.getText().toString().length()>=8){
                if(status.getCapabilities().contains("WPA") || status.getCapabilities().contains("WP2") || status.getCapabilities().contains("WPS")){

                        WifiConfiguration temComfig = WifiSupport.isExsits(status.getSsid(),getContext());
                        if(temComfig == null)
                        {
                            WifiConfiguration wifiConfiguration = WifiSupport.createWifiConfig(status.getSsid(),et_password1.getText().toString(),WifiSupport.getWifiCipher(status.getCapabilities()));
                            WifiSupport.addNetWork(wifiConfiguration,getContext());
                        }else {
                            WifiSupport.addNetWork(temComfig,getContext());
                        }
                        Toast.makeText(AnimationUseActivity.this,"连接WiFi",Toast.LENGTH_SHORT).show();
                        mDialog.dismiss();
                }else if(status.getCapabilities().contains("WEP")){
                    WifiConfiguration temConfig1 = WifiSupport.isExsits(status.getSsid(),getContext());
                    if (temConfig1 == null){
                        WifiConfiguration wifiConfiguration = WifiSupport.createWifiConfig(status.getSsid(),et_password1.getText().toString(),WifiSupport.getWifiCipher(status.getCapabilities()));
                        WifiSupport.addNetWork(wifiConfiguration,getContext());
                    }
                    else {
                        WifiSupport.addNetWork(temConfig1,getContext());
                    }
                    Toast.makeText(AnimationUseActivity.this,"连接WiFi",Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();
                }

                }else {
                    Toast.makeText(AnimationUseActivity.this,"请输入8位至8位以上的密码",Toast.LENGTH_SHORT).show();
                }
//

            }
        });
        //取消的点击事件
        mDialog.getWindow().findViewById(R.id.quxiao).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss(); //取消对话框
            }
        });
    }
        });
    }
    private void registerPermission(){
        //动态获取定位权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] { Manifest.permission.ACCESS_COARSE_LOCATION },
                    100);
        } else {
            initAdapter();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            initAdapter();
        }
    }
    @Override
    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        return super.registerReceiver(receiver, filter);
    }
    //设置监听
    private void regiterWifiBroadcast() {
        WifiBroadcastReceiver wifiReceiver = new WifiBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);//监听wifi是开关变化的状态
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);//监听wifi连接状态广播,是否连接了一个有效路由
        filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);//监听wifi列表变化（开启一个热点或者关闭一个热点）
        filter.addAction(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION); //监听密码
        registerReceiver(wifiReceiver, filter);
    }

    private void initAdapter() {
        //WiFi实时数据传入
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        wifiManager.setWifiEnabled(true);
        wifiManager.startScan();
        regiterWifiBroadcast();
        List<ScanResult> scanWifiList = wifiManager.getScanResults();
        Log.e("ad", String.valueOf(scanWifiList));
        //存储数据：WiFi名称，WiFi加密方式，WiFi信号强度
        for (int i = 0; i < scanWifiList.size(); i++) {
            Status status = new Status();
            status.setSsid(scanWifiList.get(i).SSID);
            status.setState(AppContants.WIFI_STATE_UNCONNECT);
            status.setCapabilities(scanWifiList.get(i).capabilities);
            //calculateSignalLevel为计算信号等级函数,对于获取到的数值进行函数计算，返回真实数值
            status.setLevel(WifiManager.calculateSignalLevel(scanWifiList.get(i).level,100));
            realWifiList.add(status);
        }
        //列表排序
        Collections.sort(realWifiList, new Comparator<Status>() {
            @Override
            public int compare(Status o1, Status o2) {
                int diff = Math.abs(o1.getLevel())-Math.abs(o2.getLevel());
                if(diff>0){
                    return -1;
                }
                else if(diff<0){
                    return  1;
                }
                return 0;
            }
        });
        Log.e("E",String.valueOf(realWifiList));
        mAnimationAdapter.setList(realWifiList);
    }
    public class WifiBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())){
                int state = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
                switch (state){
                    /**
                     * WIFI_STATE_DISABLED    WLAN已经关闭
                     * WIFI_STATE_DISABLING   WLAN正在关闭
                     * WIFI_STATE_ENABLED     WLAN已经打开
                     * WIFI_STATE_ENABLING    WLAN正在打开
                     * WIFI_STATE_UNKNOWN     未知
                     */
                    case WifiManager.WIFI_STATE_DISABLED:{
                        Log.d(TAG,"已经关闭");
                        Toast.makeText(AnimationUseActivity.this,"WIFI处于关闭状态",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case WifiManager.WIFI_STATE_DISABLING:{
                        Log.d(TAG,"正在关闭");
                        break;
                    }
                    case WifiManager.WIFI_STATE_ENABLED:{
                        Log.d(TAG,"已经打开");
//                        initAdapter();
                        break;
                    }
                    case WifiManager.WIFI_STATE_ENABLING:{
                        Log.d(TAG,"正在打开");
                        break;
                    }
                    case WifiManager.WIFI_STATE_UNKNOWN:{
                        Log.d(TAG,"未知状态");
                        break;
                    }
                }
            }else if(WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())){
                NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                Log.d(TAG, "--NetworkInfo--" + info.toString());
                if(NetworkInfo.State.DISCONNECTED == info.getState()){//wifi没连接上
                    Log.d(TAG,"wifi没连接上");
                    hidingProgressBar();
                    for(int i = 0;i < realWifiList.size();i++){//没连接上将 所有的连接状态都置为“未连接”
                        realWifiList.get(i).setState(AppContants.WIFI_STATE_UNCONNECT);
                    }
                    mAnimationAdapter.notifyDataSetChanged();
                }else if(NetworkInfo.State.CONNECTED == info.getState()){//wifi连接上了
                    Log.d(TAG,"wifi连接上了");
                    hidingProgressBar();
                    WifiInfo connectedWifiInfo = WifiSupport.getConnectedWifiInfo(AnimationUseActivity.this);
                    //连接成功 跳转界面 传递ip地址
                    Toast.makeText(AnimationUseActivity.this,"wifi连接上了",Toast.LENGTH_SHORT).show();
                    connectType = 1;
                    wifiListSet(connectedWifiInfo.getSSID(),connectType);
                }else if(NetworkInfo.State.CONNECTING == info.getState()){//正在连接
                    Log.d(TAG,"wifi正在连接");
                    showProgressBar();
                    WifiInfo connectedWifiInfo = WifiSupport.getConnectedWifiInfo(AnimationUseActivity.this);
                    connectType = 2;
                    wifiListSet(connectedWifiInfo.getSSID(),connectType );
                }
            }
//            else if(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION.equals(intent.getAction())){
//                Log.d(TAG,"网络列表变化了");
//                wifiListChange();
//            }
        }
    }
//    public void wifiListChange(){
//        initAdapter();
//        WifiInfo connectedWifiInfo = WifiSupport.getConnectedWifiInfo(this);
//        if(connectedWifiInfo != null){
//            wifiListSet(connectedWifiInfo.getSSID(),connectType);
//        }
//    }
    public void wifiListSet(String wifiName , int type){
        int index = -1;
        Status wifiInfo = new Status();
        if(CollectionUtils.isNullOrEmpty(realWifiList)){
            return;
        }
        for(int i = 0;i < realWifiList.size();i++){
            realWifiList.get(i).setState(AppContants.WIFI_STATE_UNCONNECT);
        }
        //根据信号强度排序
        Collections.sort(realWifiList, new Comparator<Status>() {
            @Override
            public int compare(Status o1, Status o2) {
                int diff = Math.abs(o1.getLevel())-Math.abs(o2.getLevel());
                if(diff>0){
                    return -1;
                }
                else if(diff<0){
                    return  1;
                }
                return 0;
            }
        });
        for(int i = 0;i < realWifiList.size();i++){
            Status wifiBean = realWifiList.get(i);
            if(index == -1 && ("\"" + wifiBean.getSsid() + "\"").equals(wifiName)){
                index = i;
                wifiInfo.setLevel(wifiBean.getLevel());
                wifiInfo.setSsid(wifiBean.getSsid());
                wifiInfo.setCapabilities(wifiBean.getCapabilities());
                if(type == 1){
                    wifiInfo.setState(AppContants.WIFI_STATE_CONNECT);
                }else{
                    wifiInfo.setState(AppContants.WIFI_STATE_ON_CONNECTING);
                }
            }
        }
        if(index != -1){
            realWifiList.remove(index);
            realWifiList.add(0, wifiInfo);
            mAnimationAdapter.notifyDataSetChanged();
        }
    }
    public void hidingProgressBar() {
        pbWifiLoading.setVisibility(View.GONE);
    }
    public void showProgressBar() {
        pbWifiLoading.setVisibility(View.VISIBLE);
    }

}