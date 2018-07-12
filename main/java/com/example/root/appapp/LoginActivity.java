package com.example.root.appapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    //log
    static String my_id="user_id";
    static String my_name="my__name";
    static String my_noti="my_noti";
    static String r_or_l="r_or_l";
    private TextView email_;
    private EditText password_;
    Button log_btn;
    //reg
    private TextView email_reg;
    private EditText password_reg;
    private EditText re_password_reg;
    private EditText username__reg;
    Button register_btn;
    //DB
    DatabaseReference database_chat;
    DatabaseReference img;
    DatabaseReference database_typing;
    DatabaseReference database_typing1;
    DatabaseReference activenow;
    DatabaseReference activenow1;
    DatabaseReference dotactive;
    TabHost th;
    private FirebaseAuth mAuth;
    List<chat> users;
    static String x="no";
    String user_id_val;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        database_chat= FirebaseDatabase.getInstance().getReference("users");
        email_ = (TextView) findViewById(R.id.email);
        password_ = (EditText) findViewById(R.id.password);
        log_btn = (Button) findViewById(R.id.email_sign_in_button);

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser()!=null)
        {
            success1();
        }
        users=new ArrayList<>();
        // Sregister information
        email_reg = (TextView) findViewById(R.id.email1);
        password_reg = (EditText) findViewById(R.id.password1);
        re_password_reg= (EditText) findViewById(R.id.password_re1);
        register_btn = (Button) findViewById(R.id.email_register_button);
        username__reg=(EditText)findViewById(R.id.username);
        //End register info
        log_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        register_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptReg();
            }
        });

       th=(TabHost)findViewById(R.id.tabhost1);
        th.setup();
        TabHost.TabSpec spec=th.newTabSpec("tag1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Login");
        th.addTab(spec);

        spec=th.newTabSpec("tag1");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Register");
        th.addTab(spec);
        th.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                for (int i=0;i<th.getTabWidget().getChildCount();i++)
                {
                    th.getTabWidget().getChildAt(i).setBackgroundColor(Color.BLACK);
                    TextView tvt=(TextView)th.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
                    tvt.setTextColor(Color.WHITE);
                }
                th.getTabWidget().getChildAt(th.getCurrentTab()).setBackgroundColor(Color.GRAY);
                TextView tvt=(TextView)th.getCurrentTabView().findViewById(android.R.id.title);
                tvt.setTextColor(Color.WHITE);
            }
        });
        for (int i=0;i<th.getTabWidget().getChildCount();i++)
        {
            th.getTabWidget().getChildAt(i).setBackgroundColor(Color.BLACK);
            TextView tvt=(TextView)th.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tvt.setTextColor(Color.WHITE);
        }
        th.getTabWidget().getChildAt(th.getCurrentTab()).setBackgroundColor(Color.GRAY);
        TextView tvt=(TextView)th.getCurrentTabView().findViewById(android.R.id.title);
        tvt.setTextColor(Color.WHITE);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void attemptReg() {

        // Reset errors.
        email_reg.setError(null);
        password_reg.setError(null);
        re_password_reg.setError(null);
        username__reg.setError(null);
        // Store values at the time of the login attempt.
        String emailreg = email_reg.getText().toString();
        String usernamereg = username__reg.getText().toString();
        String passwordreg = password_reg.getText().toString();
        String repasswordreg = re_password_reg.getText().toString();

        if (TextUtils.isEmpty(emailreg)) {
            email_reg.setError("please type email");
        }
        else
        {
            if (TextUtils.isEmpty(usernamereg))
            {
                username__reg.setError("please type username");
            }
            else
            {
                if (TextUtils.isEmpty(passwordreg)) {
                    password_reg.setError("type password");
                }
                else
                {
                    if (!isPasswordValid(passwordreg)) {
                        password_reg.setError("password must be at least 6 characters");
                    }
                    else
                    {
                        if (!isEmailValid(emailreg)) {
                            email_reg.setError("Email is not valid");
                        }
                        else
                        {
                            if (passwordreg.equals(repasswordreg))
                            {
                                checkemailexist(emailreg);
                                if (x.equals("yes"))
                                {
                                    email_reg.setError("Email is used before");
                                }
                                else
                                {
                                    register(emailreg,passwordreg,usernamereg);
                                }
                            }
                            else
                            {

                                re_password_reg.setError("password is not identical");
                            }

                        }
                    }
                }
            }
        }
    }

    private void attemptLogin()
    {

        // Reset errors.
        email_.setError(null);
        password_.setError(null);

        // Store values at the time of the login attempt.
        String email = email_.getText().toString();
        String password = password_.getText().toString();
        if (TextUtils.isEmpty(password)) {
            email_.setError("please type password");
        }
        else
        {
            if (TextUtils.isEmpty(email)) {
                email_.setError("type email");
            }
            else
            {
                if (!isPasswordValid(password)) {
                    password_.setError("password must be at least 4 characters");
                }
                else
                {
                    if (!isEmailValid(email)) {
                        password_.setError("Email is not valid");
                    }
                    else
                    {
                        login(email,password);
                    }
                }
            }


        }



    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return true;
        }
        else
        {
            return false;
        }
    }
    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() >5;
    }
    private void login(final String email,final String password)
    {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(LoginActivity.this, " success.",Toast.LENGTH_SHORT).show();
                            success1();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, " failed.", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }
    private void success(String email,String password,String username)
    {
        String id=mAuth.getCurrentUser().getUid();
        Random random=new Random();
        String r=random.nextInt(10000)+"";
        users u=new users(username,password,id,email,r);
        database_chat.child(id).setValue(u);

        Toast.makeText(this,"registered successfully",Toast.LENGTH_LONG).show();
        String user_id_val= mAuth.getCurrentUser().getUid();

        dotactive=FirebaseDatabase.getInstance().getReference("dotactiv").child(user_id_val);
        dotactive.setValue("1");

        img= FirebaseDatabase.getInstance().getReference("users");
        img.child(id).child("img_uri").setValue("user_img_default");

        settings(user_id_val);
        success1();
        finish();
    }
    private void success1()
    {
        //Toast.makeText(this,"login successfully",Toast.LENGTH_LONG).show();

        Random random=new Random();
        final String r=random.nextInt(10000)+"";

        final Intent myintent=new Intent(this,Main2Activity.class);
        user_id_val= mAuth.getCurrentUser().getUid();
        database_chat=database_chat.child(user_id_val).child("username");

        database_chat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String uname=dataSnapshot.getValue(String.class);
                myintent.putExtra(my_id,user_id_val);
                myintent.putExtra(my_name,uname);
                myintent.putExtra(my_noti,r);
                myintent.putExtra(r_or_l,"log");
                finish();
                startActivity(myintent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public void checkemailexist(final String email)
    {
        database_chat= FirebaseDatabase.getInstance().getReference("users");
    }
    private void register(final String email, final String password,final String username)
    {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(LoginActivity.this, "Authentication success.",Toast.LENGTH_SHORT).show();
                            success(email,password,username);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });

    }

    private void settings(final String user_id_session)
    {
        database_chat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

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

                        activenow1.child("myactive").setValue("0");
                        activenow.child("myactive").setValue("0");

                        ////////////////////////////////////
                        database_typing.child("text").setValue("Typing now...");
                        database_typing.child("my_id").setValue(user_id_session);
                        database_typing.child("isnowtyping").setValue("0");
                        //////////////////////////////////
                        database_typing1.child("text").setValue("Typing now...");
                        database_typing1.child("my_id").setValue(user_id_session);
                        database_typing1.child("isnowtyping").setValue("0");

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

