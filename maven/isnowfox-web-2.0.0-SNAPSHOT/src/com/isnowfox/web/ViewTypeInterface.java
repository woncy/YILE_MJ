// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   ViewTypeInterface.java

package com.isnowfox.web;


// Referenced classes of package com.isnowfox.web:
//			Config, Request, Response

public interface ViewTypeInterface {

	public abstract void init(Config config) throws Exception;

	public abstract void doView(String s, Object obj, Object obj1, String s1, Request request, Response response) throws Exception;
}
