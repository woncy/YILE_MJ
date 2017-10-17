// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   User.java

package com.isnowfox.game.platform;

import io.netty.channel.Channel;

public class User {

	private short id;
	private Channel channel;
	private boolean isLogin;
	private boolean checkConnect;

	public User() {
		checkConnect = false;
	}

	public User(short id, Channel channel) {
		checkConnect = false;
		this.id = id;
		this.channel = channel;
	}

	public final short getId() {
		return id;
	}

	public final void setId(short id) {
		this.id = id;
	}

	public void writeAndFlush(Object obj) {
		channel.writeAndFlush(obj);
	}

	public final Channel getChannel() {
		return channel;
	}

	public final void setChannel(Channel channel) {
		this.channel = channel;
	}

	public boolean isCheckConnect() {
		return checkConnect;
	}

	public void setCheckConnect(boolean checkConnect) {
		this.checkConnect = checkConnect;
	}

	public String toString() {
		return (new StringBuilder()).append("User{id=").append(id).append(", channel=").append(channel).append(", isLogin=").append(isLogin).append(", checkConnect=").append(checkConnect).append('}').toString();
	}
}
