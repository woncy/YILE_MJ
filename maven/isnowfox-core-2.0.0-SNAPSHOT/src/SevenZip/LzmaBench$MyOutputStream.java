// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   LzmaBench.java

package SevenZip;

import java.io.IOException;
import java.io.OutputStream;

// Referenced classes of package SevenZip:
//			LzmaBench

static class LzmaBench$MyOutputStream extends OutputStream {

	byte _buffer[];
	int _size;
	int _pos;

	public void reset() {
		_pos = 0;
	}

	public void write(int b) throws IOException {
		if (_pos >= _size) {
			throw new IOException("Error");
		} else {
			_buffer[_pos++] = (byte)b;
			return;
		}
	}

	public int size() {
		return _pos;
	}

	public LzmaBench$MyOutputStream(byte buffer[]) {
		_buffer = buffer;
		_size = _buffer.length;
	}
}
