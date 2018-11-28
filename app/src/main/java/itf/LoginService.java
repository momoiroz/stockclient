package itf;

import model.LoginObject;

import model.UserLogResponseModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginService {
    @POST("login")
    Call<UserLogResponseModel> register(@Body LoginObject loginObject);
}
