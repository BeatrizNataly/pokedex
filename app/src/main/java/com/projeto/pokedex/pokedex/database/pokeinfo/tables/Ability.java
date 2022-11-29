package com.projeto.pokedex.pokedex.database.pokeinfo.tables;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tb_ability")
public class Ability {
    @PrimaryKey
    private int id;
    private String nomeAbility;

    public Ability(int id, String nomeAbility) {
        this.id = id;
        this.nomeAbility = nomeAbility;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeAbility() {
        return nomeAbility;
    }

    public void setNomeAbility(String nomeAbility) {
        this.nomeAbility = nomeAbility;
    }
}
