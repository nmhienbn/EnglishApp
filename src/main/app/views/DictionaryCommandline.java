package views;

import models.Dictionary;

import java.util.Scanner;

public class DictionaryCommandline {
    Dictionary dictionary = Dictionary.getInstance();
    /**
     * Show all words as a table.
     */
    public void showAllWords() {
        String s1 = "No";
        String s2 = "English";
        String s3 = "Vietnamese";
        System.out.printf("%-8s| %-" + dictionary.wordMaxLen + "s | %s\n", s1, s2, s3);
        dictionary.printAllWords();
    }

    /**
     * Insert n words from command line.
     */
    public void insertFromCommandline() {
        Scanner cin = new Scanner(System.in);
        int n = cin.nextInt();
        for (int i = 0; i < n; i++) {
            String word = cin.nextLine();
            String explain = cin.nextLine();
            dictionary.addWord(word, explain);
        }
    }

    /**
     * Insert n words from command line.
     * Then show all words in dictionary.
     */
    public void dictionaryBasic() {
        insertFromCommandline();
        showAllWords();
    }

    /**
     * Print all strings having inputted string from stdin as a prefix.
     */
    public void dictionarySearcher() {
        Scanner cin = new Scanner(System.in);
        dictionary.printProposedString(cin.next());
    }
}
