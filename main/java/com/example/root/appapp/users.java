package com.example.root.appapp;

import android.app.Activity;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 1/30/18.
 */

public class users {
    private String username;
    private String email;
    private String password;
    private String id;
    private String last_msg;
    private String notid;
    DatabaseReference database_get_lastchat;
    public users()
    {
        database_get_lastchat= FirebaseDatabase.getInstance().getReference("lastmsgs");
    }

    public users(String username, String password, String id,String email,String notid) {
        this.username = username;
        this.password = password;
        this.id = id;
        this.email=email;
        this.notid=notid;
    }
    public String getEmail() {
        return email;
    }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNotid() {
        return notid;
    }

    public String getId() {
        return id;
    }

    public String getLast_msg() {
        return last_msg;
    }
    /////////////////////////////////////////////////////////////////
/*
public void getlastmsg(String userid, String my_id, Activity activity)
{

    DatabaseReference ref=database_get_lastchat.child(userid);
    final TextView lm=activity.findViewById(R.id.test_last);
    database_get_lastchat.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot c:dataSnapshot.getChildren())
            {
                last_msg=c.child("lastmsg").getValue(String.class);
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }

    });
}
*/
    /////////////////////////////////////////////////////////////////
}
