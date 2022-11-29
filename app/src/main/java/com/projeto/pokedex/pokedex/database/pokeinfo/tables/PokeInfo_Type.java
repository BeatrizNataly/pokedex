package com.projeto.pokedex.pokedex.database.pokeinfo.tables;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "tb_pokeInfo_Type",
        foreignKeys = {@ForeignKey(entity = Type.class,
                parentColumns = "idTipo", childColumns = {"fk_type"}, onUpdate = ForeignKey.CASCADE),
                @ForeignKey(entity = PokeInfo.class, parentColumns = "id", childColumns = "fk_pokeInfo", onUpdate = ForeignKey.CASCADE)
        })
public class PokeInfo_Type {
    @PrimaryKey(autoGenerate = true)
    private int id_pokeInfo_Type;
    private int fk_pokeInfo;
    private int fk_type;

    public PokeInfo_Type(int fk_pokeInfo, int fk_type) {
        this.fk_pokeInfo = fk_pokeInfo;
        this.fk_type = fk_type;
    }

    public int getId_pokeInfo_Type() {
        return id_pokeInfo_Type;
    }

    public void setId_pokeInfo_Type(int id_pokeInfo_Type) {
        this.id_pokeInfo_Type = id_pokeInfo_Type;
    }

    public int getFk_pokeInfo() {
        return fk_pokeInfo;
    }

    public void setFk_pokeInfo(int fk_pokeInfo) {
        this.fk_pokeInfo = fk_pokeInfo;
    }

    public int getFk_type() {
        return fk_type;
    }

    public void setFk_type(int fk_type) {
        this.fk_type = fk_type;
    }
}
