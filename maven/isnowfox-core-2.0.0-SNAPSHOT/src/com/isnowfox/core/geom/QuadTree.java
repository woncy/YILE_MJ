// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   QuadTree.java

package com.isnowfox.core.geom;

import java.awt.Rectangle;
import java.util.ArrayList;

public class QuadTree {

	private int MAX_OBJECTS;
	private int MAX_LEVELS;
	private int level;
	private java.util.List objects;
	private Rectangle bounds;
	private QuadTree nodes[];

	public QuadTree(Rectangle pBounds) {
		this(0, pBounds);
	}

	private QuadTree(int pLevel, Rectangle pBounds) {
		MAX_OBJECTS = 10;
		MAX_LEVELS = 5;
		level = pLevel;
		objects = new ArrayList();
		bounds = pBounds;
		nodes = new QuadTree[4];
	}

	public void clear() {
		objects.clear();
		for (int i = 0; i < nodes.length; i++) {
			if (nodes[i] != null) {
				nodes[i].clear();
				nodes[i] = null;
			}
		}

	}

	private void split() {
		int subWidth = (int)(bounds.getWidth() / 2D);
		int subHeight = (int)(bounds.getHeight() / 2D);
		int x = (int)bounds.getX();
		int y = (int)bounds.getY();
		nodes[0] = new QuadTree(level + 1, new Rectangle(x + subWidth, y, subWidth, subHeight));
		nodes[1] = new QuadTree(level + 1, new Rectangle(x, y, subWidth, subHeight));
		nodes[2] = new QuadTree(level + 1, new Rectangle(x, y + subHeight, subWidth, subHeight));
		nodes[3] = new QuadTree(level + 1, new Rectangle(x + subWidth, y + subHeight, subWidth, subHeight));
	}

	private int getIndex(Rectangle pRect) {
		int index = -1;
		double verticalMidpoint = bounds.getX() + bounds.getWidth() / 2D;
		double horizontalMidpoint = bounds.getY() + bounds.getHeight() / 2D;
		boolean topQuadrant = pRect.getY() < horizontalMidpoint && pRect.getY() + pRect.getHeight() < horizontalMidpoint;
		boolean bottomQuadrant = pRect.getY() > horizontalMidpoint;
		if (pRect.getX() < verticalMidpoint && pRect.getX() + pRect.getWidth() < verticalMidpoint) {
			if (topQuadrant) {
				index = 1;
			} else
			if (bottomQuadrant) {
				index = 2;
			}
		} else
		if (pRect.getX() > verticalMidpoint) {
			if (topQuadrant) {
				index = 0;
			} else
			if (bottomQuadrant) {
				index = 3;
			}
		}
		return index;
	}

	public void insert(Rectangle pRect) {
		if (nodes[0] != null) {
			int index = getIndex(pRect);
			if (index != -1) {
				nodes[index].insert(pRect);
				return;
			}
		}
		objects.add(pRect);
		if (objects.size() > MAX_OBJECTS && level < MAX_LEVELS) {
			if (nodes[0] == null) {
				split();
			}
			for (int i = 0; i < objects.size();) {
				int index = getIndex((Rectangle)objects.get(i));
				if (index != -1) {
					nodes[index].insert((Rectangle)objects.remove(i));
				} else {
					i++;
				}
			}

		}
	}

	public java.util.List retrieve(java.util.List returnObjects, Rectangle pRect) {
		int index = getIndex(pRect);
		if (index != -1 && nodes[0] != null) {
			nodes[index].retrieve(returnObjects, pRect);
		}
		returnObjects.addAll(objects);
		return returnObjects;
	}
}
