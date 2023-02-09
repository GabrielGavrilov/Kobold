package client;

import javax.swing.*;
import java.awt.*;

public class KoboldClient extends JFrame {

    // PANEL IMPORTS
    KoboldEditor editor = new KoboldEditor();

    /**
     *  Initiates an empty Kobold client.
     */
    public KoboldClient() {
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