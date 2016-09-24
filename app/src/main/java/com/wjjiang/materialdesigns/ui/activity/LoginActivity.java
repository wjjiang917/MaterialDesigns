package com.wjjiang.materialdesigns.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wjjiang.materialdesigns.R;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.ed_login_email)
    EditText edLoginEmail;
    @BindView(R.id.ed_login_password)
    EditText edLoginPassword;
//    @BindView(R.id.ll_btn_login)
//    Button llBtnLogin;
    @BindView(R.id.txt_forgot_pass)
    TextView txtForgotPass;
    @BindView(R.id.fab_register_profile)
    FloatingActionButton fabRegisterProfile;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick({R.id.fab_register_profile, R.id.ll_btn_login, R.id.txt_forgot_pass})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.fab_register_profile:
                Snackbar.make(view, "register", Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.ll_btn_login:
                Snackbar.make(view, "login", Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.txt_forgot_pass:
                Snackbar.make(view, "forgot pwd", Snackbar.LENGTH_SHORT).show();
                break;
        }
    }
}
