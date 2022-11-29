package com.projeto.pokedex.pokedex.database.pokeinfo.tables;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tb_pokeInfo")
public class PokeInfo {
    @PrimaryKey
    private int id;
    private int height;
    private int weight;
    private String name;

    public PokeInfo(int id, int height, int weight, String name) {
        this.id = id;
        this.height = height;
        this.weight = weight;
        this.name = name;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
