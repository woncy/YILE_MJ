// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   LzmaBench.java

package SevenZip;


// Referenced classes of package SevenZip:
//			LzmaBench

static class LzmaBench$CBitRandomGenerator {

	LzmaBench$CRandomGenerator RG;
	int Value;
	int NumBits;

	public void Init() {
		Value = 0;
		NumBits = 0;
	}

	public int GetRnd(int numBits) {
		if (NumBits > numBits) {
			int result = Value & (1 << numBits) - 1;
			Value >>>= numBits;
			NumBits -= numBits;
			return result;
		} else {
			numBits -= NumBits;
			int result = Value << numBits;
			Value = RG.Rnd();
			result |= Value & (1 << numBits) - 1;
			Value >>>= numBits;
			NumBits = 32 - numBits;
			return result;
		}
	}

	LzmaBench$CBitRandomGenerator() {
		RG = new LzmaBench$CRandomGenerator();
	}
}
