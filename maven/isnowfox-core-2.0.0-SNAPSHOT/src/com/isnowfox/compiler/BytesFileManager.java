// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   BytesFileManager.java

package com.isnowfox.compiler;

import java.io.IOException;
import java.security.SecureClassLoader;
import java.util.HashMap;
import java.util.Map;
import javax.tools.*;

// Referenced classes of package com.isnowfox.compiler:
//			BytesJavaFileObject

public class BytesFileManager extends ForwardingJavaFileManager {

	private BytesJavaFileObject jclassObject;
	private Map map;
	private SecureClassLoader secureClassLoader;

	public BytesFileManager(StandardJavaFileManager standardManager) {
		super(standardManager);
		map = new HashMap();
		secureClassLoader = new SecureClassLoader(ClassLoader.getSystemClassLoader()) {

			final BytesFileManager this$0;

			protected Class findClass(String name) throws ClassNotFoundException {
				BytesJavaFileObject fileObject = (BytesJavaFileObject)map.get(name);
				if (fileObject != null) {
					byte b[] = fileObject.getBytes();
					map.remove(name);
					return super.defineClass(name, b, 0, b.length);
				} else {
					return null;
				}
			}

			 {
				this.this$0 = BytesFileManager.this;
				super(x0);
			}
		};
	}

	public ClassLoader getClassLoader(javax.tools.JavaFileManager.Location location) {
		return secureClassLoader;
	}

	public JavaFileObject getJavaFileForOutput(javax.tools.JavaFileManager.Location location, String className, javax.tools.JavaFileObject.Kind kind, FileObject sibling) throws IOException {
		BytesJavaFileObject fileObject = new BytesJavaFileObject(className, kind);
		map.put(className, fileObject);
		return fileObject;
	}

}
