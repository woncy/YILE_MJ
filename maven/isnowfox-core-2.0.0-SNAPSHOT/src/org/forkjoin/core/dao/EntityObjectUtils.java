// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   EntityObjectUtils.java

package org.forkjoin.core.dao;

import com.google.common.collect.ImmutableMap;
import com.isnowfox.util.JsonUtils;
import java.util.*;

// Referenced classes of package org.forkjoin.core.dao:
//			EntityProperty, EntityObject, TableInfo

public class EntityObjectUtils {

	public EntityObjectUtils() {
	}

	public static Map encodeStringMap(EntityObject o) {
		com.google.common.collect.ImmutableMap.Builder builder = ImmutableMap.builder();
		Collection properties = o.getTableInfo().getEntityProperties();
		EntityProperty pro;
		String valueStr;
		for (Iterator iterator = properties.iterator(); iterator.hasNext(); builder.put(pro.getDbName(), valueStr)) {
			pro = (EntityProperty)iterator.next();
			valueStr = JsonUtils.serialize(o.get(pro.getDbName()));
		}

		return builder.build();
	}

	public static EntityObject decodeStringMap(Map map, EntityObject t) {
		Collection properties = t.getTableInfo().getEntityProperties();
		Iterator iterator = properties.iterator();
		do {
			if (!iterator.hasNext()) {
				break;
			}
			EntityProperty pro = (EntityProperty)iterator.next();
			String valueStr = (String)map.get(pro.getDbName());
			if (valueStr != null) {
				Object value = JsonUtils.deserialize(valueStr, pro.getValueTypeRef());
				t.set(pro.getDbName(), value);
			}
		} while (true);
		return t;
	}
}
