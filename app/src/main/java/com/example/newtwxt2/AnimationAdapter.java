package com.example.newtwxt2;



import android.view.View;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;


import org.jetbrains.annotations.NotNull;


public class AnimationAdapter extends BaseQuickAdapter<Status, BaseViewHolder> implements OnItemClickListener, OnItemChildClickListener {


    public AnimationAdapter() {
        super(R.layout.item_list);
        addChildClickViewIds(R.id.wifi_name,R.id.diandian);
    }

    @Override
    protected void convert( BaseViewHolder ViewHolder, Status list) {
        ViewHolder.setText(R.id.wifi_name, list.getSsid());
        if(Math.abs(list.getLevel())>90){
            ViewHolder.setImageResource(R.id.wifi_image,R.drawable.wifi_1);
        }
        else if(Math.abs(list.getLevel())>70){
            ViewHolder.setImageResource(R.id.wifi_image,R.drawable.wifi_2);
        }
        else if(Math.abs(list.getLevel())>50){
            ViewHolder.setImageResource(R.id.wifi_image,R.drawable.wifi_3);
        }
        else {
            ViewHolder.setImageResource(R.id.wifi_image,R.drawable.wifi_4);
        }

    }

    @Override
    public void onItemChildClick(@NonNull @NotNull BaseQuickAdapter adapter, @NonNull @NotNull View view, int position) {
    }

    @Override
    public void onItemClick(@NonNull @NotNull BaseQuickAdapter<?, ?> adapter, @NonNull @NotNull View view, int position) {

    }
}
//扫描WiFi，传回实时数据
//用实时数据改变WiFi图标
//点击WiFi能够输入密码进行连接
//把扫一扫完成
//深度测速完成
