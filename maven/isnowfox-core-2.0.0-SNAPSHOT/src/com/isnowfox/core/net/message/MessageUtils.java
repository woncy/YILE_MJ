// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   MessageUtils.java

package com.isnowfox.core.net.message;

import com.isnowfox.core.io.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

// Referenced classes of package com.isnowfox.core.net.message:
//			Message

public final class MessageUtils {

	public MessageUtils() {
	}

	public static final byte[] toBytes(Message msg) throws IOException, ProtocolException {
		ByteArrayOutputStream ba = new ByteArrayOutputStream(128);
		Output out = MarkCompressOutput.create(ba);
		msg.encode(out);
		return ba.toByteArray();
	}

	public static final byte[] toFullBytes(Message msg) throws IOException, ProtocolException {
		ByteArrayOutputStream ba = new ByteArrayOutputStream(128);
		Output out = MarkCompressOutput.create(ba);
		out.writeInt(msg.getMessageType());
		out.writeInt(msg.getMessageId());
		msg.encode(out);
		return ba.toByteArray();
	}
}
