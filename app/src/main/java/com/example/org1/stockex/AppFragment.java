package com.example.org1.stockex;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import control.ApiRequest;
import itf.HolderResponse;
import model.HolderQueryResponseModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class AppFragment extends Fragment implements HolderResponse {
    TextView mTvHolder;
    TextView mTvGem;
    Button mBtnStockPosition,mBtnBuy,mBtnSell;
    SharedPreferences sharedPref;
    ApiRequest api;
    HolderQueryResponseModel hqr;
    HolderResponse hr = this;
    String holder;
    public AppFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v;
        v = inflater.inflate(R.layout.app_layout, container, false);
        mapping(v);
        setListener();
        //send request
        api.holderRequest();
        return v;
    }
    public void mapping(View v){
        mTvHolder = v.findViewById(R.id.tv_holder);
        mBtnStockPosition = v.findViewById(R.id.btn_stock_position);
        mBtnBuy = v.findViewById(R.id.btn_app_buy);
        mBtnSell = v.findViewById(R.id.btn_app_sell);
        sharedPref = getActivity().getSharedPreferences("mypref", getActivity().MODE_PRIVATE);
        holder = sharedPref.getString("holder","-");
        if (!holder.equals("-")) {
            mTvHolder.setText(holder);
        }
        mTvGem = v.findViewById(R.id.tv_gem);
        api = new ApiRequest(getActivity());
    }
    public void setListener(){
        mBtnStockPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),StockPositionActivity.class);
                intent.putExtra("holder_info",hqr);
                getActivity().startActivity(intent);
            }
        });
        mBtnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),OrderActivity.class);
                intent.putExtra("holder_info",hqr);
                intent.putExtra("holder_name",holder);
                intent.putExtra("type","buy");
                getActivity().startActivity(intent);
            }
        });
        mBtnSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),OrderActivity.class);
                intent.putExtra("holder_info",hqr);
                intent.putExtra("holder_name",holder);
                intent.putExtra("type","sell");
                getActivity().startActivity(intent);
            }
        });
    }
    @Override
    public void invokeHolderResponse(boolean success, HolderQueryResponseModel hqr) {
        if (!success) {
            mTvGem.setText("Error occurs, cannot get GEM");
        }
        else {
            this.hqr = hqr;
            mTvGem.setText("Gem available: "+this.hqr.getGem());
        }
    }
}
