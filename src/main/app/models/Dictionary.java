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
    public static int wordMaxLen = 1;
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
            Integer c = charset.get(s.charAt(i));
            if (c == null) {
                System.out.println("Invalid word!");
                return false;
            }
            if (p.child[c] == null) {
                p.child[c] = new Node();
            }
            p = p.child[c];
        }

        if (p.word == null) {
            p.word = new Word(s, meaning);
        } else {
            p.word.setWordExplain((p.word.getWordExplain() + "\n" + meaning));
        }
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
            Integer c = charset.get(s.charAt(i));
            if (c == null || p.child[c] == null) {
                return false;
            }
            p = p.child[c];
        }

        // No word

        if (p.word == null) {
            p.word = new Word(s, meaning);
        } else {
            p.word.setWordExplain(meaning);
        }

        return true;
    }

    @Override
    public boolean deleteWord(String str) {
        String s = str.toLowerCase();

        Node p = root;
        for (int i = 0; i < s.length(); ++i) {
            Integer c = charset.get(s.charAt(i));
            if (c == null || p.child[c] == null) {
                System.out.println("No word found to be deleted!");
                return false;
            }
            p = p.child[c];
        }

        if (p.word != null) {
            p.word = null;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Find a string in trie.
     *
     * @param s string to find
     * @return null or the Node corresponding to s
     */
    private Node findString(String s) {
        Node p = root;
        for (int i = 0; i < s.length(); ++i) {
            Integer c = charset.get(s.charAt(i));
            if (c == null || p.child[c] == null) {
                return null; // No word found;
            }
            p = p.child[c];
        }
        return p;
    }


    /**
     * Find a word in Trie.
     *
     * @param str string to be searched.
     */
    public Word lookupWord(String str) {
        String s = str.toLowerCase();
        Node foundNode = findString(s);
        if (foundNode == null)
            return null;
        else {
            return foundNode.word;
        }
    }

    public ArrayList<Word> queryAllWords() {
        proposedString = new ArrayList<>();
        findAllWordTargets(root);
        return proposedString;
    }

    /**
     * Get all word_target(s) from Trie
     *
     * @param u Current node in Trie
     */
    private void findAllWordTargets(Node u) {
        if (u.word != null) {
            proposedString.add(u.word);
        }
        for (int i = 0; i < cntChar; ++i) {
            if (u.child[i] != null) {
                findAllWordTargets(u.child[i]);
            }
        }
    }

    private int cnt = 0;

    /**
     * Print all words from Trie as a table.
     *
     * @param u Current node in Trie
     */
    private void printAllWords(Node u) {
        if (u.word != null) {
            System.out.printf("%-8d| %-" + wordMaxLen + "s | %s\n", cnt, u.word.getWordTarget(), u.word.getWordExplain());
        }
        for (int i = 0; i < cntChar; ++i) {
            if (u.child[i] != null) {
                printAllWords(u.child[i]);
            }
        }
    }

    /**
     * Print all words from Trie as a table.
     */
    public void printAllWords() {
        cnt = 0;
        printAllWords(root);
    }

    @Override
    public ArrayList<Word> getProposedString(String str) {
        String s = str.toLowerCase();
        proposedString = new ArrayList<>();
        Node p = root;
        for (int i = 0; i < s.length(); ++i) {
            Integer c = charset.get(s.charAt(i));
            if (c == null || p.child[c] == null) {
                return null; // No word found;
            }
            p = p.child[c];
        }
        findAllWordTargets(p);

        return proposedString;
    }
}
