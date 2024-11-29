package xyz.moonflowerr.GomokuWithGUI.View;

public class Cell {
    int x, y, color;
    public Cell(int x, int y, int color){
        this.x = x;
        this.y = y;
        this.color = color;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int getColor(){
        return color;
    }
}
