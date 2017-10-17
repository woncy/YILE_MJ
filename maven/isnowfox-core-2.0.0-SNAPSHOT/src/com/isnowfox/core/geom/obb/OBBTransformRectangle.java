// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   OBBTransformRectangle.java

package com.isnowfox.core.geom.obb;

import com.isnowfox.core.geom.Matrix;
import com.isnowfox.core.geom.Rectangle;
import java.awt.geom.Point2D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Referenced classes of package com.isnowfox.core.geom.obb:
//			OOBAbstractRect, OBBVector

public class OBBTransformRectangle extends OOBAbstractRect {

	private static final Logger log = LoggerFactory.getLogger(com/isnowfox/core/geom/obb/OBBTransformRectangle);
	private double width;
	private double height;
	private double rotation;
	private double rotationX;
	private double rotationY;
	private boolean isReverse;
	private final Matrix matrix;

	public OBBTransformRectangle() {
		rotationX = 0.0D;
		rotationY = 0.0D;
		isReverse = false;
		matrix = new Matrix();
	}

	public OBBTransformRectangle(double centerX, double centerY, double width, double height, double rotation) {
		super(centerX, centerY, width, height, rotation);
		rotationX = 0.0D;
		rotationY = 0.0D;
		isReverse = false;
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
	}

	public void setCenter(double centerX, double centerY) {
		centerPoint.set(centerX, centerY);
	}

	public void setRotationPoint(double rotationX, double rotationY) {
		this.rotationX = rotationX;
		this.rotationY = rotationY;
	}

	public void commit() {
		execute();
	}

	public void commit2() {
		matrix.identity();
		matrix.scale(1.0D, 1.0D);
		matrix.rotate(rotation);
		matrix.translate(getCenterPoint().getX(), getCenterPoint().getY());
		java.awt.geom.Point2D.Double array[] = new java.awt.geom.Point2D.Double[4];
		array[0] = new java.awt.geom.Point2D.Double(-width / 2D, -height / 2D);
		matrix.transformPointSet(array[0]);
		array[1] = new java.awt.geom.Point2D.Double(width / 2D, -height / 2D);
		matrix.transformPointSet(array[1]);
		array[2] = new java.awt.geom.Point2D.Double(-width / 2D, height / 2D);
		matrix.transformPointSet(array[2]);
		array[3] = new java.awt.geom.Point2D.Double(width / 2D, height / 2D);
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
		super.innerSet(bounds.getX() + bounds.getWidth() / 2D, bounds.getY() + bounds.getHeight() / 2D, width, height, rotation);
	}

	public boolean isReverse() {
		return isReverse;
	}

	public void setReverse(boolean isReverse) {
		this.isReverse = isReverse;
	}

	private void execute() {
		matrix.identity();
		if (isReverse) {
			matrix.scale(-1D, 1.0D);
			matrix.rotate(-rotation);
		} else {
			matrix.scale(1.0D, 1.0D);
			matrix.rotate(rotation);
		}
		matrix.translate(getX() + rotationX, getY() + rotationY);
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
		if (isReverse) {
			super.innerSet(bounds.getX() + bounds.getWidth() / 2D, bounds.getY() + bounds.getHeight() / 2D, width, height, -rotation);
		} else {
			super.innerSet(bounds.getX() + bounds.getWidth() / 2D, bounds.getY() + bounds.getHeight() / 2D, width, height, rotation);
		}
	}

	public double getNativeWidth() {
		return width;
	}

	public double getNativeHeight() {
		return height;
	}

	public double getRotation() {
		return rotation;
	}

	public String toString() {
		return (new StringBuilder()).append("OBBTransformRectangle{width=").append(width).append(", height=").append(height).append(", rotation=").append(rotation).append(", rotationX=").append(rotationX).append(", rotationY=").append(rotationY).append(", isReverse=").append(isReverse).append(", matrix=").append(matrix).append('}').append(super.toString()).toString();
	}

}
