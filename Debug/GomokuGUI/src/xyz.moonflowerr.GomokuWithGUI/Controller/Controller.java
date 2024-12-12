/**
 * @since openJDK 22
 * @author moonflowerr
 * @package xyz.moonflowerr.GomokuWithGUI.Controller
 */
package xyz.moonflowerr.GomokuWithGUI.Controller;

import xyz.moonflowerr.GomokuWithGUI.Var;

public class Controller {
    public void updateMessage(String newMessage){
        Var.view.getChatPanel().addMessage(Var.opponentName, newMessage);
    }
    public void sendMessage(String newMessage){
        //将自己的发言发送到网络上,同时添加到聊天框中
        Var.view.getChatPanel().addMessage(Var.name, newMessage);
        Var.networkController.sendMessage(newMessage);

    }
    public void updateChess(int x, int y, int player){
        //从网络上获取到对方的落子信息,并更新本地的棋盘信息
        if(putChess(x, y, player)){
            repaint();
        }
        Var.isYourTurn = true;
    }
    public void sendChess(int x, int y, int player){
        //TODO:将自己的落子信息发送到网络上
    }
    public void updatePlayer(String[] info){
        //TODO:当从网络上获取到对方落子结束时，更新本地的当前玩家信息
        Var.opponentName = info[0];
        if(info[1].equals("BLACK")){
            Var.youAreBlack = false;
        }
        else{
            Var.youAreBlack = true;
        }
    }
    public void sendPlayer(){
        //TODD:将自己的落子信息发送到网络上
    }

    /**
     * 将落子信息更新到本地棋盘
     * @param x
     * @param y
     * @param player
     * @return false: 落子失败 true: 落子成功
     */
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

    /**
     * 重绘棋盘
     */
    public void repaint(){
        Var.view.getBoardPanel().repaint();
    }
}
