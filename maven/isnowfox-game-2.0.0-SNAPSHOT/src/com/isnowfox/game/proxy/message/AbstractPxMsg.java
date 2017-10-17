// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   AbstractPxMsg.java

package com.isnowfox.game.proxy.message;

import com.isnowfox.core.net.Session;
import com.isnowfox.util.ArrayExpandUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCounted;

// Referenced classes of package com.isnowfox.game.proxy.message:
//			PxMsg

public abstract class AbstractPxMsg
	implements PxMsg, ReferenceCounted {

	protected ByteBuf buf;
	protected int bufOffset;
	protected int bufLen;
	private Session session;

	public AbstractPxMsg() {
	}

	public void encode(ByteBuf out) throws Exception {
		out.writeInt(bufLen);
		out.writeBytes(buf, bufOffset, bufLen);
		encodeData(out);
	}

	protected abstract void encodeData(ByteBuf bytebuf) throws Exception;

	public void decode(ByteBuf in, ChannelHandlerContext ctx) throws Exception {
		bufLen = in.readInt();
		buf = ctx.alloc().buffer(bufLen);
		bufOffset = 0;
		in.readBytes(buf, bufLen);
		try {
			decodeData(in);
		}
		catch (Exception ex) {
			throw ex;
		}
	}

	protected abstract void decodeData(ByteBuf bytebuf) throws Exception;

	public abstract int getType();

	public final Session getSession() {
		return session;
	}

	public final void setSession(Session session) {
		this.session = session;
	}

	public final ByteBuf getBuf() {
		return buf;
	}

	public final int getBufOffset() {
		return bufOffset;
	}

	public final int getBufLen() {
		return bufLen;
	}

	public String toString() {
		if (buf.hasArray()) {
			return (new StringBuilder()).append("PxMsg [buf=").append(ArrayExpandUtils.toString(buf.array(), bufOffset, 16)).append(", bufOffset=").append(bufOffset).append(", bufLen=").append(bufLen).append(", session=").append(session).append("]").toString();
		} else {
			int len = Math.min(bufLen, 16);
			byte array[] = new byte[len];
			buf.getBytes(bufOffset, array, 0, len);
			return (new StringBuilder()).append("PxMsg [buf=").append(ArrayExpandUtils.toString(array, len)).append(", bufOffset=").append(bufOffset).append(", bufLen=").append(bufLen).append(", session=").append(session).append("]").toString();
		}
	}

	public int refCnt() {
		return buf.refCnt();
	}

	public ReferenceCounted retain() {
		return buf.retain();
	}

	public ReferenceCounted retain(int increment) {
		return buf.retain();
	}

	public boolean release() {
		return buf.release();
	}

	public boolean release(int decrement) {
		return buf.release(decrement);
	}

	public ReferenceCounted touch() {
		buf.touch();
		return this;
	}

	public ReferenceCounted touch(Object hint) {
		buf.touch(hint);
		return this;
	}
}
