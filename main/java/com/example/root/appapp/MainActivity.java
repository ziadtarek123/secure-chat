package com.example.root.appapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.DrawableRes;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.SizeF;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.math.BigInteger;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    BigInteger d_private;
    String bi="1";
    final int bitlength=1024;
    private static final int gallary=2;
    TextView message;
    TextView test_;
    TextView name;
    static String isonline="0";
    static String my_id="user_id";
    static String my_name="my__name";
    static String my_noti="my_noti";
    ScrollView sv;
    Button send;
    Button btnimg;
    DatabaseReference database_chat;DatabaseReference database_chat1;
    DatabaseReference database_lastchat;
    DatabaseReference database_noti;
    DatabaseReference database_lastsend_noti;
    DatabaseReference database_lastsend_noti2;
    DatabaseReference database_typing;
    DatabaseReference database_typing1;
    DatabaseReference activenow;
    DatabaseReference activenow1;
    DatabaseReference dotactive;
    DatabaseReference cirtificate;

    private StorageReference mStorage;
    ListView listView;
    List<chat> chatListchat;
    Button selectimg;
    String username_in;
    String userId_in;
    String myId_in;
    String my_name_;
    String typemsg="text";
    String key;
    Uri uri;
    TextView k;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //p=BigInteger.probablePrime(bitlength,r);
        //q=BigInteger.probablePrime(bitlength,r);


        ////////////////***********/////////////*************////////////*********////////

        cirtificate=FirebaseDatabase.getInstance().getReference("cirtificate").child(userId_in+myId_in);


        //cirtificate.child("public").setValue(e_public.toString());
        ////////////////***********/////////////*************////////////*********////////

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==gallary&&resultCode==RESULT_OK)
        {
            uri=data.getData();
            /*
            Picasso.with(MainActivity.this).load(uri).fit().centerCrop().into(ivsend);
            */
            //btnimg.setBackgroundResource(R.drawable.person);
            typemsg="img";
        }
    }






















    private void addMessage(final String type) {
        sv.fullScroll(View.FOCUS_DOWN);
        RC4 rc4=new RC4();
        final String message_text =rc4.encryption(message.getText().toString().trim(),key) ;

            final String id = database_chat.push().getKey();
            Random random=new Random();
            final int n_id=random.nextInt(100000);
            final String now=java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
            if (type.equals("text"))
            {
                if (!TextUtils.isEmpty(message_text)) {
                    chat chat = new chat(message_text, id, myId_in, userId_in,type,now,key);
                    lastmsg lm = new lastmsg(message_text, myId_in + userId_in, "0");

                    database_chat.child(id).setValue(chat);////**********#########
                    database_lastchat.child(myId_in + userId_in).setValue(lm);
                    database_lastchat.child(userId_in + myId_in).setValue(lm);

                    ////////////////////////////////////////////user
                    database_chat1.child(id).setValue(chat);///**************########
                    //////////////////////////////////////////

                    database_lastsend_noti.child("message").setValue(message_text);
                    database_lastsend_noti.child("msg_id").setValue(id);
                    database_lastsend_noti.child("my_id").setValue(myId_in);
                    database_lastsend_noti.child("user_id").setValue(userId_in);
                    database_lastsend_noti.child("sender").setValue(my_name_);
                    database_lastsend_noti.child("notiid").setValue(n_id);
                    database_lastsend_noti.child("seen").setValue("0");
                    /////////////////////////////////////////////////
                    database_lastsend_noti2.child("message").setValue(message_text);
                    database_lastsend_noti2.child("msg_id").setValue(id);
                    database_lastsend_noti2.child("my_id").setValue(myId_in);
                    database_lastsend_noti2.child("user_id").setValue(userId_in);
                    database_lastsend_noti2.child("sender").setValue(my_name_);
                    database_lastsend_noti2.child("notiid").setValue(n_id);
                    database_lastsend_noti2.child("seen").setValue("0");
                    //////////////////////////////////////////////////
                    message.setText("");
                    Toast.makeText(this, "Message delevered", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Type message first", Toast.LENGTH_LONG).show();
                }
            }

































            else if(typemsg.equals("img"))
            {

                ////////////////////////////////////////////////////////////////
                StorageReference filepath=mStorage.child("send_imgs").child(id);
                filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadimg=taskSnapshot.getDownloadUrl();
                        String uris=downloadimg.toString();

                        chat chat = new chat(message_text, id, myId_in, userId_in,uris,now,key);
                        lastmsg lm = new lastmsg(message_text, myId_in + userId_in, "0");

                        database_chat.child(id).setValue(chat);
                        database_lastchat.child(myId_in + userId_in).setValue(lm);
                        database_lastchat.child(userId_in + myId_in).setValue(lm);

                        ////////////////////////////////////////////user
                        database_chat1.child(id).setValue(chat);
                        //////////////////////////////////////////

                        database_lastsend_noti.child("message").setValue(message_text);
                        database_lastsend_noti.child("msg_id").setValue(id);
                        database_lastsend_noti.child("my_id").setValue(myId_in);
                        database_lastsend_noti.child("user_id").setValue(userId_in);
                        database_lastsend_noti.child("sender").setValue(my_name_);
                        database_lastsend_noti.child("notiid").setValue(n_id);
                        database_lastsend_noti.child("seen").setValue("0");
                        /////////////////////////////////////////////////
                        database_lastsend_noti2.child("message").setValue(message_text);
                        database_lastsend_noti2.child("msg_id").setValue(id);
                        database_lastsend_noti2.child("my_id").setValue(myId_in);
                        database_lastsend_noti2.child("user_id").setValue(userId_in);
                        database_lastsend_noti2.child("sender").setValue(my_name_);
                        database_lastsend_noti2.child("notiid").setValue(n_id);
                        database_lastsend_noti2.child("seen").setValue("0");
                        //////////////////////////////////////////////////
                        message.setText("");
                        typemsg="text";
                        Toast.makeText(MainActivity.this, "Message sent", Toast.LENGTH_LONG).show();
                    }
                });
            }




        sv.fullScroll(View.FOCUS_DOWN);
    }

    @Override
    protected void onStart() {
        super.onStart();
        dotactive=FirebaseDatabase.getInstance().getReference("dotactiv").child(myId_in);
        dotactive.setValue("0");
        sv.fullScroll(View.FOCUS_DOWN);

        ////////////////////////////////////////////
        activenow1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                android.support.v7.app.ActionBar ab = getSupportActionBar();

                if (dataSnapshot.hasChild("myactive"))
                {
                    isonline=dataSnapshot.child("myactive").getValue(String.class);
                    if (isonline.equals("1"))
                    {
                        ab.setSubtitle("online"+isonline);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        activenow.child("myactive").setValue("1");

        database_chat.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                LinearLayout msgs = (LinearLayout) findViewById(R.id.my_msgs);
                msgs.removeAllViews();
                for (DataSnapshot chatsnap : dataSnapshot.getChildren()) {
                    chat ch = chatsnap.getValue(chat.class);
                    TextView tv = new TextView(MainActivity.this);
                    TextView tvdate = new TextView(MainActivity.this);
                    tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(20, 10, 20, 10);
                    if (ch.getType().equals("text"))
                    {

                        if (ch.getMy_id().equals(myId_in)) {
                            lp.gravity = Gravity.RIGHT;
                            tv.setTextColor(Color.BLACK);
                            tv.setLayoutParams(lp);
                            tvdate.setLayoutParams(lp);
                            tv.setBackgroundResource(R.drawable.message_style);
                            RC4 rc4=new RC4();
                            tv.setText(rc4.encryption(ch.getMessage(),key));
                        } else {
                            lp.gravity = Gravity.LEFT;
                            tv.setLayoutParams(lp);
                            tv.setTextColor(Color.WHITE);
                            tvdate.setLayoutParams(lp);
                            tv.setBackgroundResource(R.drawable.sender_message_style);
                            RC4 rc4=new RC4();
                            tv.setText(rc4.encryption(ch.getMessage(),key));
                        }
                        tvdate.setText(ch.getDatetime());
                        tv.setTextSize(15);
                        tv.setPadding(15, 15, 15, 15);
                        tv.setGravity(Gravity.RIGHT);
                        tvdate.setTextSize(10);
                        msgs.addView(tv);
                        msgs.addView(tvdate);
                    }
                    else
                    {
                        ImageView ivsent=new ImageView(MainActivity.this);
                        Uri ur=Uri.parse(ch.getType());
                        Picasso.with(MainActivity.this).load(ur).fit().centerCrop().into(ivsent);
                        ivsent.setImageResource(R.drawable.person);
                        ivsent.setLayoutParams(lp);
                        if (ch.getMy_id().equals(myId_in)) {
                            lp.gravity = Gravity.RIGHT;
                            tv.setTextColor(Color.BLACK);
                            tv.setLayoutParams(lp);
                            tvdate.setLayoutParams(lp);
                            tv.setBackgroundResource(R.drawable.message_style);
                        } else {
                            lp.gravity = Gravity.LEFT;
                            tv.setLayoutParams(lp);
                            tv.setTextColor(Color.WHITE);
                            tvdate.setLayoutParams(lp);
                            tv.setBackgroundResource(R.drawable.sender_message_style);
                        }
                        tvdate.setText(ch.getDatetime());
                        tv.setTextSize(15);
                        tv.setPadding(15, 15, 15, 15);
                        tv.setGravity(Gravity.RIGHT);
                        RC4 rc4=new RC4();
                        tv.setText(rc4.encryption(ch.getMessage(),ch.getK()));
                        tvdate.setTextSize(10);
                        msgs.addView(ivsent);
                        if (tv.getText().equals(""))
                        {

                        }
                        else
                        {
                            msgs.addView(tv);
                        }
                        msgs.addView(tvdate);
                    }

                    sv.fullScroll(View.FOCUS_DOWN);
                    }
                sv.post(new Runnable() {
                    @Override
                    public void run() {
                        sv.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });

            }



            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        sv.fullScroll(View.FOCUS_DOWN);
        message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                database_typing.child("text").setValue("Typing now...");
                database_typing.child("my_id").setValue(myId_in);
                database_typing.child("isnowtyping").setValue("1");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        database_typing.child("isnowtyping").setValue("0");
                    }
                }, 3000);
            }
        });

        /////////////////////////////////////////
        database_typing1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                android.support.v7.app.ActionBar ab = getSupportActionBar();
                if (dataSnapshot.hasChild("isnowtyping"))
                {
                    if(dataSnapshot.child("isnowtyping").getValue(String.class).equals("1")&&!dataSnapshot.child("my_id").getValue(String.class).equals(myId_in))
                    {
                        ab.setSubtitle(dataSnapshot.child("text").getValue(String.class));
                    }else
                    {
                        ////////////////////////////////////////////
                        activenow1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                android.support.v7.app.ActionBar ab = getSupportActionBar();
                                isonline=dataSnapshot.child("myactive").getValue(String.class);
                                if (isonline.equals("1"))
                                {
                                    ab.setSubtitle("online"+isonline);
                                }
                                else
                                {
                                    ab.setSubtitle("");
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
    public void onStop() {
        super.onStop();
        activenow.child("myactive").setValue("0");
    }
}
