package com.example.org1.stockex;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import itf.LoginResponse;
import model.UserLogResponseModel;

public class MainActivity extends AppCompatActivity implements LoginResponse {
    LoginFragment lf;
    AppFragment af;
    UserLogResponseModel ulr;
    LoginResponse lr = this;
    SharedPreferences sharedPref;
    boolean login = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPref = this.getSharedPreferences("mypref", MODE_PRIVATE);
        lf = new LoginFragment();
        af = new AppFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, lf).commit();
    }
    @Override
    public void invokeLoginResponse(UserLogResponseModel ulr) {
        this.ulr = ulr;
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("token", ulr.getToken());
        editor.commit();
        if (!ulr.getSuccess()) {
            Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Login successfully", Toast.LENGTH_SHORT).show();
            af = new AppFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,af,"app_frag").commit();
            login=true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (login) {
            switch (item.getItemId()) {
                case R.id.logout:
                    logout();
                    login=false;
                    break;
                default:break;
            }
        }else {
            Toast.makeText(this, "You've already logged out.", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void logout(){
        af.onDetach();
        af.onDestroy();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,lf,"login").commit();
    }
}


