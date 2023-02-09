package client;

import core.KoboldEditorSyntaxHighlighting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

public class KoboldEditor extends JPanel implements KeyListener {

    /*
        GLOBAL CLASS COMPONENTS
     */
    public static JTextPane editor = new JTextPane();

    /**
     *  Initiates the editor.
     */
    public KoboldEditor() {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);

        /*
            COMPONENT IMPORTS
         */
        KoboldLines cLines = new KoboldLines();
        KoboldEditorSyntaxHighlighting cSyntax = new KoboldEditorSyntaxHighlighting();

        /*
            FONT SETTINGS
         */
        Font DejaVu = null;

        try {

            DejaVu = Font.createFont(Font.PLAIN, new File("fonts/DejaVuSansMono.ttf")).deriveFont(15f);
            GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
            environment.registerFont(DejaVu);

        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
            TEXT PANE SETTINGS
         */
        editor = new JTextPane(cSyntax.styleDocument);
        editor.setBounds(50, 0, 450, 500);
        editor.setFont(DejaVu);
        editor.setBackground(new Color(43,43,43,255));
        editor.setForeground(new Color(203,216,228,255));
        editor.setMargin(new Insets(3, 5, 0, 0));
        editor.setCaretColor(Color.WHITE);
        editor.addKeyListener(this);

        /*
            PANEL SETTINGS
         */
        JPanel editorContents = new JPanel();
        editorContents.setLayout(new BorderLayout());
        editorContents.add(cLines, BorderLayout.WEST);
        editorContents.add(editor, BorderLayout.CENTER);

        /*
            SCROLL PANE SETTINGS
         */
        JScrollPane editorScrollPane = new JScrollPane(editorContents);
        editorScrollPane.setBorder(BorderFactory.createEmptyBorder());

        /*
            PANEL ADDING
         */
        this.add(editorScrollPane, BorderLayout.CENTER);

    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.isControlDown() && keyEvent.getKeyCode() == KeyEvent.VK_S) {
            KoboldClient.save();
            System.out.println("File saved.");
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
