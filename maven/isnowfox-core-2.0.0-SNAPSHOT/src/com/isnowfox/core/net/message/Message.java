// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   Message.java

package com.isnowfox.core.net.message;

import com.isnowfox.core.io.*;
import com.isnowfox.core.net.Session;
import java.io.IOException;

public interface Message {

	public abstract void decode(Input input) throws IOException, ProtocolException;

	public abstract void encode(Output output) throws IOException, ProtocolException;

	public abstract int getMessageType();

	public abstract int getMessageId();

	public abstract Session getSession();

	public abstract void setSession(Session session);
}
