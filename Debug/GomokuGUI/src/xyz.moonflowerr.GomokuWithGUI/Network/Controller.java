/**
 * @since openJDK 22
 * @author moonflowerr
 * @package xyz.moonflowerr.GomokuWithGUI.Network
 */
package xyz.moonflowerr.GomokuWithGUI.Network;

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

    public void sendWin(){
        network.sendMessage(new Message(Message.MessageType.SUCCESS, new String[]{"empty", "empty"}));
    }

    public void sendPlayerInfo(String name){
        network.sendMessage(new Message(Message.MessageType.PLAYER_INFO, new String[]{name, "empty"}));
    }

    public void sendSurrender() {
        network.sendMessage(new Message(Message.MessageType.SUCCESS, new String[]{"empty", "empty"}));
    }
}
