package client.panels;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Lines extends JPanel {

    ArrayList<JLabel> lines = new ArrayList<>();

    public Lines() {
        this.setLayout(new FlowLayout());
        this.setPreferredSize(new Dimension(50, 500));
        this.setBackground(new Color(43, 43, 43,255));

        lines = generateLineList();
        renderLines(lines);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if(lines.size() > Editor.editorBox.getLineCount() || lines.size() < Editor.editorBox.getLineCount()) {
                    destroyLines(lines);
                    lines = generateLineList();
                    renderLines(lines);
                }
            }
        };

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(runnable, 0, 5, TimeUnit.NANOSECONDS);
    }

    private ArrayList<JLabel> generateLineList() {
        int numberOfLines = Editor.editorBox.getLineCount();
        ArrayList<JLabel> lines = new ArrayList<>();
        int temp = 0;
        for(int i = 0; i < numberOfLines; i++) {
            JLabel line = new JLabel(Integer.toString(i + 1), SwingConstants.CENTER);
            line.setPreferredSize(new Dimension(50, 12));
            line.setForeground(new Color(94,98,101,255));
            line.setFont(new Font("ARIAL", Font.PLAIN, 13));
            lines.add(line);
            temp+=2;
        }
        return lines;
    }

    private void renderLines(ArrayList<JLabel> lineList) {
        for(int i = 0; i < lineList.size(); i++) {
            this.add(lineList.get(i));
            this.revalidate();
            this.repaint();
        }
    }

    private void destroyLines(ArrayList<JLabel> lineList) {
        for(int i = 0; i < lineList.size(); i++) {
            this.remove(lineList.get(i));
            this.revalidate();
            this.repaint();
        }
    }
}
