package xyz.moonflowerr.GomokuWithGUI.Network;

import xyz.moonflowerr.GomokuWithGUI.Var;

import java.io.StringReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;

public class Network {
    public static String IP;
    public static Broadcast broadcast = new Broadcast();

    public void getOpponent() throws Exception{
        HashSet<String[]> infoList = new HashSet<>();
        infoList.add(broadcast.receiveBroadcast());
    }

    public void sendMessage(String message){

    }

    public String getMessage(){
        return "test";
    }

    public void sendChess(int x, int y, int color){

    }

    public static void main(String[] args) {
        try {
            do {
                broadcast.receiveBroadcast();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (true);
//            broadcast.sendBroadcast();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
