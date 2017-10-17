// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   RangePxMsg.java

package com.isnowfox.game.proxy.message;

import com.isnowfox.core.net.message.Message;
import io.netty.buffer.ByteBuf;

// Referenced classes of package com.isnowfox.game.proxy.message:
//			AbstractEncodeWrapper, RangePxMsg

public static class RangePxMsg$EncodeWrapper extends AbstractEncodeWrapper {

	private final short userList[];

	public int getType() {
		return 1;
	}

	protected void encodeData(ByteBuf out) throws Exception {
		out.writeShort(userList.length);
		short aword0[] = userList;
		int i = aword0.length;
		for (int j = 0; j < i; j++) {
			Short userId = Short.valueOf(aword0[j]);
			out.writeShort(userId.shortValue());
		}

	}

	public String toString() {
		return (new StringBuilder()).append("RangePxMsg.Wrapper [userList=").append(userList).append("]").append(super.toString()).toString();
	}

	public RangePxMsg$EncodeWrapper(Message msg, short userList[]) {
		this.msg = msg;
		this.userList = userList;
	}
}
