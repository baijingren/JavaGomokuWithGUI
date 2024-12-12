/**
 * @since openJDK 22
 * @author moonflowerr
 * @package xyz.moonflowerr.GomokuWithGUI.View
 */
package xyz.moonflowerr.GomokuWithGUI.View;

import xyz.moonflowerr.GomokuWithGUI.Network.*;
import xyz.moonflowerr.GomokuWithGUI.Var;

import javax.swing.*;
import java.awt.*;
import java.util.List;
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

	public View() {
		super();
	}

	/// 创建主窗口，包括棋盘、聊天框、玩家信息面板、控制面板
	public void createWindow() {
		JFrame frame = new JFrame("五子棋");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1200, 900);
		frame.setLayout(new GridBagLayout());

		URL hostHeadImageURL = getClass().getResource("/Icon/Cell/White/Center.png");
		URL guestHeadImageURL = getClass().getResource("/Icon/Cell/White/Center.png");
		if(hostHeadImageURL == null || guestHeadImageURL == null) {
			System.err.println("Cannot find head image");
		}
		hostPlayerPanel = new PlayerPanel("Host", hostHeadImageURL);
		guestPlayerPanel = new PlayerPanel("Guest", guestHeadImageURL);

		boardPanel = new BoardPanel();
		chatPanel = new ChatPanel();
		controlPanel = new ControlPanel();

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;

		c.gridx = 0;
		c.gridy = 0;
		c.weightx = (7.0/9.0);
		c.weighty = (11.0/12.0);
		c.gridwidth = 2;
		frame.add(boardPanel, c);

		c.gridx = 2;
		c.gridy = 0;
		c.weightx = (2.0/9.0);
		c.weighty = (11.0/12.0);
		c.gridwidth = 1;
		frame.add(chatPanel, c);

		c.gridx = 0;
		c.gridy = 1;
		c.weightx = (1.0/3.0);
		c.weighty = (0.5/12.0);
		frame.add(hostPlayerPanel, c);

		c.gridx = 1;
		c.gridy = 1;
		c.weightx = (1.0/3.0);
		c.weighty = (0.5/12.0);
		frame.add(controlPanel, c);

		c.gridx = 2;
		c.gridy = 1;
		c.weightx =  (1.0/3.0);
		c.weighty = (0.5/12.0);
		frame.add(guestPlayerPanel, c);

		frame.setResizable(false);
		frame.setVisible(true);
	}

	public ChatPanel getChatPanel() {
		return chatPanel;
	}

	/// 启动面板，获得玩家名称
	public void getName(){
		String hostName = JOptionPane.showInputDialog("Please input your name:");
		while(hostName == null || hostName.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Name cannot be empty.");
			hostName = JOptionPane.showInputDialog("Please input your name:");
		}
		Var.name = hostName;
	}

	/// 启动面板，输入对手IP
	public void getOpponentIP(){
		// TODO:在面板上显示自己的IP
		String opponentIP = JOptionPane.showInputDialog("Waiting for opponent connection, or you can input your opponent IP:");
		while(opponentIP == null || opponentIP.isEmpty()){
			JOptionPane.showMessageDialog(null, "IP cannot be empty.");
			opponentIP = JOptionPane.showInputDialog("Please input your opponent IP:");
		}
		Var.opponentIP = opponentIP;
		Var.network.connectToTarget(Var.opponentIP);
	}

	public BoardPanel getBoardPanel(){
		return boardPanel;
	}

	/**
	 * 当连接建立时调用
	 */
	@Override
	public void onConnect() {
		connected = true;
		chatPanel.addMessage("System", "Connected to " + Var.opponentIP);
		// TODO:如果有等待连接的对话框，可以在这里关闭它。
	}

	/**
	 * 当连接断开时调用
	 */
	@Override
	public void onDisconnect() {
		connected = false;
		chatPanel.addMessage("System", "Disconnected from " + Var.opponentIP);
	}
}