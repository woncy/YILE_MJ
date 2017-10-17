// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   ActionProxyBuilder.java

package com.isnowfox.web.proxy;

import com.isnowfox.core.IocFactory;
import com.isnowfox.util.ClassUtils;
import com.isnowfox.web.*;
import com.isnowfox.web.config.*;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import javassist.*;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.math.NumberUtils;

// Referenced classes of package com.isnowfox.web.proxy:
//			ActionProxy

public class ActionProxyBuilder {

	private static final String ARG_IOC = "$1";
	private static final String ARG_POOL = "$2";
	private static final String ARG_REQ = "$3";
	private static final String ARG_RESP = "$4";
	private static final String PACKAGE_NAME = com/isnowfox/web/proxy/ActionProxyBuilder.getPackage().getName();
	private Class cls;
	private ActionConfig actionConfig;
	private List viewTypes;

	public ActionProxyBuilder(ActionConfig actionConfig, Config config, ClassPool pool, int id) throws CannotCompileException, NotFoundException {
		viewTypes = new ArrayList();
		this.actionConfig = actionConfig;
		CtClass cc = pool.makeClass((new StringBuilder()).append(PACKAGE_NAME).append(".ActionProxyImpi").append(id).toString());
		cc.setSuperclass(pool.get(com/isnowfox/web/proxy/ActionProxy.getName()));
		cc.setModifiers(cc.getModifiers() | 0x10);
		CtMethod method = CtNewMethod.make((new StringBuilder()).append("public void invoke(").append(com/isnowfox/core/IocFactory.getCanonicalName()).append(" iocFactory, ").append(com/isnowfox/web/ActionObjectPool.getCanonicalName()).append(" actionObjectPool, ").append(com/isnowfox/web/Request.getCanonicalName()).append(" req, ").append(com/isnowfox/web/Response.getCanonicalName()).append(" resp) throws Exception;").toString(), cc);
		String body = makeMethodBody(actionConfig, config, pool);
		method.setBody(body);
		cc.addMethod(method);
		cls = cc.toClass();
		if (config.isDebug()) {
			cc.debugWriteFile("debug");
		}
	}

	private String makeMethodBody(ActionConfig actionConfig, Config config, ClassPool pool) {
		StringBuilder sb = new StringBuilder();
		sb.append("{\n");
		String className = actionConfig.getActionClass().getCanonicalName();
		if (config.isIocCreateObject()) {
			sb.append((new StringBuilder()).append(className).append(" actionObj = (").append(className).append(")").append("$1").append(".getBean(").append(className).append(".class);\n").toString());
		} else
		if (actionConfig.getLiefCycle() == com.isnowfox.web.config.ActionConfig.LiefCycleType.REQUEST) {
			sb.append((new StringBuilder()).append(className).append(" actionObj =  (").append(className).append(")new ").append(className).append("();\n").toString());
		} else
		if (actionConfig.getLiefCycle() == com.isnowfox.web.config.ActionConfig.LiefCycleType.SINGLETON) {
			sb.append((new StringBuilder()).append(className).append(" actionObj = (").append(className).append(")").append("$2").append(".get(").append(className).append(".class);\n").toString());
		} else {
			throw new RuntimeException("未知的情况!,请检查是否修改了 生存周期类型");
		}
		sb.append((new StringBuilder()).append("Object resultObj = actionObj.").append(actionConfig.getMethod()).append("(").toString());
		List list = actionConfig.getParamsConfig().getList();
		for (int i = 0; i < list.size(); i++) {
			com.isnowfox.web.config.ParamsConfig.Item item = (com.isnowfox.web.config.ParamsConfig.Item)list.get(i);
			if (i > 0) {
				sb.append(',');
			}
			String javaStr = StringEscapeUtils.escapeJava(item.getName());
			static class _cls1 {

				static final int $SwitchMap$com$isnowfox$web$ParameterType[];
				static final int $SwitchMap$com$isnowfox$web$config$ViewConfig$MapType[];

				static  {
					$SwitchMap$com$isnowfox$web$config$ViewConfig$MapType = new int[com.isnowfox.web.config.ViewConfig.MapType.values().length];
					try {
						$SwitchMap$com$isnowfox$web$config$ViewConfig$MapType[com.isnowfox.web.config.ViewConfig.MapType.ONLY.ordinal()] = 1;
					}
					catch (NoSuchFieldError nosuchfielderror) { }
					$SwitchMap$com$isnowfox$web$ParameterType = new int[ParameterType.values().length];
					try {
						$SwitchMap$com$isnowfox$web$ParameterType[ParameterType.COOKIE.ordinal()] = 1;
					}
					catch (NoSuchFieldError nosuchfielderror1) { }
					try {
						$SwitchMap$com$isnowfox$web$ParameterType[ParameterType.REQUEST.ordinal()] = 2;
					}
					catch (NoSuchFieldError nosuchfielderror2) { }
					try {
						$SwitchMap$com$isnowfox$web$ParameterType[ParameterType.HEADER.ordinal()] = 3;
					}
					catch (NoSuchFieldError nosuchfielderror3) { }
					try {
						$SwitchMap$com$isnowfox$web$ParameterType[ParameterType.IOC.ordinal()] = 4;
					}
					catch (NoSuchFieldError nosuchfielderror4) { }
				}
			}

			switch (_cls1..SwitchMap.com.isnowfox.web.ParameterType[item.getType().ordinal()]) {
			case 1: // '\001'
				convert(sb, item.getCls(), (new StringBuilder()).append("$3.getCookieString(\"").append(javaStr).append("\")").toString());
				break;

			case 2: // '\002'
				convert(sb, item.getCls(), (new StringBuilder()).append("$3.getString(\"").append(javaStr).append("\")").toString());
				break;

			case 3: // '\003'
				convert(sb, item.getCls(), (new StringBuilder()).append("$3.getHeader(\"").append(javaStr).append("\")").toString());
				break;

			case 4: // '\004'
				convert(sb, item.getCls(), (new StringBuilder()).append("$1.getBean(\"").append(javaStr).append("\")").toString());
				break;
			}
		}

		sb.append(");\n");
		makeViewCode(sb, actionConfig, config, pool);
		sb.append("\n}\n");
		System.out.println(sb);
		return sb.toString();
	}

	private final void makeViewCode(StringBuilder sb, ActionConfig actionConfig, Config config, ClassPool pool) {
		ViewConfig viewConfig = actionConfig.getViewConfig();
		switch (_cls1..SwitchMap.com.isnowfox.web.config.ViewConfig.MapType[viewConfig.getMapType().ordinal()]) {
		case 1: // '\001'
			viewTypes.add(viewConfig.getViewType());
			sb.append((new StringBuilder()).append("viewTypes[0].doView(\"").append(StringEscapeUtils.escapeJava(actionConfig.getPattern())).append("\", actionObj , resultObj , \"").append(StringEscapeUtils.escapeJava(viewConfig.getValue())).append("\" , ").append("$3").append(",").append("$4").append(");\n").toString());
			break;
		}
	}

	private final void convert(StringBuilder sb, Class cls, String valueExpression) {
		if (java/lang/CharSequence.isAssignableFrom(cls)) {
			sb.append(valueExpression);
		} else {
			if (ClassUtils.isVoid(cls)) {
				throw new RuntimeException("不支持Void类型参数!");
			}
			if (cls.isPrimitive()) {
				sb.append((new StringBuilder()).append(org/apache/commons/lang/math/NumberUtils.getCanonicalName()).append(".to").append(convertFistUpper(cls.getName())).append("(").append(valueExpression).append(")").toString());
			} else
			if (ClassUtils.isWrap(cls)) {
				Class primitiveCls = ClassUtils.unwrap(cls);
				sb.append((new StringBuilder()).append(cls.getCanonicalName()).append("valueOf(").append(org/apache/commons/lang/math/NumberUtils.getCanonicalName()).append(".to").append(convertFistUpper(primitiveCls.getName())).append("(").append(valueExpression).append("))").toString());
			} else {
				throw new RuntimeException((new StringBuilder()).append("暂时不支持的类型:").append(cls).toString());
			}
		}
	}

	private final String convertFistUpper(String name) {
		return (new StringBuilder()).append(Character.toUpperCase(name.charAt(0))).append(name.substring(1, name.length())).toString();
	}

	public ActionProxy build() throws InstantiationException, IllegalAccessException {
		ActionProxy obj = (ActionProxy)cls.newInstance();
		obj.viewTypes = (ViewTypeInterface[])viewTypes.toArray(new ViewTypeInterface[viewTypes.size()]);
		return obj;
	}

}
