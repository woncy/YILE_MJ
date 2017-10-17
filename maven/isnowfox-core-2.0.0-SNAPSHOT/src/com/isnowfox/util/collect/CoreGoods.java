// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   CoreGoods.java

package com.isnowfox.util.collect;


public class CoreGoods {

	protected int id;
	protected int nums;
	protected int level;
	protected int grade;
	protected int levUpProgress;

	public CoreGoods() {
	}

	public CoreGoods(int id) {
		this.id = id;
	}

	public CoreGoods(int id, int nums) {
		this.id = id;
		this.nums = nums;
	}

	public CoreGoods(int id, int nums, int level, int grade) {
		this.id = id;
		this.nums = nums;
		this.grade = grade;
		this.level = level;
	}

	public CoreGoods(CoreGoods e) {
		id = e.id;
		nums = e.nums;
		grade = e.grade;
		level = e.level;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNums() {
		return nums;
	}

	public void setNums(int nums) {
		this.nums = nums;
	}

	public int getGrade() {
		return grade;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public int getLevUpProgress() {
		return levUpProgress;
	}

	public void setLevUpProgress(int levUpProgress) {
		this.levUpProgress = levUpProgress;
	}

	public String toString() {
		return (new StringBuilder()).append("CoreGoods [id=").append(id).append(", nums=").append(nums).append(", level=").append(level).append(", grade=").append(grade).append("]").toString();
	}
}
