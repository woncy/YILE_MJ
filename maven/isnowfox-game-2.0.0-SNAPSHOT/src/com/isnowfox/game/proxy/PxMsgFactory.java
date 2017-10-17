// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   PxMsgFactory.java

package com.isnowfox.game.proxy;

import com.isnowfox.game.proxy.message.AllPxMsg;
import com.isnowfox.game.proxy.message.LogoutPxMsg;
import com.isnowfox.game.proxy.message.PxMsg;
import com.isnowfox.game.proxy.message.RangePxMsg;
import com.isnowfox.game.proxy.message.SinglePxMsg;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import net.sf.cglib.core.Constants;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastConstructor;

public class PxMsgFactory {

	private FastConstructor array[];
	private TreeMap tempMap;

	public PxMsgFactory() {
		tempMap = new TreeMap();
		init();
		fixed();
	}

	protected void init() {
		add(0, com/isnowfox/game/proxy/message/SinglePxMsg);
		add(1, com/isnowfox/game/proxy/message/RangePxMsg);
		add(2, com/isnowfox/game/proxy/message/AllPxMsg);
		add(3, com/isnowfox/game/proxy/message/LogoutPxMsg);
	}

	protected void add(int id, Class cls) {
		if (tempMap.put(Integer.valueOf(id), cls) != null) {
			throw new RuntimeException((new StringBuilder()).append("严重错误,重复的消息类,cls:").append(cls).append(",id:").append(id).toString());
		} else {
			return;
		}
	}

	protected void fixed() {
		array = new FastConstructor[((Integer)tempMap.lastKey()).intValue() + 1];
		for (Iterator iterator = tempMap.entrySet().iterator(); iterator.hasNext();) {
			java.util.Map.Entry e = (java.util.Map.Entry)iterator.next();
			array[((Integer)e.getKey()).intValue()] = FastClass.create((Class)e.getValue()).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		}

		tempMap = null;
	}

	public PxMsg get(int id) throws InvocationTargetException {
		return (PxMsg)array[id].newInstance();
	}
}
