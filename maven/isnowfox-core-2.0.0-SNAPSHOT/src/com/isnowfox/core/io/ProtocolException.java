// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   ProtocolException.java

package com.isnowfox.core.io;


public class ProtocolException extends Exception {

	private static final long serialVersionUID = 0x16177e92b08f7172L;

	public ProtocolException() {
	}

	public ProtocolException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ProtocolException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProtocolException(String message) {
		super(message);
	}

	public ProtocolException(Throwable cause) {
		super(cause);
	}

	public static final ProtocolException newTypeException(int b) {
		return new ProtocolException((new StringBuilder()).append("error type :").append(b).toString());
	}
}
