package com.projeto.pokedex.pokedex.database.pokeinfo.tables;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "tb_pokeInfo_Ability",
        foreignKeys = {@ForeignKey(entity = Ability.class,
                parentColumns = "id", childColumns = {"fk_ability"}, onUpdate = ForeignKey.CASCADE),
                @ForeignKey(entity = PokeInfo.class, parentColumns = "id", childColumns = "fk_pokeInfo", onUpdate = ForeignKey.CASCADE)
        })
public class PokeInfo_Ability {
    @PrimaryKey(autoGenerate = true)
    private int id_pokeInfo_Ability;
    private int fk_pokeInfo;
    private int fk_ability;

    public PokeInfo_Ability(int fk_pokeInfo, int fk_ability) {
        this.fk_pokeInfo = fk_pokeInfo;
        this.fk_ability = fk_ability;
    }

    public int getId_pokeInfo_Ability() {
        return id_pokeInfo_Ability;
    }

    public void setId_pokeInfo_Ability(int id_pokeInfo_Ability) {
        this.id_pokeInfo_Ability = id_pokeInfo_Ability;
    }

    public int getFk_pokeInfo() {
        return fk_pokeInfo;
    }

    public void setFk_pokeInfo(int fk_pokeInfo) {
        this.fk_pokeInfo = fk_pokeInfo;
    }

    public int getFk_ability() {
        return fk_ability;
    }

    public void setFk_ability(int fk_ability) {
        this.fk_ability = fk_ability;
    }
}
