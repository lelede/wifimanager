package com.example.newtwxt2.kuozhan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newtwxt2.R;

public class XiangQingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiang_qing);
        TextView xq_wifiname1 = findViewById(R.id.xq_wifiname);
        TextView xq_wifilevel1 = findViewById(R.id.xq_wifilevel);
        TextView xq_cap1 = findViewById(R.id.xq_wificap);
        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        String xq_wifiname2 = bd.getString("wifiname");
        int xq_wifilevel2 = bd.getInt("wifilevel");
        String a =String.valueOf(xq_wifilevel2);
        String xq_cap2 = bd.getString("capabilities");
        xq_wifiname1.setText(xq_wifiname2);
        xq_wifilevel1.setText(a+"%");
        xq_cap1.setText(xq_cap2);








    }
}