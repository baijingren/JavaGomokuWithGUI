package xyz.moonflowerr.GomokuWithGUI.Network;

import netscape.javascript.JSObject;
import xyz.moonflowerr.GomokuWithGUI.Var;

import java.io.*;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;

public class Network {
    public static String IP;
    public static Broadcast broadcast = new Broadcast();
    public static Controller controller = new Controller();
    private ServerSocket serverSocket;
    private Socket socket

    public void StartTCPConnection(String IP, int port) throws IOException {
        serverSocket = new ServerSocket(port);

        socket = serverSocket.accept();

    }

    public InputStream getInputStream() throws IOException {
        InputStream inputStream = socket.getInputStream(); // 从聊天框读取数据，一行一行
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String message = null;
        while((message = bufferedReader.readLine()) != null){
            // 拼接到文本域

        }
        return null;
    }

    public OutputStream getOutputStream(){
        OutputStream outputStream = socket.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
        return null;
    }

    public void close(){

    }

    public static void main(String[] args) {
        try {
            do {
                broadcast.sendBroadcast();
                broadcast.receiveBroadcast();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (true);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
