// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   StringObject.java

package com.isnowfox.compiler;

import java.io.IOException;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;

// Referenced classes of package com.isnowfox.compiler:
//			StringCompilerUtils

public class StringObject extends SimpleJavaFileObject {

	private StringBuilder contents;

	public StringObject(String name, StringBuilder contents) throws Exception {
		super(StringCompilerUtils.url(name, javax.tools.JavaFileObject.Kind.SOURCE), javax.tools.JavaFileObject.Kind.SOURCE);
		this.contents = null;
		this.contents = contents;
	}

	public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
		return contents;
	}
}
