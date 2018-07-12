package com.example.root.appapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

public class profile extends AppCompatActivity {
    Button selectimg;
    private static final int gallary=2;
    private static final int cameracode=1;
    private String img_uri="e";
    private ProgressDialog pd;
    private StorageReference mStorage;
    DatabaseReference database_chat;
    private ImageView iv;
    TextView tv;
    String name;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        Intent intent_session=getIntent();
        name=intent_session.getStringExtra(Main2Activity.name_i);
        id=intent_session.getStringExtra(Main2Activity.id_i);
        iv=(ImageView)findViewById(R.id.imageView);
        tv=(TextView)findViewById(R.id.name);
        mStorage= FirebaseStorage.getInstance().getReference();
        selectimg=(Button)findViewById(R.id.s_img);
        pd=new ProgressDialog(this);
        selectimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,gallary);
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onStart() {
        super.onStart();
        tv.setText(name);
        database_chat= FirebaseDatabase.getInstance().getReference("users");
        database_chat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                img_uri=dataSnapshot.child(id).child("img_uri").getValue(String.class);
                if (img_uri.equals("user_img_default"))
                {
                    iv.setImageResource(R.drawable.person);
                }
                else
                {
                    Uri uri=Uri.parse(img_uri);
                    Picasso.with(profile.this).load(uri).fit().centerCrop().into(iv);
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==gallary&&resultCode==RESULT_OK)
        {
            pd.setMessage("uploading");
            pd.show();
            Uri uri=data.getData();
            StorageReference filepath=mStorage.child("user_imgs").child(id);
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadimg=taskSnapshot.getDownloadUrl();
                    String uri=downloadimg.toString();
                    database_chat.child(id).child("img_uri").setValue(uri);
                    Picasso.with(profile.this).load(downloadimg).fit().centerCrop().into(iv);
                    Toast.makeText(profile.this,"done",Toast.LENGTH_LONG).show();
                    pd.dismiss();
                }
            });
        }
    }
}
