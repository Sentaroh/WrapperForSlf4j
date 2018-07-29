package org.slf4j;

public class LoggerFactory {
	private static LoggerOption logger_option=new LoggerOption();
	public static Logger getLogger(Class log_class) {
		return new Logger(log_class, logger_option);
	}
}
class LoggerOption {
	static public LoggerWriter logWriter=new LoggerWriter();//null;

	static public boolean debugEnabled=false;
	static public boolean errorEnabled=false;
	static public boolean infoEnabled=false;
	static public boolean traceEnabled=false; 
	static public boolean warnEnabled=false;
	static public boolean appendTime=false;
}

