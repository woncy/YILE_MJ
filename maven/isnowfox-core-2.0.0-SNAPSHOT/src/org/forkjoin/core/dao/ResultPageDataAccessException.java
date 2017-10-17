// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   ResultPageDataAccessException.java

package org.forkjoin.core.dao;

import org.springframework.dao.DataAccessException;

public class ResultPageDataAccessException extends DataAccessException {

	private static final long serialVersionUID = 0x1b8a0d38cc35e79eL;

	public ResultPageDataAccessException(String msg) {
		super(msg);
	}
}
