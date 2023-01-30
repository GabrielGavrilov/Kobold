package client.panels;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.io.LineNumberReader;

public class Editor extends JPanel {

    public static JTextPane editorBox = new JTextPane();

    public Editor() {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);

        Lines lines = new Lines();

        /*
            DOCUMENT STYLING
         */

        StyleContext context = StyleContext.getDefaultStyleContext();
        AttributeSet attribute = context.addAttribute(context.getEmptySet(), StyleConstants.Foreground, Color.RED);
        AttributeSet defaultAttribute = context.addAttribute(context.getEmptySet(), StyleConstants.Foreground, new Color(203,216,228,255));
        DefaultStyledDocument styleDocument = new DefaultStyledDocument() {
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
                        if(text.substring(wordLeft, wordRight).matches("(\\W)*(#include|int|return)")) {
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

        /*
            FONT
         */

        Font DejaVu = null;
        try {
            DejaVu = Font.createFont(Font.PLAIN, new File("src/fonts/DejaVuSansMono.ttf")).deriveFont(15f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(DejaVu);
        } catch(IOException e) {
            e.printStackTrace();
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        }

        /*
            TEXT AREA
         */

        editorBox = new JTextPane(styleDocument);
        editorBox.setBounds(50, 0, 450, 500);
        editorBox.setFont(DejaVu);
        editorBox.setBackground(new Color(48,56,65,255));
        editorBox.setForeground(new Color(203,216,228,255));
        editorBox.setMargin(new Insets(3, 5, 0, 0));
        editorBox.setCaretColor(Color.WHITE);

        /*
            PANELS
         */

        /*
        JPanel separator = new JPanel();
        separator.setPreferredSize(new Dimension(1, 500));
        separator.setBackground(new Color(85,85,85,255));
         */

        JPanel linesContents = new JPanel();
        linesContents.setLayout(new BorderLayout());
        linesContents.add(lines, BorderLayout.CENTER);
        //linesContents.add(separator, BorderLayout.EAST);

        JPanel editorContents = new JPanel();
        editorContents.setLayout(new BorderLayout());
        editorContents.add(linesContents, BorderLayout.WEST);
        editorContents.add(editorBox, BorderLayout.CENTER);

        /*
            SCROLL PANE
         */

        JScrollPane editorScrollPane = new JScrollPane(editorContents);
        editorScrollPane.setBorder(BorderFactory.createEmptyBorder());

        /*
            ADDING
         */

        this.add(editorScrollPane, BorderLayout.CENTER);

    }

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
