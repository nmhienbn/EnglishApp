package views;

import controllers.DictionaryManagement;
import controllers.googleapi.GoogleTranslate;
import models.Word;

import java.io.*;
import java.util.*;

public class TestAPI {
    public static void Init() {
        LoadDict(false);
        LoadFavoriteWords();
    }

    public static void Shutdown() {
        SaveDict();
        SaveFavoriteWords();
    }


    private static final HashSet<String> favoriteWords = new HashSet<>();
    private static final DictionaryManagement wordSet = DictionaryManagement.getInstance();
    private static final String favoriteWordsPath = TestAPI.class.getClassLoader().getResource("models/favorite_words.txt").getPath();
    private static final LinkedList<String> search_history = new LinkedList<>();
    private static final int MAX_SEARCH_HISTORY = 50;

    private static void LoadFavoriteWords() {
        //TODO load favorite words from file

        try (Scanner cin = new Scanner(new FileReader(favoriteWordsPath))) {
            while (cin.hasNextLine()) {
                String word = cin.nextLine();
                favoriteWords.add(word);
            }
            cin.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
    }

    private static void SaveFavoriteWords() {
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
        //TODO save favorite words to file
    }

    public static boolean isFavoriteWord(String word) {
        return favoriteWords.contains(word);
    }

    public static void addFavoriteWord(String word) {
        favoriteWords.add(word);
    }

    public static void removeFavoriteWord(String word) {
        favoriteWords.remove(word);
    }

    public static void clearFavoriteWords() {
        favoriteWords.clear();
    }

    public static void addSearchHistory(String word) {
        for (String s : search_history) {
            if (s.equals(word)) {
                search_history.remove(s);
                break;
            }
        }
        search_history.addFirst(word);
        if (search_history.size() > MAX_SEARCH_HISTORY) search_history.removeLast();
    }

    public static Iterable<String> SearchHistory() {
        return search_history;
    }

    public static void clearSearchHistory() {
        search_history.clear();
    }

    public static void resetDictionaryData() {
        clearFavoriteWords();
        clearSearchHistory();
        wordSet.dictionaryClear();
        wordSet.dictionaryInsertFromFile(wordSet.ORIGINAL_DICTIONARY_PATH);
    }

    public static ArrayList<String> getword(String key) {
        ArrayList<Word> words;
        if (key.equals("@all"))
            words = wordSet.getAllWords();
        else
            words = wordSet.dictionarySearcher(key);

        ArrayList<String> wordstr = new ArrayList<>();
        for (Word word : words) {
            wordstr.add(word.getWordTarget());
        }

        return wordstr;
    }

    public static String getWordMeaning(String word) {
        Word WORD = wordSet.dictionaryLookup(word);
        return WORD.getWordExplain();
    }

    public static String TranslateAPI(String input, String source, String target) {
        try {
            return GoogleTranslate.translate(input, source, target);
        } catch (Exception e) {
            return "ERROR:: Cannot translate";
        }
    }

    public static void SpeakAPI(String text, String lang) {
        if (text == null || text.isEmpty()) return;
        try {
            System.out.println("start speak: " + text);
            wordSet.speak(text, lang);
            System.out.println("end speak: " + text);
        } catch (Exception e) {
            System.out.println("SPEAK API ERROR");
        }
    }

    public static boolean dictionaryContainWord(String word) {
        return wordSet.dictionaryLookup(word) != null;
    }

    public static boolean testAddWord(String word, String meaning) {
        if (wordSet.dictionaryLookup(word) != null) {
            System.out.println("Word already exists");
            return false;
        }
        return wordSet.dictionaryAddWord(word, meaning);
    }

    public static void testEditWord(String word, String meaning) {
        if (wordSet.dictionaryLookup(word) == null) {
            System.out.println("Word does not exists");
            return;
        }
        wordSet.dictionaryEditWord(word, meaning);
    }

    public static void testRemoveWord(String word) {
        if (wordSet.dictionaryLookup(word) == null) {
            System.out.println("Word does not exists");
            return;
        }
        wordSet.dictionaryRemoveWord(word);
    }

    public static void LoadDict(boolean isOriginal) {
        //wordSet.dictionaryInsertFromFile(wordSet.ORIGINAL_DICTIONARY_PATH);
        if (isOriginal) {
            System.out.println("Load default dict: " + wordSet.ORIGINAL_DICTIONARY_PATH);
            wordSet.dictionaryInsertFromFile(wordSet.ORIGINAL_DICTIONARY_PATH);
        } else {
            System.out.println("Load default dict: " + wordSet.DEFAULT_DICTIONARY_PATH);
            wordSet.dictionaryInsertFromFile(wordSet.DEFAULT_DICTIONARY_PATH);
        }
    }


    public static void SaveDict() {
        wordSet.dictionaryExportToFile(wordSet.DEFAULT_DICTIONARY_PATH);
    }

}
