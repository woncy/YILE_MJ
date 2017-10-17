// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   Output.java

package com.isnowfox.core.io;

import io.netty.buffer.ByteBuf;
import java.io.IOException;
import java.io.OutputStream;

public interface Output {

	public abstract void flush() throws IOException;

	public abstract void close() throws IOException;

	public abstract void writeBoolean(boolean flag) throws IOException;

	public abstract void writeInt(int i) throws IOException;

	public abstract void writeLong(long l) throws IOException;

	public abstract void writeFloat(float f) throws IOException;

	public abstract void writeDouble(double d) throws IOException;

	public abstract void writeString(String s) throws IOException;

	public abstract void writeBytes(byte abyte0[]) throws IOException;

	public abstract void writeByteBuf(ByteBuf bytebuf) throws IOException;

	public abstract void writeBooleanArray(boolean aflag[]) throws IOException;

	public abstract void writeIntArray(int ai[]) throws IOException;

	public abstract void writeLongArray(long al[]) throws IOException;

	public abstract void writeFloatArray(float af[]) throws IOException;

	public abstract void writeDoubleArray(double ad[]) throws IOException;

	public abstract void writeStringArray(String as[]) throws IOException;

	public abstract OutputStream getOutputStream();

	public abstract ByteBuf getByteBuf();
}
