// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   Packet.java

package com.isnowfox.core.net.message;

import com.isnowfox.core.net.Session;
import com.isnowfox.util.ArrayExpandUtils;
import io.netty.buffer.ByteBuf;
import io.netty.util.ReferenceCounted;

public final class Packet
	implements ReferenceCounted {

	private int length;
	private byte type;
	private ByteBuf buf;
	protected int bufOffset;
	private Session session;

	public Packet() {
	}

	public Packet(int length, byte type, ByteBuf buf) {
		this.length = length;
		this.type = type;
		this.buf = buf;
	}

	public Packet(int length, byte type, ByteBuf buf, int bufOffset) {
		this.length = length;
		this.type = type;
		this.buf = buf;
		this.bufOffset = bufOffset;
	}

	public final int getLength() {
		return length;
	}

	public final void setLength(int length) {
		this.length = length;
	}

	public final byte getType() {
		return type;
	}

	public final void setType(byte type) {
		this.type = type;
	}

	public final ByteBuf getBuf() {
		return buf;
	}

	public final void setBuf(ByteBuf buf) {
		this.buf = buf;
	}

	public final Session getSession() {
		return session;
	}

	public final void setSession(Session session) {
		this.session = session;
	}

	public final int getBufOffset() {
		return bufOffset;
	}

	public final void setBufOffset(int bufOffset) {
		this.bufOffset = bufOffset;
	}

	public String toString() {
		if (buf.hasArray()) {
			return (new StringBuilder()).append("Packet [length=").append(length).append(", type=").append(type).append(", buf=").append(ArrayExpandUtils.toString(buf.array(), bufOffset, 16)).append(", bufOffset=").append(bufOffset).append(", session=").append(session).append("]").toString();
		} else {
			int len = Math.min(length, 16);
			byte array[] = new byte[len];
			buf.getBytes(bufOffset, array, 0, len);
			return (new StringBuilder()).append("Packet [length=").append(length).append(", type=").append(type).append(", buf=").append(ArrayExpandUtils.toString(array)).append(", bufOffset=").append(bufOffset).append(", session=").append(session).append("]").toString();
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

	public ReferenceCounted touch() {
		buf.touch();
		return this;
	}

	public ReferenceCounted touch(Object hint) {
		buf.touch(hint);
		return this;
	}

	public boolean release() {
		return buf.release();
	}

	public boolean release(int decrement) {
		return buf.release(decrement);
	}
}
