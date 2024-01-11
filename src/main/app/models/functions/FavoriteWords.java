package models.functions;

import models.DictFacade;

import java.io.*;
import java.util.HashSet;
import java.util.TreeSet;

public class FavoriteWords {
    private static final String favoriteWordsPath = DictFacade.class.
            getClassLoader().getResource("models/favorite_words.txt").getPath();
    private static final TreeSet<String> favoriteWords = new TreeSet<>();
    private static FavoriteWords ins = null;

    private FavoriteWords() {
    }

    public static FavoriteWords getInstance() {
        if (ins == null) {
            ins = new FavoriteWords();
        }
        return ins;
    }

    public void LoadFavoriteWords() {
        try (BufferedReader cin = new BufferedReader(new FileReader(favoriteWordsPath))) {
            String word;
            while ((word = cin.readLine()) != null) {
                favoriteWords.add(word);
            }
            cin.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    public synchronized void SaveFavoriteWords() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(favoriteWordsPath))) {
            for (String word : favoriteWords) {
                writer.println(word);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Favorite words has been exported to file!");
    }

    public boolean isFavoriteWord(String word) {
        return favoriteWords.contains(word);
    }

    public TreeSet<String> getFavoriteWords() {
        return favoriteWords;
    }

    public void addFavoriteWord(String word) {
        favoriteWords.add(word);
    }

    public void removeFavoriteWord(String word) {
        favoriteWords.remove(word);
    }

    public void clearFavoriteWords() {
        favoriteWords.clear();
    }

}
