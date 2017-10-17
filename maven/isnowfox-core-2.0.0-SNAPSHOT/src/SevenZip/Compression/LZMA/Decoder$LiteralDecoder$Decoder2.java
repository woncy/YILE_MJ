// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   Decoder.java

package SevenZip.Compression.LZMA;

import SevenZip.Compression.RangeCoder.Decoder;
import java.io.IOException;

// Referenced classes of package SevenZip.Compression.LZMA:
//			Decoder

class Decoder$LiteralDecoder$Decoder2 {

	short m_Decoders[];
	final Decoder$LiteralDecoder this$1;

	public void Init() {
		Decoder.InitBitModels(m_Decoders);
	}

	public byte DecodeNormal(Decoder rangeDecoder) throws IOException {
		int symbol = 1;
		do {
			symbol = symbol << 1 | rangeDecoder.DecodeBit(m_Decoders, symbol);
		} while (symbol < 256);
		return (byte)symbol;
	}

	public byte DecodeWithMatchByte(Decoder rangeDecoder, byte matchByte) throws IOException {
		int symbol = 1;
		do {
			int matchBit = matchByte >> 7 & 1;
			matchByte <<= 1;
			int bit = rangeDecoder.DecodeBit(m_Decoders, (1 + matchBit << 8) + symbol);
			symbol = symbol << 1 | bit;
			if (matchBit == bit) {
				continue;
			}
			for (; symbol < 256; symbol = symbol << 1 | rangeDecoder.DecodeBit(m_Decoders, symbol)) { }
			break;
		} while (symbol < 256);
		return (byte)symbol;
	}

	Decoder$LiteralDecoder$Decoder2() {
		this.this$1 = Decoder$LiteralDecoder.this;
		super();
		m_Decoders = new short[768];
	}
}
