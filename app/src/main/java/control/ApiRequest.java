package control;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.util.Log;
import android.widget.Toast;

import com.example.org1.stockex.LoginFragment;
import com.example.org1.stockex.MainActivity;
import com.example.org1.stockex.R;

import java.io.IOException;

import itf.LoginResponse;
import itf.LoginService;
import model.LoginObject;
import model.UserLogResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRequest {
    UserLogResponse ulr;
    private Activity activity;
    public ApiRequest() {
    }

    public ApiRequest(Activity activity) {
        this.activity = activity;
    }

    public void loginRequest(String username, String password)  {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://wibuchashu.ddns.net:4000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                LoginObject ro = new LoginObject(username,"Org1",password);
                LoginService rs = retrofit.create(LoginService.class);
                rs.register(ro).enqueue(new Callback<UserLogResponse>() {
                    @Override
                    public void onResponse(Call<UserLogResponse> call, Response<UserLogResponse> response) {
                        ulr = new UserLogResponse(response.body().getSuccess(),response.body().getMessage(),response.body().getToken());
                        LoginResponse lr = (MainActivity) activity;
                        lr.invokeResponse(ulr);
                }

                    @Override
                    public void onFailure(Call<UserLogResponse> call, Throwable t) {
                    }
                });
    }
}
