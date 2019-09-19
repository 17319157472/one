package com.example.cy.cy_qimo.model;

import com.example.cy.cy_qimo.bean.UserBean;
import com.example.cy.cy_qimo.callback.MainCallBack;
import com.example.cy.cy_qimo.util.DbUtil;

public class MainModel {
    public void insert(final UserBean userBean, final MainCallBack mainCallBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //添加数据
                long insert = DbUtil.getmDbUtil().insert(userBean);
                if (insert >= 0) {
                    //成功走这个方法
                    mainCallBack.onSuccess("添加成功");
                } else {
                    //失败走这个方法
                    mainCallBack.onFail("添加失败");
                }
            }
        }).start();
    }
}
