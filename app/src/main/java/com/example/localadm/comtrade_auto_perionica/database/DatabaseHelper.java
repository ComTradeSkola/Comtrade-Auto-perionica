package com.example.localadm.comtrade_auto_perionica.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Auto_perionica.db";

    public static final String CREATE_TABLE_AUTO = "CREATE TABLE " + DatabaseContract.Automobil.TABLE_NAME + " (" +
            DatabaseContract.Automobil._ID + " integer primary key autoincrement not null unique," +
            DatabaseContract.Automobil.IME_VLASNIKA + " text not null," +
            DatabaseContract.Automobil.REGISTRACIJA + " text not null," +
            DatabaseContract.Automobil.BROJ_TELEFONA + " text not null," +
            DatabaseContract.Automobil.CENA + " text not null," +
            DatabaseContract.Automobil.PRANJE + " integer not null," +
            DatabaseContract.Automobil.USISAVANJE + " integer not null," +
            DatabaseContract.Automobil.VOSKIRANJE + " integer not null," +
            DatabaseContract.Automobil.BOJA + " integer," +
            DatabaseContract.Automobil.SLIKA + " text);";

    private static final String DELETE_TABLE_AUTO =
            "DROP TABLE IF EXISTS " + DatabaseContract.Automobil.TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_AUTO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DELETE_TABLE_AUTO);
        onCreate(sqLiteDatabase);
    }
}
