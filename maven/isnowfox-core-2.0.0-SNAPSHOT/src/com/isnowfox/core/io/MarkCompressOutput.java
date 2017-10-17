// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   MarkCompressOutput.java

package com.isnowfox.core.io;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import java.io.IOException;
import java.io.OutputStream;

// Referenced classes of package com.isnowfox.core.io:
//			AbstractOutput, MarkCompressProtocol, Output

public abstract class MarkCompressOutput extends AbstractOutput
	implements MarkCompressProtocol {
	private static final class LittleEndianOutput extends MarkCompressOutput {

		protected void putShort(int v) throws IOException {
			out.write(v >>> 0);
			out.write(v >>> 8);
		}

		protected void putThree(int v) throws IOException {
			out.write(v >>> 0);
			out.write(v >>> 8);
			out.write(v >>> 16);
		}

		protected final void putInt(int v) throws IOException {
			out.write(v >>> 0);
			out.write(v >>> 8);
			out.write(v >>> 16);
			out.write(v >>> 24);
		}

		protected void putFive(long v) throws IOException {
			out.write((int)(v >>> 0));
			out.write((int)(v >>> 8));
			out.write((int)(v >>> 16));
			out.write((int)(v >>> 24));
			out.write((int)(v >>> 32));
		}

		protected void putSix(long v) throws IOException {
			out.write((int)(v >>> 0));
			out.write((int)(v >>> 8));
			out.write((int)(v >>> 16));
			out.write((int)(v >>> 24));
			out.write((int)(v >>> 32));
			out.write((int)(v >>> 40));
		}

		protected void putSeven(long v) throws IOException {
			out.write((int)(v >>> 0));
			out.write((int)(v >>> 8));
			out.write((int)(v >>> 16));
			out.write((int)(v >>> 24));
			out.write((int)(v >>> 32));
			out.write((int)(v >>> 40));
			out.write((int)(v >>> 48));
		}

		protected void putLong(long v) throws IOException {
			out.write((int)(v >>> 0));
			out.write((int)(v >>> 8));
			out.write((int)(v >>> 16));
			out.write((int)(v >>> 24));
			out.write((int)(v >>> 32));
			out.write((int)(v >>> 40));
			out.write((int)(v >>> 48));
			out.write((int)(v >>> 56));
		}

		private LittleEndianOutput(OutputStream out, String charset) {
			super(out, charset);
		}

		public LittleEndianOutput(ByteBuf byteBuf, String charset) {
			super(byteBuf, charset);
		}

	}

	private static final class BigEndianOutput extends MarkCompressOutput {

		protected void putShort(int v) throws IOException {
			out.write(v >>> 8);
			out.write(v >>> 0);
		}

		protected void putThree(int v) throws IOException {
			out.write(v >>> 16);
			out.write(v >>> 8);
			out.write(v >>> 0);
		}

		protected void putInt(int v) throws IOException {
			out.write(v >>> 24);
			out.write(v >>> 16);
			out.write(v >>> 8);
			out.write(v >>> 0);
		}

		protected void putFive(long v) throws IOException {
			out.write((int)(v >>> 32));
			out.write((int)(v >>> 24));
			out.write((int)(v >>> 16));
			out.write((int)(v >>> 8));
			out.write((int)(v >>> 0));
		}

		protected void putSix(long v) throws IOException {
			out.write((int)(v >>> 40));
			out.write((int)(v >>> 32));
			out.write((int)(v >>> 24));
			out.write((int)(v >>> 16));
			out.write((int)(v >>> 8));
			out.write((int)(v >>> 0));
		}

		protected void putSeven(long v) throws IOException {
			out.write((int)(v >>> 48));
			out.write((int)(v >>> 40));
			out.write((int)(v >>> 32));
			out.write((int)(v >>> 24));
			out.write((int)(v >>> 16));
			out.write((int)(v >>> 8));
			out.write((int)(v >>> 0));
		}

		protected void putLong(long v) throws IOException {
			out.write((int)(v >>> 56));
			out.write((int)(v >>> 48));
			out.write((int)(v >>> 40));
			out.write((int)(v >>> 32));
			out.write((int)(v >>> 24));
			out.write((int)(v >>> 16));
			out.write((int)(v >>> 8));
			out.write((int)(v >>> 0));
		}

		private BigEndianOutput(OutputStream out, String charset) {
			super(out, charset);
		}

		public BigEndianOutput(ByteBuf byteBuf, String charset) {
			super(byteBuf, charset);
		}

	}


	private ByteBuf byteBuf;

	public static final Output create(OutputStream out) {
		return create(out, "utf-8", true);
	}

	public static final Output create(OutputStream out, boolean isBigEndian) {
		return create(out, "utf-8", isBigEndian);
	}

	public static final Output create(OutputStream out, String charset) {
		return create(out, charset, true);
	}

	public static final Output create(OutputStream out, String charset, boolean isBigEndian) {
		if (isBigEndian) {
			return new BigEndianOutput(out, charset);
		} else {
			return new LittleEndianOutput(out, charset);
		}
	}

	public static final Output create(ByteBuf out) {
		return create(out, "utf-8", true);
	}

	public static final Output create(ByteBuf out, boolean isBigEndian) {
		return create(out, "utf-8", isBigEndian);
	}

	public static final Output create(ByteBuf out, String charset) {
		return create(out, charset, true);
	}

	public static final Output create(ByteBuf out, String charset, boolean isBigEndian) {
		if (isBigEndian) {
			return new BigEndianOutput(out, charset);
		} else {
			return new LittleEndianOutput(out, charset);
		}
	}

	public MarkCompressOutput(OutputStream out, String charset) {
		super(out, charset);
	}

	public MarkCompressOutput(ByteBuf byteBuf, String charset) {
		super(new ByteBufOutputStream(byteBuf), charset);
		this.byteBuf = byteBuf;
	}

	public ByteBuf getByteBuf() {
		return byteBuf;
	}

	public void writeByteBuf(ByteBuf byteBuf) throws IOException {
		if (byteBuf == null) {
			writeInt(-1);
		} else {
			writeInt(byteBuf.readableBytes());
			this.byteBuf.writeBytes(byteBuf);
			byteBuf.release();
		}
	}

	public final void flush() throws IOException {
		out.flush();
	}

	public final void close() throws IOException {
		out.close();
	}

	public final void writeBoolean(boolean v) throws IOException {
		out.write(v ? 253 : 254);
	}

	public final void writeInt(int v) throws IOException {
		if (v > -4 && v < 237) {
			out.write(v + 4);
		} else
		if (v >>> 8 == 0) {
			out.write(252);
			out.write(v);
		} else
		if (v >>> 16 == 0) {
			out.write(251);
			putShort(v);
		} else
		if (v >>> 24 == 0) {
			out.write(250);
			putThree(v);
		} else {
			out.write(249);
			putInt(v);
		}
	}

	public final void writeFloatByInt(int v) throws IOException {
		if (v == 0) {
			out.write(254);
		} else
		if (v >>> 8 == 0) {
			out.write(252);
			out.write(v);
		} else
		if (v >>> 16 == 0) {
			out.write(251);
			putShort(v);
		} else
		if (v >>> 24 == 0) {
			out.write(250);
			putThree(v);
		} else {
			out.write(249);
			putInt(v);
		}
	}

	public final void writeLong(long v) throws IOException {
		if (v > -4L && v < 237L) {
			out.write((int)(v + 4L));
		} else
		if (v >>> 8 == 0L) {
			out.write(252);
			out.write((int)(v & 255L));
		} else
		if (v >>> 16 == 0L) {
			out.write(251);
			putShort((int)v);
		} else
		if (v >>> 24 == 0L) {
			out.write(250);
			putThree((int)v);
		} else
		if (v >>> 32 == 0L) {
			out.write(249);
			putInt((int)v);
		} else
		if (v >>> 40 == 0L) {
			out.write(248);
			putFive(v);
		} else
		if (v >>> 48 == 0L) {
			out.write(247);
			putSix(v);
		} else
		if (v >>> 56 == 0L) {
			out.write(246);
			putSeven(v);
		} else {
			out.write(245);
			putLong(v);
		}
	}

	private final void writeDoubleByLong(long v) throws IOException {
		if (v == 0L) {
			out.write(254);
		} else
		if (v >>> 8 == 0L) {
			out.write(252);
			out.write((int)(v & 255L));
		} else
		if (v >>> 16 == 0L) {
			out.write(251);
			putShort((int)v);
		} else
		if (v >>> 24 == 0L) {
			out.write(250);
			putThree((int)v);
		} else
		if (v >>> 32 == 0L) {
			out.write(249);
			putInt((int)v);
		} else
		if (v >>> 40 == 0L) {
			out.write(248);
			putFive(v);
		} else
		if (v >>> 48 == 0L) {
			out.write(247);
			putSix(v);
		} else
		if (v >>> 56 == 0L) {
			out.write(246);
			putSeven(v);
		} else {
			out.write(245);
			putLong(v);
		}
	}

	public final void writeFloat(float v) throws IOException {
		writeFloatByInt(Float.floatToIntBits(v));
	}

	public final void writeDouble(double v) throws IOException {
		writeDoubleByLong(Double.doubleToLongBits(v));
	}

	public final void writeString(String s) throws IOException {
		if (s == null) {
			out.write(254);
			return;
		} else {
			byte b[] = s.getBytes(charset);
			out.write(244);
			writeInt(b.length);
			out.write(b);
			return;
		}
	}

	public void writeBytes(byte bs[]) throws IOException {
		if (bs == null) {
			out.write(254);
			return;
		} else {
			out.write(243);
			writeInt(bs.length);
			out.write(bs);
			return;
		}
	}

	protected abstract void putShort(int i) throws IOException;

	protected abstract void putThree(int i) throws IOException;

	protected abstract void putInt(int i) throws IOException;

	protected abstract void putFive(long l) throws IOException;

	protected abstract void putSix(long l) throws IOException;

	protected abstract void putSeven(long l) throws IOException;

	protected abstract void putLong(long l) throws IOException;
}
