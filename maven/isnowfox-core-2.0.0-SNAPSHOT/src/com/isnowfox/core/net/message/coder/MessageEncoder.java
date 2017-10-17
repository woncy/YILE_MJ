// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   MessageEncoder.java

package com.isnowfox.core.net.message.coder;

import com.isnowfox.core.io.MarkCompressOutput;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.net.message.*;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MessageEncoder extends MessageToByteEncoder {

	public MessageEncoder() {
	}

	protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
		int startIdx = out.writerIndex();
		out.writeMedium(0);
		out.writeByte(0);
		Output o = MarkCompressOutput.create(out);
		o.writeInt(msg.getMessageType());
		o.writeInt(msg.getMessageId());
		msg.encode(o);
		o.close();
		int endIdx = out.writerIndex();
		int len = endIdx - startIdx - 4;
		if (len > 0xffffff) {
			throw MessageException.newLengthException(len);
		} else {
			out.setMedium(startIdx, len);
			return;
		}
	}

	protected volatile void encode(ChannelHandlerContext channelhandlercontext, Object obj, ByteBuf bytebuf) throws Exception {
		encode(channelhandlercontext, (Message)obj, bytebuf);
	}
}
