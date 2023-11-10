package controllers;

import controllers.googleapi.GoogleTranslate;
import models.Trie;
import models.Word;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class DictionaryManagement implements DictionaryManagementInterface {
    private static DictionaryManagement ins = null;

    public static DictionaryManagement getInstance() {
        if (ins == null) {
            ins = new DictionaryManagement();
        }

        return ins;
    }



    @Override
    public ArrayList<Word> getAllWords() {
        ArrayList<Word> tmp = dictionary.queryAllWords();
        if (tmp == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(tmp);
    }

    @Override
    public Word dictionaryLookup(String str) {
        return dictionary.lookupWord(str);
    }

    @Override
    public ArrayList<Word> dictionarySearcher(String str) {
        ArrayList<Word> tmp = dictionary.getProposedString(str);
        if (tmp == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(tmp);
    }

    @Override
    public boolean dictionaryAddWord(String str, String meaning) {
        return dictionary.addWord(str, meaning);
    }

    @Override
    public boolean dictionaryRemoveWord(String str) {
        return dictionary.deleteWord(str);
    }

    @Override
    public String translate(String str, String sourceLanguage, String targetLanguage) throws IOException {
        return GoogleTranslate.translate(str, sourceLanguage, targetLanguage);
    }

    @Override
    public void speek(String text, String language) throws IOException {
        GoogleTranslate.speek(text, language);
    }

    @Override
    public void deleteAWordFromCommandline() {

    }

    @Override
    public void insertFromFile(String filePath) {
        if (filePath == null) {
            filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\models\\dictionaries.txt";
        }
        String fin1 = filePath;

        try(Scanner cin = new Scanner(new FileReader(fin1))) {
            dictionary.imports(cin);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

    }

    @Override
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

}
