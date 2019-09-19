package com.example.cy.cy_qimo.presenter;

import com.example.cy.cy_qimo.bean.UserBean;
import com.example.cy.cy_qimo.callback.MainCallBack;
import com.example.cy.cy_qimo.model.MainModel;
import com.example.cy.cy_qimo.view.MainView;

public class MainPresenter implements MainCallBack {
    private MainView mMainView;
    private MainModel mMainModel;
    //重写presenter
    public MainPresenter(MainView mainView) {
        mMainView = mainView;
        mMainModel=new MainModel();
    }
    //添加数据的方法
    public void insert(UserBean userBean){
        if (mMainModel != null) {
            mMainModel.insert(userBean,this);
        }
    }
    //成功走这个方法
    @Override
    public void onSuccess(String success) {
        if (mMainView != null) {
            mMainView.onSuccess(success);
        }
    }
    //失败走这个方法
    @Override
    public void onFail(String error) {
        if (mMainView != null) {
            mMainView.onFail(error);
        }
    }
}
