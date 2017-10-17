// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   MessageDecoder.java

package com.isnowfox.core.net.message.coder;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.MarkCompressInput;
import com.isnowfox.core.net.*;
import com.isnowfox.core.net.message.*;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.Attribute;
import java.util.List;

public class MessageDecoder extends ByteToMessageDecoder {

	private final MessageFactory messageFactory;
	private final NetMessageHandler handler;

	public MessageDecoder(MessageFactory messageFactory, NetMessageHandler handler) {
		if (messageFactory == null) {
			throw new NullPointerException("messageFactory");
		} else {
			this.messageFactory = messageFactory;
			this.handler = handler;
			return;
		}
	}

	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List out) throws Exception {
		Session session = (Session)ctx.channel().attr(ChannelHandler.SESSION).get();
		if (handler.onIn(session, in)) {
			int readerIndex = in.readerIndex();
			int readableLen = in.readableBytes();
			if (readableLen >= 4) {
				int len = in.readUnsignedMedium();
				in.readByte();
				if (readableLen >= len + 4) {
					Input i = MarkCompressInput.create(in);
					int t = i.readInt();
					int id = i.readInt();
					Message msg = messageFactory.getMessage(t, id);
					msg.decode(i);
					out.add(msg);
					return;
				}
			}
			in.readerIndex(readerIndex);
		}
	}
}
