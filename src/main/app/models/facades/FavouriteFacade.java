package models.facades;

import models.functions.FavoriteWords;

import java.util.TreeSet;

public class FavouriteFacade {
    static final FavoriteWords favoriteWords = FavoriteWords.getInstance();

    public static boolean check(String word) {
        return favoriteWords.isFavoriteWord(word);
    }

    public static TreeSet<String> getAll() {
        return favoriteWords.getFavoriteWords();
    }

    public static void add(String word) {
        favoriteWords.addFavoriteWord(word);
    }

    public static void remove(String word) {
        favoriteWords.removeFavoriteWord(word);
    }

    public static void clear() {
        favoriteWords.clearFavoriteWords();
    }
}
