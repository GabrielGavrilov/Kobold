package client;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class KoboldLines extends JPanel {

    ArrayList<JLabel> lines = new ArrayList<>();
    Font DejaVu;

    public KoboldLines() {
        this.setLayout(new FlowLayout());
        this.setPreferredSize(new Dimension(50, 500));
        this.setBackground(new Color(43,43,43,255));

        try {
            DejaVu = Font.createFont(Font.PLAIN, new File("fonts/DejaVuSansMono.ttf")).deriveFont(15f);
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
        int lineCount = KoboldEditor.editor.getText().split("\n").length;
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
