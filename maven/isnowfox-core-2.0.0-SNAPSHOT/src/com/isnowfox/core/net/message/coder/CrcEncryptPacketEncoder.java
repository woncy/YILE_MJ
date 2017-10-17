// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   CrcEncryptPacketEncoder.java

package com.isnowfox.core.net.message.coder;

import com.isnowfox.core.net.CrcEncryptChannelHandler;
import com.isnowfox.core.net.message.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.Attribute;

// Referenced classes of package com.isnowfox.core.net.message.coder:
//			CrcEncryptCoder

public class CrcEncryptPacketEncoder extends MessageToByteEncoder {

	public CrcEncryptPacketEncoder() {
	}

	protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) throws Exception {
		CrcEncryptCoder coder = (CrcEncryptCoder)ctx.channel().attr(CrcEncryptChannelHandler.CRC_ENCRYPT).get();
		coder.write(out, msg);
	}

	protected volatile void encode(ChannelHandlerContext channelhandlercontext, Object obj, ByteBuf bytebuf) throws Exception {
		encode(channelhandlercontext, (Packet)obj, bytebuf);
	}
}
