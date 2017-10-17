// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   QueryParams.java

package org.forkjoin.core.dao;


// Referenced classes of package org.forkjoin.core.dao:
//			QueryParams, QueryParam

static class QueryParams$2 {

	static final int $SwitchMap$org$forkjoin$core$dao$QueryParam$OperatorType[];

	static  {
		$SwitchMap$org$forkjoin$core$dao$QueryParam$OperatorType = new int[ratorType.values().length];
		try {
			$SwitchMap$org$forkjoin$core$dao$QueryParam$OperatorType[ratorType.EQ.ordinal()] = 1;
		}
		catch (NoSuchFieldError nosuchfielderror) { }
		try {
			$SwitchMap$org$forkjoin$core$dao$QueryParam$OperatorType[ratorType.LIKE.ordinal()] = 2;
		}
		catch (NoSuchFieldError nosuchfielderror1) { }
		try {
			$SwitchMap$org$forkjoin$core$dao$QueryParam$OperatorType[ratorType.LIKE_BOTH.ordinal()] = 3;
		}
		catch (NoSuchFieldError nosuchfielderror2) { }
		try {
			$SwitchMap$org$forkjoin$core$dao$QueryParam$OperatorType[ratorType.LT.ordinal()] = 4;
		}
		catch (NoSuchFieldError nosuchfielderror3) { }
		try {
			$SwitchMap$org$forkjoin$core$dao$QueryParam$OperatorType[ratorType.GT.ordinal()] = 5;
		}
		catch (NoSuchFieldError nosuchfielderror4) { }
		try {
			$SwitchMap$org$forkjoin$core$dao$QueryParam$OperatorType[ratorType.LE.ordinal()] = 6;
		}
		catch (NoSuchFieldError nosuchfielderror5) { }
		try {
			$SwitchMap$org$forkjoin$core$dao$QueryParam$OperatorType[ratorType.NOT.ordinal()] = 7;
		}
		catch (NoSuchFieldError nosuchfielderror6) { }
		try {
			$SwitchMap$org$forkjoin$core$dao$QueryParam$OperatorType[ratorType.GE.ordinal()] = 8;
		}
		catch (NoSuchFieldError nosuchfielderror7) { }
		try {
			$SwitchMap$org$forkjoin$core$dao$QueryParam$OperatorType[ratorType.IS_NULL.ordinal()] = 9;
		}
		catch (NoSuchFieldError nosuchfielderror8) { }
		try {
			$SwitchMap$org$forkjoin$core$dao$QueryParam$OperatorType[ratorType.IS_NOT_NULL.ordinal()] = 10;
		}
		catch (NoSuchFieldError nosuchfielderror9) { }
	}
}
