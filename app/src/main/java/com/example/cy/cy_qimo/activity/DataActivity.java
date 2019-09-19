package com.example.cy.cy_qimo.activity;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.cy.cy_qimo.R;
import com.example.cy.cy_qimo.adapter.MainRecyAdapter;
import com.example.cy.cy_qimo.bean.UserBean;
import com.example.cy.cy_qimo.util.DbUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DataActivity extends AppCompatActivity {

    @BindView(R.id.recy)
    RecyclerView mRecy;
    private ArrayList<UserBean> mUserBeans;
    private MainRecyAdapter mMainRecyAdapter;
    private int mPosition;
    private EditText mEtName;
    private EditText mEtPass;
    private Button mBtn_update;
    private Button mBtn_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        ButterKnife.bind(this);
        initRecy();
        initData();
    }

    private void initData() {
        List<UserBean> userBeans = new DbUtil().queryAll();
        String name = userBeans.get(mPosition).getName();
        String pass = userBeans.get(mPosition).getPass();
        Log.i("mtag", "账号: " + name + "密码:" + pass);
        mUserBeans.addAll(userBeans);
        mMainRecyAdapter.notifyDataSetChanged();
    }

    private void initRecy() {
        mUserBeans = new ArrayList<>();
        mMainRecyAdapter = new MainRecyAdapter(mUserBeans, this);
        mRecy.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        mRecy.setLayoutManager(new LinearLayoutManager(this));
        mRecy.setAdapter(mMainRecyAdapter);
        mMainRecyAdapter.setOnItemLongClick(new MainRecyAdapter.OnItemLongClick() {
            @Override
            public void onLongClick(int position) {
                mPosition = position;
            }
        });
        registerForContextMenu(mRecy);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo
            menuInfo) {
        menu.add(1, 1, 1, "删除");
        menu.add(1, 2, 1, "修改");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                delete();
                break;
            case 2:
                update();
                break;

            default:
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void update() {
        //加载修改界面
        View view = LayoutInflater.from(this).inflate(R.layout.item_data_update, null);
        //获取修改两个控件
        mEtName = view.findViewById(R.id.etName);
        mEtPass = view.findViewById(R.id.etPass);
        //两个按钮
        mBtn_update = view.findViewById(R.id.btn_update);
        mBtn_cancel = view.findViewById(R.id.btn_cancel);
        //控件直接获取值
        mEtName.setText(mUserBeans.get(mPosition).getName());
        mEtPass.setText(mUserBeans.get(mPosition).getPass());
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams
                .MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //popowindow修改
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.alpha = 0.5f;
        getWindow().setAttributes(attributes);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams attributes1 = getWindow().getAttributes();
                attributes1.alpha = 1.0f;
                getWindow().setAttributes(attributes1);
                popupWindow.dismiss();
            }
        });
        //修改按钮
        mBtn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //目前两个控件上的值
                String name = mEtName.getText().toString();
                String pass = mEtPass.getText().toString();
                //数据库的对象
                UserBean userBean = mUserBeans.get(mPosition);
                //修改数据库中的值
                userBean.setName(name);
                userBean.setPass(pass);
                //调用DbUtil中的修改方法
                DbUtil.getmDbUtil().update(userBean);
                Toast.makeText(DataActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                //刷新适配器
                mMainRecyAdapter.notifyDataSetChanged();
                popupWindow.dismiss();
            }
        });
        mBtn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popupWindow.showAtLocation(mRecy,Gravity.CENTER,0,0);
    }

    private void delete() {
        UserBean userBean = mUserBeans.get(mPosition);
        boolean delete = DbUtil.getmDbUtil().delete(userBean);
        if (delete) {
            mUserBeans.remove(userBean);
            mMainRecyAdapter.notifyDataSetChanged();
            Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "删除失败", Toast.LENGTH_SHORT).show();
        }
    }
}
