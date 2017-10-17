// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   BitTreeDecoder.java

package SevenZip.Compression.RangeCoder;

import java.io.IOException;

// Referenced classes of package SevenZip.Compression.RangeCoder:
//			Decoder

public class BitTreeDecoder {

	short Models[];
	int NumBitLevels;

	public BitTreeDecoder(int numBitLevels) {
		NumBitLevels = numBitLevels;
		Models = new short[1 << numBitLevels];
	}

	public void Init() {
		Decoder.InitBitModels(Models);
	}

	public int Decode(Decoder rangeDecoder) throws IOException {
		int m = 1;
		for (int bitIndex = NumBitLevels; bitIndex != 0; bitIndex--) {
			m = (m << 1) + rangeDecoder.DecodeBit(Models, m);
		}

		return m - (1 << NumBitLevels);
	}

	public int ReverseDecode(Decoder rangeDecoder) throws IOException {
		int m = 1;
		int symbol = 0;
		for (int bitIndex = 0; bitIndex < NumBitLevels; bitIndex++) {
			int bit = rangeDecoder.DecodeBit(Models, m);
			m <<= 1;
			m += bit;
			symbol |= bit << bitIndex;
		}

		return symbol;
	}

	public static int ReverseDecode(short Models[], int startIndex, Decoder rangeDecoder, int NumBitLevels) throws IOException {
		int m = 1;
		int symbol = 0;
		for (int bitIndex = 0; bitIndex < NumBitLevels; bitIndex++) {
			int bit = rangeDecoder.DecodeBit(Models, startIndex + m);
			m <<= 1;
			m += bit;
			symbol |= bit << bitIndex;
		}

		return symbol;
	}
}
