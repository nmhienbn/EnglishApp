import java.io.FileNotFoundException;

public class TestDict {
    private static DictionaryManagement wordSet = new DictionaryManagement();

    public static void main(String[] args) throws FileNotFoundException {
        wordSet.insertFromFile();
        wordSet.findWord("bubble");
//        wordSet.printProposedString("algo");
        wordSet.deleteWord("bubble");
        wordSet.findWord("bubble");
//        wordSet.dictionarySearcher();
//        wordSet.dictionaryExportToFile("D:\\App\\src\\out_dictionaries.txt");
    }

}