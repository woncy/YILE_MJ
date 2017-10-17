// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   FrameThread.java

package com.isnowfox.core.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class FrameThread extends Thread {

	private static final Logger log = LoggerFactory.getLogger(com/isnowfox/core/thread/FrameThread);
	private final int frameTimeSpan;
	private volatile boolean isRun;

	public FrameThread(String name, int frameTimeSpan) {
		super(name);
		isRun = true;
		this.frameTimeSpan = frameTimeSpan;
	}

	public void run() {
		threadMethod();
	}

	private void threadMethod() {
		long frameTime = System.currentTimeMillis();
		int frameCount = 0;
		do {
			if (!isRun) {
				break;
			}
			try {
				frameCount++;
				long time = System.currentTimeMillis();
				long passedTime = time - frameTime;
				frame(frameCount, time, passedTime);
				long span = (long)frameTimeSpan - (System.currentTimeMillis() - time);
				if (span > 0L) {
					Thread.sleep(span);
				} else {
					log.error("Ö¡ÑÓ³Ù:{} --- {}", Long.valueOf(span), getName());
				}
				frameTime = time;
				continue;
			}
			catch (InterruptedException e) {
				break;
			}
			catch (Throwable e) {
				errorHandler(e);
			}
		} while (true);
	}

	protected abstract void frame(int i, long l, long l1);

	protected abstract void errorHandler(Throwable throwable);

	public void close() throws InterruptedException {
		isRun = false;
		interrupt();
		join();
	}

}
