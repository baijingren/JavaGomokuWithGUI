package xyz.moonflowerr.GomokuWithGUI.Controller;

import xyz.moonflowerr.GomokuWithGUI.Var;

public class Controller {
    public void updateMessage(){
        //TODO:从网络上获取到对方的发言，或者从本地获取到自己的发言并添加到聊天框中
        String newText = "test";
        Var.view.getChatPanel().addMessage(newText);
    }
    public void sendMessage(){
        //TODO:将自己的发言发送到网络上,同时添加到聊天框中
    }
    public void updateChess(){
        //TODO:从网络上获取到对方的落子信息,并更新本地的棋盘信息
    }
    public void sendChess(){
        //TODO:将自己的落子信息发送到网络上
    }
    public void updatePlayer(){
        //TODO:当从网络上获取到对方落子结束时，更新本地的当前玩家信息
    }
    public void sendPlayer(){
        //TODD:将自己的落子信息发送到网络上
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
