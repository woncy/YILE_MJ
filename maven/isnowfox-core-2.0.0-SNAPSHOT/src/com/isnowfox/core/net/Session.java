// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   Session.java

package com.isnowfox.core.net;

import io.netty.channel.Channel;

public class Session {

	public final Channel channel;
	private Object value;
	private Object info;

	public Session(Channel channel) {
		this.channel = channel;
	}

	public Object get() {
		return value;
	}

	public void set(Object value) {
		this.value = value;
	}

	public Object getInfo() {
		return info;
	}

	public void setInfo(Object info) {
		this.info = info;
	}

	public String toString() {
		return (new StringBuilder()).append("Session{channel=").append(channel).append(", value=").append(value).append(", info=").append(info).append('}').toString();
	}
}
