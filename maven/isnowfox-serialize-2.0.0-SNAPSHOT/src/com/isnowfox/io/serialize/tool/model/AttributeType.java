// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   AttributeType.java

package com.isnowfox.io.serialize.tool.model;


public final class AttributeType extends Enum {

	public static final AttributeType INT;
	public static final AttributeType LONG;
	public static final AttributeType FLOAT;
	public static final AttributeType DOUBLE;
	public static final AttributeType BOOLEAN;
	public static final AttributeType STRING;
	public static final AttributeType BYTES;
	public static final AttributeType BYTE_BUF;
	public static final AttributeType OTHER;
	private static final AttributeType $VALUES[];

	public static AttributeType[] values() {
		return (AttributeType[])$VALUES.clone();
	}

	public static AttributeType valueOf(String name) {
		return (AttributeType)Enum.valueOf(com/isnowfox/io/serialize/tool/model/AttributeType, name);
	}

	private AttributeType(String s, int i) {
		super(s, i);
	}

	public static AttributeType to(String name) {
		String s = name;
		byte byte0 = -1;
		switch (s.hashCode()) {
		case 104431: 
			if (s.equals("int")) {
				byte0 = 0;
			}
			break;

		case 3327612: 
			if (s.equals("long")) {
				byte0 = 1;
			}
			break;

		case 97526364: 
			if (s.equals("float")) {
				byte0 = 2;
			}
			break;

		case -1325958191: 
			if (s.equals("double")) {
				byte0 = 3;
			}
			break;

		case 64711720: 
			if (s.equals("boolean")) {
				byte0 = 4;
			}
			break;

		case -1808118735: 
			if (s.equals("String")) {
				byte0 = 5;
			}
			break;

		case 3039496: 
			if (s.equals("byte")) {
				byte0 = 6;
			}
			break;

		case 2020032555: 
			if (s.equals("ByteBuf")) {
				byte0 = 7;
			}
			break;
		}
		switch (byte0) {
		case 0: // '\0'
			return INT;

		case 1: // '\001'
			return LONG;

		case 2: // '\002'
			return FLOAT;

		case 3: // '\003'
			return DOUBLE;

		case 4: // '\004'
			return BOOLEAN;

		case 5: // '\005'
			return STRING;

		case 6: // '\006'
			return BYTES;

		case 7: // '\007'
			return BYTE_BUF;
		}
		return OTHER;
	}

	static  {
		INT = new AttributeType("INT", 0);
		LONG = new AttributeType("LONG", 1);
		FLOAT = new AttributeType("FLOAT", 2);
		DOUBLE = new AttributeType("DOUBLE", 3);
		BOOLEAN = new AttributeType("BOOLEAN", 4);
		STRING = new AttributeType("STRING", 5);
		BYTES = new AttributeType("BYTES", 6);
		BYTE_BUF = new AttributeType("BYTE_BUF", 7);
		OTHER = new AttributeType("OTHER", 8);
		$VALUES = (new AttributeType[] {
			INT, LONG, FLOAT, DOUBLE, BOOLEAN, STRING, BYTES, BYTE_BUF, OTHER
		});
	}
}
