/**
 * @since openJDK 22
 * @author moonflowerr
 * @package xyz.moonflowerr.GomokuWithGUI.Controller
 */
package xyz.moonflowerr.GomokuWithGUI.Controller;

import xyz.moonflowerr.GomokuWithGUI.LogPrinter;
import xyz.moonflowerr.GomokuWithGUI.Var;

import static java.lang.System.exit;
import static java.lang.Thread.sleep;

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
        //将自己的落子信息发送到网络上
        Var.networkController.sendChess(x, y, player);
    }

    /**
     * 将落子信息更新到本地棋盘
     * @param x x坐标
     * @param y y坐标
     * @param player 落子玩家
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
		return Var.model.setChess(x, y, color);
	}

    /**
     * 重绘棋盘
     */
    public void repaint(){
        Var.view.getBoardPanel().repaint();
    }

    public void setOpponentName(String name){
        Var.view.setOpponentName(name);
    }

    public void updateSuccess() {
        Var.view.showLost();
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            LogPrinter.logStackTrace(e);
        }
        exit(0);
    }

    public void surrender() {
        Var.networkController.sendSurrender();
        Var.view.showWin();
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            LogPrinter.logStackTrace(e);
        }
        exit(0);
    }
}
