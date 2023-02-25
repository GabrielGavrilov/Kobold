package core;

import javax.swing.text.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class KoboldEditorSyntaxHighlighting {

    public static StyleContext context = StyleContext.getDefaultStyleContext();
    public static AttributeSet attribute;

    public DefaultStyledDocument styleDocument = new DefaultStyledDocument() {

        int currentPosition;
        char currentChar;
        String source;

        /**
         * Inserts a string to the Kobold Editor
         *
         * @param offset int
         * @param syntax String
         * @param highlight AttributeSet
         * @throws BadLocationException
         */
        public void insertString(int offset, String syntax, AttributeSet highlight) throws BadLocationException {
            super.insertString(offset, syntax, highlight);
            String rawText = getText(0, getLength());

            switch (KoboldSettings.getFileType()) {
                case "cpp":
                    tokenize(rawText);
                    break;
            }

        }

        /**
         * Initiates the tokenizer
         *
         * @param rawText String
         */
        private void tokenize(String rawText) {
            source = rawText + "\n";
            currentPosition = -1;
            advanceCharacter();
            tokenizeRawSource();
        }

        /**
         *  Advances the current character of the raw source.
         */
        private void advanceCharacter() {
            currentPosition++;

            if(currentPosition >= source.length())
                currentChar = '\0';

            else
                currentChar = source.charAt(currentPosition);
        }

        /**
         *  Returns the next character of the raw source.
         *
         *  @return next character
         */
        private char peekCharacter() {
            if(currentPosition + 1 >= source.length())
                return '\0';

            return source.charAt(currentPosition + 1);
        }

        /**
         *  Tokenizes the current character by checking if it equals to a "token".
         */
        private void tokenizeRawSource() {
            while(currentChar != '\0') {

                if(currentChar == '/') {
                    char temp = peekCharacter();
                    if(temp == '/') {
                        int starPosition = currentPosition;
                        int endPosition = starPosition;
                        while(peekCharacter() != '\n') {
                            advanceCharacter();
                            endPosition+=1;
                        }

                        highlightWithEndPosition(starPosition, endPosition+1, new Color(88,152,75,255));
                    }
                }

                else if(currentChar == '\"') {
                    int startPosition = currentPosition;
                    int endPosition = startPosition;
                    advanceCharacter();
                    while(currentChar != '\"') {
                        if(currentPosition < source.length()) {
                            advanceCharacter();
                            endPosition++;
                        }
                        else {
                            endPosition = startPosition;
                            break;
                        }
                    }

                    highlightWithEndPosition(startPosition, endPosition + 2, new Color(206,145,120,255));

                }

                else if(currentChar == '0' || currentChar == '1') {
                    highlightSingle(currentPosition, new Color(78,201,176,255));
                }

                else if(Character.isAlphabetic(currentChar)) {
                    int startPosition = currentPosition;
                    String temp = "";
                    while(Character.isAlphabetic(peekCharacter())) {
                        temp += currentChar;
                        advanceCharacter();
                    }

                    temp += currentChar;
                    int colour = checkIfKeyword(temp);

                    highlightWord(startPosition, currentPosition, colour);
                }

                else {
                    highlightSingle(currentPosition, new Color(203,216,228,255));
                }

                advanceCharacter();
            }
        }

        /**
         * Checks if the given string matches with a keyword.
         *
         * @param keyword String
         * @return An integer based
         */
        private int checkIfKeyword(String keyword) {
            try {
                Scanner fileScanner = new Scanner(new File("syntax/null_keywords.txt"));

                if(KoboldSettings.getFileType().equals("cpp")) {
                    fileScanner = new Scanner(new File("syntax/cpp_keywords.txt"));
                }

                while(fileScanner.hasNext()) {
                    String data = fileScanner.next();
                    String[] info = data.split("=");
                    if(keyword.equals(info[0])) {
                        switch(info[1]) {
                            case "blue":
                                return 1;
                            case "green":
                                return 2;
                            case "pink":
                                return 3;
                        }
                    }
                }

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            return 0;
        }

        /**
         * Highlights a selection based on the colour.
         *
         * @param startPosition int
         * @param endPosition int
         * @param colour Color
         */
        private void highlightWithEndPosition(int startPosition, int endPosition, Color colour) {
            attribute = context.addAttribute(context.getEmptySet(), StyleConstants.Foreground, colour);
            setCharacterAttributes(startPosition, endPosition - startPosition, attribute, false);
        }

        /**
         * Highlights a single character based on the colour.
         *
         * @param symbolPosition int
         * @param colour Color
         */
        private void highlightSingle(int symbolPosition, Color colour) {
            attribute = context.addAttribute(context.getEmptySet(), StyleConstants.Foreground, colour);
            setCharacterAttributes(symbolPosition, 1, attribute, false);
        }

        /**
         * Highlights a word based on the colour.
         *
         * @param startPosition int
         * @param endPosition int
         * @param colour int
         */
        private void highlightWord(int startPosition, int endPosition, int colour) {
            switch(colour) {
                case 1:
                    attribute = context.addAttribute(context.getEmptySet(), StyleConstants.Foreground, new Color(84,156,214,255));
                    setCharacterAttributes(startPosition, endPosition - startPosition + 1, attribute, false);
                    break;
                case 2:
                    attribute = context.addAttribute(context.getEmptySet(), StyleConstants.Foreground, new Color(78,201,176,255));
                    setCharacterAttributes(startPosition, endPosition - startPosition + 1, attribute, false);
                    break;
                case 3:
                    attribute = context.addAttribute(context.getEmptySet(), StyleConstants.Foreground, new Color(197,134,192,255));
                    setCharacterAttributes(startPosition, endPosition - startPosition + 1, attribute, false);
                    break;
                default:
                    attribute = context.addAttribute(context.getEmptySet(), StyleConstants.Foreground, new Color(203,216,228,255));
                    setCharacterAttributes(startPosition, endPosition - startPosition + 1, attribute, false);
                    break;
            }
        }

    };
}