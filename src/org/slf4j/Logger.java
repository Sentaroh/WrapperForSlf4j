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

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Logger {

	private String mClassName="";
//	private static LoggerOption mLoggerOption=null;
	private StringBuilder logBuilder=new StringBuilder(1024);
	
	public Logger(Class log_class, LoggerOption log_opt) {
		mClassName=log_class.getName();
//		LoggerOption.logWriter=new LoggerWriter();
//		mLoggerOption=log_opt;
	}
	
	private void putLogMsg(Exception e, String ...msg) {
		synchronized(logBuilder) {
			logBuilder.setLength(0);
			if (LoggerOption.appendTime) {
				logBuilder.append(convDateTime(System.currentTimeMillis()));
				logBuilder.append(" ");
			}
			for(String m:msg) logBuilder.append(m).append(" ");
			if (e!=null) {
//				log_builder.append(e.getMessage());
				StringWriter stringWriter = new StringWriter();
	            PrintWriter printWriter = new PrintWriter( stringWriter );
	            e.printStackTrace( printWriter );
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
	
	public void info(String msg, Exception e) {
		if (isInfoEnabled())    putLogMsg(e, "[Info ]", mClassName, msg);
	}
	
	public void debug(String msg, Exception e) {
		if (isDebugEnabled())   putLogMsg(e, "[Debug]", mClassName,msg);
	}
	public void warn(String msg, Exception e) {
		if (isWarnEnabled())    putLogMsg(e, "[Warn ]", mClassName, msg);
	}
	public void trace(String msg, Exception e) {
		if (isTraceEnabled())   putLogMsg(e, "[Trace]",mClassName,msg);
	}

	public void error(String msg, Exception e) {
		if (isErrorEnabled())   putLogMsg(e, "[Error]",mClassName,msg);
	}

	public void info(String msg) {
		if (isInfoEnabled()) info(msg,null);
	}
	public void debug(String msg) {
		if (isDebugEnabled()) debug(msg,null);
	}
	public void warn(String msg) {
		if (isWarnEnabled())warn(msg,null);
	}
	public void trace(String msg) {
		if (isTraceEnabled()) trace(msg,null);
	}

	public void error(String msg) {
		if (isErrorEnabled())error(msg, null);
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
    	
    	final StringBuilder sb=new StringBuilder(64)
    		.append(s_yyyy).append("/");
    	
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
