// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   AllPxMsg.java

package com.isnowfox.game.proxy.message;

import com.isnowfox.core.net.message.Message;
import io.netty.buffer.ByteBuf;

// Referenced classes of package com.isnowfox.game.proxy.message:
//			AbstractEncodeWrapper, AllPxMsg

public static class AllPxMsg$EncodeWrapper extends AbstractEncodeWrapper {

	public int getType() {
		return 2;
	}

	protected void encodeData(ByteBuf bytebuf) throws Exception {
	}

	public String toString() {
		return (new StringBuilder()).append("AllPxMsg.Wrapper []").append(super.toString()).toString();
	}

	public AllPxMsg$EncodeWrapper(Message msg) {
		super.msg = msg;
	}
}
