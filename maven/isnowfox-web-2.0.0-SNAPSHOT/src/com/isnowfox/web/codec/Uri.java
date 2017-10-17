// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   Uri.java

package com.isnowfox.web.codec;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.*;
import org.apache.commons.lang.StringUtils;
import org.jboss.netty.handler.codec.http.HttpConstants;

public final class Uri {

	public static final int DEFAULT_MAX_PARAMS = 1024;
	private Charset charset;
	private String uri;
	private int maxParams;
	private boolean isDir;
	private String path;
	private String fileName;
	private String fileExtensionName;
	private String pattern;
	private Map params;
	private int nParams;

	public Uri(String uri, Charset charset, int maxParams, String defaultFileName) {
		reset(uri, charset, maxParams, defaultFileName);
	}

	public void reset(String uri, Charset charset, int maxParams, String defaultFileName) {
		if (uri == null) {
			throw new NullPointerException("uri");
		}
		if (uri.isEmpty()) {
			throw new NullPointerException("uri empty");
		}
		if (charset == null) {
			throw new NullPointerException("charset");
		}
		if (maxParams <= 0) {
			throw new IllegalArgumentException((new StringBuilder()).append("maxParams: ").append(maxParams).append(" (expected: a positive integer)").toString());
		}
		this.uri = uri.replace(';', '&');
		this.charset = charset;
		this.maxParams = maxParams;
		int pathEndPos = uri.indexOf('?');
		if (pathEndPos < 0) {
			path = uri;
		} else {
			path = uri.substring(0, pathEndPos);
		}
		isDir = path.charAt(path.length() - 1) == '/';
		if (isDir) {
			fileName = defaultFileName;
			int extPos = defaultFileName.lastIndexOf('.');
			if (extPos < 0) {
				fileExtensionName = null;
				pattern = defaultFileName;
			} else {
				fileExtensionName = defaultFileName.substring(extPos + 1);
				pattern = defaultFileName.substring(0, extPos);
			}
		} else {
			int namePos = path.lastIndexOf('/');
			if (namePos < 0) {
				fileName = path;
			} else {
				fileName = path.substring(namePos + 1);
			}
			int extPos = path.lastIndexOf('.');
			if (extPos < 0) {
				fileExtensionName = null;
				pattern = path;
			} else {
				fileExtensionName = path.substring(extPos + 1);
				pattern = path.substring(0, extPos);
			}
		}
	}

	public boolean isExtensionType(String suffix) {
		return StringUtils.isEmpty(suffix) ? StringUtils.isEmpty(fileExtensionName) : suffix.equals(fileExtensionName);
	}

	public String getPath() {
		return path;
	}

	public String getFileName() {
		return fileName;
	}

	public String getFileExtensionName() {
		return fileExtensionName;
	}

	public Map getParameters() {
		if (params == null) {
			int pathLength = getPath().length();
			if (uri.length() == pathLength) {
				return Collections.emptyMap();
			}
			decodeParams(uri.substring(pathLength + 1));
		}
		return params;
	}

	public boolean isDir() {
		return isDir;
	}

	private void decodeParams(String s) {
		Map params = this.params = new LinkedHashMap();
		nParams = 0;
		String name = null;
		int pos = 0;
		int i;
		for (i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '=' && name == null) {
				if (pos != i) {
					name = decodeComponent(s.substring(pos, i), charset);
				}
				pos = i + 1;
				continue;
			}
			if (c != '&') {
				continue;
			}
			if (name == null && pos != i) {
				if (!addParam(params, decodeComponent(s.substring(pos, i), charset), "")) {
					return;
				}
			} else
			if (name != null) {
				if (!addParam(params, name, decodeComponent(s.substring(pos, i), charset))) {
					return;
				}
				name = null;
			}
			pos = i + 1;
		}

		if (pos != i) {
			if (name == null) {
				addParam(params, decodeComponent(s.substring(pos, i), charset), "");
			} else {
				addParam(params, name, decodeComponent(s.substring(pos, i), charset));
			}
		} else
		if (name != null) {
			addParam(params, name, "");
		}
	}

	private boolean addParam(Map params, String name, String value) {
		if (nParams >= maxParams) {
			return false;
		}
		List values = (List)params.get(name);
		if (values == null) {
			values = new ArrayList(1);
			params.put(name, values);
		}
		values.add(value);
		nParams++;
		return true;
	}

	public static String decodeComponent(String s) {
		return decodeComponent(s, HttpConstants.DEFAULT_CHARSET);
	}

	public static String decodeComponent(String s, Charset charset) {
		byte buf[];
		int pos;
		if (s == null) {
			return "";
		}
		int size = s.length();
		boolean modified = false;
		int i = 0;
		do {
			if (i >= size) {
				break;
			}
			char c = s.charAt(i);
			switch (c) {
			case 37: // '%'
				i++;
				// fall through

			case 43: // '+'
				modified = true;
				// fall through

			default:
				i++;
				break;
			}
		} while (true);
		if (!modified) {
			return s;
		}
		buf = new byte[size];
		pos = 0;
		for (int i = 0; i < size; i++) {
			char c = s.charAt(i);
			switch (c) {
			case 43: // '+'
				buf[pos++] = 32;
				break;

			case 37: // '%'
				if (i == size - 1) {
					throw new IllegalArgumentException((new StringBuilder()).append("unterminated escape sequence at end of string: ").append(s).toString());
				}
				c = s.charAt(++i);
				if (c == '%') {
					buf[pos++] = 37;
					break;
				}
				if (i == size - 1) {
					throw new IllegalArgumentException((new StringBuilder()).append("partial escape sequence at end of string: ").append(s).toString());
				}
				c = decodeHexNibble(c);
				char c2 = decodeHexNibble(s.charAt(++i));
				if (c == '\uFFFF' || c2 == '\uFFFF') {
					throw new IllegalArgumentException((new StringBuilder()).append("invalid escape sequence `%").append(s.charAt(i - 1)).append(s.charAt(i)).append("' at index ").append(i - 2).append(" of: ").append(s).toString());
				}
				c = (char)(c * 16 + c2);
				// fall through

			default:
				buf[pos++] = (byte)c;
				break;
			}
		}

		return new String(buf, 0, pos, charset.name());
		UnsupportedEncodingException e;
		e;
		throw new IllegalArgumentException((new StringBuilder()).append("unsupported encoding: ").append(charset.name()).toString(), e);
	}

	private static char decodeHexNibble(char c) {
		if ('0' <= c && c <= '9') {
			return (char)(c - 48);
		}
		if ('a' <= c && c <= 'f') {
			return (char)((c - 97) + 10);
		}
		if ('A' <= c && c <= 'F') {
			return (char)((c - 65) + 10);
		} else {
			return '\uFFFF';
		}
	}

	public String getPattern() {
		return pattern;
	}

	public String toString() {
		return (new StringBuilder()).append("Uri [charset=").append(charset).append(", uri=").append(uri).append(", maxParams=").append(maxParams).append(", isDir=").append(isDir).append(", path=").append(path).append(", fileName=").append(fileName).append(", fileExtensionName=").append(fileExtensionName).append(", params=").append(params).append(", nParams=").append(nParams).append("]").toString();
	}
}
