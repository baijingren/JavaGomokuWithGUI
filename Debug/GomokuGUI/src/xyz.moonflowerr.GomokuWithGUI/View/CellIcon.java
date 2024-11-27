package xyz.moonflowerr.GomokuWithGUI.View;

import xyz.moonflowerr.GomokuWithGUI.Var;

import java.awt.*;
import java.net.URL;
import javax.swing.*;

public class CellIcon {
    public Icon[][] emptyIcon = new Icon[Var.boardSize][Var.boardSize];
    public Icon[][] blackIcon = new Icon[Var.boardSize][Var.boardSize];
    public Icon[][] whiteIcon = new Icon[Var.boardSize][Var.boardSize];
    public Icon[][] transparentBlackIcon = new Icon[Var.boardSize][Var.boardSize];
    public Icon[][] transparentWhiteIcon = new Icon[Var.boardSize][Var.boardSize];

    public CellIcon() {
        String path = "/Icon/Cell/";
        String empty = path + "Empty/";
        String black = path + "Black/";
        String white = path + "White/";
        String transparentBlack = path + "TransparentBlack/";
        String transparentWhite = path + "TransparentWhite/";
        for (int i = 0; i < Var.boardSize; i++) {
            for (int j = 0; j < Var.boardSize; j++) {
                String position = getPosition(i, j);
                emptyIcon[i][j] = createIcon(empty + position);
                blackIcon[i][j] = createIcon(black + position);
                whiteIcon[i][j] = createIcon(white + position);
                transparentBlackIcon[i][j] = createIcon(transparentBlack + position);
                transparentWhiteIcon[i][j] = createIcon(transparentWhite + position);
            }
        }
    }

    private String getPosition(int i, int j) {
        if (i == 0 && j == 0) {
            return "Corner/Cell1.png";
        } else if (i == 0 && j == Var.boardSize - 1) {
            return "Corner/Cell2.png";
        } else if (i == Var.boardSize - 1 && j == 0) {
            return "Corner/Cell3.png";
        } else if (i == Var.boardSize - 1 && j == Var.boardSize - 1) {
            return "Corner/Cell4.png";
        } else if (i == 0) {
            return "Edge/Cell1.png";
        } else if (j == 0) {
            return "Edge/Cell3.png";
        } else if (i == Var.boardSize - 1) {
            return "Edge/Cell2.png";
        } else if (j == Var.boardSize - 1) {
            return "Edge/Cell4.png";
        } else {
            return "Cell.png";
        }
    }

    // 添加到 CellIcon 类中
    public Icon getScaledIcon(Icon icon, int width, int height) {
        if (icon instanceof ImageIcon) {
            Image img = ((ImageIcon) icon).getImage();
            Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImg);
        }
        return icon;
    }

    private Icon createIcon(String path) {
        URL url = getClass().getResource(path);
        if (url != null) {
            return new ImageIcon(url);
        } else {
            System.out.println("Image not found: " + path);
            return null;
        }
    }
}

