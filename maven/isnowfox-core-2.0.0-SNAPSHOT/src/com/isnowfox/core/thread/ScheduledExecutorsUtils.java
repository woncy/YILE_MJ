// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   ScheduledExecutorsUtils.java

package com.isnowfox.core.thread;

import java.util.concurrent.*;

public class ScheduledExecutorsUtils {
	private static class ErrorHandlerThreadPoolExecutor extends ScheduledThreadPoolExecutor {

		private Thread.UncaughtExceptionHandler errorHandler;

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

		public ErrorHandlerThreadPoolExecutor(Thread.UncaughtExceptionHandler errorHandler, int corePoolSize) {
			super(corePoolSize);
			this.errorHandler = errorHandler;
		}

		public ErrorHandlerThreadPoolExecutor(Thread.UncaughtExceptionHandler errorHandler, int corePoolSize, ThreadFactory threadFactory) {
			super(corePoolSize, threadFactory);
			this.errorHandler = errorHandler;
		}

		public ErrorHandlerThreadPoolExecutor(Thread.UncaughtExceptionHandler errorHandler, int corePoolSize, RejectedExecutionHandler handler) {
			super(corePoolSize, handler);
			this.errorHandler = errorHandler;
		}

		public ErrorHandlerThreadPoolExecutor(Thread.UncaughtExceptionHandler errorHandler, int corePoolSize, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
			super(corePoolSize, threadFactory, handler);
			this.errorHandler = errorHandler;
		}
	}


	public ScheduledExecutorsUtils() {
	}

	public static final ErrorHandlerThreadPoolExecutor newSingleThreadExecutor(Thread.UncaughtExceptionHandler errorHandler, ThreadFactory threadFactory) {
		return new ErrorHandlerThreadPoolExecutor(errorHandler, 1, threadFactory);
	}

	public static ErrorHandlerThreadPoolExecutor newSingleThreadExecutor(Thread.UncaughtExceptionHandler errorHandler) {
		return new ErrorHandlerThreadPoolExecutor(errorHandler, 1);
	}

	public static ScheduledThreadPoolExecutor newFixedThreadPool(int nThreads, Thread.UncaughtExceptionHandler errorHandler) {
		return new ErrorHandlerThreadPoolExecutor(errorHandler, nThreads);
	}

	public static ScheduledThreadPoolExecutor newFixedThreadPool(int nThreads, Thread.UncaughtExceptionHandler errorHandler, ThreadFactory threadFactory) {
		return new ErrorHandlerThreadPoolExecutor(errorHandler, nThreads, threadFactory);
	}
}
