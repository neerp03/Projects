package p;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.*;

public class PermutationsConsole {
    public static void main(String[] args) {
        try {
            FileWriter PW = new FileWriter("perms.txt");
            FileWriter AW = new FileWriter("analytics.txt");
            
            Scanner kb = new Scanner(System.in);
            System.out.println("Enter the dictionary file name:");
            String dictionaryFile = kb.nextLine();
            Dictionary dictionary = new Dictionary(dictionaryFile);
            
            ArrayList<String> queue = new ArrayList<>();
            String word;
            do {
                System.out.println("Enter a word (or 'exit' to finish):");
                word = kb.nextLine();
                if (!word.equalsIgnoreCase("exit")) {
                    queue.add(word);
                }
            } while (!word.equalsIgnoreCase("exit"));

            for (String qWord : queue) {
                PermutationsCalculator calculator = new PermutationsCalculator(dictionary);
                ArrayList<String> allPerms = calculator.allPermutations(qWord);
                ArrayList<String> uniquePerms = calculator.uniquePermutations(qWord);
                ArrayList<String> englishWords = calculator.uniqueWords(qWord);

                PW.write("Original Word: " + qWord + "\n");
                PW.write("All Permutations: " + allPerms + "\n");
                PW.write("Unique Permutations: " + uniquePerms + "\n");
                PW.write("English Words: " + englishWords + "\n\n");

                // Analytics calculations and writing to file goes here
            }

            // Overall analytics calculations and writing to file goes here

            PW.close();
            AW.close();
            kb.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
