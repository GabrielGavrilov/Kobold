package client.panels;

import core.SyntaxHighlighting;
import client.frames.Kobold;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

public class Editor extends JPanel {

    // GLOBAL CLASS COMPONENTS
    public JTextPane editor = new JTextPane();

    /**
     *  Initiates the editor.
     */
    public Editor() {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);

        // COMPONENT IMPORTS
        Lines lines = new Lines();
        SyntaxHighlighting syntax = new SyntaxHighlighting();

        // FONT SETTINGS
        Font DejaVu = null;

        try {

            DejaVu = Font.createFont(Font.PLAIN, new File("src/fonts/DejaVuSansMono.ttf")).deriveFont(15f);
            GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
            environment.registerFont(DejaVu);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // TEXT PANE SETTINGS
        editor = new JTextPane(syntax.styleDocument);
        editor.setBounds(50, 0, 450, 500);
        editor.setFont(DejaVu);
        editor.setBackground(new Color(43,43,43,255));
        editor.setForeground(new Color(203,216,228,255));
        editor.setMargin(new Insets(3, 5, 0, 0));
        editor.setCaretColor(Color.WHITE);

        // PANEL SETTINGS
        JPanel linesContents = new JPanel();
        linesContents.setLayout(new BorderLayout());
        linesContents.add(lines, BorderLayout.CENTER);

        JPanel editorContents = new JPanel();
        editorContents.setLayout(new BorderLayout());
        editorContents.add(linesContents, BorderLayout.WEST);
        editorContents.add(editor, BorderLayout.CENTER);

        // SCROLL PANE SETTINGS
        JScrollPane editorScrollPane = new JScrollPane(editorContents);
        editorScrollPane.setBorder(BorderFactory.createEmptyBorder());

        // ADDING
        this.add(editorScrollPane, BorderLayout.CENTER);

    }

}
