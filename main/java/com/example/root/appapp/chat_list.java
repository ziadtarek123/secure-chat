package com.example.root.appapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by root on 1/27/18.
 */

public class chat_list extends ArrayAdapter<chat>{
    private Activity context;
    private List<chat> chatList;
    public chat_list(Activity context,List<chat>chatList)
    {
        super(context,R.layout.list_layout,chatList);
        this.context=context;
        this.chatList=chatList;
    }

    @NonNull
    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();

        View list_view_item=inflater.inflate(R.layout.list_layout,null,true);
        TextView textView_message=(TextView)list_view_item.findViewById(R.id.message1);

        chat ch1=chatList.get(position);
        textView_message.setText(ch1.getMessage());
        return list_view_item;
    }
}
