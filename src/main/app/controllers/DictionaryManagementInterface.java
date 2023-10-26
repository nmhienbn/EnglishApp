package controllers;

import models.Dictionary;
import models.Word;

import java.io.IOException;
import java.util.ArrayList;

public interface DictionaryManagementInterface {
    Dictionary dictionary = Dictionary.getInstance();

    /**
     * Insert all words from a standard file.
     * Standard file: each line contains word_target and word_explain split by a tab ('\t').
     */
    void insertFromFile();

    /**
     * Get all words.
     */
    ArrayList<Word> getAllWords();


    /**
     * Return all the words that have prefix is str
     *
     * @param str string to search
     */
    ArrayList<Word> dictionarySearcher(String str);

    /**
     * Return all the meaning of str.
     *
     * @param str string to lookup.
     */
    Word dictionaryLookup(String str);

    /**
     * Add a new word.
     *
     * @param str     string to add.
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

    /**
     * Translate a string from source language to target language.
     * <em>sourceLanguage</em> = <em>en</em> (English), <em>vn</em> (Vietnamese), ... or an <em>auto</em> (auto detect).
     * <em>targetLanguage</em> = <em>en</em> (English), <em>vn</em> (Vietnamese), ...
     * @param str text to translate
     * @param sourceLanguage source language
     * @param targetLanguage target language
     * @return translated text
     */
    String translate(String str, String sourceLanguage, String targetLanguage) throws IOException;
}