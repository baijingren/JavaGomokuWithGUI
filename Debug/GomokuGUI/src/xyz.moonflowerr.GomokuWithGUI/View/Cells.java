package xyz.moonflowerr.GomokuWithGUI.View;

import javax.swing.*;
import java.awt.*;

public class Cells extends JButton {
    private int x, y;

    public Cells(){
        super();
        setIcon(null);
        x = 0;
        y = 0;
    }
    public Cells(int x, int y){
        super();
        if(x == 1 && y == 1){
            setIcon(new ImageIcon("Resources/Icon/Cell/Empty/Corner/Cell1.png"));
        }
        else if(x == 1 && y == 15){
            setIcon(new ImageIcon("Resources/Icon/Cell/Empty/Corner/Cell2.png"));
        }
        else if(x == 15 && y == 1){
            setIcon(new ImageIcon("Resources/Icon/Cell/Empty/Corner/Cell3.png"));
        }
        else if(x == 15 && y == 15){
            setIcon(new ImageIcon("Resources/Icon/Cell/Empty/Corner/Cell4.png"));
        }
        else if(x == 1){
            setIcon(new ImageIcon("Resources/Icon/Cell/Empty/Edge/Cell1.png"));
        }
        else if(x == 15){
            setIcon(new ImageIcon("Resources/Icon/Cell/Empty/Edge/Cell2.png"));
        }
        else if(y == 1){
            setIcon(new ImageIcon("Resources/Icon/Cell/Empty/Edge/Cell3.png"));
        }
        else if(y == 15){
            setIcon(new ImageIcon("Resources/Icon/Cell/Empty/Edge/Cell4.png"));
        }
        else{
            setIcon(new ImageIcon("Resources/Icon/Cell/Empty/Cell.png"));
        }
        this.x = x;
        this.y = y;
    }
    void getPosition(int x, int y){
        x = this.x;
        y = this.y;
    }
}
