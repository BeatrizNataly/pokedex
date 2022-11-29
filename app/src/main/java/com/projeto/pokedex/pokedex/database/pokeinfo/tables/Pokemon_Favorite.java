package com.projeto.pokedex.pokedex.database.pokeinfo.tables;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tb_pokemon_favorite")
public class Pokemon_Favorite {
    @PrimaryKey
    int id;
    int favorite = 0; //default false

    public Pokemon_Favorite(int id, int favorite) {
        this.id = id;
        this.favorite = favorite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }
}
