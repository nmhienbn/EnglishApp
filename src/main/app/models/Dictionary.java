package models;

import java.util.ArrayList;
import java.util.HashMap;

public class Dictionary implements DictionaryInterface {
    private static Dictionary instance = null;

    private static class Node {

        static int numberOfNode = 0;
        Node[] child = new Node[30]; // Children of node.
        Word word = null;

        Node() {
            ++numberOfNode;
        }
    }

    private final Node root;
    private final HashMap<Character, Integer> charset; // Map char to int.
    private int cntChar; // = 30
    public int wordMaxLen = 1;


    /**
     * Trie constructor.
     */
    private Dictionary() {
        root = new Node();

        cntChar = 0;
        charset = new HashMap<>();
        for (char c = 'a'; c <= 'z'; c++) {
            charset.put(c, cntChar++);
        }
        charset.put('-', cntChar++);
        charset.put(' ', cntChar++);
        charset.put('.', cntChar++);
        charset.put('\'', cntChar++);
    }

    /**
     * Singleton.
     */
    public static Dictionary getInstance() {
        if (instance == null) {
            instance = new Dictionary();
        }
        return instance;
    }

    @Override
    public void addWord(String str, String meaning) {
        String s = str.toLowerCase();
        if (s.length() > wordMaxLen) {
            wordMaxLen = s.length();
        }
        Node p = root;
        for (int i = 0; i < s.length(); ++i) {
            int c = charset.get(s.charAt(i));
            if (p.child[c] == null) {
                p.child[c] = new Node();
            }
            p = p.child[c];
        }
        if (p.word == null) {
            p.word = new Word(s, meaning);
        } else {
            System.out.println("This word has been existed! Please edit it if you want.");
        }
    }

    @Override
    public void editWord(String str, String meaning) {
        String s = str.toLowerCase();
        if (s.length() > wordMaxLen) {
            wordMaxLen = s.length();
        }
        Node p = root;
        for (int i = 0; i < s.length(); ++i) {
            int c = charset.get(s.charAt(i));
            if (p.child[c] == null) {
                System.out.println("No word found to edit!");
            }
            p = p.child[c];
        }
        p.word.setWordExplain(meaning);
        System.out.println("models.Word \"" + str + "\" has been edited successfully! Now it means \"" + meaning + "\"");
    }

    @Override
    public void deleteWord(String str) {
        String s = str.toLowerCase();
        Node p = root;
        for (int i = 0; i < s.length(); ++i) {
            int c = charset.get(s.charAt(i));
            if (p.child[c] == null) {
                System.out.println("No word found to be deleted!");
            }
            p = p.child[c];
        }
        if (p.word != null) {
            System.out.println("The word \"" + p.word.getWordTarget() + "\" has been deleted successfully!");
            p.word = null;
        } else {
            System.out.println("No word found to be deleted!");
        }
    }

    /**
     * Find a string in trie.
     *
     * @param s string to find
     * @return null or the Word corresponding to s
     */
    private Word findString(String s) {
        Node p = root;
        for (int i = 0; i < s.length(); ++i) {
            int c = charset.get(s.charAt(i));
            if (p.child[c] == null) {
                return null; // No word found;
            }
            p = p.child[c];
        }
        return p.word;
    }

    @Override
    public void findWord(String str) {
        String s = str.toLowerCase();
        Word foundWord = findString(s);
        if (foundWord == null)
            System.out.println("No word found!");
        else {
            System.out.println(foundWord);
        }
    }

    private ArrayList<Word> proposedString;

    private void findAllWords(Node u) {
        if (u.word != null) {
            proposedString.add(u.word);
        }
        for (int i = 0; i < cntChar; ++i) {
            if (u.child[i] != null) {
                findAllWords(u.child[i]);
            }
        }
    }

    @Override
    public void printAllWords() {
        proposedString = new ArrayList<>();
        findAllWords(root);
        for (int i = 0; i < proposedString.size(); ++i) {
            System.out.printf("%-8d| %-" + wordMaxLen + "s | %s\n", i + 1,
                    proposedString.get(i).getWordTarget(), proposedString.get(i).getWordExplain());
        }
         System.out.println(Node.numberOfNode);
    }

    /**
     * Find all strings having str as a prefix.
     *
     * @param str string to find
     */
    private void getProposedString(String str) {
        String s = str.toLowerCase();
        proposedString = new ArrayList<>();
        Node p = root;
        for (int i = 0; i < s.length(); ++i) {
            int c = charset.get(s.charAt(i));
            if (p.child[c] == null) {
                return; // No word found;
            }
            p = p.child[c];
        }
        findAllWords(p);
    }

    @Override
    public void printProposedString(String s) {
        getProposedString(s);
        System.out.println("Propose:");
        if (proposedString.isEmpty()) {
            System.out.println("No word to propose!");
        }
        for (Word word : proposedString) {
            System.out.println("- " + word.getWordTarget());
        }
    }
}
