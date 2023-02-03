package backend;

import javax.swing.text.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class SyntaxHighlighting {

    String[] blueSyntax = {
            "int", "char"
    };

    String[] greenSyntax = {
            "std"
    };

    String[] pinkSyntax = {
            "return"
    };

    public static StyleContext context = StyleContext.getDefaultStyleContext();
    public static AttributeSet blueAttribute = context.addAttribute(context.getEmptySet(), StyleConstants.Foreground, new Color(84,156,214,255));
    public static AttributeSet greenAttribute = context.addAttribute(context.getEmptySet(), StyleConstants.Foreground, new Color(78,201,176,255));
    public static AttributeSet pinkAttribute = context.addAttribute(context.getEmptySet(), StyleConstants.Foreground, new Color(197,134,192,255));
    public static AttributeSet defaultAttribute = context.addAttribute(context.getEmptySet(), StyleConstants.Foreground, new Color(203,216,228,255));
    public DefaultStyledDocument styleDocument = new DefaultStyledDocument() {
        public void insertString(int offset, String syntax, AttributeSet highlight) throws BadLocationException {
            super.insertString(offset, syntax, highlight);

            String text = getText(0, getLength());
            File keywordsFile = new File("src/backend/syntax/cpp_keywords.txt");
            int lastNonWord = findLastNonWordCharacter(text, offset);
            int firstNonWord = findFirstNonWordCharacter(text, offset + syntax.length());
            if(lastNonWord < 0)
                lastNonWord = 0;
            int wordLeft = lastNonWord;
            int wordRight = lastNonWord;

            while(wordRight <= firstNonWord) {
                // loop until wordRight finds a non-word character.
                if(wordRight == firstNonWord || String.valueOf(text.charAt(wordRight)).matches("\\W")) {
                    for(String keyword : blueSyntax) {
                        if(text.substring(wordLeft, wordRight).matches(keyword)) {
                            setCharacterAttributes(wordLeft, wordRight - wordLeft, blueAttribute, false);
                            break;
                        }
                    }
                    for(String keyword : greenSyntax) {
                        if(text.substring(wordLeft, wordRight).matches(keyword)) {
                            setCharacterAttributes(wordLeft, wordRight - wordLeft, greenAttribute, false);
                            break;
                        }
                    }
                    for(String keyword : pinkSyntax) {
                        if(text.substring(wordLeft, wordRight).matches(keyword)) {
                            setCharacterAttributes(wordLeft, wordRight - wordLeft, pinkAttribute, false);
                            break;
                        }
                    }
                }
                wordRight++;
            }
        }
    };

    private int findFirstNonWordCharacter(String text, int index) {
        // loops until index matches with a non-word character
        while(index < text.length()) {
            if(String.valueOf(text.charAt(index)).matches("\\W")) {
                break;
            }
            index++;
        }
        // return new index
        return index;
    }

    private int findLastNonWordCharacter(String text, int index) {
        // loops until the index matches with a non-word character
        while(index >= 0) {
            if(String.valueOf(text.charAt(index)).matches("\\W")) {
                break;
            }
            index--;
        }
        // return new index
        return index + 1;
    }


}
