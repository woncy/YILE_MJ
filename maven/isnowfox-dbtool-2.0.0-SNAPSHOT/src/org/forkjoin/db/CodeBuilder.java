// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   CodeBuilder.java

package org.forkjoin.db;

import com.isnowfox.core.IocFactory;
import com.isnowfox.core.SpringIocFactory;
import java.io.File;
import java.sql.Connection;
import javax.sql.DataSource;
import org.forkjoin.jdbckit.mysql.Builder;
import org.forkjoin.jdbckit.mysql.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CodeBuilder {

	private static final Logger log = LoggerFactory.getLogger(org/forkjoin/db/CodeBuilder);

	public CodeBuilder() {
	}

	public static void main(String args[]) {
		Config config;
		DataSource ds;
		Connection conn;
		String path = "src/main/java/";
		String resourcesPath = "src/main/resources/";
		File dir = new File(path);
		File resourcesDir = new File(resourcesPath);
		log.info("´úÂëÂ·¾¶:{}", dir.getAbsolutePath());
		config = new Config(dir, resourcesDir);
		config.setPack("io.grass.test");
		IocFactory ioc = new SpringIocFactory(new String[] {
			"testContext.xml"
		});
		ds = (DataSource)ioc.getBean("dataSource", javax/sql/DataSource);
		conn = null;
		conn = ds.getConnection();
		Builder builder = new Builder(config, conn);
		builder.objectCreate();
		builder.daoImplCreate();
		builder.springXmlCreate();
		if (conn != null) {
			conn.close();
		}
		break MISSING_BLOCK_LABEL_172;
		Exception exception;
		exception;
		if (conn != null) {
			conn.close();
		}
		throw exception;
		Exception e;
		e;
		e.printStackTrace();
	}

}
