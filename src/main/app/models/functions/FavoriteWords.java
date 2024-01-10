package models.functions;

import models.DictFacade;

import java.io.*;
import java.util.HashSet;

public class FavoriteWords {
    private static final String favoriteWordsPath = DictFacade.class.getClassLoader().getResource("models/favorite_words.txt").getPath();
    private static final HashSet<String> favoriteWords = new HashSet<>();
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

    public void SaveFavoriteWords() {
        try {
            PrintStream fileOutputStream = new PrintStream(new FileOutputStream(favoriteWordsPath));
            System.setOut(fileOutputStream); // Redirect System.out to the file

            for (String word : favoriteWords) {
                System.out.println(word);
            }
            // Close the fileOutputStream
            fileOutputStream.close();
            // You can restore the original System.out like this:
            System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Favorite words has been exported to file!");
    }

    public boolean isFavoriteWord(String word) {
        return favoriteWords.contains(word);
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
