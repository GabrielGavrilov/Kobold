package core;

import client.KoboldEditor;

import javax.swing.text.BadLocationException;

public class KoboldTokenizer {

    /*
        The Kobold Tokenizer is mainly used for writing text to the editor.
        The reason why we use a tokenizer and not just write directly to the editor is because without it,
        the syntax won't be highlighted when a file is opened.

        Hopefully in the future we could implement this class with the syntax highlighter.
     */

    private String source;
    private char currentChar;
    private int currentPos;
    private String temp;

    // "list" of special characters kobold renders when it opens a file.
    private String specialChars = "!@#$%^&*()_+{}|:<>?-=[]\\',./\"";

    /**
     *  Initiates the tokenizer.
     *
     *  @param input The string which the tokenizer will tokenize.
     *  @throws BadLocationException
     */
    public void tokenize(String input) throws BadLocationException {
        this.source = input + "\n";
        this.currentPos = -1;
        advanceCharacter();
        tokenize();
    }

    /**
     *  Advances the current character of the string.
     */
    private void advanceCharacter() {
        this.currentPos++;

        if(this.currentPos >= this.source.length())
            this.currentChar = '\0';
        else
            this.currentChar = this.source.charAt(currentPos);
    }

    /**
     *  Returns the next character of the string.
     *
     *  @return char this.source.charAt(currentPos + 1)
     */
    private char peekCharacter() {
        if(this.currentPos + 1 >= this.source.length())
            return '\0';

        return this.source.charAt(currentPos + 1);
    }

    /**
     *  Tokenizes the current character by checking if the current character equals to a "token".
     *
     * @throws BadLocationException
     */
    private void tokenize() throws BadLocationException {
        while(this.currentChar != '\0') {
            if(this.currentChar == ' ') {
                writeToEditor(" ");
            }

            else if(this.currentChar == '\t') {
                writeToEditor("\t");
            }

            else if(this.currentChar == ';') {
                writeToEditor(";");
            }

            else if(Character.isDigit(this.currentChar)) {
                writeToEditor(Character.toString(this.currentChar));
            }

            else if(this.specialChars.contains(Character.toString(this.currentChar))) {
                for(int i = 0; i < this.specialChars.length(); i++) {
                    if(this.currentChar == this.specialChars.charAt(i))
                        writeToEditor(Character.toString(this.currentChar));
                }
            }

            else if(Character.isAlphabetic(this.currentChar)) {
                this.temp = "";
                while(Character.isAlphabetic(peekCharacter())) {
                    this.temp += this.currentChar;
                    advanceCharacter();
                }

                this.temp += this.currentChar;

                writeToEditor(this.temp);
            }

            else if(this.currentChar == '\n') {
                writeToEditor("\n");
            }

            advanceCharacter();
        }
    }

    /**
     *  Writes the current string to the Kobold editor.
     *
     *  @param str The string which to write to the editor.
     *  @throws BadLocationException
     */
    private void writeToEditor(String str) throws BadLocationException {
        int offset = KoboldEditor.editor.getDocument().getEndPosition().getOffset() - 1;
        KoboldEditor.editor.getDocument().insertString(offset, str, null);
    }

}
