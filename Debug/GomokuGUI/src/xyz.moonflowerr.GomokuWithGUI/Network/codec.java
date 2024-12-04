package xyz.moonflowerr.GomokuWithGUI.Network;

public class codec {
    public static String encode(Message message){
        return switch (message.getType()) {
            case MESSAGE -> "MESSAGE:" + message.getMessage();
            case SET_CHESS -> "SET_CHESS:" + message.getX() + ";" + message.getY() + ";" + message.getPlayer();
            case PLAYER_INFO -> "PLAYER_INFO:" + message.getInfo()[0] + ";" + message.getInfo()[1];
            case BROADCAST -> "BROADCAST:" + message.getInfo()[0] + ";" + message.getInfo()[1];
            case CONNECT -> "CONNECT:" + message.getInfo()[0];
            case DISCONNECT -> "DISCONNECT:" + message.getInfo()[0];
            case ERROR -> "ERROR:" + message.getMessage();
            case SUCCESS -> "SUCCESS:" + message.getMessage();
        };
    }

    public static Message decode(String message){
        String[] parts = message.split(":");
        String[] info = parts[1].split(";");
        return switch (parts[0]) {
            case "MESSAGE" -> new Message(Message.MessageType.MESSAGE, info[0]);
            case "SET_CHESS" ->
                    new Message(Message.MessageType.SET_CHESS, Integer.parseInt(info[0]), Integer.parseInt(info[1]), Integer.parseInt(info[2]));
            case "PLAYER_INFO" -> new Message(Message.MessageType.PLAYER_INFO, info);
            case "BROADCAST" -> new Message(Message.MessageType.BROADCAST, info);
            case "CONNECT" -> new Message(Message.MessageType.CONNECT, info);
            case "DISCONNECT" -> new Message(Message.MessageType.DISCONNECT, info);
            case "ERROR" -> new Message(Message.MessageType.ERROR, info[0]);
            case "SUCCESS" -> new Message(Message.MessageType.SUCCESS, info[0]);
            default -> null;
        };
    }
}
