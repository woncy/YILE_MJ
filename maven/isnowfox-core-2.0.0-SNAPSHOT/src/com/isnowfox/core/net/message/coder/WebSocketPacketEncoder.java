// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   WebSocketPacketEncoder.java

package com.isnowfox.core.net.message.coder;

import com.isnowfox.core.net.message.*;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import java.util.List;

public class WebSocketPacketEncoder extends MessageToMessageEncoder {

	public WebSocketPacketEncoder() {
	}

	protected void encode(ChannelHandlerContext ctx, Packet msg, List out) throws Exception {
		int len = msg.getLength();
		if (len > 0xffffff) {
			throw MessageException.newLengthException(len);
		}
		if (msg.getBufOffset() > 0) {
			ByteBuf buffer = ctx.alloc().buffer(len);
			buffer.writeBytes(msg.getBuf(), msg.getBufOffset(), len);
			out.add(new BinaryWebSocketFrame(buffer));
		} else {
			msg.getBuf().retain();
			out.add(new BinaryWebSocketFrame(msg.getBuf()));
		}
	}

	protected volatile void encode(ChannelHandlerContext channelhandlercontext, Object obj, List list) throws Exception {
		encode(channelhandlercontext, (Packet)obj, list);
	}
}
