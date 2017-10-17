// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   Dispatcher.java

package com.isnowfox.web;

import com.isnowfox.web.codec.Uri;

// Referenced classes of package com.isnowfox.web:
//			Response, Request

public interface Dispatcher {

	public abstract boolean disposeStaticFile(Uri uri, Response response) throws Exception;

	public abstract void service(Uri uri, Request request, Response response);
}
