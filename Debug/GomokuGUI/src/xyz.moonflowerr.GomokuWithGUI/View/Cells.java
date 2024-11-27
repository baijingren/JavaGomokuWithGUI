package xyz.moonflowerr.GomokuWithGUI.View;

import xyz.moonflowerr.GomokuWithGUI.Var;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Cells extends JButton {
    private int x, y;
    MouseAdapter mouseAdapter = new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            if (Var.isYourTurn) {
                if (Var.youAreBlack) {
                    setIcon(Var.cellIcon.getScaledIcon(Var.cellIcon.transparentBlackIcon[x][y], getWidth(), getHeight()));
                } else {
                    setIcon(Var.cellIcon.getScaledIcon(Var.cellIcon.transparentWhiteIcon[x][y], getWidth(), getHeight()));
                }
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (Var.isYourTurn) {
                setIcon(Var.cellIcon.getScaledIcon(Var.cellIcon.emptyIcon[x][y], getWidth(), getHeight()));
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (Var.isYourTurn) {
                // TODO:检查落子是否合法并落子
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            if (Var.isYourTurn) {
                if (Var.youAreBlack) {
                    setIcon(Var.cellIcon.getScaledIcon(Var.cellIcon.transparentBlackIcon[x][y], getWidth(), getHeight()));
                } else {
                    setIcon(Var.cellIcon.getScaledIcon(Var.cellIcon.transparentWhiteIcon[x][y], getWidth(), getHeight()));
                }
            }
        }
    };

    public Cells() {
        super();
        setIcon(null);
        setPreferredSize(new Dimension(50, 50));
        addMouseListener(mouseAdapter);
        x = 0;
        y = 0;
    }

    public Cells(int x, int y) {
        super();
        this.x = x;
        this.y = y;
        addMouseListener(mouseAdapter);
        setPreferredSize(new Dimension(50, 50));
        setBorder(null);
    }
    public void refreshIcon() {
        setIcon(Var.cellIcon.getScaledIcon(Var.cellIcon.emptyIcon[x][y], getWidth(), getHeight()));
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}