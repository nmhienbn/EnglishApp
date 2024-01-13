package models.databases;

import java.util.ArrayList;
import java.util.HashMap;

public class Trie {

    private final TrieNode root;
    private final HashMap<Character, Integer> charset; // Map char to int.
    private int cntChar; // = 30
    private ArrayList<Word> proposedString; // use for get queries

    /**
     * Trie constructor.
     */
    Trie() {
        root = new TrieNode();

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

    public boolean addWord(String str, String meaning) {
        String s = str.toLowerCase();

        TrieNode p = root;
        for (int i = 0; i < s.length(); ++i) {
            Integer c = charset.get(s.charAt(i));
            if (c == null) {
                System.out.println("Invalid word!");
                return false;
            }
            if (p.child[c] == null) {
                p.child[c] = new TrieNode();
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

    public boolean editWord(String str, String meaning) {
        String s = str.toLowerCase();

        TrieNode p = root;
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

    public boolean deleteWord(String str) {
        String s = str.toLowerCase();

        TrieNode p = root;
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
     * @return null or the TrieNode corresponding to s
     */
    private TrieNode findString(String s) {
        TrieNode p = root;
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
        TrieNode foundNode = findString(s);
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
    private void findAllWordTargets(TrieNode u) {
        if (u.word != null) {
            proposedString.add(u.word);
        }
        for (int i = 0; i < cntChar; ++i) {
            if (u.child[i] != null) {
                findAllWordTargets(u.child[i]);
            }
        }
    }

    /**
     * Find all words with a string as prefix
     *
     * @param str prefix
     * @return
     */
    public ArrayList<Word> getProposedString(String str) {
        String s = str.toLowerCase();
        proposedString = new ArrayList<>();
        TrieNode p = root;
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

    /**
     * Delete a branch
     *
     * @param cur root of the branch
     */
    private void deleteBranch(TrieNode cur) {
        if (cur == null) {
            return;
        }
        cur.word = null;
        for (int i = 0; i < cur.child.length; ++i) {
            deleteBranch(cur.child[i]);
            cur.child[i] = null;
        }
    }

    /**
     * Clear trie.
     */
    public void clear() {
        proposedString = null;
        deleteBranch(root);
    }

    public static class TrieNode {
        public static int cnt = 0;
        private final TrieNode[] child = new TrieNode[30]; // Children of node.
        private Word word;

        protected void finalize() {
            ++cnt;
        }
    }
}
