package xyz.moonflowerr.GomokuWithGUI.Network;

public interface ConnectionListener {
	/**
	 * 当连接建立时调用
	 */
	void onConnect();

	/**
	 * 当连接断开时调用
	 */
	void onDisconnect();
}
