/**
 * @author moonflowerr
 * @package xyz.moonflowerr.GomokuWithGUI.Network
 * @since openJDK 22
 */

package xyz.moonflowerr.GomokuWithGUI.Network;

import xyz.moonflowerr.GomokuWithGUI.LogPrinter;
import xyz.moonflowerr.GomokuWithGUI.Var;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Network {
	public static String IP; // 本机IP
	public static int PORT;
	public static Controller controller;

	public static String opponentIP;
	private ServerSocket serverSocket = null;
	private Socket socket;

	private boolean running;
	private boolean isFirstConnected = false;

	private BufferedReader in;
	private PrintWriter out;

	/**
	 * 监听连接事件
	 */
	private final List<ConnectionListener> connectionListeners = new ArrayList<>();

	/**
	 * 添加连接事件监听器
	 */
	public void addConnectionListener(ConnectionListener listener) {
		if (listener != null && !connectionListeners.contains(listener)) {
			connectionListeners.add(listener);
		}
	}

	/**
	 * 移除连接事件监听器
	 */
	public void removeConnectionListener(ConnectionListener listener) {
		connectionListeners.remove(listener);
	}

	/**
	 * 通知所有连接事件监听器已连接
	 */
	private void notifyConnectionListeners() {
		for (ConnectionListener listener : connectionListeners) {
			listener.onConnect();
		}
	}

	/**
	 * 通知所有连接事件监听器连接已断开
	 */
	private void notifyDisconnectionListeners() {
		for (ConnectionListener listener : connectionListeners) {
			listener.onDisconnect();
		}
	}

	public Network() throws IOException {
		IP = Var.IP;
		opponentIP = Var.opponentIP;
		controller = Var.networkController;
		running = false;
		PORT = 17890;
	}

	public Network(int port) throws IOException {
		IP = Var.IP;
		opponentIP = Var.opponentIP;
		controller = Var.networkController;
		running = false;
		PORT = port;
	}

	/**
	 * 开启服务器，等待对手连接，与connectToTarget方法互斥
	 */
	public void startServer() {
		new Thread() {
			public void run() {
				try {
					LogPrinter.printLog("Server started. (Network.startServer)");
					serverSocket = new ServerSocket(PORT);
					LogPrinter.printLog("Waiting for a connection... (Network.startServer)");
					socket = serverSocket.accept();
					LogPrinter.printLog("Accepted connection from " + socket.getInetAddress() + ". (Network.startServer)");
					running = true;
					isFirstConnected = false;
					Var.youAreBlack = false;
					notifyConnectionListeners();
					LogPrinter.printLog(String.valueOf(socket.isClosed()) + " (Network.startServer)");
					handleConnection();
					LogPrinter.printLog(String.valueOf(socket.isClosed()) + " (Network.startServer)");
					startReceiveMessage();
				} catch (IOException e) {
					LogPrinter.printSevere("Port " + PORT + " has been occupied.");LogPrinter.printSevere("Failed to create the server socket: " + e.getMessage());LogPrinter.logStackTrace(e);

					LogPrinter.printSevere("Failed to start the server: " + e.getMessage() + ". (Network.startServer)");
					if (serverSocket.isClosed()) {
						LogPrinter.printSevere("Server socket is closed. Exiting loop.");
					}
				} catch (Exception e) {
					LogPrinter.printSevere("Other error happened when start the server: " + e.getMessage() + ". (Network.startServer)");
				}
			}
		}.start();
	}

	/**
	 * 通过IP连接到对手
	 *
	 * @param ip 对手IP
	 */
	public void connectToTarget(String ip) throws RuntimeException {
		if (ip == null) {
			return;
		}
		try {
			socket = new Socket(ip, PORT);
			isFirstConnected = true;
			Var.youAreBlack = true;
			running = true;
			notifyConnectionListeners();
			LogPrinter.printLog("Connected to " + ip);
			handleConnection();
			startReceiveMessage();
		} catch (IOException e) {
			LogPrinter.printSevere("Failed to connect to the target(" + ip +"): " + e.getMessage() + ". (Network.connectToTarget)");
			if (socket != null && socket.isClosed()) {
				LogPrinter.printSevere("Client socket is closed. Exiting loop.");
			}
			throw new RuntimeException(e);
		} catch (Exception e) {
			LogPrinter.printSevere("Other error happened when connect to the target: " + e.getMessage() + ". (Network.connectToTarget)");
			LogPrinter.logStackTrace(e);
		}
	}

	/**
	 * 处理连接，同时通过监听器返回连接状态,如果时间过长则断开连接
	 *
	 * @throws IOException 如果发生IO错误，则抛出异常
	 */
	public void handleConnection() throws IOException {
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		Var.networkController.sendPlayerInfo(Var.name);
	}

	/**
	 * 新建线程用于连接接收消息
	 */
	public void startReceiveMessage() {
		new Thread() {
			public void run() {
				String line;
				try {
					while((line = in.readLine()) != null){
						LogPrinter.printLog("Received message: " + line + ". (Network.startReceiveMessage)");
						Message receivedMessage = Codec.decode(line);
						if (receivedMessage != null) {
							handleIncomingMessage(receivedMessage);
						} else {
							throw new IllegalArgumentException("Received message is null.");
						}
					}
				} catch (IllegalArgumentException e) {
					LogPrinter.printSevere("Failed to decode the message: " + e.getMessage() + ". (Network.startReceiveMessage)");
				} catch (IOException e) {
					LogPrinter.logStackTrace(e);
					LogPrinter.printSevere(" (Network.startReceiveMessage)");
				}
			}
		}.start();
	}

	/**
	 * 处理接收到的消息
	 *
	 * @param message 格式化消息
	 */
	public void handleIncomingMessage(Message message) {
		switch (message.getType()) {
			case MESSAGE -> Var.controller.updateMessage(message.getContent()[1]);
			case SET_CHESS -> Var.controller.updateChess(message.getX(), message.getY(), message.getPlayer());
			case PLAYER_INFO -> {
				Var.opponentName = message.getInfo()[0];
				Var.controller.setOpponentName(message.getInfo()[0]);
				LogPrinter.printLog("Player info: " + Arrays.toString(message.getInfo()));
			}
			case CONNECT -> {
				Var.opponentIP = message.getInfo()[0];
				LogPrinter.printLog("Connected to " + Var.opponentIP + ". (Network.java)");
			}
			case DISCONNECT -> {
//				Var.opponentIP = null;
				LogPrinter.printLog("Disconnected from " + message.getInfo()[0] + ". (Network.java)");
			}
			case ERROR -> LogPrinter.printSevere("Error: " + Arrays.toString(message.getContent()));
			case SUCCESS -> {
				Var.controller.updateSuccess();
				LogPrinter.printLog("Success: " + Arrays.toString(message.getContent()));
			}
			default -> LogPrinter.printSevere("Unknown message type.");
		}
	}

	/**
	 * 发送消息
	 *
	 * @param message 格式化消息
	 */
	public void sendMessage(Message message) {
		if (socket != null && !socket.isClosed()) {
			try {
				out = new PrintWriter(socket.getOutputStream(), true);
				out.println(Codec.encode(message));
				LogPrinter.printLog("Sent message: " + message.toString());
			} catch (IOException e) {
				LogPrinter.printSevere("Failed to send message: " + e.getMessage());
			}
		}
	}

	/**
	 * 关闭连接
	 *
	 * @throws RuntimeException 如果出现连接错误，则抛出运行时异常
	 */
	public void close() throws RuntimeException {
		running = false;
		try {
			if (serverSocket != null && !serverSocket.isClosed()) {
				serverSocket.close();
				LogPrinter.printWarning("Server socket has been closed.");
			}
			if (socket != null && !socket.isClosed()) {
				socket.close();
				LogPrinter.printWarning("Socket has been closed.");
			}
		} catch (IOException e) {
//			throw new RuntimeException(e);
			LogPrinter.printSevere("Failed to close the connection: " + e.getMessage());
		} catch (Exception e) {
//			e.printStackTrace();
			LogPrinter.printSevere("Failed to close the connection: " + e.getMessage());
		}
	}

	public boolean isRunning() {
		return running;
	}

	public boolean isFirstConnected() {
		return isFirstConnected;
	}

	public void sendChess(int cellX, int cellY, int player) {
		Message message = new Message(Message.MessageType.SET_CHESS, cellX, cellY, player);
		sendMessage(message);
	}
}
