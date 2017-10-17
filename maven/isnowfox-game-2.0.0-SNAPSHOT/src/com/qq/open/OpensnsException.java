// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   OpensnsException.java

package com.qq.open;


public class OpensnsException extends Exception {

	private static final long serialVersionUID = 0x7265786c69616f0aL;
	private int code;

	public OpensnsException(int code, String msg) {
		super(msg);
		this.code = code;
	}

	public OpensnsException(int code, Exception ex) {
		super(ex);
		this.code = code;
	}

	public int getErrorCode() {
		return code;
	}
}
