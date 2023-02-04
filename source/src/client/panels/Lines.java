package client.panels;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.Utilities;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Lines extends JPanel {

    ArrayList<JLabel> lines = new ArrayList<>();
    Font DejaVu;

    public Lines() {
        this.setLayout(new FlowLayout());
        this.setPreferredSize(new Dimension(50, 500));
        this.setBackground(new Color(43,43,43,255));

        try {
            DejaVu = Font.createFont(Font.PLAIN, new File("src/fonts/DejaVuSansMono.ttf")).deriveFont(15f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(DejaVu);
        } catch(IOException e) {
            e.printStackTrace();
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        }

        lines = generateLineList();
        renderLines(lines);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if(lines.size() > getLineCount() || lines.size() < getLineCount()) {
                    destroyLines(lines);
                    lines = generateLineList();
                    renderLines(lines);
                }
            }
        };

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(runnable, 0, 5, TimeUnit.NANOSECONDS);
    }


    private int getLineCount() {
//        int totalChars = Editor.editorBox.getText().length();
//        int lineCount = (totalChars == 0) ? 1: 0;
//
//        try {
//            int offset = totalChars;
//            while(offset > 0) {
//                offset = Utilities.getRowStart(Editor.editorBox, offset) - 1;
//                lineCount++;
//            }
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
        int lineCount = Editor.editorBox.getText().split("\n").length;
        return lineCount;
    }

    private ArrayList<JLabel> generateLineList() {
        int numberOfLines = getLineCount();
        ArrayList<JLabel> lines = new ArrayList<>();
        int temp = 0;
        for(int i = 0; i < numberOfLines; i++) {
            JLabel line = new JLabel(Integer.toString(i + 1), SwingConstants.CENTER);
            line.setPreferredSize(new Dimension(50, 13));
            line.setForeground(new Color(96,99,102,255));
            line.setFont(DejaVu);
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
