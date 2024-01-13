package models.facades;

import models.databases.Word;
import models.functions.DictionaryManagement;
import models.functions.GoogleTranslate;

import java.util.ArrayList;

public class WordListFacade {
    static final DictionaryManagement wordSet = DictionaryManagement.getInstance();

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
