package client.panels;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.LineNumberReader;

public class Editor extends JPanel {

    public static JTextArea editorBox = new JTextArea();

    public Editor() {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);
        this.setBounds(0,0,500,500);

        Lines lines = new Lines();

        /*
            TEXT AREA
         */

        editorBox.setBounds(50, 0, 450, 500);
        editorBox.setFont(new Font("MONOSPACED", Font.PLAIN, 13));
        editorBox.setBackground(new Color(38,38,38,255));
        editorBox.setForeground(new Color(190,188,181,255));
        editorBox.setMargin(new Insets(3, 3, 0, 0));

        /*
            PANELS
         */

        JPanel separator = new JPanel();
        separator.setPreferredSize(new Dimension(1, 500));
        separator.setBackground(new Color(85,85,85,255));

        JPanel linesContents = new JPanel();
        linesContents.setLayout(new BorderLayout());
        linesContents.add(lines, BorderLayout.CENTER);
        linesContents.add(separator, BorderLayout.EAST);

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
