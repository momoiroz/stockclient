package com.example.org1.stockex;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import model.HolderQueryResponseModel;

public class StockPositionActivity extends AppCompatActivity {
    Button mBtnBack;
    TextView mTvVCB,mTvMWG,mTvVNM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_position);
        mBtnBack = findViewById(R.id.btn_stock_back);
        mTvMWG = findViewById(R.id.tv_mwgNum);
        mTvVNM = findViewById(R.id.tv_vnmNum);
        mTvVCB = findViewById(R.id.tv_vcbNum);
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Intent intent = getIntent();
        HolderQueryResponseModel hqr = (HolderQueryResponseModel) intent.getSerializableExtra("holder_info");
        if (hqr.getMWG()==null) {
            mTvMWG.setText("0");
        } else {
            mTvMWG.setText(hqr.getMWG());
        }
        if (hqr.getVNM()==null) {
            mTvVNM.setText("0");
        } else {
            mTvVNM.setText(hqr.getVNM());
        }
        if (hqr.getVCB()==null) {
            mTvVCB.setText("0");
        } else {
            mTvVCB.setText(hqr.getVCB());
        }

    }
}
