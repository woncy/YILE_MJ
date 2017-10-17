// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   AbstractSessionPxMsg.java

package com.isnowfox.game.proxy.message;

import com.isnowfox.core.net.Session;
import io.netty.buffer.ByteBuf;

// Referenced classes of package com.isnowfox.game.proxy.message:
//			PxMsg

public abstract class AbstractSessionPxMsg
	implements PxMsg {

	protected Session session;
	private int id;

	public AbstractSessionPxMsg(int id) {
		this.id = id;
	}

	protected void writeString(ByteBuf out, String value) throws Exception {
		if (value == null) {
			out.writeInt(-1);
		} else {
			byte bytes[] = value.getBytes("utf8");
			out.writeInt(bytes.length);
			out.writeBytes(bytes);
		}
	}

	public String readString(ByteBuf in) throws Exception {
		int len = in.readInt();
		if (len == -1) {
			return null;
		} else {
			byte bytes[] = new byte[len];
			in.readBytes(bytes);
			return new String(bytes, "utf8");
		}
	}

	public final Session getSession() {
		return session;
	}

	public final void setSession(Session session) {
		this.session = session;
	}

	public int getType() {
		return id;
	}
}
