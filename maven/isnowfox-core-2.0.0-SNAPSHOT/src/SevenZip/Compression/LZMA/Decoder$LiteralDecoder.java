// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   Decoder.java

package SevenZip.Compression.LZMA;

import SevenZip.Compression.RangeCoder.Decoder;
import java.io.IOException;

// Referenced classes of package SevenZip.Compression.LZMA:
//			Decoder

class Decoder$LiteralDecoder {
	class Decoder2 {

		short m_Decoders[];
		final Decoder.LiteralDecoder this$1;

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

		Decoder2() {
			this.this$1 = Decoder.LiteralDecoder.this;
			super();
			m_Decoders = new short[768];
		}
	}


	Decoder2 m_Coders[];
	int m_NumPrevBits;
	int m_NumPosBits;
	int m_PosMask;
	final SevenZip.Compression.LZMA.Decoder this$0;

	public void Create(int numPosBits, int numPrevBits) {
		if (m_Coders != null && m_NumPrevBits == numPrevBits && m_NumPosBits == numPosBits) {
			return;
		}
		m_NumPosBits = numPosBits;
		m_PosMask = (1 << numPosBits) - 1;
		m_NumPrevBits = numPrevBits;
		int numStates = 1 << m_NumPrevBits + m_NumPosBits;
		m_Coders = new Decoder2[numStates];
		for (int i = 0; i < numStates; i++) {
			m_Coders[i] = new Decoder2();
		}

	}

	public void Init() {
		int numStates = 1 << m_NumPrevBits + m_NumPosBits;
		for (int i = 0; i < numStates; i++) {
			m_Coders[i].Init();
		}

	}

	Decoder2 GetDecoder(int pos, byte prevByte) {
		return m_Coders[((pos & m_PosMask) << m_NumPrevBits) + ((prevByte & 0xff) >>> 8 - m_NumPrevBits)];
	}

	Decoder$LiteralDecoder() {
		this.this$0 = SevenZip.Compression.LZMA.Decoder.this;
		super();
	}
}
