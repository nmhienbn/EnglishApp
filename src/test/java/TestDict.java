import controllers.DictionaryManagement;
import models.Dictionary;
import models.Trie;
import views.DictionaryCommandline;

import java.util.Scanner;

public class TestDict {
    private static final DictionaryManagement wordSet = new DictionaryManagement();
    private static final DictionaryCommandline cmd = new DictionaryCommandline();
    private static final Dictionary dict = Dictionary.getInstance();

    private static void manyTests(){
        while (true) {
            cmd.lookupWord();
        }
    }

    public static void main(String[] args) {
        wordSet.dictionaryInsertFromFile(null);
//        cmd.showSearchedWords("bubble");
//        cmd.showLookupWord("bubble");
//        System.out.println(dict.lookupWord("bubble"));
//        wordSet.dictionarySearcher("c");
//        wordSet.dictionaryRemoveWord("Bubble");
//        cmd.showLookupWord("buBble");

//        manyTests();

//        wordSet.dictionarySearcher();
//        wordSet.dictionaryExportToFile("D:\\App\\src\\out_dictionaries.txt");
    }

}