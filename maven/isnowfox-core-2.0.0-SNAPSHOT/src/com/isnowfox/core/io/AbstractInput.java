// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   AbstractInput.java

package com.isnowfox.core.io;

import java.io.IOException;
import java.io.InputStream;

// Referenced classes of package com.isnowfox.core.io:
//			Input, ProtocolException

public abstract class AbstractInput
	implements Input {

	protected final InputStream in;
	protected final String charset;

	public AbstractInput(InputStream in, String charset) {
		this.in = in;
		this.charset = charset;
	}

	public InputStream getInputStream() {
		return in;
	}

	public boolean[] readBooleanArray() throws IOException, ProtocolException {
		int len = readInt();
		if (len == -1) {
			return null;
		}
		boolean a[] = new boolean[len];
		for (int i = 0; i < len; i++) {
			a[i] = readBoolean();
		}

		return a;
	}

	public int[] readIntArray() throws IOException, ProtocolException {
		int len = readInt();
		if (len == -1) {
			return null;
		}
		int a[] = new int[len];
		for (int i = 0; i < len; i++) {
			a[i] = readInt();
		}

		return a;
	}

	public long[] readLongArray() throws IOException, ProtocolException {
		int len = readInt();
		if (len == -1) {
			return null;
		}
		long a[] = new long[len];
		for (int i = 0; i < len; i++) {
			a[i] = readLong();
		}

		return a;
	}

	public float[] readFloatArray() throws IOException, ProtocolException {
		int len = readInt();
		if (len == -1) {
			return null;
		}
		float a[] = new float[len];
		for (int i = 0; i < len; i++) {
			a[i] = readFloat();
		}

		return a;
	}

	public double[] readDoubleArray() throws IOException, ProtocolException {
		int len = readInt();
		if (len == -1) {
			return null;
		}
		double a[] = new double[len];
		for (int i = 0; i < len; i++) {
			a[i] = readDouble();
		}

		return a;
	}

	public String[] readStringArray() throws IOException, ProtocolException {
		int len = readInt();
		if (len == -1) {
			return null;
		}
		String a[] = new String[len];
		for (int i = 0; i < len; i++) {
			a[i] = readString();
		}

		return a;
	}
}
