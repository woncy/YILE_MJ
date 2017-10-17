// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   Utils.java

package com.isnowfox.io.serialize.tool.model;

import com.isnowfox.io.serialize.tool.Config;
import java.io.File;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

// Referenced classes of package com.isnowfox.io.serialize.tool.model:
//			Package, Attribute, Message, AttributeType

public class Utils {

	private Map map;
	private Config config;

	public Utils(Map map, Config config) {
		this.map = map;
		this.config = config;
	}

	public String getJavaPack(Message m) {
		if (StringUtils.isEmpty(m.getPackageName())) {
			return config.getJavaRootPackage();
		} else {
			return (new StringBuilder()).append(config.getJavaRootPackage()).append(".").append(m.getPackageName()).toString();
		}
	}

	public String getJavaHandlerRootPackage() {
		return config.getJavaHandlerRootPackage();
	}

	public String getAsPack(Message m) {
		if (StringUtils.isEmpty(m.getPackageName())) {
			return config.getAsRootPackage();
		} else {
			return (new StringBuilder()).append(config.getAsRootPackage()).append(".").append(m.getPackageName()).toString();
		}
	}

	public String getAsHandlerPack(Message m) {
		if (StringUtils.isEmpty(m.getPackageName())) {
			return config.getAsHandlerRootPackage();
		} else {
			return (new StringBuilder()).append(config.getAsHandlerRootPackage()).append(".").append(m.getPackageName()).toString();
		}
	}

	public String getJavaHandlerPack(Message m) {
		if (StringUtils.isEmpty(m.getPackageName())) {
			return config.getJavaHandlerRootPackage();
		} else {
			return (new StringBuilder()).append(config.getJavaHandlerRootPackage()).append(".").append(m.getPackageName()).toString();
		}
	}

	public String getJavaType(Attribute a) {
		if (a.getType() == AttributeType.OTHER) {
			if (a.isArray()) {
				return (new StringBuilder()).append("java.util.ArrayList<").append(getOtherType(a, true)).append(">").toString();
			} else {
				return getOtherType(a, true);
			}
		} else {
			return a.getJavaTypeString();
		}
	}

	public String getAsTypeName(Attribute a) {
		if (a.getType() == AttributeType.OTHER) {
			String name = getOtherType(a, false);
			if (a.isArray()) {
				return (new StringBuilder()).append("Vector.<").append(name).append(">").toString();
			} else {
				return name;
			}
		} else {
			return a.getAsTypeString();
		}
	}

	public String getLayaTypeName(Attribute a) {
		if (a.getType() == AttributeType.OTHER) {
			String name = getOtherType(a, false);
			if (a.isArray()) {
				return "Array";
			} else {
				return name;
			}
		} else {
			return a.getLayaTypeString();
		}
	}

	public String getAsTypeNoArray(Attribute a) {
		if (a.getType() == AttributeType.OTHER) {
			String name = getOtherType(a, false);
			return name;
		} else {
			return a.getAsTypeString();
		}
	}

	private String getOtherType(Attribute a, boolean isJava) {
		String name = a.getTypeName();
		String pack = getPack(name);
		name = getName(name);
		if (StringUtils.isEmpty(pack)) {
			Package p = a.getMessage().getPack();
			Message m = p.get(name);
			if (m != null) {
				if (isJava) {
					return (new StringBuilder()).append(getJavaPack(m)).append(".").append(name).toString();
				} else {
					return (new StringBuilder()).append(getAsPack(m)).append(".").append(name).toString();
				}
			}
			p = (Package)map.get("");
			if (p != null) {
				m = p.get(name);
				if (m != null) {
					if (isJava) {
						return (new StringBuilder()).append(getJavaPack(m)).append(".").append(name).toString();
					} else {
						return (new StringBuilder()).append(getAsPack(m)).append(".").append(name).toString();
					}
				}
			}
		} else {
			Package p = (Package)map.get(pack);
			if (p != null) {
				Message m = p.get(name);
				if (m != null) {
					if (isJava) {
						return (new StringBuilder()).append(getJavaPack(m)).append(".").append(name).toString();
					} else {
						return (new StringBuilder()).append(getAsPack(m)).append(".").append(name).toString();
					}
				}
			}
		}
		return a.getTypeName();
	}

	private String getPack(String name) {
		int index = name.lastIndexOf('.');
		if (index > -1) {
			return name.substring(0, index);
		} else {
			return null;
		}
	}

	private String getName(String name) {
		int index = name.lastIndexOf('.');
		if (index > -1) {
			return name.substring(index + 1, name.length());
		} else {
			return name;
		}
	}

	public static final File packToPath(String path, String packname, String name, String suffix) {
		File f = new File(path, packname.replace(".", File.separator));
		f = new File(f, (new StringBuilder()).append(name).append(suffix).toString());
		return f;
	}

	public static final String formatComment(String comment, String indent) {
		return comment.replace("\n", (new StringBuilder()).append("\r\n").append(indent).append("* ").toString());
	}

	public static final String toClassName(String str) {
		StringBuilder sb = new StringBuilder();
		boolean isUp = false;
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (0 == i) {
				sb.append(Character.toUpperCase(c));
				continue;
			}
			if (c == '_') {
				isUp = true;
				continue;
			}
			if (isUp) {
				isUp = false;
				sb.append(Character.toUpperCase(c));
			} else {
				sb.append(c);
			}
		}

		return sb.toString();
	}

	public static final String toFieldName(String str) {
		StringBuilder sb = new StringBuilder();
		boolean isUp = false;
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (i == 0) {
				c = Character.toLowerCase(c);
			}
			if (c == '_') {
				isUp = true;
				continue;
			}
			if (isUp) {
				isUp = false;
				sb.append(Character.toUpperCase(c));
			} else {
				sb.append(c);
			}
		}

		return sb.toString();
	}

	public Config getConfig() {
		return config;
	}
}
