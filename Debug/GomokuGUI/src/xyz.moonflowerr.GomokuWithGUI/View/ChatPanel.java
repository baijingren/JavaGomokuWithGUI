package xyz.moonflowerr.GomokuWithGUI.View;

import xyz.moonflowerr.GomokuWithGUI.Var;

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
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(200, 1000));
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatPane = new JScrollPane(chatArea);

        chatField = new JTextField();
        chatField.setSize(120, 30);

        sendButton = new JButton("发送");
        sendButton.setSize(80, 30);
        sendButton.addActionListener(e -> {
            String message = chatField.getText();
            chatField.setText("");
            Var.controller.sendMessage(message);
        });

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 0.85;
        c.gridwidth = 2;
        add(chatPane, c);

        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0.75;
        c.weighty = 0.15;
        c.gridwidth = 1;
        add(chatField, c);

        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 0.25;
        c.weighty = 0.15;
        add(sendButton, c);
    }

    public void addMessage(String playername, String message){
        chatArea.append(playername + ": \n");
        chatArea.append(message + "\n");
    }
}
