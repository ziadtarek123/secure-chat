package com.example.root.appapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main2Activity extends AppCompatActivity {
    DatabaseReference database_chat;
    DatabaseReference database_noti;

    BigInteger his_e_public;
    BigInteger my_e_public;
    String e_key;

    //ListView listViewusers;
    List<users> list_of_users;
    static String user_name_="username";
    static String user_id_="user_id";
    static String my_id_="my_id";
    static String his_public="his_public";
    static String my_pub="my_pub";
    static String my_key="my_key";
    static String his_key="his_key";


    static String my_name_="my_name";
    static String name_i="name";
    static String id_i="id";
    TabHost th;

    public static String c;
    ////////////////////////////////////
    String us_id="";
    String m_id="";
    String name="";
    ////////////////////////////////////
    String last_noti;
TextView myname;

    String user_id_last_send;
    String user_id_session;
    String user_name_session;
    String seen;
    String notiid;
    String is_reg;
    String key;
    final int bitlength=1024;
    BigInteger my_public;
    Random r;

    int notiid_;
    private FirebaseAuth mAuth;
    DatabaseReference database_get_lastchat;
    DatabaseReference database_lastsend_noti;
    DatabaseReference database_typing;
    DatabaseReference database_typing1;
    DatabaseReference activenow;
    DatabaseReference activenow1;
    DatabaseReference dotactive;

    DatabaseReference keys;
    DatabaseReference keys1;
    DatabaseReference pubs;
    BigInteger p;
    BigInteger q;
    RSA rsa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        p=new BigInteger("147797388819590249059057712024519523621107568759710751200961638453363561991045162718574054785678996590988728517015352136476867755985121774803353852387847962244519800763261516501909651426739002214983618005534400246315766563385831657068376245368119821586106128125462319282716825172010748509843576841104615070893");
        q=new BigInteger("145761376063896308754407875224645176949462052021086771936566066731283361620791088148514652395925160342356787826446970712795012822230949034831985227978196660041850854803579189296904940344385608481787288100453358466372634799647590550422656878690100878789201766710107331691703133201147990014541121795989610005357");
        rsa=new RSA(p,q);


        list_of_users=new ArrayList<>();
        r=new Random();
        th=(TabHost)findViewById(R.id.tabhost);
        th.setup();
        TabHost.TabSpec spec=th.newTabSpec("tag1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Chats");
        th.addTab(spec);

        spec=th.newTabSpec("tag1");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Status");
        th.addTab(spec);

        spec=th.newTabSpec("tag1");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Groups");
        th.addTab(spec);
        //////////////////////change tabhost color///////////////////
        th.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                for (int i=0;i<th.getTabWidget().getChildCount();i++)
                {
                    th.getTabWidget().getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                    TextView tvt=(TextView)th.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
                    tvt.setTextColor(Color.WHITE);
                }
                th.getTabWidget().getChildAt(th.getCurrentTab()).setBackgroundColor(Color.parseColor("#66FFFFFF"));
                TextView tvt=(TextView)th.getCurrentTabView().findViewById(android.R.id.title);
                tvt.setTextColor(Color.WHITE);
            }
        });
        for (int i=0;i<th.getTabWidget().getChildCount();i++)
        {
            th.getTabWidget().getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
            TextView tvt=(TextView)th.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tvt.setTextColor(Color.WHITE);
        }
        th.getTabWidget().getChildAt(th.getCurrentTab()).setBackgroundColor(Color.parseColor("#33FFFFFF"));
        TextView tvt=(TextView)th.getCurrentTabView().findViewById(android.R.id.title);
        tvt.setTextColor(Color.WHITE);
        /////////////////////////////////////////////////////////////
        Intent intent_session=getIntent();
        myname=(TextView)findViewById(R.id.myname);
        user_id_session=null;
        user_name_session=null;
        notiid=null;

        user_id_session=intent_session.getStringExtra(LoginActivity.my_id);
        user_name_session=intent_session.getStringExtra(LoginActivity.my_name);
        notiid=intent_session.getStringExtra(LoginActivity.my_noti);
        is_reg=intent_session.getStringExtra(LoginActivity.r_or_l);
        notiid_=Integer.parseInt(notiid);
        myname.setText(user_name_session);
        database_chat= FirebaseDatabase.getInstance().getReference("users");
        database_noti= FirebaseDatabase.getInstance().getReference();
        database_lastsend_noti = FirebaseDatabase.getInstance().getReference("lastsend").child(user_id_session);


        database_lastsend_noti.child("message").setValue("hi");
        database_lastsend_noti.child("user_id").setValue("hi");
        database_lastsend_noti.child("my_id").setValue("hi");
        database_lastsend_noti.child("sender").setValue("hi");
        database_lastsend_noti.child("seen").setValue("1"); //and all 5 lines above if lastsend node is cleared

///$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$444
        pubs= FirebaseDatabase.getInstance().getReference("pubs");
        pubs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(user_id_session).child("public").exists())
                {

                }
                else
                {
                    my_public=BigInteger.probablePrime(bitlength,r);
                    pubs.child(user_id_session).child("public").setValue(my_public.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public void onDestroy() {
        super.onDestroy();
    }
    @Override
    public void onStop() {
        super.onStop();
        dotactive=FirebaseDatabase.getInstance().getReference("dotactiv").child(user_id_session);
        dotactive.setValue("0");
    }
    protected void onStart() {
        super.onStart();
        c="0";
        ////////////////////////////////////////////////////////////////////////
        dotactive=FirebaseDatabase.getInstance().getReference("dotactiv").child(user_id_session);
        dotactive.setValue("1");
        database_lastsend_noti.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                user_id_last_send=dataSnapshot.child("message").getValue(String.class);
                 us_id=dataSnapshot.child("user_id").getValue(String.class);
                 m_id=dataSnapshot.child("my_id").getValue(String.class);
                name=dataSnapshot.child("sender").getValue(String.class);
                seen=dataSnapshot.child("seen").getValue(String.class);


                if (c==null)
                {

                }else {
                    if (us_id.equals(user_id_session)&&seen.equals("0"))
                    {
                        if (c.equals("1"))
                        {

                        }else
                        {
                            getnotify(user_id_last_send,name,notiid_);
                        }

                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        database_chat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //list_of_users.clear();
                LinearLayout msgs=(LinearLayout)findViewById(R.id.user_layout);
                msgs.removeAllViews();

                for (DataSnapshot snapusers:dataSnapshot.getChildren())
                {

                    final String user_id;
                    final String username;
                    users u=snapusers.getValue(users.class);
                    user_id=u.getId();
                    username=u.getUsername();

                    ////////////////////////////////

                    activenow1 = FirebaseDatabase.getInstance().getReference("active").child(user_id+user_id_session);
                    activenow = FirebaseDatabase.getInstance().getReference("active").child(user_id_session+user_id);

                    database_typing = FirebaseDatabase.getInstance().getReference("typing").child(user_id_session+user_id);
                    database_typing1 = FirebaseDatabase.getInstance().getReference("typing").child(user_id+user_id_session);
                    ///////////////////////
                    if (user_id.equals(user_id_session))
                    {

                    }else {
                        int x=0;
                        final LinearLayout row=new LinearLayout(Main2Activity.this);
                        LinearLayout row_=new LinearLayout(Main2Activity.this);
                        row.setClickable(true);
                        row.setId(x);
                        //
                        fun fun=new fun();
                        key=fun.rand_txt(100);
                        //
                        keys=FirebaseDatabase.getInstance().getReference("keys");
                        keys1=FirebaseDatabase.getInstance().getReference("keys");
                        keys1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(final DataSnapshot dataSnapshot_) {
                                pubs.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(final DataSnapshot dataSnapshot) {
                                        //////**********////////////***********/////////**********///////////******/////
                                        row.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                                his_e_public=new BigInteger(dataSnapshot.child(user_id).child("public").getValue(String.class).toString());
                                                my_e_public=new BigInteger(dataSnapshot.child(user_id_session).child("public").getValue(String.class).toString());
                                                String myky="0";
                                                String hsky="0";
                                                if (dataSnapshot_.child(user_id+user_id_session).exists())
                                                {
                                                    myky=dataSnapshot_.child(user_id+user_id_session).getValue().toString();
                                                    BigInteger mypriv=rsa.getprivate(my_e_public);
                                                    myky=rsa.decrypt(myky,mypriv);
                                                }
                                                else
                                                {
                                                    keys.child(user_id_session+user_id).setValue(rsa.encrypt(key.toString(),his_e_public));
                                                    keys1.child(user_id+user_id_session).setValue(rsa.encrypt(key.toString(),my_e_public));
                                                    myky=key;
                                                }
                                                intent.putExtra(user_name_,username);
                                                intent.putExtra(user_id_,user_id);
                                                intent.putExtra(my_id_,user_id_session);
                                                intent.putExtra(his_public,his_e_public.toString());
                                                intent.putExtra(my_pub,my_e_public.toString());
                                                intent.putExtra(my_name_,user_name_session);
                                                intent.putExtra(my_key,myky);
                                                intent.putExtra(his_key,myky);
                                                c="0";
                                                startActivity(intent);
                                                ///////********///////////**********//////////***********///////////*****///////
                                            }
                                        });
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                        TextView tv=new TextView(Main2Activity.this);
                        final TextView tv_last_message=new TextView(Main2Activity.this);
                        final TextView typing=new TextView(Main2Activity.this);
                        final TextView dotact=new TextView(Main2Activity.this);

                        ImageView iv=new ImageView(Main2Activity.this);

                        LinearLayout.LayoutParams lp_row=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        LinearLayout.LayoutParams lp1=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        LinearLayout.LayoutParams lp2=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        LinearLayout.LayoutParams lp_last__msg=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        lp1.setMargins(20, 20, 20, 20);
                        row.setLayoutParams(lp_row);
                        row_.setLayoutParams(lp_last__msg);
                        row_.setOrientation(LinearLayout.VERTICAL);
                        tv.setPadding(15,5,15,5);
                        tv_last_message.setPadding(15,5,15,5);
                        typing.setPadding(15,5,15,5);
                        row.setBackgroundResource(R.drawable.users);
                        lp.gravity=Gravity.LEFT;
                        tv.setLayoutParams(lp);
                        tv.setTextColor(Color.parseColor("#FF6104B2"));
                        tv.setHeight(50);
                        //tv.setBackgroundResource(R.drawable.sender_message_style);
                        tv.setTextSize(15);
                        tv.setText(u.getUsername());
                        dotact.setLayoutParams(lp2);
                        lp2.height=15;
                        lp2.width=15;

                        lp2.setMargins(10 ,10 ,10 ,10);

                        lp1.width=100;
                        lp1.height=100;

                        //lp1.gravity=Gravity.LEFT;
                        iv.setLayoutParams(lp1);
                        iv.setBackgroundResource(R.drawable.bgpreson);
                        iv.setImageResource(R.drawable.person);
                        ///////////////////////////////////////////////////////************
                        if (dataSnapshot.child(user_id).hasChild("img_uri"))
                        {
                            String img_uri=dataSnapshot.child(user_id).child("img_uri").getValue(String.class);
                            if (img_uri.equals("user_img_default"))
                            {
                                iv.setImageResource(R.drawable.person);
                            }
                            else
                            {
                                Uri uri=Uri.parse(img_uri);
                                Picasso.with(Main2Activity.this).load(uri).fit().centerCrop().into(iv);
                            }
                        }

                        ///////////////////////////////////////////////////////************
/////////////////////////////////////////////////////////////////

                            database_get_lastchat= FirebaseDatabase.getInstance().getReference("lastmsgs");
                            database_get_lastchat.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot c:dataSnapshot.getChildren())
                                    {
                                        if (c.child("lm__id").getValue(String.class).equals(user_id_session+user_id)||c.child("lm__id").getValue(String.class).equals(user_id+user_id_session))
                                        {
                                            tv_last_message.setText(c.child("lastmsg").getValue(String.class));
                                        }

                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }

                            });

////////////////////////////////////////////////////////////////////////////////////////

                        activenow1 = FirebaseDatabase.getInstance().getReference("active").child(user_id+user_id_session);

                        activenow1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.hasChild("myactive"))
                                {
                                    if (dataSnapshot.child("myactive").getValue(String.class).equals("1"))
                                    {
                                        typing.setText("online");
                                    }
                                    else if(dataSnapshot.child("myactive").getValue(String.class).equals("0")){
                                        typing.setText("");
                                    }
                                }

                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
////////////////////////////////////////////////////////////////////////////////////////
                        dotactive=FirebaseDatabase.getInstance().getReference("dotactiv").child(user_id);
                        dotactive.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.getValue(String.class).equals("1"))
                                {
                                    dotact.setBackgroundColor(Color.GREEN);
                                    dotact.setTextColor(Color.GREEN);
                                    dotact.setBackgroundResource(R.drawable.active);
                                }else if (dataSnapshot.getValue(String.class).equals("0"))
                                {
                                    dotact.setBackgroundColor(Color.GRAY);
                                    dotact.setTextColor(Color.GRAY);
                                    dotact.setBackgroundResource(R.drawable.deactive);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
////////////////////////////////////////////////////////////////////////////
                        database_typing = FirebaseDatabase.getInstance().getReference("typing").child(user_id_session+user_id);
                        database_typing.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                android.support.v7.app.ActionBar ab = getSupportActionBar();
                                if (dataSnapshot.hasChild("isnowtyping"))
                                {
                                    if(dataSnapshot.child("isnowtyping").getValue(String.class).equals("1")&&!dataSnapshot.child("my_id").getValue(String.class).equals(user_id_session))
                                    {
                                        typing.setText(dataSnapshot.child("text").getValue(String.class));
                                    }else
                                    {
                                        ////////////////////////////////////////////////////////////////
                                        activenow1 = FirebaseDatabase.getInstance().getReference("active").child(user_id+user_id_session);
                                        activenow1.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                if (dataSnapshot.child("myactive").getValue(String.class).equals("1"))
                                                {
                                                    typing.setText("online");
                                                }
                                                else if(dataSnapshot.child("myactive").getValue(String.class).equals("0")){
                                                    typing.setText("");
                                                }
                                            }
                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });
                                        ///////////////////////////////////////////////////////////////
                                    }
                                }


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        row_.addView(tv);
                        row_.addView(tv_last_message);
                        row_.addView(typing);
                        row_.addView(dotact);
                        row.addView(iv);
                        row.addView(row_);

                        msgs.addView(row);
                        x++;
                    }


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if (id==R.id.logout)
        {
            mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();
            Intent myintent=new Intent(this,LoginActivity.class);
            startActivity(myintent);
            finish();
        }else if (id==R.id.myprofile)
        {
            Intent myintent=new Intent(this,profile.class);
            myintent.putExtra(name_i,user_name_session);
            myintent.putExtra(id_i,user_id_session);
            startActivity(myintent);
        }
        return super.onOptionsItemSelected(item);
    }
    NotificationManager noti;

    public void getnotify(String msg,String name,int id) {
        //noti.cancel(id);
        NotificationCompat.Builder nbuilder = new NotificationCompat.Builder(this)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentTitle(name)
                .setContentText(msg)
                .setSmallIcon(R.drawable.person);
        noti = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        noti.notify(id, nbuilder.build());
        DatabaseReference dbr= FirebaseDatabase.getInstance().getReference("lastsend").child(user_id_session);
        dbr.child("seen").setValue("1");
    }
}
