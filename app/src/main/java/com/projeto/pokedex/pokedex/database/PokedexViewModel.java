package com.projeto.pokedex.pokedex.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.projeto.pokedex.pokedex.database.pokeinfo.tables.Ability;
import com.projeto.pokedex.pokedex.database.pokeinfo.tables.PokeInfo;
import com.projeto.pokedex.pokedex.database.pokeinfo.tables.PokeInfo_Ability;
import com.projeto.pokedex.pokedex.database.pokeinfo.tables.PokeInfo_Type;
import com.projeto.pokedex.pokedex.database.pokeinfo.tables.Pokemon_Favorite;
import com.projeto.pokedex.pokedex.database.pokeinfo.tables.Type;
import com.projeto.pokedex.pokedex.entities.Pokemon;

import java.util.List;

public class PokedexViewModel extends AndroidViewModel {
    PokedexDao dao;

    public PokedexViewModel(@NonNull Application application) {
        super(application);
        PokedexDatabase database = PokedexDatabase.getInstance(application.getApplicationContext());
        dao = database.getPokedexDao();
    }

    //Pokemon
    public List<Pokemon> getPokemonsDoDatabase() {
        return dao.getPokemonsDoDatabase();
    }

    public void addAllPokemonsToDatabase(List<Pokemon> pokemons) {
        dao.addAllPokemons(pokemons);
    }

    public List<Pokemon> searchPokemonsFromDatabase(String pokemon) {
        return dao.searchPokemonsFromDatabase(pokemon);
    }

    public LiveData<List<Pokemon>> getFavoritePokemons() {
        return dao.getFavoritePokemonsDoDatabase();
    }

    //PokeInfo
    public void addPokeInfoToDatabase(PokeInfo pokeInfo) {
        dao.addPokeInfo(pokeInfo);
    }

    public PokeInfo getPokeInfoFromDatabase(int id) {
        return dao.getPokeInfoFromDatabase(id);
    }

    //Type
    public void addTypeToDatabase(Type type) {
        dao.addType(type);
    }

    public LiveData<List<Type>> getPokemonTypesFromDatabase(int idPokemon) {
        return dao.getTypesFromDatabase(idPokemon);
    }

    //PokeInfo_Type
    public void addRelationPokeInfoType(PokeInfo_Type pokeInfo_type) {
        dao.addRelationPokeInfoType(pokeInfo_type);
    }

    //Ability
    public void addAbilityToDatabase(Ability ability) {
        dao.addAbility(ability);
    }

    public LiveData<List<Ability>> getPokemonAbilitiesFromDatabase(int idPokemon) {
        return dao.getAbilitiesFromDatabase(idPokemon);
    }

    //PokeInfo_Ability
    public void addRelationPokeInfoAbility(PokeInfo_Ability pokeInfo_ability) {
        dao.addRelationPokeInfoAbility(pokeInfo_ability);
    }

    //Pokemon_Favorite
    public void addRelationPokemonFavorite(Pokemon_Favorite pokemonFavorite) {
        dao.addRelationPokemonFavorite(pokemonFavorite);
    }

    public int getIsPokemonFavorite(int id) {
        return dao.getIsPokemonFavorite(id);
    }

    public LiveData<List<Pokemon>> searchFavoritePokemons(String busca) {
        return dao.searchFavoritePokemonsDoDatabase(busca);
    }
}
