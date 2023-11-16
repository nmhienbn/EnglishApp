package views;

import controllers.DictionaryManagement;
import controllers.googleapi.GoogleTranslate;
import models.Word;

import java.util.ArrayList;
import java.util.List;

public class TestAPI {
    private static DictionaryManagement wordSet = DictionaryManagement.getInstance();

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
            wordSet.speek(text, lang);
            System.out.println("end speak: " + text);
        } catch (Exception e) {
            System.out.println("SPEAK API ERROR");
        }
    }

    public static boolean dictionaryContainWord(String word) {
        return wordSet.dictionaryLookup(word) != null;
    }

    public static void testAddWord(String word, String meaning) {
        if (wordSet.dictionaryLookup(word) != null) {
            System.out.println("Word already exists");
            return;
        }
        wordSet.dictionaryAddWord(word, meaning);
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
