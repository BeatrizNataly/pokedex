package com.projeto.pokedex.pokedex.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.projeto.pokedex.util.FormatUtil;

import java.io.Serializable;

@Entity(tableName = "tb_pokemon")
public class Pokemon implements Serializable {
    @PrimaryKey
    private int number;
    private String name;
    private String url;

    public String getUrl() {
        String subUrl = "pokemon-form/" + this.getNumber();
        return subUrl;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String nome) {
        this.name = nome;
    }

    public int getNumber() {
        this.number = FormatUtil.getSlashedUrl(url);
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

}
