// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   ViewConfig.java

package com.isnowfox.web.config;

import com.isnowfox.web.ViewType;
import com.isnowfox.web.ViewTypeInterface;
import java.util.*;

// Referenced classes of package com.isnowfox.web.config:
//			IllegalConfigException

public class ViewConfig {
	private static class Item {

		private ViewTypeInterface viewType;
		private Class cls;
		private String key;
		private String value;



		public Item(ViewTypeInterface viewType, Class cls, String key, String value) {
			this.viewType = viewType;
			this.cls = cls;
			this.key = key;
			this.value = value;
		}
	}

	public static final class MapType extends Enum {

		public static final MapType CLASS;
		public static final MapType VIEW;
		public static final MapType ONLY;
		public static final MapType EL;
		public static final MapType NULL;
		private static final MapType $VALUES[];

		public static MapType[] values() {
			return (MapType[])$VALUES.clone();
		}

		public static MapType valueOf(String name) {
			return (MapType)Enum.valueOf(com/isnowfox/web/config/ViewConfig$MapType, name);
		}

		static  {
			CLASS = new MapType("CLASS", 0);
			VIEW = new MapType("VIEW", 1);
			ONLY = new MapType("ONLY", 2);
			EL = new MapType("EL", 3);
			NULL = new MapType("NULL", 4);
			$VALUES = (new MapType[] {
				CLASS, VIEW, ONLY, EL, NULL
			});
		}

		private MapType(String s, int i) {
			super(s, i);
		}
	}


	private MapType mapType;
	private List list;
	private ViewTypeInterface viewType;
	private String el;
	private String value;

	public ViewConfig() {
		mapType = MapType.NULL;
		list = new ArrayList();
	}

	ViewConfig addClassMap(Class cls, String value, ViewTypeInterface viewType) throws IllegalConfigException {
		check(MapType.CLASS);
		add(new Item(viewType, cls, null, value));
		return this;
	}

	ViewConfig addViewMap(String name, String value, ViewTypeInterface viewType) throws IllegalConfigException {
		if (mapType == MapType.VIEW) {
			check(MapType.VIEW);
			add(new Item(viewType, null, name, value));
		} else {
			check(MapType.EL);
			add(new Item(viewType, null, name, value));
		}
		return this;
	}

	ViewConfig el(String el) throws IllegalConfigException {
		check(MapType.EL);
		this.el = el;
		return this;
	}

	void Json() throws IllegalConfigException {
		viewType(ViewType.JSON, null);
	}

	void stream() throws IllegalConfigException {
		viewType(ViewType.STREAM, null);
	}

	void viewType(ViewTypeInterface viewType, String value) throws IllegalConfigException {
		check(MapType.ONLY);
		if (list.isEmpty() && this.viewType == null) {
			this.viewType = viewType;
			this.value = value;
		} else {
			throw new IllegalConfigException("单类型映射!不能设置多个");
		}
	}

	void add(Item i) {
		if (i.value.length() > 0) {
			if (i.value.charAt(0) != '/') {
				i.value = (new StringBuilder()).append('/').append(i.value).toString();
			} else {
				i.value = i.value;
			}
		}
		list.add(i);
	}

	private void check(MapType type) throws IllegalConfigException {
		if (mapType == MapType.NULL || type == mapType) {
			mapType = type;
		} else {
			throw new IllegalConfigException("只能有一个类型的映射");
		}
	}

	public MapType getMapType() {
		return mapType;
	}

	public List getItems() {
		return Collections.unmodifiableList(list);
	}

	public String getEl() {
		return el;
	}

	public ViewTypeInterface getViewType() {
		return viewType;
	}

	public String getValue() {
		return value;
	}
}
