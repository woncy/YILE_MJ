// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   MessageProtocol.java

package com.isnowfox.core.net.message;

import com.isnowfox.core.io.MarkCompressProtocol;

public interface MessageProtocol {

	public static final int MESSAGE_MAX = 0xffffff;
	public static final int LENGTH_BYTE_NUMS = 3;
	public static final int HEAD_LENGTH = 4;
	public static final int TYPE_NORMAL = 0;
	public static final int TYPE_GZIP = 1;
	public static final int TYPE_OR_ID_MAX = 237;
	public static final int EXPAND_MESSAGE_TYPE = 0;
	public static final int LOGIN_MESSAGE_TYPE = 7;
}
