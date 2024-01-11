package models;

import models.functions.DictionaryManagement;
import models.functions.FavoriteWords;
import models.functions.GoogleTranslate;
import models.functions.HistoryWords;
import models.databases.Word;

import java.util.*;
import java.util.concurrent.ExecutorService;

public class DictFacade {
    public static void Init() {
        ExecutorService executorService = java.util.concurrent.Executors.newFixedThreadPool(3);
        executorService.submit(() -> {
            LoadDict(false);
        });
        executorService.submit(favoriteWords::LoadFavoriteWords);
        executorService.submit(historyWords::LoadSearchedWords);
        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, java.util.concurrent.TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void Shutdown() {
        ExecutorService executorService = java.util.concurrent.Executors.newFixedThreadPool(3);
        executorService.submit(DictFacade::SaveDict);
        executorService.submit(favoriteWords::SaveFavoriteWords);
        executorService.submit(historyWords::SaveSearchedWords);
        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, java.util.concurrent.TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static final DictionaryManagement wordSet = DictionaryManagement.getInstance();
    private static final FavoriteWords favoriteWords = FavoriteWords.getInstance();
    private static final HistoryWords historyWords = HistoryWords.getInstance();

    public static class Favourite {
        public static boolean check(String word) {
            return FavoriteWords.getInstance().isFavoriteWord(word);
        }

        public static TreeSet<String> getAll() {
            return FavoriteWords.getInstance().getFavoriteWords();
        }

        public static void add(String word) {
            FavoriteWords.getInstance().addFavoriteWord(word);
        }

        public static void remove(String word) {
            FavoriteWords.getInstance().removeFavoriteWord(word);
        }

        public static void clear() {
            FavoriteWords.getInstance().clearFavoriteWords();
        }
    }

    public static class History {
        public static void add(String word) {
            HistoryWords.getInstance().add(word);
        }

        public static Iterable<String> toIterable() {
            return HistoryWords.getInstance().SearchHistory();
        }

        public static void clear() {
            HistoryWords.getInstance().clear();
        }
    }

    public static void resetDictionaryData() {
        Favourite.clear();
        History.clear();
        wordSet.dictionaryClear();
        wordSet.dictionaryInsertFromFile(wordSet.ORIGINAL_DICTIONARY_PATH);
    }

    public static synchronized void SaveDict() {
        wordSet.dictionaryExportToFile(wordSet.DEFAULT_DICTIONARY_PATH);
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

    public static class Dict {
        public static ArrayList<String> getWordsWithPrefix(String key) {
            ArrayList<Word> words;
            if (key.equals("@all"))
                words = wordSet.getAllWords();
            else
                words = wordSet.dictionarySearcher(key);

            ArrayList<String> wordStr = new ArrayList<>();
            for (Word word : words) {
                wordStr.add(word.getWordTarget());
            }

            return wordStr;
        }

        public static String getMeaning(String word) {
            Word WORD = wordSet.dictionaryLookup(word);
            return WORD.getWordExplain();
        }

        public static String translate(String input, String source, String target) {
            try {
                return GoogleTranslate.translate(input, source, target);
            } catch (Exception e) {
                return "ERROR:: Cannot translate";
            }
        }

        public static void speak(String text, String lang) {
            if (text == null || text.isEmpty()) return;
            try {
                System.out.println("start speak: " + text);
                GoogleTranslate.speak(text, lang);
                System.out.println("end speak: " + text);
            } catch (Exception e) {
                System.out.println("SPEAK API ERROR");
            }
        }

        public static boolean contains(String word) {
            return wordSet.dictionaryLookup(word) != null;
        }

        public static boolean add(String word, String meaning) {
            if (wordSet.dictionaryLookup(word) != null) {
                System.out.println("Word already exists");
                return false;
            }
            return wordSet.dictionaryAddWord(word, meaning);
        }

        public static void edit(String word, String meaning) {
            if (wordSet.dictionaryLookup(word) == null) {
                System.out.println("Word does not exists");
                return;
            }
            wordSet.dictionaryEditWord(word, meaning);
        }

        public static void remove(String word) {
            if (wordSet.dictionaryLookup(word) == null) {
                System.out.println("Word does not exists");
                return;
            }
            wordSet.dictionaryRemoveWord(word);
        }
    }
}
