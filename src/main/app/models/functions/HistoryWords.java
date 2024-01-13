package models.functions;

import models.facades.DictFacade;

import java.io.*;
import java.util.LinkedList;

public class HistoryWords {
    private static final String historyWordsPath = DictFacade.class.
            getClassLoader().getResource("models/searched_words.txt").getPath();
    private static final LinkedList<String> searched_history = new LinkedList<>();
    private static HistoryWords ins = null;
    private static final int MAX_SEARCH_HISTORY = 50;

    private HistoryWords() {

    }

    public static HistoryWords getInstance() {
        if (ins == null) {
            ins = new HistoryWords();
        }
        return ins;
    }

    public void add(String word) {
        for (String s : searched_history) {
            if (s.equals(word)) {
                searched_history.remove(s);
                break;
            }
        }
        searched_history.addFirst(word);
        if (searched_history.size() > MAX_SEARCH_HISTORY) searched_history.removeLast();
    }

    public Iterable<String> SearchHistory() {
        return searched_history;
    }

    public void clear() {
        searched_history.clear();
    }

    public void LoadSearchedWords() {
        try (BufferedReader cin = new BufferedReader(new FileReader(historyWordsPath))) {
            String word;
            while ((word = cin.readLine()) != null) {
                searched_history.add(word);
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }

    public synchronized void SaveSearchedWords() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(historyWordsPath))) {
            for (String word : searched_history) {
                writer.println(word);
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        System.out.println("History words has been exported to file!");
        searched_history.clear();
    }
}
