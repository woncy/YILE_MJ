// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   IllegalConfigException.java

package com.isnowfox.web.config;


public class IllegalConfigException extends Exception {

	private static final long serialVersionUID = 0x58bb73948e6c180L;

	public IllegalConfigException() {
	}

	public IllegalConfigException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public IllegalConfigException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalConfigException(String message) {
		super(message);
	}

	public IllegalConfigException(Throwable cause) {
		super(cause);
	}
}
