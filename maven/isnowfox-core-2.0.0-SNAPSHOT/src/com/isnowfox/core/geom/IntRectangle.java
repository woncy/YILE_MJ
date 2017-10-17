// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   IntRectangle.java

package com.isnowfox.core.geom;


public final class IntRectangle {

	public int x;
	public int y;
	public int width;
	public int height;

	public IntRectangle(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public IntRectangle(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public IntRectangle() {
	}

	public IntRectangle(IntRectangle intRectangle) {
		x = intRectangle.x;
		y = intRectangle.y;
		width = intRectangle.width;
		height = intRectangle.height;
	}

	public void set(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public int getRight() {
		return x + width;
	}

	public int getBottom() {
		return y + height;
	}

	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean contains(int x, int y) {
		if (x < this.x || y < this.y) {
			return false;
		}
		return x < this.x + width && y < this.y + height;
	}

	public IntRectangle clone() {
		return (IntRectangle)super.clone();
		CloneNotSupportedException e;
		e;
		return new IntRectangle(this);
	}

	public String toString() {
		return (new StringBuilder()).append("Rectangle [x=").append(x).append(", y=").append(y).append(", width=").append(width).append(", height=").append(height).append("]").toString();
	}

	public volatile Object clone() throws CloneNotSupportedException {
		return clone();
	}
}
