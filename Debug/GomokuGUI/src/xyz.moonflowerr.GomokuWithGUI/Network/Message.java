package xyz.moonflowerr.GomokuWithGUI.Network;

public class Message {
    public enum MessageType{
        MESSAGE, SET_CHESS, PLAYER_INFO, BROADCAST, CONNECT, DISCONNECT, ERROR, SUCCESS
    }
    private MessageType type;
    private String message;
    private int x;
    private int y;
    private int player;
    private String[] info;

    public Message(MessageType type, String message){
        this.type = type;
        this.message = message;
    }

    public Message(MessageType type, int x, int y, int player){
        this.type = type;
        this.x = x;
        this.y = y;
        this.player = player;
    }

    public Message(MessageType type, String[] info){
        this.type = type;
        this.info = info;
    }

    public MessageType getType(){
        return type;
    }

    public String getMessage(){
        return message;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getPlayer(){
        return player;
    }

    public String[] getInfo(){
        return info;
    }

    public String toString(){
        // 用：分割消息类型和消息内容,用;分割消息内容的各个部分
        switch (type){
            case MESSAGE:
                return "MESSAGE:" + message;
            case SET_CHESS:
                return "SET_CHESS:" + x + ";" + y + ";" + player;
            case PLAYER_INFO:
                return "PLAYER_INFO:" + info[0] + ";" + info[1];
            case BROADCAST:
                return "BROADCAST:" + info[0] + ";" + info[1];
            case CONNECT:
                return "CONNECT:" + info[0] + ";" + info[1];
            case DISCONNECT:
                return "DISCONNECT:" + info[0] + ";" + info[1];
            case ERROR:
                return "ERROR:" + message;
            case SUCCESS:
                return "SUCCESS:" + message;
            default:
                return "ERROR:Unknown message type";
        }

    }
}
