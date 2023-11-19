package models;

import java.util.ArrayList;
import java.util.Scanner;

public class Dictionary implements DictionaryInterface {

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

    Trie trie;

    Dictionary() {
        trie = new Trie();
    }

    @Override
    public boolean addWord(String str, String meaning) {
        return trie.addWord(str, meaning);
    }

    @Override
    public boolean editWord(String str, String meaning) {
        return trie.editWord(str, meaning);
    }

    @Override
    public boolean deleteWord(String str) {
        return trie.deleteWord(str);
    }

    @Override
    public ArrayList<Word> queryAllWords() {
        return trie.queryAllWords();
    }

    @Override
    public ArrayList<Word> getProposedString(String str) {
        return trie.getProposedString(str);
    }

    @Override
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
