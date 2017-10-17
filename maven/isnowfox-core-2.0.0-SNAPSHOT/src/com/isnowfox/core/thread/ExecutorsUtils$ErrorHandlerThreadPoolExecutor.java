// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   ExecutorsUtils.java

package com.isnowfox.core.thread;

import java.util.concurrent.*;

// Referenced classes of package com.isnowfox.core.thread:
//			ExecutorsUtils

private static class ExecutorsUtils$ErrorHandlerThreadPoolExecutor extends ThreadPoolExecutor {

	private Thread$UncaughtExceptionHandler errorHandler;

	protected void afterExecute(Runnable r, Throwable t) {
		super.afterExecute(r, t);
		if (t == null && (r instanceof Future)) {
			try {
				Future future = (Future)r;
				if (future.isDone()) {
					future.get();
				}
			}
			catch (CancellationException ce) {
				t = ce;
			}
			catch (ExecutionException ee) {
				t = ee.getCause();
			}
			catch (InterruptedException ie) {
				Thread.currentThread().interrupt();
			}
		}
		if (t != null) {
			errorHandler.uncaughtException(Thread.currentThread(), t);
		}
	}

	public ExecutorsUtils$ErrorHandlerThreadPoolExecutor(adPoolExecutor errorHandler, int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue workQueue, 
			ThreadFactory threadFactory) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
		this.errorHandler = errorHandler;
	}

	public ExecutorsUtils$ErrorHandlerThreadPoolExecutor(Thread$UncaughtExceptionHandler errorHandler, int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue workQueue, 
			RejectedExecutionHandler handler) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
		this.errorHandler = errorHandler;
	}

	public ExecutorsUtils$ErrorHandlerThreadPoolExecutor(Thread$UncaughtExceptionHandler errorHandler, int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue workQueue, 
			ThreadFactory threadFactory, RejectedExecutionHandler handler) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
		this.errorHandler = errorHandler;
	}

	public ExecutorsUtils$ErrorHandlerThreadPoolExecutor(Thread$UncaughtExceptionHandler errorHandler, int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
		this.errorHandler = errorHandler;
	}
}
