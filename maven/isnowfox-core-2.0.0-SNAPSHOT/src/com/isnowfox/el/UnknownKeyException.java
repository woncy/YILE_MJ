// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   UnknownKeyException.java

package com.isnowfox.el;


public class UnknownKeyException extends Exception {

	private static final long serialVersionUID = 0xcab126231e010638L;

	public UnknownKeyException() {
	}

	public UnknownKeyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UnknownKeyException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnknownKeyException(String message) {
		super(message);
	}

	public UnknownKeyException(Throwable cause) {
		super(cause);
	}
}
