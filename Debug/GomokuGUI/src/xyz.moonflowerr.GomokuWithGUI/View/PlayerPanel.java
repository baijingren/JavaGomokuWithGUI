package xyz.moonflowerr.GomokuWithGUI.View;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class PlayerPanel extends JPanel {
    JLabel nameLabel;
    JLabel timeLabel;
    Image headImage;
    JLabel headImageLabel;

    public PlayerPanel(String name, URL headImageURL) {
        super();
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(200, 100));
        nameLabel = new JLabel(name);
        nameLabel.setMaximumSize(new Dimension(100, 50));
        headImage = new ImageIcon(headImageURL).getImage();
        headImageLabel = new JLabel(new ImageIcon(headImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH)));

		GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.5;
        c.weighty = 1;
        c.gridheight = 2;
        add(headImageLabel, c);

        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridheight = 1;
        add(nameLabel, c);

	}

    public PlayerPanel(String name) {
        super();
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(200, 100));
        nameLabel = new JLabel(name);
        nameLabel.setMaximumSize(new Dimension(100, 50));

		timeLabel = new JLabel("0");
        timeLabel.setMaximumSize(new Dimension(100, 50));

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 0.5;
        c.gridheight = 1;
        add(nameLabel, c);

        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1;
        c.weighty = 0.5;
        add(timeLabel, c);
    }

    public void setName(String name) {
        nameLabel.setText(name);
    }
}
