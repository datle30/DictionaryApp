package DictionaryCommandLine;

public class DictionaryCommandline extends Dictionary{
    public static String showAllWords() {
        String ans = "";
        System.out.printf("%-6s%c %-15s%c %-20s%n","No", '|' ,"English", '|', "Vietnamese");
        for (int i = 0; i < dictionary.size(); i++) {
            System.out.printf("%-6d%c %-15s%c %-15s%n", i + 1,'|', dictionary.get(i).getWordTarget(), '|',dictionary.get(i).getWordExplain());
        }
        return ans;
    }
}
