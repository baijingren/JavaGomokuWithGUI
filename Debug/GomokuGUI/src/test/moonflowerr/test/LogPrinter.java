/**
 * @author Jingren Bai
 * @package moonflowerr.test
 * @since openJDK 22
 */

package moonflowerr.test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.logging.*;

/**
 * 输出日志
 */
public class LogPrinter {
	private static final Logger log = Logger.getLogger(LogPrinter.class.getName());
	private static final String LogFileName = "Test.log";

	static {
		Formatter formatter = new SimpleFormatter() {
			private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			@Override
			public String format(LogRecord record) {
				return String.format("[%s] (%s): %s\n", dateFormat.format(record.getMillis()), record.getLevel(), record.getMessage());
			}
		};
		FileHandler fileHandler;
		try {
			fileHandler = new FileHandler(LogFileName);
			fileHandler.setFormatter(formatter);
			log.addHandler(fileHandler);
		} catch (Exception e) {
			log.severe("Failed to create log file.");
		}

		log.setLevel(Level.ALL);
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

	public static void logStackTrace(Throwable throwable) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		throwable.printStackTrace(pw);
		log.log(Level.SEVERE, sw.toString());
	}
}
