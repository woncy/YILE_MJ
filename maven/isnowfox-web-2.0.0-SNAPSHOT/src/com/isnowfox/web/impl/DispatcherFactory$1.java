// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   DispatcherFactory.java

package com.isnowfox.web.impl;

import com.isnowfox.web.*;
import com.isnowfox.web.codec.Uri;

// Referenced classes of package com.isnowfox.web.impl:
//			BaseDispatcher, DispatcherFactory

static class DispatcherFactory$1 extends BaseDispatcher {

	public boolean disposeStaticFile(Uri uri, Response resp) {
		return false;
	}

	DispatcherFactory$1(Config config, Server server) {
		super(config, server);
	}
}
