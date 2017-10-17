// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   ClassBuilder.java

package com.isnowfox.el;

import com.isnowfox.el.exp.BooleanExpression;
import com.isnowfox.el.exp.ByteExpression;
import com.isnowfox.el.exp.CharExpression;
import com.isnowfox.el.exp.DoubleExpression;
import com.isnowfox.el.exp.FloatExpression;
import com.isnowfox.el.exp.IntExpression;
import com.isnowfox.el.exp.LongExpression;
import com.isnowfox.util.ArrayExpandUtils;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import javassist.*;

// Referenced classes of package com.isnowfox.el:
//			ElEngine, Expression, UnknownKeyException, KeyInfo, 
//			ShortExpression, VoidExpression

class ClassBuilder {
	private static class ExpressionInner {

		private static Map map;

		static  {
			map = new HashMap(16);
			map.put(Boolean.TYPE, com/isnowfox/el/exp/BooleanExpression);
			map.put(Byte.TYPE, com/isnowfox/el/exp/ByteExpression);
			map.put(Character.TYPE, com/isnowfox/el/exp/CharExpression);
			map.put(Double.TYPE, com/isnowfox/el/exp/DoubleExpression);
			map.put(Float.TYPE, com/isnowfox/el/exp/FloatExpression);
			map.put(Integer.TYPE, com/isnowfox/el/exp/IntExpression);
			map.put(Long.TYPE, com/isnowfox/el/exp/LongExpression);
			map.put(Short.TYPE, com/isnowfox/el/ShortExpression);
			map.put(Void.TYPE, com/isnowfox/el/VoidExpression);
		}


		private ExpressionInner() {
		}
	}


	private static final String PACKAGE_NAME = com/isnowfox/el/ElEngine.getPackage().getName();
	private Class returnClass;
	private CtMethod method;
	private Class cls;

	ClassBuilder(ElEngine oel, ClassPool pool, Class cls, String el, int id) throws UnknownKeyException, CannotCompileException, NotFoundException {
		CtClass cc = pool.makeClass((new StringBuilder()).append(PACKAGE_NAME).append(".OelProxyImpi").append(id).toString());
		cc.setModifiers(cc.getModifiers() | 0x10);
		String body = makeMethodBody(cls, el);
		if (returnClass.isPrimitive()) {
			cc.setSuperclass(pool.get(((Class)ExpressionInner.map.get(returnClass)).getName()));
			method = CtNewMethod.make((new StringBuilder()).append("public ").append(returnClass.getCanonicalName()).append(" ").append(returnClass.getCanonicalName()).append("El(Object obj);").toString(), cc);
			method.setBody(body);
		} else {
			cc.addInterface(pool.get(com/isnowfox/el/Expression.getCanonicalName()));
			method = CtNewMethod.make("public Object el(Object obj);", cc);
			method.setBody(body);
		}
		cc.addMethod(method);
		cc.debugWriteFile("debug");
		this.cls = cc.toClass();
		cc.detach();
	}

	private final String makeMethodBody(Class cls, String el) throws UnknownKeyException {
		int varSeed = 0;
		StringBuilder sb = new StringBuilder("{\n");
		String keys[] = el.split("\\.");
		sb.append((new StringBuilder()).append(cls.getCanonicalName()).append(" var_").append(++varSeed).append(" = (").append(cls.getCanonicalName()).append(")$1;\n").toString());
		returnClass = cls;
		for (int i = 0; i < keys.length; i++) {
			String key = keys[i].trim();
			KeyInfo info = analyse(returnClass, key);
			int nextVar;
			if (info == null) {
				if (i == 0) {
					throw new UnknownKeyException((new StringBuilder()).append("Î»ÖÃ:").append(i).append(",Î´ÖªµÄkey:").append(key).append(",cls:").append(cls).toString());
				}
				nextVar = varSeed++;
				String itemEl = ArrayExpandUtils.join(keys, '.', i);
				sb.append((new StringBuilder()).append("Object  var_").append(varSeed).append(" = ").append(com/isnowfox/el/ElEngine.getCanonicalName()).append(".getInstance().el(var_").append(nextVar).append(", \"").append(itemEl).append("\");\n").toString());
				continue;
			}
			nextVar = varSeed++;
			static class _cls1 {

				static final int $SwitchMap$com$isnowfox$el$KeyInfo$Type[];

				static  {
					$SwitchMap$com$isnowfox$el$KeyInfo$Type = new int[KeyInfo.Type.values().length];
					try {
						$SwitchMap$com$isnowfox$el$KeyInfo$Type[KeyInfo.Type.FIELD.ordinal()] = 1;
					}
					catch (NoSuchFieldError nosuchfielderror) { }
					try {
						$SwitchMap$com$isnowfox$el$KeyInfo$Type[KeyInfo.Type.PROPERTY.ordinal()] = 2;
					}
					catch (NoSuchFieldError nosuchfielderror1) { }
					try {
						$SwitchMap$com$isnowfox$el$KeyInfo$Type[KeyInfo.Type.METHOD.ordinal()] = 3;
					}
					catch (NoSuchFieldError nosuchfielderror2) { }
				}
			}

			switch (_cls1..SwitchMap.com.isnowfox.el.KeyInfo.Type[info.getType().ordinal()]) {
			case 1: // '\001'
				sb.append((new StringBuilder()).append(info.getCls().getCanonicalName()).append(" var_").append(varSeed).append(" = var_").append(nextVar).append(".").append(key).append(";\n").toString());
				break;

			case 2: // '\002'
				sb.append((new StringBuilder()).append(info.getCls().getCanonicalName()).append(" var_").append(varSeed).append(" = var_").append(nextVar).append(".").append(info.getMethodName()).append("();\n").toString());
				break;

			case 3: // '\003'
				sb.append((new StringBuilder()).append(info.getCls().getCanonicalName()).append(" var_").append(varSeed).append(" = var_").append(nextVar).append(".").append(key).append(";\n").toString());
				break;
			}
			returnClass = info.getCls();
		}

		if (returnClass.isPrimitive()) {
			sb.append((new StringBuilder()).append("return var_").append(varSeed).append(";\n}").toString());
		} else {
			sb.append((new StringBuilder()).append("return var_").append(varSeed).append(";\n}").toString());
		}
		return sb.toString();
	}

	private final KeyInfo analyse(Class cls, String key) throws UnknownKeyException {
		if (!key.endsWith("()")) {
			break MISSING_BLOCK_LABEL_45;
		}
		Method method = cls.getMethod(key.substring(0, key.length() - 2), new Class[0]);
		return new KeyInfo(KeyInfo.Type.METHOD, method.getReturnType());
		IntrospectionException e;
		e;
		PropertyDescriptor prop = new PropertyDescriptor(key, cls);
		return new KeyInfo(KeyInfo.Type.PROPERTY, prop.getPropertyType(), prop.getReadMethod().getName());
		prop;
		Field field = cls.getField(key);
		return new KeyInfo(KeyInfo.Type.FIELD, field.getType());
		Exception exception;
		exception;
		return null;
	}

	public Object build() throws InstantiationException, IllegalAccessException {
		return cls.newInstance();
	}

}
