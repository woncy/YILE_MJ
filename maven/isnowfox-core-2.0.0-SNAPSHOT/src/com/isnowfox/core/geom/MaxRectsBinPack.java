// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   MaxRectsBinPack.java

package com.isnowfox.core.geom;

import java.util.ArrayList;

// Referenced classes of package com.isnowfox.core.geom:
//			IntRectangle

public class MaxRectsBinPack {
	public class FreeRectangleChoiceHeuristic {

		public static final int BestShortSideFit = 0;
		public static final int BestLongSideFit = 1;
		public static final int BestAreaFit = 2;
		public static final int BottomLeftRule = 3;
		public static final int ContactPointRule = 4;
		final MaxRectsBinPack this$0;

		public FreeRectangleChoiceHeuristic() {
			this.this$0 = MaxRectsBinPack.this;
			super();
		}
	}


	public int binWidth;
	public int binHeight;
	public boolean allowRotations;
	public ArrayList usedRectangles;
	public ArrayList freeRectangles;
	private int score1;
	private int score2;
	private int bestShortSideFit;
	private int bestLongSideFit;

	public MaxRectsBinPack(int width, int height) {
		this(width, height, true);
	}

	public MaxRectsBinPack(int width, int height, boolean rotations) {
		binWidth = 0;
		binHeight = 0;
		allowRotations = false;
		usedRectangles = new ArrayList();
		freeRectangles = new ArrayList();
		score1 = 0;
		score2 = 0;
		init(width, height, rotations);
	}

	private void init(int width, int height) {
		init(width, height, true);
	}

	private void init(int width, int height, boolean rotations) {
		if (count(width) % 1.0D != 0.0D || count(height) % 1.0D != 0.0D) {
			throw new Error("Must be 2,4,8,16,32,...512,1024,...");
		} else {
			binWidth = width;
			binHeight = height;
			allowRotations = rotations;
			IntRectangle n = new IntRectangle(0, 0, width, height);
			usedRectangles.clear();
			freeRectangles.clear();
			freeRectangles.add(n);
			return;
		}
	}

	private double count(double n) {
		if (n >= 2D) {
			return count(n / 2D);
		} else {
			return n;
		}
	}

	public IntRectangle insert(int width, int height, int method) {
		IntRectangle newNode = new IntRectangle();
		score1 = 0;
		score2 = 0;
		switch (method) {
		case 0: // '\0'
			newNode = findPositionForNewNodeBestShortSideFit(width, height);
			break;

		case 3: // '\003'
			newNode = findPositionForNewNodeBottomLeft(width, height, score1, score2);
			break;

		case 4: // '\004'
			newNode = findPositionForNewNodeContactPo(width, height, score1);
			break;

		case 1: // '\001'
			newNode = findPositionForNewNodeBestLongSideFit(width, height, score2, score1);
			break;

		case 2: // '\002'
			newNode = findPositionForNewNodeBestAreaFit(width, height, score1, score2);
			break;
		}
		if (newNode.getHeight() == 0) {
			return newNode;
		} else {
			placeRectangle(newNode);
			return newNode;
		}
	}

	private void insert2(ArrayList Rectangles, ArrayList dst, int method) {
		dst.clear();
		int bestRectangleIndex;
		for (; Rectangles.size() > 0; Rectangles.remove(bestRectangleIndex)) {
			int bestScore1 = 0x7fffffff;
			int bestScore2 = 0x7fffffff;
			bestRectangleIndex = -1;
			IntRectangle bestNode = new IntRectangle();
			for (int i = 0; i < Rectangles.size(); i++) {
				int score1 = 0;
				int score2 = 0;
				IntRectangle rect = (IntRectangle)Rectangles.get(i);
				IntRectangle newNode = scoreRectangle(rect.getWidth(), rect.getHeight(), method, score1, score2);
				if (score1 < bestScore1 || score1 == bestScore1 && score2 < bestScore2) {
					bestScore1 = score1;
					bestScore2 = score2;
					bestNode = newNode;
					bestRectangleIndex = i;
				}
			}

			if (bestRectangleIndex == -1) {
				return;
			}
			placeRectangle(bestNode);
		}

	}

	private void placeRectangle(IntRectangle node) {
		int numRectanglesToProcess = freeRectangles.size();
		for (int i = 0; i < numRectanglesToProcess; i++) {
			if (splitFreeNode((IntRectangle)freeRectangles.get(i), node)) {
				freeRectangles.remove(i);
				i--;
				numRectanglesToProcess--;
			}
		}

		pruneFreeList();
		usedRectangles.add(node);
	}

	private IntRectangle scoreRectangle(int width, int height, int method, int score1, int score2) {
		IntRectangle newNode = new IntRectangle();
		score1 = 0x7fffffff;
		score2 = 0x7fffffff;
		switch (method) {
		case 0: // '\0'
			newNode = findPositionForNewNodeBestShortSideFit(width, height);
			break;

		case 3: // '\003'
			newNode = findPositionForNewNodeBottomLeft(width, height, score1, score2);
			break;

		case 4: // '\004'
			newNode = findPositionForNewNodeContactPo(width, height, score1);
			score1 = -score1;
			break;

		case 1: // '\001'
			newNode = findPositionForNewNodeBestLongSideFit(width, height, score2, score1);
			break;

		case 2: // '\002'
			newNode = findPositionForNewNodeBestAreaFit(width, height, score1, score2);
			break;
		}
		if (newNode.getHeight() == 0) {
			score1 = 0x7fffffff;
			score2 = 0x7fffffff;
		}
		return newNode;
	}

	private double occupancy() {
		double usedSurfaceArea = 0.0D;
		for (int i = 0; i < usedRectangles.size(); i++) {
			IntRectangle rect = (IntRectangle)usedRectangles.get(i);
			usedSurfaceArea += rect.getWidth() * rect.getHeight();
		}

		return usedSurfaceArea / (double)(binWidth * binHeight);
	}

	private IntRectangle findPositionForNewNodeBottomLeft(int width, int height, int bestY, int bestX) {
		IntRectangle bestNode = new IntRectangle();
		bestY = 0x7fffffff;
		for (int i = 0; i < freeRectangles.size(); i++) {
			IntRectangle rect = (IntRectangle)freeRectangles.get(i);
			int topSideY;
			if (rect.getWidth() >= width && rect.getHeight() >= height) {
				topSideY = rect.getY() + height;
				if (topSideY < bestY || topSideY == bestY && rect.getX() < bestX) {
					bestNode.set(rect.getX(), rect.getY(), width, height);
					bestY = topSideY;
					bestX = rect.getX();
				}
			}
			if (!allowRotations || rect.getWidth() < height || rect.getHeight() < width) {
				continue;
			}
			topSideY = rect.getY() + width;
			if (topSideY < bestY || topSideY == bestY && rect.getX() < bestX) {
				bestNode.set(rect.getX(), rect.getY(), width, height);
				bestY = topSideY;
				bestX = rect.getX();
			}
		}

		return bestNode;
	}

	private IntRectangle findPositionForNewNodeBestShortSideFit(int width, int height) {
		IntRectangle bestNode = new IntRectangle();
		bestShortSideFit = 0x7fffffff;
		bestLongSideFit = score2;
		for (int i = 0; i < freeRectangles.size(); i++) {
			IntRectangle rect = (IntRectangle)freeRectangles.get(i);
			if (rect.getWidth() >= width && rect.getHeight() >= height) {
				int leftoverHoriz = Math.abs(rect.getWidth() - width);
				int leftoverVert = Math.abs(rect.getHeight() - height);
				int shortSideFit = Math.min(leftoverHoriz, leftoverVert);
				int longSideFit = Math.max(leftoverHoriz, leftoverVert);
				if (shortSideFit < bestShortSideFit || shortSideFit == bestShortSideFit && longSideFit < bestLongSideFit) {
					bestNode.set(rect.getX(), rect.getY(), width, height);
					bestShortSideFit = shortSideFit;
					bestLongSideFit = longSideFit;
				}
			}
			if (!allowRotations || rect.getWidth() < height || rect.getHeight() < width) {
				continue;
			}
			double flippedLeftoverHoriz = Math.abs(rect.getWidth() - height);
			double flippedLeftoverVert = Math.abs(rect.getHeight() - width);
			double flippedShortSideFit = Math.min(flippedLeftoverHoriz, flippedLeftoverVert);
			double flippedLongSideFit = Math.max(flippedLeftoverHoriz, flippedLeftoverVert);
			if (flippedShortSideFit < (double)bestShortSideFit || flippedShortSideFit == (double)bestShortSideFit && flippedLongSideFit < (double)bestLongSideFit) {
				bestNode.set(rect.getX(), rect.getY(), width, height);
				bestShortSideFit = (int)flippedShortSideFit;
				bestLongSideFit = (int)flippedLongSideFit;
			}
		}

		return bestNode;
	}

	private IntRectangle findPositionForNewNodeBestLongSideFit(int width, int height, int bestShortSideFit, int bestLongSideFit) {
		IntRectangle bestNode = new IntRectangle();
		bestLongSideFit = 0x7fffffff;
		for (int i = 0; i < freeRectangles.size(); i++) {
			IntRectangle rect = (IntRectangle)freeRectangles.get(i);
			int leftoverHoriz;
			int leftoverVert;
			int shortSideFit;
			int longSideFit;
			if (rect.getWidth() >= width && rect.getHeight() >= height) {
				leftoverHoriz = Math.abs(rect.getWidth() - width);
				leftoverVert = Math.abs(rect.getHeight() - height);
				shortSideFit = Math.min(leftoverHoriz, leftoverVert);
				longSideFit = Math.max(leftoverHoriz, leftoverVert);
				if (longSideFit < bestLongSideFit || longSideFit == bestLongSideFit && shortSideFit < bestShortSideFit) {
					bestNode.set(rect.getX(), rect.getY(), width, height);
					bestShortSideFit = shortSideFit;
					bestLongSideFit = longSideFit;
				}
			}
			if (!allowRotations || rect.getWidth() < height || rect.getHeight() < width) {
				continue;
			}
			leftoverHoriz = Math.abs(rect.getWidth() - height);
			leftoverVert = Math.abs(rect.getHeight() - width);
			shortSideFit = Math.min(leftoverHoriz, leftoverVert);
			longSideFit = Math.max(leftoverHoriz, leftoverVert);
			if (longSideFit < bestLongSideFit || longSideFit == bestLongSideFit && shortSideFit < bestShortSideFit) {
				bestNode.set(rect.getX(), rect.getY(), width, height);
				bestShortSideFit = shortSideFit;
				bestLongSideFit = longSideFit;
			}
		}

		return bestNode;
	}

	private IntRectangle findPositionForNewNodeBestAreaFit(int width, int height, int bestAreaFit, int bestShortSideFit) {
		IntRectangle bestNode = new IntRectangle();
		bestAreaFit = 0x7fffffff;
		for (int i = 0; i < freeRectangles.size(); i++) {
			IntRectangle rect = (IntRectangle)freeRectangles.get(i);
			int areaFit = rect.getWidth() * rect.getHeight() - width * height;
			int leftoverHoriz;
			int leftoverVert;
			int shortSideFit;
			if (rect.getWidth() >= width && rect.getHeight() >= height) {
				leftoverHoriz = Math.abs(rect.getWidth() - width);
				leftoverVert = Math.abs(rect.getHeight() - height);
				shortSideFit = Math.min(leftoverHoriz, leftoverVert);
				if (areaFit < bestAreaFit || areaFit == bestAreaFit && shortSideFit < bestShortSideFit) {
					bestNode.set(rect.getX(), rect.getY(), width, height);
					bestShortSideFit = shortSideFit;
					bestAreaFit = areaFit;
				}
			}
			if (!allowRotations || rect.getWidth() < height || rect.getHeight() < width) {
				continue;
			}
			leftoverHoriz = Math.abs(rect.getWidth() - height);
			leftoverVert = Math.abs(rect.getHeight() - width);
			shortSideFit = Math.min(leftoverHoriz, leftoverVert);
			if (areaFit < bestAreaFit || areaFit == bestAreaFit && shortSideFit < bestShortSideFit) {
				bestNode.set(rect.getX(), rect.getY(), width, height);
				bestShortSideFit = shortSideFit;
				bestAreaFit = areaFit;
			}
		}

		return bestNode;
	}

	private int commonIntervalLength(int i1start, int i1end, int i2start, int i2end) {
		if (i1end < i2start || i2end < i1start) {
			return 0;
		} else {
			return Math.min(i1end, i2end) - Math.max(i1start, i2start);
		}
	}

	private int contactPointScoreNode(int x, int y, int width, int height) {
		int score = 0;
		if (x == 0 || x + width == binWidth) {
			score += height;
		}
		if (y == 0 || y + height == binHeight) {
			score += width;
		}
		for (int i = 0; i < usedRectangles.size(); i++) {
			IntRectangle rect = (IntRectangle)usedRectangles.get(i);
			if (rect.x == x + width || rect.x + rect.getWidth() == x) {
				score += commonIntervalLength(rect.y, rect.y + rect.getHeight(), y, y + height);
			}
			if (rect.y == y + height || rect.y + rect.getHeight() == y) {
				score += commonIntervalLength(rect.x, rect.x + rect.getWidth(), x, x + width);
			}
		}

		return score;
	}

	private IntRectangle findPositionForNewNodeContactPo(int width, int height, int bestContactScore) {
		IntRectangle bestNode = new IntRectangle();
		bestContactScore = -1;
		for (int i = 0; i < freeRectangles.size(); i++) {
			IntRectangle rect = (IntRectangle)freeRectangles.get(i);
			int score;
			if (rect.getWidth() >= width && rect.getHeight() >= height) {
				score = contactPointScoreNode(rect.x, rect.y, width, height);
				if (score > bestContactScore) {
					bestNode.set(rect.getX(), rect.getY(), width, height);
					bestContactScore = score;
				}
			}
			if (!allowRotations || rect.getWidth() < height || rect.getHeight() < width) {
				continue;
			}
			score = contactPointScoreNode(rect.x, rect.y, height, width);
			if (score > bestContactScore) {
				bestNode.set(rect.getX(), rect.getY(), width, height);
				bestContactScore = score;
			}
		}

		return bestNode;
	}

	private boolean splitFreeNode(IntRectangle freeNode, IntRectangle usedNode) {
		if (usedNode.x >= freeNode.x + freeNode.getWidth() || usedNode.x + usedNode.getWidth() <= freeNode.x || usedNode.y >= freeNode.y + freeNode.getHeight() || usedNode.y + usedNode.getHeight() <= freeNode.y) {
			return false;
		}
		if (usedNode.x < freeNode.x + freeNode.getWidth() && usedNode.x + usedNode.getWidth() > freeNode.x) {
			if (usedNode.y > freeNode.y && usedNode.y < freeNode.y + freeNode.getHeight()) {
				IntRectangle newNode = freeNode.clone();
				newNode.setHeight(usedNode.y - newNode.y);
				freeRectangles.add(newNode);
			}
			if (usedNode.y + usedNode.getHeight() < freeNode.y + freeNode.getHeight()) {
				IntRectangle newNode = freeNode.clone();
				newNode.y = usedNode.y + usedNode.getHeight();
				newNode.setHeight((freeNode.y + freeNode.getHeight()) - (usedNode.y + usedNode.getHeight()));
				freeRectangles.add(newNode);
			}
		}
		if (usedNode.y < freeNode.y + freeNode.getHeight() && usedNode.y + usedNode.getHeight() > freeNode.y) {
			if (usedNode.x > freeNode.x && usedNode.x < freeNode.x + freeNode.getWidth()) {
				IntRectangle newNode = freeNode.clone();
				newNode.setWidth(usedNode.x - newNode.x);
				freeRectangles.add(newNode);
			}
			if (usedNode.x + usedNode.getWidth() < freeNode.x + freeNode.getWidth()) {
				IntRectangle newNode = freeNode.clone();
				newNode.x = usedNode.x + usedNode.getWidth();
				newNode.setWidth((freeNode.x + freeNode.getWidth()) - (usedNode.x + usedNode.getWidth()));
				freeRectangles.add(newNode);
			}
		}
		return true;
	}

	private void pruneFreeList() {
		for (int i = 0; i < freeRectangles.size(); i++) {
			for (int j = i + 1; j < freeRectangles.size(); j++) {
				if (isContainedIn((IntRectangle)freeRectangles.get(i), (IntRectangle)freeRectangles.get(j))) {
					freeRectangles.remove(i);
					break;
				}
				if (isContainedIn((IntRectangle)freeRectangles.get(j), (IntRectangle)freeRectangles.get(i))) {
					freeRectangles.remove(j);
				}
			}

		}

	}

	private boolean isContainedIn(IntRectangle a, IntRectangle b) {
		return a.x >= b.x && a.y >= b.y && a.x + a.getWidth() <= b.x + b.getWidth() && a.y + a.getHeight() <= b.y + b.getHeight();
	}
}
