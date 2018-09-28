package com.example.deepakrattan.apirequestdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.deepakrattan.apirequestdemo.R;
import com.example.deepakrattan.apirequestdemo.beans.User_Bean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by deepak.rattan on 1/11/2017.
 */

public class UserAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<User_Bean> user_beanArrayList;
    private LayoutInflater inflater;

    public UserAdapter(Context context, ArrayList<User_Bean> user_beanArrayList) {
        this.context = context;
        this.user_beanArrayList = user_beanArrayList;
    }

    @Override
    public int getCount() {
        return user_beanArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return user_beanArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.layout_user, viewGroup, false);
        TextView txtName = (TextView) view.findViewById(R.id.txtName);
        TextView txtAddress = (TextView) view.findViewById(R.id.txtAddress);
        TextView txtPhone = (TextView) view.findViewById(R.id.txtPhone);
        ImageView iv = (ImageView) view.findViewById(R.id.imageUser);

        User_Bean user_bean = user_beanArrayList.get(i);

        txtName.setText(user_bean.getName());
        txtAddress.setText(user_bean.getAddress());
        txtPhone.setText(user_bean.getPhone());
        Picasso.with(context).load(user_bean.getImageUrl()).error(R.drawable.default_image).into(iv);

        return view;
    }
}
