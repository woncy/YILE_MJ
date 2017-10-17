// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   Config.java

package com.isnowfox.io.serialize.tool;

import com.thoughtworks.xstream.XStream;
import java.io.*;
import java.net.URL;

public class Config {

	public static final String FILE_SUFFIX = ".m";
	private static final String DATA_TOOL_CONFIG_XML = "msgToolConfig.xml";
	private static final XStream xstream = new XStream();
	private String path;
	private String javaSrcPath;
	private String asSrcPath;
	private String javaRootPackage;
	private String asRootPackage;
	private String javaHandlerRootPackage;
	private String asHandlerRootPackage;
	private String javaCharacterPackage;
	private String javaCharacterClassName;
	private String asDirName;
	private String javaDirName;
	private boolean isOverrideHandler;

	public Config() {
		isOverrideHandler = false;
	}

	public static final Config load() {
		File file = new File("msgToolConfig.xml");
		if (file.exists()) {
			return (Config)xstream.fromXML(file);
		}
		Config c = new Config();
		URL u = com/isnowfox/io/serialize/tool/Config.getResource("/msg");
		if (u != null) {
			c.setPath(u.getFile());
		} else {
			File f = new File("msgConfig");
			c.setPath("D:\\tr\\tool\\msgConfig");
		}
		c.setJavaSrcPath("D:\\tr\\code\\server\\project\\zgame-server\\src\\main\\java");
		c.setAsSrcPath("D:\\tr\\code\\client\\project\\zgame-core\\src");
		c.setJavaRootPackage("game.net.message");
		c.setJavaHandlerRootPackage("game.net.handler");
		c.setAsRootPackage("com.isnowfox.net.message");
		c.setAsHandlerRootPackage("com.isnowfox.net.handler");
		return c;
	}

	public void save() throws FileNotFoundException, IOException {
		OutputStream out;
		Throwable throwable;
		Exception exception;
		out = new BufferedOutputStream(new FileOutputStream("msgToolConfig.xml"));
		throwable = null;
		try {
			xstream.toXML(this, out);
		}
		catch (Throwable throwable2) {
			throwable = throwable2;
			throw throwable2;
		}
		finally {
			if (out == null) goto _L0; else goto _L0
		}
		if (out != null) {
			if (throwable != null) {
				try {
					out.close();
				}
				catch (Throwable throwable1) {
					throwable.addSuppressed(throwable1);
				}
			} else {
				out.close();
			}
		}
		break MISSING_BLOCK_LABEL_98;
		if (throwable != null) {
			try {
				out.close();
			}
			catch (Throwable throwable3) {
				throwable.addSuppressed(throwable3);
			}
		} else {
			out.close();
		}
		throw exception;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getJavaSrcPath() {
		return javaSrcPath;
	}

	public void setJavaSrcPath(String javaSrcPath) {
		this.javaSrcPath = javaSrcPath;
	}

	public String getAsSrcPath() {
		return asSrcPath;
	}

	public void setAsSrcPath(String asSrcPath) {
		this.asSrcPath = asSrcPath;
	}

	public String getJavaRootPackage() {
		return javaRootPackage;
	}

	public void setJavaRootPackage(String javaRootPackage) {
		this.javaRootPackage = javaRootPackage;
	}

	public String getAsRootPackage() {
		return asRootPackage;
	}

	public void setAsRootPackage(String asRootPackage) {
		this.asRootPackage = asRootPackage;
	}

	public final String getJavaHandlerRootPackage() {
		return javaHandlerRootPackage;
	}

	public final void setJavaHandlerRootPackage(String javaHandlerRootPackage) {
		this.javaHandlerRootPackage = javaHandlerRootPackage;
	}

	public final String getAsHandlerRootPackage() {
		return asHandlerRootPackage;
	}

	public final void setAsHandlerRootPackage(String asHandlerRootPackage) {
		this.asHandlerRootPackage = asHandlerRootPackage;
	}

	public String getJavaCharacterPackage() {
		return javaCharacterPackage;
	}

	public void setJavaCharacterPackage(String javaCharacterPackage) {
		this.javaCharacterPackage = javaCharacterPackage;
	}

	public String getJavaCharacterClassName() {
		return javaCharacterClassName;
	}

	public void setJavaCharacterClassName(String javaCharacterClassName) {
		this.javaCharacterClassName = javaCharacterClassName;
	}

	public String getAsDirName() {
		return asDirName;
	}

	public void setAsDirName(String asDirName) {
		this.asDirName = asDirName;
	}

	public String getJavaDirName() {
		return javaDirName;
	}

	public void setJavaDirName(String javaDirName) {
		this.javaDirName = javaDirName;
	}

	public boolean isOverrideHandler() {
		return isOverrideHandler;
	}

	public void setOverrideHandler(boolean overrideHandler) {
		isOverrideHandler = overrideHandler;
	}

	public String toString() {
		return (new StringBuilder()).append("Config [path=").append(path).append(", javaSrcPath=").append(javaSrcPath).append(", asSrcPath=").append(asSrcPath).append(", javaRootPackage=").append(javaRootPackage).append(", asRootPackage=").append(asRootPackage).append(", javaHandlerRootPackage=").append(javaHandlerRootPackage).append(", asHandlerRootPackage=").append(asHandlerRootPackage).append("]").toString();
	}

}
