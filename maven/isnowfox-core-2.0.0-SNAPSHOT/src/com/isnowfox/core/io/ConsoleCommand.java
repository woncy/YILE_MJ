// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   ConsoleCommand.java

package com.isnowfox.core.io;

import java.util.Arrays;
import java.util.Scanner;
import org.apache.commons.lang.ArrayUtils;

public class ConsoleCommand {
	public static interface Command {

		public transient abstract boolean execute(String s, String as[]) throws Exception;

		public abstract boolean onError(Throwable throwable);
	}


	public ConsoleCommand() {
	}

	public static final void blockStart(Command cmd) {
		Scanner in;
		Throwable throwable;
		in = new Scanner(System.in);
		throwable = null;
_L2:
		boolean result;
		String line;
		if (null == (line = in.nextLine())) {
			break; /* Loop/switch isn't completed */
		}
		line = line.trim();
		if (line.isEmpty()) {
			continue; /* Loop/switch isn't completed */
		}
		String array[] = line.split("[\\s]+");
		String name = array[0].trim();
		if (array.length > 1) {
			array = (String[])Arrays.copyOfRange(array, 1, array.length);
		} else {
			array = ArrayUtils.EMPTY_STRING_ARRAY;
		}
		try {
			result = cmd.execute(name, array);
		}
		catch (Throwable th) {
			result = cmd.onError(th);
		}
		if (result) {
			if (in != null) {
				if (throwable != null) {
					try {
						in.close();
					}
					catch (Throwable throwable3) {
						throwable.addSuppressed(throwable3);
					}
				} else {
					in.close();
				}
			}
			return;
		}
		if (true) goto _L2; else goto _L1
_L1:
		if (in != null) {
			if (throwable != null) {
				try {
					in.close();
				}
				catch (Throwable throwable1) {
					throwable.addSuppressed(throwable1);
				}
			} else {
				in.close();
			}
		}
		break MISSING_BLOCK_LABEL_218;
		Throwable throwable2;
		throwable2;
		throwable = throwable2;
		throw throwable2;
		Exception exception;
		exception;
		if (in != null) {
			if (throwable != null) {
				try {
					in.close();
				}
				catch (Throwable throwable4) {
					throwable.addSuppressed(throwable4);
				}
			} else {
				in.close();
			}
		}
		throw exception;
	}

	public static final void start(Command cmd) {
		(new Thread(cmd) {

			final Command val$cmd;

			public void run() {
				ConsoleCommand.blockStart(cmd);
			}

			 {
				cmd = command;
				super();
			}
		}).start();
	}
}
