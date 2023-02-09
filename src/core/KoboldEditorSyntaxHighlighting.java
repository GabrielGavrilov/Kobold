package core;

import javax.swing.text.*;
import java.awt.*;
import java.io.File;
import java.util.Scanner;

public class KoboldEditorSyntaxHighlighting {

    public static StyleContext context = StyleContext.getDefaultStyleContext();
    public static AttributeSet attribute;

    public DefaultStyledDocument styleDocument = new DefaultStyledDocument() {
        public void insertString(int offset, String syntax, AttributeSet highlight) throws BadLocationException {
            super.insertString(offset, syntax, highlight);

            String text = getText(0, getLength());
            File keywordsFile = new File("syntax/cpp_keywords.txt");
            int lastNonWord = findLastNonWordCharacter(text, offset);
            int firstNonWord = findFirstNonWordCharacter(text, offset + syntax.length());
            if(lastNonWord < 0)
                lastNonWord = 0;
            int wordLeft = lastNonWord;
            int wordRight = lastNonWord;

            while(wordRight <= firstNonWord) {
                // loop until wordRight finds a non-word character.
                if(wordRight == firstNonWord || String.valueOf(text.charAt(wordRight)).matches("\\W")) {
                    try {
                        Scanner keywordsScanner = new Scanner(keywordsFile);
                        while(keywordsScanner.hasNext()) {
                            String currentKeyword = keywordsScanner.next();

                            String keyword = currentKeyword.split("=")[0];
                            String colour = currentKeyword.split("=")[1].toLowerCase();

                            if(text.substring(wordLeft, wordRight).matches(keyword) && colour.equals("blue")) {
                                attribute = context.addAttribute(context.getEmptySet(), StyleConstants.Foreground, new Color(84,156,214,255));
                                setCharacterAttributes(wordLeft, wordRight - wordLeft, attribute, false);
                                break;
                            }
                            else if(text.substring(wordLeft, wordRight).matches(keyword) && colour.equals("green")) {
                                attribute = context.addAttribute(context.getEmptySet(), StyleConstants.Foreground, new Color(78,201,176,255));
                                setCharacterAttributes(wordLeft, wordRight - wordLeft, attribute, false);
                                break;
                            }
                            else if(text.substring(wordLeft, wordRight).matches(keyword) && colour.equals("pink")) {
                                attribute = context.addAttribute(context.getEmptySet(), StyleConstants.Foreground, new Color(197,134,192,255));
                                setCharacterAttributes(wordLeft, wordRight - wordLeft, attribute, false);
                                break;
                            }
                            else {
                                attribute = context.addAttribute(context.getEmptySet(), StyleConstants.Foreground, new Color(203,216,228,255));
                                setCharacterAttributes(wordLeft, wordRight - wordLeft, attribute, false);
                            }

                        }

                    } catch(Exception e) {
                        e.printStackTrace();
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