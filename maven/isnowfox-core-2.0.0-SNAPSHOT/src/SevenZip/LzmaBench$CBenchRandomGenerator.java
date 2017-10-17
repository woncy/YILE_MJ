// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   LzmaBench.java

package SevenZip;


// Referenced classes of package SevenZip:
//			LzmaBench

static class LzmaBench$CBenchRandomGenerator {

	LzmaBench$CBitRandomGenerator RG;
	int Pos;
	int Rep0;
	public int BufferSize;
	public byte Buffer[];

	public void Init() {
		RG.it();
		Rep0 = 1;
	}

	public void Set(int bufferSize) {
		Buffer = new byte[bufferSize];
		Pos = 0;
		BufferSize = bufferSize;
	}

	int GetRndBit() {
		return RG.tRnd(1);
	}

	int GetLogRandBits(int numBits) {
		int len = RG.tRnd(numBits);
		return RG.tRnd(len);
	}

	int GetOffset() {
		if (GetRndBit() == 0) {
			return GetLogRandBits(4);
		} else {
			return GetLogRandBits(4) << 10 | RG.tRnd(10);
		}
	}

	int GetLen1() {
		return RG.tRnd(1 + RG.tRnd(2));
	}

	int GetLen2() {
		return RG.tRnd(2 + RG.tRnd(2));
	}

	public void Generate() {
		while (Pos < BufferSize)  {
			if (GetRndBit() == 0 || Pos < 1) {
				Buffer[Pos++] = (byte)RG.tRnd(8);
			} else {
				int len;
				if (RG.tRnd(3) == 0) {
					len = 1 + GetLen1();
				} else {
					do {
						Rep0 = GetOffset();
					} while (Rep0 >= Pos);
					Rep0++;
					len = 2 + GetLen2();
				}
				int i = 0;
				while (i < len && Pos < BufferSize)  {
					Buffer[Pos] = Buffer[Pos - Rep0];
					i++;
					Pos++;
				}
			}
		}
	}

	public LzmaBench$CBenchRandomGenerator() {
		RG = new LzmaBench$CBitRandomGenerator();
		Buffer = null;
	}
}
