package com.example.org1.stockex;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RegisterService {
    @POST("users")
    Call<UserRegRespond> register(@Body RegisterObject registerObject);
}
