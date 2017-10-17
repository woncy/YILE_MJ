// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   HeaderConfig.java

package com.isnowfox.web.config;

import com.isnowfox.web.annotation.Header;
import com.isnowfox.web.annotation.HeaderGroup;
import java.util.ArrayList;
import java.util.List;

public class HeaderConfig {
	public static class Item {

		public final String name;
		public final String value;

		public String toString() {
			return (new StringBuilder()).append("Item [name=").append(name).append(", value=").append(value).append("]").toString();
		}

		public Item(String name, String value) {
			this.name = name;
			this.value = value;
		}
	}


	private List list;

	public HeaderConfig() {
		list = new ArrayList();
	}

	public void add(String name, String value) {
		list.add(new Item(name, value));
	}

	public void add(Header header) {
		list.add(new Item(header.name(), header.value()));
	}

	public void add(HeaderGroup headerGroup) {
		Header aheader[] = headerGroup.value();
		int i = aheader.length;
		for (int j = 0; j < i; j++) {
			Header header = aheader[j];
			add(header);
		}

	}

	public List getList() {
		return list;
	}

	public String toString() {
		return (new StringBuilder()).append("HeaderConfig [list=").append(list).append("]").toString();
	}
}
