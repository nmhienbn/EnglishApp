package controllers;

import models.Dictionary;
import models.Word;

import java.io.*;
import java.util.ArrayList;

public class DictionaryManagement implements DictionaryManagementInterface {
    private static DictionaryManagement ins = null;

    public static DictionaryManagement getInstance() {
        if (ins == null) {
            ins = new DictionaryManagement();
        }

        return ins;
    }

    @Override
    public void insertFromFile() {

        String fin1=System.getProperty("user.dir") + "\\src\\main\\resources\\models\\dictionaries.txt";
        //String fin1 = "dictionaries.txt";
        try (BufferedReader fin = new BufferedReader(new FileReader(fin1))) {
            String line;
            while ((line = fin.readLine()) != null) {
                String[] tmp = line.split("\t", 2);
                dictionary.addWord(tmp[0], tmp[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Word> getAllWords() {
        ArrayList<Word> temp = new ArrayList<Word>(dictionary.queryAllWords());
        return temp;
    }

    @Override
    public ArrayList<Word> dictionaryLookup(String str) {
        ArrayList<Word> temp = new ArrayList<Word>(dictionary.lookupWord(str));
        return temp;
    }

    @Override
    public ArrayList<Word> dictionarySearcher(String str) {
        ArrayList<Word> temp = new ArrayList<Word>(dictionary.getProposedString(str));
        return temp;
    }

    @Override
    public boolean dictionaryAddWord(String str, String meaning) {
        return dictionary.addWord(str, meaning);
    }

    @Override
    public boolean dictionaryRemoveWord(String str){
        return dictionary.deleteWord(str);
    }

    @Override
    public void deleteAWordFromCommandline() {

    }

    @Override
    public void dictionaryExportToFile(String fout) {
        /*
        try {
            PrintStream fileOutputStream = new PrintStream(new FileOutputStream(fout));
            System.setOut(fileOutputStream); // Redirect System.out to the file

            // Now, anything printed to System.out will be written to the file
            String s1 = "No";
            String s2 = "English";
            String s3 = "Vietnamese";
            System.out.printf("%-8s| %-" + dictionary.wordMaxLen + "s | %s\n", s1, s2, s3);
            dictionary.printAllWords();

            // Close the fileOutputStream
            fileOutputStream.close();

            // You can restore the original System.out like this:
            // System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // After this block, System.out will be back to its original state (console).
        System.out.println("Dictionary has been exported to file!");
        */
    }

}
