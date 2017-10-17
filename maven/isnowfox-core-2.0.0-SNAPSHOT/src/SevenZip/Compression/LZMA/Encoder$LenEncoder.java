// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   Encoder.java

package SevenZip.Compression.LZMA;

import SevenZip.Compression.RangeCoder.BitTreeEncoder;
import SevenZip.Compression.RangeCoder.Encoder;
import java.io.IOException;

// Referenced classes of package SevenZip.Compression.LZMA:
//			Base, Encoder

class Encoder$LenEncoder {

	short _choice[];
	BitTreeEncoder _lowCoder[];
	BitTreeEncoder _midCoder[];
	BitTreeEncoder _highCoder;
	final SevenZip.Compression.LZMA.Encoder this$0;

	public void Init(int numPosStates) {
		Encoder.InitBitModels(_choice);
		for (int posState = 0; posState < numPosStates; posState++) {
			_lowCoder[posState].Init();
			_midCoder[posState].Init();
		}

		_highCoder.Init();
	}

	public void Encode(Encoder rangeEncoder, int symbol, int posState) throws IOException {
		if (symbol < 8) {
			rangeEncoder.Encode(_choice, 0, 0);
			_lowCoder[posState].Encode(rangeEncoder, symbol);
		} else {
			symbol -= 8;
			rangeEncoder.Encode(_choice, 0, 1);
			if (symbol < 8) {
				rangeEncoder.Encode(_choice, 1, 0);
				_midCoder[posState].Encode(rangeEncoder, symbol);
			} else {
				rangeEncoder.Encode(_choice, 1, 1);
				_highCoder.Encode(rangeEncoder, symbol - 8);
			}
		}
	}

	public void SetPrices(int posState, int numSymbols, int prices[], int st) {
		int a0 = Encoder.GetPrice0(_choice[0]);
		int a1 = Encoder.GetPrice1(_choice[0]);
		int b0 = a1 + Encoder.GetPrice0(_choice[1]);
		int b1 = a1 + Encoder.GetPrice1(_choice[1]);
		int i = 0;
		for (i = 0; i < 8; i++) {
			if (i >= numSymbols) {
				return;
			}
			prices[st + i] = a0 + _lowCoder[posState].GetPrice(i);
		}

		for (; i < 16; i++) {
			if (i >= numSymbols) {
				return;
			}
			prices[st + i] = b0 + _midCoder[posState].GetPrice(i - 8);
		}

		for (; i < numSymbols; i++) {
			prices[st + i] = b1 + _highCoder.GetPrice(i - 8 - 8);
		}

	}

	public Encoder$LenEncoder() {
		this.this$0 = SevenZip.Compression.LZMA.Encoder.this;
		super();
		_choice = new short[2];
		_lowCoder = new BitTreeEncoder[16];
		_midCoder = new BitTreeEncoder[16];
		_highCoder = new BitTreeEncoder(8);
		for (int posState = 0; posState < 16; posState++) {
			_lowCoder[posState] = new BitTreeEncoder(3);
			_midCoder[posState] = new BitTreeEncoder(3);
		}

	}
}
