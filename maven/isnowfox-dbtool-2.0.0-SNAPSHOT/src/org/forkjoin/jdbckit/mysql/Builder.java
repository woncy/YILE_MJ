// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   Builder.java

package org.forkjoin.jdbckit.mysql;

import com.google.common.collect.Lists;
import java.sql.*;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Referenced classes of package org.forkjoin.jdbckit.mysql:
//			Table, Config, ObjectCreate, DaoImplCreate, 
//			SpringXmlCreate, ReadOnlyDaoImplCreate, CacheDaoImplCreate

public class Builder {

	private static final Logger log = LoggerFactory.getLogger(org/forkjoin/jdbckit/mysql/Builder);
	private Config config;
	private List list;

	public Builder(Config config, Connection conn) throws SQLException {
		this.config = config;
		log.info("初始化数据库信息");
		DatabaseMetaData dm = conn.getMetaData();
		log.info((new StringBuilder()).append("数据库系统名词:").append(dm.getDatabaseProductName()).toString());
		log.info((new StringBuilder()).append("数据库系统版本:").append(dm.getDatabaseProductVersion()).toString());
		ResultSet rs = dm.getTables(null, null, null, null);
		list = Lists.newArrayList();
		do {
			if (!rs.next()) {
				break;
			}
			String type = String.valueOf(rs.getObject("TABLE_TYPE"));
			String name = String.valueOf(rs.getObject("TABLE_NAME"));
			String remark = String.valueOf(rs.getObject("REMARKS"));
			if (type.equals("TABLE")) {
				Table t = new Table(config.getTablePrefix(), conn, dm, name, type, remark);
				list.add(t);
			}
		} while (true);
		rs.close();
	}

	public void objectCreate() throws Exception {
		ObjectCreate.create(list, config, config.getObjectPack());
	}

	public void daoImplCreate() throws Exception {
		DaoImplCreate.create(list, config, config.getObjectPack(), config.getDaoPack(), null);
	}

	public void springXmlCreate() throws Exception {
		SpringXmlCreate.create(SpringXmlCreate.Type.BASE, list, config, config.getObjectPack(), config.getDaoPack(), "impl");
	}

	public void readOnlyDaoImplCreate() throws Exception {
		ReadOnlyDaoImplCreate.create(list, config, config.getObjectPack(), (new StringBuilder()).append(config.getDaoPack()).append(".readonly").toString(), null);
	}

	public void readOnlySpringXmlCreate() throws Exception {
		SpringXmlCreate.create(SpringXmlCreate.Type.READ_ONLY, list, config, config.getObjectPack(), config.getDaoPack(), "impl");
	}

	public void cacheDaoImplCreate() throws Exception {
		CacheDaoImplCreate.create(list, config, config.getObjectPack(), (new StringBuilder()).append(config.getDaoPack()).append(".cache").toString(), null);
	}

	public void cacheSpringXmlCreate() throws Exception {
		SpringXmlCreate.create(SpringXmlCreate.Type.CACHE, list, config, config.getObjectPack(), config.getDaoPack(), "impl");
	}

}
