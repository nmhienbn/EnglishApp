package models.facades;


import models.functions.HistoryWords;

public class HistoryFacade {
    static final HistoryWords historyWords = HistoryWords.getInstance();
    public static void add(String word) {
        HistoryWords.getInstance().add(word);
    }

    public static Iterable<String> toIterable() {
        return HistoryWords.getInstance().SearchHistory();
    }

    public static void clear() {
        HistoryWords.getInstance().clear();
    }
}
