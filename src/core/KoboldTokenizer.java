package core;

import client.KoboldEditor;

import javax.swing.text.BadLocationException;

public class KoboldTokenizer {

    private KoboldEditor koboldEditor;

    private String source;
    private char currentChar;
    private int currentPos;
    private String temp;

    private String specialChars = "!@#$%^&*()_+{}|:<>?-=[]\\',./\"";

    public void tokenize(String input) throws BadLocationException {
        this.source = input + "\n";
        this.currentPos = -1;
        advanceCharacter();
        tokenize();
    }

    private void advanceCharacter() {
        this.currentPos++;

        if(this.currentPos >= this.source.length())
            this.currentChar = '\0';
        else
            this.currentChar = this.source.charAt(currentPos);
    }

    private char peekCharacter() {
        if(this.currentPos + 1 >= this.source.length())
            return '\0';

        return this.source.charAt(currentPos + 1);
    }

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

    private void writeToEditor(String str) throws BadLocationException {
        int offset = koboldEditor.editor.getDocument().getEndPosition().getOffset() - 1;
        koboldEditor.editor.getDocument().insertString(offset, str, null);
    }

}
