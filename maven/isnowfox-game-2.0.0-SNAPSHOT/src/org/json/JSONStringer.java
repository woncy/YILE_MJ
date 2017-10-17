// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   JSONStringer.java

package org.json;

import java.io.StringWriter;

// Referenced classes of package org.json:
//			JSONWriter

public class JSONStringer extends JSONWriter {

	public JSONStringer() {
		super(new StringWriter());
	}

	public String toString() {
		return mode != 'd' ? null : writer.toString();
	}
}
