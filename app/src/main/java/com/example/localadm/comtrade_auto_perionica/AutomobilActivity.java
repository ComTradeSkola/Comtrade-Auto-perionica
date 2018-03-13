package com.example.localadm.comtrade_auto_perionica;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class AutomobilActivity extends AppCompatActivity implements AutoAdapter.OnAutomobilSelected {

    private static final int DODAJ_AUTO_ACTIVITY_REQUEST_CODE = 10;
    RecyclerView recyclerView;
    ArrayList<Automobil> autoList;
    AutoAdapter autoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automobil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        autoList = new ArrayList<>();

//        Automobil automobil = new Automobil("miljan", "bg 45 33", "5465465", 50, true, true, false, 5);
//        autoList.add(automobil);

        recyclerView = findViewById(R.id.recycler_view);


        autoAdapter = new AutoAdapter(autoList, this);
        ItemTouchHelper.Callback callback =
                new SimpleItemTouchHelperCallback(autoAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(autoAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otvoriDodajAutoActivity();
            }
        });
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
}
