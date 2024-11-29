package xyz.moonflowerr.GomokuWithGUI.View;

import xyz.moonflowerr.GomokuWithGUI.Var;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.net.URL;

import static java.lang.System.exit;

public class View {
    BoardPanel boardPanel;
    ChatPanel chatPanel;
    PlayerPanel hostPlayerPanel;
    PlayerPanel guestPlayerPanel;
    ControlPanel controlPanel;
    Boolean connected = false;

    public View() {
        super();
    }

    public void createWindow() {
        JFrame frame = new JFrame("五子棋");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 900);
        frame.setLayout(new GridBagLayout());

        URL hostHeadImageURL = getClass().getResource("/Icon/Cell/White/Center.png");
        URL guestHeadImageURL = getClass().getResource("/Icon/Cell/White/Center.png");
        if(hostHeadImageURL == null || guestHeadImageURL == null) {
            System.err.println("Cannot find head image");
        }
        hostPlayerPanel = new PlayerPanel("Host", hostHeadImageURL);
        guestPlayerPanel = new PlayerPanel("Guest", guestHeadImageURL);

        boardPanel = new BoardPanel();
        chatPanel = new ChatPanel();
        controlPanel = new ControlPanel();

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = (7.0/9.0);
        c.weighty = (11.0/12.0);
        c.gridwidth = 2;
        frame.add(boardPanel, c);

        c.gridx = 2;
        c.gridy = 0;
        c.weightx = (2.0/9.0);
        c.weighty = (11.0/12.0);
        c.gridwidth = 1;
        frame.add(chatPanel, c);

        c.gridx = 0;
        c.gridy = 1;
        c.weightx = (1.0/3.0);
        c.weighty = (0.5/12.0);
        frame.add(hostPlayerPanel, c);

        c.gridx = 1;
        c.gridy = 1;
        c.weightx = (1.0/3.0);
        c.weighty = (0.5/12.0);
        frame.add(controlPanel, c);

        c.gridx = 2;
        c.gridy = 1;
        c.weightx =  (1.0/3.0);
        c.weighty = (0.5/12.0);
        frame.add(guestPlayerPanel, c);

        frame.setResizable(false);
        frame.setVisible(true);
    }

    public ChatPanel getChatPanel() {
        return chatPanel;
    }

    public void getName(){
        String hostName = JOptionPane.showInputDialog("Please input your name:");
        if(hostName == null || hostName.isEmpty()) {
            exit(0);
        }
        else {
            Var.name = hostName;
        }
    }

    public boolean isConnected() {
        JFrame connectionFrame = new JFrame("Connection");
        connectionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        connectionFrame.setSize(300, 200);
        connectionFrame.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;

        JLabel connectionLabel = new JLabel("Waiting for connection...");
        connectionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        connectionLabel.setVerticalAlignment(SwingConstants.CENTER);
        connectionFrame.add(connectionLabel, c);

        IPMessagePanel ipMessagePanel = new IPMessagePanel(Var.name, Var.IP);
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1;
        c.weighty = 1;
        connectionFrame.add(ipMessagePanel, c);

        connectionFrame.setResizable(false);

        connectionFrame.setVisible(true);

        while(connected == false){
            try{
                Thread.sleep(1000);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        connectionFrame.dispose();
        return true;
    }
}