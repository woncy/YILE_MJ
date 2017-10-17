// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   ZipUtils.java

package com.isnowfox.util;

import com.isnowfox.core.io.IoUtils;
import io.netty.buffer.*;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.zip.*;
import org.apache.commons.compress.compressors.gzip.*;
import org.apache.commons.compress.utils.IOUtils;

public final class ZipUtils {

	public ZipUtils() {
	}

	public static ByteBuf decompressionMap(Blob blob, ByteBufAllocator alloc) throws IOException, SQLException {
		java.io.InputStream binaryStream = blob.getBinaryStream();
		int len = IoUtils.getBigEndianInt(binaryStream);
		GzipCompressorInputStream in = new GzipCompressorInputStream(binaryStream);
		ByteBuf zipBuf = alloc.buffer(len);
		zipBuf.writeBytes(in, len);
		return zipBuf;
	}

	public static ByteBuf decompression(ByteBuf byteBuf, int len) throws IOException {
		ByteBufInputStream byteBufInputStream = new ByteBufInputStream(byteBuf, len);
		GzipCompressorInputStream in = new GzipCompressorInputStream(byteBufInputStream);
		ByteBuf zipBuf = byteBuf.alloc().buffer();
		ByteBufOutputStream out = new ByteBufOutputStream(zipBuf);
		IOUtils.copy(in, out);
		return zipBuf;
	}

	public static ByteBuf decompressionDeflater(ByteBuf byteBuf, int len) throws IOException {
		ByteBufInputStream byteBufInputStream = new ByteBufInputStream(byteBuf, len);
		DeflaterInputStream in = new DeflaterInputStream(byteBufInputStream, new Deflater(9, true));
		ByteBuf zipBuf = byteBuf.alloc().buffer();
		ByteBufOutputStream out = new ByteBufOutputStream(zipBuf);
		IOUtils.copy(in, out);
		return zipBuf;
	}

	public static ByteBuf compressDeflater(ByteBuf byteBuf, int bufOffset, int length) throws IOException {
		ByteBuf zipBuf = byteBuf.alloc().buffer();
		Deflater deflater = new Deflater(9, true);
		ByteBufOutputStream out = new ByteBufOutputStream(zipBuf);
		DeflaterOutputStream cout = new DeflaterOutputStream(out, deflater);
		byteBuf.getBytes(bufOffset, cout, length);
		cout.finish();
		cout.flush();
		cout.close();
		return zipBuf;
	}

	public static ByteBuf compress(ByteBuf byteBuf, int bufOffset, int length) throws IOException {
		GzipParameters cParams = new GzipParameters();
		cParams.setCompressionLevel(9);
		ByteBuf zipBuf = byteBuf.alloc().buffer();
		ByteBufOutputStream out = new ByteBufOutputStream(zipBuf);
		GzipCompressorOutputStream cout = new GzipCompressorOutputStream(out, cParams);
		byteBuf.getBytes(bufOffset, cout, length);
		cout.finish();
		cout.flush();
		cout.close();
		return zipBuf;
	}

	public static byte[] compressMapBestSpeed(ByteBuf byteBuf, int length) throws IOException {
		GzipParameters cParams = new GzipParameters();
		cParams.setCompressionLevel(1);
		ByteBuf zipBuf = byteBuf.alloc().buffer();
		zipBuf.writeInt(length);
		ByteBufOutputStream out = new ByteBufOutputStream(zipBuf);
		GzipCompressorOutputStream cout = new GzipCompressorOutputStream(out, cParams);
		byteBuf.getBytes(0, cout, length);
		cout.finish();
		cout.flush();
		cout.close();
		int readableBytes = zipBuf.readableBytes();
		byte result[] = new byte[readableBytes];
		zipBuf.getBytes(0, result);
		zipBuf.release();
		return result;
	}
}
