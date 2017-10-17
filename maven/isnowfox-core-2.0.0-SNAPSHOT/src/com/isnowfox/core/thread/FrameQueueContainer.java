// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   FrameQueueContainer.java

package com.isnowfox.core.thread;


// Referenced classes of package com.isnowfox.core.thread:
//			FrameQueueThread

public abstract class FrameQueueContainer {
	private class InnerFrameQueueThread extends FrameQueueThread {

		final FrameQueueContainer this$0;

		protected void frame(int frameCount, long time, long passedTime) {
			super.frame(frameCount, time, passedTime);
			threadMethod(frameCount, time, passedTime);
		}

		protected void errorHandler(Throwable e) {
			FrameQueueContainer.this.errorHandler(e);
		}

		InnerFrameQueueThread(String name, int frameTimeSpan, int queueMax) {
			this$0 = FrameQueueContainer.this;
			super(name, frameTimeSpan, queueMax);
		}
	}


	protected FrameQueueThread frameThread;

	public FrameQueueContainer(int frameTimeSpan, int queueMax) {
		frameThread = new InnerFrameQueueThread(getClass().getName(), frameTimeSpan, queueMax);
	}

	public void start() {
		frameThread.start();
	}

	protected void run(Runnable runnable) {
		frameThread.add(runnable);
	}

	protected abstract void threadMethod(int i, long l, long l1);

	protected abstract void errorHandler(Throwable throwable);

	public void close() throws InterruptedException {
		frameThread.close();
	}
}
