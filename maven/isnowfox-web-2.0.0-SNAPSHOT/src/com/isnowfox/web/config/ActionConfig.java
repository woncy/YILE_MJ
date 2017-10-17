// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   ActionConfig.java

package com.isnowfox.web.config;

import com.isnowfox.web.*;

// Referenced classes of package com.isnowfox.web.config:
//			HeaderConfig, ParamsConfig, ViewConfig, IllegalConfigException, 
//			CacheConfig

public class ActionConfig {
	public static final class LiefCycleType extends Enum {

		public static final LiefCycleType SINGLETON;
		public static final LiefCycleType REQUEST;
		private static final LiefCycleType $VALUES[];

		public static LiefCycleType[] values() {
			return (LiefCycleType[])$VALUES.clone();
		}

		public static LiefCycleType valueOf(String name) {
			return (LiefCycleType)Enum.valueOf(com/isnowfox/web/config/ActionConfig$LiefCycleType, name);
		}

		static  {
			SINGLETON = new LiefCycleType("SINGLETON", 0);
			REQUEST = new LiefCycleType("REQUEST", 1);
			$VALUES = (new LiefCycleType[] {
				SINGLETON, REQUEST
			});
		}

		private LiefCycleType(String s, int i) {
			super(s, i);
		}
	}


	private LiefCycleType liefCycle;
	private String pattern;
	private String method;
	private Class actionClass;
	private CacheConfig cache;
	private HeaderConfig headerConfig;
	private ParamsConfig paramsConfig;
	private ViewConfig viewConfig;
	private Config config;

	public ActionConfig(Class actionClass, String method, String pattern, Config config, LiefCycleType liefCycle) {
		headerConfig = new HeaderConfig();
		paramsConfig = new ParamsConfig();
		viewConfig = new ViewConfig();
		this.actionClass = actionClass;
		if (pattern.length() > 0) {
			if (pattern.charAt(0) != '/') {
				this.pattern = (new StringBuilder()).append('/').append(pattern).toString();
			} else {
				this.pattern = pattern;
			}
		} else {
			this.pattern = pattern;
		}
		this.method = method;
		this.config = config;
		this.liefCycle = liefCycle;
	}

	public ActionConfig param(Class cls, ParameterType type, String name) {
		paramsConfig.add(cls, type, name);
		return this;
	}

	public ActionConfig param(Class cls, String name) {
		paramsConfig.add(cls, ParameterType.REQUEST, name);
		return this;
	}

	public ActionConfig header(String name, String value) {
		headerConfig.add(name, value);
		return this;
	}

	public HeaderConfig getHeaderConfig() {
		return headerConfig;
	}

	public ActionConfig cache(CacheType type, String value) throws IllegalConfigException {
		if (cache != null) {
			throw new IllegalConfigException("´íÎóµÄ²ÎÊý!");
		} else {
			cache = new CacheConfig(type, value);
			return this;
		}
	}

	public ActionConfig noCache() throws IllegalConfigException {
		cache(CacheType.NO_CACHE, null);
		return this;
	}

	public ActionConfig view(Class cls, String value) throws IllegalConfigException {
		viewConfig.addClassMap(cls, value, config.getDefaultViewType());
		return this;
	}

	public ActionConfig view(Class cls, String value, ViewTypeInterface viewType) throws IllegalConfigException {
		viewConfig.addClassMap(cls, value, viewType);
		return this;
	}

	public ActionConfig viewJson(Class cls) throws IllegalConfigException {
		viewConfig.addClassMap(cls, null, ViewType.JSON);
		return this;
	}

	public ActionConfig view(Class cls, ViewTypeInterface viewType) throws IllegalConfigException {
		viewConfig.addClassMap(cls, null, viewType);
		return this;
	}

	public ActionConfig view(String name, String value) throws IllegalConfigException {
		viewConfig.addViewMap(name, value, config.getDefaultViewType());
		return this;
	}

	public ActionConfig view(String name, String value, ViewTypeInterface viewType) throws IllegalConfigException {
		viewConfig.addViewMap(name, value, viewType);
		return this;
	}

	public ActionConfig viewJson(String name) throws IllegalConfigException {
		viewConfig.addViewMap(name, null, ViewType.JSON);
		return this;
	}

	public ActionConfig view(String name, ViewTypeInterface viewType) throws IllegalConfigException {
		viewConfig.addViewMap(name, null, viewType);
		return this;
	}

	public ActionConfig view(ViewTypeInterface viewType, String value) throws IllegalConfigException {
		viewConfig.viewType(viewType, value);
		return this;
	}

	public ActionConfig view(ViewTypeInterface viewType) throws IllegalConfigException {
		viewConfig.viewType(viewType, null);
		return this;
	}

	public ActionConfig el(String el) throws IllegalConfigException {
		viewConfig.el(el);
		return this;
	}

	public ActionConfig json() throws IllegalConfigException {
		viewConfig.Json();
		return this;
	}

	public ActionConfig stream() throws IllegalConfigException {
		viewConfig.stream();
		return this;
	}

	public Class getActionClass() {
		return actionClass;
	}

	public String getPattern() {
		return pattern;
	}

	public String getMethod() {
		return method;
	}

	public ParamsConfig getParamsConfig() {
		return paramsConfig;
	}

	public LiefCycleType getLiefCycle() {
		return liefCycle;
	}

	public ViewConfig getViewConfig() {
		return viewConfig;
	}
}
