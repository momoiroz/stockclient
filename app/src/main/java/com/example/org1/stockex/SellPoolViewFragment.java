package com.example.org1.stockex;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonElement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import control.ApiRequest;
import control.MiscFunction;
import itf.InvokeInterface;
import itf.PoolQueryService;
import itf.PoolResponse;
import model.OrderModel;
import model.QueryObject;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class SellPoolViewFragment extends Fragment implements InvokeInterface {
    private ArrayList<OrderModel>  sellOrderModel;
    JsonElement element;
    SharedPreferences sharedPref;
    OkHttpClient okHttpClient;
    Retrofit retrofit;
    String holder;
    String token;
    Activity activity;
    MiscFunction miscFunction = new MiscFunction();
    private ArrayList<String> list = new ArrayList();
    ListView lv;
    Thread dataThread;
    Boolean bool=true;
    public SellPoolViewFragment() {
        // Required empty public constructor
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void invokeBuy() {

    }

    @Override
    public void invokeSell() {
        sellPoolRequest();
    }

    @Override
    public void invokeMatch() {

    }

    class getDataThread implements Runnable {
        public void run(){
            while (bool){
                sellPoolRequest();
                try {
                    Thread.sleep(1000);
                }catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        bool=false;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_sell_pool_view, container, false);
        lv = v.findViewById(R.id.listview_sell);
//        getDataThread getDataThread = new getDataThread();
//        dataThread = new Thread(getDataThread);
//        dataThread.start();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, list);
        lv.setAdapter(arrayAdapter);
        return v;
    }
    public void sellPoolRequest(){
        sharedPref = activity.getSharedPreferences("mypref", activity.MODE_PRIVATE);
        holder = sharedPref.getString("holder","-");
        token = sharedPref.getString("token","-");
        setClient();
        if (holder.equals("-")||token.equals("-")) {
            Toast.makeText(activity,"Failed",Toast.LENGTH_LONG).show();
        }else{
            QueryObject queryObject = new QueryObject("sellpool");
            PoolQueryService prs = retrofit.create(PoolQueryService.class);
            prs.getPool(queryObject).enqueue(new Callback<JsonElement>() {
                @Override
                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                    JsonElement jtmp = response.body();
                    if (element == null||!element.equals(jtmp)) {
                        element = jtmp;
                        if (!element.toString().equals("{}"))
                        {
                            ArrayList<OrderModel> tmp = miscFunction.convertJsonToOrderObject(element);
                            sellOrderModel = tmp;
                            if (!list.isEmpty()) {
                                list.clear();
                            }
                            int counter=1;
                            int size = sellOrderModel.size();
                            for (int i = 0; i < sellOrderModel.size(); i++) {
                                if (sellOrderModel.get(i).getHolder().equals(holder)){
                                    list.add(counter + ". " + sellOrderModel.get(size-i-1).toString());
                                    counter++;
                                }
                            }

//                        arrayAdapter.clear();
//                        arrayAdapter.addAll(list);
//                        arrayAdapter.notifyDataSetChanged();
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, list);
                            lv.setAdapter(arrayAdapter);
                    }

                    }

                }
                @Override
                public void onFailure(Call<JsonElement> call, Throwable t) {
                //    Toast.makeText(activity,"Connection error. Cannot connect to the server!",Toast.LENGTH_LONG).show();
                }
            });
        }
    }
    public void setClient(){
        okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10,TimeUnit.SECONDS)
                .readTimeout(5,TimeUnit.SECONDS)
                .writeTimeout(5,TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request newRequest  = chain.request().newBuilder()
                                .addHeader("Authorization", "Bearer " + token)
                                .build();
                        return chain.proceed(newRequest);
                    }
                })
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://wibuchashu.ddns.net:4000/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
