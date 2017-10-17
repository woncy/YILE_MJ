// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   ReadOnlyDaoImplCreate.java

package org.forkjoin.jdbckit.mysql;

import java.io.*;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Referenced classes of package org.forkjoin.jdbckit.mysql:
//			Table, SqlUtils, Config, HttlUtils

public class ReadOnlyDaoImplCreate {

	private static final Logger log = LoggerFactory.getLogger(org/forkjoin/jdbckit/mysql/ReadOnlyDaoImplCreate);

	public ReadOnlyDaoImplCreate() {
	}

	public static void create(List tl, Config config, String objectPack, String daoPack, String daoImplPack) throws Exception {
		OutputStream out;
		Throwable throwable;
		Exception exception;
		File dir;
		if (daoImplPack == null) {
			dir = config.getPackPath(daoPack);
		} else {
			dir = config.getPackPath((new StringBuilder()).append(daoPack).append(".").append(daoImplPack).toString());
		}
		if (!dir.exists() && !dir.mkdirs()) {
			throw new RuntimeException((new StringBuilder()).append("´´½¨Â·¾¶Ê§°Ü:").append(dir.getAbsolutePath()).toString());
		}
		Iterator iterator = tl.iterator();
		do {
			if (!iterator.hasNext()) {
				break;
			}
			Table ta = (Table)iterator.next();
			File f = new File(dir, (new StringBuilder()).append(ta.getClassName()).append("ReadOnlyDao.java").toString());
			log.debug("ObjectCreate file:{}", f.getAbsolutePath());
			out = new BufferedOutputStream(new FileOutputStream(f));
			throwable = null;
			try {
				Map context = new HashMap();
				context.put("t", ta);
				context.put("beanPack", config.getPack(objectPack));
				String pack = config.getPack(daoPack);
				if (daoImplPack == null) {
					context.put("implPack", pack);
				} else {
					context.put("implPack", config.getPack(pack, daoImplPack));
				}
				context.put("daoPack", pack);
				context.put("sql", new SqlUtils());
				HttlUtils.render("/org/forkjoin/jdbckit/mysql/template/readOnlyDaoImpl.httl", context, out);
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
		} while (true);
		break MISSING_BLOCK_LABEL_401;
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
