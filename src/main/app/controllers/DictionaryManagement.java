package controllers;

public class DictionaryManagement implements DictionaryManagementInterface {
    private static DictionaryManagement instance = null;

    /** Singleton. */
    public static DictionaryManagement getInstance() {
        if (instance == null) {
            instance = new DictionaryManagement();
        }

        return instance;
    }
    /** Main. */
    public static void main(String[] args) {

    }
    public void insertFromCommandLine() {

    }
}
