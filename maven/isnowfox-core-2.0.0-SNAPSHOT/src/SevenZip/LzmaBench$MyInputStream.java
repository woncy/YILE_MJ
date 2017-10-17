// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   LzmaBench.java

package SevenZip;

import java.io.InputStream;

// Referenced classes of package SevenZip:
//			LzmaBench

static class LzmaBench$MyInputStream extends InputStream {

	byte _buffer[];
	int _size;
	int _pos;

	public void reset() {
		_pos = 0;
	}

	public int read() {
		if (_pos >= _size) {
			return -1;
		} else {
			return _buffer[_pos++] & 0xff;
		}
	}

	public LzmaBench$MyInputStream(byte buffer[], int size) {
		_buffer = buffer;
		_size = size;
	}
}
