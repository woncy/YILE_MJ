// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   AbstractMessage.java

package com.isnowfox.core.net.message;

import com.isnowfox.core.net.Session;

// Referenced classes of package com.isnowfox.core.net.message:
//			Message

public abstract class AbstractMessage
	implements Message {

	private Session session;

	public AbstractMessage() {
	}

	public final Session getSession() {
		return session;
	}

	public final void setSession(Session session) {
		this.session = session;
	}
}
