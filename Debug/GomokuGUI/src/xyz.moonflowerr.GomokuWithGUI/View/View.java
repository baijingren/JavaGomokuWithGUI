/**
 * @author moonflowerr
 * @package xyz.moonflowerr.GomokuWithGUI.View
 * @since openJDK 22
 */
package xyz.moonflowerr.GomokuWithGUI.View;

import xyz.moonflowerr.GomokuWithGUI.LogPrinter;
import xyz.moonflowerr.GomokuWithGUI.Network.*;
import xyz.moonflowerr.GomokuWithGUI.Var;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class View implements ConnectionListener {
	/// boardPanel 棋盘的视图
	BoardPanel boardPanel;
	/// chatPanel 聊天的视图
	ChatPanel chatPanel;
	/// hostPlayerPanel 主机玩家的信息面板
	PlayerPanel hostPlayerPanel;
	/// guestPlayerPanel 对手玩家的信息面板
	PlayerPanel guestPlayerPanel;
	/// controlPanel 控制面板
	ControlPanel controlPanel;
	/// connected 连接状态
	Boolean connected = false;

	JFrame templateFrame;

	IPConnectionPanel ipPanel;

	public View() {
		super();
		Var.network.addConnectionListener(this);
	}

	/// 创建主窗口，包括棋盘、聊天框、玩家信息面板、控制面板
	public void createWindow() {
		JFrame frame = new JFrame("五子棋");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1200, 900);
		frame.setLayout(new GridBagLayout());

		URL blackURL = getClass().getResource("/Black.png");
		URL whiteURL = getClass().getResource("/White.png");

		if(blackURL == null || whiteURL == null){
			LogPrinter.printSevere("Cannot find image resources.");
			hostPlayerPanel = new PlayerPanel(Var.name);
			guestPlayerPanel = new PlayerPanel(Var.opponentName);
		}
		else if(Var.youAreBlack){
			hostPlayerPanel = new PlayerPanel(Var.name, blackURL);
			guestPlayerPanel = new PlayerPanel(Var.opponentName, whiteURL);
		}
		else{
			hostPlayerPanel = new PlayerPanel(Var.name, whiteURL);
			guestPlayerPanel = new PlayerPanel(Var.opponentName, blackURL);
		}

		boardPanel = new BoardPanel();
		chatPanel = new ChatPanel();
		controlPanel = new ControlPanel();

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;

		c.gridx = 0;
		c.gridy = 0;
		c.weightx = (7.0 / 9.0);
		c.weighty = (11.0 / 12.0);
		c.gridwidth = 2;
		frame.add(boardPanel, c);

		c.gridx = 2;
		c.gridy = 0;
		c.weightx = (2.0 / 9.0);
		c.weighty = (11.0 / 12.0);
		c.gridwidth = 1;
		frame.add(chatPanel, c);

		c.gridx = 0;
		c.gridy = 1;
		c.weightx = (1.0 / 3.0);
		c.weighty = (0.5 / 12.0);
		frame.add(hostPlayerPanel, c);

		c.gridx = 1;
		c.gridy = 1;
		c.weightx = (1.0 / 3.0);
		c.weighty = (0.5 / 12.0);
		frame.add(controlPanel, c);

		c.gridx = 2;
		c.gridy = 1;
		c.weightx = (1.0 / 3.0);
		c.weighty = (0.5 / 12.0);
		frame.add(guestPlayerPanel, c);

		templateFrame = new JFrame();
		templateFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ipPanel = new IPConnectionPanel();
		templateFrame.add(ipPanel);
		templateFrame.setSize(500, 100);
		templateFrame.setResizable(false);
		templateFrame.setVisible(true);

		while (!connected) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				LogPrinter.printSevere("Thread was interrupted: " + e.getMessage() + ". (View.createWindow)");
			}
		}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			LogPrinter.printSevere("Thread was interrupted: " + e.getMessage() + ". (View.createWindow)");
		}

		frame.setResizable(false);
		frame.setVisible(true);
	}

	public ChatPanel getChatPanel() {
		return chatPanel;
	}

	/// 启动面板，获得玩家名称
	public void getName() {
		String hostName = JOptionPane.showInputDialog("Please input your name:");
		if (hostName == null || hostName.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Name cannot be empty. Game will exit.");
			try{
				Thread.sleep(1000);
			} catch (InterruptedException e){
				Thread.currentThread().interrupt();
				LogPrinter.printSevere("Thread was interrupted: " + e.getMessage() + ". (View.getName)");
			}
			LogPrinter.printSevere("Name cannot be empty.Game will exit.");
			System.exit(0);
		}
		Var.name = hostName;
	}

	public BoardPanel getBoardPanel() {
		return boardPanel;
	}

	/**
	 * 当连接建立时调用
	 */
	@Override
	public void onConnect() {
		connected = true;
		chatPanel.addMessage("System", "Connected to " + Var.opponentIP);
		//如果有等待连接的对话框，可以在这里关闭它。
		if(templateFrame != null) {
			templateFrame.setVisible(false);
		}
	}

	/**
	 * 当连接断开时调用
	 */
	@Override
	public void onDisconnect() {
		connected = false;
		chatPanel.addMessage("System", "Disconnected from " + Var.opponentIP);
	}

	/**
	 * 设置对手的名字
	 * @param name 对手的名字
	 */
	public void setOpponentName(String name){
		guestPlayerPanel.setName(name);
	}

	public void showLost() {
		JOptionPane.showMessageDialog(null, "You Lost!");
	}

	public void showWin() {
		JOptionPane.showMessageDialog(null, "You Win!");
	}
}