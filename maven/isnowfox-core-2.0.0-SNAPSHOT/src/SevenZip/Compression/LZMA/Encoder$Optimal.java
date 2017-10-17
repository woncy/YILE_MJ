// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   Encoder.java

package SevenZip.Compression.LZMA;


// Referenced classes of package SevenZip.Compression.LZMA:
//			Encoder

class Encoder$Optimal {

	public int State;
	public boolean Prev1IsChar;
	public boolean Prev2;
	public int PosPrev2;
	public int BackPrev2;
	public int Price;
	public int PosPrev;
	public int BackPrev;
	public int Backs0;
	public int Backs1;
	public int Backs2;
	public int Backs3;
	final Encoder this$0;

	public void MakeAsChar() {
		BackPrev = -1;
		Prev1IsChar = false;
	}

	public void MakeAsShortRep() {
		BackPrev = 0;
		Prev1IsChar = false;
	}

	public boolean IsShortRep() {
		return BackPrev == 0;
	}

	Encoder$Optimal() {
		this.this$0 = Encoder.this;
		super();
	}
}
