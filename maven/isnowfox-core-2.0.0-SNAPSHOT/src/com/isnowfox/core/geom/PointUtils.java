// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   PointUtils.java

package com.isnowfox.core.geom;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.RandomAccess;

public final class PointUtils {

	public PointUtils() {
	}

	public static final double len(Point2D p) {
		return Math.sqrt(p.getX() * p.getX() + p.getY() * p.getY());
	}

	public static final void normalize(Point2D p, double len) {
		double invD = len(p);
		if (invD > 0.0D) {
			invD = len / invD;
			p.setLocation(p.getX() * invD, p.getY() * invD);
		}
	}

	public static final java.awt.geom.Point2D.Double polarDouble(double len, double angle) {
		return new java.awt.geom.Point2D.Double(len * Math.cos(angle), len * Math.sin(angle));
	}

	public static final void polarDouble(Point2D p, double len, double angle) {
		p.setLocation(len * Math.cos(angle), len * Math.sin(angle));
	}

	public static int[] toIntArray(java.util.List path) {
		if (path == null) {
			return null;
		}
		int intArray[] = new int[path.size() * 2];
		if (path instanceof RandomAccess) {
			int i = 0;
			int j = 0;
			for (; i < path.size(); i++) {
				Point p = (Point)path.get(i);
				intArray[j++] = p.x;
				intArray[j++] = p.y;
			}

		} else {
			int j = 0;
			for (Iterator iterator = path.iterator(); iterator.hasNext();) {
				Point p = (Point)iterator.next();
				intArray[j++] = p.x;
				intArray[j++] = p.y;
			}

		}
		return intArray;
	}

	public static LinkedList pathArrayToList(int path[]) {
		if (path == null) {
			return null;
		}
		int length = path.length;
		if (length % 2 != 0) {
			throw new RuntimeException("错误的长度，长度必须是2的整数倍");
		}
		LinkedList list = new LinkedList();
		for (int j = 0; j < length;) {
			Point p = new Point();
			p.x = path[j++];
			p.y = path[j++];
			list.add(p);
		}

		return list;
	}
}
