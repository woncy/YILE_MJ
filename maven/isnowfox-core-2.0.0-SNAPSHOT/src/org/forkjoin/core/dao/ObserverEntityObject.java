// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   ObserverEntityObject.java

package org.forkjoin.core.dao;


// Referenced classes of package org.forkjoin.core.dao:
//			EntityObject, ObserverObject, Observer

public abstract class ObserverEntityObject extends EntityObject
	implements ObserverObject {

	private static final long serialVersionUID = 0x93259f91868411b0L;
	private boolean isChange;
	private Observer obs;

	public ObserverEntityObject() {
		isChange = false;
	}

	public boolean isChange() {
		return isChange;
	}

	public void onSaveAfter() {
		isChange = true;
	}

	public void setObserver(Observer obs) {
		this.obs = obs;
	}

	protected void changeProperty(String name, Object o) {
		isChange = true;
		if (obs != null) {
			obs.changeProperty(name, o);
		}
	}
}
