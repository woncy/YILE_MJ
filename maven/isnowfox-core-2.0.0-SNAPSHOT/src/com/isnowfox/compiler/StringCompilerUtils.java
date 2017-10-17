// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   StringCompilerUtils.java

package com.isnowfox.compiler;

import java.io.StringWriter;
import java.net.URI;
import java.util.Arrays;
import javax.tools.*;

// Referenced classes of package com.isnowfox.compiler:
//			StringObject, BytesFileManager

public class StringCompilerUtils {

	private static JavaCompiler compiler;
	private static BytesFileManager fileManager;

	public StringCompilerUtils() {
	}

	public static URI url(String name, javax.tools.JavaFileObject.Kind kind) {
		return URI.create((new StringBuilder()).append("string:///").append(name.replace('.', '/')).append(kind.extension).toString());
	}

	public static synchronized Class compiler(String className, StringBuilder javaCode) throws Exception {
		StringObject so = new StringObject(className, javaCode);
		StringWriter stringWriter = new StringWriter();
		javax.tools.JavaCompiler.CompilationTask task = compiler.getTask(stringWriter, fileManager, null, null, null, Arrays.asList(new StringObject[] {
			so
		}));
		Boolean result = task.call();
		if (!result.booleanValue()) {
			throw new RuntimeException((new StringBuilder()).append("编译失败！\n").append(stringWriter).toString());
		} else {
			return fileManager.getClassLoader(null).loadClass(className);
		}
	}

	static  {
		compiler = ToolProvider.getSystemJavaCompiler();
		fileManager = new BytesFileManager(compiler.getStandardFileManager(null, null, null));
		if (compiler == null) {
			throw new RuntimeException("需要编译api，classpath里面没用tools.jar");
		}
	}
}
