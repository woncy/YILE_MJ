// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   OBBCircle.java

package com.isnowfox.core.geom.obb;

import com.isnowfox.core.geom.Rectangle;

// Referenced classes of package com.isnowfox.core.geom.obb:
//			OBBVector, OBBUtils, OBB

public class OBBCircle
	implements OBB {

	private OBBVector centerPoint;
	private double mRadius;

	public OBBCircle() {
		centerPoint = new OBBVector();
		mRadius = 0.0D;
	}

	public OBBCircle(OBBVector centerPoint, double mRadius) {
		this.centerPoint = new OBBVector();
		this.mRadius = 0.0D;
		this.centerPoint = centerPoint;
		this.mRadius = mRadius;
	}

	public void init(double _x, double _y, double _radius) {
		centerPoint.x = _x;
		centerPoint.y = _y;
		mRadius = _radius;
	}

	public void setCenterPoint(OBBVector centerPoint) {
		this.centerPoint = centerPoint;
	}

	public OBBVector getCenterPoint() {
		return centerPoint;
	}

	public double getmRadius() {
		return mRadius;
	}

	public void setmRadius(double mRadius) {
		this.mRadius = mRadius;
	}

	public Rectangle getBounds() {
		return null;
	}

	public OBBVector[] getAxes() {
		return null;
	}

	public int getCollisionType() {
		return 2;
	}

	public double getRadius() {
		return mRadius;
	}

	public double getProjectionRadius(OBBVector axis) {
		return 0.0D;
	}
}
