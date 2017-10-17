// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   CrcEncryptChannelHandler.java

package com.isnowfox.core.net;

import com.isnowfox.core.net.message.coder.CrcEncryptCoder;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

// Referenced classes of package com.isnowfox.core.net:
//			PacketChannelHandler, NetPacketHandler

public class CrcEncryptChannelHandler extends PacketChannelHandler {

	public static final AttributeKey CRC_ENCRYPT = AttributeKey.valueOf("CrcEncryptChannelHandler.CRC_ENCRYPT");
	private final NetPacketHandler messageHandler;
	private final int zipSize;
	private final int encryptValue;

	public CrcEncryptChannelHandler(NetPacketHandler messageHandler, int zipSize, int encryptValue) {
		super(messageHandler);
		this.messageHandler = messageHandler;
		this.zipSize = zipSize;
		this.encryptValue = encryptValue;
	}

	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		CrcEncryptCoder encryptCoder = new CrcEncryptCoder(zipSize, encryptValue);
		CrcEncryptCoder crcEncryptCoder = (CrcEncryptCoder)ctx.channel().attr(CRC_ENCRYPT).get();
		if (crcEncryptCoder != null) {
			throw new Exception("怎么通道已经被绑定了？");
		} else {
			ctx.channel().attr(CRC_ENCRYPT).set(encryptCoder);
			super.channelActive(ctx);
			return;
		}
	}

	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		ctx.channel().attr(CRC_ENCRYPT).remove();
		super.channelInactive(ctx);
	}

}
