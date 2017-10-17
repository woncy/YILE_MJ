// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   ConcurrentArrayBagListener.java

package com.isnowfox.util.collect;


// Referenced classes of package com.isnowfox.util.collect:
//			CoreGoods

public interface ConcurrentArrayBagListener {

	public abstract void onChanged();

	public abstract void onChangedItem(CoreGoods coregoods, int i);
}
