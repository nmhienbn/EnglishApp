package controllers;

import java.io.*;

public class DictionaryManagement implements DictionaryManagementInterface {
    @Override
    public void insertFromFile() {
        String fin1 = "D:\\App\\src\\dictionaries.txt";
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
    public void dictionaryLookup() {

    }

    @Override
    public void insertAWordFromCommandline() {

    }

    @Override
    public void editAWordFromCommandline() {

    }

    @Override
    public void deleteAWordFromCommandline() {

    }

    @Override
    public void dictionaryExportToFile(String fout) {
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
    }

}
