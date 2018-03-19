package com.example.localadm.comtrade_auto_perionica.database;

import android.provider.BaseColumns;

public final class DatabaseContract {

    private DatabaseContract() {}

    public final class Automobil implements BaseColumns {
        public static final String TABLE_NAME = "automobil";
        public static final String IME_VLASNIKA = "ime_vlasnika";
        public static final String REGISTRACIJA = "registracija";
        public static final String BROJ_TELEFONA = "broj_telefona";
        public static final String CENA = "cena";
        public static final String PRANJE = "pranje";
        public static final String USISAVANJE = "usisavanje";
        public static final String VOSKIRANJE = "voskiranje";
        public static final String SLIKA = "lokacija_slike";
    }
}
