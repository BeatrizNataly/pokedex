package com.projeto.pokedex.recyclerview.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.projeto.pokedex.R;
import com.projeto.pokedex.pokedex.database.pokeinfo.tables.Ability;
import com.projeto.pokedex.pokedex.entities.PokemonInfo;
import com.projeto.pokedex.util.ConstantUtil;

import java.util.ArrayList;

public class PokemonAbilityAdapter extends RecyclerView.Adapter<PokemonAbilityAdapter.ViewHolderAbility> {
    private ArrayList<PokemonInfo.Ability> listaAbility;
    private ArrayList<Ability> listaAbilityDB;
    private Activity activity;
    private Context context;

    public PokemonAbilityAdapter(Activity activity) {
        this.activity = activity;
        this.context = activity.getApplicationContext();
        this.listaAbility = new ArrayList<>();
        this.listaAbilityDB = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolderAbility onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_info, parent, false);
        return new ViewHolderAbility(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAbility holder, int position) {
        if (listaAbility.isEmpty())
            holder.abilityTextView.setText(listaAbilityDB.get(position).getNomeAbility().toUpperCase());
        else
            holder.abilityTextView.setText(listaAbility.get(position).getAbilityInfo().getName().toUpperCase());
    }

    @Override
    public int getItemCount() {
        if (listaAbility.isEmpty())
            return listaAbilityDB.size();
        return listaAbility.size();
    }

    public void adicionaListaAbilities(ArrayList<PokemonInfo.Ability> lista) {
        if (lista != null) {
            this.listaAbility.addAll(lista);
            notifyDataSetChanged();
        } else {
            Log.i(ConstantUtil.POKEDEX_ERRO, lista.toString());
        }
    }

    public void adicionaListaDoDatabase(ArrayList<Ability> lista) {
        if (lista != null) {
            this.listaAbilityDB.addAll(lista);
            notifyDataSetChanged();
        } else {
            Log.i(ConstantUtil.POKEDEX_ERRO, lista.toString());
        }
    }

    class ViewHolderAbility extends RecyclerView.ViewHolder {
        private TextView abilityTextView;

        public ViewHolderAbility(View view) {
            super(view);
            abilityTextView = view.findViewById(R.id.item_topic_texto);
        }
    }
}