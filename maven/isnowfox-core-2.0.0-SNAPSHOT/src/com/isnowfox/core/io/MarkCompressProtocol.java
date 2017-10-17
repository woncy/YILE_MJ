// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   MarkCompressProtocol.java

package com.isnowfox.core.io;


public interface MarkCompressProtocol {

	public static final String DEFAULT_CHARSET = "utf-8";
	public static final boolean DEFAULT_IS_BIG_ENDIAN = true;
	public static final int THREE_MAX = 0x7fffff;
	public static final int THREE_MIN = 0xff800000;
	public static final int FIRST_WIDTH = 7;
	public static final int OTHER_WIDTH = 8;
	public static final int TYPE_NULL_OR_ZERO_OR_FALSE = 254;
	public static final int TYPE_TRUE = 253;
	public static final int TYPE_INT_1BYTE = 252;
	public static final int TYPE_INT_2BYTE = 251;
	public static final int TYPE_INT_3BYTE = 250;
	public static final int TYPE_INT_4BYTE = 249;
	public static final int TYPE_INT_5BYTE = 248;
	public static final int TYPE_INT_6BYTE = 247;
	public static final int TYPE_INT_7BYTE = 246;
	public static final int TYPE_INT_8BYTE = 245;
	public static final int TYPE_STRING = 244;
	public static final int TYPE_BYTES = 243;
	public static final int TYPE_MAX = 241;
	public static final int TYPE_MIN = 0;
	public static final int TYPE_MINUS = 4;
	public static final int TYPE_ONE_BYTE_MAX = 237;
	public static final int TYPE_ONE_BYTE_MIN = -4;
}
