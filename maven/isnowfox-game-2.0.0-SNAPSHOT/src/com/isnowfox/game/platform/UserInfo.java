// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   UserInfo.java

package com.isnowfox.game.platform;


public class UserInfo {

	private String uuid;
	private int yellow;
	private boolean year;
	private Object param;

	public UserInfo() {
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getYellow() {
		return yellow;
	}

	public void setYellow(int yellow) {
		this.yellow = yellow;
	}

	public boolean isYear() {
		return year;
	}

	public void setYear(boolean year) {
		this.year = year;
	}

	public Object getParam() {
		return param;
	}

	public void setParam(Object param) {
		this.param = param;
	}

	public String toString() {
		return (new StringBuilder()).append("UserInfo{uuid='").append(uuid).append('\'').append(", yellow=").append(yellow).append(", year=").append(year).append(", param=").append(param).append('}').toString();
	}
}
