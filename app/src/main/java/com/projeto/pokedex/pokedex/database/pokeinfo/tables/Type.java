package com.projeto.pokedex.pokedex.database.pokeinfo.tables;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tb_type")
public class Type {
    @PrimaryKey
    private int idTipo;
    private String nomeTipo;

    public Type(int idTipo, String nomeTipo) {
        this.idTipo = idTipo;
        this.nomeTipo = nomeTipo;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public String getNomeTipo() {
        return nomeTipo;
    }

    public void setNomeTipo(String nomeTipo) {
        this.nomeTipo = nomeTipo;
    }
}
