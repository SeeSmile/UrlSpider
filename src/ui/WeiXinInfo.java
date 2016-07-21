/*
 * WeiXinInfo.java
 *
 * Created on __DATE__, __TIME__
 */

package ui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.swing.ImageIcon;

import org.json.JSONException;

import data.SoGouWX;
import db.WeiXinDB;

import spider.WbySpider;
import spider.WeiXinSpider;
import utils.SFileUtil;
import utils.SFileUtil.ReadListener;

/**
 *
 * @author  __USER__
 */
public class WeiXinInfo extends javax.swing.JFrame {

	/** Creates new form WeiXinInfo */
	public WeiXinInfo() {
		initComponents();
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	//GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		jPanel1 = new javax.swing.JPanel();
		icon_code = new javax.swing.JLabel();
		et_code = new javax.swing.JTextField();
		btn_start = new javax.swing.JButton();
		btn_code = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		icon_code.setText(" ");

		et_code.setColumns(6);
		et_code.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				et_codeActionPerformed(evt);
			}
		});

		btn_start.setText("\u5f00\u59cb");
		btn_start.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				btn_startMouseClicked(evt);
			}
		});

		btn_code.setText("\u83b7\u53d6");
		btn_code.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				btn_codeMouseClicked(evt);
			}
		});

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(
				jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout
				.setHorizontalGroup(jPanel1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel1Layout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(icon_code)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												51, Short.MAX_VALUE)
										.addComponent(
												et_code,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(btn_code)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(btn_start)
										.addContainerGap()));
		jPanel1Layout
				.setVerticalGroup(jPanel1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel1Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(icon_code)
														.addComponent(btn_start)
														.addComponent(btn_code)
														.addComponent(
																et_code,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
				javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
				javax.swing.GroupLayout.DEFAULT_SIZE,
				javax.swing.GroupLayout.PREFERRED_SIZE));

		pack();
	}// </editor-fold>
	//GEN-END:initComponents

	private void btn_startMouseClicked(java.awt.event.MouseEvent evt) {
		if(WeiXinSpider.login(et_code.getText())) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					String path = SFileUtil.getDataFile("account.txt");
					File file = new File(path);
					SFileUtil.readFileLine(file, new ReadListener() {
						int start = 6891;
						@Override
						public void onRead(int index, String text) {
							
							if(index >= start) {
								try {
									System.out.println("index = " + index + ", text = " + text);
									String sign = WeiXinSpider.getResarchSign(text.trim());
									List<SoGouWX> list = WeiXinSpider.getArticleList(text, sign);
									WeiXinDB.getInstance().upDateArticle(text, list);
								} catch (IOException | JSONException e) {
									if(e instanceof IOException) {
										System.out.println("io exception");
										try {
											Thread.sleep(3 * 1000);
										} catch (InterruptedException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
									}
								}
							}					
						}
						
						@Override
						public void onFinish() {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void onFail() {
							// TODO Auto-generated method stub
							
						}
					});
					
				}
			}).start();
			
		}
	}

	private void btn_codeMouseClicked(java.awt.event.MouseEvent evt) {
		
		try {
			File file = WbySpider.getCode();
			icon_code.setIcon(new ImageIcon(file.getAbsolutePath()));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void et_codeActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new WeiXinInfo().setVisible(true);
			}
		});
	}

	//GEN-BEGIN:variables
	// Variables declaration - do not modify
	private javax.swing.JButton btn_code;
	private javax.swing.JButton btn_start;
	private javax.swing.JTextField et_code;
	private javax.swing.JLabel icon_code;
	private javax.swing.JPanel jPanel1;
	// End of variables declaration//GEN-END:variables

}