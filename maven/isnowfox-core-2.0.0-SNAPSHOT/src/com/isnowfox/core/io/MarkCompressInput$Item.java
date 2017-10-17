// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   MarkCompressInput.java

package com.isnowfox.core.io;


// Referenced classes of package com.isnowfox.core.io:
//			MarkCompressInput

static class MarkCompressInput$Item {

	private String type;
	private Object value;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String toString() {
		return (new StringBuilder()).append("[type=").append(type).append(", value=").append(value).append("]").toString();
	}

	public MarkCompressInput$Item() {
	}

	public MarkCompressInput$Item(String type, Object value) {
		this.type = type;
		this.value = value;
	}
}
