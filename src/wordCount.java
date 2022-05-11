import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author smh
 */
public class wordCount {
    public static void main(String[] args) {
        File file = new File(args[1]);
        try {
            if (args[0].equals("-c")) {
                System.out.println("文本中的字符数为：" + countCharacters(file));
            } else if (args[0].equals("-w")) {
                System.out.println("文本中的单词数为：" + countWords(file));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 统计文件中所有字符的个数，包括换行符
     * @param file 传入的文件对象
     * @return 统计的字符数
     */
    public static int countCharacters(File file) throws IOException {
        FileReader reader = new FileReader(file.getCanonicalPath());
        char[] data = new char[1024];
        int readCount;
        int characterCount = 0;
        while ((readCount = reader.read(data)) != -1) {
            characterCount += readCount;
        }
        reader.close();
        return characterCount;
    }

    /**
     * 统计文件中所有单词的个数，但是不做单词的合法性检查
     * @param file 传入的文件对象
     * @return 统计的单词数
     */
    public static int countWords(File file) throws IOException {
        FileReader reader = new FileReader(file.getCanonicalPath());
        char[] data = new char[1024];
        int readCount;
        int wordCount = 0;
        char previous = ' ';
        while ((readCount = reader.read(data)) != -1) {
            if (!isCharacterContained(previous) && isCharacterContained(data[0])) {
                wordCount ++;
            }
            for (int i = 0; i < readCount - 1; i++) {
                if (!isCharacterContained(data[i]) && isCharacterContained(data[i + 1])) {
                    wordCount ++;
                }
            }
            previous = data[readCount - 1];
        }
        reader.close();
        return wordCount;
    }

    /**
     * 判断传入的字符是否为指定的字符
     * @param c 待检测的字符
     * @return true 则表明参数c是指定的字符， false 则相反
     */
    public static boolean isCharacterContained(char c) {
        char[] punctuationMarks = {',', '.', ';', '?', '"', ':', ' ', '\n', '\r'};
        for (Character character : punctuationMarks) {
            if (c == character) {
                return true;
            }
        }
        return false;
    }
}
