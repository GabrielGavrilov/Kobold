package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

public class KoboldClient extends JFrame {

    private Action openNewTab;

    public static JTabbedPane koboldTabs = new JTabbedPane();

    public KoboldClient() {
        this.setLayout(new BorderLayout());
        this.setSize(600, 600);
        this.setTitle("Kobold IDE");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.add(koboldTabs, BorderLayout.CENTER);

        koboldTabs.add(new KoboldEditor(new File("misc/welcome.txt")), "Welcome");

        openNewTab = new OpenNewTab();

        koboldTabs.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK), "openNewTab");
        koboldTabs.getActionMap().put("openNewTab", openNewTab);

        this.setVisible(true);
    }

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

}
