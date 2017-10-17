// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   ConsoleCommand.java

package com.isnowfox.core.io;


// Referenced classes of package com.isnowfox.core.io:
//			ConsoleCommand

static class ConsoleCommand$1 extends Thread {

	final mmand val$cmd;

	public void run() {
		ConsoleCommand.blockStart(val$cmd);
	}

	ConsoleCommand$1(mmand mmand) {
		val$cmd = mmand;
		super();
	}
}
