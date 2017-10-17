// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   CRC.java

package SevenZip;


public class CRC {

	public static int Table[];
	int _value;

	public CRC() {
		_value = -1;
	}

	public void Init() {
		_value = -1;
	}

	public void Update(byte data[], int offset, int size) {
		for (int i = 0; i < size; i++) {
			_value = Table[(_value ^ data[offset + i]) & 0xff] ^ _value >>> 8;
		}

	}

	public void Update(byte data[]) {
		int size = data.length;
		for (int i = 0; i < size; i++) {
			_value = Table[(_value ^ data[i]) & 0xff] ^ _value >>> 8;
		}

	}

	public void UpdateByte(int b) {
		_value = Table[(_value ^ b) & 0xff] ^ _value >>> 8;
	}

	public int GetDigest() {
		return ~_value;
	}

	static  {
		Table = new int[256];
		for (int i = 0; i < 256; i++) {
			int r = i;
			for (int j = 0; j < 8; j++) {
				if ((r & 1) != 0) {
					r = r >>> 1 ^ 0xedb88320;
				} else {
					r >>>= 1;
				}
			}

			Table[i] = r;
		}

	}
}
