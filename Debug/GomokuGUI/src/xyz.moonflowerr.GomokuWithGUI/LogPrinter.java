/**
 * @author Jingren Bai
 * @package xyz.moonflowerr.GomokuWithGUI
 * @since openJDK 22
 */

package xyz.moonflowerr.GomokuWithGUI;

import java.text.SimpleDateFormat;
import java.util.logging.*;

/**
 * 输出日志
 */
public class LogPrinter {
	private static final Logger log = Logger.getLogger(LogPrinter.class.getName());

	static {
		Formatter formatter = new SimpleFormatter(){
			private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			@Override
			public String format(LogRecord record) {
				return String.format("[%s] (%s): %s\n", dateFormat.format(record.getMillis()), record.getLevel(), record.getMessage());
			}
		};
		FileHandler fileHandler;
		try {
			fileHandler = new FileHandler("Game.log");
			fileHandler.setFormatter(formatter);
			log.addHandler(fileHandler);
		} catch (Exception e) {
			log.severe("Failed to create log file.");
		}

		log.setLevel(Level.ALL);
		log.info("This is a log message.");
		log.warning("This is a warning message.");
		log.severe("This is a severe message.");
	}

	public static void printLog(String message) {
		log.info(message);
	}

	public static void printWarning(String message) {
		log.warning(message);
	}

	public static void printSevere(String message) {
		log.severe(message);
	}
}
