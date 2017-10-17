// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   ConcurrentArrayBag.java

package com.isnowfox.util.collect;

import gnu.trove.list.array.TIntArrayList;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

// Referenced classes of package com.isnowfox.util.collect:
//			CoreGoods, ConcurrentArrayBagListener

public class ConcurrentArrayBag {

	final transient ReentrantLock lock;
	private List observers;
	private volatile transient CoreGoods array[];

	public ConcurrentArrayBag() {
		this(new CoreGoods[0]);
	}

	public ConcurrentArrayBag(int size) {
		this(new CoreGoods[size]);
	}

	protected ConcurrentArrayBag(CoreGoods value[]) {
		lock = new ReentrantLock();
		observers = new ArrayList();
		setArray(value);
	}

	public final CoreGoods[] getArray() {
		return array;
	}

	public CoreGoods[] toCopyArray() {
		CoreGoods array[] = getArray();
		return (CoreGoods[])Arrays.copyOf(array, array.length);
	}

	public final void setArray(CoreGoods a[]) {
		array = a;
	}

	final CoreGoods get(Object a[], int index) {
		return (CoreGoods)a[index];
	}

	protected CoreGoods create(int id) {
		return new CoreGoods(id);
	}

	public CoreGoods get(int index) {
		return get(((Object []) (getArray())), index);
	}

	public int size() {
		return getArray().length;
	}

	public boolean isCanSet(int index, CoreGoods e) {
		return true;
	}

	public boolean isCanSet(int index, int itemId) {
		return true;
	}

	public boolean isCanPile(CoreGoods e, int id, int level, int quality) {
		return e.getId() == id && e.getLevel() == level && e.getGrade() == quality;
	}

	private Object[] getValue() {
		return getArray();
	}

	public void extend(int size) {
		ReentrantLock lock;
		lock = this.lock;
		lock.lock();
		CoreGoods elements[] = getArray();
		if (size > elements.length) {
			setArray((CoreGoods[])Arrays.copyOf(elements, size));
			fireChanged();
		}
		lock.unlock();
		break MISSING_BLOCK_LABEL_52;
		Exception exception;
		exception;
		lock.unlock();
		throw exception;
	}

	public boolean checkDeduct(int id, int nums) {
		CoreGoods elements[] = getArray();
		int len = elements.length;
		int hasNums = 0;
		for (int i = 0; i < len && hasNums < nums; i++) {
			CoreGoods ele = elements[i];
			if (ele != null && ele.getId() == id) {
				hasNums += ele.getNums();
			}
		}

		return hasNums >= nums;
	}

	public boolean checkSpace(int id, int nums, int pileNumsMax) {
		CoreGoods elements[] = getArray();
		int len = elements.length;
		int freeNums = 0;
		for (int i = 0; i < len && freeNums < nums; i++) {
			CoreGoods ele = elements[i];
			if (ele == null) {
				freeNums += pileNumsMax;
				continue;
			}
			if (ele.getId() != id) {
				continue;
			}
			int fn = pileNumsMax - ele.getNums();
			if (fn > 0) {
				freeNums += fn;
			}
		}

		return freeNums >= nums;
	}

	public int getNums(int id) {
		CoreGoods elements[] = getArray();
		int len = elements.length;
		int hasNums = 0;
		for (int i = 0; i < len; i++) {
			CoreGoods ele = elements[i];
			if (ele != null && ele.getId() == id) {
				hasNums += ele.getNums();
			}
		}

		return hasNums;
	}

	public boolean swap(int index0, int index1) {
		ReentrantLock lock;
		lock = this.lock;
		lock.lock();
		CoreGoods elements[];
		boolean flag;
		elements = getArray();
		if (index0 >= 0 && index0 < elements.length && index1 >= 0 && index1 < elements.length) {
			break MISSING_BLOCK_LABEL_47;
		}
		flag = false;
		lock.unlock();
		return flag;
		boolean flag1;
		CoreGoods e = elements[index0];
		elements[index0] = elements[index1];
		elements[index1] = e;
		flag1 = true;
		lock.unlock();
		return flag1;
		Exception exception;
		exception;
		lock.unlock();
		throw exception;
	}

	public CoreGoods remove(int index0) {
		ReentrantLock lock;
		lock = this.lock;
		lock.lock();
		CoreGoods elements[];
		CoreGoods coregoods;
		elements = getArray();
		if (index0 >= 0 && index0 < elements.length) {
			break MISSING_BLOCK_LABEL_34;
		}
		coregoods = null;
		lock.unlock();
		return coregoods;
		CoreGoods coregoods1;
		CoreGoods e = elements[index0];
		elements[index0] = null;
		coregoods1 = e;
		lock.unlock();
		return coregoods1;
		Exception exception;
		exception;
		lock.unlock();
		throw exception;
	}

	public CoreGoods set(int index0, CoreGoods goods) {
		ReentrantLock lock;
		lock = this.lock;
		lock.lock();
		CoreGoods elements[];
		CoreGoods coregoods;
		elements = getArray();
		if (index0 >= 0 && index0 < elements.length) {
			break MISSING_BLOCK_LABEL_36;
		}
		coregoods = null;
		lock.unlock();
		return coregoods;
		CoreGoods coregoods1;
		CoreGoods e = elements[index0];
		elements[index0] = goods;
		coregoods1 = e;
		lock.unlock();
		return coregoods1;
		Exception exception;
		exception;
		lock.unlock();
		throw exception;
	}

	protected TIntArrayList checkAndAppend(int id, int nums, int level, int quality, int pileNumsMax, int type) {
		ReentrantLock lock;
		lock = this.lock;
		lock.lock();
		CoreGoods elements[];
		int len;
		TIntArrayList tintarraylist;
		elements = getArray();
		len = elements.length;
		if (checkSpace(id, nums, pileNumsMax)) {
			break MISSING_BLOCK_LABEL_44;
		}
		tintarraylist = null;
		lock.unlock();
		return tintarraylist;
		TIntArrayList tintarraylist1;
		TIntArrayList destPos = new TIntArrayList();
		for (int i = 0; i < len && nums > 0; i++) {
			CoreGoods ele = elements[i];
			if (!isCanSet(i, id)) {
				continue;
			}
			if (ele == null) {
				ele = create(id);
				ele.setNums(Math.min(nums, pileNumsMax));
				ele.setGrade(quality);
				ele.setLevel(level);
				nums -= ele.getNums();
				elements[i] = ele;
				fireChangedItem(ele, i);
				destPos.add(i);
				continue;
			}
			if (!isCanPile(ele, id, level, quality)) {
				continue;
			}
			int freeNum = pileNumsMax - ele.getNums();
			if (freeNum > 0) {
				int n = Math.min(Math.min(freeNum, nums), pileNumsMax);
				ele.setNums(ele.getNums() + n);
				nums -= n;
				fireChangedItem(ele, i);
				destPos.add(i);
			}
		}

		tintarraylist1 = destPos;
		lock.unlock();
		return tintarraylist1;
		Exception exception;
		exception;
		lock.unlock();
		throw exception;
	}

	public boolean checkAndDeduct(int id, int nums) {
		ReentrantLock lock;
		lock = this.lock;
		lock.lock();
		CoreGoods elements[];
		int len;
		int i;
		elements = getArray();
		len = elements.length;
		if (checkDeduct(id, nums)) {
			break MISSING_BLOCK_LABEL_39;
		}
		i = 0;
		lock.unlock();
		return i;
		for (i = 0; i < len && nums > 0; i++) {
			CoreGoods ele = elements[i];
			if (ele == null || ele.getId() != id) {
				continue;
			}
			int n = Math.min(ele.getNums(), nums);
			nums -= n;
			ele.setNums(ele.getNums() - n);
			if (ele.getNums() <= 0) {
				elements[i] = null;
				ele = null;
			}
			fireChangedItem(ele, i);
		}

		i = 1;
		lock.unlock();
		return i;
		Exception exception;
		exception;
		lock.unlock();
		throw exception;
	}

	public void addListener(ConcurrentArrayBagListener obs) {
		observers.add(obs);
	}

	public void fireChanged() {
		for (int i = 0; i < observers.size(); i++) {
			((ConcurrentArrayBagListener)observers.get(i)).onChanged();
		}

	}

	public void fireChangedItem(CoreGoods e, int index) {
		for (int i = 0; i < observers.size(); i++) {
			ConcurrentArrayBagListener listener = (ConcurrentArrayBagListener)observers.get(i);
			listener.onChangedItem(e, index);
		}

	}

	public String toString() {
		return (new StringBuilder()).append("ConcurrentArrayBag [array=").append(Arrays.toString(array)).append("]").toString();
	}
}
