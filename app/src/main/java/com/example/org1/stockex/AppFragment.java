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
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import control.ApiRequest;
import control.MiscFunction;
import itf.HolderResponse;
import itf.PoolResponse;
import model.HolderQueryResponseModel;
import model.MatchModel;
import model.OrderModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class AppFragment extends Fragment implements HolderResponse, PoolResponse {
    TextView mTvHolder;
    TextView mTvGem;
    Button mBtnStockPosition,mBtnBuy,mBtnSell;
    SharedPreferences sharedPref;
    ApiRequest api;
    MiscFunction miscFuntion = new MiscFunction();
    HolderQueryResponseModel hqr;
    JsonElement element;
    String holder;
    Thread dataThread;
    Boolean bool=true;
    public AppFragment() {
        // Required empty public constructor
    }


    class getDataThread implements Runnable {
        public void run(){
            while (bool){
                api.holderRequest();
                try {
                    Thread.sleep(1000);
                }catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
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
        getDataThread getDataThread = new getDataThread();
        dataThread = new Thread(getDataThread);
        dataThread.start();
        api.matchPoolRequest();
        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        bool=false;
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

    @Override
    public void invokeBuyPoolResponse(boolean success, JsonElement element) {
        if (!success) {
        }
        else {
            this.element = element;
            ArrayList<OrderModel> buyOrderModel = miscFuntion.convertJsonToOrderObject(element);
           Toast.makeText(getActivity(),buyOrderModel.get(1).getTx_id(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void invokeSellPoolResponse(boolean success, JsonElement element) {
        if (!success) {
        }
        else {
            this.element = element;
            ArrayList<OrderModel> sellOrderModel = miscFuntion.convertJsonToOrderObject(element);
            Toast.makeText(getActivity(),sellOrderModel.get(0).getTx_id(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void invokeMatchPoolResponse(boolean success, JsonElement element) {
        if (!success) {
        }
        else {
            this.element = element;
            ArrayList<MatchModel> matchOrderModel = miscFuntion.convertJsonToMatchObject(element);
            Toast.makeText(getActivity(),matchOrderModel.get(1).getPrice(),Toast.LENGTH_LONG).show();
        }
    }

}
