/**
 * @since openJDK 22
 * @author moonflowerr
 * @package xyz.moonflowerr.GomokuWithGUI
 */
package xyz.moonflowerr.GomokuWithGUI;

import java.net.InetAddress;

// 不写注释是SB，包括我自己
public class Main {
    public static void init() throws Exception{
        InetAddress localhost = InetAddress.getLocalHost();
//        System.err.println(localhost.getHostAddress());
        Var.IP = localhost.getHostAddress();
    }

    public static void main(String[] args) throws Exception{
        init();
        Var.view.getName();
        Var.network.startServer();
        Var.view.createWindow();
    }
}
