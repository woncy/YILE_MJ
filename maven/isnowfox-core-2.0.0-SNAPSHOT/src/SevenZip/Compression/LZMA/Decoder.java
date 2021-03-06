// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   Decoder.java

package SevenZip.Compression.LZMA;

import SevenZip.Compression.LZ.OutWindow;
import SevenZip.Compression.RangeCoder.BitTreeDecoder;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

// Referenced classes of package SevenZip.Compression.LZMA:
//			Base

public class Decoder {
	class LiteralDecoder {

		Decoder2 m_Coders[];
		int m_NumPrevBits;
		int m_NumPosBits;
		int m_PosMask;
		final Decoder this$0;

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

		LiteralDecoder() {
			this.this$0 = Decoder.this;
			super();
		}
	}

	class LenDecoder {

		short m_Choice[];
		BitTreeDecoder m_LowCoder[];
		BitTreeDecoder m_MidCoder[];
		BitTreeDecoder m_HighCoder;
		int m_NumPosStates;
		final Decoder this$0;

		public void Create(int numPosStates) {
			for (; m_NumPosStates < numPosStates; m_NumPosStates++) {
				m_LowCoder[m_NumPosStates] = new BitTreeDecoder(3);
				m_MidCoder[m_NumPosStates] = new BitTreeDecoder(3);
			}

		}

		public void Init() {
			SevenZip.Compression.RangeCoder.Decoder.InitBitModels(m_Choice);
			for (int posState = 0; posState < m_NumPosStates; posState++) {
				m_LowCoder[posState].Init();
				m_MidCoder[posState].Init();
			}

			m_HighCoder.Init();
		}

		public int Decode(SevenZip.Compression.RangeCoder.Decoder rangeDecoder, int posState) throws IOException {
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

		LenDecoder() {
			this.this$0 = Decoder.this;
			super();
			m_Choice = new short[2];
			m_LowCoder = new BitTreeDecoder[16];
			m_MidCoder = new BitTreeDecoder[16];
			m_HighCoder = new BitTreeDecoder(8);
			m_NumPosStates = 0;
		}
	}


	OutWindow m_OutWindow;
	SevenZip.Compression.RangeCoder.Decoder m_RangeDecoder;
	short m_IsMatchDecoders[];
	short m_IsRepDecoders[];
	short m_IsRepG0Decoders[];
	short m_IsRepG1Decoders[];
	short m_IsRepG2Decoders[];
	short m_IsRep0LongDecoders[];
	BitTreeDecoder m_PosSlotDecoder[];
	short m_PosDecoders[];
	BitTreeDecoder m_PosAlignDecoder;
	LenDecoder m_LenDecoder;
	LenDecoder m_RepLenDecoder;
	LiteralDecoder m_LiteralDecoder;
	int m_DictionarySize;
	int m_DictionarySizeCheck;
	int m_PosStateMask;

	public Decoder() {
		m_OutWindow = new OutWindow();
		m_RangeDecoder = new SevenZip.Compression.RangeCoder.Decoder();
		m_IsMatchDecoders = new short[192];
		m_IsRepDecoders = new short[12];
		m_IsRepG0Decoders = new short[12];
		m_IsRepG1Decoders = new short[12];
		m_IsRepG2Decoders = new short[12];
		m_IsRep0LongDecoders = new short[192];
		m_PosSlotDecoder = new BitTreeDecoder[4];
		m_PosDecoders = new short[114];
		m_PosAlignDecoder = new BitTreeDecoder(4);
		m_LenDecoder = new LenDecoder();
		m_RepLenDecoder = new LenDecoder();
		m_LiteralDecoder = new LiteralDecoder();
		m_DictionarySize = -1;
		m_DictionarySizeCheck = -1;
		for (int i = 0; i < 4; i++) {
			m_PosSlotDecoder[i] = new BitTreeDecoder(6);
		}

	}

	boolean SetDictionarySize(int dictionarySize) {
		if (dictionarySize < 0) {
			return false;
		}
		if (m_DictionarySize != dictionarySize) {
			m_DictionarySize = dictionarySize;
			m_DictionarySizeCheck = Math.max(m_DictionarySize, 1);
			m_OutWindow.Create(Math.max(m_DictionarySizeCheck, 4096));
		}
		return true;
	}

	boolean SetLcLpPb(int lc, int lp, int pb) {
		if (lc > 8 || lp > 4 || pb > 4) {
			return false;
		} else {
			m_LiteralDecoder.Create(lp, lc);
			int numPosStates = 1 << pb;
			m_LenDecoder.Create(numPosStates);
			m_RepLenDecoder.Create(numPosStates);
			m_PosStateMask = numPosStates - 1;
			return true;
		}
	}

	void Init() throws IOException {
		m_OutWindow.Init(false);
		SevenZip.Compression.RangeCoder.Decoder.InitBitModels(m_IsMatchDecoders);
		SevenZip.Compression.RangeCoder.Decoder.InitBitModels(m_IsRep0LongDecoders);
		SevenZip.Compression.RangeCoder.Decoder.InitBitModels(m_IsRepDecoders);
		SevenZip.Compression.RangeCoder.Decoder.InitBitModels(m_IsRepG0Decoders);
		SevenZip.Compression.RangeCoder.Decoder.InitBitModels(m_IsRepG1Decoders);
		SevenZip.Compression.RangeCoder.Decoder.InitBitModels(m_IsRepG2Decoders);
		SevenZip.Compression.RangeCoder.Decoder.InitBitModels(m_PosDecoders);
		m_LiteralDecoder.Init();
		for (int i = 0; i < 4; i++) {
			m_PosSlotDecoder[i].Init();
		}

		m_LenDecoder.Init();
		m_RepLenDecoder.Init();
		m_PosAlignDecoder.Init();
		m_RangeDecoder.Init();
	}

	public boolean Code(InputStream inStream, OutputStream outStream, long outSize) throws IOException {
		m_RangeDecoder.SetStream(inStream);
		m_OutWindow.SetStream(outStream);
		Init();
		int state = Base.StateInit();
		int rep0 = 0;
		int rep1 = 0;
		int rep2 = 0;
		int rep3 = 0;
		long nowPos64 = 0L;
		byte prevByte = 0;
		do {
			if (outSize >= 0L && nowPos64 >= outSize) {
				break;
			}
			int posState = (int)nowPos64 & m_PosStateMask;
			if (m_RangeDecoder.DecodeBit(m_IsMatchDecoders, (state << 4) + posState) == 0) {
				LiteralDecoder.Decoder2 decoder2 = m_LiteralDecoder.GetDecoder((int)nowPos64, prevByte);
				if (!Base.StateIsCharState(state)) {
					prevByte = decoder2.DecodeWithMatchByte(m_RangeDecoder, m_OutWindow.GetByte(rep0));
				} else {
					prevByte = decoder2.DecodeNormal(m_RangeDecoder);
				}
				m_OutWindow.PutByte(prevByte);
				state = Base.StateUpdateChar(state);
				nowPos64++;
				continue;
			}
			int len;
			if (m_RangeDecoder.DecodeBit(m_IsRepDecoders, state) == 1) {
				len = 0;
				if (m_RangeDecoder.DecodeBit(m_IsRepG0Decoders, state) == 0) {
					if (m_RangeDecoder.DecodeBit(m_IsRep0LongDecoders, (state << 4) + posState) == 0) {
						state = Base.StateUpdateShortRep(state);
						len = 1;
					}
				} else {
					int distance;
					if (m_RangeDecoder.DecodeBit(m_IsRepG1Decoders, state) == 0) {
						distance = rep1;
					} else {
						if (m_RangeDecoder.DecodeBit(m_IsRepG2Decoders, state) == 0) {
							distance = rep2;
						} else {
							distance = rep3;
							rep3 = rep2;
						}
						rep2 = rep1;
					}
					rep1 = rep0;
					rep0 = distance;
				}
				if (len == 0) {
					len = m_RepLenDecoder.Decode(m_RangeDecoder, posState) + 2;
					state = Base.StateUpdateRep(state);
				}
			} else {
				rep3 = rep2;
				rep2 = rep1;
				rep1 = rep0;
				len = 2 + m_LenDecoder.Decode(m_RangeDecoder, posState);
				state = Base.StateUpdateMatch(state);
				int posSlot = m_PosSlotDecoder[Base.GetLenToPosState(len)].Decode(m_RangeDecoder);
				if (posSlot >= 4) {
					int numDirectBits = (posSlot >> 1) - 1;
					rep0 = (2 | posSlot & 1) << numDirectBits;
					if (posSlot < 14) {
						rep0 += BitTreeDecoder.ReverseDecode(m_PosDecoders, rep0 - posSlot - 1, m_RangeDecoder, numDirectBits);
					} else {
						rep0 += m_RangeDecoder.DecodeDirectBits(numDirectBits - 4) << 4;
						rep0 += m_PosAlignDecoder.ReverseDecode(m_RangeDecoder);
						if (rep0 < 0) {
							if (rep0 != -1) {
								return false;
							}
							break;
						}
					}
				} else {
					rep0 = posSlot;
				}
			}
			if ((long)rep0 >= nowPos64 || rep0 >= m_DictionarySizeCheck) {
				return false;
			}
			m_OutWindow.CopyBlock(rep0, len);
			nowPos64 += len;
			prevByte = m_OutWindow.GetByte(0);
		} while (true);
		m_OutWindow.Flush();
		m_OutWindow.ReleaseStream();
		m_RangeDecoder.ReleaseStream();
		return true;
	}

	public boolean SetDecoderProperties(byte properties[]) {
		if (properties.length < 5) {
			return false;
		}
		int val = properties[0] & 0xff;
		int lc = val % 9;
		int remainder = val / 9;
		int lp = remainder % 5;
		int pb = remainder / 5;
		int dictionarySize = 0;
		for (int i = 0; i < 4; i++) {
			dictionarySize += (properties[1 + i] & 0xff) << i * 8;
		}

		if (!SetLcLpPb(lc, lp, pb)) {
			return false;
		} else {
			return SetDictionarySize(dictionarySize);
		}
	}
}
