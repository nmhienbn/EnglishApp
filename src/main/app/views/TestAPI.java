package views;

import controllers.DictionaryManagement;
import models.Word;

import java.util.ArrayList;
import java.util.List;

public class TestAPI {
    private static DictionaryManagement wordSet = new DictionaryManagement();

    public static void SetupDict() {
        wordSet.insertFromFile();
    }

    public static ArrayList<String> getword(String key) {
        ArrayList<Word> words = wordSet.dictionarySearcher(key);

        ArrayList<String> wordstr = new ArrayList<>();
        for (int i = 0; i < words.size(); i++) {
            wordstr.add(words.get(i).toString());
        }

        return wordstr;
    }
}
