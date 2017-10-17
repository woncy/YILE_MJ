// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   KeyInfo.java

package com.isnowfox.el;


// Referenced classes of package com.isnowfox.el:
//			KeyInfo

static final class KeyInfo$Type extends Enum {

	public static final KeyInfo$Type FIELD;
	public static final KeyInfo$Type PROPERTY;
	public static final KeyInfo$Type METHOD;
	private static final KeyInfo$Type $VALUES[];

	public static KeyInfo$Type[] values() {
		return (KeyInfo$Type[])$VALUES.clone();
	}

	public static KeyInfo$Type valueOf(String name) {
		return (KeyInfo$Type)Enum.valueOf(com/isnowfox/el/KeyInfo$Type, name);
	}

	static  {
		FIELD = new KeyInfo$Type("FIELD", 0);
		PROPERTY = new KeyInfo$Type("PROPERTY", 1);
		METHOD = new KeyInfo$Type("METHOD", 2);
		$VALUES = (new KeyInfo$Type[] {
			FIELD, PROPERTY, METHOD
		});
	}

	private KeyInfo$Type(String s, int i) {
		super(s, i);
	}
}
