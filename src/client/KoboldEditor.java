package client;

import core.KoboldEditorSyntaxHighlighting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

public class KoboldEditor extends JPanel {

    Action saveAction;

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

        saveAction = new SaveAction();

        editor.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK), "saveAction");
        editor.getActionMap().put("saveAction", saveAction);

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

    public class SaveAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            KoboldClient.save();
            System.out.println("Saved.");
        }
    }

}
