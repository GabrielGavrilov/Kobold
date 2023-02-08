package client.frames;

import backend.Tokenizer;
import client.panels.Editor;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Kobold extends JFrame {

    /*
        IMPORT PANELS
     */

    Editor editor = new Editor();

    public Kobold() {
        this.setLayout(new BorderLayout());
        this.setSize(600, 600);
        this.setTitle("Kobold IDE");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /*
            ADDING
         */

        this.add(editor, BorderLayout.CENTER);

        this.setVisible(true);
    }

    public void open(String file) {
        File fileToWrite = new File(file);

        try {
            Scanner fileScanner = new Scanner(fileToWrite);
            Tokenizer line = new Tokenizer();
            while (fileScanner.hasNextLine()) {
                line.tokenize(fileScanner.nextLine());
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }
    }
}