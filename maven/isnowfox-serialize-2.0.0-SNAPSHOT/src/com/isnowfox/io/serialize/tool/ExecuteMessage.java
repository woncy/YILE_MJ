// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   ExecuteMessage.java

package com.isnowfox.io.serialize.tool;

import com.isnowfox.io.serialize.tool.model.Message;

public interface ExecuteMessage {

	public abstract void exe(Message message) throws Exception;
}
