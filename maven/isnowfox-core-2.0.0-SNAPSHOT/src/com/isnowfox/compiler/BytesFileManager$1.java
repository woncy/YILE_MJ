// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   BytesFileManager.java

package com.isnowfox.compiler;

import java.security.SecureClassLoader;
import java.util.Map;

// Referenced classes of package com.isnowfox.compiler:
//			BytesJavaFileObject, BytesFileManager

class BytesFileManager$1 extends SecureClassLoader {

	final BytesFileManager this$0;

	protected Class findClass(String name) throws ClassNotFoundException {
		BytesJavaFileObject fileObject = (BytesJavaFileObject)BytesFileManager.access$000(BytesFileManager.this).get(name);
		if (fileObject != null) {
			byte b[] = fileObject.getBytes();
			BytesFileManager.access$000(BytesFileManager.this).remove(name);
			return super.defineClass(name, b, 0, b.length);
		} else {
			return null;
		}
	}

	BytesFileManager$1(ClassLoader x0) {
		this.this$0 = BytesFileManager.this;
		super(x0);
	}
}
