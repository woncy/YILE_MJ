// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   ImageType.java

package com.isnowfox.image;


public final class ImageType extends Enum {

	public static final ImageType unknown;
	public static final ImageType jpg;
	public static final ImageType gif;
	public static final ImageType bmp;
	public static final ImageType png;
	private int value;
	private static final ImageType $VALUES[];

	public static ImageType[] values() {
		return (ImageType[])$VALUES.clone();
	}

	public static ImageType valueOf(String name) {
		return (ImageType)Enum.valueOf(com/isnowfox/image/ImageType, name);
	}

	private ImageType(String s, int i, int value) {
		super(s, i);
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static final ImageType forName(String name) {
		if ("jpg".equalsIgnoreCase(name)) {
			return jpg;
		}
		if ("jpeg".equalsIgnoreCase(name)) {
			return jpg;
		}
		if ("gif".equalsIgnoreCase(name)) {
			return gif;
		}
		if ("bmp".equalsIgnoreCase(name)) {
			return png;
		}
		if ("png".equalsIgnoreCase(name)) {
			return png;
		} else {
			return unknown;
		}
	}

	public static final ImageType forValue(int value) {
		ImageType is[] = values();
		if (value < is.length) {
			return is[value];
		} else {
			return unknown;
		}
	}

	static  {
		unknown = new ImageType("unknown", 0, 0);
		jpg = new ImageType("jpg", 1, 1);
		gif = new ImageType("gif", 2, 2);
		bmp = new ImageType("bmp", 3, 3);
		png = new ImageType("png", 4, 4);
		$VALUES = (new ImageType[] {
			unknown, jpg, gif, bmp, png
		});
	}
}
