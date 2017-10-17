// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   SpringXmlCreate.java

package org.forkjoin.jdbckit.mysql;

import java.io.*;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Referenced classes of package org.forkjoin.jdbckit.mysql:
//			Config, HttlUtils

public class SpringXmlCreate {
	public static final class Type extends Enum {

		public static final Type BASE;
		public static final Type READ_ONLY;
		public static final Type CACHE;
		final String contextFileName;
		final String packName;
		final String anotherName;
		private static final Type $VALUES[];

		public static Type[] values() {
			return (Type[])$VALUES.clone();
		}

		public static Type valueOf(String name) {
			return (Type)Enum.valueOf(org/forkjoin/jdbckit/mysql/SpringXmlCreate$Type, name);
		}

		static  {
			BASE = new Type("BASE", 0, "DaoContext.xml", "", "");
			READ_ONLY = new Type("READ_ONLY", 1, "ReadOnlyDaoContext.xml", ".readonly", "ReadOnly");
			CACHE = new Type("CACHE", 2, "CacheDaoContext.xml", ".cache", "Cache");
			$VALUES = (new Type[] {
				BASE, READ_ONLY, CACHE
			});
		}

		private Type(String s, int i, String contextFileName, String packName, String anotherName) {
			super(s, i);
			this.contextFileName = contextFileName;
			this.packName = packName;
			this.anotherName = anotherName;
		}
	}


	private static final Logger log = LoggerFactory.getLogger(org/forkjoin/jdbckit/mysql/SpringXmlCreate);

	public SpringXmlCreate() {
	}

	public static void create(Type type, List tl, Config config, String objectPack, String daoPack, String daoImplPack) throws Exception {
		OutputStream out;
		Throwable throwable;
		Exception exception;
		File dir = config.getResourcesPackPath("");
		if (!dir.exists() && !dir.mkdirs()) {
			throw new RuntimeException((new StringBuilder()).append("´´½¨Â·¾¶Ê§°Ü:").append(dir.getAbsolutePath()).toString());
		}
		File f = new File(dir, type.contextFileName);
		log.debug("ObjectCreate file:{}", f.getAbsolutePath());
		out = new BufferedOutputStream(new FileOutputStream(f));
		throwable = null;
		try {
			Map context = new HashMap();
			String pack = config.getPack(daoPack);
			context.put("pack", (new StringBuilder()).append(pack).append(type.packName).toString());
			context.put("tables", tl);
			context.put("anotherName", type.anotherName);
			HttlUtils.render("/org/forkjoin/jdbckit/mysql/template/springContextImpl.httl", context, out);
		}
		catch (Throwable throwable2) {
			throwable = throwable2;
			throw throwable2;
		}
		finally {
			if (out == null) goto _L0; else goto _L0
		}
		if (out != null) {
			if (throwable != null) {
				try {
					out.close();
				}
				catch (Throwable throwable1) {
					throwable.addSuppressed(throwable1);
				}
			} else {
				out.close();
			}
		}
		break MISSING_BLOCK_LABEL_276;
		if (throwable != null) {
			try {
				out.close();
			}
			catch (Throwable throwable3) {
				throwable.addSuppressed(throwable3);
			}
		} else {
			out.close();
		}
		throw exception;
	}

}
