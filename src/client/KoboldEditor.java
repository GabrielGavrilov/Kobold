package client;

import core.KoboldColors;
import core.Old;
import core.KoboldSyntax;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class KoboldEditor extends JPanel {

    // actions
    private Action openNewTab;
    private Action closeCurrentTab;

    // private class variables
    private File fileEditing;
    private StyleContext context = StyleContext.getDefaultStyleContext();
    private AttributeSet attribute;

    // public class variables
    public JTextPane editor;

    /**
     * Initializes the Kobold editor.
     * @param file File
     */
    public KoboldEditor(File file) {
        fileEditing = file;
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);
        Font dejaVu = null;

        // creates the DejaVu font
        try {
            dejaVu = Font.createFont(Font.PLAIN, new File("misc/DejaVuSansMono.ttf")).deriveFont(15f);
            GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
            environment.registerFont(dejaVu);
        } catch(Exception e) {
            e.printStackTrace();
        }

        /*
            JTEXTPANE PROPERTIES
         */
        editor = new JTextPane(styleDocument);
        editor.setFont(dejaVu);
        editor.setBackground(KoboldColors.Colors.DARK_GRAY.getColorValue());
        editor.setForeground(KoboldColors.Colors.LIGHT.getColorValue());
        editor.setMargin(new Insets(3, 5, 0, 0));
        editor.setCaretColor(Color.WHITE);

        /*
            FILE SCANNER PROPERTIES
            (Used to open the file inside the editor.)
         */
        try {
            Scanner fileScanner = new Scanner(fileEditing);

            while(fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine() + "\n";
                int offset = editor.getDocument().getEndPosition().getOffset() - 1;
                editor.getDocument().insertString(offset, line, null);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }

        /*
            PANEL PROPERTIES
         */
        KoboldLines lines = new KoboldLines(this);
        JPanel editorContents = new JPanel();
        editorContents.setLayout(new BorderLayout());
        editorContents.add(lines, BorderLayout.WEST);
        editorContents.add(editor, BorderLayout.CENTER);

        /*
            JSCROLLPANE PROPERTIES
         */
        JScrollPane editorScrollPane = new JScrollPane(editorContents);
        editorScrollPane.setBorder(BorderFactory.createEmptyBorder());

        /*
            ACTION MACROS
         */
        openNewTab = new KoboldClient.OpenNewTab();

        editor.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK), "openNewTab");
        editor.getActionMap().put("openNewTab", openNewTab);

        /*
            ADDING
         */
        this.add(editorScrollPane, BorderLayout.CENTER);

    }

    /*
        SYNTAX HIGHLIGHTER
     */

    /**
     * Initializes the syntax highlighter for the editor.
     */
    private DefaultStyledDocument styleDocument = new DefaultStyledDocument() {

        String fileType;
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

            if (getFileType().equals("cpp")) {
                tokenize(rawText);
            }

        }

        /**
         * Initiates the tokenizer
         *
         * @param rawText String
         */
        private void tokenize(String rawText) {
            fileType = getFileType();
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
                    if(fileType.equals("cpp")) {
                        char temp = peekCharacter();
                        if(temp == '/') {
                            int starPosition = currentPosition;
                            int endPosition = starPosition;
                            while(peekCharacter() != '\n') {
                                advanceCharacter();
                                endPosition+=1;
                            }

                            highlightWithEndPosition(starPosition, endPosition+1, KoboldColors.Colors.GREEN.getColorValue());
                        }
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

                    highlightWithEndPosition(startPosition, endPosition + 2, KoboldColors.Colors.PEACH.getColorValue());

                }

                else if(currentChar == '#') {
                    if(fileType.equals("cpp")) {
                        int startPosition = currentPosition;
                        int endPosition = startPosition;
                        while(peekCharacter() != '\n') {
                            advanceCharacter();
                            endPosition+=1;
                        }

                        highlightWithEndPosition(startPosition, endPosition + 1, KoboldColors.Colors.GRAY.getColorValue());
                    }
                }


                else if(Character.isDigit(currentChar)) {
                    highlightSingle(currentPosition, KoboldColors.Colors.LIGHT_BLUE.getColorValue());
                }

                else if(Character.isAlphabetic(currentChar)) {
                    int startPosition = currentPosition;
                    String temp = "";
                    while(Character.isAlphabetic(peekCharacter())) {
                        temp += currentChar;
                        advanceCharacter();
                    }

                    temp += currentChar;
                    KoboldColors.Colors color = checkIfKeyword(temp);

                    highlightWord(startPosition, currentPosition, color.getColorValue());
                }

                else {
                    highlightSingle(currentPosition, KoboldColors.Colors.LIGHT.getColorValue());
                }

                advanceCharacter();
            }
        }

        /**
         * Checks if the given string matches with a keyword.
         *
         * @param value String
         * @return An integer based
         */
        private KoboldColors.Colors checkIfKeyword(String value) {
            switch(fileType) {
                case "cpp":
                    for(KoboldSyntax.CPP keyword : KoboldSyntax.CPP.values()) {
                        String key = keyword.toString().substring(1);
                        if(value.equals(key)) {
                            return keyword.getColor();
                        }
                    }
                    break;
            }

            return KoboldColors.Colors.LIGHT;
        }

        /**
         * Highlights a selection based on the colour.
         *
         * @param startPosition int
         * @param endPosition int
         * @param color Color
         */
        private void highlightWithEndPosition(int startPosition, int endPosition, Color color) {
            attribute = context.addAttribute(context.getEmptySet(), StyleConstants.Foreground, color);
            setCharacterAttributes(startPosition, endPosition - startPosition, attribute, false);
        }

        /**
         * Highlights a single character based on the colour.
         *
         * @param symbolPosition int
         * @param color Color
         */
        private void highlightSingle(int symbolPosition, Color color) {
            attribute = context.addAttribute(context.getEmptySet(), StyleConstants.Foreground, color);
            setCharacterAttributes(symbolPosition, 1, attribute, false);
        }

        /**
         * Highlights a word based on the colour.
         *
         * @param startPosition int
         * @param endPosition int
         * @param color int
         */
        private void highlightWord(int startPosition, int endPosition, Color color ) {
            attribute = context.addAttribute(context.getEmptySet(), StyleConstants.Foreground, color);
            setCharacterAttributes(startPosition, endPosition - startPosition + 1, attribute, false);
        }

    };

    /**
     * Returns the current file that is being edited.
     * @return File
     */
    public File getFile() {
        return this.fileEditing;
    }

    /**
     * Returns the extension of the current file that is being edited.
     * @return String
     */
    public String getFileType() {
        return this.fileEditing.getName().split("\\.")[1];
    }

}
