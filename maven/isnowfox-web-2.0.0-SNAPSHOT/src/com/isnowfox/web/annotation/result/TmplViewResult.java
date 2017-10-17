// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   TmplViewResult.java

package com.isnowfox.web.annotation.result;

import java.lang.annotation.Annotation;

public interface TmplViewResult
	extends Annotation {

	public static final String JSON = "json";
	public static final String HTTL = "httl";

	public abstract String type();

	public abstract String name();

	public abstract String value();
}
