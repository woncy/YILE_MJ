// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   OBBRectangle.java

package com.isnowfox.core.geom.obb;

import com.isnowfox.core.geom.Matrix;
import com.isnowfox.core.geom.Rectangle;
import java.awt.geom.Point2D;

// Referenced classes of package com.isnowfox.core.geom.obb:
//			OOBAbstractRect, OBBVector

public class OBBRectangle extends OOBAbstractRect {

	private double width;
	private double height;
	private double rotation;
	private final Matrix matrix;

	public OBBRectangle() {
		matrix = new Matrix();
	}

	public OBBRectangle(double centerX, double centerY, double width, double height, double rotation) {
		super(centerX, centerY, width, height, rotation);
		matrix = new Matrix();
	}

	public void setByLeftTop(double leftTopX, double leftTopY, double width, double height, double rotation) {
		innerSet(leftTopX + width / 2D, leftTopY + height / 2D, width, height, rotation);
	}

	public void set(double centerX, double centerY, double width, double height, double rotation) {
		innerSet(centerX, centerY, width, height, rotation);
	}

	protected void innerSet(double centerX, double centerY, double width, double height, double rotation) {
		super.innerSet(centerX, centerY, width, height, rotation);
		this.width = width;
		this.height = height;
		this.rotation = rotation;
		execute();
	}

	public void setCenter(double centerX, double centerY) {
		centerPoint.set(centerX, centerY);
		execute();
	}

	public void setCenter(double centerX, double centerY, double rotation) {
		centerPoint.set(centerX, centerY);
		this.rotation = rotation;
		execute();
	}

	private void execute() {
		matrix.identity();
		matrix.translate(-extents[0], -extents[1]);
		matrix.rotate(rotation);
		matrix.translate(extents[0] + getX(), extents[1] + getY());
		matrix.translate(getX(), getY());
		java.awt.geom.Point2D.Double array[] = new java.awt.geom.Point2D.Double[4];
		array[0] = new java.awt.geom.Point2D.Double(0.0D, 0.0D);
		matrix.transformPointSet(array[0]);
		array[1] = new java.awt.geom.Point2D.Double(width, 0.0D);
		matrix.transformPointSet(array[1]);
		array[2] = new java.awt.geom.Point2D.Double(0.0D, height);
		matrix.transformPointSet(array[2]);
		array[3] = new java.awt.geom.Point2D.Double(width, height);
		matrix.transformPointSet(array[3]);
		java.awt.geom.Point2D.Double leftTop = new java.awt.geom.Point2D.Double(1.7976931348623157E+308D, 1.7976931348623157E+308D);
		java.awt.geom.Point2D.Double rightBottom = new java.awt.geom.Point2D.Double(4.9406564584124654E-324D, 4.9406564584124654E-324D);
		java.awt.geom.Point2D.Double adouble[] = array;
		int i = adouble.length;
		for (int j = 0; j < i; j++) {
			java.awt.geom.Point2D.Double point = adouble[j];
			if (point.x < leftTop.x) {
				leftTop.x = point.x;
			}
			if (point.y < leftTop.y) {
				leftTop.y = point.y;
			}
			if (point.x > rightBottom.x) {
				rightBottom.x = point.x;
			}
			if (point.y > rightBottom.y) {
				rightBottom.y = point.y;
			}
		}

		bounds.set(leftTop.x, leftTop.y, rightBottom.x - leftTop.x, rightBottom.y - leftTop.y);
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	public double getRotation() {
		return rotation;
	}
}
