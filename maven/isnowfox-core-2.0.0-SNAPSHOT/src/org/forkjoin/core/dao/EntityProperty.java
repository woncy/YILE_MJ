// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   EntityProperty.java

package org.forkjoin.core.dao;

import com.fasterxml.jackson.core.type.TypeReference;

public class EntityProperty {

	private String dbName;
	private String propertyName;
	private Class type;
	private TypeReference valueTypeRef;

	public EntityProperty() {
	}

	public EntityProperty(String dbName, String propertyName, Class type, TypeReference valueTypeRef) {
		this.dbName = dbName;
		this.propertyName = propertyName;
		this.type = type;
		this.valueTypeRef = valueTypeRef;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public Class getType() {
		return type;
	}

	public void setType(Class type) {
		this.type = type;
	}

	public TypeReference getValueTypeRef() {
		return valueTypeRef;
	}

	public void setValueTypeRef(TypeReference valueTypeRef) {
		this.valueTypeRef = valueTypeRef;
	}

	public String toString() {
		return (new StringBuilder()).append("EntityProperty{dbName='").append(dbName).append('\'').append(", propertyName='").append(propertyName).append('\'').append(", type=").append(type).append(", valueTypeRef=").append(valueTypeRef).append('}').toString();
	}
}
