// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   SinglePxMsg.java

package com.isnowfox.game.proxy.message;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.MarkCompressInput;
import com.isnowfox.core.net.message.Message;
import com.isnowfox.core.net.message.MessageFactory;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;

// Referenced classes of package com.isnowfox.game.proxy.message:
//			AbstractPxMsg, AbstractEncodeWrapper

public class SinglePxMsg extends AbstractPxMsg {
	public static class EncodeWrapper extends AbstractEncodeWrapper {

		private short sessionId;

		public int getType() {
			return 0;
		}

		protected void encodeData(ByteBuf out) throws Exception {
			out.writeShort(sessionId);
		}

		public String toString() {
			return (new StringBuilder()).append("SinglePxMsg.Wrapper [sessionId=").append(sessionId).append("]").append(super.toString()).toString();
		}

		public EncodeWrapper(short sessionId, Message msg) {
			this.sessionId = sessionId;
			this.msg = msg;
		}
	}


	public static final int ID = 0;
	private short sessionId;

	public SinglePxMsg() {
	}

	public SinglePxMsg(short sessionId, ByteBuf buf) {
		this.sessionId = sessionId;
		this.buf = buf;
		bufOffset = 0;
		bufLen = buf.capacity();
	}

	public final Message toMessage(MessageFactory messageFactory) throws Exception {
		buf.readerIndex(bufOffset);
		ByteBufInputStream bin = new ByteBufInputStream(buf, bufLen);
		Input in = MarkCompressInput.create(bin);
		int type = in.readInt();
		int id = in.readInt();
		Message rawMsg = messageFactory.getMessage(type, id);
		rawMsg.decode(in);
		return rawMsg;
	}

	protected void encodeData(ByteBuf out) throws Exception {
		out.writeShort(sessionId);
	}

	protected void decodeData(ByteBuf in) throws Exception {
		sessionId = in.readShort();
	}

	public int getType() {
		return 0;
	}

	public short getSessionId() {
		return sessionId;
	}

	public String toString() {
		return (new StringBuilder()).append("SinglePxMsg [sessionId=").append(sessionId).append("]").append(super.toString()).toString();
	}
}
