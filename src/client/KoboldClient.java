package client;

import core.KoboldSettings;
import core.KoboldTokenizer;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class KoboldClient extends JFrame {

    KoboldEditor cEditor = new KoboldEditor();

    /**
     *  Initiates an empty Kobold client.
     */
    public KoboldClient() {
        this.setLayout(new BorderLayout());
        this.setSize(600, 600);
        this.setTitle("Kobold IDE");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /*
            FRAME ADDING
         */
        this.add(cEditor, BorderLayout.CENTER);

        this.setVisible(true);
    }

    /**
     *  Opens a file inside Kobold's client.
     *
     *  @param file The file Kobold will open.
     */
    public void open(File file) {
        try {
            KoboldTokenizer tokenizer = new KoboldTokenizer();
            Scanner fileScanner = new Scanner(file);

            while(fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                tokenizer.tokenize(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  Saves the current Kobold editor text to the file we're working with.
     */
    public static void save() {
        try {
            FileWriter fileWriter = new FileWriter(KoboldSettings.getCurrentFile());
            fileWriter.write(KoboldEditor.editor.getText());

            fileWriter.close();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}