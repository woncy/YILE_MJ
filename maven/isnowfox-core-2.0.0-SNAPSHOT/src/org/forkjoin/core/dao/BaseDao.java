// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   BaseDao.java

package org.forkjoin.core.dao;

import java.util.List;
import java.util.Map;

// Referenced classes of package org.forkjoin.core.dao:
//			ReadOnlyDao, EntityObject, KeyObject

public interface BaseDao
	extends ReadOnlyDao {

	public abstract long insert(EntityObject entityobject);

	public abstract long replace(EntityObject entityobject);

	public abstract boolean update(EntityObject entityobject);

	public abstract int insert(List list);

	public abstract boolean del(KeyObject keyobject);

	public abstract boolean del(String s, Object obj);

	public abstract boolean incrementUpdatePartial(String s, Object obj, KeyObject keyobject);

	public abstract boolean incrementUpdatePartial(String s, Object obj, String s1, Object obj1, KeyObject keyobject);

	public abstract boolean incrementUpdatePartial(Map map, KeyObject keyobject);

	public abstract boolean updatePartial(String s, Object obj, KeyObject keyobject);

	public abstract boolean updatePartial(String s, Object obj, String s1, Object obj1, KeyObject keyobject);

	public abstract boolean updatePartial(String s, Object obj, String s1, Object obj1, String s2, Object obj2, KeyObject keyobject);

	public abstract boolean updatePartial(Map map, KeyObject keyobject);

	public abstract int updatePartial(Map map, String s, Object obj);

	public abstract int updatePartial(Map map, String s, Object obj, String s1, Object obj1);

	public abstract int updatePartial(Map map, Map map1);
}
