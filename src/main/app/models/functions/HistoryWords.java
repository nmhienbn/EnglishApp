package models.functions;

import java.util.LinkedList;

public class HistoryWords {
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
}
