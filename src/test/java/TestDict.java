import controllers.DictionaryManagement;
import models.Dictionary;
import views.DictionaryCommandline;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class TestDict {
    private static DictionaryManagement wordSet = new DictionaryManagement();
    private static DictionaryCommandline cmd = new DictionaryCommandline();

    public static void main(String[] args) {
        wordSet.insertFromFile();
        cmd.showLookupWord("bubble");
        wordSet.dictionarySearcher("c");
//        wordSet.printProposedString("algo");
        wordSet.dictionaryRemoveWord("Bubble");
        cmd.showLookupWord("buBble");
        Scanner cin = new Scanner(System.in);
        while (true) {
            cmd.showLookupWord(cin.next());
        }
//        wordSet.dictionarySearcher();
//        wordSet.dictionaryExportToFile("D:\\App\\src\\out_dictionaries.txt");
    }

}