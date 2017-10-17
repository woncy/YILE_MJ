// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   ExpandMessage.java

package com.isnowfox.core.net.message;


// Referenced classes of package com.isnowfox.core.net.message:
//			AbstractMessage, MessageProtocol

public abstract class ExpandMessage extends AbstractMessage {

	public ExpandMessage() {
	}

	public final int getMessageType() {
		return 0;
	}

	public String toString() {
		return (new StringBuilder()).append("ExpandMessage [messageId=").append(getMessageId()).append("]").toString();
	}
}
