package com.example.root.appapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by root on 2/6/18.
 */

public class list_users extends ArrayAdapter<users> {
    private Activity context;
    private List<users> userslist;

    public list_users(Activity context,List<users> users_list) {
        super(context,R.layout.users_layout,users_list);
        this.context=context;
        this.userslist=users_list;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater=context.getLayoutInflater();


        View list_view_item=inflater.inflate(R.layout.users_layout,null,true);
        TextView textView_message=(TextView)list_view_item.findViewById(R.id.username);
        final TextView last_message=(TextView)list_view_item.findViewById(R.id.lastmessage);

        ImageView iv=(ImageView)list_view_item.findViewById(R.id.image_user);
        users users=userslist.get(position);
        textView_message.setText(users.getUsername());
        last_message.setText(users.getLast_msg());
        iv.setImageResource(R.drawable.person);
        return list_view_item;
    }
}
