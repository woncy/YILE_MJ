// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   Encoder.java

package SevenZip.Compression.LZMA;

import SevenZip.Compression.LZ.BinTree;
import SevenZip.Compression.RangeCoder.BitTreeEncoder;
import SevenZip.ICodeProgress;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

// Referenced classes of package SevenZip.Compression.LZMA:
//			Base

public class Encoder {
	class Optimal {

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

		Optimal() {
			this.this$0 = Encoder.this;
			super();
		}
	}

	class LenPriceTableEncoder extends LenEncoder {

		int _prices[];
		int _tableSize;
		int _counters[];
		final Encoder this$0;

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

		public void Encode(SevenZip.Compression.RangeCoder.Encoder rangeEncoder, int symbol, int posState) throws IOException {
			super.Encode(rangeEncoder, symbol, posState);
			if (--_counters[posState] == 0) {
				UpdateTable(posState);
			}
		}

		LenPriceTableEncoder() {
			this.this$0 = Encoder.this;
			super();
			_prices = new int[4352];
			_counters = new int[16];
		}
	}

	class LenEncoder {

		short _choice[];
		BitTreeEncoder _lowCoder[];
		BitTreeEncoder _midCoder[];
		BitTreeEncoder _highCoder;
		final Encoder this$0;

		public void Init(int numPosStates) {
			SevenZip.Compression.RangeCoder.Encoder.InitBitModels(_choice);
			for (int posState = 0; posState < numPosStates; posState++) {
				_lowCoder[posState].Init();
				_midCoder[posState].Init();
			}

			_highCoder.Init();
		}

		public void Encode(SevenZip.Compression.RangeCoder.Encoder rangeEncoder, int symbol, int posState) throws IOException {
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
			int a0 = SevenZip.Compression.RangeCoder.Encoder.GetPrice0(_choice[0]);
			int a1 = SevenZip.Compression.RangeCoder.Encoder.GetPrice1(_choice[0]);
			int b0 = a1 + SevenZip.Compression.RangeCoder.Encoder.GetPrice0(_choice[1]);
			int b1 = a1 + SevenZip.Compression.RangeCoder.Encoder.GetPrice1(_choice[1]);
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

		public LenEncoder() {
			this.this$0 = Encoder.this;
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

	class LiteralEncoder {

		Encoder2 m_Coders[];
		int m_NumPrevBits;
		int m_NumPosBits;
		int m_PosMask;
		final Encoder this$0;

		public void Create(int numPosBits, int numPrevBits) {
			if (m_Coders != null && m_NumPrevBits == numPrevBits && m_NumPosBits == numPosBits) {
				return;
			}
			m_NumPosBits = numPosBits;
			m_PosMask = (1 << numPosBits) - 1;
			m_NumPrevBits = numPrevBits;
			int numStates = 1 << m_NumPrevBits + m_NumPosBits;
			m_Coders = new Encoder2[numStates];
			for (int i = 0; i < numStates; i++) {
				m_Coders[i] = new Encoder2();
			}

		}

		public void Init() {
			int numStates = 1 << m_NumPrevBits + m_NumPosBits;
			for (int i = 0; i < numStates; i++) {
				m_Coders[i].Init();
			}

		}

		public Encoder2 GetSubCoder(int pos, byte prevByte) {
			return m_Coders[((pos & m_PosMask) << m_NumPrevBits) + ((prevByte & 0xff) >>> 8 - m_NumPrevBits)];
		}

		LiteralEncoder() {
			this.this$0 = Encoder.this;
			super();
		}
	}


	public static final int EMatchFinderTypeBT2 = 0;
	public static final int EMatchFinderTypeBT4 = 1;
	static final int kIfinityPrice = 0xfffffff;
	static byte g_FastPos[];
	int _state;
	byte _previousByte;
	int _repDistances[];
	static final int kDefaultDictionaryLogSize = 22;
	static final int kNumFastBytesDefault = 32;
	public static final int kNumLenSpecSymbols = 16;
	static final int kNumOpts = 4096;
	Optimal _optimum[];
	BinTree _matchFinder;
	SevenZip.Compression.RangeCoder.Encoder _rangeEncoder;
	short _isMatch[];
	short _isRep[];
	short _isRepG0[];
	short _isRepG1[];
	short _isRepG2[];
	short _isRep0Long[];
	BitTreeEncoder _posSlotEncoder[];
	short _posEncoders[];
	BitTreeEncoder _posAlignEncoder;
	LenPriceTableEncoder _lenEncoder;
	LenPriceTableEncoder _repMatchLenEncoder;
	LiteralEncoder _literalEncoder;
	int _matchDistances[];
	int _numFastBytes;
	int _longestMatchLength;
	int _numDistancePairs;
	int _additionalOffset;
	int _optimumEndIndex;
	int _optimumCurrentIndex;
	boolean _longestMatchWasFound;
	int _posSlotPrices[];
	int _distancesPrices[];
	int _alignPrices[];
	int _alignPriceCount;
	int _distTableSize;
	int _posStateBits;
	int _posStateMask;
	int _numLiteralPosStateBits;
	int _numLiteralContextBits;
	int _dictionarySize;
	int _dictionarySizePrev;
	int _numFastBytesPrev;
	long nowPos64;
	boolean _finished;
	InputStream _inStream;
	int _matchFinderType;
	boolean _writeEndMark;
	boolean _needReleaseMFStream;
	int reps[];
	int repLens[];
	int backRes;
	long processedInSize[];
	long processedOutSize[];
	boolean finished[];
	public static final int kPropSize = 5;
	byte properties[];
	int tempPrices[];
	int _matchPriceCount;

	static int GetPosSlot(int pos) {
		if (pos < 2048) {
			return g_FastPos[pos];
		}
		if (pos < 0x200000) {
			return g_FastPos[pos >> 10] + 20;
		} else {
			return g_FastPos[pos >> 20] + 40;
		}
	}

	static int GetPosSlot2(int pos) {
		if (pos < 0x20000) {
			return g_FastPos[pos >> 6] + 12;
		}
		if (pos < 0x8000000) {
			return g_FastPos[pos >> 16] + 32;
		} else {
			return g_FastPos[pos >> 26] + 52;
		}
	}

	void BaseInit() {
		_state = Base.StateInit();
		_previousByte = 0;
		for (int i = 0; i < 4; i++) {
			_repDistances[i] = 0;
		}

	}

	void Create() {
		if (_matchFinder == null) {
			BinTree bt = new BinTree();
			int numHashBytes = 4;
			if (_matchFinderType == 0) {
				numHashBytes = 2;
			}
			bt.SetType(numHashBytes);
			_matchFinder = bt;
		}
		_literalEncoder.Create(_numLiteralPosStateBits, _numLiteralContextBits);
		if (_dictionarySize == _dictionarySizePrev && _numFastBytesPrev == _numFastBytes) {
			return;
		} else {
			_matchFinder.Create(_dictionarySize, 4096, _numFastBytes, 274);
			_dictionarySizePrev = _dictionarySize;
			_numFastBytesPrev = _numFastBytes;
			return;
		}
	}

	public Encoder() {
		_state = Base.StateInit();
		_repDistances = new int[4];
		_optimum = new Optimal[4096];
		_matchFinder = null;
		_rangeEncoder = new SevenZip.Compression.RangeCoder.Encoder();
		_isMatch = new short[192];
		_isRep = new short[12];
		_isRepG0 = new short[12];
		_isRepG1 = new short[12];
		_isRepG2 = new short[12];
		_isRep0Long = new short[192];
		_posSlotEncoder = new BitTreeEncoder[4];
		_posEncoders = new short[114];
		_posAlignEncoder = new BitTreeEncoder(4);
		_lenEncoder = new LenPriceTableEncoder();
		_repMatchLenEncoder = new LenPriceTableEncoder();
		_literalEncoder = new LiteralEncoder();
		_matchDistances = new int[548];
		_numFastBytes = 32;
		_posSlotPrices = new int[256];
		_distancesPrices = new int[512];
		_alignPrices = new int[16];
		_distTableSize = 44;
		_posStateBits = 2;
		_posStateMask = 3;
		_numLiteralPosStateBits = 0;
		_numLiteralContextBits = 3;
		_dictionarySize = 0x400000;
		_dictionarySizePrev = -1;
		_numFastBytesPrev = -1;
		_matchFinderType = 1;
		_writeEndMark = false;
		_needReleaseMFStream = false;
		reps = new int[4];
		repLens = new int[4];
		processedInSize = new long[1];
		processedOutSize = new long[1];
		finished = new boolean[1];
		properties = new byte[5];
		tempPrices = new int[128];
		for (int i = 0; i < 4096; i++) {
			_optimum[i] = new Optimal();
		}

		for (int i = 0; i < 4; i++) {
			_posSlotEncoder[i] = new BitTreeEncoder(6);
		}

	}

	void SetWriteEndMarkerMode(boolean writeEndMarker) {
		_writeEndMark = writeEndMarker;
	}

	void Init() {
		BaseInit();
		_rangeEncoder.Init();
		SevenZip.Compression.RangeCoder.Encoder.InitBitModels(_isMatch);
		SevenZip.Compression.RangeCoder.Encoder.InitBitModels(_isRep0Long);
		SevenZip.Compression.RangeCoder.Encoder.InitBitModels(_isRep);
		SevenZip.Compression.RangeCoder.Encoder.InitBitModels(_isRepG0);
		SevenZip.Compression.RangeCoder.Encoder.InitBitModels(_isRepG1);
		SevenZip.Compression.RangeCoder.Encoder.InitBitModels(_isRepG2);
		SevenZip.Compression.RangeCoder.Encoder.InitBitModels(_posEncoders);
		_literalEncoder.Init();
		for (int i = 0; i < 4; i++) {
			_posSlotEncoder[i].Init();
		}

		_lenEncoder.Init(1 << _posStateBits);
		_repMatchLenEncoder.Init(1 << _posStateBits);
		_posAlignEncoder.Init();
		_longestMatchWasFound = false;
		_optimumEndIndex = 0;
		_optimumCurrentIndex = 0;
		_additionalOffset = 0;
	}

	int ReadMatchDistances() throws IOException {
		int lenRes = 0;
		_numDistancePairs = _matchFinder.GetMatches(_matchDistances);
		if (_numDistancePairs > 0) {
			lenRes = _matchDistances[_numDistancePairs - 2];
			if (lenRes == _numFastBytes) {
				lenRes += _matchFinder.GetMatchLen(lenRes - 1, _matchDistances[_numDistancePairs - 1], 273 - lenRes);
			}
		}
		_additionalOffset++;
		return lenRes;
	}

	void MovePos(int num) throws IOException {
		if (num > 0) {
			_matchFinder.Skip(num);
			_additionalOffset += num;
		}
	}

	int GetRepLen1Price(int state, int posState) {
		return SevenZip.Compression.RangeCoder.Encoder.GetPrice0(_isRepG0[state]) + SevenZip.Compression.RangeCoder.Encoder.GetPrice0(_isRep0Long[(state << 4) + posState]);
	}

	int GetPureRepPrice(int repIndex, int state, int posState) {
		int price;
		if (repIndex == 0) {
			price = SevenZip.Compression.RangeCoder.Encoder.GetPrice0(_isRepG0[state]);
			price += SevenZip.Compression.RangeCoder.Encoder.GetPrice1(_isRep0Long[(state << 4) + posState]);
		} else {
			price = SevenZip.Compression.RangeCoder.Encoder.GetPrice1(_isRepG0[state]);
			if (repIndex == 1) {
				price += SevenZip.Compression.RangeCoder.Encoder.GetPrice0(_isRepG1[state]);
			} else {
				price += SevenZip.Compression.RangeCoder.Encoder.GetPrice1(_isRepG1[state]);
				price += SevenZip.Compression.RangeCoder.Encoder.GetPrice(_isRepG2[state], repIndex - 2);
			}
		}
		return price;
	}

	int GetRepPrice(int repIndex, int len, int state, int posState) {
		int price = _repMatchLenEncoder.GetPrice(len - 2, posState);
		return price + GetPureRepPrice(repIndex, state, posState);
	}

	int GetPosLenPrice(int pos, int len, int posState) {
		int lenToPosState = Base.GetLenToPosState(len);
		int price;
		if (pos < 128) {
			price = _distancesPrices[lenToPosState * 128 + pos];
		} else {
			price = _posSlotPrices[(lenToPosState << 6) + GetPosSlot2(pos)] + _alignPrices[pos & 0xf];
		}
		return price + _lenEncoder.GetPrice(len - 2, posState);
	}

	int Backward(int cur) {
		_optimumEndIndex = cur;
		int posMem = _optimum[cur].PosPrev;
		int backMem = _optimum[cur].BackPrev;
		do {
			if (_optimum[cur].Prev1IsChar) {
				_optimum[posMem].MakeAsChar();
				_optimum[posMem].PosPrev = posMem - 1;
				if (_optimum[cur].Prev2) {
					_optimum[posMem - 1].Prev1IsChar = false;
					_optimum[posMem - 1].PosPrev = _optimum[cur].PosPrev2;
					_optimum[posMem - 1].BackPrev = _optimum[cur].BackPrev2;
				}
			}
			int posPrev = posMem;
			int backCur = backMem;
			backMem = _optimum[posPrev].BackPrev;
			posMem = _optimum[posPrev].PosPrev;
			_optimum[posPrev].BackPrev = backCur;
			_optimum[posPrev].PosPrev = cur;
			cur = posPrev;
		} while (cur > 0);
		backRes = _optimum[0].BackPrev;
		_optimumCurrentIndex = _optimum[0].PosPrev;
		return _optimumCurrentIndex;
	}

	int GetOptimum(int position) throws IOException {
		int numDistancePairs;
		int posState;
		int lenEnd;
		int normalMatchPrice;
		int cur;
		if (_optimumEndIndex != _optimumCurrentIndex) {
			int lenRes = _optimum[_optimumCurrentIndex].PosPrev - _optimumCurrentIndex;
			backRes = _optimum[_optimumCurrentIndex].BackPrev;
			_optimumCurrentIndex = _optimum[_optimumCurrentIndex].PosPrev;
			return lenRes;
		}
		_optimumCurrentIndex = _optimumEndIndex = 0;
		int lenMain;
		if (!_longestMatchWasFound) {
			lenMain = ReadMatchDistances();
		} else {
			lenMain = _longestMatchLength;
			_longestMatchWasFound = false;
		}
		numDistancePairs = _numDistancePairs;
		int numAvailableBytes = _matchFinder.GetNumAvailableBytes() + 1;
		if (numAvailableBytes < 2) {
			backRes = -1;
			return 1;
		}
		if (numAvailableBytes > 273) {
			numAvailableBytes = 273;
		}
		int repMaxIndex = 0;
		for (int i = 0; i < 4; i++) {
			reps[i] = _repDistances[i];
			repLens[i] = _matchFinder.GetMatchLen(-1, reps[i], 273);
			if (repLens[i] > repLens[repMaxIndex]) {
				repMaxIndex = i;
			}
		}

		if (repLens[repMaxIndex] >= _numFastBytes) {
			backRes = repMaxIndex;
			int lenRes = repLens[repMaxIndex];
			MovePos(lenRes - 1);
			return lenRes;
		}
		if (lenMain >= _numFastBytes) {
			backRes = _matchDistances[numDistancePairs - 1] + 4;
			MovePos(lenMain - 1);
			return lenMain;
		}
		byte currentByte = _matchFinder.GetIndexByte(-1);
		byte matchByte = _matchFinder.GetIndexByte(0 - _repDistances[0] - 1 - 1);
		if (lenMain < 2 && currentByte != matchByte && repLens[repMaxIndex] < 2) {
			backRes = -1;
			return 1;
		}
		_optimum[0].State = _state;
		posState = position & _posStateMask;
		_optimum[1].Price = SevenZip.Compression.RangeCoder.Encoder.GetPrice0(_isMatch[(_state << 4) + posState]) + _literalEncoder.GetSubCoder(position, _previousByte).GetPrice(!Base.StateIsCharState(_state), matchByte, currentByte);
		_optimum[1].MakeAsChar();
		int matchPrice = SevenZip.Compression.RangeCoder.Encoder.GetPrice1(_isMatch[(_state << 4) + posState]);
		int repMatchPrice = matchPrice + SevenZip.Compression.RangeCoder.Encoder.GetPrice1(_isRep[_state]);
		if (matchByte == currentByte) {
			int shortRepPrice = repMatchPrice + GetRepLen1Price(_state, posState);
			if (shortRepPrice < _optimum[1].Price) {
				_optimum[1].Price = shortRepPrice;
				_optimum[1].MakeAsShortRep();
			}
		}
		lenEnd = lenMain < repLens[repMaxIndex] ? repLens[repMaxIndex] : lenMain;
		if (lenEnd < 2) {
			backRes = _optimum[1].BackPrev;
			return 1;
		}
		_optimum[1].PosPrev = 0;
		_optimum[0].Backs0 = reps[0];
		_optimum[0].Backs1 = reps[1];
		_optimum[0].Backs2 = reps[2];
		_optimum[0].Backs3 = reps[3];
		int len = lenEnd;
		do {
			_optimum[len--].Price = 0xfffffff;
		} while (len >= 2);
		for (int i = 0; i < 4; i++) {
			int repLen = repLens[i];
			if (repLen < 2) {
				continue;
			}
			int price = repMatchPrice + GetPureRepPrice(i, _state, posState);
			do {
				int curAndLenPrice = price + _repMatchLenEncoder.GetPrice(repLen - 2, posState);
				Optimal optimum = _optimum[repLen];
				if (curAndLenPrice < optimum.Price) {
					optimum.Price = curAndLenPrice;
					optimum.PosPrev = 0;
					optimum.BackPrev = i;
					optimum.Prev1IsChar = false;
				}
			} while (--repLen >= 2);
		}

		normalMatchPrice = matchPrice + SevenZip.Compression.RangeCoder.Encoder.GetPrice0(_isRep[_state]);
		len = repLens[0] < 2 ? 2 : repLens[0] + 1;
		if (len <= lenMain) {
			int offs;
			for (offs = 0; len > _matchDistances[offs]; offs += 2) { }
			do {
				int distance = _matchDistances[offs + 1];
				int curAndLenPrice = normalMatchPrice + GetPosLenPrice(distance, len, posState);
				Optimal optimum = _optimum[len];
				if (curAndLenPrice < optimum.Price) {
					optimum.Price = curAndLenPrice;
					optimum.PosPrev = 0;
					optimum.BackPrev = distance + 4;
					optimum.Prev1IsChar = false;
				}
				if (len == _matchDistances[offs] && (offs += 2) == numDistancePairs) {
					break;
				}
				len++;
			} while (true);
		}
		cur = 0;
_L1:
		int state;
		int numAvailableBytesFull;
		int offs;
		int lenTest;
		int matchPrice;
		int newLen;
		int startLen;
		do {
			int numAvailableBytes;
			byte currentByte;
			byte matchByte;
			int repMatchPrice;
			int curAnd1Price;
			boolean nextIsChar;
			do {
				if (++cur == lenEnd) {
					return Backward(cur);
				}
				newLen = ReadMatchDistances();
				numDistancePairs = _numDistancePairs;
				if (newLen >= _numFastBytes) {
					_longestMatchLength = newLen;
					_longestMatchWasFound = true;
					return Backward(cur);
				}
				position++;
				int posPrev = _optimum[cur].PosPrev;
				if (_optimum[cur].Prev1IsChar) {
					posPrev--;
					if (_optimum[cur].Prev2) {
						state = _optimum[_optimum[cur].PosPrev2].State;
						if (_optimum[cur].BackPrev2 < 4) {
							state = Base.StateUpdateRep(state);
						} else {
							state = Base.StateUpdateMatch(state);
						}
					} else {
						state = _optimum[posPrev].State;
					}
					state = Base.StateUpdateChar(state);
				} else {
					state = _optimum[posPrev].State;
				}
				if (posPrev == cur - 1) {
					if (_optimum[cur].IsShortRep()) {
						state = Base.StateUpdateShortRep(state);
					} else {
						state = Base.StateUpdateChar(state);
					}
				} else {
					int pos;
					if (_optimum[cur].Prev1IsChar && _optimum[cur].Prev2) {
						posPrev = _optimum[cur].PosPrev2;
						pos = _optimum[cur].BackPrev2;
						state = Base.StateUpdateRep(state);
					} else {
						pos = _optimum[cur].BackPrev;
						if (pos < 4) {
							state = Base.StateUpdateRep(state);
						} else {
							state = Base.StateUpdateMatch(state);
						}
					}
					Optimal opt = _optimum[posPrev];
					if (pos < 4) {
						if (pos == 0) {
							reps[0] = opt.Backs0;
							reps[1] = opt.Backs1;
							reps[2] = opt.Backs2;
							reps[3] = opt.Backs3;
						} else
						if (pos == 1) {
							reps[0] = opt.Backs1;
							reps[1] = opt.Backs0;
							reps[2] = opt.Backs2;
							reps[3] = opt.Backs3;
						} else
						if (pos == 2) {
							reps[0] = opt.Backs2;
							reps[1] = opt.Backs0;
							reps[2] = opt.Backs1;
							reps[3] = opt.Backs3;
						} else {
							reps[0] = opt.Backs3;
							reps[1] = opt.Backs0;
							reps[2] = opt.Backs1;
							reps[3] = opt.Backs2;
						}
					} else {
						reps[0] = pos - 4;
						reps[1] = opt.Backs0;
						reps[2] = opt.Backs1;
						reps[3] = opt.Backs2;
					}
				}
				_optimum[cur].State = state;
				_optimum[cur].Backs0 = reps[0];
				_optimum[cur].Backs1 = reps[1];
				_optimum[cur].Backs2 = reps[2];
				_optimum[cur].Backs3 = reps[3];
				int curPrice = _optimum[cur].Price;
				currentByte = _matchFinder.GetIndexByte(-1);
				matchByte = _matchFinder.GetIndexByte(0 - reps[0] - 1 - 1);
				posState = position & _posStateMask;
				curAnd1Price = curPrice + SevenZip.Compression.RangeCoder.Encoder.GetPrice0(_isMatch[(state << 4) + posState]) + _literalEncoder.GetSubCoder(position, _matchFinder.GetIndexByte(-2)).GetPrice(!Base.StateIsCharState(state), matchByte, currentByte);
				Optimal nextOptimum = _optimum[cur + 1];
				nextIsChar = false;
				if (curAnd1Price < nextOptimum.Price) {
					nextOptimum.Price = curAnd1Price;
					nextOptimum.PosPrev = cur;
					nextOptimum.MakeAsChar();
					nextIsChar = true;
				}
				matchPrice = curPrice + SevenZip.Compression.RangeCoder.Encoder.GetPrice1(_isMatch[(state << 4) + posState]);
				repMatchPrice = matchPrice + SevenZip.Compression.RangeCoder.Encoder.GetPrice1(_isRep[state]);
				if (matchByte == currentByte && (nextOptimum.PosPrev >= cur || nextOptimum.BackPrev != 0)) {
					int shortRepPrice = repMatchPrice + GetRepLen1Price(state, posState);
					if (shortRepPrice <= nextOptimum.Price) {
						nextOptimum.Price = shortRepPrice;
						nextOptimum.PosPrev = cur;
						nextOptimum.MakeAsShortRep();
						nextIsChar = true;
					}
				}
				numAvailableBytesFull = _matchFinder.GetNumAvailableBytes() + 1;
				numAvailableBytesFull = Math.min(4095 - cur, numAvailableBytesFull);
				numAvailableBytes = numAvailableBytesFull;
			} while (numAvailableBytes < 2);
			if (numAvailableBytes > _numFastBytes) {
				numAvailableBytes = _numFastBytes;
			}
			if (!nextIsChar && matchByte != currentByte) {
				int t = Math.min(numAvailableBytesFull - 1, _numFastBytes);
				int lenTest2 = _matchFinder.GetMatchLen(0, reps[0], t);
				if (lenTest2 >= 2) {
					int state2 = Base.StateUpdateChar(state);
					int posStateNext = position + 1 & _posStateMask;
					int nextRepMatchPrice = curAnd1Price + SevenZip.Compression.RangeCoder.Encoder.GetPrice1(_isMatch[(state2 << 4) + posStateNext]) + SevenZip.Compression.RangeCoder.Encoder.GetPrice1(_isRep[state2]);
					int offset;
					for (offset = cur + 1 + lenTest2; lenEnd < offset;) {
						_optimum[++lenEnd].Price = 0xfffffff;
					}

					int curAndLenPrice = nextRepMatchPrice + GetRepPrice(0, lenTest2, state2, posStateNext);
					Optimal optimum = _optimum[offset];
					if (curAndLenPrice < optimum.Price) {
						optimum.Price = curAndLenPrice;
						optimum.PosPrev = cur + 1;
						optimum.BackPrev = 0;
						optimum.Prev1IsChar = true;
						optimum.Prev2 = false;
					}
				}
			}
			startLen = 2;
			for (int repIndex = 0; repIndex < 4; repIndex++) {
				lenTest = _matchFinder.GetMatchLen(-1, reps[repIndex], numAvailableBytes);
				if (lenTest < 2) {
					continue;
				}
				int lenTestTemp = lenTest;
				do {
					while (lenEnd < cur + lenTest)  {
						_optimum[++lenEnd].Price = 0xfffffff;
					}
					int curAndLenPrice = repMatchPrice + GetRepPrice(repIndex, lenTest, state, posState);
					Optimal optimum = _optimum[cur + lenTest];
					if (curAndLenPrice < optimum.Price) {
						optimum.Price = curAndLenPrice;
						optimum.PosPrev = cur;
						optimum.BackPrev = repIndex;
						optimum.Prev1IsChar = false;
					}
				} while (--lenTest >= 2);
				lenTest = lenTestTemp;
				if (repIndex == 0) {
					startLen = lenTest + 1;
				}
				if (lenTest >= numAvailableBytesFull) {
					continue;
				}
				int t = Math.min(numAvailableBytesFull - 1 - lenTest, _numFastBytes);
				int lenTest2 = _matchFinder.GetMatchLen(lenTest, reps[repIndex], t);
				if (lenTest2 < 2) {
					continue;
				}
				int state2 = Base.StateUpdateRep(state);
				int posStateNext = position + lenTest & _posStateMask;
				int curAndLenCharPrice = repMatchPrice + GetRepPrice(repIndex, lenTest, state, posState) + SevenZip.Compression.RangeCoder.Encoder.GetPrice0(_isMatch[(state2 << 4) + posStateNext]) + _literalEncoder.GetSubCoder(position + lenTest, _matchFinder.GetIndexByte(lenTest - 1 - 1)).GetPrice(true, _matchFinder.GetIndexByte(lenTest - 1 - (reps[repIndex] + 1)), _matchFinder.GetIndexByte(lenTest - 1));
				state2 = Base.StateUpdateChar(state2);
				posStateNext = position + lenTest + 1 & _posStateMask;
				int nextMatchPrice = curAndLenCharPrice + SevenZip.Compression.RangeCoder.Encoder.GetPrice1(_isMatch[(state2 << 4) + posStateNext]);
				int nextRepMatchPrice = nextMatchPrice + SevenZip.Compression.RangeCoder.Encoder.GetPrice1(_isRep[state2]);
				int offset;
				for (offset = lenTest + 1 + lenTest2; lenEnd < cur + offset;) {
					_optimum[++lenEnd].Price = 0xfffffff;
				}

				int curAndLenPrice = nextRepMatchPrice + GetRepPrice(0, lenTest2, state2, posStateNext);
				Optimal optimum = _optimum[cur + offset];
				if (curAndLenPrice < optimum.Price) {
					optimum.Price = curAndLenPrice;
					optimum.PosPrev = cur + lenTest + 1;
					optimum.BackPrev = 0;
					optimum.Prev1IsChar = true;
					optimum.Prev2 = true;
					optimum.PosPrev2 = cur;
					optimum.BackPrev2 = repIndex;
				}
			}

			if (newLen > numAvailableBytes) {
				newLen = numAvailableBytes;
				for (numDistancePairs = 0; newLen > _matchDistances[numDistancePairs]; numDistancePairs += 2) { }
				_matchDistances[numDistancePairs] = newLen;
				numDistancePairs += 2;
			}
		} while (newLen < startLen);
		normalMatchPrice = matchPrice + SevenZip.Compression.RangeCoder.Encoder.GetPrice0(_isRep[state]);
		while (lenEnd < cur + newLen)  {
			_optimum[++lenEnd].Price = 0xfffffff;
		}
		for (offs = 0; startLen > _matchDistances[offs]; offs += 2) { }
		lenTest = startLen;
_L3:
		int curBack = _matchDistances[offs + 1];
		int curAndLenPrice = normalMatchPrice + GetPosLenPrice(curBack, lenTest, posState);
		Optimal optimum = _optimum[cur + lenTest];
		if (curAndLenPrice < optimum.Price) {
			optimum.Price = curAndLenPrice;
			optimum.PosPrev = cur;
			optimum.BackPrev = curBack + 4;
			optimum.Prev1IsChar = false;
		}
		if (lenTest != _matchDistances[offs]) {
			break; /* Loop/switch isn't completed */
		}
		if (lenTest < numAvailableBytesFull) {
			int t = Math.min(numAvailableBytesFull - 1 - lenTest, _numFastBytes);
			int lenTest2 = _matchFinder.GetMatchLen(lenTest, curBack, t);
			if (lenTest2 >= 2) {
				int state2 = Base.StateUpdateMatch(state);
				int posStateNext = position + lenTest & _posStateMask;
				int curAndLenCharPrice = curAndLenPrice + SevenZip.Compression.RangeCoder.Encoder.GetPrice0(_isMatch[(state2 << 4) + posStateNext]) + _literalEncoder.GetSubCoder(position + lenTest, _matchFinder.GetIndexByte(lenTest - 1 - 1)).GetPrice(true, _matchFinder.GetIndexByte(lenTest - (curBack + 1) - 1), _matchFinder.GetIndexByte(lenTest - 1));
				state2 = Base.StateUpdateChar(state2);
				posStateNext = position + lenTest + 1 & _posStateMask;
				int nextMatchPrice = curAndLenCharPrice + SevenZip.Compression.RangeCoder.Encoder.GetPrice1(_isMatch[(state2 << 4) + posStateNext]);
				int nextRepMatchPrice = nextMatchPrice + SevenZip.Compression.RangeCoder.Encoder.GetPrice1(_isRep[state2]);
				int offset;
				for (offset = lenTest + 1 + lenTest2; lenEnd < cur + offset;) {
					_optimum[++lenEnd].Price = 0xfffffff;
				}

				curAndLenPrice = nextRepMatchPrice + GetRepPrice(0, lenTest2, state2, posStateNext);
				optimum = _optimum[cur + offset];
				if (curAndLenPrice < optimum.Price) {
					optimum.Price = curAndLenPrice;
					optimum.PosPrev = cur + lenTest + 1;
					optimum.BackPrev = 0;
					optimum.Prev1IsChar = true;
					optimum.Prev2 = true;
					optimum.PosPrev2 = cur;
					optimum.BackPrev2 = curBack + 4;
				}
			}
		}
		if ((offs += 2) != numDistancePairs) goto _L2; else goto _L1
_L2:
		lenTest++;
		  goto _L3
		if (true) goto _L1; else goto _L4
_L4:
	}

	boolean ChangePair(int smallDist, int bigDist) {
		int kDif = 7;
		return smallDist < 1 << 32 - kDif && bigDist >= smallDist << kDif;
	}

	void WriteEndMarker(int posState) throws IOException {
		if (!_writeEndMark) {
			return;
		} else {
			_rangeEncoder.Encode(_isMatch, (_state << 4) + posState, 1);
			_rangeEncoder.Encode(_isRep, _state, 0);
			_state = Base.StateUpdateMatch(_state);
			int len = 2;
			_lenEncoder.Encode(_rangeEncoder, len - 2, posState);
			int posSlot = 63;
			int lenToPosState = Base.GetLenToPosState(len);
			_posSlotEncoder[lenToPosState].Encode(_rangeEncoder, posSlot);
			int footerBits = 30;
			int posReduced = (1 << footerBits) - 1;
			_rangeEncoder.EncodeDirectBits(posReduced >> 4, footerBits - 4);
			_posAlignEncoder.ReverseEncode(_rangeEncoder, posReduced & 0xf);
			return;
		}
	}

	void Flush(int nowPos) throws IOException {
		ReleaseMFStream();
		WriteEndMarker(nowPos & _posStateMask);
		_rangeEncoder.FlushData();
		_rangeEncoder.FlushStream();
	}

	public void CodeOneBlock(long inSize[], long outSize[], boolean finished[]) throws IOException {
		inSize[0] = 0L;
		outSize[0] = 0L;
		finished[0] = true;
		if (_inStream != null) {
			_matchFinder.SetStream(_inStream);
			_matchFinder.Init();
			_needReleaseMFStream = true;
			_inStream = null;
		}
		if (_finished) {
			return;
		}
		_finished = true;
		long progressPosValuePrev = nowPos64;
		if (nowPos64 == 0L) {
			if (_matchFinder.GetNumAvailableBytes() == 0) {
				Flush((int)nowPos64);
				return;
			}
			ReadMatchDistances();
			int posState = (int)nowPos64 & _posStateMask;
			_rangeEncoder.Encode(_isMatch, (_state << 4) + posState, 0);
			_state = Base.StateUpdateChar(_state);
			byte curByte = _matchFinder.GetIndexByte(0 - _additionalOffset);
			_literalEncoder.GetSubCoder((int)nowPos64, _previousByte).Encode(_rangeEncoder, curByte);
			_previousByte = curByte;
			_additionalOffset--;
			nowPos64++;
		}
		if (_matchFinder.GetNumAvailableBytes() == 0) {
			Flush((int)nowPos64);
			return;
		}
		do {
			do {
				int len = GetOptimum((int)nowPos64);
				int pos = backRes;
				int posState = (int)nowPos64 & _posStateMask;
				int complexState = (_state << 4) + posState;
				if (len == 1 && pos == -1) {
					_rangeEncoder.Encode(_isMatch, complexState, 0);
					byte curByte = _matchFinder.GetIndexByte(0 - _additionalOffset);
					LiteralEncoder.Encoder2 subCoder = _literalEncoder.GetSubCoder((int)nowPos64, _previousByte);
					if (!Base.StateIsCharState(_state)) {
						byte matchByte = _matchFinder.GetIndexByte(0 - _repDistances[0] - 1 - _additionalOffset);
						subCoder.EncodeMatched(_rangeEncoder, matchByte, curByte);
					} else {
						subCoder.Encode(_rangeEncoder, curByte);
					}
					_previousByte = curByte;
					_state = Base.StateUpdateChar(_state);
				} else {
					_rangeEncoder.Encode(_isMatch, complexState, 1);
					if (pos < 4) {
						_rangeEncoder.Encode(_isRep, _state, 1);
						if (pos == 0) {
							_rangeEncoder.Encode(_isRepG0, _state, 0);
							if (len == 1) {
								_rangeEncoder.Encode(_isRep0Long, complexState, 0);
							} else {
								_rangeEncoder.Encode(_isRep0Long, complexState, 1);
							}
						} else {
							_rangeEncoder.Encode(_isRepG0, _state, 1);
							if (pos == 1) {
								_rangeEncoder.Encode(_isRepG1, _state, 0);
							} else {
								_rangeEncoder.Encode(_isRepG1, _state, 1);
								_rangeEncoder.Encode(_isRepG2, _state, pos - 2);
							}
						}
						if (len == 1) {
							_state = Base.StateUpdateShortRep(_state);
						} else {
							_repMatchLenEncoder.Encode(_rangeEncoder, len - 2, posState);
							_state = Base.StateUpdateRep(_state);
						}
						int distance = _repDistances[pos];
						if (pos != 0) {
							for (int i = pos; i >= 1; i--) {
								_repDistances[i] = _repDistances[i - 1];
							}

							_repDistances[0] = distance;
						}
					} else {
						_rangeEncoder.Encode(_isRep, _state, 0);
						_state = Base.StateUpdateMatch(_state);
						_lenEncoder.Encode(_rangeEncoder, len - 2, posState);
						int posSlot = GetPosSlot(pos -= 4);
						int lenToPosState = Base.GetLenToPosState(len);
						_posSlotEncoder[lenToPosState].Encode(_rangeEncoder, posSlot);
						if (posSlot >= 4) {
							int footerBits = (posSlot >> 1) - 1;
							int baseVal = (2 | posSlot & 1) << footerBits;
							int posReduced = pos - baseVal;
							if (posSlot < 14) {
								BitTreeEncoder.ReverseEncode(_posEncoders, baseVal - posSlot - 1, _rangeEncoder, footerBits, posReduced);
							} else {
								_rangeEncoder.EncodeDirectBits(posReduced >> 4, footerBits - 4);
								_posAlignEncoder.ReverseEncode(_rangeEncoder, posReduced & 0xf);
								_alignPriceCount++;
							}
						}
						int distance = pos;
						for (int i = 3; i >= 1; i--) {
							_repDistances[i] = _repDistances[i - 1];
						}

						_repDistances[0] = distance;
						_matchPriceCount++;
					}
					_previousByte = _matchFinder.GetIndexByte(len - 1 - _additionalOffset);
				}
				_additionalOffset -= len;
				nowPos64 += len;
			} while (_additionalOffset != 0);
			if (_matchPriceCount >= 128) {
				FillDistancesPrices();
			}
			if (_alignPriceCount >= 16) {
				FillAlignPrices();
			}
			inSize[0] = nowPos64;
			outSize[0] = _rangeEncoder.GetProcessedSizeAdd();
			if (_matchFinder.GetNumAvailableBytes() == 0) {
				Flush((int)nowPos64);
				return;
			}
		} while (nowPos64 - progressPosValuePrev < 4096L);
		_finished = false;
		finished[0] = false;
	}

	void ReleaseMFStream() {
		if (_matchFinder != null && _needReleaseMFStream) {
			_matchFinder.ReleaseStream();
			_needReleaseMFStream = false;
		}
	}

	void SetOutStream(OutputStream outStream) {
		_rangeEncoder.SetStream(outStream);
	}

	void ReleaseOutStream() {
		_rangeEncoder.ReleaseStream();
	}

	void ReleaseStreams() {
		ReleaseMFStream();
		ReleaseOutStream();
	}

	void SetStreams(InputStream inStream, OutputStream outStream, long inSize, long outSize) {
		_inStream = inStream;
		_finished = false;
		Create();
		SetOutStream(outStream);
		Init();
		FillDistancesPrices();
		FillAlignPrices();
		_lenEncoder.SetTableSize((_numFastBytes + 1) - 2);
		_lenEncoder.UpdateTables(1 << _posStateBits);
		_repMatchLenEncoder.SetTableSize((_numFastBytes + 1) - 2);
		_repMatchLenEncoder.UpdateTables(1 << _posStateBits);
		nowPos64 = 0L;
	}

	public void Code(InputStream inStream, OutputStream outStream, long inSize, long outSize, ICodeProgress progress) throws IOException {
		_needReleaseMFStream = false;
		SetStreams(inStream, outStream, inSize, outSize);
_L1:
		CodeOneBlock(processedInSize, processedOutSize, finished);
		if (finished[0]) {
			ReleaseStreams();
			return;
		}
		if (progress != null) {
			progress.SetProgress(processedInSize[0], processedOutSize[0]);
		}
		  goto _L1
		Exception exception;
		exception;
		ReleaseStreams();
		throw exception;
	}

	public void WriteCoderProperties(OutputStream outStream) throws IOException {
		properties[0] = (byte)((_posStateBits * 5 + _numLiteralPosStateBits) * 9 + _numLiteralContextBits);
		for (int i = 0; i < 4; i++) {
			properties[1 + i] = (byte)(_dictionarySize >> 8 * i);
		}

		outStream.write(properties, 0, 5);
	}

	void FillDistancesPrices() {
		for (int i = 4; i < 128; i++) {
			int posSlot = GetPosSlot(i);
			int footerBits = (posSlot >> 1) - 1;
			int baseVal = (2 | posSlot & 1) << footerBits;
			tempPrices[i] = BitTreeEncoder.ReverseGetPrice(_posEncoders, baseVal - posSlot - 1, footerBits, i - baseVal);
		}

		for (int lenToPosState = 0; lenToPosState < 4; lenToPosState++) {
			BitTreeEncoder encoder = _posSlotEncoder[lenToPosState];
			int st = lenToPosState << 6;
			for (int posSlot = 0; posSlot < _distTableSize; posSlot++) {
				_posSlotPrices[st + posSlot] = encoder.GetPrice(posSlot);
			}

			for (int posSlot = 14; posSlot < _distTableSize; posSlot++) {
				_posSlotPrices[st + posSlot] += (posSlot >> 1) - 1 - 4 << 6;
			}

			int st2 = lenToPosState * 128;
			int i;
			for (i = 0; i < 4; i++) {
				_distancesPrices[st2 + i] = _posSlotPrices[st + i];
			}

			for (; i < 128; i++) {
				_distancesPrices[st2 + i] = _posSlotPrices[st + GetPosSlot(i)] + tempPrices[i];
			}

		}

		_matchPriceCount = 0;
	}

	void FillAlignPrices() {
		for (int i = 0; i < 16; i++) {
			_alignPrices[i] = _posAlignEncoder.ReverseGetPrice(i);
		}

		_alignPriceCount = 0;
	}

	public boolean SetAlgorithm(int algorithm) {
		return true;
	}

	public boolean SetDictionarySize(int dictionarySize) {
		int kDicLogSizeMaxCompress = 29;
		if (dictionarySize < 1 || dictionarySize > 1 << kDicLogSizeMaxCompress) {
			return false;
		}
		_dictionarySize = dictionarySize;
		int dicLogSize;
		for (dicLogSize = 0; dictionarySize > 1 << dicLogSize; dicLogSize++) { }
		_distTableSize = dicLogSize * 2;
		return true;
	}

	public boolean SeNumFastBytes(int numFastBytes) {
		if (numFastBytes < 5 || numFastBytes > 273) {
			return false;
		} else {
			_numFastBytes = numFastBytes;
			return true;
		}
	}

	public boolean SetMatchFinder(int matchFinderIndex) {
		if (matchFinderIndex < 0 || matchFinderIndex > 2) {
			return false;
		}
		int matchFinderIndexPrev = _matchFinderType;
		_matchFinderType = matchFinderIndex;
		if (_matchFinder != null && matchFinderIndexPrev != _matchFinderType) {
			_dictionarySizePrev = -1;
			_matchFinder = null;
		}
		return true;
	}

	public boolean SetLcLpPb(int lc, int lp, int pb) {
		if (lp < 0 || lp > 4 || lc < 0 || lc > 8 || pb < 0 || pb > 4) {
			return false;
		} else {
			_numLiteralPosStateBits = lp;
			_numLiteralContextBits = lc;
			_posStateBits = pb;
			_posStateMask = (1 << _posStateBits) - 1;
			return true;
		}
	}

	public void SetEndMarkerMode(boolean endMarkerMode) {
		_writeEndMark = endMarkerMode;
	}

	static  {
		g_FastPos = new byte[2048];
		int kFastSlots = 22;
		int c = 2;
		g_FastPos[0] = 0;
		g_FastPos[1] = 1;
		for (int slotFast = 2; slotFast < kFastSlots; slotFast++) {
			int k = 1 << (slotFast >> 1) - 1;
			for (int j = 0; j < k;) {
				g_FastPos[c] = (byte)slotFast;
				j++;
				c++;
			}

		}

	}
}
