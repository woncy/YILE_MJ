// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   MarkCompressInput.java

package com.isnowfox.core.io;

import io.netty.buffer.ByteBuf;
import java.io.IOException;
import java.io.InputStream;

// Referenced classes of package com.isnowfox.core.io:
//			MarkCompressInput

private static final class MarkCompressInput$BigEndianInput extends MarkCompressInput {

	protected int getShort() throws IOException {
		int ch1 = in.read();
		int ch2 = in.read();
		return (ch1 << 8) + (ch2 << 0);
	}

	protected int getThree() throws IOException {
		int ch1 = in.read();
		int ch2 = in.read();
		int ch3 = in.read();
		return (ch1 << 16) + (ch2 << 8) + (ch3 << 0);
	}

	protected int getInt() throws IOException {
		int ch1 = in.read();
		int ch2 = in.read();
		int ch3 = in.read();
		int ch4 = in.read();
		return (ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0);
	}

	protected long getFive() throws IOException {
		long ch1 = in.read();
		long ch2 = in.read();
		long ch3 = in.read();
		long ch4 = in.read();
		long ch5 = in.read();
		return (ch1 << 32) + (ch2 << 24) + (ch3 << 16) + (ch4 << 8) + (ch5 << 0);
	}

	protected long getSix() throws IOException {
		long ch1 = in.read();
		long ch2 = in.read();
		long ch3 = in.read();
		long ch4 = in.read();
		long ch5 = in.read();
		long ch6 = in.read();
		return (ch1 << 40) + (ch2 << 32) + (ch3 << 24) + (ch4 << 16) + (ch5 << 8) + (ch6 << 0);
	}

	protected long getSeven() throws IOException {
		long ch1 = in.read();
		long ch2 = in.read();
		long ch3 = in.read();
		long ch4 = in.read();
		long ch5 = in.read();
		long ch6 = in.read();
		long ch7 = in.read();
		return (ch1 << 48) + (ch2 << 40) + (ch3 << 32) + (ch4 << 24) + (ch5 << 16) + (ch6 << 8) + (ch7 << 0);
	}

	protected long getLong() throws IOException {
		long ch1 = in.read();
		long ch2 = in.read();
		long ch3 = in.read();
		long ch4 = in.read();
		long ch5 = in.read();
		long ch6 = in.read();
		long ch7 = in.read();
		long ch8 = in.read();
		return (ch1 << 56) + (ch2 << 48) + (ch3 << 40) + (ch4 << 32) + (ch5 << 24) + (ch6 << 16) + (ch7 << 8) + (ch8 << 0);
	}

	public MarkCompressInput$BigEndianInput(InputStream in, String charset) {
		super(in, charset);
	}

	public MarkCompressInput$BigEndianInput(ByteBuf in, String charset) {
		super(in, charset);
	}
}
