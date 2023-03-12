package client;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

public class KoboldClient extends JFrame {

    // private class variables
    private static int currentTab;

    // public class variables
    public static JTabbedPane koboldTabs = new JTabbedPane();

    /**
     * Initializes the Kobold client.
     */
    public KoboldClient() {
        this.setLayout(new BorderLayout());
        this.setSize(600, 600);
        this.setTitle("Kobold IDE");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /*
            JTABBEDPANE PROPERTIES
         */

        ChangeListener tabListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JTabbedPane source = (JTabbedPane) e.getSource();
                currentTab = source.getSelectedIndex();
            }
        };

        koboldTabs.addChangeListener(tabListener);
        koboldTabs.add(new KoboldEditor(new File("misc/welcome.txt")), "welcome.txt");

        /*
            ACTION MACROS
         */

        Action openNewTab = new OpenNewTab();

        koboldTabs.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK), "openNewTab");
        koboldTabs.getActionMap().put("openNewTab", openNewTab);

        /*
            ADDING
         */

        this.add(koboldTabs, BorderLayout.CENTER);

        this.setVisible(true);
    }

    /**
     * Macro function for opening a new tab.
     */
    public static class OpenNewTab extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fc = new JFileChooser();
            int i = fc.showDialog(koboldTabs, null);
            if(i == JFileChooser.APPROVE_OPTION) {
                File f = fc.getSelectedFile();
                koboldTabs.add(new KoboldEditor(f), f.getName());
            }
        }
    }

    /**
     * Macro function for closing the current tab.
     */
    public static class CloseCurrentTab extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            koboldTabs.remove(currentTab);
        }
    }

}
