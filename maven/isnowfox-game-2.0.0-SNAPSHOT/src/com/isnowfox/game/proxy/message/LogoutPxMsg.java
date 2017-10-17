// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   LogoutPxMsg.java

package com.isnowfox.game.proxy.message;

import com.isnowfox.core.net.Session;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

// Referenced classes of package com.isnowfox.game.proxy.message:
//			PxMsg

public class LogoutPxMsg
	implements PxMsg {

	public static final int ID = 3;
	private short sessionId;
	private Session session;

	public LogoutPxMsg() {
	}

	public LogoutPxMsg(short sessionId) {
		this.sessionId = sessionId;
	}

	public short getSessionId() {
		return sessionId;
	}

	public void setSessionId(short sessionId) {
		this.sessionId = sessionId;
	}

	public LogoutPxMsg(short sessionId, Session session) {
		this.sessionId = sessionId;
		this.session = session;
	}

	public void encode(ByteBuf out) throws Exception {
		out.writeShort(sessionId);
	}

	public void decode(ByteBuf in, ChannelHandlerContext ctx) throws Exception {
		sessionId = in.readShort();
	}

	public int getType() {
		return 3;
	}

	public final Session getSession() {
		return session;
	}

	public final void setSession(Session session) {
		this.session = session;
	}

	public String toString() {
		return (new StringBuilder()).append("LogoutPxMsg [sessionId=").append(sessionId).append(", session=").append(session).append("]").toString();
	}
}
