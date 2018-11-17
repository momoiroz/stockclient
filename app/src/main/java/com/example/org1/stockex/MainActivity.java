package com.example.org1.stockex;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    Button mBtnRegister;
    EditText mEdtHolderName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnRegister = findViewById(R.id.btn_register);
        mEdtHolderName = findViewById(R.id.edt_holder_name);
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mEdtHolderName.getText().toString();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://wibuchashu.ddns.net:4000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RegisterObject ro = new RegisterObject(name,"Org1");
                RegisterService rs = retrofit.create(RegisterService.class);
                rs.register(ro).enqueue(new Callback<UserRegRespond>() {
                    @Override
                    public void onResponse(Call<UserRegRespond> call, Response<UserRegRespond> response) {
                        Log.e("token", response.body().getToken());
                        mEdtHolderName.setText(response.body().getToken());
                    }

                    @Override
                    public void onFailure(Call<UserRegRespond> call, Throwable t) {

                    }
                });
            }
        });
    }
}
