// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   KeyInfo.java

package com.isnowfox.el;


class KeyInfo {
	static final class Type extends Enum {

		public static final Type FIELD;
		public static final Type PROPERTY;
		public static final Type METHOD;
		private static final Type $VALUES[];

		public static Type[] values() {
			return (Type[])$VALUES.clone();
		}

		public static Type valueOf(String name) {
			return (Type)Enum.valueOf(com/isnowfox/el/KeyInfo$Type, name);
		}

		static  {
			FIELD = new Type("FIELD", 0);
			PROPERTY = new Type("PROPERTY", 1);
			METHOD = new Type("METHOD", 2);
			$VALUES = (new Type[] {
				FIELD, PROPERTY, METHOD
			});
		}

		private Type(String s, int i) {
			super(s, i);
		}
	}


	private Type type;
	private Class cls;
	private String methodName;

	public KeyInfo(Type type, Class cls) {
		this.type = type;
		this.cls = cls;
	}

	public KeyInfo(Type type, Class cls, String methodName) {
		this.type = type;
		this.cls = cls;
		this.methodName = methodName;
	}

	Class getCls() {
		return cls;
	}

	void setCls(Class cls) {
		this.cls = cls;
	}

	Type getType() {
		return type;
	}

	void setType(Type type) {
		this.type = type;
	}

	void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	String getMethodName() {
		return methodName;
	}

	public String toString() {
		return (new StringBuilder()).append("KeyInfo [type=").append(type).append(", cls=").append(cls).append(", methodName=").append(methodName).append("]").toString();
	}
}
