package controllers;

import models.Dictionary;
import models.Word;

import java.util.ArrayList;
import java.util.Arrays;

public interface DictionaryManagementInterface {
    Dictionary dictionary = Dictionary.getInstance();

    /**
     * Insert all words from a standard file.
     * Standard file: each line contains word_target and word_explain split by a tab ('\t').
     */
    void insertFromFile();

    /**
     * Get all words.
     * */
    ArrayList<Word> getAllWords();


    /**
     * return all the words that have prefix is str
     *
     * @param str string to search
     * */
    ArrayList<Word> dictionarySearcher(String str);

    /**
     * return all the words that is represented as str.
     *
     * @param str string to lookup.
     * */
    ArrayList<Word> dictionaryLookup(String str);

    /**
     * add a new word.
     *
     * @param str string to add.
     * @param meaning the meaning of that word in VNese.
     * @return true if the word is added successfully.
     */
    boolean dictionaryAddWord(String str, String meaning);

    /**
     * remove a word.
     *
     * @param str string to delete.
     * @return true if the word is deleted successfully.
     */
    boolean dictionaryRemoveWord(String str);

    void deleteAWordFromCommandline();

    /**
     * Print all words in dictionary to a file.
     *
     * @param fout output directory.
     */
    void dictionaryExportToFile(String fout);
}