import controllers.DictionaryManagement;
import models.Dictionary;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class TestDict {
    public static Dictionary words = Dictionary.getInstance();
    private static DictionaryManagement wordSet = new DictionaryManagement();

    public static void main(String[] args) {
        wordSet.insertFromFile();
        words.findWord("bubble");
//        wordSet.printProposedString("algo");
        words.deleteWord("Bubble");
        words.findWord("buBble");
        Scanner cin = new Scanner(System.in);
        while (true) {
            words.printProposedString(cin.next());
        }
//        wordSet.dictionarySearcher();
//        wordSet.dictionaryExportToFile("D:\\App\\src\\out_dictionaries.txt");
    }

}