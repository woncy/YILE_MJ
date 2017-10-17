// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   AbstractOutput.java

package com.isnowfox.core.io;

import java.io.IOException;
import java.io.OutputStream;

// Referenced classes of package com.isnowfox.core.io:
//			Output

public abstract class AbstractOutput
	implements Output {

	protected final OutputStream out;
	protected final String charset;

	public AbstractOutput(OutputStream out, String charset) {
		this.out = out;
		this.charset = charset;
	}

	public OutputStream getOutputStream() {
		return out;
	}

	public void writeBooleanArray(boolean bs[]) throws IOException {
		if (bs == null) {
			writeInt(-1);
			return;
		}
		writeInt(bs.length);
		boolean aflag[] = bs;
		int i = aflag.length;
		for (int j = 0; j < i; j++) {
			boolean b = aflag[j];
			writeBoolean(b);
		}

	}

	public void writeIntArray(int bs[]) throws IOException {
		if (bs == null) {
			writeInt(-1);
			return;
		}
		writeInt(bs.length);
		int ai[] = bs;
		int i = ai.length;
		for (int j = 0; j < i; j++) {
			int b = ai[j];
			writeInt(b);
		}

	}

	public void writeLongArray(long bs[]) throws IOException {
		if (bs == null) {
			writeInt(-1);
			return;
		}
		writeInt(bs.length);
		long al[] = bs;
		int i = al.length;
		for (int j = 0; j < i; j++) {
			long b = al[j];
			writeLong(b);
		}

	}

	public void writeFloatArray(float bs[]) throws IOException {
		if (bs == null) {
			writeInt(-1);
			return;
		}
		writeInt(bs.length);
		float af[] = bs;
		int i = af.length;
		for (int j = 0; j < i; j++) {
			float b = af[j];
			writeFloat(b);
		}

	}

	public void writeDoubleArray(double bs[]) throws IOException {
		if (bs == null) {
			writeInt(-1);
			return;
		}
		writeInt(bs.length);
		double ad[] = bs;
		int i = ad.length;
		for (int j = 0; j < i; j++) {
			double b = ad[j];
			writeDouble(b);
		}

	}

	public void writeStringArray(String bs[]) throws IOException {
		if (bs == null) {
			writeInt(-1);
			return;
		}
		writeInt(bs.length);
		String as[] = bs;
		int i = as.length;
		for (int j = 0; j < i; j++) {
			String b = as[j];
			writeString(b);
		}

	}
}
