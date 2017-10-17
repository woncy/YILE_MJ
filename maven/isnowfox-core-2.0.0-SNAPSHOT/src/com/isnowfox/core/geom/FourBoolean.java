// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   FourBoolean.java

package com.isnowfox.core.geom;


public class FourBoolean {

	public boolean top;
	public boolean right;
	public boolean bottom;
	public boolean left;

	public FourBoolean() {
	}

	public FourBoolean(boolean top, boolean right, boolean bottom, boolean left) {
		this.top = top;
		this.right = right;
		this.bottom = bottom;
		this.left = left;
	}

	public void setTo(boolean top, boolean right, boolean bottom, boolean left) {
		this.top = top;
		this.right = right;
		this.bottom = bottom;
		this.left = left;
	}

	public void topBottom(boolean top, boolean bottom) {
		this.top = top;
		this.bottom = bottom;
	}

	public void rightLeft(boolean right, boolean left) {
		this.right = right;
		this.left = left;
	}

	public String toString() {
		return (new StringBuilder()).append("FourBoolean{top=").append(top).append(", right=").append(right).append(", bottom=").append(bottom).append(", left=").append(left).append('}').toString();
	}
}
