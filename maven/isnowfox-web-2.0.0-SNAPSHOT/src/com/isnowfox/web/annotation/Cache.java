// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   Cache.java

package com.isnowfox.web.annotation;

import com.isnowfox.web.CacheType;
import java.lang.annotation.Annotation;

public interface Cache
	extends Annotation {

	public abstract CacheType type();

	public abstract String value();
}
