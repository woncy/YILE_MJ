// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   Encoder.java

package SevenZip.Compression.LZMA;

import SevenZip.Compression.RangeCoder.Encoder;
import java.io.IOException;

// Referenced classes of package SevenZip.Compression.LZMA:
//			Base, Encoder

class Encoder$LenPriceTableEncoder extends Encoder$LenEncoder {

	int _prices[];
	int _tableSize;
	int _counters[];
	final SevenZip.Compression.LZMA.Encoder this$0;

	public void SetTableSize(int tableSize) {
		_tableSize = tableSize;
	}

	public int GetPrice(int symbol, int posState) {
		return _prices[posState * 272 + symbol];
	}

	void UpdateTable(int posState) {
		SetPrices(posState, _tableSize, _prices, posState * 272);
		_counters[posState] = _tableSize;
	}

	public void UpdateTables(int numPosStates) {
		for (int posState = 0; posState < numPosStates; posState++) {
			UpdateTable(posState);
		}

	}

	public void Encode(Encoder rangeEncoder, int symbol, int posState) throws IOException {
		super.Encode(rangeEncoder, symbol, posState);
		if (--_counters[posState] == 0) {
			UpdateTable(posState);
		}
	}

	Encoder$LenPriceTableEncoder() {
		this.this$0 = SevenZip.Compression.LZMA.Encoder.this;
		super(SevenZip.Compression.LZMA.Encoder.this);
		_prices = new int[4352];
		_counters = new int[16];
	}
}
