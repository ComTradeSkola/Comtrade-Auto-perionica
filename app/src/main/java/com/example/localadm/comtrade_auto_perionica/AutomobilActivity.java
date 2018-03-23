package com.example.localadm.comtrade_auto_perionica;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.localadm.comtrade_auto_perionica.database.DatabaseContract;
import com.example.localadm.comtrade_auto_perionica.database.DatabaseHelper;

import java.util.ArrayList;

public class AutomobilActivity extends AppCompatActivity implements AutoAdapter.OnAutomobilSelected {

    private static final int DODAJ_AUTO_ACTIVITY_REQUEST_CODE = 10;
    RecyclerView recyclerView;
    ArrayList<Automobil> autoList;
    AutoAdapter autoAdapter;
    DatabaseHelper databaseHelper;
    SQLiteDatabase database;

    int ukupnaCena; //TODO cena svih pranja, voditi racuna da ovo treba da se sacuva i kada se aplikacija zavrsi i kada se aktiviti rotira. Koristiti shared preferences.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automobil);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        databaseHelper = new DatabaseHelper(this);
        database = databaseHelper.getWritableDatabase();

        //Automobil automobil;

        autoList = new ArrayList<>();

        autoList.add(new Automobil("Dalibor Mirkovic", "BG 11445 ww", "054265465", 150, true, true, false, 0));
        autoList.add(new Automobil("Misa Peric", "BG 11345 33", "054635465", 50, true, false, false, 0));

        recyclerView = findViewById(R.id.recycler_view);

        autoAdapter = new AutoAdapter(autoList, this);
        ItemTouchHelper.Callback callback =
                new SimpleItemTouchHelperCallback(autoAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(autoAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otvoriDodajAutoActivity();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DODAJ_AUTO_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Automobil automobil = data.getParcelableExtra(DodajAutoActivity.AUTOMOBIL__INTENT_KEY);
                if (automobil != null) {
                    autoList.add(automobil);
                    autoAdapter.notifyItemInserted(autoList.size() - 1);
                    addAutoToDataBase(automobil);
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void addAutoToDataBase(Automobil automobil) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContract.Automobil.IME_VLASNIKA, automobil.getImeVlasnika());
        contentValues.put(DatabaseContract.Automobil.REGISTRACIJA, automobil.getRegistracija());
        contentValues.put(DatabaseContract.Automobil.BROJ_TELEFONA, automobil.getBrojTelefona());
        contentValues.put(DatabaseContract.Automobil.CENA, automobil.getCena());
        contentValues.put(DatabaseContract.Automobil.BOJA, automobil.getBoja());
        contentValues.put(DatabaseContract.Automobil.PRANJE, automobil.isPranje() ? 1 : 0);
        contentValues.put(DatabaseContract.Automobil.VOSKIRANJE, automobil.isVoskiranje() ? 1 : 0);
        contentValues.put(DatabaseContract.Automobil.USISAVANJE, automobil.isUsisavanje() ? 1 : 0);
        contentValues.put(DatabaseContract.Automobil.SLIKA, automobil.getSlikaUri());
        database.insert(DatabaseContract.Automobil.TABLE_NAME, null, contentValues);
    }


    private void otvoriDodajAutoActivity() {
        Intent intent = new Intent(this, DodajAutoActivity.class);
        startActivityForResult(intent, DODAJ_AUTO_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAutomobilSelected(Automobil automobil) {
        Intent intent = new Intent(this, DodajAutoActivity.class);
        intent.putExtra("todo_to_edit", automobil);
        startActivityForResult(intent, DODAJ_AUTO_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void onAutomobileDeleted(Automobil automobil) {
        //TODO ovde obrisati iz database, automobil, to znaci da je neko odustao.
        //sto znaci pozvati metodu ukloniAutoIzDatabase i proslediti joj automobilov databaseId
    }

    @Override
    public void onAutomobilDone(Automobil automobil) {
        //TODO ako je automobil opran, to znaci da treba da uzmemo cenu, da je dodamo na cene koje vec imamo, i onda da uklonimo auto iz databasa
        //ukupnaCena += cena;
        //sto znaci pozvati metodu ukloniAutoIzDatabase i proslediti joj automobilov databaseId
    }

    private void ukloniAutoIzDatabase(int autoDatabaseId) {
        //TODO ukloniti iz database auto ciji je id = autoDatabaseId
    }
}
