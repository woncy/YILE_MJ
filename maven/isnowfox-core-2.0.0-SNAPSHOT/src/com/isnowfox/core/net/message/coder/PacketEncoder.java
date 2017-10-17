// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   PacketEncoder.java

package com.isnowfox.core.net.message.coder;

import com.isnowfox.core.net.message.*;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketEncoder extends MessageToByteEncoder {

	public PacketEncoder() {
	}

	protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) throws Exception {
		int len = msg.getLength();
		if (len > 0xffffff) {
			throw MessageException.newLengthException(len);
		} else {
			out.writeMedium(len);
			out.writeByte(msg.getType());
			out.writeBytes(msg.getBuf(), msg.getBufOffset(), len);
			return;
		}
	}

	protected volatile void encode(ChannelHandlerContext channelhandlercontext, Object obj, ByteBuf bytebuf) throws Exception {
		encode(channelhandlercontext, (Packet)obj, bytebuf);
	}
}
