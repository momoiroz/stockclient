package com.example.org1.stockex;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import control.ApiRequest;

public class LoginFragment extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    EditText mEdtAcc,mEdtPwd;
    Button mBtnLogin;
    ApiRequest api;
    SharedPreferences sharedPref;

    public LoginFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v;
        v = inflater.inflate(R.layout.fragment_login, container, false);
        api = new ApiRequest(getActivity());
        mEdtAcc = v.findViewById(R.id.edt_account);
        sharedPref = getActivity().getSharedPreferences("mypref", getActivity().MODE_PRIVATE);
        String holder = sharedPref.getString("holder","-");
        if (!holder.equals("-")) {
            mEdtAcc.setText(holder);
        }
        mEdtPwd = v.findViewById(R.id.edt_password);
        mEdtPwd.setText("123456");
        mBtnLogin = v.findViewById(R.id.btn_Login);
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mEdtAcc.getText().toString();
                String password = mEdtPwd.getText().toString();
                api.loginRequest(username,password);
            }
        });
        return v;
    }
}
