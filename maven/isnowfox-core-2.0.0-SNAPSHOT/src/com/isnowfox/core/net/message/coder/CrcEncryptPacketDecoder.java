// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   CrcEncryptPacketDecoder.java

package com.isnowfox.core.net.message.coder;

import com.isnowfox.core.net.*;
import com.isnowfox.core.net.message.MessageProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.Attribute;
import java.util.List;

// Referenced classes of package com.isnowfox.core.net.message.coder:
//			CrcEncryptCoder

public class CrcEncryptPacketDecoder extends ByteToMessageDecoder {

	private final NetPacketHandler handler;

	public CrcEncryptPacketDecoder(NetPacketHandler handler) {
		this.handler = handler;
	}

	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List out) throws Exception {
		Session session = (Session)ctx.channel().attr(ChannelHandler.SESSION).get();
		if (handler.onIn(session, in)) {
			int readerIndex = in.readerIndex();
			int readableLen = in.readableBytes();
			if (readableLen >= 3) {
				int len = in.readUnsignedMedium();
				if (readableLen >= len + 3) {
					CrcEncryptCoder coder = (CrcEncryptCoder)ctx.channel().attr(CrcEncryptChannelHandler.CRC_ENCRYPT).get();
					com.isnowfox.core.net.message.Packet p = coder.read(len, in);
					out.add(p);
					return;
				}
			}
			in.readerIndex(readerIndex);
		}
	}
}
