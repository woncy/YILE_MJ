// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   AbstractReadOnlyDao.java

package org.forkjoin.core.dao.impi;

import com.isnowfox.core.PageResult;
import java.util.List;
import org.forkjoin.core.dao.*;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public abstract class AbstractReadOnlyDao extends JdbcDaoSupport
	implements ReadOnlyDao {

	public AbstractReadOnlyDao() {
	}

	public PageResult findPage(int page, int pageSize) {
		return findPage(null, null, page, pageSize);
	}

	public PageResult findPage(Order order, int page, int pageSize) {
		return findPage(null, order, page, pageSize);
	}

	public PageResult findPage(QueryParams params, int page, int pageSize) {
		return findPage(params, null, page, pageSize);
	}

	public abstract PageResult findPage(QueryParams queryparams, Order order, int i, int j);

	public List find(int max) {
		return find(max, (QueryParams)null, ((Order) (null)));
	}

	public List find(int max, String key, Object value) {
		return find(max, QueryParams.single(key, value), ((Order) (null)));
	}

	public List find(int max, String key, Object value, Order order) {
		return find(max, QueryParams.single(key, value), order);
	}

	public List find(int max, String key0, Object value0, String key1, Object value1) {
		return find(max, QueryParams.create().add(key0, value0).add(key1, value1));
	}

	public List find(int max, String key0, Object value0, String key1, Object value1, Order order) {
		return find(max, QueryParams.create().add(key0, value0).add(key1, value1), order);
	}

	public List find(int max, QueryParams params) {
		return find(max, params, ((Order) (null)));
	}

	public abstract List find(int i, QueryParams queryparams, Order order);

	public EntityObject findObject() {
		return findObject((QueryParams)null);
	}

	public EntityObject findObject(String key, Object value) {
		return findObject(QueryParams.single(key, value));
	}

	public EntityObject findObject(String key0, Object value0, String key1, Object value1) {
		return findObject(QueryParams.create().add(key0, value0).add(key1, value1));
	}

	public EntityObject findObject(String key0, Object value0, String key1, Object value1, String key2, Object value2) {
		return findObject(QueryParams.create().add(key0, value0).add(key1, value1).add(key2, value2));
	}

	public EntityObject findObject(QueryParams params) {
		List results = find(1, params);
		return (EntityObject)DataAccessUtils.singleResult(results);
	}

	public EntityObject findObject(Select select) {
		List results = find(1, select);
		return (EntityObject)DataAccessUtils.singleResult(results);
	}
}
