// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   QueryParam.java

package org.forkjoin.core.dao;


public final class QueryParam {
	public static final class OperatorType extends Enum {

		public static final OperatorType EQ;
		public static final OperatorType LIKE;
		public static final OperatorType LIKE_BOTH;
		public static final OperatorType LT;
		public static final OperatorType GT;
		public static final OperatorType LE;
		public static final OperatorType GE;
		public static final OperatorType NOT;
		public static final OperatorType IS_NULL;
		public static final OperatorType IS_NOT_NULL;
		private static final OperatorType $VALUES[];

		public static OperatorType[] values() {
			return (OperatorType[])$VALUES.clone();
		}

		public static OperatorType valueOf(String name) {
			return (OperatorType)Enum.valueOf(org/forkjoin/core/dao/QueryParam$OperatorType, name);
		}

		static  {
			EQ = new OperatorType("EQ", 0);
			LIKE = new OperatorType("LIKE", 1);
			LIKE_BOTH = new OperatorType("LIKE_BOTH", 2);
			LT = new OperatorType("LT", 3);
			GT = new OperatorType("GT", 4);
			LE = new OperatorType("LE", 5);
			GE = new OperatorType("GE", 6);
			NOT = new OperatorType("NOT", 7);
			IS_NULL = new OperatorType("IS_NULL", 8);
			IS_NOT_NULL = new OperatorType("IS_NOT_NULL", 9);
			$VALUES = (new OperatorType[] {
				EQ, LIKE, LIKE_BOTH, LT, GT, LE, GE, NOT, IS_NULL, IS_NOT_NULL
			});
		}

		private OperatorType(String s, int i) {
			super(s, i);
		}
	}


	protected OperatorType opt;
	protected boolean and;
	protected boolean not;
	protected String key;
	protected Object value;

	public QueryParam() {
		opt = OperatorType.EQ;
		and = true;
		not = false;
	}

	public QueryParam(String key, Object value) {
		opt = OperatorType.EQ;
		and = true;
		not = false;
		this.key = key;
		this.value = value;
	}

	public QueryParam(String key, Object value, OperatorType opt) {
		this.opt = OperatorType.EQ;
		and = true;
		not = false;
		this.key = key;
		this.value = value;
		this.opt = opt;
	}

	public QueryParam(String key, Object value, OperatorType opt, boolean and) {
		this.opt = OperatorType.EQ;
		this.and = true;
		not = false;
		this.key = key;
		this.value = value;
		this.opt = opt;
		this.and = and;
	}

	public QueryParam(String key, Object value, OperatorType opt, boolean and, boolean not) {
		this.opt = OperatorType.EQ;
		this.and = true;
		this.not = false;
		this.key = key;
		this.value = value;
		this.opt = opt;
		this.and = and;
		this.not = not;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public QueryParam not() {
		not = true;
		return this;
	}

	public OperatorType getOpt() {
		return opt;
	}

	public void setOpt(OperatorType opt) {
		this.opt = opt;
	}

	public boolean isNot() {
		return not;
	}

	public void setNot(boolean not) {
		this.not = not;
	}

	public boolean isAnd() {
		return and;
	}

	public void setAnd(boolean and) {
		this.and = and;
	}

	public String toString() {
		return (new StringBuilder()).append("QueryParam [opt=").append(opt).append(", and=").append(and).append(", not=").append(not).append(", key=").append(key).append(", value=").append(value).append("]").toString();
	}
}
