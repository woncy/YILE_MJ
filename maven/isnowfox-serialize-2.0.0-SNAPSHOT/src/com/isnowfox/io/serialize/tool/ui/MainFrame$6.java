// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   MainFrame.java

package com.isnowfox.io.serialize.tool.ui;

import com.isnowfox.io.serialize.tool.Config;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.swing.JTextArea;
import org.slf4j.Logger;

// Referenced classes of package com.isnowfox.io.serialize.tool.ui:
//			MainFrame

class MainFrame$6
	implements ActionListener {

	final Config val$c;
	final JTextArea val$logArea;
	final MainFrame this$0;

	public void actionPerformed(ActionEvent event) {
		updateCfg();
		try {
			val$c.save();
		}
		catch (Exception e) {
			MainFrame.access$300().info(e.getMessage(), e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			val$logArea.append((new StringBuilder()).append(sw.toString()).append("\n").toString());
		}
	}

	MainFrame$6() {
		this.this$0 = this$0;
		val$c = config;
		val$logArea = JTextArea.this;
		super();
	}
}
