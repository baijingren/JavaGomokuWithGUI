package xyz.moonflowerr.GomokuWithGUI.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;

public class View {
    BoardPanel boardPanel;
    ChatPanel chatPanel;

    public View() {
        super();
    }

    public void createWindow() {
        JFrame frame = new JFrame("五子棋");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 900);
        frame.setLayout(new BorderLayout());

        boardPanel = new BoardPanel();
        chatPanel = new ChatPanel();
        frame.add(boardPanel, BorderLayout.CENTER);
        frame.add(chatPanel, BorderLayout.EAST);

        frame.setVisible(true);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        boardPanel.refreshAllCells();
    }

    public ChatPanel getChatPanel() {
        return chatPanel;
    }
}