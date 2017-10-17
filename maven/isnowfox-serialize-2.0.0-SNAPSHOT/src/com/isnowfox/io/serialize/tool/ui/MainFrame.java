// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   MainFrame.java

package com.isnowfox.io.serialize.tool.ui;

import com.isnowfox.io.serialize.tool.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.concurrent.ExecutionException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import net.miginfocom.swing.MigLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 0x216a7d2ef95c2c4cL;
	private static final Logger log = LoggerFactory.getLogger(com/isnowfox/io/serialize/tool/ui/MainFrame);
	private JPanel contentPane;
	private JTextField pathField;
	private JTextField javaField;
	private JTextField asField;
	private JTextField javaPackageField;
	private JTextField asPackageField;
	private JTextField javaHandlerPackage;
	private JTextField asHandlerPackage;
	private final Config c;

	public MainFrame(final Config c) {
		this.c = c;
		final JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(1);
		setTitle("msg 工具");
		setResizable(false);
		setDefaultCloseOperation(3);
		setBounds(100, 100, 750, 689);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((d.width - getWidth()) / 2, (d.height - getHeight()) / 2);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(new TitledBorder(null, "设置", 4, 2, null, null));
		contentPane.add(mainPanel, "North");
		mainPanel.setLayout(new MigLayout("", "[grow][561px,grow]", "[32.00px][32.00][32.00][32.00][32.00][32.00,grow][32.00]"));
		JLabel lblNewLabel = new JLabel("消息文件目录：");
		mainPanel.add(lblNewLabel, "cell 0 0,alignx right,aligny center");
		pathField = new JTextField();
		mainPanel.add(pathField, "flowx,cell 1 0");
		pathField.setEditable(false);
		pathField.setColumns(40);
		pathField.setText(c.getPath());
		JButton selectPathButton = new JButton("选择目录");
		mainPanel.add(selectPathButton, "cell 1 0");
		JLabel lblJava = new JLabel("java代码目录：");
		mainPanel.add(lblJava, "cell 0 1,alignx trailing,aligny center");
		javaField = new JTextField();
		javaField.setEditable(false);
		mainPanel.add(javaField, "flowx,cell 1 1");
		javaField.setColumns(40);
		javaField.setText(c.getJavaSrcPath());
		JButton selectJavaButton = new JButton("选择目录");
		selectJavaButton.addActionListener(new ActionListener() {

			final JFileChooser val$chooser;
			final MainFrame this$0;

			public void actionPerformed(ActionEvent e) {
				chooser.setSelectedFile(new File(javaField.getText()));
				int i = chooser.showOpenDialog(MainFrame.this);
				if (i == 0) {
					javaField.setText(chooser.getSelectedFile().getAbsolutePath());
				}
			}

			 {
				this.this$0 = MainFrame.this;
				chooser = jfilechooser;
				super();
			}
		});
		mainPanel.add(selectJavaButton, "cell 1 1");
		JLabel lblAs = new JLabel("AS代码目录：");
		mainPanel.add(lblAs, "cell 0 2,alignx trailing,aligny center");
		asField = new JTextField();
		asField.setEditable(false);
		mainPanel.add(asField, "flowx,cell 1 2");
		asField.setColumns(40);
		asField.setText(c.getAsSrcPath());
		JButton selectAsButton = new JButton("选择目录");
		selectAsButton.addActionListener(new ActionListener() {

			final JFileChooser val$chooser;
			final MainFrame this$0;

			public void actionPerformed(ActionEvent e) {
				chooser.setSelectedFile(new File(asField.getText()));
				int i = chooser.showOpenDialog(MainFrame.this);
				if (i == 0) {
					asField.setText(chooser.getSelectedFile().getAbsolutePath());
				}
			}

			 {
				this.this$0 = MainFrame.this;
				chooser = jfilechooser;
				super();
			}
		});
		mainPanel.add(selectAsButton, "cell 1 2");
		JLabel lblNewLabel_1 = new JLabel("java根包：");
		mainPanel.add(lblNewLabel_1, "cell 0 3,alignx trailing,aligny center");
		javaPackageField = new JTextField();
		mainPanel.add(javaPackageField, "cell 1 3");
		javaPackageField.setColumns(60);
		javaPackageField.setText(c.getJavaRootPackage());
		JLabel lblNewLabel_2 = new JLabel("as根包：");
		mainPanel.add(lblNewLabel_2, "cell 0 4,alignx trailing,aligny center");
		asPackageField = new JTextField();
		mainPanel.add(asPackageField, "cell 1 4");
		asPackageField.setColumns(60);
		asPackageField.setText(c.getAsRootPackage());
		JLabel lblJava_1 = new JLabel("java处理器包");
		mainPanel.add(lblJava_1, "cell 0 5,alignx trailing,aligny center");
		javaHandlerPackage = new JTextField();
		mainPanel.add(javaHandlerPackage, "cell 1 5,alignx left");
		javaHandlerPackage.setColumns(60);
		javaHandlerPackage.setText(c.getJavaHandlerRootPackage());
		JLabel lblNewLabel_3 = new JLabel("as处理器包");
		mainPanel.add(lblNewLabel_3, "cell 0 6,alignx trailing,aligny center");
		asHandlerPackage = new JTextField();
		mainPanel.add(asHandlerPackage, "cell 1 6,alignx left");
		asHandlerPackage.setColumns(60);
		asHandlerPackage.setText(c.getAsHandlerRootPackage());
		selectPathButton.addActionListener(new ActionListener() {

			final JFileChooser val$chooser;
			final MainFrame this$0;

			public void actionPerformed(ActionEvent e) {
				chooser.setSelectedFile(new File(pathField.getText()));
				int i = chooser.showOpenDialog(MainFrame.this);
				if (i == 0) {
					pathField.setText(chooser.getSelectedFile().getAbsolutePath());
				}
			}

			 {
				this.this$0 = MainFrame.this;
				chooser = jfilechooser;
				super();
			}
		});
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "日志", 4, 2, null, null));
		contentPane.add(panel_1, "Center");
		panel_1.setLayout(new BoxLayout(panel_1, 0));
		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane);
		final JTextArea logArea = new JTextArea();
		scrollPane.setViewportView(logArea);
		JPanel panel = new JPanel();
		contentPane.add(panel, "South");
		final JButton startButtonServer = new JButton("生成服务器文件");
		final JButton startButtonClient = new JButton("生成客户端文件");
		startButtonServer.setMargin(new Insets(12, 24, 12, 24));
		startButtonServer.setFont(new Font("宋体", 0, 18));
		startButtonServer.addActionListener(new ActionListener() {

			final JButton val$startButtonServer;
			final JButton val$startButtonClient;
			final Config val$c;
			final JTextArea val$logArea;
			final MainFrame this$0;

			public void actionPerformed(ActionEvent e) {
				startButtonServer.setEnabled(false);
				startButtonClient.setEnabled(false);
				updateCfg();
				(new SwingWorker() {

					final _cls4 this$1;

					protected Void doInBackground() throws Exception {
						MessageBuilder b = new MessageBuilder(c, new LogProxy() {

							final _cls1 this$2;

							public transient void info(String str, Object args[]) {
								String msg = String.format(str, args);
								MainFrame.log.info(msg);
								logArea.append((new StringBuilder()).append(msg).append("\n").toString());
							}

						 {
							this.this$2 = _cls1.this;
							super();
						}
						});
						b.buildServer();
						return null;
					}

					protected void done() {
						try {
							get();
						}
						catch (InterruptedException e) {
							MainFrame.log.info(e.getMessage(), e);
							StringWriter sw = new StringWriter();
							e.printStackTrace(new PrintWriter(sw));
							logArea.append((new StringBuilder()).append(sw.toString()).append("\n").toString());
						}
						catch (ExecutionException ee) {
							Throwable e = ee.getCause();
							MainFrame.log.info(e.getMessage(), e);
							StringWriter sw = new StringWriter();
							e.printStackTrace(new PrintWriter(sw));
							logArea.append((new StringBuilder()).append(sw.toString()).append("\n").toString());
						}
						startButtonServer.setEnabled(true);
						startButtonClient.setEnabled(true);
					}

					protected volatile Object doInBackground() throws Exception {
						return doInBackground();
					}

					 {
						this.this$1 = _cls4.this;
						super();
					}
				}).execute();
			}

			 {
				this.this$0 = MainFrame.this;
				startButtonServer = jbutton;
				startButtonClient = jbutton1;
				c = config;
				logArea = jtextarea;
				super();
			}
		});
		panel.add(startButtonServer);
		startButtonClient.addActionListener(new ActionListener() {

			final JButton val$startButtonServer;
			final JButton val$startButtonClient;
			final Config val$c;
			final JTextArea val$logArea;
			final MainFrame this$0;

			public void actionPerformed(ActionEvent e) {
				startButtonServer.setEnabled(false);
				startButtonClient.setEnabled(false);
				updateCfg();
				(new SwingWorker() {

					final _cls5 this$1;

					protected Void doInBackground() throws Exception {
						MessageBuilder b = new MessageBuilder(c, new LogProxy() {

							final _cls1 this$2;

							public transient void info(String str, Object args[]) {
								String msg = String.format(str, args);
								MainFrame.log.info(msg);
								logArea.append((new StringBuilder()).append(msg).append("\n").toString());
							}

						 {
							this.this$2 = _cls1.this;
							super();
						}
						});
						b.buildClient();
						return null;
					}

					protected void done() {
						try {
							get();
						}
						catch (InterruptedException e) {
							MainFrame.log.info(e.getMessage(), e);
							StringWriter sw = new StringWriter();
							e.printStackTrace(new PrintWriter(sw));
							logArea.append((new StringBuilder()).append(sw.toString()).append("\n").toString());
						}
						catch (ExecutionException ee) {
							Throwable e = ee.getCause();
							MainFrame.log.info(e.getMessage(), e);
							StringWriter sw = new StringWriter();
							e.printStackTrace(new PrintWriter(sw));
							logArea.append((new StringBuilder()).append(sw.toString()).append("\n").toString());
						}
						startButtonServer.setEnabled(true);
						startButtonClient.setEnabled(true);
					}

					protected volatile Object doInBackground() throws Exception {
						return doInBackground();
					}

					 {
						this.this$1 = _cls5.this;
						super();
					}
				}).execute();
			}

			 {
				this.this$0 = MainFrame.this;
				startButtonServer = jbutton;
				startButtonClient = jbutton1;
				c = config;
				logArea = jtextarea;
				super();
			}
		});
		startButtonClient.setMargin(new Insets(12, 24, 12, 24));
		startButtonClient.setFont(new Font("宋体", 0, 18));
		panel.add(startButtonClient);
		JButton saveConfig = new JButton("保存配置");
		saveConfig.addActionListener(new ActionListener() {

			final Config val$c;
			final JTextArea val$logArea;
			final MainFrame this$0;

			public void actionPerformed(ActionEvent event) {
				updateCfg();
				try {
					c.save();
				}
				catch (Exception e) {
					MainFrame.log.info(e.getMessage(), e);
					StringWriter sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					logArea.append((new StringBuilder()).append(sw.toString()).append("\n").toString());
				}
			}

			 {
				this.this$0 = MainFrame.this;
				c = config;
				logArea = jtextarea;
				super();
			}
		});
		saveConfig.setMargin(new Insets(12, 24, 12, 24));
		saveConfig.setFont(new Font("宋体", 0, 18));
		panel.add(saveConfig);
	}

	public void updateCfg() {
		c.setAsSrcPath(asField.getText());
		c.setJavaSrcPath(javaField.getText());
		c.setPath(pathField.getText());
		c.setJavaRootPackage(javaPackageField.getText().trim());
		c.setAsRootPackage(asPackageField.getText().trim());
		c.setJavaHandlerRootPackage(javaHandlerPackage.getText().trim());
		c.setAsHandlerRootPackage(asHandlerPackage.getText().trim());
	}





}
