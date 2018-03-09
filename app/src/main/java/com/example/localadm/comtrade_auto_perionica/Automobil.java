package com.example.localadm.comtrade_auto_perionica;


import android.os.Parcel;
import android.os.Parcelable;

public class Automobil implements Parcelable {

    private String imeVlasnika;
    private String registracija;
    private String BrojTelefona;
    private String slikaUri;
    private boolean pranje;
    private boolean usisavanje;
    private boolean voskiranje;
    private int boja;

    public Automobil(String imeVlasnika, String registracija, String brojTelefona, String slikaUri, boolean pranje, boolean usisavanje, boolean voskiranje, int boja) {
        this.imeVlasnika = imeVlasnika;
        this.registracija = registracija;
        BrojTelefona = brojTelefona;
        this.slikaUri = slikaUri;
        this.pranje = pranje;
        this.usisavanje = usisavanje;
        this.voskiranje = voskiranje;
        this.boja = boja;
    }

    public String getImeVlasnika() {
        return imeVlasnika;
    }

    public void setImeVlasnika(String imeVlasnika) {
        this.imeVlasnika = imeVlasnika;
    }

    public String getRegistracija() {
        return registracija;
    }

    public void setRegistracija(String registracija) {
        this.registracija = registracija;
    }

    public String getBrojTelefona() {
        return BrojTelefona;
    }

    public void setBrojTelefona(String brojTelefona) {
        BrojTelefona = brojTelefona;
    }

    public String getSlikaUri() {
        return slikaUri;
    }

    public void setSlikaUri(String slikaUri) {
        this.slikaUri = slikaUri;
    }

    public boolean isPranje() {
        return pranje;
    }

    public void setPranje(boolean pranje) {
        this.pranje = pranje;
    }

    public boolean isUsisavanje() {
        return usisavanje;
    }

    public void setUsisavanje(boolean usisavanje) {
        this.usisavanje = usisavanje;
    }

    public boolean isVoskiranje() {
        return voskiranje;
    }

    public void setVoskiranje(boolean voskiranje) {
        this.voskiranje = voskiranje;
    }

    public int getBoja() {
        return boja;
    }

    public void setBoja(int boja) {
        this.boja = boja;
    }

    public Automobil(String imeVlasnika, String registracija, String brojTelefona, String slikaUri, boolean pranje, boolean usisavanje, boolean voskiranje) {
        this.imeVlasnika = imeVlasnika;
        this.registracija = registracija;
        BrojTelefona = brojTelefona;
        this.slikaUri = slikaUri;
        this.pranje = pranje;
        this.usisavanje = usisavanje;
        this.voskiranje = voskiranje;
    }

    protected Automobil(Parcel in) {
        imeVlasnika = in.readString();
        registracija = in.readString();
        BrojTelefona = in.readString();
        slikaUri = in.readString();
        pranje = in.readByte() != 0x00;
        usisavanje = in.readByte() != 0x00;
        voskiranje = in.readByte() != 0x00;
        boja = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imeVlasnika);
        dest.writeString(registracija);
        dest.writeString(BrojTelefona);
        dest.writeString(slikaUri);
        dest.writeByte((byte) (pranje ? 0x01 : 0x00));
        dest.writeByte((byte) (usisavanje ? 0x01 : 0x00));
        dest.writeByte((byte) (voskiranje ? 0x01 : 0x00));
        dest.writeInt(boja);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Automobil> CREATOR = new Parcelable.Creator<Automobil>() {
        @Override
        public Automobil createFromParcel(Parcel in) {
            return new Automobil(in);
        }

        @Override
        public Automobil[] newArray(int size) {
            return new Automobil[size];
        }
    };
}