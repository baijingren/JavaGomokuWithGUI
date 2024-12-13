/**
 * @since openJDK 22
 * @author moonflowerr
 * @package xyz.moonflowerr.GomokuWithGUI.Network
 */
package xyz.moonflowerr.GomokuWithGUI.Network;

import java.awt.*;
import java.util.List;
import java.util.HashSet;

public class Controller {
    public Network network;

    public Controller(Network network){
        this.network = network;
    }

    public void sendMessage(String message){
        network.sendMessage(new Message(Message.MessageType.MESSAGE, message));
    }

    public void sendChess(int x, int y, int color){
        network.sendMessage(new Message(Message.MessageType.SET_CHESS, x, y, color));
    }

    public void sendPlayerInfo(String name, int color){
        network.sendMessage(new Message(Message.MessageType.PLAYER_INFO, new String[]{name, Integer.toString(color)}));
    }

    public void sendWin(){
        network.sendMessage(new Message(Message.MessageType.SUCCESS, ""));
    }
}
