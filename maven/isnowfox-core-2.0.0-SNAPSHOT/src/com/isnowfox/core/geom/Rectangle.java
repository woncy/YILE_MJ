// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   Rectangle.java

package com.isnowfox.core.geom;


public final class Rectangle {

	public double x;
	public double y;
	public double width;
	public double height;

	public Rectangle(double x, double y, double width, double height) {
		this.x = x;
		this.x = y;
		this.width = width;
		this.height = height;
	}

	public Rectangle(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public Rectangle() {
	}

	public void set(double x, double y, double width, double height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public double getRight() {
		return x + width;
	}

	public double getBottom() {
		return y + height;
	}

	public void setLocation(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public String toString() {
		return (new StringBuilder()).append("Rectangle [x=").append(x).append(", y=").append(y).append(", width=").append(width).append(", height=").append(height).append("]").toString();
	}

	public boolean contains(double x, double y, double w, double h) {
		if (isEmpty() || w <= 0.0D || h <= 0.0D) {
			return false;
		} else {
			double x0 = getX();
			double y0 = getY();
			return x >= x0 && y >= y0 && x + w <= x0 + getWidth() && y + h <= y0 + getHeight();
		}
	}

	public boolean intersects(double x, double y, double w, double h) {
		if (isEmpty() || w <= 0.0D || h <= 0.0D) {
			return false;
		} else {
			double x0 = getX();
			double y0 = getY();
			return x + w > x0 && y + h > y0 && x < x0 + getWidth() && y < y0 + getHeight();
		}
	}

	public boolean isEmpty() {
		return width <= 0.0D || height <= 0.0D;
	}
}
