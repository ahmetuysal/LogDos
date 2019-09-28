package util;


public class Logger {

	static final LogLevel DEFAULT_LOG_LEVEL = LogLevel.INFO;
	static String DEFAULT_LOG_TITLE = ""; // TODO Tidy up.

	static final LogLevel MINIMUM_LOG_LEVEL = LogLevel.ERROR;

	public static void log(String message) {
		if (checkLevel(DEFAULT_LOG_LEVEL)) {
			DEFAULT_LOG_TITLE = Thread.currentThread().getStackTrace()[2].getMethodName() + "@"
					+ Thread.currentThread().getStackTrace()[2].getClassName();
			System.out.println("[" + DEFAULT_LOG_LEVEL.getTitle() + "] " + DEFAULT_LOG_TITLE + ": " + message);
		}
	}

	public static void log(String title, String message) {
		if (checkLevel(DEFAULT_LOG_LEVEL))
			System.out.println("[" + DEFAULT_LOG_LEVEL.getTitle() + "] " + title + ": " + message);
	}

	public static void log(LogLevel level, String message) {
		if (checkLevel(level)) {
			DEFAULT_LOG_TITLE = Thread.currentThread().getStackTrace()[2].getMethodName() + "@"
					+ Thread.currentThread().getStackTrace()[2].getClassName();
			System.out.println("[" + level.getTitle() + "] " + DEFAULT_LOG_TITLE + ": " + message);
		}
	}

	public static void log(LogLevel level, String title, String message) {
		if (checkLevel(level))
			System.out.println("[" + level.getTitle() + "] " + title + ": " + message);
	}
	
	public static void error(Exception e) {
		if (checkLevel(LogLevel.ERROR))
			System.err.println("[" + LogLevel.ERROR.getTitle() + " | " + Thread.currentThread().getStackTrace()[2].getMethodName() + "@"
					+ Thread.currentThread().getStackTrace()[2].getClassName() + "] " + e.getClass().getSimpleName() + ": " + e.getMessage());
		e.printStackTrace();
	}
	
	public static void error(String title, String message) {
		if (checkLevel(LogLevel.ERROR))
			System.err.println("[" + LogLevel.ERROR.getTitle() + " | " + Thread.currentThread().getStackTrace()[2].getMethodName() + "@"
			+ Thread.currentThread().getStackTrace()[2].getClassName() + "] " + title + ": " + message);
	}

	private static boolean checkLevel(LogLevel logLevel) {
		return logLevel.getLevel() >= MINIMUM_LOG_LEVEL.getLevel();
	}

}
