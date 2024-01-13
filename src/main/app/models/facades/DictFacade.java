package models.facades;

import java.util.concurrent.ExecutorService;

import static models.facades.FavouriteFacade.favoriteWords;
import static models.facades.HistoryFacade.historyWords;
import static models.facades.WordListFacade.wordSet;

public class DictFacade {
    static boolean isInitialized = false;

    public static void Init() {
        if (isInitialized) return;
        isInitialized = true;
        ExecutorService executorService = java.util.concurrent.Executors.newFixedThreadPool(3);
        executorService.submit(() -> {
            LoadDict(false);
        });
        executorService.submit(favoriteWords::LoadFavoriteWords);
        executorService.submit(historyWords::LoadSearchedWords);
        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, java.util.concurrent.TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace(System.out);
        }
    }

    public static void Shutdown() {
        if (!isInitialized) return;
        isInitialized = false;
        ExecutorService executorService = java.util.concurrent.Executors.newFixedThreadPool(3);
        executorService.submit(DictFacade::SaveDict);
        executorService.submit(favoriteWords::SaveFavoriteWords);
        executorService.submit(historyWords::SaveSearchedWords);
        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, java.util.concurrent.TimeUnit.NANOSECONDS);
            System.out.println("All data has been saved!");
            wordSet.dictionaryClear();
            FavouriteFacade.clear();
            HistoryFacade.clear();
            System.gc();
        } catch (InterruptedException e) {
            e.printStackTrace(System.out);
        }
    }

    public static void resetDictionaryData() {
        wordSet.dictionaryClear();
        FavouriteFacade.clear();
        HistoryFacade.clear();
        wordSet.dictionaryInsertFromFile(wordSet.ORIGINAL_DICTIONARY_PATH);
        System.gc();
    }

    public static synchronized void SaveDict() {
        wordSet.dictionaryExportToFile(wordSet.DEFAULT_DICTIONARY_PATH);
    }

    public static void LoadDict(boolean isOriginal) {
        //wordSet.dictionaryInsertFromFile(wordSet.ORIGINAL_DICTIONARY_PATH);
        if (isOriginal) {
            System.out.println("Load default dict: " + wordSet.ORIGINAL_DICTIONARY_PATH);
            wordSet.dictionaryInsertFromFile(wordSet.ORIGINAL_DICTIONARY_PATH);
        } else {
            System.out.println("Load default dict: " + wordSet.DEFAULT_DICTIONARY_PATH);
            wordSet.dictionaryInsertFromFile(wordSet.DEFAULT_DICTIONARY_PATH);
        }
    }

}
