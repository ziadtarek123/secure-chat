package com.example.root.appapp;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DB extends SQLiteOpenHelper {

    Context context;
    private static final String dbname="keys";
    private static final String tablename="mykeys";
    private static final int db_version=1;
    private static final String createtbl="Create table "+tablename+" (k_id INTEGER PRIMARY KEY AUTOINCREMENT, key VARCHAR(255));";
    private static final String droptbl="Drop table if exist "+tablename;




    public DB(Context context) {
        super(context, dbname, null, db_version);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(createtbl);
            Toast.makeText(context,"database created successfully ",Toast.LENGTH_LONG).show();
        }
        catch (SQLException e)
        {
            Toast.makeText(context,"err "+e,Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(droptbl);
            onCreate(db);
            Toast.makeText(context,"upgraded",Toast.LENGTH_LONG).show();
        }
        catch (SQLException e)
        {
            Toast.makeText(null,"err "+e,Toast.LENGTH_LONG).show();
        }


    }
}
