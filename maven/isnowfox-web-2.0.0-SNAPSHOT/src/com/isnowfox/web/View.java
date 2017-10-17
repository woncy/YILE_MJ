// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   View.java

package com.isnowfox.web;


public class View {

	public static final String VIEW_DEFAULT_NAME = "default";
	private String name;
	private Object value;

	private View() {
		name = "default";
	}

	private View(Object value) {
		name = "default";
		this.value = value;
	}

	private View(String name) {
		this.name = "default";
		this.name = name;
	}

	private View(String name, Object value) {
		this.name = "default";
		this.name = name;
		this.value = value;
	}

	public static final View view() {
		return new View();
	}

	public static final View view(String name, Object value) {
		return new View(name, value);
	}

	public static final View view(String name) {
		return new View(name);
	}

	public static final View view(Object value) {
		return new View(value);
	}

	public String getName() {
		return name;
	}

	public View setName(String name) {
		this.name = name;
		return this;
	}

	public Object getValue() {
		return value;
	}

	public View setValue(Object value) {
		this.value = value;
		return this;
	}
}
