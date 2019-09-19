package com.example.cy.cy_qimo.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cy.cy_qimo.R;
import com.example.cy.cy_qimo.bean.UserBean;
import com.example.cy.cy_qimo.presenter.MainPresenter;
import com.example.cy.cy_qimo.view.MainView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity implements MainView {

    @BindView(R.id.etName)
    EditText mEtName;
    @BindView(R.id.etPwd)
    EditText mEtPwd;
    @BindView(R.id.etRePwd)
    EditText mEtRePwd;
    @BindView(R.id.iv)
    ImageView mIv;
    @BindView(R.id.btn_register)
    Button mBtnRegister;
    private MainPresenter mMainPresenter;
    private long id = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mMainPresenter = new MainPresenter(this);
        ButterKnife.bind(this);
    }


    private void btnRerister() {
        //获取控件EditText的值
        String name = mEtName.getText().toString();
        String pass = mEtPwd.getText().toString();
        String rePass = mEtRePwd.getText().toString();
        //符合要求去登录页面
        if (name != null && pass.equals(rePass) && pass != null && mIv != null && name.length() >
                0) {
            mMainPresenter.insert(new UserBean(id++, name, pass, null));
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            intent.putExtra("name", name);
            setResult(100, intent);
            finish();
        } else {
            Toast.makeText(this, "账号密码不符合要求", Toast.LENGTH_SHORT).show();
        }
    }

    //成功方法
    @Override
    public void onSuccess(final String success) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(RegisterActivity.this, success, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //失败方法
    @Override
    public void onFail(final String error) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(RegisterActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick({R.id.iv, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv:
                btnIv();
                break;
            case R.id.btn_register:
                btnRerister();
                break;
        }
    }

    //头像
    private void btnIv() {
        mIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 20);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 20) {
            Uri uri = data.getData();
            mIv.setImageURI(uri);
        }
    }
}
