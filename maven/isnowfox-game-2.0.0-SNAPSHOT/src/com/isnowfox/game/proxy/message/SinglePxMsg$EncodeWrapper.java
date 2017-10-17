// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   SinglePxMsg.java

package com.isnowfox.game.proxy.message;

import com.isnowfox.core.net.message.Message;
import io.netty.buffer.ByteBuf;

// Referenced classes of package com.isnowfox.game.proxy.message:
//			AbstractEncodeWrapper, SinglePxMsg

public static class SinglePxMsg$EncodeWrapper extends AbstractEncodeWrapper {

	private short sessionId;

	public int getType() {
		return 0;
	}

	protected void encodeData(ByteBuf out) throws Exception {
		out.writeShort(sessionId);
	}

	public String toString() {
		return (new StringBuilder()).append("SinglePxMsg.Wrapper [sessionId=").append(sessionId).append("]").append(super.toString()).toString();
	}

	public SinglePxMsg$EncodeWrapper(short sessionId, Message msg) {
		this.sessionId = sessionId;
		this.msg = msg;
	}
}
