package xyz.moonflowerr.GomokuWithGUI;

import xyz.moonflowerr.GomokuWithGUI.Controller.Controller;
import xyz.moonflowerr.GomokuWithGUI.Network.Network;
import xyz.moonflowerr.GomokuWithGUI.View.View;
import xyz.moonflowerr.GomokuWithGUI.Model.Model;

public class Var {
    public static final int BLACK = 0x1001;
    public static final int WHITE = 0x1002;
    public static final int NULL = 0x1000;

    //Main
    public static View view = new View();
    public static Controller controller = new Controller();
    public static Model model = new Model();
    public static Network network = new Network();

    // View
    public static final int boardSize = 15;

    // Controller
    public static Boolean isYourTurn = true;
    public static Boolean youAreBlack = true;

    // Network
    public static String name = "test"; // TODO: 实现在游戏开始界面输入名字
    public static String IP;
    public static String opponentName;
}
