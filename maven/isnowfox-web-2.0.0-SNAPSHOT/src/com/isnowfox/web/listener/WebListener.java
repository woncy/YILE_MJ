// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   WebListener.java

package com.isnowfox.web.listener;


// Referenced classes of package com.isnowfox.web.listener:
//			WebContextEvent, WebResponseEvent, WebRequestEvent

public interface WebListener {

	public abstract void contextInitialized(WebContextEvent webcontextevent);

	public abstract void contextDestroyed(WebContextEvent webcontextevent);

	public abstract void response(WebResponseEvent webresponseevent);

	public abstract void requestHandlerStart(WebRequestEvent webrequestevent);

	public abstract void requestHandlerEnd(WebRequestEvent webrequestevent);
}
