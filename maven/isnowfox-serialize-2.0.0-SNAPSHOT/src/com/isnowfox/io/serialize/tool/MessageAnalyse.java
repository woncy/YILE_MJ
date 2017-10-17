// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   MessageAnalyse.java

package com.isnowfox.io.serialize.tool;

import com.isnowfox.io.serialize.tool.model.Message;
import com.isnowfox.util.StringExpandUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang.math.NumberUtils;

// Referenced classes of package com.isnowfox.io.serialize.tool:
//			Config

public class MessageAnalyse {

	private static final Pattern COMMENT_PATTERN = Pattern.compile("//(.*)");
	private static final Pattern HEAD_PATTERN = Pattern.compile("\\s*+message\\s*+");
	private static final Pattern HANDLER_PATTERN = Pattern.compile("\\s*+handler\\s*+=\\s*+([\\w_$.,]+)");
	private static final Pattern ATTRIBUTE_PATTERN = Pattern.compile("\\s*+([\\w_$.]+)(\\[(\\d)*?\\])*?\\s*+([\\w_$]+)");
	private Message msg;
	private StringBuilder sb;

	public MessageAnalyse(Config cfg) {
		sb = new StringBuilder();
	}

	public Message analyse(LineIterator it, String pack) {
		sb.setLength(0);
		msg = new Message();
		String line;
		for (; it.hasNext(); analyseLine(line)) {
			line = it.nextLine();
		}

		if (pack == null) {
			pack = "";
		}
		msg.setPackageName(pack);
		return msg;
	}

	private void analyseLine(String line) {
		Matcher m = COMMENT_PATTERN.matcher(line);
		if (m.find()) {
			sb.append('\n');
			sb.append(StringExpandUtils.trim(m.group(1)));
			line = line.substring(0, m.start());
		}
		m = HEAD_PATTERN.matcher(line);
		if (m.find()) {
			if (sb.length() > 2) {
				msg.setComment(sb.substring(1, sb.length()));
			}
			sb.setLength(0);
			return;
		}
		m = HANDLER_PATTERN.matcher(line);
		if (m.find()) {
			String array[] = m.group(1).split(",");
			String as[] = array;
			int i = as.length;
			for (int j = 0; j < i; j++) {
				String string = as[j];
				if (string.equalsIgnoreCase("client")) {
					msg.setClientHandler(true);
				} else
				if (string.equalsIgnoreCase("server")) {
					msg.setServerHandler(true);
				}
			}

		} else {
			m = ATTRIBUTE_PATTERN.matcher(line);
			if (m.find()) {
				String comment = null;
				boolean isArray = StringExpandUtils.isNotBlank(m.group(2));
				int arrayNums = NumberUtils.toInt(m.group(3), 0);
				if (sb.length() > 2) {
					comment = sb.substring(1, sb.length());
				}
				sb.setLength(0);
				msg.add(m.group(1), m.group(4), comment, isArray, arrayNums);
			}
		}
	}

}
