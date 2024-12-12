/**
 * @since openJDK 22
 * @author moonflowerr
 * @package xyz.moonflowerr.GomokuWithGUI.Network
 */

package xyz.moonflowerr.GomokuWithGUI.Network;

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
	public static Controller controller;

	public static String opponentIP;
	private final ServerSocket serverSocket;
	private Socket socket;

	private boolean running;

	/**
	 * 监听连接事件
	 */
	private final List<ConnectionListener> connectionListeners = new ArrayList<>();

	/**
	 * 添加连接事件监听器
	 */
	public void addConnectionListener(ConnectionListener listener){
		if(listener != null && !connectionListeners.contains(listener)){
			connectionListeners.add(listener);
		}
	}

	/**
	 * 移除连接事件监听器
	 */
	public void removeConnectionListener(ConnectionListener listener){
		connectionListeners.remove(listener);
	}

	/**
	 * 通知所有连接事件监听器已连接
	 */
	private void notifyConnectionListeners(){
		for(ConnectionListener listener : connectionListeners){
			listener.onConnect();
		}
	}

	/**
	 * 通知所有连接事件监听器连接已断开
	 */
	private void notifyDisconnectionListeners(){
		for(ConnectionListener listener : connectionListeners){
			listener.onDisconnect();
		}
	}

	public Network() throws IOException {
		IP = Var.IP;
		opponentIP = Var.opponentIP;
		controller = Var.networkController;
		serverSocket = new ServerSocket(17890);
		running = false;
	}

	/**
	 * 开启服务器，等待对手连接，与connectToTarget方法互斥
	 */
	public void startServer(){
		new Thread(() -> {
			try{
				System.err.println("Wating for a connection...");
				socket = serverSocket.accept();
				running = true;
				System.err.println("Connected to " + socket.getInetAddress());
				handleConnection(socket);
			} catch(IOException e) {
				e.printStackTrace();
			} catch (Exception e){
				e.printStackTrace();
			} finally {
				close();
			}
		}).start();
	}

	/**
	 * 通过IP连接到对手
	 * @param ip 对手IP
	 */
	public void connectToTarget(String ip){
		if(ip == null){
			return;
		}
		new Thread(() -> {
			try {
				Socket client = new Socket(ip, 17890);
				handleConnection(client);
			} catch (IOException e){
				e.printStackTrace();
			} catch (Exception e){
				e.printStackTrace();
			}
		}).start();
	}

	/**
	 * 处理连接，同时通过监听器返回连接状态
	 * @param socket 连接
	 * @throws IOException 如果发生IO错误，则抛出异常
	 */
	public void handleConnection(Socket socket) throws IOException {
		try(BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true)){
			notifyConnectionListeners();
			while(running){
				String line = in.readLine();
				if(line == null){
					break;
				}
				try{
					Message receivedMessage = Codec.decode(line);
					handleIncomingMessage(receivedMessage, out);
				} catch (IllegalArgumentException e){
					System.err.println("Failed to decode the message: " + e.getMessage());
				}
			}
		} finally {
			notifyDisconnectionListeners();
		}
	}

	/**
	 * 处理接收到的消息
	 * @param message 格式化消息
	 * @param out 输出流
	 */
	public void handleIncomingMessage(Message message, PrintWriter out){
		switch (message.getType()){
			case MESSAGE -> Var.controller.updateMessage(message.getContent()[1]);
			case SET_CHESS -> Var.controller.updateChess(message.getX(), message.getY(), message.getPlayer());
			case PLAYER_INFO -> Var.controller.updatePlayer(message.getInfo());
			case CONNECT -> {
				Var.opponentIP = message.getInfo()[0];
				Var.controller.updatePlayer(message.getInfo());
			}
			case DISCONNECT -> {
				Var.opponentIP = null;
				Var.controller.updatePlayer(message.getInfo());
			}
			case ERROR -> System.err.println("Error: " + Arrays.toString(message.getContent()));
			case SUCCESS -> System.err.println("Success: " + Arrays.toString(message.getContent()));
			default -> System.err.println("Unknown message type.");
		}
	}

	/**
	 * 发送消息
	 * @param message 格式化消息
	 */
	public void sendMessage(Message message){
		if(socket != null && !socket.isClosed()){
			try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
				out.println(Codec.encode(message));
			} catch (IOException e){
				e.printStackTrace();
			}
		}
	}

	/**
	 * 检查是否能成功连接到对手
	 * @return 连接成功与否
	 */
	public boolean tryConnection(){
		// try to connect to the opponent and return if the ip could be connected
		String ip = Var.opponentIP;
		if(ip == null){
			return false;
		}
		try{
			Socket client = new Socket(ip, 17890);
			client.close();
		} catch (IOException e){
			JOptionPane.showMessageDialog(null, "Cannot connect to the opponent. Game will be closed.");
			return false;
		}
		return true;
	}

	/**
	 * 关闭连接
	 * @throws RuntimeException 如果出现连接错误，则抛出运行时异常
	 */
	public void close() throws RuntimeException{
		running  = false;
		try{
			if(serverSocket != null && !serverSocket.isClosed()){
				serverSocket.close();
			}
			if(socket != null && !socket.isClosed()){
				socket.close();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
