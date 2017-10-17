// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   UniqueIndex.java

package org.forkjoin.jdbckit.mysql;


// Referenced classes of package org.forkjoin.jdbckit.mysql:
//			UniqueIndex

public static class UniqueIndex$Field
	implements Comparable {

	private String fieldName;
	private int ordinalPosition;

	public String getFieldName() {
		return fieldName;
	}

	public int getOrdinalPosition() {
		return ordinalPosition;
	}

	public int compareTo(UniqueIndex$Field o) {
		return Integer.compare(ordinalPosition, o.ordinalPosition);
	}

	public String toString() {
		return (new StringBuilder()).append("Field{fieldName='").append(fieldName).append('\'').append(", ordinalPosition=").append(ordinalPosition).append('}').toString();
	}

	public volatile int compareTo(Object obj) {
		return compareTo((UniqueIndex$Field)obj);
	}




	public UniqueIndex$Field() {
	}
}
