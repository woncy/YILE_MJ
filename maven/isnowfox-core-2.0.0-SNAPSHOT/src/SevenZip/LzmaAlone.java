// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   LzmaAlone.java

package SevenZip;

import SevenZip.Compression.LZMA.Decoder;
import SevenZip.Compression.LZMA.Encoder;
import java.io.*;

// Referenced classes of package SevenZip:
//			LzmaBench

public class LzmaAlone {
	public static class CommandLine {

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

		public CommandLine() {
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


	public LzmaAlone() {
	}

	static void PrintHelp() {
		System.out.println("\nUsage:  LZMA <e|d> [<switches>...] inputFile outputFile\n  e: encode file\n  d: decode file\n  b: Benchmark\n<Switches>\n  -d{N}:  set dictionary - [0,28], default: 23 (8MB)\n  -fb{N}: set number of fast bytes - [5, 273], default: 128\n  -lc{N}: set number of literal context bits - [0, 8], default: 3\n  -lp{N}: set number of literal pos bits - [0, 4], default: 0\n  -pb{N}: set number of pos bits - [0, 4], default: 2\n  -mf{MF_ID}: set Match Finder: [bt2, bt4], default: bt4\n  -eos:   write End Of Stream marker\n");
	}

	public static void main(String args[]) throws Exception {
		System.out.println("\nLZMA (Java) 4.33 Copyright (c) 1999-2006 Igor Pavlov  2006-02-05\n");
		if (args.length < 1) {
			PrintHelp();
			return;
		}
		CommandLine params = new CommandLine();
		if (!params.Parse(args)) {
			System.out.println("\nIncorrect command");
			return;
		}
		if (params.Command == 2) {
			int dictionary = 0x200000;
			if (params.DictionarySizeIsDefined) {
				dictionary = params.DictionarySize;
			}
			if (params.MatchFinder > 1) {
				throw new Exception("Unsupported match finder");
			}
			LzmaBench.LzmaBenchmark(params.NumBenchmarkPasses, dictionary);
		} else
		if (params.Command == 0 || params.Command == 1) {
			File inFile = new File(params.InFile);
			File outFile = new File(params.OutFile);
			BufferedInputStream inStream = new BufferedInputStream(new FileInputStream(inFile));
			BufferedOutputStream outStream = new BufferedOutputStream(new FileOutputStream(outFile));
			boolean eos = false;
			if (params.Eos) {
				eos = true;
			}
			if (params.Command == 0) {
				Encoder encoder = new Encoder();
				if (!encoder.SetAlgorithm(params.Algorithm)) {
					throw new Exception("Incorrect compression mode");
				}
				if (!encoder.SetDictionarySize(params.DictionarySize)) {
					throw new Exception("Incorrect dictionary size");
				}
				if (!encoder.SeNumFastBytes(params.Fb)) {
					throw new Exception("Incorrect -fb value");
				}
				if (!encoder.SetMatchFinder(params.MatchFinder)) {
					throw new Exception("Incorrect -mf value");
				}
				if (!encoder.SetLcLpPb(params.Lc, params.Lp, params.Pb)) {
					throw new Exception("Incorrect -lc or -lp or -pb value");
				}
				encoder.SetEndMarkerMode(eos);
				encoder.WriteCoderProperties(outStream);
				long fileSize;
				if (eos) {
					fileSize = -1L;
				} else {
					fileSize = inFile.length();
				}
				for (int i = 0; i < 8; i++) {
					outStream.write((int)(fileSize >>> 8 * i) & 0xff);
				}

				encoder.Code(inStream, outStream, -1L, -1L, null);
			} else {
				int propertiesSize = 5;
				byte properties[] = new byte[propertiesSize];
				if (inStream.read(properties, 0, propertiesSize) != propertiesSize) {
					throw new Exception("input .lzma file is too short");
				}
				Decoder decoder = new Decoder();
				if (!decoder.SetDecoderProperties(properties)) {
					throw new Exception("Incorrect stream properties");
				}
				long outSize = 0L;
				for (int i = 0; i < 8; i++) {
					int v = inStream.read();
					if (v < 0) {
						throw new Exception("Can't read stream size");
					}
					outSize |= (long)v << 8 * i;
				}

				if (!decoder.Code(inStream, outStream, outSize)) {
					throw new Exception("Error in data stream");
				}
			}
			outStream.flush();
			outStream.close();
			inStream.close();
		} else {
			throw new Exception("Incorrect command");
		}
	}
}
