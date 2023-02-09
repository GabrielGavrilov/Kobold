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

    /*
        GLOBAL CLASS COMPONENTS
     */
    private ArrayList<JLabel> lines = new ArrayList<>();
    private Font DejaVu;

    /**
     *  Initiates the lines for the editor.
     */
    public KoboldLines() {
        this.setLayout(new FlowLayout());
        this.setPreferredSize(new Dimension(50, 500));
        this.setBackground(new Color(43,43,43,255));

        /*
            FONT SETTINGS
         */
        try {

            DejaVu = Font.createFont(Font.PLAIN, new File("fonts/DejaVuSansMono.ttf")).deriveFont(15f);
            GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
            environment.registerFont(DejaVu);

        } catch(Exception e) {
            e.printStackTrace();
        }

        // initiates the first line.
        lines = generateLineList();
        renderLines(lines);

        listenForLines();
    }

    /**
     *  Returns the current line count inside the KoboldEditor.
     *
     *  @return int lineCount
     */
    private int getLineCount() {
        int lineCount = KoboldEditor.editor.getText().split("\n").length;
        return lineCount;
    }

    /**
     *  Generates an ArrayList of JLabels corresponding with the line count.
     *
     *  @return ArrayList lines
     */
    private ArrayList<JLabel> generateLineList() {
        int numberOfLines = getLineCount();
        ArrayList<JLabel> lines = new ArrayList<>();
        for(int i = 0; i < numberOfLines; i++) {
            JLabel line = new JLabel(Integer.toString(i + 1), SwingConstants.CENTER);
            line.setPreferredSize(new Dimension(50, 13));
            line.setForeground(new Color(96,99,102,255));
            line.setFont(DejaVu);
            lines.add(line);
        }
        return lines;
    }

    /**
     *  Renders the list of lines.
     *
     *  @param lineList The ArrayList of JLabels that Kobold will render.
     */
    private void renderLines(ArrayList<JLabel> lineList) {
        for(int i = 0; i < lineList.size(); i++) {
            this.add(lineList.get(i));
            this.revalidate();
            this.repaint();
        }
    }

    /**
     *  Destroys the list of lines.
     *
     *  @param lineList - The ArrayList of JLabels that Kobold will destroy.
     */
    private void destroyLines(ArrayList<JLabel> lineList) {
        for(int i = 0; i < lineList.size(); i++) {
            this.remove(lineList.get(i));
            this.revalidate();
            this.repaint();
        }
    }

    /**
     *  If Kobold detects a change in the line count, it will delete all the previous lines
     *  and re-render the new ones.
     */
    private void listenForLines() {
        Runnable checkIfLineCountHasChanged = new Runnable() {
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
        // checks if the line count has changed every 5 milliseconds.
        executor.scheduleAtFixedRate(checkIfLineCountHasChanged, 0, 5, TimeUnit.MILLISECONDS);
    }
}
