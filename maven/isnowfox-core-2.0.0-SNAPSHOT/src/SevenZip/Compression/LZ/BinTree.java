// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   BinTree.java

package SevenZip.Compression.LZ;

import java.io.IOException;

// Referenced classes of package SevenZip.Compression.LZ:
//			InWindow

public class BinTree extends InWindow {

	int _cyclicBufferPos;
	int _cyclicBufferSize;
	int _matchMaxLen;
	int _son[];
	int _hash[];
	int _cutValue;
	int _hashMask;
	int _hashSizeSum;
	boolean HASH_ARRAY;
	static final int kHash2Size = 1024;
	static final int kHash3Size = 0x10000;
	static final int kBT2HashSize = 0x10000;
	static final int kStartMaxLen = 1;
	static final int kHash3Offset = 1024;
	static final int kEmptyHashValue = 0;
	static final int kMaxValForNormalize = 0x3fffffff;
	int kNumHashDirectBytes;
	int kMinMatchCheck;
	int kFixHashSize;
	private static final int CrcTable[];

	public BinTree() {
		_cyclicBufferSize = 0;
		_cutValue = 255;
		_hashSizeSum = 0;
		HASH_ARRAY = true;
		kNumHashDirectBytes = 0;
		kMinMatchCheck = 4;
		kFixHashSize = 0x10400;
	}

	public void SetType(int numHashBytes) {
		HASH_ARRAY = numHashBytes > 2;
		if (HASH_ARRAY) {
			kNumHashDirectBytes = 0;
			kMinMatchCheck = 4;
			kFixHashSize = 0x10400;
		} else {
			kNumHashDirectBytes = 2;
			kMinMatchCheck = 3;
			kFixHashSize = 0;
		}
	}

	public void Init() throws IOException {
		super.Init();
		for (int i = 0; i < _hashSizeSum; i++) {
			_hash[i] = 0;
		}

		_cyclicBufferPos = 0;
		ReduceOffsets(-1);
	}

	public void MovePos() throws IOException {
		if (++_cyclicBufferPos >= _cyclicBufferSize) {
			_cyclicBufferPos = 0;
		}
		super.MovePos();
		if (_pos == 0x3fffffff) {
			Normalize();
		}
	}

	public boolean Create(int historySize, int keepAddBufferBefore, int matchMaxLen, int keepAddBufferAfter) {
		if (historySize > 0x3ffffeff) {
			return false;
		}
		_cutValue = 16 + (matchMaxLen >> 1);
		int windowReservSize = (historySize + keepAddBufferBefore + matchMaxLen + keepAddBufferAfter) / 2 + 256;
		super.Create(historySize + keepAddBufferBefore, matchMaxLen + keepAddBufferAfter, windowReservSize);
		_matchMaxLen = matchMaxLen;
		int cyclicBufferSize = historySize + 1;
		if (_cyclicBufferSize != cyclicBufferSize) {
			_son = new int[(_cyclicBufferSize = cyclicBufferSize) * 2];
		}
		int hs = 0x10000;
		if (HASH_ARRAY) {
			hs = historySize - 1;
			hs |= hs >> 1;
			hs |= hs >> 2;
			hs |= hs >> 4;
			hs |= hs >> 8;
			hs >>= 1;
			hs |= 0xffff;
			if (hs > 0x1000000) {
				hs >>= 1;
			}
			_hashMask = hs;
			hs = ++hs + kFixHashSize;
		}
		if (hs != _hashSizeSum) {
			_hash = new int[_hashSizeSum = hs];
		}
		return true;
	}

	public int GetMatches(int distances[]) throws IOException {
		int lenLimit;
		if (_pos + _matchMaxLen <= _streamPos) {
			lenLimit = _matchMaxLen;
		} else {
			lenLimit = _streamPos - _pos;
			if (lenLimit < kMinMatchCheck) {
				MovePos();
				return 0;
			}
		}
		int offset = 0;
		int matchMinPos = _pos <= _cyclicBufferSize ? 0 : _pos - _cyclicBufferSize;
		int cur = _bufferOffset + _pos;
		int maxLen = 1;
		int hash2Value = 0;
		int hash3Value = 0;
		int hashValue;
		if (HASH_ARRAY) {
			int temp = CrcTable[_bufferBase[cur] & 0xff] ^ _bufferBase[cur + 1] & 0xff;
			hash2Value = temp & 0x3ff;
			temp ^= (_bufferBase[cur + 2] & 0xff) << 8;
			hash3Value = temp & 0xffff;
			hashValue = (temp ^ CrcTable[_bufferBase[cur + 3] & 0xff] << 5) & _hashMask;
		} else {
			hashValue = _bufferBase[cur] & 0xff ^ (_bufferBase[cur + 1] & 0xff) << 8;
		}
		int curMatch = _hash[kFixHashSize + hashValue];
		if (HASH_ARRAY) {
			int curMatch2 = _hash[hash2Value];
			int curMatch3 = _hash[1024 + hash3Value];
			_hash[hash2Value] = _pos;
			_hash[1024 + hash3Value] = _pos;
			if (curMatch2 > matchMinPos && _bufferBase[_bufferOffset + curMatch2] == _bufferBase[cur]) {
				distances[offset++] = maxLen = 2;
				distances[offset++] = _pos - curMatch2 - 1;
			}
			if (curMatch3 > matchMinPos && _bufferBase[_bufferOffset + curMatch3] == _bufferBase[cur]) {
				if (curMatch3 == curMatch2) {
					offset -= 2;
				}
				distances[offset++] = maxLen = 3;
				distances[offset++] = _pos - curMatch3 - 1;
				curMatch2 = curMatch3;
			}
			if (offset != 0 && curMatch2 == curMatch) {
				offset -= 2;
				maxLen = 1;
			}
		}
		_hash[kFixHashSize + hashValue] = _pos;
		int ptr0 = (_cyclicBufferPos << 1) + 1;
		int ptr1 = _cyclicBufferPos << 1;
		int len1;
		int len0 = len1 = kNumHashDirectBytes;
		if (kNumHashDirectBytes != 0 && curMatch > matchMinPos && _bufferBase[_bufferOffset + curMatch + kNumHashDirectBytes] != _bufferBase[cur + kNumHashDirectBytes]) {
			distances[offset++] = maxLen = kNumHashDirectBytes;
			distances[offset++] = _pos - curMatch - 1;
		}
		int count = _cutValue;
		do {
			if (curMatch <= matchMinPos || count-- == 0) {
				_son[ptr0] = _son[ptr1] = 0;
				break;
			}
			int delta = _pos - curMatch;
			int cyclicPos = (delta > _cyclicBufferPos ? (_cyclicBufferPos - delta) + _cyclicBufferSize : _cyclicBufferPos - delta) << 1;
			int pby1 = _bufferOffset + curMatch;
			int len = Math.min(len0, len1);
			if (_bufferBase[pby1 + len] == _bufferBase[cur + len]) {
				while (++len != lenLimit && _bufferBase[pby1 + len] == _bufferBase[cur + len]) ;
				if (maxLen < len) {
					distances[offset++] = maxLen = len;
					distances[offset++] = delta - 1;
					if (len == lenLimit) {
						_son[ptr1] = _son[cyclicPos];
						_son[ptr0] = _son[cyclicPos + 1];
						break;
					}
				}
			}
			if ((_bufferBase[pby1 + len] & 0xff) < (_bufferBase[cur + len] & 0xff)) {
				_son[ptr1] = curMatch;
				ptr1 = cyclicPos + 1;
				curMatch = _son[ptr1];
				len1 = len;
			} else {
				_son[ptr0] = curMatch;
				ptr0 = cyclicPos;
				curMatch = _son[ptr0];
				len0 = len;
			}
		} while (true);
		MovePos();
		return offset;
	}

	public void Skip(int num) throws IOException {
		do {
			int lenLimit;
			if (_pos + _matchMaxLen <= _streamPos) {
				lenLimit = _matchMaxLen;
			} else {
				lenLimit = _streamPos - _pos;
				if (lenLimit < kMinMatchCheck) {
					MovePos();
					continue;
				}
			}
			int matchMinPos = _pos <= _cyclicBufferSize ? 0 : _pos - _cyclicBufferSize;
			int cur = _bufferOffset + _pos;
			int hashValue;
			if (HASH_ARRAY) {
				int temp = CrcTable[_bufferBase[cur] & 0xff] ^ _bufferBase[cur + 1] & 0xff;
				int hash2Value = temp & 0x3ff;
				_hash[hash2Value] = _pos;
				temp ^= (_bufferBase[cur + 2] & 0xff) << 8;
				int hash3Value = temp & 0xffff;
				_hash[1024 + hash3Value] = _pos;
				hashValue = (temp ^ CrcTable[_bufferBase[cur + 3] & 0xff] << 5) & _hashMask;
			} else {
				hashValue = _bufferBase[cur] & 0xff ^ (_bufferBase[cur + 1] & 0xff) << 8;
			}
			int curMatch = _hash[kFixHashSize + hashValue];
			_hash[kFixHashSize + hashValue] = _pos;
			int ptr0 = (_cyclicBufferPos << 1) + 1;
			int ptr1 = _cyclicBufferPos << 1;
			int len1;
			int len0 = len1 = kNumHashDirectBytes;
			int count = _cutValue;
			do {
				if (curMatch <= matchMinPos || count-- == 0) {
					_son[ptr0] = _son[ptr1] = 0;
					break;
				}
				int delta = _pos - curMatch;
				int cyclicPos = (delta > _cyclicBufferPos ? (_cyclicBufferPos - delta) + _cyclicBufferSize : _cyclicBufferPos - delta) << 1;
				int pby1 = _bufferOffset + curMatch;
				int len = Math.min(len0, len1);
				if (_bufferBase[pby1 + len] == _bufferBase[cur + len]) {
					while (++len != lenLimit && _bufferBase[pby1 + len] == _bufferBase[cur + len]) ;
					if (len == lenLimit) {
						_son[ptr1] = _son[cyclicPos];
						_son[ptr0] = _son[cyclicPos + 1];
						break;
					}
				}
				if ((_bufferBase[pby1 + len] & 0xff) < (_bufferBase[cur + len] & 0xff)) {
					_son[ptr1] = curMatch;
					ptr1 = cyclicPos + 1;
					curMatch = _son[ptr1];
					len1 = len;
				} else {
					_son[ptr0] = curMatch;
					ptr0 = cyclicPos;
					curMatch = _son[ptr0];
					len0 = len;
				}
			} while (true);
			MovePos();
		} while (--num != 0);
	}

	void NormalizeLinks(int items[], int numItems, int subValue) {
		for (int i = 0; i < numItems; i++) {
			int value = items[i];
			if (value <= subValue) {
				value = 0;
			} else {
				value -= subValue;
			}
			items[i] = value;
		}

	}

	void Normalize() {
		int subValue = _pos - _cyclicBufferSize;
		NormalizeLinks(_son, _cyclicBufferSize * 2, subValue);
		NormalizeLinks(_hash, _hashSizeSum, subValue);
		ReduceOffsets(subValue);
	}

	public void SetCutValue(int cutValue) {
		_cutValue = cutValue;
	}

	static  {
		CrcTable = new int[256];
		for (int i = 0; i < 256; i++) {
			int r = i;
			for (int j = 0; j < 8; j++) {
				if ((r & 1) != 0) {
					r = r >>> 1 ^ 0xedb88320;
				} else {
					r >>>= 1;
				}
			}

			CrcTable[i] = r;
		}

	}
}
