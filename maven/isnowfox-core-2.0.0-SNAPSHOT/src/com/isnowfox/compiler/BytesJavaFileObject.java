// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   BytesJavaFileObject.java

package com.isnowfox.compiler;

import java.io.*;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;

// Referenced classes of package com.isnowfox.compiler:
//			StringCompilerUtils

public class BytesJavaFileObject extends SimpleJavaFileObject {

	protected final ByteArrayOutputStream out = new ByteArrayOutputStream();

	public BytesJavaFileObject(String name, javax.tools.JavaFileObject.Kind kind) {
		super(StringCompilerUtils.url(name, kind), kind);
	}

	public OutputStream openOutputStream() throws IOException {
		return out;
	}

	public byte[] getBytes() {
		return out.toByteArray();
	}
}
