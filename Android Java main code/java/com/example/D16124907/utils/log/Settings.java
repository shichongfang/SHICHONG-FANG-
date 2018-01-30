package com.example.D16124907.utils.log;



public final class Settings {

	private int methodCount = 2;
	private boolean showThreadInfo = true;

	private LogLevel logLevel = LogLevel.FULL;

	public Settings hideThreadInfo() {
		showThreadInfo = false;
		return this;
	}

	public Settings setMethodCount(int methodCount) {
		this.methodCount = methodCount;
		return this;
	}

	public Settings setLogLevel(LogLevel logLevel) {
		this.logLevel = logLevel;
		return this;
	}

	public int getMethodCount() {
		return methodCount;
	}

	public boolean isShowThreadInfo() {
		return showThreadInfo;
	}

	public LogLevel getLogLevel() {
		return logLevel;
	}
}
