// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   Decoder.java

package SevenZip.Compression.LZMA;

import SevenZip.Compression.RangeCoder.BitTreeDecoder;
import SevenZip.Compression.RangeCoder.Decoder;
import java.io.IOException;

// Referenced classes of package SevenZip.Compression.LZMA:
//			Base, Decoder

class Decoder$LenDecoder {

	short m_Choice[];
	BitTreeDecoder m_LowCoder[];
	BitTreeDecoder m_MidCoder[];
	BitTreeDecoder m_HighCoder;
	int m_NumPosStates;
	final SevenZip.Compression.LZMA.Decoder this$0;

	public void Create(int numPosStates) {
		for (; m_NumPosStates < numPosStates; m_NumPosStates++) {
			m_LowCoder[m_NumPosStates] = new BitTreeDecoder(3);
			m_MidCoder[m_NumPosStates] = new BitTreeDecoder(3);
		}

	}

	public void Init() {
		Decoder.InitBitModels(m_Choice);
		for (int posState = 0; posState < m_NumPosStates; posState++) {
			m_LowCoder[posState].Init();
			m_MidCoder[posState].Init();
		}

		m_HighCoder.Init();
	}

	public int Decode(Decoder rangeDecoder, int posState) throws IOException {
		if (rangeDecoder.DecodeBit(m_Choice, 0) == 0) {
			return m_LowCoder[posState].Decode(rangeDecoder);
		}
		int symbol = 8;
		if (rangeDecoder.DecodeBit(m_Choice, 1) == 0) {
			symbol += m_MidCoder[posState].Decode(rangeDecoder);
		} else {
			symbol += 8 + m_HighCoder.Decode(rangeDecoder);
		}
		return symbol;
	}

	Decoder$LenDecoder() {
		this.this$0 = SevenZip.Compression.LZMA.Decoder.this;
		super();
		m_Choice = new short[2];
		m_LowCoder = new BitTreeDecoder[16];
		m_MidCoder = new BitTreeDecoder[16];
		m_HighCoder = new BitTreeDecoder(8);
		m_NumPosStates = 0;
	}
}
