package p;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

public class PermutationsCalculator {
    private Stack<String> stack;
    private ArrayList<String> allPermutations;
    private ArrayList<String> uniquePermutations;
    private ArrayList<String> uniqueEnglishWords;
    private Dictionary dictionary;
    
    public PermutationsCalculator(Dictionary dictionary) {
        this.dictionary = dictionary;
        stack = new Stack<>();
        allPermutations = new ArrayList<>();
        uniquePermutations = new ArrayList<>();
        uniqueEnglishWords = new ArrayList<>();
    }

    public ArrayList<String> allPermutations(String word) {
        stack.push(word + "+");
        while (!stack.isEmpty()) {
            String str = stack.pop();
            int plusIndex = str.indexOf("+");
            if (plusIndex == str.length() - 1) {
                allPermutations.add(str.replace("+", ""));
            } else {
                for (int i = plusIndex + 1; i < str.length(); i++) {
                    String newStr = str.substring(0, plusIndex) + str.charAt(i) +
                            str.substring(plusIndex, i) + str.substring(i + 1);
                    stack.push(newStr);
                }
            }
        }
        return allPermutations;
    }

    public ArrayList<String> uniquePermutations(String word) {
        HashSet<String> set = new HashSet<>(allPermutations(word));
        uniquePermutations.addAll(set);
        return uniquePermutations;
    }

    public ArrayList<String> uniqueWords(String word) {
        for (String temp : uniquePermutations(word)) {
            if (dictionary.lookup(temp)) {
                uniqueEnglishWords.add(temp);
            }
        }
        return uniqueEnglishWords;
    }

    public int numDuplicates() {
        return allPermutations.size() - uniquePermutations.size();
    }
}

