package com.projeto.pokedex.recyclerview.adapter;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.projeto.pokedex.R;
import com.projeto.pokedex.pokedex.database.pokeinfo.tables.Type;
import com.projeto.pokedex.pokedex.entities.PokemonInfo;
import com.projeto.pokedex.util.ColorUtil;
import com.projeto.pokedex.util.ConstantUtil;

import java.util.ArrayList;

public class PokemonTypeAdapter extends RecyclerView.Adapter<com.projeto.pokedex.recyclerview.adapter.PokemonTypeAdapter.ViewHolder> {
    private ArrayList<PokemonInfo.Types> listaType;
    private ArrayList<Type> listaTypeDB;

    public PokemonTypeAdapter() {
        this.listaType = new ArrayList<>();
        this.listaTypeDB = new ArrayList<>();
    }

    @NonNull
    @Override
    public PokemonTypeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_info, parent, false);
        return new PokemonTypeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (listaType.isEmpty() || listaType == null) {
            getListaDoDatabase(holder, position);
        } else {
            holder.typeTextView.setText(listaType.get(position).getType().getName().toUpperCase());
            int type = listaType.get(position).getType().getNumber();
            setColorType(holder, type);
        }
    }

    private void getListaDoDatabase(ViewHolder holder, int position) {
        holder.typeTextView.setText(listaTypeDB.get(position).getNomeTipo().toUpperCase());
        int type = listaTypeDB.get(position).getIdTipo();
        setColorType(holder, type);
    }

    @Override
    public int getItemCount() {
        if (listaType.isEmpty())
            return listaTypeDB.size();
        return listaType.size();
    }

    private void setColorType(ViewHolder holder, int type) {
        Drawable background = holder.typeContainerImageView.getBackground();
        Drawable pokeball = holder.logoImageView.getBackground();
        int viewColor = ColorUtil.getForegroundViewColor(type);

        background.setColorFilter(ColorUtil.getBackgroundColor(type), PorterDuff.Mode.MULTIPLY);
        holder.typeTextView.setTextColor(viewColor);
        pokeball.setColorFilter(viewColor, PorterDuff.Mode.SRC_IN);
    }

    public void adicionaLista(ArrayList<PokemonInfo.Types> lista) {
        if (lista != null) {
            this.listaType.addAll(lista);
            notifyDataSetChanged();
        } else {
            Log.i(ConstantUtil.POKEDEX_ERRO, lista.toString());
        }
    }

    public void adicionaListaDoDatabase(ArrayList<Type> lista) {
        if (lista != null) {
            this.listaTypeDB.addAll(lista);
            notifyDataSetChanged();
        } else {
            Log.i(ConstantUtil.POKEDEX_ERRO, lista.toString());
        }
    }

    public int getType() {
        if (listaType.isEmpty() && listaTypeDB.isEmpty())
            return 0;
        if (listaType.isEmpty())
            return listaTypeDB.get(0).getIdTipo();
        return listaType.get(0).getType().getNumber();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView logoImageView, typeContainerImageView;
        private TextView typeTextView;

        public ViewHolder(View view) {
            super(view);
            logoImageView = view.findViewById(R.id.item_topic_vector);
            typeTextView = view.findViewById(R.id.item_topic_texto);
            typeContainerImageView = view.findViewById(R.id.item_topic_container);
        }
    }
}