// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   MessageFactory.java

package com.isnowfox.core.net.message;


// Referenced classes of package com.isnowfox.core.net.message:
//			Message

public interface MessageFactory {

	public abstract Message getMessage(int i, int j) throws Exception;
}
