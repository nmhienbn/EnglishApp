package models.databases;

public class Word {
    private final String word_target; // Just contains 30 distinct chars: 'a'-'z', ' ', '-', '.', '\''
    private String word_explain;

    public Word(String word_target, String word_explain) {
        this.word_target = word_target;
        this.word_explain = word_explain;
    }

    @Override
    public String toString() {
        return word_target + '\n' + word_explain.replaceAll("\\\\", "\n");
    }

    public void setWordExplain(String word_explain) {
        this.word_explain = word_explain;
    }

    public String getWordTarget() {
        return word_target;
    }

    public String getWordExplain() {
        return word_explain;
    }
}
