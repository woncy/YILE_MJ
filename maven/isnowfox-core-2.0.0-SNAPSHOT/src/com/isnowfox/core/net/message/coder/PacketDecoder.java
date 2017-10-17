// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   PacketDecoder.java

package com.isnowfox.core.net.message.coder;

import com.isnowfox.core.net.*;
import com.isnowfox.core.net.message.MessageProtocol;
import com.isnowfox.core.net.message.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.Attribute;
import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {

	private final NetPacketHandler handler;

	public PacketDecoder(NetPacketHandler handler) {
		this.handler = handler;
	}

	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List out) throws Exception {
		Session session = (Session)ctx.channel().attr(ChannelHandler.SESSION).get();
		if (handler.onIn(session, in)) {
			int readerIndex = in.readerIndex();
			int readableLen = in.readableBytes();
			if (readableLen >= 4) {
				int len = in.readUnsignedMedium();
				byte type = in.readByte();
				if (readableLen >= len + 4) {
					ByteBuf buf = ctx.alloc().buffer(len);
					in.readBytes(buf, len);
					Packet p = new Packet(len, type, buf, 0);
					out.add(p);
					return;
				}
			}
			in.readerIndex(readerIndex);
		}
	}
}
