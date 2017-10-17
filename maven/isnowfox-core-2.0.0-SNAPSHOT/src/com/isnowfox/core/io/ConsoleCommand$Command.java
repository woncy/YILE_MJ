// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   ConsoleCommand.java

package com.isnowfox.core.io;


// Referenced classes of package com.isnowfox.core.io:
//			ConsoleCommand

public static interface ConsoleCommand$Command {

	public transient abstract boolean execute(String s, String as[]) throws Exception;

	public abstract boolean onError(Throwable throwable);
}
