import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class WritePoetry {
    public String writePoem(String file, String startWord, int length, boolean printHashtable) throws IOException {
        HashTable<String, WordFreqInfo> table = new HashTable();
        String poem = startWord;

        ArrayList<String> wordList = readFile(file);

        for (int i = 0; i < wordList.size() - 1; i++) {
            WordFreqInfo wordFreqInfo;
            if (!table.contains(wordList.get(i))) {
                wordFreqInfo = new WordFreqInfo(wordList.get(i), 0);
                wordFreqInfo.updateFollows(wordList.get(i + 1));
                table.insert(wordList.get(i), wordFreqInfo);
            } else {
                wordFreqInfo = table.find(wordList.get(i));
                wordFreqInfo.updateFollows(wordList.get(i + 1));
            }
        }

        poem += createPoem(table, table.find(startWord), "", 1, length, false) + ".";

        if (printHashtable) {
            System.out.println(table.toString(table.size()));
        }

        return poem;
    }

    private ArrayList<String> readFile(String file) throws IOException {
        File newFile = new File(file);
        ArrayList<String> wordList = new ArrayList<>();
        try (Scanner input = new Scanner(newFile)) {
            while (input.hasNextLine()) {
                String currentLine = input.nextLine();
                String[] words = currentLine.split(" ");
                for (String word : words) {
                    word = word.trim();
                    String punctuationMark = "";
                    if (word.length() > 0) {
                        if (word.length() > 1) {
                            if (word.charAt(word.length() - 1) != '\'' && !Character.isAlphabetic(word.charAt(word.length() - 1))) {
                                punctuationMark = String.valueOf(word.charAt(word.length() - 1));
                                String newWord = word.replace(punctuationMark, "");
                                wordList.add(newWord.toLowerCase());
                                wordList.add(punctuationMark);
                            } else {
                                wordList.add(word.toLowerCase());
                            }
                        } else {
                            wordList.add(word.toLowerCase());
                        }
                    }
                }
            }
        }
        return wordList;
    }

    private String createPoem(HashTable table, WordFreqInfo currentWord, String poem, int numWords, int length, boolean noSpace) {
        if (numWords == length - 1) {
            return poem;
        }

        String nextWord = currentWord.getFollowWord((int) (Math.random() * (currentWord.getOccurCount() - 1)));
        if (nextWord.length() == 1 && !Character.isAlphabetic(nextWord.charAt(0))) {
            poem += nextWord + "\n";
            noSpace = true;
        } else {
            if (noSpace) {
                poem += nextWord;
                noSpace = false;
            } else {
                poem += " " + nextWord;
            }
        }

        return createPoem(table, (WordFreqInfo) table.find(nextWord), poem, numWords + 1, length, noSpace);
    }
}
