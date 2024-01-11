package models.functions;

import models.DictFacade;

import java.io.*;
import java.util.LinkedList;

public class HistoryWords {
    private static final String historyWordsPath = DictFacade.class.
            getClassLoader().getResource("models/searched_words.txt").getPath();
    private static final LinkedList<String> search_history = new LinkedList<>();
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
        for (String s : search_history) {
            if (s.equals(word)) {
                search_history.remove(s);
                break;
            }
        }
        search_history.addFirst(word);
        if (search_history.size() > MAX_SEARCH_HISTORY) search_history.removeLast();
    }

    public Iterable<String> SearchHistory() {
        return search_history;
    }

    public void clear() {
        search_history.clear();
    }

    public void LoadSearchedWords() {
        try (BufferedReader cin = new BufferedReader(new FileReader(historyWordsPath))) {
            String word;
            while ((word = cin.readLine()) != null) {
                search_history.add(word);
            }
            cin.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    public synchronized void SaveSearchedWords() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(historyWordsPath))) {
            for (String word : search_history) {
                writer.println(word);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("History words has been exported to file!");
    }
}
