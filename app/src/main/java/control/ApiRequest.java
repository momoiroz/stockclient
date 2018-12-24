package control;

import android.app.Activity;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.org1.stockex.AppFragment;
import com.example.org1.stockex.BuyPoolViewFragment;
import com.example.org1.stockex.MatchPoolViewFragment;
import com.example.org1.stockex.OrderActivity;
import com.example.org1.stockex.MainActivity;
import com.example.org1.stockex.R;
import com.example.org1.stockex.SellPoolViewFragment;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import itf.HolderQueryService;
import itf.HolderResponse;
import itf.InvokeResponseInterface;
import itf.LoginResponse;
import itf.LoginService;
import itf.OrderInvokeService;
import itf.PoolQueryService;
import itf.PoolResponse;
import model.HolderQueryResponseModel;
import model.InvokeBuyObject;
import model.InvokeResponseModel;
import model.LoginObject;
import model.QueryObject;
import model.UserLogResponseModel;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRequest {
    UserLogResponseModel ulr;
    HolderQueryResponseModel hqr;
    InvokeResponseModel ir;
    JsonElement element;
    private Activity activity;
    SharedPreferences sharedPref;
    OkHttpClient okHttpClient;
    Retrofit retrofit;
    String token;
    public ApiRequest() {
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
    public void setClientForLogin(){
        okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10,TimeUnit.SECONDS)
                .readTimeout(5,TimeUnit.SECONDS)
                .writeTimeout(5,TimeUnit.SECONDS)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://wibuchashu.ddns.net:4000/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public ApiRequest(Activity activity) {
        this.activity = activity;
    }
    public void loginRequest(final String username, String password)  {
        setClientForLogin();
                LoginObject ro = new LoginObject(username,"Org1",password);
                LoginService rs = retrofit.create(LoginService.class);
                rs.register(ro).enqueue(new Callback<UserLogResponseModel>() {
                    @Override
                    public void onResponse(Call<UserLogResponseModel> call, Response<UserLogResponseModel> response) {
                        ulr = response.body();
                        sharedPref = activity.getSharedPreferences("mypref", activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("holder", username);
                        editor.commit();
                        LoginResponse lr = (MainActivity) activity;
                        lr.invokeLoginResponse(ulr);
                }

                    @Override
                    public void onFailure(Call<UserLogResponseModel> call, Throwable t) {
                        Toast.makeText(activity,"Connection error. Cannot connect to the server!",Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void holderRequest() {
        sharedPref = activity.getSharedPreferences("mypref", activity.MODE_PRIVATE);
        String holder = sharedPref.getString("holder","-");
        token = sharedPref.getString("token","-");
        setClient();
        if (holder.equals("-")||token.equals("-")) {
            HolderResponse hr = (AppFragment) ((MainActivity) activity).getSupportFragmentManager().findFragmentByTag("app_frag");
            hr.invokeHolderResponse(false,hqr);
        }else {
            QueryObject queryObject = new QueryObject(holder);
            HolderQueryService hqs = retrofit.create(HolderQueryService.class);
            hqs.getHolder(queryObject).enqueue(new Callback<HolderQueryResponseModel>() {
                @Override
                public void onResponse(Call<HolderQueryResponseModel> call, Response<HolderQueryResponseModel> response) {
                    hqr=response.body();
                    HolderResponse hr = (AppFragment) ((MainActivity) activity).getSupportFragmentManager().findFragmentByTag("app_frag");
                    hr.invokeHolderResponse(true,hqr);
                }

                @Override
                public void onFailure(Call<HolderQueryResponseModel> call, Throwable t) {
                    Toast.makeText(activity,"Connection error. Cannot connect to the server!",Toast.LENGTH_LONG).show();
                }
            });
        }

    }
//    public void buyPoolRequest(){
//        sharedPref = activity.getSharedPreferences("mypref", activity.MODE_PRIVATE);
//        String holder = sharedPref.getString("holder","-");
//        token = sharedPref.getString("token","-");
//        setClient();
//        if (holder.equals("-")||token.equals("-")) {
//            PoolResponse pr = (BuyPoolViewFragment) ((MainActivity) activity).getSupportFragmentManager().findFragmentById(R.id.buy_pool_view);
//            pr.invokeBuyPoolResponse(false,element);
//        }else{
//            QueryObject queryObject = new QueryObject("buypool");
//            PoolQueryService prs = retrofit.create(PoolQueryService.class);
//            prs.getPool(queryObject).enqueue(new Callback<JsonElement>() {
//                @Override
//                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
//                    element = response.body();
//                    PoolResponse pr = (BuyPoolViewFragment) ((MainActivity) activity).getSupportFragmentManager().findFragmentById(R.id.buy_pool_view);
//                    pr.invokeBuyPoolResponse(true,element);
//                }
//
//                @Override
//                public void onFailure(Call<JsonElement> call, Throwable t) {
//                    Toast.makeText(activity,"Connection error. Cannot connect to the server!",Toast.LENGTH_LONG).show();
//                }
//            });
//        }
//    }
//    public void sellPoolRequest(){
//        sharedPref = activity.getSharedPreferences("mypref", activity.MODE_PRIVATE);
//        String holder = sharedPref.getString("holder","-");
//        token = sharedPref.getString("token","-");
//        setClient();
//        if (holder.equals("-")||token.equals("-")) {
//            PoolResponse pr = (SellPoolViewFragment) ((MainActivity) activity).getSupportFragmentManager().findFragmentById(R.id.sell_pool_view);
//            pr.invokeSellPoolResponse(false,element);
//        }else{
//            QueryObject queryObject = new QueryObject("sellpool");
//            PoolQueryService prs = retrofit.create(PoolQueryService.class);
//            prs.getPool(queryObject).enqueue(new Callback<JsonElement>() {
//                @Override
//                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
//                    element = response.body();
//                    PoolResponse pr = (SellPoolViewFragment) ((MainActivity) activity).getSupportFragmentManager().findFragmentById(R.id.sell_pool_view);
//                    pr.invokeSellPoolResponse(true,element);
//                }
//
//                @Override
//                public void onFailure(Call<JsonElement> call, Throwable t) {
//                    Toast.makeText(activity,"Connection error. Cannot connect to the server!",Toast.LENGTH_LONG).show();
//                }
//            });
//        }
//    }
//    public void matchPoolRequest(){
//        sharedPref = activity.getSharedPreferences("mypref", activity.MODE_PRIVATE);
//        String holder = sharedPref.getString("holder","-");
//        token = sharedPref.getString("token","-");
//        setClient();
//        if (holder.equals("-")||token.equals("-")) {
//            PoolResponse pr = (MatchPoolViewFragment) ((MainActivity) activity).getSupportFragmentManager().findFragmentById(R.id.match_pool_view);
//            pr.invokeMatchPoolResponse(false,element);
//        }else{
//            QueryObject queryObject = new QueryObject("matchpool");
//            PoolQueryService prs = retrofit.create(PoolQueryService.class);
//            prs.getPool(queryObject).enqueue(new Callback<JsonElement>() {
//                @Override
//                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
//                    element = response.body();
//                    PoolResponse pr = (MatchPoolViewFragment) ((MainActivity) activity).getSupportFragmentManager().findFragmentById(R.id.match_pool_view);
//                    pr.invokeMatchPoolResponse(true,element);
//                }
//
//                @Override
//                public void onFailure(Call<JsonElement> call, Throwable t) {
//                    Toast.makeText(activity,"Connection error. Cannot connect to the server!",Toast.LENGTH_LONG).show();
//                }
//            });
//        }
//    }
    public void invokeOrderRequest(InvokeBuyObject ibo) {
        sharedPref = activity.getSharedPreferences("mypref", activity.MODE_PRIVATE);
        String holder = sharedPref.getString("holder", "-");
        token = sharedPref.getString("token", "-");
        setClient();
        if (holder.equals("-") || token.equals("-")) {
            InvokeResponseInterface iri = (OrderActivity) activity;
            iri.invokeResponse(false, ir);
        } else {
            OrderInvokeService ois = retrofit.create(OrderInvokeService.class);
            ois.invokeOrder(ibo).enqueue(new Callback<InvokeResponseModel>() {
                @Override
                public void onResponse(Call<InvokeResponseModel> call, Response<InvokeResponseModel> response) {
                    InvokeResponseInterface iri = (OrderActivity) activity;
                    ir = response.body();
                    iri.invokeResponse(true, ir);
                }

                @Override
                public void onFailure(Call<InvokeResponseModel> call, Throwable t) {
                    InvokeResponseInterface iri = (OrderActivity) activity;
                    ((OrderActivity) iri).invokeResponse(false,ir);
                }
            });
        }
    }
}