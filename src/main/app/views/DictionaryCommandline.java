package views;

import controllers.DictionaryManagement;
import models.Trie;
import models.Word;

import java.util.ArrayList;
import java.util.Scanner;

public class DictionaryCommandline {
    DictionaryManagement manager = DictionaryManagement.getInstance();

    /**
     * Insert n words from command line.
     */
    public void insertFromCommandline() {
        Scanner cin = new Scanner(System.in);
        int n = cin.nextInt();
        for (int i = 0; i < n; i++) {
            String word = cin.nextLine();
            String explain = cin.nextLine();
            manager.dictionaryAddWord(word, explain);
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
     * Print all meaning of word str.
     *
     * @param str string for looking up
     */
    public void showLookupWord(String str) {
        Word proposedString = manager.dictionaryLookup(str);

        System.out.println("Lookup results:");
        if (proposedString == null) {
            System.out.println("No results founded!");
        } else {
            System.out.println(proposedString.getWordExplain());
        }
    }

    /**
     * Print all strings having str as a prefix.
     *
     * @param str string to search
     */
    public void showSearchedWords(String str) {
        ArrayList<Word> proposedString = manager.dictionarySearcher(str);

        System.out.println("Searched results:");
        if (proposedString.isEmpty()) {
            System.out.println("No result founded!");
        }

        for (Word word : proposedString) {
            System.out.println("- " + word.getWordTarget());
        }
    }

    /**
     * Print all the words in the dictionary to System.out.
     */
    public void showAllWords() {
        ArrayList<Word> proposedString = manager.getAllWords();

        int maxLen = 0;
        for (Word word : proposedString) {
            maxLen = Math.max(maxLen, word.getWordTarget().length());
        }

        // head
        String s1 = "No";
        String s2 = "English";
        String s3 = "Vietnamese";
        System.out.printf("%-8s| %-" + maxLen + "s | %s\n", s1, s2, s3);

        for (int i = 0; i < proposedString.size(); ++i) {
            System.out.printf("%-8d| %-" + maxLen + "s | %s\n", i + 1,
                    proposedString.get(i).getWordTarget(),
                    proposedString.get(i).getWordExplain());
        }
    }
}
