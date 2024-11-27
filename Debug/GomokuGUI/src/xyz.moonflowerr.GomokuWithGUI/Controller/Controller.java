package xyz.moonflowerr.GomokuWithGUI.Controller;

import xyz.moonflowerr.GomokuWithGUI.Var;

public class Controller {
    public void updateMessage(){
        //TODO:从网络上获取到对方的发言，或者从本地获取到自己的发言并添加到聊天框中
        String newText = "test";
        Var.view.getChatPanel().addMessage(newText);
    }
}
