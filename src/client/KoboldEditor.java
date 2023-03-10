package client;

import core.KoboldColors;
import core.KoboldSyntax;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class KoboldEditor extends JPanel {

    // private class variables
    private final File currentFileEditing;
    private final StyleContext context = StyleContext.getDefaultStyleContext();
    private AttributeSet attribute;

    // public class variables
    public JTextPane editor;

    /**
     * Initializes the Kobold editor.
     * @param file File
     */
    public KoboldEditor(File file) {
        currentFileEditing = file;
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);
        Font dejaVu = null;

        // creates the DejaVu font
        try {
            dejaVu = Font.createFont(Font.PLAIN, new File("misc/RobotoMono-Regular.ttf")).deriveFont(15f);
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
        editor.setBackground(KoboldColors.Colors.DARK.getColorValue());
        editor.setForeground(KoboldColors.Colors.GREY.getColorValue());
        editor.setMargin(new Insets(3, 5, 0, 0));
        editor.setCaretColor(Color.WHITE);

        /*
            FILE SCANNER PROPERTIES
            (Used to open the file inside the editor.)
         */

        try {
            Scanner fileScanner = new Scanner(currentFileEditing);

            while(fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine() + "\n";
                int offset = editor.getDocument().getEndPosition().getOffset() - 1;
                editor.getDocument().insertString(offset, line, null);
            }

        } catch (FileNotFoundException | BadLocationException e) {
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

        Action openNewTab = new KoboldClient.OpenNewTab();
        Action closeCurrentTab = new KoboldClient.CloseCurrentTab();
        Action saveCurrentTab = new KoboldClient.SaveCurrentTab();

        editor.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK), "openNewTab");
        editor.getActionMap().put("openNewTab", openNewTab);

        editor.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK), "closeCurrentTab");
        editor.getActionMap().put("closeCurrentTab", closeCurrentTab);

        editor.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK), "saveCurrentTab");
        editor.getActionMap().put("saveCurrentTab", saveCurrentTab);

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
            } else {
                attribute = context.addAttribute(context.getEmptySet(), StyleConstants.Foreground, KoboldColors.Colors.WHITE.getColorValue());
                setCharacterAttributes(0, rawText.length() + 1, attribute, false);
            }

        }

        /**
         * Initiates the tokenizer
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
                    if(fileType.equals("cpp") || fileType.equals("c")) {
                        char temp = peekCharacter();
                        if(temp == '/') {
                            int starPosition = currentPosition;
                            int endPosition = starPosition;
                            while(peekCharacter() != '\n') {
                                advanceCharacter();
                                endPosition+=1;
                            }

                            highlightWithEndPosition(starPosition, endPosition+1, KoboldColors.Colors.GREY.getColorValue());
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

                    highlightWithEndPosition(startPosition, endPosition + 2, KoboldColors.Colors.LIGHT_GREEN.getColorValue());

                }

                else if(currentChar == '\'') {
                    int startPosition = currentPosition;
                    int endPosition = startPosition;
                    advanceCharacter();
                    while(currentChar != '\'') {
                        if(currentPosition < source.length()) {
                            advanceCharacter();
                            endPosition++;
                        }
                        else {
                            endPosition = startPosition;
                            break;
                        }
                    }

                    highlightWithEndPosition(startPosition, endPosition + 2, KoboldColors.Colors.LIGHT_GREEN.getColorValue());

                }

                else if(currentChar == '#') {
                    if(fileType.equals("cpp") || fileType.equals("c")) {
                        int startPosition = currentPosition;
                        int endPosition = startPosition;
                        while(peekCharacter() != '\n') {
                            advanceCharacter();
                            endPosition+=1;
                        }

                        highlightWithEndPosition(startPosition, endPosition + 1, KoboldColors.Colors.GREEN.getColorValue());
                    }
                }


                else if(Character.isDigit(currentChar)) {
                    highlightSingle(currentPosition, KoboldColors.Colors.PINK.getColorValue());
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
                    highlightSingle(currentPosition, KoboldColors.Colors.WHITE.getColorValue());
                }

                advanceCharacter();
            }
        }

        /**
         * Checks if the given string matches with a keyword.
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
                case "c":
                    for(KoboldSyntax.CPP keyword : KoboldSyntax.CPP.values()) {
                        String key = keyword.toString().substring(1);
                        if(value.equals(key)) {
                            return keyword.getColor();
                        }
                    }
                    break;
            }

            return KoboldColors.Colors.WHITE;
        }

        /**
         * Highlights a selection based on the colour.
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
         * @param symbolPosition int
         * @param color Color
         */
        private void highlightSingle(int symbolPosition, Color color) {
            attribute = context.addAttribute(context.getEmptySet(), StyleConstants.Foreground, color);
            setCharacterAttributes(symbolPosition, 1, attribute, false);
        }

        /**
         * Highlights a word based on the colour.
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
        return this.currentFileEditing;
    }

    public void saveFile() {
        try {
            FileWriter fileWriter = new FileWriter(getFile());
            fileWriter.write(editor.getText());

            fileWriter.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the extension of the current file that is being edited.
     * @return String
     */
    public String getFileType() {
        return this.currentFileEditing.getName().split("\\.")[1];
    }

}
