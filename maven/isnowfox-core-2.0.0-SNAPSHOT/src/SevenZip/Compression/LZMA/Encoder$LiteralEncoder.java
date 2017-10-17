// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   Encoder.java

package SevenZip.Compression.LZMA;

import SevenZip.Compression.RangeCoder.Encoder;
import java.io.IOException;

// Referenced classes of package SevenZip.Compression.LZMA:
//			Encoder

class Encoder$LiteralEncoder {
	class Encoder2 {

		short m_Encoders[];
		final Encoder.LiteralEncoder this$1;

		public void Init() {
			Encoder.InitBitModels(m_Encoders);
		}

		public void Encode(Encoder rangeEncoder, byte symbol) throws IOException {
			int context = 1;
			for (int i = 7; i >= 0; i--) {
				int bit = symbol >> i & 1;
				rangeEncoder.Encode(m_Encoders, context, bit);
				context = context << 1 | bit;
			}

		}

		public void EncodeMatched(Encoder rangeEncoder, byte matchByte, byte symbol) throws IOException {
			int context = 1;
			boolean same = true;
			for (int i = 7; i >= 0; i--) {
				int bit = symbol >> i & 1;
				int state = context;
				if (same) {
					int matchBit = matchByte >> i & 1;
					state += 1 + matchBit << 8;
					same = matchBit == bit;
				}
				rangeEncoder.Encode(m_Encoders, state, bit);
				context = context << 1 | bit;
			}

		}

		public int GetPrice(boolean matchMode, byte matchByte, byte symbol) {
			int price = 0;
			int context = 1;
			int i = 7;
			if (matchMode) {
				do {
					if (i < 0) {
						break;
					}
					int matchBit = matchByte >> i & 1;
					int bit = symbol >> i & 1;
					price += Encoder.GetPrice(m_Encoders[(1 + matchBit << 8) + context], bit);
					context = context << 1 | bit;
					if (matchBit != bit) {
						i--;
						break;
					}
					i--;
				} while (true);
			}
			for (; i >= 0; i--) {
				int bit = symbol >> i & 1;
				price += Encoder.GetPrice(m_Encoders[context], bit);
				context = context << 1 | bit;
			}

			return price;
		}

		Encoder2() {
			this.this$1 = Encoder.LiteralEncoder.this;
			super();
			m_Encoders = new short[768];
		}
	}


	Encoder2 m_Coders[];
	int m_NumPrevBits;
	int m_NumPosBits;
	int m_PosMask;
	final SevenZip.Compression.LZMA.Encoder this$0;

	public void Create(int numPosBits, int numPrevBits) {
		if (m_Coders != null && m_NumPrevBits == numPrevBits && m_NumPosBits == numPosBits) {
			return;
		}
		m_NumPosBits = numPosBits;
		m_PosMask = (1 << numPosBits) - 1;
		m_NumPrevBits = numPrevBits;
		int numStates = 1 << m_NumPrevBits + m_NumPosBits;
		m_Coders = new Encoder2[numStates];
		for (int i = 0; i < numStates; i++) {
			m_Coders[i] = new Encoder2();
		}

	}

	public void Init() {
		int numStates = 1 << m_NumPrevBits + m_NumPosBits;
		for (int i = 0; i < numStates; i++) {
			m_Coders[i].Init();
		}

	}

	public Encoder2 GetSubCoder(int pos, byte prevByte) {
		return m_Coders[((pos & m_PosMask) << m_NumPrevBits) + ((prevByte & 0xff) >>> 8 - m_NumPrevBits)];
	}

	Encoder$LiteralEncoder() {
		this.this$0 = SevenZip.Compression.LZMA.Encoder.this;
		super();
	}
}
