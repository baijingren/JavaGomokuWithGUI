package xyz.moonflowerr.GomokuWithGUI.View;


import xyz.moonflowerr.GomokuWithGUI.Var;

import javax.swing.*;
import java.awt.*;

/**
 * @author Jingren Bai
 * @package xyz.moonflowerr.GomokuWithGUI.View
 * @since openJDK 22
 */
public class IPconnectionPanel extends JPanel {
	private JLabel IPLabel;
	private JTextField IPField;
	private JButton connectButton;

	public IPconnectionPanel() {
		super();
		IPLabel = new JLabel("your IP:" + Var.IP);
		IPField = new JTextField(10);
		connectButton = new JButton("Connect");
		JPanel emptyPanel = new JPanel();

		connectButton.addActionListener(e -> {
			Var.network.connectToTarget(IPField.getText());
		});

		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.7;
		c.weighty = 0.5;
		c.gridheight = 3;
		add(IPLabel, c);

		c.gridx = 0;
		c.gridy = 3;
		c.weightx = 0.7;
		c.weighty = 0.5;
		add(IPField, c);

		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 0.3;
		c.weighty = 1.0/3.0;
		c.gridheight = 2;
		add(emptyPanel, c);

		c.gridx = 1;
		c.gridy = 2;
		c.weightx = 0.3;
		c.weighty = 1.0/3.0;
		c.gridheight = 2;
		add(connectButton, c);

		c.gridx = 1;
		c.gridy = 4;
		c.weightx = 0.3;
		c.weighty = 1.0/3.0;
		c.gridheight = 2;
		add(emptyPanel, c);

		setPreferredSize(new Dimension(200, 100));
	}

	public String getIP() {
		return IPField.getText();
	}

	public void setIP(String IP) {
		IPField.setText(IP);
	}

}
