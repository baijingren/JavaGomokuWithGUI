package xyz.moonflowerr.GomokuWithGUI.Network;

import java.util.HashSet;

public class Controller {
    public void getOpponent(Broadcast broadcast) throws Exception{
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
}
