package views;

import controllers.DictionaryManagement;
import controllers.googleapi.GoogleTranslate;
import models.Word;

import java.util.ArrayList;
import java.util.List;

public class TestAPI {
    private static DictionaryManagement wordSet = DictionaryManagement.getInstance();

    public static void SetupDict() {
        wordSet.insertFromFile();
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

    public static String translateVE(String input) {
        try {
            return GoogleTranslate.translate(input, "vi", "en");
        } catch (Exception e) {
            return "ERROR:: Cannot translate";
        }
    }

    public static String translateEV(String input) {
        try {
            return GoogleTranslate.translate(input, "en", "vi");
        } catch (Exception e) {
            return "ERROR:: Cannot translate";
        }
    }

    public static String TranslateAPI(String input, String source, String target) {
        try {
            return GoogleTranslate.translate(input, source, target);
        } catch (Exception e) {
            return "ERROR:: Cannot translate";
        }
    }

}
