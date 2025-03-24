public class MorseCode {
    // Array to store Morse code representations for A-Z
    String[] encoding = {".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", ".---",
            "-.-", ".-..", "--", "-.", "---", ".--.", "--.-", ".-.", "...", "-",
            "..-", "...-", ".--", "-..-", "-.--", "--.."};

    // Root node of the binary tree used for decoding Morse code
    BinTree decodingTree;

    // Constructor: Sets up the Morse code decoding tree
    public MorseCode() {
        buildDecodingTree();
    }

    // Builds the binary tree for Morse code decoding
    private void buildDecodingTree() {
        // Start with a root node representing a space character
        decodingTree = new BinTree(' '); 
        // Add each letter of the alphabet to the decoding tree
        for (char c = 'A'; c <= 'Z'; c++) {
            addCharacter(c, encoding[c - 'A']);
        }
    }

    // Adds a character and its Morse code to the decoding tree
    private void addCharacter(char character, String morse) {
        BinTree current = decodingTree;
        // Iterate through each dot/dash in the Morse code
        for (char ch : morse.toCharArray()) {
            // Navigate or create left node for dot
            if (ch == '.') {
                if (current.left == null) current.left = new BinTree(' ');
                current = current.left;
            } 
            // Navigate or create right node for dash
            else if (ch == '-') {
                if (current.right == null) current.right = new BinTree(' ');
                current = current.right;
            }
        }
        // Assign the character value to the terminal node
        current.value = character;
    }

    // Encodes a text message into Morse code
    public String encode(String msg) {
        StringBuilder encoded = new StringBuilder();
        // Convert the message to uppercase and iterate through each character
        for (char c : msg.toUpperCase().toCharArray()) {
            // Append a special character for spaces
            if (c == ' ') {
                encoded.append("# ");
            } 
            // Append the Morse code representation of the character
            else {
                encoded.append(encoding[c - 'A']).append(" ");
            }
        }
        return encoded.toString().trim();
    }

    // Decodes a Morse code message into text
    public String decode(String code) {
        StringBuilder decoded = new StringBuilder();
        // Split the Morse code into words separated by '#'
        String[] words = code.split("#");
        // Decode each word
        for (String word : words) {
            for (String morseChar : word.trim().split(" ")) {
                decoded.append(decodeCharacter(morseChar));
            }
            // Add a space after each word
            decoded.append(" ");
        }
        return decoded.toString().trim();
    }

    // Decodes a single Morse code sequence into its corresponding character
    private char decodeCharacter(String morseCode) {
        BinTree current = decodingTree;
        // Traverse the tree according to the dots and dashes in the Morse code
        for (char ch : morseCode.toCharArray()) {
            current = (ch == '.') ? current.left : current.right;
        }
        // Return the character stored at the terminal node
        return current.value;
    }
}