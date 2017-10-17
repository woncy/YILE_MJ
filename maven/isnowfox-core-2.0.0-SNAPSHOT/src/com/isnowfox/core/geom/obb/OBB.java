// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   OBB.java

package com.isnowfox.core.geom.obb;

import com.isnowfox.core.geom.BoundsObject;

// Referenced classes of package com.isnowfox.core.geom.obb:
//			OBBVector

public interface OBB
	extends BoundsObject {

	public abstract OBBVector getCenterPoint();

	public abstract OBBVector[] getAxes();

	public abstract int getCollisionType();

	public abstract double getRadius();

	public abstract double getProjectionRadius(OBBVector obbvector);
}
