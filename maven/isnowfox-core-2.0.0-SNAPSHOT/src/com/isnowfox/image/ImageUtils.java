// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   ImageUtils.java

package com.isnowfox.image;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.MemoryCacheImageInputStream;

// Referenced classes of package com.isnowfox.image:
//			ImageInfo, ImageType

public final class ImageUtils {

	public static final String PNG = "png";
	public static final String JPG = "jpeg";
	public static final String BMP = "bmp";
	public static final String GIF = "gif";

	public ImageUtils() {
	}

	public static byte[] readFromFile(String path) throws IOException {
		InputStream is = new FileInputStream(new File(path));
		byte buf[] = new byte[is.available()];
		is.read(buf);
		is.close();
		return buf;
	}

	public static ImageInfo getImageInfo(byte img[]) throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(img);
		MemoryCacheImageInputStream is = new MemoryCacheImageInputStream(bais);
		Iterator it = ImageIO.getImageReaders(is);
		ImageReader r = null;
		if (it.hasNext()) {
			r = (ImageReader)it.next();
		}
		if (r == null) {
			return null;
		}
		ImageInfo i = new ImageInfo();
		i.setType(ImageType.forName(r.getFormatName().toLowerCase()));
		int index = r.getMinIndex();
		synchronized (r) {
			r.setInput(is);
			i.setData(img);
			i.setHeigth(r.getHeight(index));
			i.setWidth(r.getWidth(index));
		}
		return i;
	}

	public static BufferedImage getImage(byte img[]) throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(img);
		BufferedImage src = ImageIO.read(bais);
		return src;
	}

	public static int getHeight(int width, int sourceWidth, int sourceHight) {
		int height = (int)(((float)width / (float)sourceWidth) * (float)sourceHight);
		return height;
	}

	public static String getImageUrl(int id, int width, int height) {
		return (new StringBuilder()).append("/images.vm?id=").append(id).append("&width=").append(width).append("&height=").append(height).toString();
	}

	public static String getImageUrl(String id, String width, String height) {
		return (new StringBuilder()).append("/images.vm?id=").append(id).append("&width=").append(width).append("&height=").append(height).toString();
	}

	public static String getImageUrl(int id, int width) {
		return (new StringBuilder()).append("/images.vm?id=").append(id).append("&width=").append(width).toString();
	}

	public static String getImageUrl(String id, String width) {
		return (new StringBuilder()).append("/images.vm?id=").append(id).append("&width=").append(width).toString();
	}

	public static String getHeadImageUrl(Object id, Object sex, String width, String height) {
		if (Integer.valueOf(0).equals(id) || "0".equals(id) || "".equals(id)) {
			return (new StringBuilder()).append("/face/").append(width).append("x").append(height).append(".jpg").toString();
		} else {
			return (new StringBuilder()).append("/images.vm?id=").append(id).append("&width=").append(width).append("&height=").append(height).toString();
		}
	}

	public static BufferedImage getScaleImage(byte img[], int width, String outType) throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(img);
		BufferedImage src = ImageIO.read(bais);
		int w = src.getWidth();
		int h = src.getHeight();
		int height = (int)(((float)width / (float)w) * (float)h);
		Image im = src.getScaledInstance(width, height, 4);
		BufferedImage bi = new BufferedImage(width, height, getType(src, outType));
		bi.getGraphics().drawImage(im, 0, 0, null);
		return bi;
	}

	public static BufferedImage getScaleImage(byte img[], int cwidth, int cheight, String outType) throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(img);
		BufferedImage src = ImageIO.read(bais);
		int w = src.getWidth();
		int h = src.getHeight();
		int height = (int)(((float)cwidth / (float)w) * (float)h);
		int width = cwidth;
		if (height > cheight) {
			width = (int)(((float)cheight / (float)h) * (float)w);
			height = cheight;
		}
		Image im = src.getScaledInstance(width, height, 4);
		BufferedImage bi = new BufferedImage(cwidth, cheight, getType(src, outType));
		int left = (cwidth - width) / 2;
		int top = (cheight - height) / 2;
		Graphics g = bi.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, cwidth, cheight);
		bi.getGraphics().drawImage(im, left, top, null);
		return bi;
	}

	private static int getType(BufferedImage bi, String outType) {
		return !outType.equalsIgnoreCase("gif") ? 5 : 7;
	}

	public static byte[] getScaleImageBytes(byte img[], String outType, int width) throws IOException {
		BufferedImage bi = getScaleImage(img, width, outType);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(bi, outType, out);
		return out.toByteArray();
	}

	public static byte[] getScaleImageBytes(byte img[], String outType, int width, int height) throws IOException {
		BufferedImage bi = getScaleImage(img, width, height, outType);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(bi, outType, out);
		return out.toByteArray();
	}

	public static String fastParseFileType(byte byte1[]) {
		if (byte1[0] == 71 && byte1[1] == 73 && byte1[2] == 70 && byte1[3] == 56 && (byte1[4] == 55 || byte1[4] == 57) && byte1[5] == 97) {
			return "gif";
		}
		if (byte1[6] == 74 && byte1[7] == 70 && byte1[8] == 73 && byte1[9] == 70) {
			return "jpeg";
		}
		if (byte1[0] == 66 && byte1[1] == 77) {
			return "bmp";
		}
		if (byte1[1] == 80 && byte1[2] == 78 && byte1[3] == 71) {
			return "png";
		} else {
			return null;
		}
	}
}
