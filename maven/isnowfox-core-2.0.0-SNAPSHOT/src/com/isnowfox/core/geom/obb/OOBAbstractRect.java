// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   OOBAbstractRect.java

package com.isnowfox.core.geom.obb;

import com.isnowfox.core.geom.Rectangle;
import java.util.Arrays;

// Referenced classes of package com.isnowfox.core.geom.obb:
//			OBBVector, OBBUtils, OBB

public abstract class OOBAbstractRect
	implements OBB {

	protected final OBBVector centerPoint;
	protected final double extents[];
	protected final OBBVector axes[];
	protected double mRadius;
	protected final Rectangle bounds;

	public OOBAbstractRect() {
		centerPoint = new OBBVector();
		extents = new double[2];
		axes = new OBBVector[2];
		mRadius = 0.0D;
		bounds = new Rectangle();
	}

	public OOBAbstractRect(double centerX, double centerY, double width, double height, double rotation) {
		centerPoint = new OBBVector();
		extents = new double[2];
		axes = new OBBVector[2];
		mRadius = 0.0D;
		bounds = new Rectangle();
		innerSet(centerX, centerY, width, height, rotation);
	}

	protected void innerSet(double centerX, double centerY, double width, double height, double rotation) {
		centerPoint.set(centerX, centerY);
		extents[0] = width / 2D;
		extents[1] = height / 2D;
		axes[0] = new OBBVector(Math.cos(rotation), Math.sin(rotation));
		axes[1] = new OBBVector(-1D * Math.sin(rotation), Math.cos(rotation));
		mRadius = Math.sqrt(extents[0] * extents[0] + extents[1] * extents[1]);
	}

	public final double getHalfWidth() {
		return extents[0];
	}

	public final double getHalfHeight() {
		return extents[1];
	}

	public double getProjectionRadius(OBBVector axis) {
		return extents[0] * Math.abs(axis.dot(axes[0])) + extents[1] * Math.abs(axis.dot(axes[1]));
	}

	public OBBVector getCenterPoint() {
		return centerPoint;
	}

	public double getX() {
		return centerPoint.getX() - extents[0];
	}

	public double getY() {
		return centerPoint.getY() - extents[1];
	}

	public double getRight() {
		return centerPoint.getX() + extents[0];
	}

	public double getBottom() {
		return centerPoint.getY() + extents[1];
	}

	public OBBVector[] getAxes() {
		return axes;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public int getCollisionType() {
		return 1;
	}

	public double getRadius() {
		return mRadius;
	}

	public String toString() {
		return (new StringBuilder()).append("OOBAbstractRect{centerPoint=").append(centerPoint).append(", extents=").append(Arrays.toString(extents)).append(", axes=").append(Arrays.toString(axes)).append(", bounds=").append(bounds).append('}').toString();
	}
}
