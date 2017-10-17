// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   QueryParam.java

package org.forkjoin.core.dao;


// Referenced classes of package org.forkjoin.core.dao:
//			QueryParam

public static final class QueryParam$OperatorType extends Enum {

	public static final QueryParam$OperatorType EQ;
	public static final QueryParam$OperatorType LIKE;
	public static final QueryParam$OperatorType LIKE_BOTH;
	public static final QueryParam$OperatorType LT;
	public static final QueryParam$OperatorType GT;
	public static final QueryParam$OperatorType LE;
	public static final QueryParam$OperatorType GE;
	public static final QueryParam$OperatorType NOT;
	public static final QueryParam$OperatorType IS_NULL;
	public static final QueryParam$OperatorType IS_NOT_NULL;
	private static final QueryParam$OperatorType $VALUES[];

	public static QueryParam$OperatorType[] values() {
		return (QueryParam$OperatorType[])$VALUES.clone();
	}

	public static QueryParam$OperatorType valueOf(String name) {
		return (QueryParam$OperatorType)Enum.valueOf(org/forkjoin/core/dao/QueryParam$OperatorType, name);
	}

	static  {
		EQ = new QueryParam$OperatorType("EQ", 0);
		LIKE = new QueryParam$OperatorType("LIKE", 1);
		LIKE_BOTH = new QueryParam$OperatorType("LIKE_BOTH", 2);
		LT = new QueryParam$OperatorType("LT", 3);
		GT = new QueryParam$OperatorType("GT", 4);
		LE = new QueryParam$OperatorType("LE", 5);
		GE = new QueryParam$OperatorType("GE", 6);
		NOT = new QueryParam$OperatorType("NOT", 7);
		IS_NULL = new QueryParam$OperatorType("IS_NULL", 8);
		IS_NOT_NULL = new QueryParam$OperatorType("IS_NOT_NULL", 9);
		$VALUES = (new QueryParam$OperatorType[] {
			EQ, LIKE, LIKE_BOTH, LT, GT, LE, GE, NOT, IS_NULL, IS_NOT_NULL
		});
	}

	private QueryParam$OperatorType(String s, int i) {
		super(s, i);
	}
}
