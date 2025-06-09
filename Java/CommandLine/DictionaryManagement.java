package DictionaryCommandLine;

import java.io.*;
import java.util.*;

public class DictionaryManagement extends Dictionary {
    public static final String url = "jdbc:sqlite:./src/main/resources/db/dict_hh.db";
    public static final String IN_PATH = "C:\\Users\\TNC\\IdeaProjects\\BTLTuDien\\DictionaryApp\\src\\main\\resources\\words\\in.txt";
    public static final String OUT_PATH = "C:\\Users\\TNC\\IdeaProjects\\BTLTuDien\\DictionaryApp\\src\\main\\resources\\words\\out.txt";

    /**
     * insertFromCommandline.
     */
    public static void insertFromCommandline() {
        Scanner getStringInput = new Scanner(System.in);
        Scanner getIntegerInput = new Scanner(System.in);
        int size = getIntegerInput.nextInt();
        int i = 0;
        while (i < size) {
            String target = getStringInput.nextLine();
            String meaning = getStringInput.nextLine();
            Word temp = new Word(target, meaning);
            dictionary.add(temp);
            i++;
        }
    }

    public static void insertFromFile() {
        try {
            File inFile = new File(IN_PATH);
            FileReader fileReader = new FileReader(inFile);
            BufferedReader reader = new BufferedReader(fileReader);
            String line;
            while ((line = reader.readLine()) != null) {
                String[] wordsInLine = line.split(",");
                Word temp = new Word(wordsInLine[0], wordsInLine[1]);
                dictionary.add(temp);

            }
            dictionary.sort(Comparator.comparing(Word::getWordTarget));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void exportWordToFile() {
        try {
            // Tạo một đối tượng File với đường dẫn được chỉ định.
            File file = new File(OUT_PATH);
            // Tạo một luồng đầu ra (OutputStream) để ghi dữ liệu vào tệp tin.
            OutputStream outputStream = new FileOutputStream(file);
            // Tạo một OutputStreamWriter để chuyển đổi dữ liệu ký tự sang dữ liệu byte và ghi vào luồng đầu ra.
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            // Tạo một BufferedWriter để cung cấp bộ đệm cho việc ghi dữ liệu vào tệp tin.
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            // Định dạng chuỗi để ghi vào tệp tin. %-15s có nghĩa là chuỗi được căn lề trái với chiều rộng tối đa là 15 ký tự.
            String format = "%-15s %-15s%n";
            // Duyệt qua danh sách từ điển và ghi thông tin về mỗi từ vào tệp tin.
            for (Word word : dictionary) {
                bufferedWriter.write(String.format(format, word.getWordTarget(), word.getWordExplain()));
            }
            // Xả bộ đệm để đảm bảo rằng tất cả dữ liệu đã được ghi vào tệp tin.
            bufferedWriter.flush();
            // Đóng BufferedWriter để giải phóng tài nguyên.
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace(); // In ra lỗi nếu có bất kỳ ngoại lệ nào xảy ra.
        }
    }

    public static void updateWordToFile() {
        try {
            FileWriter fileWriter = new FileWriter(IN_PATH);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (Word word : dictionary) {
                bufferedWriter.write(word.getWordTarget() + "," + word.getWordExplain() + "\n");
            }
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addWord(String searching, String meaning) {
        searching = searching.toLowerCase();
        meaning = meaning.toLowerCase();
        int posAddWord = binaryCheck(0, dictionary.size(), searching);
        if (posAddWord == -1) {
            System.out.println("Từ bạn thêm đã tồn tại. Hãy chọn chức năng sửa đổi!");
            return;
        }
        dictionary.add(new Word());
        for (int i = dictionary.size() - 2; i >= posAddWord; i--) {
            dictionary.get(i + 1).setWordTarget(dictionary.get(i).getWordTarget());
            dictionary.get(i + 1).setWordExplain(dictionary.get(i).getWordExplain());
        }
        dictionary.get(posAddWord).setWordTarget(searching);
        dictionary.get(posAddWord).setWordExplain(meaning);
        updateWordToFile();
    }

    static class WordComparator implements Comparator<Word> {
        @Override
        public int compare(Word o1, Word o2) {
            return o1.getWordTarget().compareTo(o2.getWordTarget());
        }
    }

    public static void removeWord(String searching) {
        searching = searching.toLowerCase().trim();

        int index = Collections.binarySearch(dictionary, new Word(searching, null), new WordComparator());
        if (index >= 0) {
            dictionary.remove(index);
        } else {
            System.out.println("Từ bạn cần xoá không có trong từ điển!");
        }
        updateWordToFile();
    }

    public static int isContain(String str1, String str2) {
        for (int i = 0; i < Math.min(str1.length(), str2.length()); i++) {
            if (str1.charAt(i) > str2.charAt(i)) {
                return 1;
            } else if (str1.charAt(i) < str2.charAt(i)) {
                return -1;
            }
        }
        if (str1.length() > str2.length()) {
            return 1;
        }
        return 0;
    }

    public static int binaryCheck(int start, int end, String word) {
        if (end < start) {
            return -1; // Trả về -1 nếu end nhỏ hơn start, tức là không có phần tử nào trong phạm vi tìm kiếm.
        }
        int mid = start + (end - start) / 2; // Tính chỉ số trung tâm.
        int compareNext = word.compareTo(dictionary.get(mid).getWordTarget()); // So sánh từ cần thêm với từ ở vị trí mid.

        if (mid == 0) { // Nếu mid là vị trí đầu tiên trong danh sách.
            if (compareNext < 0) {
                return 0; // Nếu từ cần thêm nhỏ hơn từ ở vị trí mid, trả về vị trí đầu tiên.
            } else if (compareNext > 0) {
                return binaryCheck(mid + 1, end, word); // Nếu từ cần thêm lớn hơn từ ở vị trí mid, tiếp tục tìm kiếm ở nửa bên phải của danh sách.
            } else {
                return -1; // Nếu từ cần thêm đã tồn tại, trả về -1.
            }
        } else {
            int comparePrevious = word.compareTo(dictionary.get(mid - 1).getWordTarget()); // So sánh từ cần thêm với từ ở vị trí mid - 1.
            if (comparePrevious > 0 && compareNext < 0) {
                return mid; // Nếu từ cần thêm nằm giữa từ ở vị trí mid - 1 và từ ở vị trí mid, trả về vị trí mid.
            } else if (comparePrevious < 0) {
                return binaryCheck(start, mid - 1, word); // Nếu từ cần thêm nhỏ hơn từ ở vị trí mid - 1, tiếp tục tìm kiếm ở nửa bên trái của danh sách.
            } else if (compareNext > 0) {
                if (mid == dictionary.size() - 1) {
                    return dictionary.size(); // Nếu từ cần thêm lớn hơn tất cả các từ trong danh sách, trả về vị trí cuối cùng.
                }
                return binaryCheck(mid + 1, end, word); // Nếu từ cần thêm lớn hơn từ ở vị trí mid, tiếp tục tìm kiếm ở nửa bên phải của danh sách.
            } else {
                return -1; // Nếu từ cần thêm đã tồn tại, trả về -1.
            }
        }
    }


    public static int dictionaryLookup(int start, int end, String word) {
        if (end < start) {
            return -1;
        }
        int mid = start + (end - start) / 2;
        int compare = isContain(word, dictionary.get(mid).getWordTarget());
        if (compare == -1) {
            return dictionaryLookup(start, mid - 1, word);
        } else if (compare == 1) {
            return dictionaryLookup(mid + 1, end, word);
        } else {
            return mid;
        }
    }

    public static void ExportWordToFile() {
        try {
            // Tạo một đối tượng File với đường dẫn được chỉ định.
            File file = new File(OUT_PATH);
            // Tạo một luồng đầu ra (OutputStream) để ghi dữ liệu vào tệp tin.
            OutputStream outputStream = new FileOutputStream(file);
            // Tạo một OutputStreamWriter để chuyển đổi dữ liệu ký tự sang dữ liệu byte và ghi vào luồng đầu ra.
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            // Tạo một BufferedWriter để cung cấp bộ đệm cho việc ghi dữ liệu vào tệp tin.
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            // Định dạng chuỗi để ghi vào tệp tin. %-15s có nghĩa là chuỗi được căn lề trái với chiều rộng tối đa là 15 ký tự.
            String format = "%-15s %-15s%n";
            // Duyệt qua danh sách từ điển và ghi thông tin về mỗi từ vào tệp tin.
            for (Word word : dictionary) {
                bufferedWriter.write(String.format(format, word.getWordTarget(), word.getWordExplain()));
            }
            // Xả bộ đệm để đảm bảo rằng tất cả dữ liệu đã được ghi vào tệp tin.
            bufferedWriter.flush();
            // Đóng BufferedWriter để giải phóng tài nguyên.
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace(); // In ra lỗi nếu có bất kỳ ngoại lệ nào xảy ra.
        }
    }

    public static List<Word> dictionarySearcher(String wordPrefix) {
        List<Word> result = new ArrayList<>();
        for (Word word : dictionary) {
            if (word.getWordTarget().startsWith(wordPrefix)) {
                result.add(word);
            }
        }
        return result;
    }
}

