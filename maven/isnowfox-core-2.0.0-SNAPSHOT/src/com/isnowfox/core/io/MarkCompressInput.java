// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   MarkCompressInput.java

package com.isnowfox.core.io;

import io.netty.buffer.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

// Referenced classes of package com.isnowfox.core.io:
//			AbstractInput, MarkCompressProtocol, ProtocolException, Input

public abstract class MarkCompressInput extends AbstractInput
	implements MarkCompressProtocol {
	static class Item {

		private String type;
		private Object value;

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public Object getValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}

		public String toString() {
			return (new StringBuilder()).append("[type=").append(type).append(", value=").append(value).append("]").toString();
		}

		public Item() {
		}

		public Item(String type, Object value) {
			this.type = type;
			this.value = value;
		}
	}

	private static final class LittleEndianInput extends MarkCompressInput {

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

		public LittleEndianInput(InputStream in, String charset) {
			super(in, charset, null);
		}

		public LittleEndianInput(ByteBuf in, String charset) {
			super(in, charset, null);
		}
	}

	private static final class BigEndianInput extends MarkCompressInput {

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

		public BigEndianInput(InputStream in, String charset) {
			super(in, charset, null);
		}

		public BigEndianInput(ByteBuf in, String charset) {
			super(in, charset, null);
		}
	}


	private ByteBuf byteBuf;

	public static final Input create(InputStream in) {
		return create(in, "utf-8", true);
	}

	public static final Input create(InputStream in, boolean isBigEndian) {
		return create(in, "utf-8", isBigEndian);
	}

	public static final Input create(InputStream in, String charset) {
		return create(in, charset, true);
	}

	public static final Input create(InputStream in, String charset, boolean isBigEndian) {
		if (isBigEndian) {
			return new BigEndianInput(in, charset);
		} else {
			return new LittleEndianInput(in, charset);
		}
	}

	public static final Input create(ByteBuf in) {
		return create(in, "utf-8", true);
	}

	public static final Input create(ByteBuf in, boolean isBigEndian) {
		return create(in, "utf-8", isBigEndian);
	}

	public static final Input create(ByteBuf in, String charset) {
		return create(in, charset, true);
	}

	public static final Input create(ByteBuf in, String charset, boolean isBigEndian) {
		if (isBigEndian) {
			return new BigEndianInput(in, charset);
		} else {
			return new LittleEndianInput(in, charset);
		}
	}

	private MarkCompressInput(InputStream in, String charset) {
		super(in, charset);
	}

	private MarkCompressInput(ByteBuf in, String charset) {
		super(new ByteBufInputStream(in), charset);
		byteBuf = in;
	}

	public ByteBuf getByteBuf() {
		return byteBuf;
	}

	public void close() throws IOException {
		in.close();
	}

	ArrayList readAll() throws IOException, ProtocolException {
		ArrayList array = new ArrayList();
		int b = in.read();
		do {
			switch (b) {
			case 254: 
			{
				array.add(new Item("TYPE_NULL_OR_ZERO_OR_FALSE", Integer.valueOf(0)));
				break;
			}

			case 253: 
			{
				array.add(new Item("TYPE_TRUE", Integer.valueOf(1)));
				break;
			}

			case 252: 
			{
				array.add(new Item("TYPE_INT_1BYTE", Integer.valueOf(in.read())));
				break;
			}

			case 251: 
			{
				array.add(new Item("TYPE_INT_2BYTE", Integer.valueOf(getShort())));
				break;
			}

			case 250: 
			{
				array.add(new Item("TYPE_INT_3BYTE", Integer.valueOf(getThree())));
				break;
			}

			case 249: 
			{
				array.add(new Item("TYPE_INT_4BYTE", Integer.valueOf(getInt())));
				break;
			}

			case 248: 
			{
				array.add(new Item("TYPE_INT_5BYTE", Long.valueOf(getFive())));
				break;
			}

			case 247: 
			{
				array.add(new Item("TYPE_INT_6BYTE", Long.valueOf(getSix())));
				break;
			}

			case 246: 
			{
				array.add(new Item("TYPE_INT_7BYTE", Long.valueOf(getSeven())));
				break;
			}

			case 245: 
			{
				array.add(new Item("TYPE_INT_8BYTE", Long.valueOf(getLong())));
				break;
			}

			case 244: 
			{
				int length = readInt();
				byte bs[] = new byte[length];
				in.read(bs);
				array.add(new Item("TYPE_STRING", new String(bs, charset)));
				break;
			}

			case 243: 
			{
				int length = readInt();
				byte bs[] = new byte[length];
				in.read(bs);
				array.add(new Item("TYPE_BYTES", bs));
				break;
			}

			case -1: 
			{
				return array;
			}

			default:
			{
				if (b > 0 && b < 241) {
					array.add(new Item("TYPE_MIN - TYPE_MAX", Integer.valueOf(b - 4)));
				} else {
					throw ProtocolException.newTypeException(b);
				}
				break;
			}
			}
			b = in.read();
		} while (true);
	}

	public boolean readBoolean() throws IOException, ProtocolException {
		int b = in.read();
		if (b == 253) {
			return true;
		}
		if (b == 254) {
			return false;
		} else {
			throw ProtocolException.newTypeException(b);
		}
	}

	public int readInt() throws IOException, ProtocolException {
		int b = in.read();
		switch (b) {
		case 253: 
			return 1;

		case 254: 
			return 0;

		case 252: 
			return in.read();

		case 251: 
			return getShort();

		case 250: 
			return getThree();

		case 249: 
			return getInt();
		}
		if (b > 0 && b < 241) {
			return b - 4;
		} else {
			throw ProtocolException.newTypeException(b);
		}
	}

	public long readLong() throws IOException, ProtocolException {
		int b = in.read();
		switch (b) {
		case 253: 
			return 1L;

		case 254: 
			return 0L;

		case 252: 
			return (long)in.read();

		case 251: 
			return (long)getShort();

		case 250: 
			return (long)getThree();

		case 249: 
			return (long)getInt();

		case 248: 
			return getFive();

		case 247: 
			return getSix();

		case 246: 
			return getSeven();

		case 245: 
			return getLong();
		}
		if (b > 0 && b < 241) {
			return (long)(b - 4);
		} else {
			throw ProtocolException.newTypeException(b);
		}
	}

	public double readDouble() throws IOException, ProtocolException {
		long l = readLong();
		return Double.longBitsToDouble(l);
	}

	public float readFloat() throws IOException, ProtocolException {
		int i = readInt();
		return Float.intBitsToFloat(i);
	}

	public String readString() throws IOException, ProtocolException {
		int b = in.read();
		if (b == 244) {
			int length = readInt();
			byte bs[] = new byte[length];
			in.read(bs);
			return new String(bs, charset);
		}
		if (b == 254) {
			return null;
		} else {
			throw ProtocolException.newTypeException(b);
		}
	}

	public byte[] readBytes() throws IOException, ProtocolException {
		int b = in.read();
		if (b == 243) {
			int length = readInt();
			byte bs[] = new byte[length];
			in.read(bs);
			return bs;
		}
		if (b == 254) {
			return null;
		} else {
			throw ProtocolException.newTypeException(b);
		}
	}

	public ByteBuf readByteBuf() throws IOException, ProtocolException {
		int length = readInt();
		if (length > 0) {
			ByteBuf copy = byteBuf.copy(byteBuf.readerIndex(), length);
			byteBuf.readerIndex(byteBuf.readerIndex() + length);
			return copy;
		}
		if (length < 0) {
			return null;
		} else {
			return byteBuf.alloc().buffer(0);
		}
	}

	protected abstract int getShort() throws IOException;

	protected abstract int getThree() throws IOException;

	protected abstract int getInt() throws IOException;

	protected abstract long getFive() throws IOException;

	protected abstract long getSix() throws IOException;

	protected abstract long getSeven() throws IOException;

	protected abstract long getLong() throws IOException;


}
