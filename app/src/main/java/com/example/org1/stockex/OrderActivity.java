package com.example.org1.stockex;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import control.ApiRequest;
import itf.InvokeResponseInterface;
import model.HolderQueryResponseModel;
import model.InvokeResponseModel;

public class OrderActivity extends AppCompatActivity implements InvokeResponseInterface {
    TextView mTvGem, mTvTitle;
    RadioGroup mRdShare;
    EditText mEdtAmount, mEdtPrice;
    Button mBtnBuy, mBtnBack;
    HolderQueryResponseModel hqr;
    InvokeResponseInterface iri = this;
    String holder;
    String type;
    ApiRequest api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = new ApiRequest(this);
        setContentView(R.layout.activity_order);
        mapping();
        setListener();

    }

    public String[] setArgs() {
        String[] args = new String[5];
        args[0] = holder;
        args[1] = type;
        int checked = mRdShare.getCheckedRadioButtonId();
        if (checked == R.id.rd_vcb) {
            args[2] = "VCB";
        } else if (checked == R.id.rd_mwg) {
            args[2] = "MWG";
        } else if (checked == R.id.rd_vnm) {
            args[2] = "VNM";
        }
        args[3] = mEdtAmount.getText().toString();
        args[4] = mEdtPrice.getText().toString();
        return args;
    }

    public void mapping() {
        mTvGem = findViewById(R.id.tv_gemNum);
        mTvTitle = findViewById(R.id.tv_buy);
        Intent intent = getIntent();
        hqr = (HolderQueryResponseModel) intent.getSerializableExtra("holder_info");
        holder = intent.getStringExtra("holder_name");
        type = intent.getStringExtra("type");
        mTvTitle.setText("Set " + type + " order");
        if (hqr.getGem() == null) {
            mTvGem.setText("0");
        } else {
            mTvGem.setText(hqr.getGem());
        }
        mRdShare = findViewById(R.id.rd_share);
        mEdtAmount = findViewById(R.id.edt_amount);
        mEdtPrice = findViewById(R.id.edt_price);
        mBtnBack = findViewById(R.id.btn_buy_back);
        mBtnBuy = findViewById(R.id.btn_buy);
        mBtnBuy.setText(type);
        mEdtPrice.setHint("Price to "+type);
    }

    public void setListener() {
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mBtnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBtnBuy.setEnabled(false);
                if (checkCondition()) {
                    String[] args = setArgs();
                    api.invokeOrderRequest(args);
                } else {
                    mBtnBuy.setEnabled(true);
                }

            }
        });
    }

    public boolean checkCondition() {
        if (type.equals("buy")) {
            int gem = Integer.parseInt(hqr.getGem());
            int amount = Integer.parseInt(mEdtAmount.getText().toString());
            int price = Integer.parseInt(mEdtPrice.getText().toString());
            if (gem < amount * price) {
                Toast.makeText(this, "Your gem is not enough. Try again!", Toast.LENGTH_LONG).show();
                return false;
            }
        } else if (type.equals("sell")) {
            int amount = Integer.parseInt(mEdtAmount.getText().toString());
            int shareAvai = getShareNumber();
            if (amount>shareAvai) {
                Toast.makeText(this, "Your share is not enough. Try again!", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
    }

    public int getShareNumber() {
        int checked = mRdShare.getCheckedRadioButtonId();
        if (checked == R.id.rd_vcb) {
            if (hqr.getVCB() == null) return 0;
            return Integer.parseInt(hqr.getVCB());
        } else if (checked == R.id.rd_mwg) {
            if (hqr.getMWG() == null) return 0;
            return Integer.parseInt(hqr.getMWG());
        } else if (checked == R.id.rd_vnm) {
            if (hqr.getVNM() == null) return 0;
            return Integer.parseInt(hqr.getVNM());
        }
        return -1;
    }

    @Override
    public void invokeResponse(boolean success, InvokeResponseModel ir) {
        if (!success) {
            Toast.makeText(this, "Error occurs. Connection time out!", Toast.LENGTH_LONG).show();
            onBackPressed();
        } else {
            Toast.makeText(this, "Order added succesfully", Toast.LENGTH_LONG).show();
            onBackPressed();
        }
    }
}
