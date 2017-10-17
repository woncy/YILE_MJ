// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   RangePxMsg.java

package com.isnowfox.game.proxy.message;

import com.isnowfox.core.net.message.Message;
import com.isnowfox.util.collect.primitive.ShortList;
import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Referenced classes of package com.isnowfox.game.proxy.message:
//			AbstractPxMsg, AbstractEncodeWrapper

public class RangePxMsg extends AbstractPxMsg {
	public static class EncodeWrapper extends AbstractEncodeWrapper {

		private final short userList[];

		public int getType() {
			return 1;
		}

		protected void encodeData(ByteBuf out) throws Exception {
			out.writeShort(userList.length);
			short aword0[] = userList;
			int i = aword0.length;
			for (int j = 0; j < i; j++) {
				Short userId = Short.valueOf(aword0[j]);
				out.writeShort(userId.shortValue());
			}

		}

		public String toString() {
			return (new StringBuilder()).append("RangePxMsg.Wrapper [userList=").append(userList).append("]").append(super.toString()).toString();
		}

		public EncodeWrapper(Message msg, short userList[]) {
			this.msg = msg;
			this.userList = userList;
		}
	}


	private static final Logger log = LoggerFactory.getLogger(com/isnowfox/game/proxy/message/RangePxMsg);
	private static final int INIT_SIZE = 64;
	public static final int ID = 1;
	private final ShortList userList;

	public RangePxMsg() {
		this(64);
	}

	public RangePxMsg(int initialCapacity) {
		userList = new ShortList(initialCapacity);
	}

	protected void encodeData(ByteBuf out) throws Exception {
		out.writeShort(userList.size());
		for (int i = 0; i < userList.size(); i++) {
			out.writeShort(userList.get(i));
		}

	}

	protected void decodeData(ByteBuf in) throws Exception {
		int len = in.readShort();
		try {
			for (int i = 0; i < len; i++) {
				userList.add(in.readShort());
			}

		}
		catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw ex;
		}
	}

	public final ShortList getUserList() {
		return userList;
	}

	public final void add(short userId) {
		userList.add(userId);
	}

	public final transient void add(short array[]) {
		for (int i = 0; i < array.length; i++) {
			userList.add(array[i]);
		}

	}

	public int getType() {
		return 1;
	}

	public String toString() {
		return (new StringBuilder()).append("RangePxMsg [userList=").append(userList).append("]").append(super.toString()).toString();
	}

}
