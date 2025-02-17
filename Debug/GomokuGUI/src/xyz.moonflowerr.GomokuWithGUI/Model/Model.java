/**
 * @since openJDK 22
 * @author moonflowerr
 * @package xyz.moonflowerr.GomokuWithGUI.Model
 */
package xyz.moonflowerr.GomokuWithGUI.Model;

import xyz.moonflowerr.GomokuWithGUI.LogPrinter;
import xyz.moonflowerr.GomokuWithGUI.Var;
import xyz.moonflowerr.GomokuWithGUI.View.Cell;

import java.util.LinkedList;

public class Model {
    LinkedList<Cell> chessList = new LinkedList<>();
    int[][] board = new int[Var.boardSize + 5][Var.boardSize + 5];

    public boolean setChess(int x, int y, int color){
		if(x < 0 || x > Var.boardSize || y < 0 || y > Var.boardSize){
            LogPrinter.printWarning("out of board.");
            return false;
        }
        if(board[x][y] != Var.NULL && board[x][y] != 0){
            LogPrinter.printWarning("already have chess on " + x + " " + y);
            return false;
        }
        if(color == Var.BLACK){
            board[x][y] = Var.BLACK;
            addChessToCell(x, y, color);
            LogPrinter.printLog("set black chess on " + x + " " + y);
        }
        else if(color == Var.WHITE){
            board[x][y] = Var.WHITE;
            addChessToCell(x, y, color);
            LogPrinter.printLog("set white chess on " + x + " " + y);
        }
        else return false;
        return true;
    }

    private void addChessToCell(int x, int y, int color){
        Cell tmp = new Cell(x, y, color);
        chessList.add(tmp);
    }

    public LinkedList<Cell> getChessList(){
        return chessList;
    }

    public int query(int x, int y){
        return board[x][y];
    }
    public int isCurrentPlayerWin(int x, int y){
        int color;
        if(Var.youAreBlack){
            color = Var.BLACK;
        }
        else{
            color = Var.WHITE;
        }
        if (checkDirection(x, y, color, 1, 0) ||
                checkDirection(x, y, color, 0, 1) ||
                checkDirection(x, y, color, 1, 1) ||
                checkDirection(x, y, color, 1, -1)) {
            return 1;
        }
        return 0;
    }
    private boolean checkDirection(int x, int y, int color, int dx, int dy) {
        int count = 1;
        int nx = x + dx;
        int ny = y + dy;
        while (nx >= 0 && nx <= 15 && ny >= 0 && ny <= 15 && query(nx, ny) == color) {
            count++;
            if (count >= 5) {
                return true;
            }
            nx += dx;
            ny += dy;
        }
        nx = x - dx;
        ny = y - dy;
        while (nx >= 0 && nx <= 15 && ny >= 0 && ny <= 15 && query(nx, ny) == color) {
            count++;
            if (count >= 5) {
                return true;
            }
            nx -= dx;
            ny -= dy;
        }
        return false;
    }
    public void clear(){
        chessList.clear();
        for(int i = 0; i <= Var.boardSize; i++){
            for(int j = 0; j <= Var.boardSize; j++){
                board[i][j] = Var.NULL;
            }
        }
    }

}
