// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   Response.java

package com.isnowfox.web;

import java.io.IOException;
import java.io.Writer;
import org.jboss.netty.handler.codec.http.Cookie;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;

public interface Response {

	public abstract void flushAndClose() throws IOException;

	public abstract void setContentType(String s);

	public abstract String getContentType();

	public abstract Writer getWriter();

	public abstract Appendable getAppendable();

	public abstract void oneWrite(byte abyte0[]);

	public abstract void sendError(int i);

	public abstract void sendError(HttpResponseStatus httpresponsestatus);

	public abstract void sendError(HttpResponseStatus httpresponsestatus, String s);

	public abstract void sendError(HttpResponseStatus httpresponsestatus, String s, Throwable throwable);

	public abstract void sendError(int i, String s);

	public abstract void sendRedirect(String s);

	public abstract void addHeader(String s, Object obj);

	public abstract void setHeader(String s, Object obj);

	public abstract void setHeader(String s, Iterable iterable);

	public abstract void removeHeader(String s);

	public abstract void clearHeaders();

	public abstract void add(Cookie cookie);

	public abstract void addCookie(String s, String s1);
}
