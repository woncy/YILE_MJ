// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   Attribute.java

package com.isnowfox.io.serialize.tool.model;


// Referenced classes of package com.isnowfox.io.serialize.tool.model:
//			AttributeType, Utils, Message

public final class Attribute {

	private AttributeType type;
	private String typeName;
	private String name;
	private String comment;
	private boolean isArray;
	private int arrayNums;
	private Message message;

	public Attribute(Message message) {
		this.message = message;
	}

	public String getJavaTypeString() {
		if (type == AttributeType.BYTES || !isArray) {
			return getJavaTypeStringInner();
		}
		if (type == AttributeType.OTHER) {
			return (new StringBuilder()).append("java.util.ArrayList<").append(getJavaWrapTypeStringInner()).append(">").toString();
		} else {
			return (new StringBuilder()).append(getJavaTypeStringInner()).append("[]").toString();
		}
	}

	public String getAsTypeString() {
		if (type == AttributeType.BYTES || !isArray) {
			return getAsTypeStringInner();
		} else {
			return (new StringBuilder()).append("Vector.<").append(getAsTypeStringInner()).append(">").toString();
		}
	}

	public String getLayaTypeString() {
		if (type == AttributeType.BYTES || !isArray) {
			return getLayaTypeStringInner();
		} else {
			return "Array";
		}
	}

	public String getAsFieldName() {
		return (new StringBuilder()).append("_").append(Utils.toFieldName(name)).toString();
	}

	private String getJavaWrapTypeStringInner() {
		static class _cls1 {

			static final int $SwitchMap$com$isnowfox$io$serialize$tool$model$AttributeType[];

			static  {
				$SwitchMap$com$isnowfox$io$serialize$tool$model$AttributeType = new int[AttributeType.values().length];
				try {
					$SwitchMap$com$isnowfox$io$serialize$tool$model$AttributeType[AttributeType.BOOLEAN.ordinal()] = 1;
				}
				catch (NoSuchFieldError nosuchfielderror) { }
				try {
					$SwitchMap$com$isnowfox$io$serialize$tool$model$AttributeType[AttributeType.FLOAT.ordinal()] = 2;
				}
				catch (NoSuchFieldError nosuchfielderror1) { }
				try {
					$SwitchMap$com$isnowfox$io$serialize$tool$model$AttributeType[AttributeType.DOUBLE.ordinal()] = 3;
				}
				catch (NoSuchFieldError nosuchfielderror2) { }
				try {
					$SwitchMap$com$isnowfox$io$serialize$tool$model$AttributeType[AttributeType.OTHER.ordinal()] = 4;
				}
				catch (NoSuchFieldError nosuchfielderror3) { }
				try {
					$SwitchMap$com$isnowfox$io$serialize$tool$model$AttributeType[AttributeType.INT.ordinal()] = 5;
				}
				catch (NoSuchFieldError nosuchfielderror4) { }
				try {
					$SwitchMap$com$isnowfox$io$serialize$tool$model$AttributeType[AttributeType.LONG.ordinal()] = 6;
				}
				catch (NoSuchFieldError nosuchfielderror5) { }
				try {
					$SwitchMap$com$isnowfox$io$serialize$tool$model$AttributeType[AttributeType.STRING.ordinal()] = 7;
				}
				catch (NoSuchFieldError nosuchfielderror6) { }
				try {
					$SwitchMap$com$isnowfox$io$serialize$tool$model$AttributeType[AttributeType.BYTES.ordinal()] = 8;
				}
				catch (NoSuchFieldError nosuchfielderror7) { }
				try {
					$SwitchMap$com$isnowfox$io$serialize$tool$model$AttributeType[AttributeType.BYTE_BUF.ordinal()] = 9;
				}
				catch (NoSuchFieldError nosuchfielderror8) { }
			}
		}

		switch (_cls1..SwitchMap.com.isnowfox.io.serialize.tool.model.AttributeType[type.ordinal()]) {
		case 1: // '\001'
			return "Boolean";

		case 2: // '\002'
			return "Float";

		case 3: // '\003'
			return "Double";

		case 4: // '\004'
			return typeName;

		case 5: // '\005'
			return "Integer";

		case 6: // '\006'
			return "Long";

		case 7: // '\007'
			return "String";

		case 8: // '\b'
			return "byte[]";

		case 9: // '\t'
			return "ByteBuf";
		}
		return null;
	}

	private String getAsTypeStringInner() {
		switch (_cls1..SwitchMap.com.isnowfox.io.serialize.tool.model.AttributeType[type.ordinal()]) {
		case 1: // '\001'
			return "Boolean";

		case 2: // '\002'
			return "Number";

		case 3: // '\003'
			return "Number";

		case 4: // '\004'
			return typeName;

		case 5: // '\005'
			return "int";

		case 6: // '\006'
			return "Number";

		case 7: // '\007'
			return "String";

		case 8: // '\b'
		case 9: // '\t'
			return "flash.utils.ByteArray";
		}
		return null;
	}

	private String getLayaTypeStringInner() {
		switch (_cls1..SwitchMap.com.isnowfox.io.serialize.tool.model.AttributeType[type.ordinal()]) {
		case 1: // '\001'
			return "Boolean";

		case 2: // '\002'
			return "Number";

		case 3: // '\003'
			return "Number";

		case 4: // '\004'
			return typeName;

		case 5: // '\005'
			return "int";

		case 6: // '\006'
			return "Number";

		case 7: // '\007'
			return "String";

		case 8: // '\b'
		case 9: // '\t'
			return "laya.utils.Byte";
		}
		return null;
	}

	private String getJavaTypeStringInner() {
		switch (_cls1..SwitchMap.com.isnowfox.io.serialize.tool.model.AttributeType[type.ordinal()]) {
		case 1: // '\001'
			return "boolean";

		case 2: // '\002'
			return "float";

		case 3: // '\003'
			return "double";

		case 4: // '\004'
			return typeName;

		case 5: // '\005'
			return "int";

		case 6: // '\006'
			return "long";

		case 7: // '\007'
			return "String";

		case 8: // '\b'
			return "byte[]";

		case 9: // '\t'
			return "ByteBuf";
		}
		return null;
	}

	public String getBasEncodeMethod() {
		String method = getBasEncodeMethodInner();
		if (type == AttributeType.BYTES) {
			return method;
		}
		if (isArray) {
			return (new StringBuilder()).append(method).append("Array").toString();
		} else {
			return method;
		}
	}

	private final String getBasEncodeMethodInner() {
		switch (_cls1..SwitchMap.com.isnowfox.io.serialize.tool.model.AttributeType[type.ordinal()]) {
		case 1: // '\001'
			return "writeBoolean";

		case 2: // '\002'
			return "writeFloat";

		case 3: // '\003'
			return "writeDouble";

		case 5: // '\005'
			return "writeInt";

		case 6: // '\006'
			return "writeLong";

		case 7: // '\007'
			return "writeString";

		case 8: // '\b'
			return "writeBytes";

		case 9: // '\t'
			return "writeByteBuf";

		case 4: // '\004'
		default:
			return null;
		}
	}

	public String getBasDecodeMethod() {
		String method = getBasDecodeMethodInner();
		if (type == AttributeType.BYTES) {
			return method;
		}
		if (isArray) {
			return (new StringBuilder()).append(method).append("Array").toString();
		} else {
			return method;
		}
	}

	private final String getBasDecodeMethodInner() {
		switch (_cls1..SwitchMap.com.isnowfox.io.serialize.tool.model.AttributeType[type.ordinal()]) {
		case 1: // '\001'
			return "readBoolean";

		case 2: // '\002'
			return "readFloat";

		case 3: // '\003'
			return "readDouble";

		case 5: // '\005'
			return "readInt";

		case 6: // '\006'
			return "readLong";

		case 7: // '\007'
			return "readString";

		case 8: // '\b'
			return "readBytes";

		case 9: // '\t'
			return "readByteBuf";

		case 4: // '\004'
		default:
			return null;
		}
	}

	public boolean isBasType() {
		return type != AttributeType.OTHER;
	}

	public String getJavaAddName() {
		return (new StringBuilder()).append("add").append(Utils.toClassName(name)).toString();
	}

	public String getJavaSetName() {
		return (new StringBuilder()).append("set").append(Utils.toClassName(name)).toString();
	}

	public String getJavaGetName() {
		return (new StringBuilder()).append("get").append(Utils.toClassName(name)).toString();
	}

	protected void setType(AttributeType type) {
		this.type = type;
	}

	protected void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	protected void setName(String name) {
		this.name = name;
	}

	protected void setComment(String comment) {
		this.comment = comment;
	}

	protected void setArray(boolean isArray) {
		this.isArray = isArray;
	}

	protected void setArrayNums(int arrayNums) {
		this.arrayNums = arrayNums;
	}

	public AttributeType getType() {
		return type;
	}

	public String getTypeName() {
		return typeName;
	}

	public String getName() {
		return name;
	}

	public String getComment() {
		return comment;
	}

	public boolean isArray() {
		return isArray;
	}

	public boolean isByteBuf() {
		return type == AttributeType.BYTE_BUF;
	}

	public int getArrayNums() {
		return arrayNums;
	}

	public Message getMessage() {
		return message;
	}

	public String toString() {
		return (new StringBuilder()).append("Attribute [type=").append(type).append(", typeName=").append(typeName).append(", name=").append(name).append(", comment=").append(comment).append(", isArray=").append(isArray).append(", arrayNums=").append(arrayNums).append("]").toString();
	}
}
