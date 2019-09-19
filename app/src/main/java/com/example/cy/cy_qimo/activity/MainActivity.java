package com.example.cy.cy_qimo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cy.cy_qimo.R;
import com.example.cy.cy_qimo.adapter.MainRecyAdapter;
import com.example.cy.cy_qimo.bean.UserBean;
import com.example.cy.cy_qimo.util.DbUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.etName)
    EditText mEtName;
    @BindView(R.id.etPwd)
    EditText mEtPwd;
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    @BindView(R.id.btn_register)
    Button mBtnRegister;
    private ArrayList<UserBean> mUserBeans;
    private MainRecyAdapter mMainRecyAdapter;
    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initAdapter();
    }


    private void initAdapter() {
        //获取mUserBeans集合
        mUserBeans = new ArrayList<>();
        //创建适配器
        mMainRecyAdapter = new MainRecyAdapter(mUserBeans, this);
        //接口回调传值
        mMainRecyAdapter.setOnItemLongClick(new MainRecyAdapter.OnItemLongClick() {
            @Override
            public void onLongClick(int position) {
                mPosition = position;
            }
        });
    }

    @OnClick({R.id.btn_login, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                btnLogin();
                break;
            case R.id.btn_register:
                btnRegister();
                break;
        }
    }

    private void btnLogin() {
        //登录注册控件的值
        String name = mEtName.getText().toString();
        String pwd = mEtPwd.getText().toString();
        //调用DbUtil查询数据库中的方法
        List<UserBean> userBeans = DbUtil.getmDbUtil().queryAll();
        UserBean userBean = userBeans.get(mPosition);
        //查询数据库中登录注册的值
        String loginName = userBean.getName();
        String loginPass = userBean.getPass();
        //符合要求走成功方法
        if (name.equals(loginName) && pwd.equals(loginPass)) {
            startActivity(new Intent(MainActivity.this, DataActivity.class));
            Toast.makeText(this, "登陆成功", Toast.LENGTH_SHORT).show();
        } else {
            //不符合题目要求走失败方法
            Toast.makeText(this, "登陆失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void btnRegister() {
        //去注册页面获取值
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 100) {
            String name = data.getStringExtra("name");
            mEtName.setText(name);
        }
    }
}
