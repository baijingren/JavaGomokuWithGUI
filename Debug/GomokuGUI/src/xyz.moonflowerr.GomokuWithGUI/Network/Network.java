/**
 * @author moonflowerr
 * @package xyz.moonflowerr.GomokuWithGUI.Network
 * @since openJDK 22
 */

package xyz.moonflowerr.GomokuWithGUI.Network;

import xyz.moonflowerr.GomokuWithGUI.LogPrinter;
import xyz.moonflowerr.GomokuWithGUI.Var;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Network {
	public static String IP; // 本机IP
	public static final int PORT = 17890;
	public static Controller controller;

	public static String opponentIP;
	private ServerSocket serverSocket = null;
	private Socket socket;

	private boolean running;
	private boolean isFirstConnected = false;

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
		try {
			serverSocket = new ServerSocket(PORT);
		} catch (IOException e) {
			LogPrinter.printSevere("Port " + PORT + "has been occupied.");
//			throw e;
			LogPrinter.printSevere("Failed to create the server socket: " + e.getMessage());
			e.printStackTrace();
		}
		running = false;
	}

	/**
	 * 开启服务器，等待对手连接，与connectToTarget方法互斥
	 */
	public void startServer() {
		new Thread(() -> {
			while (true) {
				try {
					LogPrinter.printLog("Server started. (Network.startServer)");
					LogPrinter.printLog("Waiting for a connection... (Network.startServer)");
					socket = serverSocket.accept();
					running = true;
					isFirstConnected = false;
					Var.youAreBlack = false;
					notifyConnectionListeners();

//				System.err.println("Connected to " + socket.getInetAddress());
					LogPrinter.printLog("Accepted connection from " + socket.getInetAddress() + ". (Network.startServer)");
					handleConnection(socket);
				} catch(IOException e){
//				e.printStackTrace();
					LogPrinter.printSevere("Failed to start the server: " + e.getMessage() + ". (Network.startServer)");
				} catch(Exception e){
//				e.printStackTrace();
					LogPrinter.printSevere("Other error happened when start the server: " + e.getMessage() + ". (Network.startServer)");
				}
			}
		}).start();
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
		new Thread(() -> {
			while (true) {
				try {
					Socket client = new Socket(ip, PORT);
					isFirstConnected = true;
					Var.youAreBlack = true;
					running = true;
					socket = client;
					notifyConnectionListeners();
					handleConnection(client);
					LogPrinter.printLog("Connected to " + ip);
				} catch (IOException e) {
//				e.printStackTrace();
					LogPrinter.printSevere("Failed to connect to the target: " + e.getMessage() + ". (Network.connectToTarget)");
					throw new RuntimeException(e);
				} catch (Exception e) {
//				e.printStackTrace();
					LogPrinter.printSevere("Other error happened when connect to the target: " + e.getMessage() + ". (Network.connectToTarget)");
				}
			}
		}).start();
	}

	/**
	 * 处理连接，同时通过监听器返回连接状态,如果时间过长则断开连接
	 *
	 * @param socket 连接
	 * @throws IOException 如果发生IO错误，则抛出异常
	 */
	public void handleConnection(Socket socket) throws IOException {
		socket.setSoTimeout(300000);
		try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
			notifyConnectionListeners();
			Var.networkController.sendPlayerInfo(Var.name);
			while (running) {
				String line = in.readLine();
				if (line == null) {
//					break;
					try {
						Thread.sleep(1000);
					} catch (InterruptedException ie) {
						Thread.currentThread().interrupt();
						LogPrinter.printSevere("Thread was interrupted: " + ie.getMessage() + ". (Network.handleConnection)");
					}
					LogPrinter.printWarning("No message received, continue to wait... (Network.handleConnection)");
					continue;
				}
				LogPrinter.printLog("Received message: " + line + ". (Network.handleConnection)");
				try {
					Message receivedMessage = Codec.decode(line);
					if (receivedMessage != null) {
						handleIncomingMessage(receivedMessage, out);
					}
				} catch (IllegalArgumentException e) {
//					System.err.println("Failed to decode the message: " + e.getMessage());
					LogPrinter.printSevere("Failed to decode the message: " + e.getMessage() + ". (Network.handleConnection)");
				}
			}
		} finally {
			notifyDisconnectionListeners();
		}
	}

	/**
	 * 处理接收到的消息
	 *
	 * @param message 格式化消息
	 * @param out     输出流
	 */
	public void handleIncomingMessage(Message message, PrintWriter out) {
		switch (message.getType()) {
			case MESSAGE -> Var.controller.updateMessage(message.getContent()[1]);
			case SET_CHESS -> Var.controller.updateChess(message.getX(), message.getY(), message.getPlayer());
			case PLAYER_INFO -> {
				Var.opponentName = message.getInfo()[0];
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
			case SUCCESS -> LogPrinter.printLog("Success: " + Arrays.toString(message.getContent()));
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
			try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
				out.println(Codec.encode(message));
				LogPrinter.printLog("Sent message: " + message.toString());
			} catch (IOException e) {
//				e.printStackTrace();
				LogPrinter.printSevere("Failed to send message: " + e.getMessage());
			}
		}
	}

	/**
	 * 检查是否能成功连接到对手
	 *
	 * @return 连接成功与否
	 */
	public boolean tryConnection() {
		// try to connect to the opponent and return if the ip could be connected
		String ip = Var.opponentIP;
		if (ip == null) {
			return false;
		}
		try {
			Socket client = new Socket(ip, PORT);
			client.close();
			LogPrinter.printLog("ip " + ip + " is available.");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Cannot connect to the opponent. Game will be closed.");
			return false;
		}
		return true;
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
}
