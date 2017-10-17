// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   MaxValueException.java

package com.isnowfox.util;


public class MaxValueException extends RuntimeException {

	private static final long serialVersionUID = 0xa652d027c9183e2dL;

	public MaxValueException() {
	}

	public MaxValueException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MaxValueException(String message, Throwable cause) {
		super(message, cause);
	}

	public MaxValueException(String message) {
		super(message);
	}

	public MaxValueException(Throwable cause) {
		super(cause);
	}
}
