// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   UniqueIndex.java

package org.forkjoin.jdbckit.mysql;

import java.util.Iterator;
import java.util.TreeSet;
import org.apache.commons.lang3.StringEscapeUtils;

public class UniqueIndex {
	public static class Field
		implements Comparable {

		private String fieldName;
		private int ordinalPosition;

		public String getFieldName() {
			return fieldName;
		}

		public int getOrdinalPosition() {
			return ordinalPosition;
		}

		public int compareTo(Field o) {
			return Integer.compare(ordinalPosition, o.ordinalPosition);
		}

		public String toString() {
			return (new StringBuilder()).append("Field{fieldName='").append(fieldName).append('\'').append(", ordinalPosition=").append(ordinalPosition).append('}').toString();
		}

		public volatile int compareTo(Object obj) {
			return compareTo((Field)obj);
		}




		public Field() {
		}
	}


	private String indexName;
	private TreeSet fields;

	public UniqueIndex(String indexName) {
		fields = new TreeSet();
		this.indexName = indexName;
	}

	public void addField(String fieldName, int ordinalPosition) {
		Field field = new Field();
		field.fieldName = fieldName;
		field.ordinalPosition = ordinalPosition;
		fields.add(field);
	}

	public String getConstantName() {
		return indexName.toUpperCase();
	}

	public String toFieldsArgs() {
		StringBuilder sb = new StringBuilder();
		for (Iterator iterator = fields.iterator(); iterator.hasNext(); sb.append('"')) {
			Field field = (Field)iterator.next();
			if (sb.length() > 0) {
				sb.append(" ,");
			}
			sb.append('"');
			sb.append(StringEscapeUtils.escapeJava(field.fieldName));
		}

		return sb.toString();
	}

	public String getIndexName() {
		return indexName;
	}

	public TreeSet getFields() {
		return fields;
	}
}
