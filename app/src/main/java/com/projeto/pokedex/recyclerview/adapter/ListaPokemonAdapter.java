package com.projeto.pokedex.recyclerview.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.projeto.pokedex.R;
import com.projeto.pokedex.pokedex.entities.Pokemon;
import com.projeto.pokedex.ui.activity.InfoPokemonActivity;
import com.projeto.pokedex.util.ConstantUtil;
import com.projeto.pokedex.util.FormatUtil;

import java.util.ArrayList;
import java.util.List;

public class ListaPokemonAdapter extends RecyclerView.Adapter<ListaPokemonAdapter.ViewHolder> {

    private List<Pokemon> listaPokemon;
    private Context context;
    private Activity activity;

    public ListaPokemonAdapter(Activity activity) {
        this.activity = activity;
        this.context = activity.getApplicationContext();
        this.listaPokemon = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_pokemon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Pokemon pokemon = listaPokemon.get(position);
        holder.nomeView.setText(FormatUtil.getNomePokemonFormatado(pokemon));
        getImageFromApi(holder, pokemon);
        holder.container.setOnClickListener(view -> {
            startActivityInfoPokemon(pokemon);
        });
    }

    private void startActivityInfoPokemon(Pokemon pokemon) {
        Intent intent = new Intent(activity, InfoPokemonActivity.class);
        intent.putExtra(ConstantUtil.EXTRA_POKEMON, pokemon);
        activity.startActivity(intent);
    }

    private void getImageFromApi(@NonNull ViewHolder holder, Pokemon pokemon) {
        Glide.with(context)
                .load(ConstantUtil.IMAGE_BASE_URL + pokemon.getNumber() + ".png")
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imagemView);
    }

    @Override
    public int getItemCount() {
        return listaPokemon.size();
    }

    public void adicionaLista(ArrayList<Pokemon> lista) {
        if (lista != null) {
            this.listaPokemon.addAll(lista);
            notifyDataSetChanged();
        } else {
            Log.i(ConstantUtil.POKEDEX_ERRO, lista.toString());
        }
    }

    public void adicionaListaDeBusca(ArrayList<Pokemon> lista) {
        limpaLista();
        adicionaLista(lista);
    }

    public void limpaLista() {
        this.listaPokemon.removeAll(listaPokemon);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imagemView, container;
        private TextView nomeView;

        public ViewHolder(View view) {
            super(view);
            imagemView = view.findViewById(R.id.item_imagem_pokemon);
            nomeView = view.findViewById(R.id.placeholder_nome_pokemon);
            container = view.findViewById(R.id.item_container_pokemon);
        }
    }
}