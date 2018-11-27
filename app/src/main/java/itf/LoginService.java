package itf;

import model.LoginObject;

import model.UserLogResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginService {
    @POST("login")
    Call<UserLogResponse> register(@Body LoginObject loginObject);
}
