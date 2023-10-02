package models;

public interface DictionaryInterface {
    /**
     * Add a word to Trie.
     *
     * @param str string to be added.
     */
    void addWord(String str, String meaning);

    /**
     * Edit a word in Trie.
     *
     * @param str string to be edited.
     */
    void editWord(String str, String meaning);


    /**
     * Delete a word from Trie.
     *
     * @param str string to be deleted.
     */
    void deleteWord(String str);

    /**
     * Find a word in Trie.
     *
     * @param str string to be deleted.
     */
    void findWord(String str);

    /**
     * Print all the words in the dictionary to System.out.
     */
    void printAllWords();

    /**
     * Print all strings having str as a prefix.
     *
     * @param s string
     */
    void printProposedString(String s);
}
