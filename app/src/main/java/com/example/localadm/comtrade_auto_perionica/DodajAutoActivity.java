package com.example.localadm.comtrade_auto_perionica;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DodajAutoActivity extends AppCompatActivity {

    public static final String AUTOMOBIL_INTENT_KEY = "automobil_intent_key";
    public static final String AUTOMOBIL_TO_EDIT_BUNDLE_KEY = "automobil_bundle_key";

    private static final int PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE = 10;
    private static final int REQUEST_IMAGE_CAPTURE = 5;

    private static final int CENA_PRANJA = 200;
    private static final int CENA_VOSKIRANJA = 150;
    private static final int CENA_USISAVANJA = 100;

    ImageView imageView;
    ImageButton uslikajImageButton;
    TextView imeVlasnikatextView;
    TextView registracijaTextView;
    TextView brojtelefonaTextView;
    CheckBox pranjeCheckbox;
    CheckBox usisavanjeCheckbox;
    CheckBox voskiranjeCheckbox;
    Button dodajAutoButton;
    ConstraintLayout layout;
    String lokacijaSlike;
    View izabranaBojaView;
    TextView ukupnaCenaUsluge;
    int cenaUsluge = 0;


    //TODO zapamtiti da se na rotaciji ovo mora sacuvati i da se na osnovu ovoga mora opet oznaciti koja je boja selektovana
    int izabranaBoja;
    Automobil automobil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_auto);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //TODO set back button

        layout = findViewById(R.id.dodaj_auto_layout);

        imageView = findViewById(R.id.image_view_dodaj_auto);
        uslikajImageButton = findViewById(R.id.uslikaj_image_button);
        uslikajImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proveriPermisiju();
            }
        });

        imeVlasnikatextView = findViewById(R.id.ime_vlasnika_text_view);
        registracijaTextView = findViewById(R.id.registracija_text_view);
        brojtelefonaTextView = findViewById(R.id.broj_telefona_text_view);

        ukupnaCenaUsluge = findViewById(R.id.ukupna_cena_usluge);
        pranjeCheckbox = findViewById(R.id.pranje_checkbox);
        pranjeCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cenaUsluge += CENA_PRANJA;
                } else {
                    cenaUsluge -= CENA_PRANJA;
                }
                ukupnaCenaUsluge.setText(String.valueOf(cenaUsluge));
            }
        });
        usisavanjeCheckbox = findViewById(R.id.usisavanje_checkbox);
        voskiranjeCheckbox = findViewById(R.id.voskiranje_checkbox);
        //TODO dodati za ovo checkbox

        izabranaBojaView = findViewById(R.id.izabrana_boja_view);


        automobil = getIntent().getParcelableExtra(AUTOMOBIL_TO_EDIT_BUNDLE_KEY);
        if (automobil != null) {
            imeVlasnikatextView.setText(automobil.getImeVlasnika());
            registracijaTextView.setText(automobil.getRegistracija());
            brojtelefonaTextView.setText(automobil.getBrojTelefona());
            pranjeCheckbox.setChecked(automobil.isPranje());
            voskiranjeCheckbox.setChecked(automobil.isVoskiranje());
            usisavanjeCheckbox.setChecked(automobil.isUsisavanje());
            lokacijaSlike = automobil.getSlikaUri();
            if (lokacijaSlike != null && !lokacijaSlike.isEmpty()) {
                Glide.with(this)
                        .load(lokacijaSlike)
                        .into(imageView);
            }

            //TODO odraditi ovo za boju, to je to
        }


        dodajAutoButton = findViewById(R.id.dodaj_auto_button);
        dodajAutoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ucitali podatke

                String imeString = imeVlasnikatextView.getText().toString();
                String registracijaString = registracijaTextView.getText().toString();
                String telefonString = brojtelefonaTextView.getText().toString();

                cenaUsluge = 0;
                if (pranjeCheckbox.isChecked()) {
                    cenaUsluge = cenaUsluge + 200;
                }
                if (usisavanjeCheckbox.isChecked()) {
                    cenaUsluge = cenaUsluge + 100;
                }
                if (voskiranjeCheckbox.isChecked()) {
                    cenaUsluge = cenaUsluge + 150;
                }

                if (!voskiranjeCheckbox.isChecked() && !pranjeCheckbox.isChecked() && !usisavanjeCheckbox.isChecked()) {
                    //TODo prikazati poruku da nije dobro
                    return;
                }


                if (imeVlasnikatextView.length() == 0) {
                    Snackbar.make(view, "Niste uneli ime", Snackbar.LENGTH_SHORT).show();
                } else if (registracijaTextView.length() == 0) {
                    Snackbar.make(view, "Niste uneli registraciju", Snackbar.LENGTH_SHORT).show();
                } else if (brojtelefonaTextView.length() == 0) {
                    Snackbar.make(view, "Niste uneli broj telefona", Snackbar.LENGTH_SHORT).show();
                } else {

                    if (automobil == null) {
                        automobil = new Automobil();
                    }
                    automobil.setImeVlasnika(imeString);
                    automobil.setRegistracija(registracijaString);
                    automobil.setBrojTelefona(telefonString);
                    automobil.setPranje(pranjeCheckbox.isChecked());
                    automobil.setVoskiranje(voskiranjeCheckbox.isChecked());
                    automobil.setUsisavanje(usisavanjeCheckbox.isChecked());
                    automobil.setSlikaUri(lokacijaSlike);
                    automobil.setBoja(izabranaBoja);
                    automobil.setCena(cenaUsluge);

                    Intent intent = new Intent();
                    intent.putExtra(AUTOMOBIL_INTENT_KEY, automobil);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    private void proveriPermisiju() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            slikaj();
        } else {
            zatraziPermisiju();
        }
    }

    private void zatraziPermisiju() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Snackbar.make(layout, R.string.obavestenje,
                    Snackbar.LENGTH_INDEFINITE).setAction(R.string.ok, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivityCompat.requestPermissions(DodajAutoActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE);
                }
            }).show();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                slikaj();
            }
        } else {
            Snackbar.make(layout, "Komanda nije dozvoljena.",
                    Snackbar.LENGTH_SHORT).
                    show();
        }
    }

    private void slikaj() {
        Intent slikajIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (slikajIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = kreirajSliku();
            } catch (IOException ex) {
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.localadm.comtrade_auto_perionica.fileprovider",
                        photoFile);
                slikajIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(slikajIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File kreirajSliku() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File slika = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        lokacijaSlike = slika.getAbsolutePath();
        return slika;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            new DecodePictureAsyncTask(imageView).execute(lokacijaSlike);
        }
    }

    public void onSelectColor(View view) {
        int viewId = view.getId();
        switch (viewId) {
            case R.id.crvena_boja_imageButton:
                izabranaBoja = R.color.crvena;
                izabranaBojaView.setBackgroundResource(R.color.crvena);
                break;
            case R.id.zuta_boja_imageButton:
                izabranaBoja = R.color.zuta;
                izabranaBojaView.setBackgroundResource(R.color.zuta);
                break;
            case R.id.plava_boja_imageButton:
                izabranaBoja = R.color.plava;
                izabranaBojaView.setBackgroundResource(R.color.plava);
                break;
            case R.id.roze_boja_imageButton:
                izabranaBoja = R.color.roze;
                izabranaBojaView.setBackgroundResource(R.color.roze);
                break;
            case R.id.siva_boja_imageButton:
                izabranaBoja = R.color.siva;
                izabranaBojaView.setBackgroundResource(R.color.siva);
                break;
            case R.id.zelena_boja_imageButton:
                izabranaBoja = R.color.zelena;
                izabranaBojaView.setBackgroundResource(R.color.zelena);
                break;
            case R.id.lila_boja_imageButton:
                izabranaBoja = R.color.lila;
                izabranaBojaView.setBackgroundResource(R.color.lila);
                break;
            case R.id.narandzasta_boja_imageButton:
                izabranaBoja = R.color.narandzasta;
                izabranaBojaView.setBackgroundResource(R.color.narandzasta);
                break;
            case R.id.crna_boja_imageButton:
                izabranaBoja = R.color.crna;
                izabranaBojaView.setBackgroundResource(R.color.crna);
                break;
        }

    }
}


class DecodePictureAsyncTask extends AsyncTask<String, Void, Bitmap> {

    @SuppressLint("StaticFieldLeak")
    private ImageView imageView;

    public DecodePictureAsyncTask(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        imageView.setVisibility(View.INVISIBLE);
    }

    protected Bitmap doInBackground(String... urls) {
        String fileName = urls[0];
        int targetW = imageView.getMeasuredWidth();
        int targetH = imageView.getMeasuredHeight();
        targetW = targetW == 0 ? 1 : targetW;
        targetH = targetH == 0 ? 1 : targetH;

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(fileName, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        return BitmapFactory.decodeFile(fileName, bmOptions);
    }

    protected void onPostExecute(Bitmap bitmap) {
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageBitmap(bitmap);
        imageView = null;
    }
}
