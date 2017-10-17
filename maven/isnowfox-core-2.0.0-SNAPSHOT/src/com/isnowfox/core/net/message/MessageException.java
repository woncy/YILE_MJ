// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   MessageException.java

package com.isnowfox.core.net.message;


// Referenced classes of package com.isnowfox.core.net.message:
//			MessageProtocol

public class MessageException extends Exception {

	private static final long serialVersionUID = 0x16177e92b08f7172L;

	public MessageException() {
	}

	public MessageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MessageException(String message, Throwable cause) {
		super(message, cause);
	}

	public MessageException(String message) {
		super(message);
	}

	public MessageException(Throwable cause) {
		super(cause);
	}

	public static final MessageException newLengthException(int length) {
		return new MessageException((new StringBuilder()).append("to length :").append(length).append(",max:").append(0xffffff).toString());
	}

	public static final MessageException newTypeValueRangeException(int type) {
		return new MessageException((new StringBuilder()).append("type value :").append(type).append(",max:").append(237).toString());
	}

	public static final MessageException newIdValueRangeException(int id) {
		return new MessageException((new StringBuilder()).append("id value :").append(id).append(",max:").append(237).toString());
	}

	public static final MessageException newIdDuplicateRangeException(int id, Class c0, Class c1) {
		return new MessageException((new StringBuilder()).append("id value :").append(id).append(",duplicate:").append(id).append(",cls:").append(c0).append(",cls:").append(c1).toString());
	}

	public static final MessageException newFactoryDuplicateExpandException() {
		return new MessageException("duplicate expand");
	}
}
