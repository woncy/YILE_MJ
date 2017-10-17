// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   OBBVector.java

package com.isnowfox.core.geom.obb;

import java.awt.geom.Point2D;

public final class OBBVector extends java.awt.geom.Point2D.Double {

	public OBBVector() {
	}

	public OBBVector(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public OBBVector sub(OBBVector v) {
		return new OBBVector(x - v.x, y - v.y);
	}

	public OBBVector sub(OBBVector v, OBBVector target) {
		target.x = x - v.x;
		target.y = y - v.y;
		return v;
	}

	public double dot(OBBVector v) {
		return x * v.x + y * v.y;
	}

	public void set(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getLenth() {
		return Math.sqrt(x * x + y * y);
	}

	public String toString() {
		return (new StringBuilder()).append("Vector [x=").append(x).append(", y=").append(y).append("]").toString();
	}
}
