package xyz.moonflowerr.GomokuWithGUI.View;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel{
    int cols, rows;
    Cells[][] cells;
    BoardPanel(int boardSize){
        super();
        cols = boardSize;
        rows = boardSize;
        cells = new Cells[cols][rows];
        setLayout(new GridLayout(cols, rows));
        for(int i = 0; i < cols; i++){
            for(int j = 0; j < rows; j++){
                cells[i][j] = new Cells(i, j);
                add(cells[i][j]);
            }
        }
    }
}
