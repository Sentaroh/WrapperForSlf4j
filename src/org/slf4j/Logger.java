package org.slf4j;
/*
The MIT License (MIT)
Copyright (c) 2018 Sentaroh

Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights to use,
copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
and to permit persons to whom the Software is furnished to do so, subject to
the following conditions:

The above copyright notice and this permission notice shall be included in all copies or
substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
OTHER DEALINGS IN THE SOFTWARE.

*/

import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Logger {

	private String mClassName = "";
//	private static LoggerOption mLoggerOption=null;
	private StringBuilder logBuilder=new StringBuilder(1024);

	public Logger(Class<?> log_class, LoggerOption log_opt) {
		mClassName=log_class.getName();
//		LoggerOption.logWriter=new LoggerWriter();
//		mLoggerOption=log_opt;
	}

	private void putLogMsg(Throwable t, String ...msg) {
		synchronized(logBuilder) {
			logBuilder.setLength(0);
			if (LoggerOption.appendTime) {
				logBuilder.append(convDateTime(System.currentTimeMillis()));
				logBuilder.append(" ");
			}
			for(String m:msg) logBuilder.append(m).append(" ");
			if (t != null) {
//				log_builder.append(t.getMessage());
				StringWriter stringWriter = new StringWriter();
	            PrintWriter printWriter = new PrintWriter( stringWriter );
	            t.printStackTrace( printWriter );
	            logBuilder.append("\n").append(stringWriter.toString());
			}
			writeMsg(logBuilder.toString());
		}
	}

	public void setWriter(LoggerWriter lw) {
		LoggerOption.logWriter=lw;
	}

	private void writeMsg(String msg) {
		LoggerOption.logWriter.write(msg);
	}

    /**
    * For formatted messages, first substitute arguments and then log.
    *
    * @param level
    * @param format
    * @param arg1
    * @param arg2
    */
    private void formatAndLog(String type, String class_name, String format, Object arg1,
                            Object arg2) {
        FormattingTuple tp = MessageFormatter.format(format, arg1, arg2);
        putLogMsg(tp.getThrowable(), type, class_name, tp.getMessage());
    }

    /**
    * For formatted messages, first substitute arguments and then log.
    *
    * @param level
    * @param format
    * @param arguments a list of 3 ore more arguments
    */
    private void formatAndLog(String type, String class_name, String format, Object... arguments) {
        FormattingTuple tp = MessageFormatter.arrayFormat(format, arguments);
        putLogMsg(tp.getThrowable(), type, class_name, tp.getMessage());
    }

    /**
    * A simple implementation which logs messages of level TRACE according
    * to the format outlined above.
    */
	public void trace(String msg) {
		if (isTraceEnabled()) putLogMsg(null, "[Trace]", mClassName, msg);
	}

    /**
    * Perform single parameter substitution before logging the message of level
    * TRACE according to the format outlined above.
    */
    public void trace(String format, Object param1) {
        if (isTraceEnabled()) formatAndLog("[Trace]", mClassName, format, param1, null);
    }

    /**
    * Perform double parameter substitution before logging the message of level
    * TRACE according to the format outlined above.
    */
    public void trace(String format, Object param1, Object param2) {
        if (isTraceEnabled()) formatAndLog("[Trace]", mClassName, format, param1, param2);
    }

    /**
    * Perform double parameter substitution before logging the message of level
    * TRACE according to the format outlined above.
    */
    public void trace(String format, Object... argArray) {
        if (isTraceEnabled()) formatAndLog("[Trace]", mClassName, format, argArray);
    }

    /** Log a message of level TRACE, including an exception. */
	public void trace(String msg, Throwable t) {
		if (isTraceEnabled()) putLogMsg(t, "[Trace]", mClassName, msg);
	}

    /**
     * A simple implementation which logs messages of level DEBUG according to
     * the format outlined above.
     */
    public void debug(String msg) {
        if (isDebugEnabled()) putLogMsg(null, "[Debug]", mClassName, msg);
    }

    /**
     * Perform single parameter substitution before logging the message of level
     * DEBUG according to the format outlined above.
     */
    public void debug(String format, Object param1) {
        if (isDebugEnabled()) formatAndLog("[Debug]", mClassName, format, param1, null);
    }

    /**
     * Perform double parameter substitution before logging the message of level
     * DEBUG according to the format outlined above.
     */
    public void debug(String format, Object param1, Object param2) {
        if (isDebugEnabled()) formatAndLog("[Debug]", mClassName, format, param1, param2);
    }

    /**
     * Perform double parameter substitution before logging the message of level
     * DEBUG according to the format outlined above.
     */
    public void debug(String format, Object... argArray) {
        if (isDebugEnabled()) formatAndLog("[Debug]", mClassName, format, argArray);
    }

    /** Log a message of level DEBUG, including an exception. */
	public void debug(String msg, Throwable t) {
		if (isDebugEnabled()) putLogMsg(t, "[Debug]", mClassName, msg);
	}

    /**
     * A simple implementation which logs messages of level INFO according to
     * the format outlined above.
     */
    public void info(String msg) {
        if (isInfoEnabled()) putLogMsg(null, "[Info]", mClassName, msg);
    }

    /**
     * Perform single parameter substitution before logging the message of level
     * INFO according to the format outlined above.
     */
    public void info(String format, Object arg) {
        if (isInfoEnabled()) formatAndLog("[Info]", mClassName, format, arg, null);
    }

    /**
     * Perform double parameter substitution before logging the message of level
     * INFO according to the format outlined above.
     */
    public void info(String format, Object arg1, Object arg2) {
        if (isInfoEnabled()) formatAndLog("[Info]", mClassName, format, arg1, arg2);
    }

    /**
     * Perform double parameter substitution before logging the message of level
     * INFO according to the format outlined above.
     */
    public void info(String format, Object... argArray) {
        if (isInfoEnabled()) formatAndLog("[Info]", mClassName, format, argArray);
    }

    /** Log a message of level INFO, including an exception. */
    public void info(String msg, Throwable t) {
        if (isInfoEnabled()) putLogMsg(t, "[Info]", mClassName, msg);
    }

    /**
     * A simple implementation which always logs messages of level WARN
     * according to the format outlined above.
     */
    public void warn(String msg) {
        if (isWarnEnabled()) putLogMsg(null, "[Warn]", mClassName, msg);
    }

    /**
     * Perform single parameter substitution before logging the message of level
     * WARN according to the format outlined above.
     */
    public void warn(String format, Object arg) {
        if (isWarnEnabled()) formatAndLog("[Warn]", mClassName, format, arg, null);
    }

    /**
     * Perform double parameter substitution before logging the message of level
     * WARN according to the format outlined above.
     */
    public void warn(String format, Object arg1, Object arg2) {
        if (isWarnEnabled()) formatAndLog("[Warn]", mClassName, format, arg1, arg2);
    }

    /**
     * Perform double parameter substitution before logging the message of level
     * WARN according to the format outlined above.
     */
    public void warn(String format, Object... argArray) {
        if (isWarnEnabled()) formatAndLog("[Warn]", mClassName, format, argArray);
    }

    /** Log a message of level WARN, including an exception. */
    public void warn(String msg, Throwable t) {
        if (isWarnEnabled()) putLogMsg(t, "[Warn]", mClassName, msg);
    }

    /**
     * A simple implementation which always logs messages of level ERROR
     * according to the format outlined above.
     */
    public void error(String msg) {
        if (isErrorEnabled()) putLogMsg(null, "[Error]", mClassName, msg);
    }

    /**
     * Perform single parameter substitution before logging the message of level
     * ERROR according to the format outlined above.
     */
    public void error(String format, Object arg) {
        if (isErrorEnabled()) formatAndLog("[Error]", mClassName, format, arg, null);
    }

    /**
     * Perform double parameter substitution before logging the message of level
     * ERROR according to the format outlined above.
     */
    public void error(String format, Object arg1, Object arg2) {
        if (isErrorEnabled()) formatAndLog("[Error]", mClassName, format, arg1, arg2);
    }

    /**
     * Perform double parameter substitution before logging the message of level
     * ERROR according to the format outlined above.
     */
    public void error(String format, Object... argArray) {
        if (isErrorEnabled()) formatAndLog("[Error]", mClassName, format, argArray);
    }

    /** Log a message of level ERROR, including an exception. */
    public void error(String msg, Throwable t) {
        if (isErrorEnabled()) putLogMsg(t, "[Error]", mClassName, msg);
    }

	public boolean isDebugEnabled() {
		return LoggerOption.debugEnabled;
	}

	public boolean isInfoEnabled() {
		return LoggerOption.infoEnabled;
	}
	public boolean isErrorEnabled() {
		return LoggerOption.errorEnabled;
	}

	public boolean isTraceEnabled() {
		return LoggerOption.traceEnabled;
	}

	public boolean isWarnEnabled() {
		return LoggerOption.warnEnabled;
	}

    final static private String convDateTime(long time) {
    	final Calendar gcal = new GregorianCalendar();
    	gcal.setTimeInMillis(time);
    	final int yyyy=gcal.get(Calendar.YEAR);
    	final int month=gcal.get(Calendar.MONTH)+1;
    	final int day=gcal.get(Calendar.DATE);
    	final int hours=gcal.get(Calendar.HOUR_OF_DAY);
    	final int minutes=gcal.get(Calendar.MINUTE);
    	final int second=gcal.get(Calendar.SECOND);
    	final int ms=gcal.get(Calendar.MILLISECOND);
    	
    	final String s_yyyy=String.valueOf(yyyy);
    	final String s_month=String.valueOf(month);
    	final String s_day=String.valueOf(day);
    	final String s_hours=String.valueOf(hours);
    	final String s_minutes=String.valueOf(minutes);
    	final String s_second=String.valueOf(second);
    	final String s_ms=String.valueOf(ms);
    	
    	final StringBuilder sb=new StringBuilder(64).append(s_yyyy).append("/");
    	
    	if (month>9) sb.append(s_month);
    	else sb.append("0").append(s_month);
    	sb.append("/");
    	
    	if (day>9) sb.append(s_day);
    	else sb.append("0").append(s_day);
    	sb.append(" ");
    	
    	if (hours>9) sb.append(s_hours);
    	else sb.append("0").append(s_hours);
    	sb.append(":");
    	
    	if (minutes>9) sb.append(s_minutes);
    	else sb.append("0").append(s_minutes);
    	sb.append(":");

    	if (second>9) sb.append(s_second);
    	else sb.append("0").append(s_second);
    	sb.append(".");
    	
    	if (ms>99) sb.append(s_ms);
    	else {
    		if (ms>9) sb.append("0").append(s_ms);
    		else sb.append("00").append(s_ms);
    	}
    	return sb.toString();
    };

	public void setAppendTime(boolean append) {
		LoggerOption.appendTime=append;
	}

	/**
	   * Set logger option
	   *
	   * @param debug true is enable debug
	   * @param error true is enable error
	   * @param info true is enable info
	   * @param trace true is enable trace
	   * @param warn true is enable warn
	   */
	public void setLogOption(boolean debug, boolean error, boolean info, boolean trace, boolean warn) {
		LoggerOption.debugEnabled=debug;
		LoggerOption.errorEnabled=error;
		LoggerOption.infoEnabled=info;
		LoggerOption.traceEnabled=trace;
		LoggerOption.warnEnabled=warn;
	}

}
