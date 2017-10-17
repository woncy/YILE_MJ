// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   ImageInfo.java

package com.isnowfox.image;


// Referenced classes of package com.isnowfox.image:
//			ImageType

public class ImageInfo {

	private int width;
	private int heigth;
	private byte data[];
	private ImageType type;

	public ImageInfo() {
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeigth() {
		return heigth;
	}

	public void setHeigth(int heigth) {
		this.heigth = heigth;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte data[]) {
		this.data = data;
	}

	public ImageType getType() {
		return type;
	}

	public void setType(ImageType type) {
		this.type = type;
	}
}
