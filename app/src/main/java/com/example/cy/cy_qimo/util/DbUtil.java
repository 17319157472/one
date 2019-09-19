package com.example.cy.cy_qimo.util;

import com.example.cy.cy_qimo.app.MainApplication;
import com.example.cy.cy_qimo.bean.UserBean;
import com.example.cy.cy_qimo.db.DaoMaster;
import com.example.cy.cy_qimo.db.DaoSession;
import com.example.cy.cy_qimo.db.UserBeanDao;
import java.util.List;

public class DbUtil {
    public static DbUtil mDbUtil;
    private final UserBeanDao mUserBeanDao;

    public DbUtil() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(MainApplication
                .getApp(), "user.db");
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        DaoSession daoSession = daoMaster.newSession();
        mUserBeanDao = daoSession.getUserBeanDao();
    }

    //单例
    public static DbUtil getmDbUtil() {
        if (mDbUtil == null) {
            synchronized (DbUtil.class) {
                if (mDbUtil == null) {
                    mDbUtil = new DbUtil();
                }
            }
        }
        return mDbUtil;
    }
    //添加数据的方法
    public long insert(UserBean userBean) {
        if (!isInserted(userBean)) {
            long l = mUserBeanDao.insertOrReplace(userBean);
            return l;
        }
        return -1;
    }

    private boolean isInserted(UserBean userBean) {
        List<UserBean> list = mUserBeanDao.queryBuilder().where(UserBeanDao.Properties.Name.eq
                (userBean.getName())).list();
        if (list.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
    //删除数据的方法
    public boolean delete(UserBean userBean) {
        if (isInserted(userBean)) {
            mUserBeanDao.delete(userBean);
            return true;
        }
        return false;
    }
    //查询数据的方法
    public List<UserBean> queryAll(){
        List<UserBean> list = mUserBeanDao.queryBuilder().list();
        return list;
    }
    public void update(UserBean userBean){
        mUserBeanDao.update(userBean);
    }
}
