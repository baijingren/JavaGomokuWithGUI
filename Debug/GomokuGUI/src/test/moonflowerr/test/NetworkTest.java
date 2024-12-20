/**
 * @author Jingren Bai
 * @package moonflowerr.test
 * @since openJDK 22
 */
package moonflowerr.test;

import org.junit.jupiter.api.*;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertTrue;

import xyz.moonflowerr.GomokuWithGUI.Network.Network;

public class NetworkTest {
	private ExecutorService executorService;
	private static final int TEST_PORT = 17890;
	private static final String TEST_IP = "localhost";
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private Network p2pNode1;
	private Network p2pNode2;

	@BeforeEach
	public void setUp() throws Exception {
		LogPrinter.printLog("Network test started.");
		Runnable node1 = () -> {
			try {
				p2pNode1 = new Network(17890);
			} catch (IOException e) {
//				throw new RuntimeException(e);
				LogPrinter.printSevere("Failed to set up node 1." + e.getMessage());
			}
		};

//		Runnable node2 = () -> {
//			try {
//				p2pNode2 = new Network(17891);
//			} catch (IOException e) {
//				LogPrinter.printSevere("Failed to set up node 2." + e.getMessage());
//			}
//			p2pNode2.startServer();
//		};

		executorService = Executors.newFixedThreadPool(2);
		executorService.submit(node1);
//		executorService.submit(node2);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			LogPrinter.printSevere("Failed to set up test environment." + e.getMessage());
		}
	}

	@AfterEach
	public void tearDown() throws Exception {
		p2pNode1.close();
//		p2pNode2.close();
		executorService.shutdown();
		executorService.awaitTermination(1, TimeUnit.SECONDS);
	}

	@Test
	public void testConnectionToTarget() {
		p2pNode1.startServer();
		try{
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			LogPrinter.printSevere("Failed to set up test environment." + e.getMessage());
		}
		try {
			clientSocket = new Socket(InetAddress.getByName(TEST_IP), TEST_PORT);
		} catch (IOException e) {
			LogPrinter.printSevere("Failed to connect to target." + e.getMessage());
		}
		assertTrue(clientSocket.isConnected());
		while(true){
			AtomicReference<BufferedReader> in = new AtomicReference<>();
			AtomicReference<PrintWriter> out = new AtomicReference<>();
			new Thread(() -> {
				try {
					out.set(new PrintWriter(clientSocket.getOutputStream(), true));
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
				out.get().println("Time:" + System.currentTimeMillis() + ";Unit test.");
			}).start();
			new Thread(() -> {
				try {
					in.set(new BufferedReader(new InputStreamReader(clientSocket.getInputStream())));
					String line = in.get().readLine();
					if (line != null) {
						LogPrinter.printLog("Received: " + line + "(NetworkTest.testBeingConnected)");
						assertTrue(line.contains("Unit"));
					}
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}).start();
		}
	}

	@Test
	public void testBeingConnected() throws IOException {
		try {
			serverSocket = new ServerSocket(17890);
			Thread.sleep(1000);
		} catch (IOException e) {
			LogPrinter.printSevere("Failed to set up server socket." + e.getMessage());
		} catch (InterruptedException e) {
			LogPrinter.printSevere("Failed to set up test environment." + e.getMessage());
		}
		p2pNode1.connectToTarget("localhost");
		try {
			Thread.sleep(1000);
			clientSocket = serverSocket.accept();
		} catch (IOException e) {
			LogPrinter.printSevere("Failed to accept connection." + e.getMessage());
		} catch (InterruptedException e) {
			LogPrinter.printSevere("Failed to set up test environment." + e.getMessage());
		}
		while(true){
			AtomicReference<BufferedReader> in = new AtomicReference<>();
			AtomicReference<PrintWriter> out = new AtomicReference<>();
				new Thread(() -> {
					try {
						out.set(new PrintWriter(clientSocket.getOutputStream(), true));
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
					out.get().println("Time:" + System.currentTimeMillis() + ";Unit test.");
				}).start();
				new Thread(() -> {
					try {
						in.set(new BufferedReader(new InputStreamReader(clientSocket.getInputStream())));
						String line = in.get().readLine();
						if (line != null) {
							LogPrinter.printLog("Received: " + line + "(NetworkTest.testBeingConnected)");
							assertTrue(line.contains("Unit"));
						}
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}).start();
		}
	}
}
