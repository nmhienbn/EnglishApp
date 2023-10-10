package models;

import java.util.ArrayList;

public interface DictionaryInterface {
    /**
     * Add a word to dictionary.
     *
     * @param str string to be added.
     * @return true if the word is added successfully.
     */
    boolean addWord(String str, String meaning);

    /**
     * Edit a word in dictionary.
     *
     * @param str string to be edited.
     * @param meaning meaning of edited word
     * @return true if the word is successful edited
     */
    boolean editWord(String str, String meaning);

    /**
     * Delete a word from dictionary.
     *
     * @param str string to be deleted.
     * @return true if the word is deleted successfully.
     */
    boolean deleteWord(String str);

    /**
     * Get all words from dictionary.
     * */
    ArrayList<Word> queryAllWords();

    /**
     * Get all words with prefix is str in dictionary.
     *
     * @param str string to search.
     * */
    ArrayList<Word> getProposedString(String str);

    /**
     * Return the Word that its target is str in dictionary.
     *
     * @param str string to lookup.
     * */
    Word lookupWord(String str);

}
