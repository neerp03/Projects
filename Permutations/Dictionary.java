package p;

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Dictionary {
    private ArrayList<String> words;

    public Dictionary(String fileName) {
        words = new ArrayList<>();
        try {
            File f = new File("dictionary.txt");
            Scanner scanner = new Scanner(f);
            while (scanner.hasNext()) {
                addWord(scanner.next());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found");
            System.exit(1);
        }
    }

    public void addWord(String word) {
        words.add(word);
    }

    public boolean lookup(String word) {
        return words.contains(word);
    }
}
