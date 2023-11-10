package views;

import controllers.DictionaryManagement;
import models.Trie;
import models.Word;

import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.System.exit;

public class DictionaryCommandline {
    DictionaryManagement manager = DictionaryManagement.getInstance();

    /**
     * Pause the program.
     */
    public void paused() {
        System.out.println("Press any key to continue...");
        Scanner cin = new Scanner(System.in);
        cin.nextLine();
    }

    /**
     * Print all the words in the dictionary to System.out.
     */
    public void showAllWords() {
        ArrayList<Word> proposedString = manager.getAllWords();

        int maxLen = 0;
        for (Word word : proposedString) {
            maxLen = Math.max(maxLen, word.getWordTarget().length());
        }

        // head
        String s1 = "No";
        String s2 = "English";
        String s3 = "Vietnamese";
        System.out.printf("%-8s| %-" + maxLen + "s | %s\n", s1, s2, s3);

        for (int i = 0; i < proposedString.size(); ++i) {
            System.out.printf("%-8d| %-" + maxLen + "s | %s\n", i + 1,
                    proposedString.get(i).getWordTarget(),
                    proposedString.get(i).getWordExplain());
        }

        paused();
    }

    /**
     * Insert n words from command line.
     * Then show all words in dictionary.
     */
    public void dictionaryBasic() {
        insertFromCommandline();
        showAllWords();
    }

    /**
     * Insert n words from command line.
     */
    public void insertFromCommandline() {
        Scanner cin = new Scanner(System.in);
        int n = cin.nextInt();
        for (int i = 0; i < n; i++) {
            String word = cin.nextLine();
            String explain = cin.nextLine();
            manager.dictionaryAddWord(word, explain);
        }
    }
    /**
     * Print all meaning of word str.
     */
    public void lookupWord() {
        String str;
        Scanner cin = new Scanner(System.in);
        System.out.print("Mời nhập từ cần tra cứu: ");
        str = cin.nextLine();

        Word proposedString = manager.dictionaryLookup(str);

        System.out.println("Kết quả tra cứu:");
        if (proposedString == null) {
            System.out.println("No results founded!");
        } else {
            System.out.println(proposedString.getWordExplain());
        }

        paused();
    }

    /**
     * Print all strings having str as a prefix.
     */
    public void searchWords() {
        String str;
        Scanner cin = new Scanner(System.in);
        System.out.print("Mời nhập từ cần tìm: ");
        str = cin.nextLine();

        ArrayList<Word> proposedString = manager.dictionarySearcher(str);

        System.out.println("Các từ tìm được:");
        if (proposedString.isEmpty()) {
            System.out.println("No result founded!");
        }

        for (Word word : proposedString) {
            System.out.println("- " + word.getWordTarget());
        }

        paused();
    }

    /**
     * remove word.
     */
    public void removeWord() {
        String str;
        Scanner cin = new Scanner(System.in);
        System.out.print("Mời nhập từ cần xóa: ");
        str = cin.nextLine();

        if (manager.dictionaryRemoveWord(str)) {
            System.out.println("Remove successfully!");
        } else {
            System.out.println("Remove failed!");
        }

        paused();
    }

    /**
     * update word.
     */
    public void updateWord() {
        paused();
    }

    /**
     * Play game.
     */
    public void playGame() {
        paused();
    }

    /**
     * Print all the words in the dictionary to System.out.
     */
    public int getRequest() {
        System.out.println("Welcome to My Application!\n" +
                "[0] Exit\n" +
                "[1] Add\n" +
                "[2] Remove\n" +
                "[3] Update\n" +
                "[4] Display\n" +
                "[5] Lookup\n" +
                "[6] Search\n" +
                "[7] Game\n" +
                "[8] Import from file\n" +
                "2\n" +
                "[9] Export to file");
        int req;
        Scanner cin = new Scanner(System.in);
        System.out.print("Mời nhập lựa chọn: ");
        req = cin.nextInt();

        return req;
    }

    public static void main(String[] args) {
        TestAPI.SetupDict();
        DictionaryCommandline dict = new DictionaryCommandline();

        while (true) {
            int req = dict.getRequest();

            switch (req) {
                case 0: exit(0); break;
                case 1: dict.insertFromCommandline(); break;
                case 2: dict.removeWord(); break;
                case 3: dict.updateWord(); break;
                case 4: dict.showAllWords(); break;
                case 5: dict.lookupWord(); break;
                case 6: dict.searchWords(); break;
                case 7: dict.playGame(); break;
                case 8: dict.manager.dictionaryInsertFromFile(null); break;
                case 9: dict.manager.dictionaryExportToFile(null); break;
                default: System.out.println("Invalid request!"); break;
            }
        }
    }
}
