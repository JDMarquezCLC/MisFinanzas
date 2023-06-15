package com.josedavid.misfinanzas.bbdd;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class Helper extends SQLiteOpenHelper {

    private String sqlCreateUsersTable = "CREATE TABLE CuentasUsuarios(" +
            "clave INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nombre TEXT, " +
            "primer TINYINT(1), " +
            "total DECIMAL(16,2), " +
            "password TEXT)";
    private String sqlDropUsersTable = "DROP TABLE IF EXISTS CuentasUsuarios";


    public Helper(
            @Nullable Context context,
            @Nullable String name,
            @Nullable SQLiteDatabase.CursorFactory factory,
            int version) {
        super(context, name, factory, version);
    }

    public Helper(
            @Nullable Context context,
            @Nullable String name,
            @Nullable SQLiteDatabase.CursorFactory factory,
            int version,
            @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public Helper(
            @Nullable Context context,
            @Nullable String name,
            int version,
            @NonNull SQLiteDatabase.OpenParams openParams) {
        super(context, name, version, openParams);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreateUsersTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(sqlDropUsersTable);
        db.execSQL(sqlCreateUsersTable);

    }
}
