// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   CrcEncryptCoder.java

package com.isnowfox.core.net.message.coder;

import com.isnowfox.core.net.message.*;
import com.isnowfox.util.ZipUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CrcEncryptCoder {

	protected static final Logger log = LoggerFactory.getLogger(com/isnowfox/core/net/message/coder/CrcEncryptCoder);
	public static final byte CRC_7_BYTE[] = {
		0, 18, 36, 54, 72, 90, 108, 126, 25, 11, 
		61, 47, 81, 67, 117, 103, 50, 32, 22, 4, 
		122, 104, 94, 76, 43, 57, 15, 29, 99, 113, 
		71, 85, 100, 118, 64, 82, 44, 62, 8, 26, 
		125, 111, 89, 75, 53, 39, 17, 3, 86, 68, 
		114, 96, 30, 12, 58, 40, 79, 93, 107, 121, 
		7, 21, 35, 49, 65, 83, 101, 119, 9, 27, 
		45, 63, 88, 74, 124, 110, 16, 2, 52, 38, 
		115, 97, 87, 69, 59, 41, 31, 13, 106, 120, 
		78, 92, 34, 48, 6, 20, 37, 55, 1, 19, 
		109, 127, 73, 91, 60, 46, 24, 10, 116, 102, 
		80, 66, 23, 5, 51, 33, 95, 77, 123, 105, 
		14, 28, 42, 56, 70, 84, 98, 112
	};
	public static final int ZIP_MASK = 128;
	public static final int RANDOM_BIT = 8;
	private final int zipSize;
	private final int initEncryptValue;
	private int readValue;
	private int readCount;
	private int writeValue;
	private int writeCount;
	private final ReentrantLock checkLock = new ReentrantLock();

	public CrcEncryptCoder(int zipSize, int encryptValue) {
		readCount = 0;
		writeCount = 0;
		this.zipSize = zipSize;
		initEncryptValue = encryptValue;
		readValue = initEncryptValue;
		writeValue = initEncryptValue;
	}

	public Packet read(int len, ByteBuf in) throws IOException, MessageException {
		if (!checkLock.tryLock()) {
			break MISSING_BLOCK_LABEL_260;
		}
		Packet packet;
		readCount++;
		ByteBuf buf = in.alloc().buffer(len);
		in.readBytes(buf, len);
		int crc7 = 0;
		readValue = item(readValue, 8);
		int dataByte = 0;
		for (int i = 0; i < len - 1; i++) {
			dataByte = buf.getUnsignedByte(i) ^ readValue;
			crc7 = crc7Item(dataByte, crc7);
			buf.setByte(i, dataByte);
		}

		readValue = item(readValue, 8);
		dataByte = buf.getUnsignedByte(len - 1);
		dataByte ^= readValue;
		boolean isZip = (dataByte & 0x80) > 0;
		dataByte &= 0xffffff7f;
		if (crc7 != dataByte) {
			throw new RuntimeException("crc7验证失败！");
		}
		Packet p;
		if (isZip) {
			ByteBuf decBuf = ZipUtils.decompressionDeflater(buf, len - 1);
			buf.release();
			p = new Packet(decBuf.writerIndex(), (byte)0, decBuf, 0);
		} else {
			p = new Packet(len - 1, (byte)0, buf, 0);
		}
		packet = p;
		checkLock.unlock();
		return packet;
		Exception exception;
		exception;
		checkLock.unlock();
		throw exception;
		throw new RuntimeException("错误的！为啥不是线程安全的？");
	}

	public void write(ByteBuf out, Packet msg) throws IOException, MessageException {
		if (!checkLock.tryLock()) {
			break MISSING_BLOCK_LABEL_290;
		}
		writeCount++;
		int len = msg.getLength();
		int offset = 0;
		ByteBuf data;
		boolean isZip;
		if (len >= zipSize) {
			data = ZipUtils.compressDeflater(msg.getBuf(), msg.getBufOffset(), len);
			log.debug("压缩率:{}, {}k, {}k", new Object[] {
				Double.valueOf((double)data.readableBytes() / (double)len), Double.valueOf((double)data.readableBytes() / 1204D), Double.valueOf((double)len / 1024D)
			});
			len = data.readableBytes();
			isZip = true;
		} else {
			data = msg.getBuf();
			offset = msg.getBufOffset();
			isZip = false;
		}
		if (len > 0xffffff) {
			throw MessageException.newLengthException(len);
		}
		int crc7 = 0;
		writeValue = item(writeValue, 8);
		out.writeMedium(len + 1);
		for (int i = offset; i < len; i++) {
			int dataByte = data.getByte(i);
			crc7 = crc7Item(dataByte, crc7);
			out.writeByte(dataByte ^ writeValue);
		}

		if (isZip) {
			crc7 |= 0x80;
			data.release();
		}
		writeValue = item(writeValue, 8);
		crc7 ^= writeValue;
		out.writeByte(crc7);
		checkLock.unlock();
		break MISSING_BLOCK_LABEL_300;
		Exception exception;
		exception;
		checkLock.unlock();
		throw exception;
		throw new RuntimeException("错误的！为啥不是线程安全的？");
	}

	private int item(int seed, int bits) {
		seed = (seed ^ 0x1315d) & 0xffffff;
		seed = seed * 0x1315d + 11 & 0xffffff;
		return seed >>> 24 - bits;
	}

	public static int crc7Check(byte by1[]) {
		int result = 0;
		for (int i = 0; i < by1.length; i++) {
			int data = by1[i];
			if (data < 0) {
				result = CRC_7_BYTE[256 + data >>> 1 ^ result];
			} else {
				result = CRC_7_BYTE[data >>> 1 ^ result];
			}
			int b = data & 1;
			if (b == 0) {
				result ^= 0;
			} else {
				result ^= 9;
			}
			if (result > 127) {
				throw new RuntimeException("result max");
			}
		}

		return result;
	}

	public static int crc7Item(int data, int crc7Value) {
		if (data < 0) {
			crc7Value = CRC_7_BYTE[256 + data >>> 1 ^ crc7Value];
		} else {
			crc7Value = CRC_7_BYTE[data >>> 1 ^ crc7Value];
		}
		int b = data & 1;
		if (b == 0) {
			crc7Value ^= 0;
		} else {
			crc7Value ^= 9;
		}
		if (crc7Value > 127) {
			throw new RuntimeException("result max");
		} else {
			return crc7Value;
		}
	}

}
