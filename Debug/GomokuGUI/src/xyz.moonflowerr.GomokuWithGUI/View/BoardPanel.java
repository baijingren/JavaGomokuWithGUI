package xyz.moonflowerr.GomokuWithGUI.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class BoardPanel extends JPanel{
    // 这是棋盘的类，继承自JPanel
    int cols, rows;
    Cells[][] cells;
    public static final int boardSize = 15;
    BoardPanel(){
        //棋盘初始化需要声明棋盘大小，由于游戏是固定的，所以boardSize是一个常量
        super();
        cols = boardSize;
        rows = boardSize;
        cells = new Cells[cols][rows];
        setLayout(new GridLayout(cols, rows));
        for(int i = 0; i < cols; i++){
            for(int j = 0; j < rows; j++){
                cells[i][j] = new Cells(i, j); // 棋盘上的每个格子都是一个Cells对象, 传入坐标，以便于初始化格子的图片
                add(cells[i][j]);

            }
        }
    }
    public void refreshAllCells() {
        //TODO:完成修改界面大小之后仍然能够正常显示的功能
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                cells[i][j].refreshIcon();
            }
        }
    }
}
