package com.example.localadm.comtrade_auto_perionica;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Collections;
import java.util.List;

public class AutoAdapter extends RecyclerView.Adapter<AutoAdapter.AutoViewHolder> implements ItemTouchHelperAdapter {

    private List<Automobil> automobilList;
    private OnAutomobilSelected onAutomobilSelected;

    public AutoAdapter(List<Automobil> automobilList, OnAutomobilSelected onAutomobilSelected) {
        this.automobilList = automobilList;
        this.onAutomobilSelected = onAutomobilSelected;
    }

    
    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(automobilList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(automobilList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        Automobil automobil = automobilList.remove(position);
        notifyItemRemoved(position);
        if (onAutomobilSelected != null) {
            onAutomobilSelected.onAutomobilDone(automobil);
        }
    }

    @Override
    public AutoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_automobil, parent, false);

        return new AutoAdapter.AutoViewHolder(view, new OnAutomobilClick() {
            @Override
            public void onAutomobilClicked(int position) {
                Automobil automobil = automobilList.get(position);
                onAutomobilSelected.onAutomobilSelected(automobil);
            }

            @Override
            public void onUkloniAutomobil(int adapterPosition) {
                Automobil automobil = automobilList.remove(adapterPosition);
                notifyItemRemoved(adapterPosition);
                onAutomobilSelected.onAutomobileDeleted(automobil);
            }

            @Override
            public void onPozoviVlasnika(int adapterPosition) {
                Automobil automobil = automobilList.remove(adapterPosition);
                onAutomobilSelected.onPozoviVlasnika(automobil);
            }
        });
    }

    @Override
    public void onBindViewHolder(AutoViewHolder holder, int position) {
        Automobil automobil = automobilList.get(position);
        holder.imeVlsnikaTextView.setText(automobil.getImeVlasnika());
        holder.registracijaTextView.setText(automobil.getRegistracija());
        holder.brojTelefonaTextView.setText(automobil.getBrojTelefona());
        holder.cenaTextView.setText(String.format("%d", automobil.getCena()));
        holder.pranje.setChecked(automobil.isPranje());
        holder.usisavanje.setChecked(automobil.isUsisavanje());
        holder.voskiranje.setChecked(automobil.isVoskiranje());
        if (automobil.getBoja() != 0) { //Ako nema bolje staviti boju na 0, obratiti paznju na ovo
            holder.itemView.setBackgroundResource(automobil.getBoja());
        }
        Glide.with(holder.itemView.getContext())
                .load(automobil.getSlikaUri())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return automobilList != null ? automobilList.size() : 0;
    }

    public interface OnAutomobilSelected {
        void onAutomobilSelected(Automobil automobil);
        void onAutomobileDeleted(Automobil automobil);
        void onAutomobilDone(Automobil automobil);
        void onPozoviVlasnika(Automobil automobil);
    }

    public interface OnAutomobilClick {
        void onAutomobilClicked(int position);
        void onUkloniAutomobil(int adapterPosition);
        void onPozoviVlasnika(int adapterPosition);
    }

    static class AutoViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView imeVlsnikaTextView;
        private TextView registracijaTextView;
        private TextView brojTelefonaTextView;
        private TextView cenaTextView;
        private CheckBox pranje;
        private CheckBox usisavanje;
        private CheckBox voskiranje;
        private Button uklontAuto;
        private OnAutomobilClick onAutomobilClick;
        private Button pozoviVlasnika;

        public AutoViewHolder(View itemView, final OnAutomobilClick onAutomobilClick) {
            super(itemView);
            this.onAutomobilClick = onAutomobilClick;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int adapterPosition = getAdapterPosition();
                    if (adapterPosition != RecyclerView.NO_POSITION && onAutomobilClick != null) {
                        onAutomobilClick.onAutomobilClicked(adapterPosition);
                    }
                }
            });
            imeVlsnikaTextView = itemView.findViewById(R.id.ime_vlasnika_item);
            registracijaTextView = itemView.findViewById(R.id.registracija_item);
            brojTelefonaTextView = itemView.findViewById(R.id.broj_telefona_item);
            cenaTextView = itemView.findViewById(R.id.cena_item);
            imageView = itemView.findViewById(R.id.image_view_item);
            pranje = itemView.findViewById(R.id.pranje_item);
            usisavanje = itemView.findViewById(R.id.usisavanje_item);
            voskiranje = itemView.findViewById(R.id.voskiranje_item);
            uklontAuto = itemView.findViewById(R.id.ukolni_button_item);
            pozoviVlasnika = itemView.findViewById(R.id.button_pozovi_vlasnika);
            pozoviVlasnika.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPosition = getAdapterPosition();
                    if (adapterPosition != RecyclerView.NO_POSITION && onAutomobilClick != null) {
                        onAutomobilClick.onPozoviVlasnika(adapterPosition);
                    }
                }
            });
            uklontAuto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPosition = getAdapterPosition();
                    if (adapterPosition != RecyclerView.NO_POSITION && onAutomobilClick != null) {
                        onAutomobilClick.onUkloniAutomobil(adapterPosition);
                    }
                }
            });
        }
    }
}