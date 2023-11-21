package controllers;

import models.Dictionary;
import models.Word;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class DictionaryManagement implements DictionaryManagementInterface {
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
    public boolean dictionaryEditWord(String str, String meaning) {
        return dictionary.editWord(str, meaning);
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
    public void speak(String text, String language) throws IOException {
        GoogleTranslate.speak(text, language);
    }

    @Override
    public void dictionaryClear() {
        dictionary.clear();
    }

    @Override
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

    @Override
    public void insertFromFile() {
        dictionaryInsertFromFile(null);
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
