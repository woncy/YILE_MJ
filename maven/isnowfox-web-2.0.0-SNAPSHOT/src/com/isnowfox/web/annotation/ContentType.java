// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   ContentType.java

package com.isnowfox.web.annotation;

import java.lang.annotation.Annotation;

public interface ContentType
	extends Annotation {

	public static final String TEXT_HTML = "text/html";

	public abstract String value();
}
