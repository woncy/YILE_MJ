// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   Matrix.java

package com.isnowfox.core.geom;

import java.awt.geom.Point2D;

public class Matrix {

	public double a;
	public double b;
	public double c;
	public double d;
	public double tx;
	public double ty;

	public Matrix() {
		a = d = 1.0D;
		b = c = 0.0D;
		tx = ty = 0.0D;
	}

	public Matrix(double a, double b, double c, double d, double tx, double ty) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.tx = tx;
		this.ty = ty;
	}

	public Matrix clone() {
		return new Matrix(a, b, c, d, tx, ty);
	}

	public void identity() {
		a = d = 1.0D;
		b = c = 0.0D;
		tx = ty = 0.0D;
	}

	public void rotate(double angle) {
		double u = Math.cos(angle);
		double v = Math.sin(angle);
		double result_a = u * a - v * b;
		double result_b = v * a + u * b;
		double result_c = u * c - v * d;
		double result_d = v * c + u * d;
		double result_tx = u * tx - v * ty;
		double result_ty = v * tx + u * ty;
		a = result_a;
		b = result_b;
		c = result_c;
		d = result_d;
		tx = result_tx;
		ty = result_ty;
	}

	public void translate(double dx, double dy) {
		tx = tx + dx;
		ty = ty + dy;
	}

	public void scale(double sx, double sy) {
		a = a * sx;
		b = b * sy;
		c = c * sx;
		d = d * sy;
		tx = tx * sx;
		ty = ty * sy;
	}

	public Point2D transformPoint(Point2D point) {
		return new java.awt.geom.Point2D.Double(a * point.getX() + c * point.getY() + tx, d * point.getY() + b * point.getX() + ty);
	}

	public void transformPointSet(Point2D point) {
		point.setLocation(a * point.getX() + c * point.getY() + tx, d * point.getY() + b * point.getX() + ty);
	}

	public String toString() {
		return (new StringBuilder()).append("(a=").append(a).append(", b=").append(b).append(", c=").append(c).append(", d=").append(d).append(", tx=").append(tx).append(", ty=").append(ty).append(")").toString();
	}

	public volatile Object clone() throws CloneNotSupportedException {
		return clone();
	}
}
