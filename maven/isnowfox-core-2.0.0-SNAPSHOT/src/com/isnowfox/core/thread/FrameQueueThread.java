// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   FrameQueueThread.java

package com.isnowfox.core.thread;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

// Referenced classes of package com.isnowfox.core.thread:
//			FrameThread

public abstract class FrameQueueThread extends FrameThread {

	private final ArrayBlockingQueue runQueue;
	private final ArrayList swapList = new ArrayList();

	public FrameQueueThread(String name, int frameTimeSpan, int queueMax) {
		super(name, frameTimeSpan);
		runQueue = new ArrayBlockingQueue(queueMax);
	}

	protected void frame(int frameCount, long time, long passedTime) {
		threadRunQueue();
	}

	private void threadRunQueue() {
		ArrayList swapList = this.swapList;
		swapList.clear();
		int len = runQueue.drainTo(swapList);
		if (len > 0) {
			for (int i = 0; i < len; i++) {
				try {
					((Runnable)swapList.get(i)).run();
				}
				catch (Throwable th) {
					errorHandler(th);
				}
			}

		}
	}

	public final boolean add(Runnable run) {
		return runQueue.add(run);
	}

	public void close() throws InterruptedException {
		super.close();
		threadRunQueue();
	}
}
