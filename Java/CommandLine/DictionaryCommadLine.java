package DictionaryCommandLine;

import java.util.List;
import java.util.Scanner;

public class DictionaryCommadLine {

    public static void DictionaryBasic() {
        DictionaryManagement.insertFromCommandline(); //thêm từ
        DictionaryCommandline.showAllWords();         //hiện từ
    }

    public static void dictionaryAdvanced() {
        while (true) {                                          //vòng lặp vô hạn
            System.out.println("Welcome to My Application!");
            System.out.println("[0] Exit");
            System.out.println("[1] Add");
            System.out.println("[2] Remove");
            System.out.println("[3] Update");
            System.out.println("[4] Display");
            System.out.println("[5] Lookup");
            System.out.println("[6] Search");
            System.out.println("[7] Game");
            System.out.println("[8] Import from file");
            System.out.println("[9] Export to file");
            System.out.println("Choose your action: ");
            Scanner scanner = new Scanner(System.in);           //đọc số nguyên nhập vào
            int temp = scanner.nextInt();
            switch (temp) {
                case 0:
                    break;
                case 1:
                    System.out.println("Hãy nhập từ cần thêm: ");
                    Scanner scanner1 = new Scanner(System.in);
                    String wordEx = scanner1.nextLine();                //đọc từ dòng 1
                    String wordTarget = scanner1.nextLine();            //đọc từ dòng 2
                    DictionaryManagement.addWord(wordEx, wordTarget);   //thêm vào từ điển
                    break;
                case 2:
                    System.out.println("Hãy nhập từ cần xoá: ");
                    Scanner scanner2 = new Scanner(System.in);
                    String wordTarget1 = scanner2.nextLine();           //đọc từ nhập vào
                    DictionaryManagement.removeWord(wordTarget1);       //xóa
                    break;
                case 3:
                    DictionaryManagement.updateWordToFile();
                    System.out.println("Đã update.");
                    break;
                case 4:
                    DictionaryCommandline.showAllWords();
                    break;
                case 5:
                    System.out.println("Nhập từ cần tìm: ");
                    Scanner scanner3 = new Scanner(System.in);
                    String wordTarget2 = scanner3.nextLine();
                    int pos = DictionaryManagement.dictionaryLookup(0, DictionaryManagement.dictionary.size() - 1, wordTarget2);
                    if (pos != -1) {
                        System.out.println("Từ bạn cần tìm là: " + DictionaryManagement.dictionary.get(pos).getInfo());
                    } else {
                        System.out.println("Từ cần tìm không tồn tại.");
                    }
                    break;
                case 6:
                    System.out.println("Nhập từ cần tìm: ");
                    Scanner scanner4 = new Scanner(System.in);
                    String wordPrefix = scanner4.nextLine();
                    List<Word> res = DictionaryManagement.dictionarySearcher(wordPrefix);
                    for (Word word : res) {
                        System.out.println(word.getWordTarget());
                    }
                    break;
                case 7:
                    break;
                case 8:
                    DictionaryManagement.insertFromFile();
                    break;
                case 9:
                    DictionaryManagement.ExportWordToFile();
                    break;
                default:
                    System.out.println("Action not supported!");
            }
        }
    }
}