package models;

import java.util.ArrayList;
import java.util.Scanner;

public class Dictionary {

    private static Dictionary instance = null;

    /**
     * Singleton.
     */
    public static Dictionary getInstance() {
        if (instance == null) {
            instance = new Dictionary();
        }
        return instance;
    }

    private Trie trie = null;

    private Dictionary() {
        trie = new Trie();
    }

    /**
     * Add a word to dictionary.
     *
     * @param str string to be added.
     * @return true if the word is added successfully.
     */
    public boolean addWord(String str, String meaning) {
        return trie.addWord(str, meaning);
    }

    /**
     * Edit a word in dictionary.
     *
     * @param str     string to be edited.
     * @param meaning meaning of edited word
     * @return true if the word is successful edited
     */
    public boolean editWord(String str, String meaning) {
        return trie.editWord(str, meaning);
    }

    /**
     * Delete a word from dictionary.
     *
     * @param str string to be deleted.
     * @return true if the word is deleted successfully.
     */
    public boolean deleteWord(String str) {
        return trie.deleteWord(str);
    }

    /**
     * Get all words from dictionary.
     */
    public ArrayList<Word> queryAllWords() {
        return trie.queryAllWords();
    }

    /**
     * Get all words with prefix is str in dictionary.
     *
     * @param str string to search.
     */
    public ArrayList<Word> getProposedString(String str) {
        return trie.getProposedString(str);
    }

    /**
     * Return the Word that its target is str in dictionary.
     *
     * @param str string to lookup.
     */
    public Word lookupWord(String str) {
        return trie.lookupWord(str);
    }

    /**
     * Export all words to System.out.
     * Format: "{English meaning}" + "{Vietnamese meaning}"
     */
    public void exports() {
        ArrayList<Word> words = trie.queryAllWords();

        for (Word word : words) {
            System.out.printf("%s\t%s\n", word.getWordTarget(), word.getWordExplain().replaceAll("\n", "\\\\"));
        }
    }

    /**
     * Import all words from Scanner.
     * Format: "{English meaning}" + "{Vietnamese meaning}"
     */
    public void imports(Scanner cin) {
        String line;
        while (cin.hasNextLine()) {
            line = cin.nextLine();
            String[] tmp = line.split("\t", 2);
            addWord(tmp[0], tmp[1].replaceAll("\\\\", "\n"));
        }
    }

    /**
     * Clear all data from dictionary.
     */
    public void clear() {
        trie.clear();
    }
}
