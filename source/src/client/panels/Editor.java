package client.panels;

import backend.SyntaxHighlighting;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Editor extends JPanel {

    public static JTextPane editorBox = new JTextPane();

    public Editor() {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);

        Lines lines = new Lines();
        SyntaxHighlighting syntax = new SyntaxHighlighting();

        /*
            FONT
         */

        Font DejaVu = null;
        try {
            DejaVu = Font.createFont(Font.PLAIN, new File("src/fonts/DejaVuSansMono.ttf")).deriveFont(15f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(DejaVu);
        } catch(IOException e) {
            e.printStackTrace();
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        }

        /*
            TEXT AREA
         */

        editorBox = new JTextPane(syntax.styleDocument);
        editorBox.setBounds(50, 0, 450, 500);
        editorBox.setFont(DejaVu);
        editorBox.setBackground(new Color(43,43,43,255));
        editorBox.setForeground(new Color(203,216,228,255));
        editorBox.setMargin(new Insets(3, 5, 0, 0));
        editorBox.setCaretColor(Color.WHITE);

        /*
            PANELS
         */

        /*
        JPanel separator = new JPanel();
        separator.setPreferredSize(new Dimension(1, 500));
        separator.setBackground(new Color(85,85,85,255));
         */

        JPanel linesContents = new JPanel();
        linesContents.setLayout(new BorderLayout());
        linesContents.add(lines, BorderLayout.CENTER);
        //linesContents.add(separator, BorderLayout.EAST);

        JPanel editorContents = new JPanel();
        editorContents.setLayout(new BorderLayout());
        editorContents.add(linesContents, BorderLayout.WEST);
        editorContents.add(editorBox, BorderLayout.CENTER);

        /*
            SCROLL PANE
         */

        JScrollPane editorScrollPane = new JScrollPane(editorContents);
        editorScrollPane.setBorder(BorderFactory.createEmptyBorder());

        /*
            ADDING
         */

        this.add(editorScrollPane, BorderLayout.CENTER);

    }

}
