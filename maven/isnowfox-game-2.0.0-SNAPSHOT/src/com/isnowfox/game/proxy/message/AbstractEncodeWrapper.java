// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   AbstractEncodeWrapper.java

package com.isnowfox.game.proxy.message;

import com.isnowfox.core.io.MarkCompressOutput;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.net.Session;
import com.isnowfox.core.net.message.Message;
import com.isnowfox.core.net.message.MessageException;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

// Referenced classes of package com.isnowfox.game.proxy.message:
//			PxMsg

public abstract class AbstractEncodeWrapper
	implements PxMsg {

	private static final int HEAD_LENGTH = 4;
	private Session session;
	protected Message msg;

	public AbstractEncodeWrapper() {
	}

	public abstract int getType();

	public void encode(ByteBuf out) throws Exception {
		int startIdx = out.writerIndex();
		out.writeInt(0);
		Output o = MarkCompressOutput.create(out);
		o.writeInt(msg.getMessageType());
		o.writeInt(msg.getMessageId());
		msg.encode(o);
		int endIdx = out.writerIndex();
		int len = endIdx - startIdx - 4;
		out.setInt(startIdx, len);
		encodeData(out);
	}

	protected abstract void encodeData(ByteBuf bytebuf) throws Exception;

	public void decode(ByteBuf in, ChannelHandlerContext ctx) throws Exception {
		throw new MessageException("包装不实现解码！");
	}

	public final Session getSession() {
		return session;
	}

	public final void setSession(Session session) {
		this.session = session;
	}

	public String toString() {
		return (new StringBuilder()).append("AbstractWrapper [session=").append(session).append(", msg=").append(msg).append("]").toString();
	}
}
