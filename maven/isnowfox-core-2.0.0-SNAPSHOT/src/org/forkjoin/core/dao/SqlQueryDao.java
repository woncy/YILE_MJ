// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   SqlQueryDao.java

package org.forkjoin.core.dao;

import com.isnowfox.core.PageResult;
import org.forkjoin.core.dao.grid.Columns;
import org.forkjoin.core.dao.grid.GridPageResult;

public interface SqlQueryDao {

	public abstract GridPageResult query(String s, int i, int j);

	public abstract PageResult queryData(Columns columns, String s, int i, int j);
}
