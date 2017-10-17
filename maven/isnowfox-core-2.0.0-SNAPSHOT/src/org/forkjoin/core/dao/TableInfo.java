// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   TableInfo.java

package org.forkjoin.core.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import org.springframework.jdbc.core.RowMapper;

// Referenced classes of package org.forkjoin.core.dao:
//			EntityProperty, EntityObject, KeyObject, UniqueInfo

public abstract class TableInfo {

	private Map propertiesMap;
	private Map dbNameMap;

	public TableInfo() {
		propertiesMap = new HashMap();
		dbNameMap = new HashMap();
	}

	public abstract String getInsertSql();

	public abstract String getReplaceSql();

	public abstract String getFastInsertPrefixSql();

	public abstract String getFastInsertValueItemsSql();

	public abstract String getUpdateSql();

	public abstract int setPreparedStatement(EntityObject entityobject, PreparedStatement preparedstatement, int i, boolean flag) throws SQLException;

	public abstract int setAllPreparedStatement(EntityObject entityobject, PreparedStatement preparedstatement, int i) throws SQLException;

	public abstract int setPreparedStatementKeys(EntityObject entityobject, PreparedStatement preparedstatement, int i) throws SQLException;

	public abstract String getKeyUpdatePartialPrefixSql();

	public abstract String getKeyWhereByKeySql();

	public abstract String getKeyDeleteSql();

	public abstract int setKeyPreparedStatement(KeyObject keyobject, PreparedStatement preparedstatement, int i) throws SQLException;

	public abstract String getSelectByKeySql();

	public abstract String getSelectCountSql();

	public abstract String getFormatSelectSql();

	public abstract String getFormatSelectPrefixSql();

	public abstract String getSelectPrefixSql();

	public abstract String getOrderByIdDescSql();

	public abstract RowMapper getRowMapper();

	public abstract RowMapper getRowMapper(Class class1);

	public abstract String getDbTableName();

	public abstract Map getUniques();

	public abstract UniqueInfo getUniques(String s);

	protected void initProperty(String dbName, String propertyName, Class type, TypeReference valueTypeRef) {
		EntityProperty property = new EntityProperty(dbName, propertyName, type, valueTypeRef);
		dbNameMap.put(dbName, property);
		propertiesMap.put(propertyName, property);
	}

	public Collection getEntityProperties() {
		return dbNameMap.values();
	}

	public EntityProperty getPropertyByDbName(String dbName) {
		return (EntityProperty)dbNameMap.get(dbName);
	}

	public EntityProperty getProperty(String propertyName) {
		return (EntityProperty)propertiesMap.get(propertyName);
	}
}
