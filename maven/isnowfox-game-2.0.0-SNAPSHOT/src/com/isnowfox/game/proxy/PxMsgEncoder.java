// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   PxMsgEncoder.java

package com.isnowfox.game.proxy;

import com.isnowfox.game.proxy.message.PxMsg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PxMsgEncoder extends MessageToByteEncoder {

	public PxMsgEncoder() {
	}

	protected void encode(ChannelHandlerContext ctx, PxMsg msg, ByteBuf out) throws Exception {
		int startIdx = out.writerIndex();
		out.writeInt(0);
		out.writeByte(msg.getType());
		msg.encode(out);
		int endIdx = out.writerIndex();
		int len = endIdx - startIdx - 5;
		out.setInt(startIdx, len);
	}

	protected volatile void encode(ChannelHandlerContext channelhandlercontext, Object obj, ByteBuf bytebuf) throws Exception {
		encode(channelhandlercontext, (PxMsg)obj, bytebuf);
	}
}
