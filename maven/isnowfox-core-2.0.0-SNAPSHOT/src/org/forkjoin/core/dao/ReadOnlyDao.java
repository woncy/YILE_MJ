// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   ReadOnlyDao.java

package org.forkjoin.core.dao;

import com.isnowfox.core.PageResult;
import java.util.List;
import org.springframework.jdbc.core.RowMapper;

// Referenced classes of package org.forkjoin.core.dao:
//			KeyObject, EntityObject, QueryParams, Select, 
//			Order

public interface ReadOnlyDao {

	public abstract EntityObject get(KeyObject keyobject);

	public abstract long getCount();

	public abstract long getCount(QueryParams queryparams);

	public abstract long getCount(Select select);

	public abstract PageResult findPage(int i, int j);

	public abstract PageResult findPage(Order order, int i, int j);

	public abstract PageResult findPage(QueryParams queryparams, int i, int j);

	public abstract PageResult findPage(QueryParams queryparams, Order order, int i, int j);

	public abstract PageResult findPage(Select select, int i, int j);

	public abstract PageResult findPageBySelect(Select select, RowMapper rowmapper, int i, int j);

	public abstract List find(int i);

	public abstract List find(int i, String s, Object obj);

	public abstract List find(int i, String s, Object obj, Order order);

	public abstract List find(int i, String s, Object obj, String s1, Object obj1);

	public abstract List find(int i, String s, Object obj, String s1, Object obj1, Order order);

	public abstract List find(int i, QueryParams queryparams);

	public abstract List find(int i, QueryParams queryparams, Order order);

	public abstract List find(int i, Select select);

	public abstract List findBySelect(int i, Select select, RowMapper rowmapper);

	public abstract EntityObject findObject();

	public abstract EntityObject findObject(String s, Object obj);

	public abstract EntityObject findObject(String s, Object obj, String s1, Object obj1);

	public abstract EntityObject findObject(String s, Object obj, String s1, Object obj1, String s2, Object obj2);

	public abstract EntityObject findObject(QueryParams queryparams);

	public abstract EntityObject findObject(Select select);
}
