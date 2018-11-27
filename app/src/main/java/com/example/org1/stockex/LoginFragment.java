package com.example.org1.stockex;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import control.ApiRequest;
import itf.LoginResponse;
import model.UserLogResponse;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    EditText mEdtAcc,mEdtPwd;
    Button mBtnLogin;
    ApiRequest api;
    UserLogResponse ulr;

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
        mEdtPwd = v.findViewById(R.id.edt_password);
        mBtnLogin = v.findViewById(R.id.btn_Login);
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mEdtAcc.getText().toString();
                String password = mEdtPwd.getText().toString();
                api.loginRequest(username,password);
//                try {
//                    UserLogResponse rp = api.loginRequest(username,password);
//                    if(!rp.getSuccess()) {
//                        Toast.makeText(getActivity().getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
//                    } else{
//                        Toast.makeText(getActivity().getApplicationContext(), "Login Successfully", Toast.LENGTH_SHORT).show();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

            }
        });
        return v;
    }
}
