// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   OBBFixedRectangle.java

package com.isnowfox.core.geom.obb;

import com.isnowfox.core.geom.Rectangle;

// Referenced classes of package com.isnowfox.core.geom.obb:
//			OOBAbstractRect, OBBVector

public class OBBFixedRectangle extends OOBAbstractRect {

	public OBBFixedRectangle() {
	}

	public OBBFixedRectangle(double centerX, double centerY, double width, double height) {
		super(centerX, centerY, width, height, 0.0D);
	}

	public void setByLeftTop(double leftTopX, double leftTopY, double width, double height) {
		innerSet(leftTopX + width / 2D, leftTopY + height / 2D, width, height, 0.0D);
	}

	public void set(double centerX, double centerY, double width, double height) {
		innerSet(centerX, centerY, width, height, 0.0D);
	}

	protected void innerSet(double centerX, double centerY, double width, double height, double rotation) {
		super.innerSet(centerX, centerY, width, height, rotation);
		bounds.set(centerX - extents[0], centerY - extents[1], width, height);
	}

	public void setCenter(double centerX, double centerY) {
		centerPoint.set(centerX, centerY);
		bounds.setLocation(centerX - extents[0], centerY - extents[1]);
	}

	public void setLeftTop(double x, double y) {
		setCenter(x + extents[0], y + extents[1]);
		bounds.setLocation(x, y);
	}

	public void setX(double x) {
		centerPoint.setX(x + extents[0]);
		bounds.setX(x);
	}

	public void setY(double y) {
		centerPoint.setY(y + extents[1]);
		bounds.setY(y);
	}

	public final double getX() {
		return bounds.getX();
	}

	public final double getY() {
		return bounds.getY();
	}

	public final double getWidth() {
		return bounds.getWidth();
	}

	public final double getHeight() {
		return bounds.getHeight();
	}
}
