// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   Message.java

package com.isnowfox.io.serialize.tool.model;

import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.isnowfox.io.serialize.tool.model:
//			Attribute, AttributeType, Utils, Package

public final class Message {

	private ArrayList asImports;
	private ArrayList javaImports;
	private String comment;
	private String packageName;
	private String name;
	private int type;
	private int id;
	private Package pack;
	private boolean isClientHandler;
	private boolean isServerHandler;
	private ArrayList attributes;

	public Message() {
		asImports = new ArrayList();
		javaImports = new ArrayList();
		attributes = new ArrayList();
	}

	public void add(String type, String name, String comment, boolean isArray, int arrayNums) {
		Attribute attr = new Attribute(this);
		AttributeType t = AttributeType.to(type);
		if (t == AttributeType.OTHER) {
			attr.setTypeName(type);
		}
		attr.setType(t);
		attr.setName(name);
		attr.setComment(comment);
		attr.setArray(isArray);
		attr.setArrayNums(arrayNums);
		attributes.add(attr);
	}

	public String getJavaToString() {
		StringBuilder sb = new StringBuilder();
		sb.append('"');
		sb.append(name);
		sb.append(" [");
		for (Iterator iterator = attributes.iterator(); iterator.hasNext(); sb.append(',')) {
			Attribute attr = (Attribute)iterator.next();
			sb.append(attr.getName());
			sb.append("=\" + ");
			sb.append(attr.getName());
			sb.append(" + \"");
		}

		sb.append(" ]\"");
		return sb.toString();
	}

	public String getJavaConstructorString(Utils utils) {
		StringBuilder sb = new StringBuilder();
		Attribute attr;
		for (Iterator iterator = attributes.iterator(); iterator.hasNext(); sb.append(attr.getName())) {
			attr = (Attribute)iterator.next();
			if (sb.length() != 0) {
				sb.append(", ");
			}
			sb.append(utils.getJavaType(attr));
			sb.append(" ");
		}

		return sb.toString();
	}

	public String getAsConstructorString(Utils utils) {
		StringBuilder sb = new StringBuilder();
		Attribute attr;
		for (Iterator iterator = attributes.iterator(); iterator.hasNext(); sb.append(utils.getAsTypeName(attr))) {
			attr = (Attribute)iterator.next();
			if (sb.length() != 0) {
				sb.append(", ");
			}
			sb.append(attr.getName());
			sb.append(":");
		}

		return sb.toString();
	}

	public String getLayaConstructorString(Utils utils) {
		StringBuilder sb = new StringBuilder();
		Attribute attr;
		for (Iterator iterator = attributes.iterator(); iterator.hasNext(); sb.append(utils.getLayaTypeName(attr))) {
			attr = (Attribute)iterator.next();
			if (sb.length() != 0) {
				sb.append(", ");
			}
			sb.append(attr.getName());
			sb.append(":");
		}

		return sb.toString();
	}

	public String getAsToString() {
		StringBuilder sb = new StringBuilder();
		sb.append('"');
		sb.append(name);
		sb.append(" [");
		for (Iterator iterator = attributes.iterator(); iterator.hasNext(); sb.append(',')) {
			Attribute attr = (Attribute)iterator.next();
			sb.append(attr.getName());
			sb.append("=\" + ");
			if (attr.getType() == AttributeType.BYTES) {
				sb.append("ByteArrayUtils.toHexString(");
				sb.append(attr.getAsFieldName());
				sb.append(", 10)");
			} else {
				sb.append(attr.getAsFieldName());
			}
			sb.append(" + \"");
		}

		sb.append(" ]\"");
		return sb.toString();
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setName(String name) {
		this.name = name;
	}

	protected void setAttributes(ArrayList attributes) {
		this.attributes = attributes;
	}

	public String getComment() {
		return comment;
	}

	public String getName() {
		return name;
	}

	public String getFieldName() {
		return Utils.toFieldName(name);
	}

	public ArrayList getAttributes() {
		return attributes;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Package getPack() {
		return pack;
	}

	public void setPack(Package pack) {
		this.pack = pack;
	}

	public ArrayList getAsImports() {
		return asImports;
	}

	public void addAsImport(String item) {
		asImports.add(item);
	}

	public ArrayList getJavaImports() {
		return javaImports;
	}

	public void addJavaImport(String item) {
		javaImports.add(item);
	}

	public final String getPackageName() {
		return packageName;
	}

	public final void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public boolean isClientHandler() {
		return isClientHandler;
	}

	public boolean isServerHandler() {
		return isServerHandler;
	}

	public void setServerHandler(boolean isServerHandler) {
		this.isServerHandler = isServerHandler;
	}

	public void setClientHandler(boolean isClientHandler) {
		this.isClientHandler = isClientHandler;
	}

	public String toString() {
		return (new StringBuilder()).append("Message [asImports=").append(asImports).append(", comment=").append(comment).append(", packageName=").append(packageName).append(", name=").append(name).append(", type=").append(type).append(", id=").append(id).append(", attributes=").append(attributes).append("]").toString();
	}
}
