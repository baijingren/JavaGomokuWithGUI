package xyz.moonflowerr.GomokuWithGUI.Model;

import xyz.moonflowerr.GomokuWithGUI.Var;
import xyz.moonflowerr.GomokuWithGUI.View.Cell;

import java.util.LinkedList;

public class Model {
    LinkedList<Cell> chessList = new LinkedList<>();
    int[][] board = new int[Var.boardSize + 5][Var.boardSize + 5];

    public boolean setChess(int x, int y, int color){
        for(int i = 0; i <= Var.boardSize; i++){
            for(int j = 0; j <= Var.boardSize; j++){
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        if(x < 0 || x > Var.boardSize || y < 0 || y > Var.boardSize){
            System.err.println("out of board.");
            return false;
        }
        if(board[x][y] != Var.NULL && board[x][y] != 0){
            System.err.println("already have chess on " + x + " " + y);
            return false;
        }
        if(color == Var.BLACK){
            board[x][y] = Var.BLACK;
            addChessToCell(x, y, color);
        }
        else if(color == Var.WHITE){
            board[x][y] = Var.WHITE;
            addChessToCell(x, y, color);
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
}
