package com.example.localadm.comtrade_auto_perionica;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class DodajAutoActivity extends AppCompatActivity {

    ImageView imageView;
    ImageButton uslikajImageButton;
    TextView imeVlasnikatextView;
    TextView registracijaTextView;
    TextView brojtelefonaTextView;
    CheckBox pranjeCheckbox;
    CheckBox usisavanjeCheckbox;
    CheckBox voskiranjeCheckbox;
    ImageButton crvenaBojaImageButton;
    ImageButton zutaBojaImageButton;
    ImageButton plavaBojaImageButton;
    ImageButton lilaBojaImageButton;
    ImageButton sivaBojaImageButton;
    ImageButton rozeBojaImageButton;
    ImageButton crnaBojaImageButton;
    ImageButton zelenaBojaImageButton;
    ImageButton narandzastaBojaImageButton;
    Button dodajAutoButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_auto);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageView = findViewById(R.id.image_view_dodaj_auto);
        uslikajImageButton = findViewById(R.id.uslikaj_image_button);
        imeVlasnikatextView = findViewById(R.id.ime_vlasnika_text_view);
        registracijaTextView = findViewById(R.id.registracija_text_view);
        brojtelefonaTextView = findViewById(R.id.broj_telefona_text_view);
        pranjeCheckbox = findViewById(R.id.pranje_checkbox);
        usisavanjeCheckbox = findViewById(R.id.usisavanje_checkbox);
        voskiranjeCheckbox = findViewById(R.id.voskiranje_checkbox);
        crvenaBojaImageButton = findViewById(R.id.crvena_boja_imageButton);
        plavaBojaImageButton = findViewById(R.id.plava_boja_imageButton);
        zutaBojaImageButton = findViewById(R.id.zuta_boja_imageButton);
        zelenaBojaImageButton = findViewById(R.id.zelena_boja_imageButton);
        rozeBojaImageButton = findViewById(R.id.roze_boja_imageButton);
        lilaBojaImageButton = findViewById(R.id.lila_boja_imageButton);
        sivaBojaImageButton = findViewById(R.id.siva_boja_imageButton);
        narandzastaBojaImageButton = findViewById(R.id.narandzasta_boja_imageButton);
        crnaBojaImageButton = findViewById(R.id.crna_boja_imageButton);
        dodajAutoButton = findViewById(R.id.dodaj_auto_button);
    }

}
