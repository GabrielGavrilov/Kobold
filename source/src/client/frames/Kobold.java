package client.frames;

import client.panels.Editor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Kobold extends JFrame {

    /*
        IMPORT PANELS
     */

    Editor editor = new Editor();

    public Kobold() {
        this.setLayout(new BorderLayout());
        this.setSize(500, 500);
        this.setTitle("Kobold IDE");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /*
            ADDING
         */

        this.add(editor, BorderLayout.CENTER);

        this.setVisible(true);
    }

}