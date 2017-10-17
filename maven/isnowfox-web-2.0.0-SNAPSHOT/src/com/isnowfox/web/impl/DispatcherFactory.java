// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   DispatcherFactory.java

package com.isnowfox.web.impl;

import com.isnowfox.util.MimeUtils;
import com.isnowfox.web.*;
import com.isnowfox.web.codec.Uri;
import java.io.File;
import java.io.FileInputStream;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

// Referenced classes of package com.isnowfox.web.impl:
//			BaseDispatcher

public class DispatcherFactory {

	public DispatcherFactory() {
	}

	public static final Dispatcher create(Config config, Server server) throws Exception {
		com.isnowfox.web.Config.StaticHandleType type = config.getStaticType();
		if (type == com.isnowfox.web.Config.StaticHandleType.NONE) {
			return new BaseDispatcher(config, server) {

				public boolean disposeStaticFile(Uri uri, Response resp) {
					return false;
				}

			};
		}
		if (type == com.isnowfox.web.Config.StaticHandleType.NOT_CACHE) {
			return new BaseDispatcher(config, server, config) {

				final Config val$config;

				public boolean disposeStaticFile(Uri uri, Response resp) throws Exception {
					return DispatcherFactory.noCacheDisposeStaticFile(config, uri.getPath(), resp);
				}

			 {
				config = config1;
				super(config, server);
			}
			};
		} else {
			return null;
		}
	}

	private static final boolean noCacheDisposeStaticFile(Config config, String path, Response resp) throws Exception {
		File file;
		FileInputStream fi;
		File dir = new File(config.getStaticFilePath());
		file = new File(dir, path);
		if (!file.exists()) {
			break MISSING_BLOCK_LABEL_129;
		}
		if (file.isDirectory()) {
			file = new File(file, "index.html");
			if (!file.exists()) {
				return false;
			}
		}
		fi = new FileInputStream(file);
		byte data[] = IOUtils.toByteArray(fi);
		String contentType = MimeUtils.getMimeType(FilenameUtils.getExtension(file.getName()));
		resp.setContentType(contentType);
		resp.oneWrite(data);
		fi.close();
		break MISSING_BLOCK_LABEL_127;
		Exception exception;
		exception;
		fi.close();
		throw exception;
		return true;
		resp.sendError(404);
		return false;
	}

}
