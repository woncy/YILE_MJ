// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   BoundsQuadTree.java

package com.isnowfox.core.geom;

import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.isnowfox.core.geom:
//			Rectangle, BoundsObject

public class BoundsQuadTree {

	public static final int DEFAULT_OBJECTS_MAX = 10;
	public static final int DEFAULT_MIN_WIDTH = 240;
	private int objectsMax;
	private int minWidth;
	private List objects;
	private List childObjects;
	private Rectangle bounds;
	private BoundsQuadTree nodes[];

	public BoundsQuadTree(int width, int height) {
		this(new Rectangle(width, height), 10, 240);
	}

	public BoundsQuadTree(int width, int height, int objectsMax) {
		this(new Rectangle(width, height), objectsMax, 240);
	}

	public BoundsQuadTree(int width, int height, int objectsMax, int minWidth) {
		this(new Rectangle(width, height), objectsMax, minWidth);
	}

	private BoundsQuadTree(Rectangle pBounds, int objectsMax, int minWidth) {
		this.objectsMax = 10;
		this.minWidth = minWidth;
		objects = new ArrayList();
		childObjects = new ArrayList();
		bounds = pBounds;
		nodes = new BoundsQuadTree[4];
		this.objectsMax = objectsMax;
	}

	public void clear() {
		objects.clear();
		childObjects.clear();
		for (int i = 0; i < nodes.length; i++) {
			if (nodes[i] != null) {
				nodes[i].clear();
			}
		}

	}

	private void split() {
		int subWidth = (int)bounds.getWidth() >> 1;
		int subHeight = (int)bounds.getHeight() >> 1;
		int x = (int)bounds.getX();
		int y = (int)bounds.getY();
		nodes[0] = new BoundsQuadTree(new Rectangle(x + subWidth, y, subWidth, subHeight), objectsMax, minWidth);
		nodes[1] = new BoundsQuadTree(new Rectangle(x, y, subWidth, subHeight), objectsMax, minWidth);
		nodes[2] = new BoundsQuadTree(new Rectangle(x, y + subHeight, subWidth, subHeight), objectsMax, minWidth);
		nodes[3] = new BoundsQuadTree(new Rectangle(x + subWidth, y + subHeight, subWidth, subHeight), objectsMax, minWidth);
	}

	private int getIndex(Rectangle pRect) {
		int index = -1;
		double verticalMidpoint = bounds.getX() + bounds.getWidth() / 2D;
		double horizontalMidpoint = bounds.getY() + bounds.getHeight() / 2D;
		double pRectY = pRect.getY();
		boolean topQuadrant = pRectY < horizontalMidpoint && pRectY + pRect.getHeight() < horizontalMidpoint;
		boolean bottomQuadrant = pRectY > horizontalMidpoint;
		double pRectX = pRect.getX();
		if (pRectX < verticalMidpoint && pRectX + pRect.getWidth() < verticalMidpoint) {
			if (topQuadrant) {
				index = 1;
			} else
			if (bottomQuadrant) {
				index = 2;
			}
		} else
		if (pRectX > verticalMidpoint) {
			if (topQuadrant) {
				index = 0;
			} else
			if (bottomQuadrant) {
				index = 3;
			}
		}
		return index;
	}

	public void insert(BoundsObject pObj) {
		if (nodes[0] != null) {
			int index = getIndex(pObj.getBounds());
			if (index != -1) {
				childObjects.add(pObj);
				nodes[index].insert(pObj);
				return;
			}
		}
		objects.add(pObj);
		if (objects.size() > objectsMax && bounds.width > (double)minWidth) {
			if (nodes[0] == null) {
				split();
			}
			for (int i = 0; i < objects.size();) {
				int index = getIndex(((BoundsObject)objects.get(i)).getBounds());
				if (index != -1) {
					BoundsObject item = (BoundsObject)objects.remove(i);
					childObjects.add(item);
					nodes[index].insert(item);
				} else {
					i++;
				}
			}

		}
	}

	public void retrieve(List returnObjects, Rectangle pRect) {
		if (!objects.isEmpty()) {
			returnObjects.addAll(objects);
		}
		if (nodes[0] == null) {
			return;
		}
		int index = getIndex(pRect);
		if (index != -1) {
			nodes[index].retrieve(returnObjects, pRect);
		} else {
			returnObjects.addAll(childObjects);
		}
	}
}
