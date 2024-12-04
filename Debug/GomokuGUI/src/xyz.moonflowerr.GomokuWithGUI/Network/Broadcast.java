package xyz.moonflowerr.GomokuWithGUI.Network;

import xyz.moonflowerr.GomokuWithGUI.Var;

import java.net.*;
import java.io.*;

public class Broadcast {
    private static final int PORT = 17890;

    private String BROADCAST_IP; // TODO: 正式代码中需要获取本机局域网IP
    private String playerName;
    private Thread broadcastThread;
    private Thread listenThread;
    private boolean running = true;


    public Broadcast(String LocalIP){
        BROADCAST_IP = LocalIP;
        playerName = Var.name;
    }
    public void sendBroadcast() throws IOException{
        InetAddress myAddress = InetAddress.getByName(BROADCAST_IP);
        DatagramSocket socket = new DatagramSocket();
        socket.setBroadcast(true);

        String message = Var.name + " " + Var.IP;
        byte[] buffer = message.getBytes();

        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, myAddress, PORT);
        socket.send(packet);
        socket.close();
    }

    public String[] receiveBroadcast() throws IOException{
        DatagramSocket socket = new DatagramSocket(PORT);
        byte[] buffer = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
        String message = new String(packet.getData(), 0, packet.getLength());
        String[] info = message.split(" ");
        socket.close();
        return info;
    }
}
