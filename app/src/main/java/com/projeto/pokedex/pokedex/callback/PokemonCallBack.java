package com.projeto.pokedex.pokedex.callback;

import com.projeto.pokedex.pokedex.entities.Pokemon;

import java.util.ArrayList;

public class PokemonCallBack {
    private ArrayList<Pokemon> results;

    public ArrayList<Pokemon> getResults() {
        return results;
    }

    public void setResults(ArrayList<Pokemon> callBackPokemonList) {
        this.results = callBackPokemonList;
    }
}