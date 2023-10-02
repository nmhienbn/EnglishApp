package controllers;

import models.Dictionary;

public interface DictionaryManagementInterface {
    Dictionary dictionary = Dictionary.getInstance();

    /**
     * Insert all words from a standard file.
     * Standard file: each line contains word_target and word_explain split by a tab ('\t').
     */
    void insertFromFile();

    void dictionaryLookup();

    void insertAWordFromCommandline();

    void editAWordFromCommandline();

    void deleteAWordFromCommandline();

    /**
     * Print all words in dictionary to a file.
     *
     * @param fout output directory.
     */
    void dictionaryExportToFile(String fout);
}