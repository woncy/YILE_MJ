// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   LzmaBench.java

package SevenZip;

import SevenZip.Compression.LZMA.Decoder;
import SevenZip.Compression.LZMA.Encoder;
import java.io.*;

// Referenced classes of package SevenZip:
//			CRC, ICodeProgress

public class LzmaBench {
	static class CProgressInfo
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

		CProgressInfo() {
		}
	}

	static class MyInputStream extends InputStream {

		byte _buffer[];
		int _size;
		int _pos;

		public void reset() {
			_pos = 0;
		}

		public int read() {
			if (_pos >= _size) {
				return -1;
			} else {
				return _buffer[_pos++] & 0xff;
			}
		}

		public MyInputStream(byte buffer[], int size) {
			_buffer = buffer;
			_size = size;
		}
	}

	static class MyOutputStream extends OutputStream {

		byte _buffer[];
		int _size;
		int _pos;

		public void reset() {
			_pos = 0;
		}

		public void write(int b) throws IOException {
			if (_pos >= _size) {
				throw new IOException("Error");
			} else {
				_buffer[_pos++] = (byte)b;
				return;
			}
		}

		public int size() {
			return _pos;
		}

		public MyOutputStream(byte buffer[]) {
			_buffer = buffer;
			_size = _buffer.length;
		}
	}

	static class CrcOutStream extends OutputStream {

		public CRC CRC;

		public void Init() {
			CRC.Init();
		}

		public int GetDigest() {
			return CRC.GetDigest();
		}

		public void write(byte b[]) {
			CRC.Update(b);
		}

		public void write(byte b[], int off, int len) {
			CRC.Update(b, off, len);
		}

		public void write(int b) {
			CRC.UpdateByte(b);
		}

		CrcOutStream() {
			CRC = new CRC();
		}
	}

	static class CBenchRandomGenerator {

		CBitRandomGenerator RG;
		int Pos;
		int Rep0;
		public int BufferSize;
		public byte Buffer[];

		public void Init() {
			RG.Init();
			Rep0 = 1;
		}

		public void Set(int bufferSize) {
			Buffer = new byte[bufferSize];
			Pos = 0;
			BufferSize = bufferSize;
		}

		int GetRndBit() {
			return RG.GetRnd(1);
		}

		int GetLogRandBits(int numBits) {
			int len = RG.GetRnd(numBits);
			return RG.GetRnd(len);
		}

		int GetOffset() {
			if (GetRndBit() == 0) {
				return GetLogRandBits(4);
			} else {
				return GetLogRandBits(4) << 10 | RG.GetRnd(10);
			}
		}

		int GetLen1() {
			return RG.GetRnd(1 + RG.GetRnd(2));
		}

		int GetLen2() {
			return RG.GetRnd(2 + RG.GetRnd(2));
		}

		public void Generate() {
			while (Pos < BufferSize)  {
				if (GetRndBit() == 0 || Pos < 1) {
					Buffer[Pos++] = (byte)RG.GetRnd(8);
				} else {
					int len;
					if (RG.GetRnd(3) == 0) {
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

		public CBenchRandomGenerator() {
			RG = new CBitRandomGenerator();
			Buffer = null;
		}
	}

	static class CBitRandomGenerator {

		CRandomGenerator RG;
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
				Value = RG.GetRnd();
				result |= Value & (1 << numBits) - 1;
				Value >>>= numBits;
				NumBits = 32 - numBits;
				return result;
			}
		}

		CBitRandomGenerator() {
			RG = new CRandomGenerator();
		}
	}

	static class CRandomGenerator {

		int A1;
		int A2;

		public void Init() {
			A1 = 0x159a55e5;
			A2 = 0x1f123bb5;
		}

		public int GetRnd() {
			return (A1 = 36969 * (A1 & 0xffff) + (A1 >>> 16)) << 16 ^ (A2 = 18000 * (A2 & 0xffff) + (A2 >>> 16));
		}

		public CRandomGenerator() {
			Init();
		}
	}


	static final int kAdditionalSize = 0x200000;
	static final int kCompressedAdditionalSize = 1024;
	static final int kSubBits = 8;

	public LzmaBench() {
	}

	static int GetLogSize(int size) {
		for (int i = 8; i < 32; i++) {
			for (int j = 0; j < 256; j++) {
				if (size <= (1 << i) + (j << i - 8)) {
					return (i << 8) + j;
				}
			}

		}

		return 8192;
	}

	static long MyMultDiv64(long value, long elapsedTime) {
		long freq = 1000L;
		long elTime;
		for (elTime = elapsedTime; freq > 0xf4240L; elTime >>>= 1) {
			freq >>>= 1;
		}

		if (elTime == 0L) {
			elTime = 1L;
		}
		return (value * freq) / elTime;
	}

	static long GetCompressRating(int dictionarySize, long elapsedTime, long size) {
		long t = GetLogSize(dictionarySize) - 4608;
		long numCommandsForOne = 1060L + (t * t * 10L >> 16);
		long numCommands = size * numCommandsForOne;
		return MyMultDiv64(numCommands, elapsedTime);
	}

	static long GetDecompressRating(long elapsedTime, long outSize, long inSize) {
		long numCommands = inSize * 220L + outSize * 20L;
		return MyMultDiv64(numCommands, elapsedTime);
	}

	static long GetTotalRating(int dictionarySize, long elapsedTimeEn, long sizeEn, long elapsedTimeDe, long inSizeDe, long outSizeDe) {
		return (GetCompressRating(dictionarySize, elapsedTimeEn, sizeEn) + GetDecompressRating(elapsedTimeDe, inSizeDe, outSizeDe)) / 2L;
	}

	static void PrintValue(long v) {
		String s = "";
		s = (new StringBuilder()).append(s).append(v).toString();
		for (int i = 0; i + s.length() < 6; i++) {
			System.out.print(" ");
		}

		System.out.print(s);
	}

	static void PrintRating(long rating) {
		PrintValue(rating / 0xf4240L);
		System.out.print(" MIPS");
	}

	static void PrintResults(int dictionarySize, long elapsedTime, long size, boolean decompressMode, long secondSize) {
		long speed = MyMultDiv64(size, elapsedTime);
		PrintValue(speed / 1024L);
		System.out.print(" KB/s  ");
		long rating;
		if (decompressMode) {
			rating = GetDecompressRating(elapsedTime, size, secondSize);
		} else {
			rating = GetCompressRating(dictionarySize, elapsedTime, size);
		}
		PrintRating(rating);
	}

	public static int LzmaBenchmark(int numIterations, int dictionarySize) throws Exception {
		if (numIterations <= 0) {
			return 0;
		}
		if (dictionarySize < 0x40000) {
			System.out.println("\nError: dictionary size for benchmark must be >= 18 (256 KB)");
			return 1;
		}
		System.out.print("\n       Compressing                Decompressing\n\n");
		Encoder encoder = new Encoder();
		Decoder decoder = new Decoder();
		if (!encoder.SetDictionarySize(dictionarySize)) {
			throw new Exception("Incorrect dictionary size");
		}
		int kBufferSize = dictionarySize + 0x200000;
		int kCompressedBufferSize = kBufferSize / 2 + 1024;
		ByteArrayOutputStream propStream = new ByteArrayOutputStream();
		encoder.WriteCoderProperties(propStream);
		byte propArray[] = propStream.toByteArray();
		decoder.SetDecoderProperties(propArray);
		CBenchRandomGenerator rg = new CBenchRandomGenerator();
		rg.Init();
		rg.Set(kBufferSize);
		rg.Generate();
		CRC crc = new CRC();
		crc.Init();
		crc.Update(rg.Buffer, 0, rg.BufferSize);
		CProgressInfo progressInfo = new CProgressInfo();
		progressInfo.ApprovedStart = dictionarySize;
		long totalBenchSize = 0L;
		long totalEncodeTime = 0L;
		long totalDecodeTime = 0L;
		long totalCompressedSize = 0L;
		MyInputStream inStream = new MyInputStream(rg.Buffer, rg.BufferSize);
		byte compressedBuffer[] = new byte[kCompressedBufferSize];
		MyOutputStream compressedStream = new MyOutputStream(compressedBuffer);
		CrcOutStream crcOutStream = new CrcOutStream();
		MyInputStream inputCompressedStream = null;
		int compressedSize = 0;
		for (int i = 0; i < numIterations; i++) {
			progressInfo.Init();
			inStream.reset();
			compressedStream.reset();
			encoder.Code(inStream, compressedStream, -1L, -1L, progressInfo);
			long encodeTime = System.currentTimeMillis() - progressInfo.Time;
			if (i == 0) {
				compressedSize = compressedStream.size();
				inputCompressedStream = new MyInputStream(compressedBuffer, compressedSize);
			} else
			if (compressedSize != compressedStream.size()) {
				throw new Exception("Encoding error");
			}
			if (progressInfo.InSize == 0L) {
				throw new Exception("Internal ERROR 1282");
			}
			long decodeTime = 0L;
			for (int j = 0; j < 2; j++) {
				inputCompressedStream.reset();
				crcOutStream.Init();
				long outSize = kBufferSize;
				long startTime = System.currentTimeMillis();
				if (!decoder.Code(inputCompressedStream, crcOutStream, outSize)) {
					throw new Exception("Decoding Error");
				}
				decodeTime = System.currentTimeMillis() - startTime;
				if (crcOutStream.GetDigest() != crc.GetDigest()) {
					throw new Exception("CRC Error");
				}
			}

			long benchSize = (long)kBufferSize - progressInfo.InSize;
			PrintResults(dictionarySize, encodeTime, benchSize, false, 0L);
			System.out.print("     ");
			PrintResults(dictionarySize, decodeTime, kBufferSize, true, compressedSize);
			System.out.println();
			totalBenchSize += benchSize;
			totalEncodeTime += encodeTime;
			totalDecodeTime += decodeTime;
			totalCompressedSize += compressedSize;
		}

		System.out.println("---------------------------------------------------");
		PrintResults(dictionarySize, totalEncodeTime, totalBenchSize, false, 0L);
		System.out.print("     ");
		PrintResults(dictionarySize, totalDecodeTime, (long)kBufferSize * (long)numIterations, true, totalCompressedSize);
		System.out.println("    Average");
		return 0;
	}
}
