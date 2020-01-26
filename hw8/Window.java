package hw8;


import javax.swing.*;
import java.awt.*;

public class Window {
    public static JFrame frame;


    public Window(int width, int height, String title, PathVisualizer pathVisualizer) {
        frame = new JFrame(title);
        frame.setPreferredSize(new Dimension(width, height));
        //frame.setPreferredSize(new Dimension(1920, 1080));
        frame.setMaximumSize(new Dimension(width, height));
        //frame.setMaximumSize(new Dimension(1920, 1080));
        frame.setMinimumSize(new Dimension(width, height));
        //frame.setMinimumSize(new Dimension(1920, 1080));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.add(pathVisualizer);
        frame.setVisible(true);
        //GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(frame);
        pathVisualizer.start();
    }
}
