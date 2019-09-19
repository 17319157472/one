package com.example.cy.cy_qimo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cy.cy_qimo.R;
import com.example.cy.cy_qimo.bean.UserBean;

import java.util.ArrayList;

public class MainRecyAdapter extends RecyclerView.Adapter<MainRecyAdapter.ViewHolder> {
    //重写两个有参构造
    private ArrayList<UserBean> mUserBeans;
    private Context mContext;

    public MainRecyAdapter(ArrayList<UserBean> userBeans, Context context) {
        mUserBeans = userBeans;
        mContext = context;
    }
    //加载布局
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_data_recy, null);
        return new ViewHolder(view);
    }
    //绑定数据
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.mTvName.setText(mUserBeans.get(i).getName());
        viewHolder.mTvPass.setText(mUserBeans.get(i).getPass());
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mOnItemLongClick.onLongClick(i);
                return false;
            }
        });
    }
    //数据条目个数
    @Override
    public int getItemCount() {
        return mUserBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvName;
        private TextView mTvPass;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //加载布局控件
            mTvName = itemView.findViewById(R.id.tvName);
            mTvPass = itemView.findViewById(R.id.tvPass);
        }
    }
    //接口回调
    public interface OnItemLongClick {
        void onLongClick(int position);
    }

    private OnItemLongClick mOnItemLongClick;

    public void setOnItemLongClick(OnItemLongClick onItemLongClick) {
        mOnItemLongClick = onItemLongClick;
    }
}
