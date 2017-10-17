// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   UniqueInfo.java

package org.forkjoin.core.dao;

import com.google.common.base.Function;
import java.util.Arrays;

public class UniqueInfo {

	private String name;
	private String fields[];
	private Function getFunction;

	public UniqueInfo() {
	}

	public transient UniqueInfo(String name, String fields[]) {
		this.name = name;
		this.fields = fields;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getFields() {
		return fields;
	}

	public void setFields(String fields[]) {
		this.fields = fields;
	}

	public String toString() {
		return (new StringBuilder()).append("UniqueInfo{name='").append(name).append('\'').append(", fields=").append(Arrays.toString(fields)).append('}').toString();
	}
}
