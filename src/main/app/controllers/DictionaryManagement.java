package controllers;

import controllers.GoogleTranslate;
import models.Dictionary;
import models.Word;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class DictionaryManagement {
    public final String ORIGINAL_DICTIONARY_PATH = this.getClass().getResource("/models/original_dictionaries.txt").getPath();
    public final String DEFAULT_DICTIONARY_PATH = this.getClass().getResource("/models/dictionaries.txt").getPath();
    private static DictionaryManagement ins = null;
    private Dictionary dictionary = Dictionary.getInstance();

    public static DictionaryManagement getInstance() {
        if (ins == null) {
            ins = new DictionaryManagement();
        }
        return ins;
    }

    private DictionaryManagement() {

    }

    /**
     * Get all words.
     */
    public ArrayList<Word> getAllWords() {
        ArrayList<Word> tmp = dictionary.queryAllWords();
        if (tmp == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(tmp);
    }

    /**
     * Return all the meaning of str.
     *
     * @param str string to lookup.
     */
    public Word dictionaryLookup(String str) {
        return dictionary.lookupWord(str);
    }

    /**
     * Return all the words that have prefix is str
     *
     * @param str string to search
     */
    public ArrayList<Word> dictionarySearcher(String str) {
        ArrayList<Word> tmp = dictionary.getProposedString(str);
        if (tmp == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(tmp);
    }

    /**
     * Add a new word.
     *
     * @param str     string to add.
     * @param meaning the meaning of that word in VNese.
     * @return true if the word is added successfully.
     */
    public boolean dictionaryAddWord(String str, String meaning) {
        return dictionary.addWord(str, meaning);
    }

    /**
     * Edit an existing word.
     *
     * @param str     string to edit.
     * @param meaning the meaning of that word in VNese.
     * @return true if the word is added successfully.
     */
    public boolean dictionaryEditWord(String str, String meaning) {
        return dictionary.editWord(str, meaning);
    }

    /**
     * remove a word.
     *
     * @param str string to delete.
     * @return true if the word is deleted successfully.
     */
    public boolean dictionaryRemoveWord(String str) {
        return dictionary.deleteWord(str);
    }

    /**
     * Translate a string from source language to target language.
     * <em>sourceLanguage</em> = <em>en</em> (English), <em>vn</em> (Vietnamese), ... or an <em>auto</em> (auto detect).
     * <em>targetLanguage</em> = <em>en</em> (English), <em>vn</em> (Vietnamese), ...
     *
     * @param str            text to translate
     * @param sourceLanguage source language
     * @param targetLanguage target language
     * @return translated text
     */
    public String translate(String str, String sourceLanguage, String targetLanguage) throws IOException {
        return GoogleTranslate.translate(str, sourceLanguage, targetLanguage);
    }

    /**
     * Speek text.
     *
     * @param text     content of text to speak
     * @param language language of text, vi for vietnamese, en for english, ...
     * @throws IOException if cannot speak
     */
    public void speak(String text, String language) throws IOException {
        GoogleTranslate.speak(text, language);
    }

    /**
     * Clear all the words in dictionary
     */
    public void dictionaryClear() {
        dictionary.clear();
    }

    /**
     * Insert all words from a standard file.
     * Standard file: each line contains word_target and word_explain split by a tab ('\t').
     * file should be in resources
     *
     * @param filePath path to file (null for default file-dictionaries.txt in resources).
     */
    public void dictionaryInsertFromFile(String filePath) {
        if (filePath == null) {
            filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\models\\dictionaries.txt";
        } else {
            //filePath = System.getProperty("user.dir") + "\\src\\main\\resources" + filePath;
        }
        String fin1 = filePath;

        try (Scanner cin = new Scanner(new FileReader(fin1))) {
            dictionary.imports(cin);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

    }

    /**
     * Insert from dictionaries.txt
     */
    public void insertFromFile() {
        dictionaryInsertFromFile(null);
    }

    /**
     * Print all words in dictionary to a file.
     *
     * @param fout output directory.
     */
    public void dictionaryExportToFile(String fout) {
        try {
            PrintStream fileOutputStream = new PrintStream(new FileOutputStream(fout));
            System.setOut(fileOutputStream); // Redirect System.out to the file

            // export
            dictionary.exports();

            // Close the fileOutputStream
            fileOutputStream.close();

            // You can restore the original System.out like this:
            System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // After this block, System.out will be back to its original state (console).
        System.out.println("Dictionary has been exported to file!");
    }

    public static void main(String[] args) {
        getInstance().dictionaryInsertFromFile(getInstance().ORIGINAL_DICTIONARY_PATH);
        ArrayList<Word> ins = getInstance().getAllWords();
        System.out.println(ins.size());

        getInstance().dictionaryClear();
        ins = getInstance().getAllWords();
        System.out.println(ins.size());

        getInstance().dictionaryInsertFromFile(getInstance().DEFAULT_DICTIONARY_PATH);
        ins = getInstance().getAllWords();
        System.out.println(ins.size());
    }
}
