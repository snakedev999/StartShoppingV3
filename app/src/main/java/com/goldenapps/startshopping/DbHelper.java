package com.goldenapps.startshopping;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "data.ss";
    private static final int DB_SCHEME_VERSION = 1;

    //Constructor de la clase
    public DbHelper(Context context) {
        super(context, DB_NAME,null, DB_SCHEME_VERSION);
    }

    @Override
    public void onCreate(@NonNull SQLiteDatabase db) {
        db.execSQL("CREATE TABLE USUARIO (ID INT PRIMARY KEY NOT NULL,IDUSUARIO TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

