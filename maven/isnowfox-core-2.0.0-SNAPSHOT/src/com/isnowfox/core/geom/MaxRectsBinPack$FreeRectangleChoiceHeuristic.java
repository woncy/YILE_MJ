// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   MaxRectsBinPack.java

package com.isnowfox.core.geom;


// Referenced classes of package com.isnowfox.core.geom:
//			MaxRectsBinPack

public class MaxRectsBinPack$FreeRectangleChoiceHeuristic {

	public static final int BestShortSideFit = 0;
	public static final int BestLongSideFit = 1;
	public static final int BestAreaFit = 2;
	public static final int BottomLeftRule = 3;
	public static final int ContactPointRule = 4;
	final MaxRectsBinPack this$0;

	public MaxRectsBinPack$FreeRectangleChoiceHeuristic() {
		this.this$0 = MaxRectsBinPack.this;
		super();
	}
}
