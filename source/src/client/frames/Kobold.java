package client.frames;

import core.Tokenizer;
import client.panels.Editor;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Kobold extends JFrame {

    // PANEL IMPORTS
    Editor editor = new Editor();

    /**
     *  Initiates an empty Kobold client.
     */
    public Kobold() {
        this.setLayout(new BorderLayout());
        this.setSize(600, 600);
        this.setTitle("Kobold IDE");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ADDING
        this.add(editor, BorderLayout.CENTER);

        this.setVisible(true);
    }

    /**
     *  Opens a file inside Kobold's client.
     *
     *  @param file The file Kobold will open.
     */
    public void open(String file) {

    }

}