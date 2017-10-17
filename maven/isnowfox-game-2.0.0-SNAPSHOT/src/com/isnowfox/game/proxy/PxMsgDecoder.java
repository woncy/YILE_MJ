// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   PxMsgDecoder.java

package com.isnowfox.game.proxy;

import com.isnowfox.game.proxy.message.PxMsg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

// Referenced classes of package com.isnowfox.game.proxy:
//			PxMsgFactory

public class PxMsgDecoder extends ByteToMessageDecoder {

	private final PxMsgFactory messageFactory;

	public PxMsgDecoder(PxMsgFactory messageFactory) {
		if (messageFactory == null) {
			throw new NullPointerException("messageFactory");
		} else {
			this.messageFactory = messageFactory;
			return;
		}
	}

	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List out) throws Exception {
		int readerIndex = in.readerIndex();
		int readableLen = in.readableBytes();
		if (readableLen >= 5) {
			int len = in.readInt();
			byte id = in.readByte();
			if (readableLen >= len + 5) {
				PxMsg pm = messageFactory.get(id);
				pm.decode(in, ctx);
				out.add(pm);
				return;
			}
		}
		in.readerIndex(readerIndex);
	}
}
