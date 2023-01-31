package backend;

import javax.swing.text.*;
import java.awt.*;
import java.util.regex.Pattern;

public class SyntaxHighlighting {

    /*

     */

    Pattern keywords = Pattern.compile("(\\W)*(" +
            "asm|" + "auto|" + "bool|" + "break|" + "case|" + "catch|" + "char|" + "char8_t|" + "char16_t|" +
            "char32_t|" + "class|" + "concept|" + "const|" + "continue|" + "co_await|" + "co_return|" + "co_yield|" +
            "default|" + "delete|" + "do|" + "double|" + "dynamic_cast|" + "else|" + "enum|" + "explicit|" + "export|" +
            "extern|" + "false|" + "float|" + "for|" + "friend|" + "goto|" + "if|" + "inline|" + "int|" + "long|" +
            "mutable|" + "namespace|" + "new|" + "nullptr|" + "operator|" + "private|" + "protected|" + "public|" +
            "register|" + "requires|" + "return|" + "short|" + "signed|" + "sizeof|" + "static|" + "static_cast|" +
            "struct|" + "switch|" + "template|" + "this|" + "thread_local|" + "throw|" + "true|" + "try|" + "typedef|" +
            "union|" + "unsigned|" + "using|" + "virtual|" + "void|" + "volatile|" + "wchar_t|" + "while|)"
    );


    public static StyleContext context = StyleContext.getDefaultStyleContext();
    public static AttributeSet attribute = context.addAttribute(context.getEmptySet(), StyleConstants.Foreground, Color.RED);
    public static AttributeSet defaultAttribute = context.addAttribute(context.getEmptySet(), StyleConstants.Foreground, new Color(203,216,228,255));
    public DefaultStyledDocument styleDocument = new DefaultStyledDocument() {
        public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
            super.insertString(offset, str, a);

            String text = getText(0, getLength());
            int before = findLastNonWordCharacter(text, offset);
            if(before < 0) before = 0;
            int after = findFirstNonWordCharacter(text, offset + str.length());
            int wordLeft = before;
            int wordRight = before;

            while(wordRight <= after) {
                if(wordRight == after || String.valueOf(text.charAt(wordRight)).matches("\\W")) {
                    if(text.substring(wordLeft, wordRight).matches(keywords.pattern())) {
                        setCharacterAttributes(wordLeft, wordRight - wordLeft, attribute, false);
                    } else {
                        setCharacterAttributes(wordLeft, wordRight - wordLeft, defaultAttribute, false);
                    }
                    wordLeft = wordRight;
                }
                wordRight++;
            }
        }

        public void remove(int offset, int length) throws BadLocationException {
            super.remove(offset, length);

            String text = getText(0, getLength());
            int before = findLastNonWordCharacter(text, offset);
            if(before < 0) before = 0;
            int after = findFirstNonWordCharacter(text, offset);

            if(text.substring(before, after).matches("(\\W)*(#include|int|return)")) {
                setCharacterAttributes(before, after - before, attribute, false);
            } else {
                setCharacterAttributes(before, after - before, defaultAttribute, false);
            }
        }
    };

    private int findFirstNonWordCharacter(String text, int index) {
        while(index < text.length()) {
            if(String.valueOf(text.charAt(index)).matches("\\W")) {
                break;
            }
            index++;
        }
        return index;
    }

    private int findLastNonWordCharacter(String text, int index) {
        while(--index >= 0) {
            if(String.valueOf(text.charAt(index)).matches("\\W")) {
                break;
            }
        }
        return index;
    }


}
