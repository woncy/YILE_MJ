// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   MainFrame.java

package com.isnowfox.io.serialize.tool.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

// Referenced classes of package com.isnowfox.io.serialize.tool.ui:
//			MainFrame

class MainFrame$3
	implements ActionListener {

	final JFileChooser val$chooser;
	final MainFrame this$0;

	public void actionPerformed(ActionEvent e) {
		val$chooser.setSelectedFile(new File(MainFrame.access$200(MainFrame.this).getText()));
		int i = val$chooser.showOpenDialog(MainFrame.this);
		if (i == 0) {
			MainFrame.access$200(MainFrame.this).setText(val$chooser.getSelectedFile().getAbsolutePath());
		}
	}

	MainFrame$3() {
		this.this$0 = this$0;
		val$chooser = JFileChooser.this;
		super();
	}
}
