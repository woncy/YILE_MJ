// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   OBBUtils.java

package com.isnowfox.core.geom.obb;

import com.isnowfox.core.geom.Rectangle;
import java.awt.geom.Point2D;

// Referenced classes of package com.isnowfox.core.geom.obb:
//			OBBCircle, OBBVector, OBB

public final class OBBUtils {

	public static final int CM_COLLISIONTYPE_RECT = 1;
	public static final int CM_COLLISIONTYPE_CIRCLE = 2;

	public OBBUtils() {
	}

	public static boolean detector(OBB obb0, OBB obb1) {
		if (obb0.getCollisionType() == obb1.getCollisionType()) {
			switch (obb0.getCollisionType()) {
			case 2: // '\002'
				return checkCollidesCircleAndCircle((OBBCircle)obb0, (OBBCircle)obb1);

			case 1: // '\001'
				return detectorRect(obb0, obb1);
			}
		} else {
			switch (obb0.getCollisionType()) {
			case 2: // '\002'
				return detectorCircleAndRect((OBBCircle)obb0, obb1);

			case 1: // '\001'
				return detectorCircleAndRect((OBBCircle)obb1, obb0);
			}
		}
		return false;
	}

	private static boolean checkCollidesCircleAndCircle(OBBCircle _circle1, OBBCircle _circle2) {
		OBBVector nv = _circle1.getCenterPoint().sub(_circle2.getCenterPoint());
		return nv.getLenth() < _circle1.getRadius() + _circle2.getRadius();
	}

	private static boolean detectorRect(OBB obb0, OBB obb1) {
		OBBVector nv = obb0.getCenterPoint().sub(obb1.getCenterPoint());
		if (nv.getLenth() > obb0.getRadius() + obb1.getRadius()) {
			return false;
		}
		OBBVector aobbvector[] = obb0.getAxes();
		int i = aobbvector.length;
		for (int j = 0; j < i; j++) {
			OBBVector a = aobbvector[j];
			if (obb0.getProjectionRadius(a) + obb1.getProjectionRadius(a) <= Math.abs(nv.dot(a))) {
				return false;
			}
		}

		aobbvector = obb1.getAxes();
		i = aobbvector.length;
		for (int k = 0; k < i; k++) {
			OBBVector a = aobbvector[k];
			if (obb0.getProjectionRadius(a) + obb1.getProjectionRadius(a) <= Math.abs(nv.dot(a))) {
				return false;
			}
		}

		return true;
	}

	public static boolean detectorCircleAndRect(OBBCircle obbCircle, OBB obbRect) {
		java.awt.geom.Point2D.Double mULPoint = new java.awt.geom.Point2D.Double();
		java.awt.geom.Point2D.Double mURPoint = new java.awt.geom.Point2D.Double();
		java.awt.geom.Point2D.Double mDLPoint = new java.awt.geom.Point2D.Double();
		java.awt.geom.Point2D.Double mDRPoint = new java.awt.geom.Point2D.Double();
		OBBVector nv = obbRect.getCenterPoint().sub(obbCircle.getCenterPoint());
		if (nv.getLenth() > obbCircle.getRadius() + obbRect.getRadius()) {
			return false;
		}
		OBBVector rectCenter = obbRect.getCenterPoint();
		double halfRectW = obbRect.getBounds().getWidth() / 2D;
		double halfRectH = obbRect.getBounds().getHeight() / 2D;
		mULPoint.x = rectCenter.x - halfRectW;
		mULPoint.y = rectCenter.y - halfRectH;
		mURPoint.x = rectCenter.x + halfRectW;
		mURPoint.y = rectCenter.y - halfRectH;
		mDLPoint.x = rectCenter.x - halfRectW;
		mDLPoint.y = rectCenter.y + halfRectH;
		mDRPoint.x = rectCenter.x + halfRectW;
		mDRPoint.y = rectCenter.y + halfRectH;
		if (checkDistance(obbCircle.getCenterPoint().x, obbCircle.getCenterPoint().y, mULPoint.x, mULPoint.y, obbCircle.getmRadius()).booleanValue()) {
			return true;
		}
		if (checkDistance(obbCircle.getCenterPoint().x, obbCircle.getCenterPoint().y, mURPoint.x, mURPoint.y, obbCircle.getmRadius()).booleanValue()) {
			return true;
		}
		if (checkDistance(obbCircle.getCenterPoint().x, obbCircle.getCenterPoint().y, mDLPoint.x, mDLPoint.y, obbCircle.getmRadius()).booleanValue()) {
			return true;
		}
		if (checkDistance(obbCircle.getCenterPoint().x, obbCircle.getCenterPoint().y, mDRPoint.x, mDRPoint.y, obbCircle.getmRadius()).booleanValue()) {
			return true;
		}
		if (obbCircle.getCenterPoint().x > mULPoint.x && obbCircle.getCenterPoint().x < mURPoint.x) {
			OBBVector tObbVector = obbRect.getAxes()[1];
			if (halfRectH + obbCircle.getmRadius() > Math.abs(nv.dot(tObbVector))) {
				return true;
			}
		}
		if (obbCircle.getCenterPoint().y > mULPoint.y && obbCircle.getCenterPoint().y < mDLPoint.y) {
			OBBVector tObbVector = obbRect.getAxes()[0];
			if (halfRectW + obbCircle.getmRadius() > Math.abs(nv.dot(tObbVector))) {
				return true;
			}
		}
		return false;
	}

	public static Boolean checkDistance(double _x1, double _y1, double _x2, double _y2, 
			double _maxDis) {
		double tX = _x2 - _x1;
		double tY = _y2 - _y1;
		if (tX * tX + tY * tY < _maxDis * _maxDis) {
			return Boolean.valueOf(true);
		} else {
			return Boolean.valueOf(false);
		}
	}
}
