package xyz.moonflowerr.GomokuWithGUI.Controller;

import xyz.moonflowerr.GomokuWithGUI.Var;

public class Controller {
    public void updateMessage(){
        //TODO:从网络上获取到对方的发言，或者从本地获取到自己的发言并添加到聊天框中
        String newText = "test";
        Var.view.getChatPanel().addMessage(newText);
    }
    public boolean putChess(int x, int y, int player){
        int color;
        if(player == Var.BLACK){
            color = Var.BLACK;
        }
        else{
            color = Var.WHITE;
        }
//        while(!Var.model.setChess(x, y, color));
        if(Var.model.setChess(x, y, color)){
            return true;
        }
        return false;
    }
}
