package xyz.moonflowerr.GomokuWithGUI;

import xyz.moonflowerr.GomokuWithGUI.View.CellIcon;
import xyz.moonflowerr.GomokuWithGUI.View.View;


public class Var {
    //Main
    public static View view = new View();

    // View
    public static final int boardSize = 15;
    public static final CellIcon cellIcon = new CellIcon();

    // Controller
    public static Boolean isYourTurn = true;
    public static Boolean youAreBlack = true;
}
