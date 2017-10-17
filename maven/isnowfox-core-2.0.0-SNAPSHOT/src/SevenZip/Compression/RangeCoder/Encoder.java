// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   Encoder.java

package SevenZip.Compression.RangeCoder;

import java.io.IOException;
import java.io.OutputStream;

public class Encoder {

	static final int kTopMask = 0xff000000;
	static final int kNumBitModelTotalBits = 11;
	static final int kBitModelTotal = 2048;
	static final int kNumMoveBits = 5;
	OutputStream Stream;
	long Low;
	int Range;
	int _cacheSize;
	int _cache;
	long _position;
	static final int kNumMoveReducingBits = 2;
	public static final int kNumBitPriceShiftBits = 6;
	private static int ProbPrices[];

	public Encoder() {
	}

	public void SetStream(OutputStream stream) {
		Stream = stream;
	}

	public void ReleaseStream() {
		Stream = null;
	}

	public void Init() {
		_position = 0L;
		Low = 0L;
		Range = -1;
		_cacheSize = 1;
		_cache = 0;
	}

	public void FlushData() throws IOException {
		for (int i = 0; i < 5; i++) {
			ShiftLow();
		}

	}

	public void FlushStream() throws IOException {
		Stream.flush();
	}

	public void ShiftLow() throws IOException {
		int LowHi = (int)(Low >>> 32);
		if (LowHi != 0 || Low < 0xff000000L) {
			_position += _cacheSize;
			int temp = _cache;
			do {
				Stream.write(temp + LowHi);
				temp = 255;
			} while (--_cacheSize != 0);
			_cache = (int)Low >>> 24;
		}
		_cacheSize++;
		Low = (Low & 0xffffffL) << 8;
	}

	public void EncodeDirectBits(int v, int numTotalBits) throws IOException {
		for (int i = numTotalBits - 1; i >= 0; i--) {
			Range >>>= 1;
			if ((v >>> i & 1) == 1) {
				Low += Range;
			}
			if ((Range & 0xff000000) == 0) {
				Range <<= 8;
				ShiftLow();
			}
		}

	}

	public long GetProcessedSizeAdd() {
		return (long)_cacheSize + _position + 4L;
	}

	public static void InitBitModels(short probs[]) {
		for (int i = 0; i < probs.length; i++) {
			probs[i] = 1024;
		}

	}

	public void Encode(short probs[], int index, int symbol) throws IOException {
		int prob = probs[index];
		int newBound = (Range >>> 11) * prob;
		if (symbol == 0) {
			Range = newBound;
			probs[index] = (short)(prob + (2048 - prob >>> 5));
		} else {
			Low += (long)newBound & 0xffffffffL;
			Range -= newBound;
			probs[index] = (short)(prob - (prob >>> 5));
		}
		if ((Range & 0xff000000) == 0) {
			Range <<= 8;
			ShiftLow();
		}
	}

	public static int GetPrice(int Prob, int symbol) {
		return ProbPrices[((Prob - symbol ^ -symbol) & 0x7ff) >>> 2];
	}

	public static int GetPrice0(int Prob) {
		return ProbPrices[Prob >>> 2];
	}

	public static int GetPrice1(int Prob) {
		return ProbPrices[2048 - Prob >>> 2];
	}

	static  {
		ProbPrices = new int[512];
		int kNumBits = 9;
		for (int i = kNumBits - 1; i >= 0; i--) {
			int start = 1 << kNumBits - i - 1;
			int end = 1 << kNumBits - i;
			for (int j = start; j < end; j++) {
				ProbPrices[j] = (i << 6) + ((end - j << 6) >>> kNumBits - i - 1);
			}

		}

	}
}
