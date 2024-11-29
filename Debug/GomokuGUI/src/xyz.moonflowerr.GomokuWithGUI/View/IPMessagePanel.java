package xyz.moonflowerr.GomokuWithGUI.View;

import xyz.moonflowerr.GomokuWithGUI.Var;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IPMessagePanel extends JPanel {
    JLabel IPLabel;
    JTextField nameTextField;
    JButton confirmButton;

    public IPMessagePanel(String name, String IP) {
        super();
        IPLabel = new JLabel("IP:" + IP);
        nameTextField = new JTextField("Name:" + name);
        confirmButton = new JButton("Confirm");

        nameTextField.setEditable(false);

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Var.opponentName = name;
                Var.IP = IP;
            }
        });
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.8;
        c.weighty = 0.5;
        add(nameTextField,c);

        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0.8;
        c.weighty = 0.5;
        add(IPLabel,c);

        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 0.2;
        c.weighty = 0.5;
        c.gridheight = 2;
        add(confirmButton,c);
    }
}
