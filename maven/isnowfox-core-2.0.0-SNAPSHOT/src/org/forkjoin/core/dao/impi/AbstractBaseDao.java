// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   AbstractBaseDao.java

package org.forkjoin.core.dao.impi;

import com.google.common.collect.ImmutableMap;
import java.util.*;
import org.forkjoin.core.dao.*;

// Referenced classes of package org.forkjoin.core.dao.impi:
//			AbstractReadOnlyDao

public abstract class AbstractBaseDao extends AbstractReadOnlyDao
	implements BaseDao {

	protected final TableInfo tableInfo;

	public AbstractBaseDao(TableInfo tableInfo) {
		this.tableInfo = tableInfo;
	}

	public int insert(List list) {
		EntityObject t;
		for (Iterator iterator = list.iterator(); iterator.hasNext(); insert(t)) {
			t = (EntityObject)iterator.next();
		}

		return list.size();
	}

	public boolean incrementUpdatePartial(String name0, Object value0, KeyObject key) {
		return incrementUpdatePartial(Collections.singletonMap(name0, value0), key);
	}

	public boolean incrementUpdatePartial(String name0, Object value0, String name1, Object value1, KeyObject key) {
		ImmutableMap map = ImmutableMap.of(name0, value0, name1, value1);
		return incrementUpdatePartial(((Map) (map)), key);
	}

	public abstract boolean incrementUpdatePartial(Map map, KeyObject keyobject);

	public boolean updatePartial(String name, Object value, KeyObject key) {
		return updatePartial(Collections.singletonMap(name, value), key);
	}

	public boolean updatePartial(String name0, Object value0, String name1, Object value1, KeyObject key) {
		return updatePartial(((Map) (ImmutableMap.of(name0, value0, name1, value1))), key);
	}

	public boolean updatePartial(String name0, Object value0, String name1, Object value1, String name2, Object value2, KeyObject key) {
		return updatePartial(((Map) (ImmutableMap.of(name0, value0, name1, value1, name2, value2))), key);
	}

	public int updatePartial(Map m, String key0, Object keyValue0) {
		return updatePartial(m, Collections.singletonMap(key0, keyValue0));
	}

	public int updatePartial(Map m, String key0, Object keyValue0, String key1, Object keyValue1) {
		return updatePartial(m, ((Map) (ImmutableMap.of(key0, keyValue0, key1, keyValue1))));
	}

	public abstract boolean updatePartial(Map map, KeyObject keyobject);
}
