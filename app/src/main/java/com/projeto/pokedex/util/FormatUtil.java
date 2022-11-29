package com.projeto.pokedex.util;

import com.projeto.pokedex.pokedex.entities.Pokemon;

public class FormatUtil {

    public static String formatMedida(int atributo, String medida) {
        String format = "" + atributo;
        if (format.length() == 1)
            return "0," + atributo + medida;
        return format.substring(0, format.length() - 1) + "," + format.charAt(format.length() - 1) + medida;
    }

    public static int getSlashedUrl(String url) {
        String[] slashedUrl = url.split("/");
        return Integer.parseInt(slashedUrl[slashedUrl.length - 1]);
    }

    public static String getNomePokemonFormatado(Pokemon pokemon) {
        String nomePokemon = pokemon.getName();
        nomePokemon = nomePokemon.substring(0, 1).toUpperCase() + nomePokemon.substring(1);
        return nomePokemon;
    }
}
