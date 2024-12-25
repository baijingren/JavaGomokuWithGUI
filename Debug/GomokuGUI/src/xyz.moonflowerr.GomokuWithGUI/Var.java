/**
 * @since openJDK 22
 * @author moonflowerr
 * @package xyz.moonflowerr.GomokuWithGUI
 */
package xyz.moonflowerr.GomokuWithGUI;

import xyz.moonflowerr.GomokuWithGUI.Controller.Controller;
import xyz.moonflowerr.GomokuWithGUI.Network.Network;
import xyz.moonflowerr.GomokuWithGUI.View.View;
import xyz.moonflowerr.GomokuWithGUI.Model.Model;

import java.io.IOException;

public class Var {
    public static final int BLACK = 0x1001;
    public static final int WHITE = 0x1002;
    public static final int NULL = 0x1000;

    //Main
	public static Network network;
	static {
		try {
			network = new Network();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	public static View view = new View();
	public static Controller controller = new Controller();
	public static Model model = new Model();

	public static xyz.moonflowerr.GomokuWithGUI.Network.Controller networkController = new xyz.moonflowerr.GomokuWithGUI.Network.Controller(network);

	// View
    public static final int boardSize = 15;

    // Controller
    public static Boolean isYourTurn = true;
    public static Boolean youAreBlack = true;

    // Network
    public static String name = "test";
    public static String IP;
    public static String opponentName;
    public static String opponentIP;
}
