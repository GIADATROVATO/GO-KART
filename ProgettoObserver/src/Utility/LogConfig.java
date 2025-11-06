package Utility;

public class LogConfig {
	/*
	 *  quando ho bisogno di scegliere un file xml su x file
	 */
	 public static void init(String env) {
	        String file = env.equalsIgnoreCase("logFile") ? "log4j2-log4j2.xml" : "log4j2-logFile.xml";
	        System.setProperty("log4j.configurationFile", "src/ " + file);
	    }
}
