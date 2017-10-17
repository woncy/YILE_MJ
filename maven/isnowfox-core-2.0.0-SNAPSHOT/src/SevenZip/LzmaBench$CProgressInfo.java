// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   LzmaBench.java

package SevenZip;


// Referenced classes of package SevenZip:
//			ICodeProgress, LzmaBench

static class LzmaBench$CProgressInfo
	implements ICodeProgress {

	public long ApprovedStart;
	public long InSize;
	public long Time;

	public void Init() {
		InSize = 0L;
	}

	public void SetProgress(long inSize, long outSize) {
		if (inSize >= ApprovedStart && InSize == 0L) {
			Time = System.currentTimeMillis();
			InSize = inSize;
		}
	}

	LzmaBench$CProgressInfo() {
	}
}
