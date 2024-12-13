/**
 * @since openJDK 22, IntelliJ
 * @author moonflowerr
 * @package xyz.moonflowerr.GomokuWithGUI.Network
 */

package xyz.moonflowerr.GomokuWithGUI.Network;

public class Message {
	public enum MessageType{
		MESSAGE, SET_CHESS, PLAYER_INFO, CONNECT, DISCONNECT, ERROR, SUCCESS
	}
	private final MessageType type;
	private String message;
	private int x;
	private int y;

	/// player 的值为Var.BLACK或Var.WHITE
	private int player;

	/** info 在不同消息类型下的用法
	 * <p>PLAYER_INFO: info[0]为玩家名，info[1]无用</p>
	 * <p>CONNECT: info[0]为对方名字，info[1]为对方颜色</p>
	 * <p>DISCONNECT: info[0]为对方IP，info[1]为对方名字</p>
	 * <p>ERROR: info[0]为错误信息</p>
	 * <p>SUCCESS: info[0]为成功信息</p>
	 * <p>SET_CHESS: x,y为落子坐标，player为落子玩家</p>
	 * <p>MESSAGE: message为消息内容</p>
	 * <p>Other 其他类型的消息info为null</p>
	 */
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

	/**
	 * 将消息转换为字符串
	 * @return 字符串格式的消息, 格式为"消息类型:消息内容;消息内容;...", 用于TCP传递
	 */
	public String toString(){
		// 用：分割消息类型和消息内容,用;分割消息内容的各个部分
		return switch (type) {
			case MESSAGE -> "MESSAGE:" + message;
			case SET_CHESS -> "SET_CHESS:" + x + ";" + y + ";" + player;
			case PLAYER_INFO -> "PLAYER_INFO:" + info[0] + ";" + info[1];
			case CONNECT -> "CONNECT:" + info[0] + ";" + info[1];
			case DISCONNECT -> "DISCONNECT:" + info[0] + ";" + info[1];
			case ERROR -> "ERROR:" + message;
			case SUCCESS -> "SUCCESS:" + message;
			default -> "ERROR:Unknown message type";
		};
	}

	/**
	 * 将消息转换为字符串数组
	 * @return 字符串数组格式的消息, 第一个元素为消息类型, 后面的元素为消息内容
	 */
	public String[] getContent(){
		String[] ret = new String[3];
		switch (type){
			case MESSAGE:
				ret[0] = "MESSAGE";
				ret[1] = message;
				break;
			case SET_CHESS:
				ret[0] = "SET_CHESS";
				ret[1] = x + ";" + y;
				ret[2] = player + "";
				break;
			case PLAYER_INFO:
				ret[0] = "PLAYER_INFO";
				ret[1] = info[0];
				ret[2] = info[1];
				break;
			case CONNECT:
				ret[0] = "CONNECT";
				ret[1] = info[0];
				ret[2] = info[1];
				break;
			case DISCONNECT:
				ret[0] = "DISCONNECT";
				ret[1] = info[0];
				ret[2] = info[1];
				break;
			case ERROR:
				ret[0] = "ERROR";
				ret[1] = message;
				break;
			case SUCCESS:
				ret[0] = "SUCCESS";
				ret[1] = message;
				break;
			default:
				ret[0] = "ERROR";
				ret[1] = "Unknown message type";
				break;
		}
		return ret;
	}
}
