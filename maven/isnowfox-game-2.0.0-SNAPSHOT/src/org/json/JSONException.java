// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   JSONException.java

package org.json;


public class JSONException extends Exception {

	private static final long serialVersionUID = 0L;
	private Throwable cause;

	public JSONException(String message) {
		super(message);
	}

	public JSONException(Throwable cause) {
		super(cause.getMessage());
		this.cause = cause;
	}

	public Throwable getCause() {
		return cause;
	}
}
