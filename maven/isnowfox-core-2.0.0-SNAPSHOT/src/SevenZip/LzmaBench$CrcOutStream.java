// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   LzmaBench.java

package SevenZip;

import java.io.OutputStream;

// Referenced classes of package SevenZip:
//			CRC, LzmaBench

static class LzmaBench$CrcOutStream extends OutputStream {

	public CRC CRC;

	public void Init() {
		CRC.Init();
	}

	public int GetDigest() {
		return CRC.GetDigest();
	}

	public void write(byte b[]) {
		CRC.Update(b);
	}

	public void write(byte b[], int off, int len) {
		CRC.Update(b, off, len);
	}

	public void write(int b) {
		CRC.UpdateByte(b);
	}

	LzmaBench$CrcOutStream() {
		CRC = new CRC();
	}
}
