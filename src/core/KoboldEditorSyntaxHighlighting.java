package core;

import javax.swing.text.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class KoboldEditorSyntaxHighlighting {

    public static StyleContext context = StyleContext.getDefaultStyleContext();
    public static AttributeSet attribute;

    public DefaultStyledDocument styleDocument = new DefaultStyledDocument() {

        int currentPosition;
        char currentChar;
        String source;

        public void insertString(int offset, String syntax, AttributeSet highlight) throws BadLocationException {
            super.insertString(offset, syntax, highlight);

            String rawText = getText(0, getLength());

            tokenize(rawText);
        }

        private void tokenize(String rawText) {
            source = rawText + "\n";
            currentPosition = -1;
            advanceCharacter();
            tokenizeRawSource();
        }

        private void advanceCharacter() {
            currentPosition++;

            if(currentPosition >= source.length())
                currentChar = '\0';

            else
                currentChar = source.charAt(currentPosition);
        }

        private char peekCharacter() {
            if(currentPosition + 1 >= source.length())
                return '\0';

            return source.charAt(currentPosition + 1);
        }

        private void tokenizeRawSource() {
            while(currentChar != '\0') {

                if(currentChar == '/') {
                    char temp = peekCharacter();
                    if(temp == '/') {
                        int starPosition = currentPosition;
                        while(peekCharacter() != '\n') {
                            advanceCharacter();
                        }

                        highlightComment(starPosition, currentPosition+1);
                    }
                }

                else if(currentChar == '\"') {
                    int startPosition = currentPosition;
                    int endPosition = startPosition;
                    advanceCharacter();
                    while(currentChar != '\"') {
                        if(currentPosition <= source.length()) {
                            advanceCharacter();
                            endPosition++;
                        }
                        else {
                            endPosition = startPosition;
                            break;
                        }
                    }

                    highlightComment(startPosition, endPosition + 2);

                }

                else if(currentChar == '(' || currentChar == ')') {
                    highlightSymbol(currentPosition);
                }

                else if(currentChar == '{' || currentChar == '}') {
                    highlightSymbol(currentPosition);
                }

                else if(currentChar == '0' || currentChar == '1') {
                    highlightDigit(currentPosition);
                }

                advanceCharacter();
            }
        }

        private void highlightComment(int startPosition, int endPosition) {
            attribute = context.addAttribute(context.getEmptySet(), StyleConstants.Foreground, new Color(88,152,75,255));
            setCharacterAttributes(startPosition, endPosition - startPosition, attribute, false);
        }

        private void highlightSymbol(int symbolPosition) {
            attribute = context.addAttribute(context.getEmptySet(), StyleConstants.Foreground, new Color(236,216,41,255));
            setCharacterAttributes(symbolPosition, 1, attribute, false);
        }

        private void highlightDigit(int symbolPosition) {
            attribute = context.addAttribute(context.getEmptySet(), StyleConstants.Foreground, new Color(78,201,176,255));
            setCharacterAttributes(symbolPosition, 1, attribute, false);
        }

        private void highlightSyntax() {

        }

    };
}