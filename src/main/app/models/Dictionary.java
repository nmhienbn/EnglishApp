package models;

import java.util.ArrayList;
import java.util.HashMap;

public class Dictionary implements DictionaryInterface {
    private static Dictionary instance = null;

    private static class Node {

        static int numberOfNode = 0;
        Node[] child = new Node[30]; // Children of node.
        ArrayList<Word> words = null;

        Node() {
            ++numberOfNode;
        }
    }

    private final Node root;
    private final HashMap<Character, Integer> charset; // Map char to int.
    private int cntChar; // = 30
    public int wordMaxLen = 1;
    private ArrayList<Word> proposedString;

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
    public boolean addWord(String str, String meaning) {
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

        if (p.words == null) {
            p.words = new ArrayList<Word>();
        }

        if (p.words.size() == 1) {
            return false;
        }

        p.words.add(new Word(s, meaning));

        return true;
    }

    @Override
    public boolean editWord(String str, String meaning) {
        String s = str.toLowerCase();
        if (s.length() > wordMaxLen) {
            wordMaxLen = s.length();
        }

        Node p = root;
        for (int i = 0; i < s.length(); ++i) {
            int c = charset.get(s.charAt(i));
            if (p.child[c] == null) {
                return false;
            }
            p = p.child[c];
        }

        // No word
        if (p.words.isEmpty()) {
            return false;
        }

        p.words.get(0).setWordExplain(meaning);

        return true;
    }

    @Override
    public boolean deleteWord(String str) {
        String s = str.toLowerCase();

        Node p = root;
        for (int i = 0; i < s.length(); ++i) {
            int c = charset.get(s.charAt(i));
            if (p.child[c] == null) {
                System.out.println("No word found to be deleted!");
            }
            p = p.child[c];
        }

        if (p.words.get(0) != null) {
            p.words.remove(p.words.size() - 1);
            return true;
        } else {
            return false;
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
        return p.words.get(0);
    }


    /**
     * Find a word in Trie.
     *
     * @param str string to be search.
     */
    public void findWord(String str) {
        String s = str.toLowerCase();
        Word foundWord = findString(s);
        if (foundWord == null)
            System.out.println("No word found!");
        else {
            System.out.println(foundWord);
        }
    }

    public ArrayList<Word> lookupWord(String str) {
        proposedString = new ArrayList<>();



        return proposedString;
    }

    public ArrayList<Word> queryAllWords() {
        proposedString = new ArrayList<>();
        findAllWords(root);
        return proposedString;
    }
    /**
     * Get all words from Trie
     *
     * @param u Current node in Trie
     * */
    private void findAllWords(Node u) {
        if (!u.words.isEmpty()) {
            proposedString.add(u.words.get(0));
        }
        for (int i = 0; i < cntChar; ++i) {
            if (u.child[i] != null) {
                findAllWords(u.child[i]);
            }
        }
    }

    @Override
    public ArrayList<Word> getProposedString(String str) {
        String s = str.toLowerCase();
        proposedString = new ArrayList<>();
        Node p = root;
        for (int i = 0; i < s.length(); ++i) {
            int c = charset.get(s.charAt(i));
            if (p.child[c] == null) {
                return null; // No word found;
            }
            p = p.child[c];
        }
        findAllWords(p);

        return proposedString;
    }
}
