package xyz.moonflowerr.GomokuWithGUI.View;

import xyz.moonflowerr.GomokuWithGUI.Var;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

public class BoardPanel extends JPanel{
    // 这是棋盘的类，继承自JPanel
    int cols, rows;
    public static final int boardSize = 15;
    BoardPanel(){
        //棋盘初始化需要声明棋盘大小，由于游戏是固定的，所以boardSize是一个常量
        super();
        cols = boardSize;
        rows = boardSize;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                int cellWidth = getWidth() / (boardSize + 1);
                int cellHeight = getHeight() / (boardSize + 1);

                int cellX = x / cellWidth;
                int cellY = y / cellHeight;

                if(Var.isYourTurn){
                    boolean isSuccess = Var.controller.putChess(cellX, cellY, (Var.youAreBlack?Var.BLACK:Var.WHITE));
                    if(isSuccess) {
                        System.err.println("set Chess.");
                        Var.isYourTurn = false;
//                        Var.network.sendChess(cellX, cellY, (Var.youAreBlack?Var.BLACK:Var.WHITE));
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "You can't put chess here.");
                    }
                }

                repaint();
            }
            public void mouseReleased(MouseEvent e){
                int x = e.getX();
                int y = e.getY();
                int cellWidth = getWidth() / (boardSize + 1);
                int cellHeight = getHeight() / (boardSize + 1);

                int cellX = x / cellWidth;
                int cellY = y / cellHeight;
                int isWin = Var.model.isCurrentPlayerWin(cellX, cellY);
                if(isWin == 1){
                    JOptionPane.showMessageDialog(null, "You Win!");
                    //TODO: 向对方发送胜利信息
                }
                repaint();
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
        drawChess(g);
    }
    public void drawBoard(Graphics g){
        int cellWidth = getWidth() / (boardSize + 1);
        int cellHeight = getHeight() / (boardSize + 1);
        int broderWidth = cellWidth / 2;
        int broderHeight = cellHeight / 2;
        g.setColor(Color.decode("#FBC260"));
        g.fillRect(0, 0, getWidth(), getHeight());
        for(int i = 0; i < boardSize; i++){
            for(int j = 0; j < boardSize; j++){
                g.setColor(Color.BLACK);
                g.drawRect(cellWidth * i + broderWidth, cellHeight * j + broderHeight, cellWidth, cellHeight);
            }
        }
    }
    public void drawChess(Graphics g){
        LinkedList<Cell> chessList = Var.model.getChessList();
        for(Cell cell: chessList){
            int cellWidth = getWidth() / (boardSize + 1);
            int cellHeight = getHeight() / (boardSize + 1);
            int x = cell.getX();
            int y = cell.getY();
            int color = cell.getColor();
            if(color == Var.BLACK){
                g.setColor(Color.BLACK);
            }else if(color == Var.WHITE){
                g.setColor(Color.WHITE);
            }
            g.fillOval(cellWidth * x, cellHeight * y, cellWidth, cellHeight);
        }
    }
}
