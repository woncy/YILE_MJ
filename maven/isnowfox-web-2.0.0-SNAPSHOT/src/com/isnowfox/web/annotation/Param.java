// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   Param.java

package com.isnowfox.web.annotation;

import com.isnowfox.web.ParameterType;
import java.lang.annotation.Annotation;

public interface Param
	extends Annotation {

	public abstract ParameterType value();

	public abstract String name();
}
