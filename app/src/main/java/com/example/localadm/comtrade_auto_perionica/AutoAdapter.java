package com.example.localadm.comtrade_auto_perionica;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class AutoAdapter extends RecyclerView.Adapter<AutoAdapter.AutoViewHolder>{

    private List<Automobil> automobilList;
    private OnAutomobilSelected onAutomobilSelected;

    public AutoAdapter(List<Automobil> automobilList, OnAutomobilSelected onAutomobilSelected) {
        this.automobilList = automobilList;
        this.onAutomobilSelected = onAutomobilSelected;
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
        private OnAutomobilClick onAutomobilClick;

        public AutoViewHolder(View itemView, final OnAutomobilClick onAutomobilClick) {
            super(itemView);
            this.onAutomobilClick = onAutomobilClick;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onAutomobilClick.onAutomobilClicked(getAdapterPosition());
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
        });
    }

    @Override
    public void onBindViewHolder(AutoViewHolder holder, int position) {
        Automobil automobil = automobilList.get(position);
        holder.imeVlsnikaTextView.setText(automobil.getImeVlasnika());
        holder.registracijaTextView.setText(automobil.getRegistracija());
        holder.brojTelefonaTextView.setText(automobil.getBrojTelefona());
        holder.cenaTextView.setText(automobil.getCena());
        holder.pranje.setChecked(automobil.isPranje());
        holder.usisavanje.setChecked(automobil.isUsisavanje());
        holder.voskiranje.setChecked(automobil.isVoskiranje());
        Glide.with(holder.itemView.getContext())
                .load(automobil.getSlikaUri())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return automobilList.size();
    }

    public interface OnAutomobilSelected {
        void onAutomobilSelected(Automobil automobil);
    }

    public interface OnAutomobilClick {
        void onAutomobilClicked(int position);
    }

}


