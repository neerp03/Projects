import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        MorseCode morseObj = new MorseCode();
        String in;

        // User interface loop for encoding and decoding operations
        while (true) {
            System.out.println("Enter 'text' to convert to Morse code, 'Morse' to decode, or 'exit' to quit:");
            in = kb.nextLine();

            // Exit the loop if the user inputs 'exit'
            if (in.equalsIgnoreCase("exit")) {
                break;
            } 
            // Encode text to Morse code
            else if (in.equalsIgnoreCase("text")) {
                System.out.println("Enter text to encode:");
                in = kb.nextLine();
                System.out.println("Morse Code: " + morseObj.encode(in));
            } 
            // Decode Morse code to text
            else if (in.equalsIgnoreCase("morse")) {
                System.out.println("Enter Morse code to decode:");
                in = kb.nextLine();
                System.out.println("Text: " + morseObj.decode(in));
            } 
            // Handle invalid inputs
            else {
                System.out.println("Invalid input, please try again.");
            }
        }
        kb.close();
    }
}