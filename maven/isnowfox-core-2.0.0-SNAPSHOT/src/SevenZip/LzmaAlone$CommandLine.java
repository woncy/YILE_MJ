// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   LzmaAlone.java

package SevenZip;


// Referenced classes of package SevenZip:
//			LzmaAlone

public static class LzmaAlone$CommandLine {

	public static final int kEncode = 0;
	public static final int kDecode = 1;
	public static final int kBenchmak = 2;
	public int Command;
	public int NumBenchmarkPasses;
	public int DictionarySize;
	public boolean DictionarySizeIsDefined;
	public int Lc;
	public int Lp;
	public int Pb;
	public int Fb;
	public boolean FbIsDefined;
	public boolean Eos;
	public int Algorithm;
	public int MatchFinder;
	public String InFile;
	public String OutFile;

	boolean ParseSwitch(String s) {
		if (s.startsWith("d")) {
			DictionarySize = 1 << Integer.parseInt(s.substring(1));
			DictionarySizeIsDefined = true;
		} else
		if (s.startsWith("fb")) {
			Fb = Integer.parseInt(s.substring(2));
			FbIsDefined = true;
		} else
		if (s.startsWith("a")) {
			Algorithm = Integer.parseInt(s.substring(1));
		} else
		if (s.startsWith("lc")) {
			Lc = Integer.parseInt(s.substring(2));
		} else
		if (s.startsWith("lp")) {
			Lp = Integer.parseInt(s.substring(2));
		} else
		if (s.startsWith("pb")) {
			Pb = Integer.parseInt(s.substring(2));
		} else
		if (s.startsWith("eos")) {
			Eos = true;
		} else
		if (s.startsWith("mf")) {
			String mfs = s.substring(2);
			if (mfs.equals("bt2")) {
				MatchFinder = 0;
			} else
			if (mfs.equals("bt4")) {
				MatchFinder = 1;
			} else
			if (mfs.equals("bt4b")) {
				MatchFinder = 2;
			} else {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}

	public boolean Parse(String args[]) throws Exception {
		int pos;
		boolean switchMode;
		int i;
		pos = 0;
		switchMode = true;
		i = 0;
_L3:
		if (i >= args.length) goto _L2; else goto _L1
_L1:
		String s;
		String sw;
		s = args[i];
		if (s.length() == 0) {
			return false;
		}
		if (!switchMode) {
			break MISSING_BLOCK_LABEL_99;
		}
		if (s.compareTo("--") == 0) {
			switchMode = false;
			continue; /* Loop/switch isn't completed */
		}
		if (s.charAt(0) != '-') {
			break MISSING_BLOCK_LABEL_99;
		}
		sw = s.substring(1).toLowerCase();
		if (sw.length() == 0) {
			return false;
		}
		if (!ParseSwitch(sw)) {
			return false;
		}
		continue; /* Loop/switch isn't completed */
		NumberFormatException e;
		e;
		return false;
		if (pos == 0) {
			if (s.equalsIgnoreCase("e")) {
				Command = 0;
			} else
			if (s.equalsIgnoreCase("d")) {
				Command = 1;
			} else
			if (s.equalsIgnoreCase("b")) {
				Command = 2;
			} else {
				return false;
			}
			break MISSING_BLOCK_LABEL_223;
		}
		if (pos != 1) {
			break MISSING_BLOCK_LABEL_207;
		}
		if (Command != 2) {
			break MISSING_BLOCK_LABEL_198;
		}
		NumBenchmarkPasses = Integer.parseInt(s);
		if (NumBenchmarkPasses < 1) {
			return false;
		}
		break MISSING_BLOCK_LABEL_223;
		NumberFormatException e;
		e;
		return false;
		InFile = s;
		break MISSING_BLOCK_LABEL_223;
		if (pos == 2) {
			OutFile = s;
		} else {
			return false;
		}
		pos++;
		i++;
		  goto _L3
_L2:
		return true;
	}

	public LzmaAlone$CommandLine() {
		Command = -1;
		NumBenchmarkPasses = 10;
		DictionarySize = 0x800000;
		DictionarySizeIsDefined = false;
		Lc = 3;
		Lp = 0;
		Pb = 2;
		Fb = 128;
		FbIsDefined = false;
		Eos = false;
		Algorithm = 2;
		MatchFinder = 1;
	}
}
