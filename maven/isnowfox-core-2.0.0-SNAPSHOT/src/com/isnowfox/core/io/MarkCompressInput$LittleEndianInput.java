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

private static final class MarkCompressInput$LittleEndianInput extends MarkCompressInput {

	protected int getShort() throws IOException {
		int ch1 = in.read();
		int ch2 = in.read();
		return (ch1 << 0) + (ch2 << 8);
	}

	protected int getThree() throws IOException {
		int ch1 = in.read();
		int ch2 = in.read();
		int ch3 = in.read();
		return (ch1 << 0) + (ch2 << 8) + (ch3 << 16);
	}

	protected int getInt() throws IOException {
		int ch1 = in.read();
		int ch2 = in.read();
		int ch3 = in.read();
		int ch4 = in.read();
		return (ch1 << 0) + (ch2 << 8) + (ch3 << 16) + (ch4 << 24);
	}

	protected long getFive() throws IOException {
		long ch1 = in.read();
		long ch2 = in.read();
		long ch3 = in.read();
		long ch4 = in.read();
		long ch5 = in.read();
		return (ch1 << 0) + (ch2 << 8) + (ch3 << 16) + (ch4 << 24) + (ch5 << 32);
	}

	protected long getSix() throws IOException {
		long ch1 = in.read();
		long ch2 = in.read();
		long ch3 = in.read();
		long ch4 = in.read();
		long ch5 = in.read();
		long ch6 = in.read();
		return (ch1 << 0) + (ch2 << 8) + (ch3 << 16) + (ch4 << 24) + (ch5 << 32) + (ch6 << 40);
	}

	protected long getSeven() throws IOException {
		long ch1 = in.read();
		long ch2 = in.read();
		long ch3 = in.read();
		long ch4 = in.read();
		long ch5 = in.read();
		long ch6 = in.read();
		long ch7 = in.read();
		return (ch1 << 0) + (ch2 << 8) + (ch3 << 16) + (ch4 << 24) + (ch5 << 32) + (ch6 << 40) + (ch7 << 48);
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
		return (ch1 << 0) + (ch2 << 8) + (ch3 << 16) + (ch4 << 24) + (ch5 << 32) + (ch6 << 40) + (ch7 << 48) + (ch8 << 56);
	}

	public MarkCompressInput$LittleEndianInput(InputStream in, String charset) {
		super(in, charset);
	}

	public MarkCompressInput$LittleEndianInput(ByteBuf in, String charset) {
		super(in, charset);
	}
}
