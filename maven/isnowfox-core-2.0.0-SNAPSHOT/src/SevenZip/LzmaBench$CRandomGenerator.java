// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   LzmaBench.java

package SevenZip;


// Referenced classes of package SevenZip:
//			LzmaBench

static class LzmaBench$CRandomGenerator {

	int A1;
	int A2;

	public void Init() {
		A1 = 0x159a55e5;
		A2 = 0x1f123bb5;
	}

	public int GetRnd() {
		return (A1 = 36969 * (A1 & 0xffff) + (A1 >>> 16)) << 16 ^ (A2 = 18000 * (A2 & 0xffff) + (A2 >>> 16));
	}

	public LzmaBench$CRandomGenerator() {
		Init();
	}
}
