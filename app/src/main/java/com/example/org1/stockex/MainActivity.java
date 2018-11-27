package com.example.org1.stockex;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import itf.LoginResponse;
import model.UserLogResponse;

public class MainActivity extends AppCompatActivity implements LoginResponse {
    LoginFragment lf;
    UserLogResponse ulr;
    LoginResponse lr = this;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPref = this.getSharedPreferences("mypref", MODE_PRIVATE);
        lf = new LoginFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, lf).commit();
    }
    @Override
    public void invokeResponse(UserLogResponse ulr) {
        this.ulr = ulr;
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("token", ulr.getToken());
        editor.commit();
        if (!ulr.getSuccess()) {
            Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Login successfully", Toast.LENGTH_SHORT).show();
        }
    }
}


