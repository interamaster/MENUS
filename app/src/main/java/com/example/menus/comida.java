package com.example.menus;

public class comida {
    private final String name;

    private boolean isFavorite = false;


    public comida(String name ) {
        this.name = name;

    }

    public String getName() {
        return name;
    }


    public boolean getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public void toggleFavorite() {
        isFavorite = !isFavorite;
    }


}
