package xyz.moonflowerr.GomokuWithGUI.View;

import javax.swing.*;
import java.awt.*;

public class ChatPanel extends JPanel{
    // 这是聊天框的类，继承自JPanel，使用Border布局
    JTextArea chatArea;
    JScrollPane chatPane;
    JTextField chatField; // 编辑框
    JButton sendButton;
    ChatPanel(){
        //聊天框初始化
        super();
        setSize(290, 900);
        setLayout(new BorderLayout());
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatPane = new JScrollPane(chatArea);
        chatField = new JTextField();
        sendButton = new JButton("发送");
        add(chatPane, BorderLayout.CENTER);
        add(chatField, BorderLayout.SOUTH);
        add(sendButton, BorderLayout.EAST);
    }

    public void addMessage(String message){
        chatArea.append(message + "\n");
    }
}
