package com.projeto.pokedex.util;

import android.graphics.Color;

public class ColorUtil {

    public static int getBackgroundColor(int type) {
        switch (type) {
            case 1:
                return Color.parseColor(ConstantUtil.NORMAL);
            case 2:
                return Color.parseColor(ConstantUtil.FIGHTING);
            case 3:
                return Color.parseColor(ConstantUtil.FLYING);
            case 4:
                return Color.parseColor(ConstantUtil.POISON);
            case 5:
                return Color.parseColor(ConstantUtil.GROUND);
            case 6:
                return Color.parseColor(ConstantUtil.ROCK);
            case 7:
                return Color.parseColor(ConstantUtil.BUG);
            case 8:
                return Color.parseColor(ConstantUtil.GHOST);
            case 9:
                return Color.parseColor(ConstantUtil.STEEL);
            case 10:
                return Color.parseColor(ConstantUtil.FIRE);
            case 11:
                return Color.parseColor(ConstantUtil.WATER);
            case 12:
                return Color.parseColor(ConstantUtil.GRASS);
            case 13:
                return Color.parseColor(ConstantUtil.ELETRIC);
            case 14:
                return Color.parseColor(ConstantUtil.PSYCHIC);
            case 15:
                return Color.parseColor(ConstantUtil.ICE);
            case 16:
                return Color.parseColor(ConstantUtil.DRAGON);
            case 17:
                return Color.parseColor(ConstantUtil.DARK);
            case 18:
                return Color.parseColor(ConstantUtil.FAIRY);
            case 10001:
                return Color.parseColor(ConstantUtil.UNKNOWN);
            case 10002:
                return Color.parseColor(ConstantUtil.SHADOW);
            default:
        }
        return Color.CYAN;
    }

    public static int getForegroundViewColor(int type) {
        if (type == 1 || type == 7 || type == 9 || type == 12 || type == 13 || type == 15) {
            return Color.BLACK;
        }
        return Color.WHITE;
    }
}
