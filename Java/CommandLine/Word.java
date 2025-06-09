package DictionaryCommandLine;

public class Word implements Comparable<Word>{
    private String wordTarget;
    private String wordExplain;
    private String pronounce;

    public Word() {
        this.wordTarget = "";
        this.wordExplain = "";
        this.pronounce = "";
    }

    public Word(String wordTarget, String wordExplain, String pronounce) {
        this.wordTarget = wordTarget;
        this.wordExplain = wordExplain;
        this.pronounce = pronounce;
    }

    public Word(String target, String meaning) {
        this.wordTarget = target;
        this.wordExplain = meaning;
    }

    public String getWordTarget() {
        return wordTarget;
    }

    public void setWordTarget(String wordTarget) {
        this.wordTarget = wordTarget;
    }

    public String getWordExplain() {
        return wordExplain;
    }

    public void setWordExplain(String wordExplain) {
        this.wordExplain = wordExplain;
    }

    public String getPronounce() {
        return pronounce;
    }

    public void setPronounce(String pronounce) {
        this.pronounce = pronounce;
    }

    @Override
    public String toString() {
        return wordTarget;
    }

    @Override
    public int compareTo(Word o) {
        return 0;
    }

    public String getInfo() {
        return wordExplain + " : " + wordTarget;
    }

}
