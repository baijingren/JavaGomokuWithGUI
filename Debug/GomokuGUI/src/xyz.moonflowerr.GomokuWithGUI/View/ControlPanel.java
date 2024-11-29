package xyz.moonflowerr.GomokuWithGUI.View;

import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel{
    JButton startButton = new JButton("Start");
    JButton stopButton = new JButton("Stop");
    public ControlPanel(){
        super();
        setLayout(new GridLayout(1, 2));
        add(startButton);
        add(stopButton);
    }
}
