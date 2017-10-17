// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   Config.java

package org.forkjoin.jdbckit.mysql;

import java.io.File;

public class Config {

	private String pack;
	private String objectPack;
	private String daoPack;
	private String charset;
	private String tablePrefix;
	private File dir;
	private File resourcesDir;

	public File getPackPath(String childPack) {
		return new File(dir, (new StringBuilder()).append(pack.replace('.', File.separatorChar)).append(File.separatorChar).append(childPack.replace('.', File.separatorChar)).toString());
	}

	public File getResourcesPackPath(String childPack) {
		return new File(resourcesDir, (new StringBuilder()).append(pack.replace('.', File.separatorChar)).append(File.separatorChar).append(childPack.replace('.', File.separatorChar)).toString());
	}

	public String getPack(String childPack) {
		return (new StringBuilder()).append(pack).append(".").append(childPack).toString();
	}

	public String getPack(String pack, String childPack) {
		return (new StringBuilder()).append(pack).append(".").append(childPack).toString();
	}

	public Config(File dir, File resourcesDir) throws Exception {
		pack = "test";
		objectPack = "entity";
		daoPack = "dao";
		charset = "utf8";
		this.dir = dir;
		this.resourcesDir = resourcesDir;
	}

	public String getPack() {
		return pack;
	}

	public void setPack(String pack) {
		this.pack = pack;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public File getDir() {
		return dir;
	}

	public void setObjectPack(String objectPack) {
		this.objectPack = objectPack;
	}

	public String getObjectPack() {
		return objectPack;
	}

	public void setDaoPack(String daoPack) {
		this.daoPack = daoPack;
	}

	public String getDaoPack() {
		return daoPack;
	}

	public File getResourcesDir() {
		return resourcesDir;
	}

	public String getTablePrefix() {
		return tablePrefix;
	}

	public void setTablePrefix(String tablePrefix) {
		this.tablePrefix = tablePrefix;
	}
}
