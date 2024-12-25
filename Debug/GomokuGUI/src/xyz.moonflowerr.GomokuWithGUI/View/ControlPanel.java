package xyz.moonflowerr.GomokuWithGUI.View;

import xyz.moonflowerr.GomokuWithGUI.Var;

import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel{
    JButton startButton = new JButton("投降");
    public ControlPanel(){
        super();
        setLayout(new GridLayout(1, 2));
        add(startButton);
        startButton.addActionListener(_ -> Var.controller.surrender());
    }
}
