package com.example.org1.stockex;


import android.app.Activity;
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
import model.MatchModel;
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
public class MatchPoolViewFragment extends Fragment implements InvokeInterface {
    private ArrayList<MatchModel> matchModel;
    JsonElement element;
    SharedPreferences sharedPref;
    OkHttpClient okHttpClient;
    Retrofit retrofit;
    String holder;
    String token;
    MiscFunction miscFunction = new MiscFunction();
    private ArrayList<String> list = new ArrayList();
    ListView lv;
    Thread dataThread;
    Boolean bool = true;
    Activity activity;

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public MatchPoolViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void invokeBuy() {

    }

    @Override
    public void invokeSell() {

    }

    @Override
    public void invokeMatch() {
        matchPoolRequest();
    //    Toast.makeText(activity, "Failed", Toast.LENGTH_LONG).show();
    }

    class getDataThread implements Runnable {
        public void run() {
            while (bool) {
                matchPoolRequest();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        bool = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_match_pool_view, container, false);
        lv = v.findViewById(R.id.listview_match);
//        getDataThread getDataThread = new getDataThread();
//        dataThread = new Thread(getDataThread);
//        dataThread.start();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, list);
        lv.setAdapter(arrayAdapter);
        return v;
    }

    public void matchPoolRequest() {
        sharedPref = activity.getSharedPreferences("mypref", activity.MODE_PRIVATE);
        holder = sharedPref.getString("holder", "-");
        token = sharedPref.getString("token", "-");
        setClient();
        if (holder.equals("-") || token.equals("-")) {
            Toast.makeText(activity, "Failed", Toast.LENGTH_LONG).show();
        } else {
            QueryObject queryObject = new QueryObject("matchpool");
            PoolQueryService prs = retrofit.create(PoolQueryService.class);
            prs.getPool(queryObject).enqueue(new Callback<JsonElement>() {
                @Override
                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                    JsonElement jtmp = response.body();
                    if (element == null || !element.equals(jtmp)) {
                        element = jtmp;
                        if (!element.toString().equals("{}")) {
                            ArrayList<MatchModel> tmp = miscFunction.convertJsonToMatchObject(element);

                            matchModel = tmp;
                            if (!list.isEmpty()) {
                                list.clear();
                            }
                            int counter = 1;
                            int size= matchModel.size();
                            for (int i = 0; i < matchModel.size(); i++) {
                                if (matchModel.get(i).getBuyer().equals(holder)) {
                                    list.add(counter + ". BUY: " + matchModel.get(size-i-1).toString());
                                    counter++;
                                } else if (matchModel.get(i).getSeller().equals(holder)) {
                                    list.add(counter + ". SELL: " + matchModel.get(size-i-1).toString());
                                    counter++;
                                }
                            }
//                        arrayAdapter.clear();
//                        arrayAdapter.addAll(list);
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, list);
                            lv.setAdapter(arrayAdapter);
//                    arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, list);
//                    lv.setAdapter(arrayAdapter);
                        }

                    }

                }

                @Override
                public void onFailure(Call<JsonElement> call, Throwable t) {
                   // Toast.makeText(activity, "Connection error. Cannot connect to the server!", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void setClient() {
        okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request newRequest = chain.request().newBuilder()
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
