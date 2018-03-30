package com.example.localadm.comtrade_auto_perionica;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.TextView;

import com.example.localadm.comtrade_auto_perionica.database.DatabaseContract;
import com.example.localadm.comtrade_auto_perionica.database.DatabaseHelper;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.localadm.comtrade_auto_perionica.database.DatabaseContract.Automobil.TABLE_NAME;

public class AutomobilActivity extends AppCompatActivity implements AutoAdapter.OnAutomobilSelected {

    private static final int DODAJ_AUTO_ACTIVITY_REQUEST_CODE = 10;
    RecyclerView recyclerView;
    TextView nemaAutomobila;
    ArrayList<Automobil> autoList;
    AutoAdapter autoAdapter;
    DatabaseHelper databaseHelper;
    SQLiteDatabase database;
    private TextView ukupnaCenaTextView;
    int ukupnaCena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automobil);
        Toolbar toolbar = findViewById(R.id.toolbar);
        ukupnaCenaTextView = findViewById(R.id.ukupna_cena);
        nemaAutomobila = findViewById(R.id.poruka_nema_automobila);
        recyclerView = findViewById(R.id.recycler_view);

        setSupportActionBar(toolbar);

        databaseHelper = new DatabaseHelper(this);
        database = databaseHelper.getWritableDatabase();

        //Automobil automobil;

        if (savedInstanceState==null){
            readAutoFromDatabase();
            updateVisibilty();
            //autoList.add(new Automobil("Dalibor Mirkovic", "BG 11445 ww", "054265465", 150, true, true, false, 0));
            //autoList.add(new Automobil("Misa Peric", "BG 11345 33", "054635465", 50, true, false, false, 0));

            autoAdapter = new AutoAdapter(autoList, this);
            ItemTouchHelper.Callback callback =
                    new SimpleItemTouchHelperCallback(autoAdapter);
            ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
            touchHelper.attachToRecyclerView(recyclerView);
            recyclerView.setAdapter(autoAdapter);

        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setUkupnaCena();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otvoriDodajAutoActivity();
            }
        });
    }


    //ovo zatvara vezu sa bazom kaka se ovaj activiti "ubije, zatvori"
    @Override
    protected void onDestroy() {
        database.close();
        super.onDestroy();
    }

    private void readAutoFromDatabase() {
        autoList = new ArrayList<>();
        String colums[] = {
                DatabaseContract.Automobil._ID,
                DatabaseContract.Automobil.IME_VLASNIKA,
                DatabaseContract.Automobil.REGISTRACIJA,
                DatabaseContract.Automobil.BROJ_TELEFONA,
                DatabaseContract.Automobil.CENA,
                DatabaseContract.Automobil.PRANJE,
                DatabaseContract.Automobil.VOSKIRANJE,
                DatabaseContract.Automobil.USISAVANJE,
                DatabaseContract.Automobil.BOJA,
                DatabaseContract.Automobil.SLIKA
        };
        Cursor cursor = database.query(TABLE_NAME,
                colums, null, null, null, null, null);
        while(cursor.moveToNext()){
            long dataBaseId = cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseContract.Automobil._ID));
            String imeVlasnika = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.Automobil.IME_VLASNIKA));
            String registracija = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.Automobil.REGISTRACIJA));
            String brojTelefona = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.Automobil.BROJ_TELEFONA));
            String slikaURL = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.Automobil.SLIKA));
            int cena = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.Automobil.CENA));
            int pranje = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.Automobil.PRANJE));
            int usisavanje = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.Automobil.USISAVANJE));
            int voskiranje = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.Automobil.VOSKIRANJE));
            int boja = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.Automobil.BOJA));
            Automobil automobil = new Automobil(dataBaseId, imeVlasnika, registracija, brojTelefona, slikaURL, cena, pranje==1, usisavanje==1, voskiranje==1, boja);
            autoList.add(automobil);
        }
        cursor.close();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DODAJ_AUTO_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Automobil automobil = data.getParcelableExtra(DodajAutoActivity.AUTOMOBIL_INTENT_KEY);
                if (automobil != null) {
                    if (automobil.getDatabaseID() == 0) {
                        autoList.add(automobil);
                        autoAdapter.notifyItemInserted(autoList.size() - 1);
                        addAutoToDataBase(automobil);
                    } else {
                        int found = -1;
                        for (int i = 0; i < autoList.size(); i++) {
                            Automobil a = autoList.get(i);
                            if (a.getDatabaseID() == automobil.getDatabaseID()) {
                                found = i;
                                break;
                            }
                        }
                        if (found != -1) {
                            autoList.remove(found);
                            autoList.add(found, automobil);
                            autoAdapter.notifyItemChanged(found);
                            updateAutoInDatabase(automobil);
                        }
                    }
                    updateVisibilty();
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
        long id = database.insert(TABLE_NAME, null, contentValues);
        automobil.setDatabaseID(id);
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
            //TODO dodati code koji ce da resetuje ukupnu cenu i view za ukupnu cenu
            //TODO napravite novu metocu koja brise sve iz baze.
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setUkupnaCena() {
        String cenaText = getString(R.string.ukupna_zarada_text, ukupnaCena);
        ukupnaCenaTextView.setText(cenaText);
    }

    @Override
    public void onAutomobilSelected(Automobil automobil) {
        Intent intent = new Intent(this, DodajAutoActivity.class);
        intent.putExtra(DodajAutoActivity.AUTOMOBIL_TO_EDIT_BUNDLE_KEY, automobil);
        startActivityForResult(intent, DODAJ_AUTO_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void onAutomobileDeleted(Automobil automobil) {
        updateVisibilty();
        ukloniAutoIzDatabase(automobil.getDatabaseID());
    }

    @Override
    public void onAutomobilDone(Automobil automobil) {
        ukupnaCena += automobil.izracunaCenuPranja();
        setUkupnaCena();
        updateVisibilty();
        ukloniAutoIzDatabase(automobil.getDatabaseID());
    }

    private void ukloniAutoIzDatabase(long autoDatabaseId) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.delete(TABLE_NAME, _ID + " = ?", new String[] { String.valueOf(autoDatabaseId)});
        db.close();
    }

    private void updateAutoInDatabase(Automobil automobil) {
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
        long id = database.update(TABLE_NAME, contentValues, _ID + " = ?", new String[] { String.valueOf(automobil.getDatabaseID())});
    }

    public void updateVisibilty() {
        if (autoList.isEmpty()) {
            nemaAutomobila.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        } else {
            nemaAutomobila.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}