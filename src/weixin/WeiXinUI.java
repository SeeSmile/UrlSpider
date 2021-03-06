/*
 * WbyWxInfo.java
 *
 * Created on __DATE__, __TIME__
 */

package weixin;

import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;

import org.json.JSONException;

import exception.AccountException;

import spider.WbySpider;
import utils.SFileUtil;
import utils.SFileUtil.ReadListener;

/**
 *
 * @author  __USER__
 */
public class WeiXinUI extends javax.swing.JFrame {

	/** Creates new form WbyWxInfo */
	public WeiXinUI() {
		initComponents();
	}

	//GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		jPanel1 = new javax.swing.JPanel();
		et_code = new javax.swing.JTextField();
		jLabel1 = new javax.swing.JLabel();
		btn_code = new javax.swing.JButton();
		btn_weixin = new javax.swing.JButton();
		et_page = new javax.swing.JTextField();
		jLabel2 = new javax.swing.JLabel();
		lb_result = new javax.swing.JLabel();
		lb_code = new javax.swing.JLabel();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		et_code.setColumns(6);

		jLabel1.setText("\u9a8c\u8bc1\u7801:");

		btn_code.setText("\u83b7\u53d6\u9a8c\u8bc1\u7801");
		btn_code.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				btn_codeMouseClicked(evt);
			}
		});

		btn_weixin.setText("\u5fae\u4fe1\u5f00\u59cb");
		btn_weixin.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				btn_weixinMouseClicked(evt);
			}
		});

		et_page.setColumns(5);

		jLabel2.setText("\u8d77\u59cb\u9875:");

		lb_result.setText("  ");

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
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																jPanel1Layout
																		.createSequentialGroup()
																		.addGroup(
																				jPanel1Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING,
																								false)
																						.addGroup(
																								jPanel1Layout
																										.createSequentialGroup()
																										.addComponent(
																												jLabel1)
																										.addGap(18,
																												18,
																												18)
																										.addComponent(
																												et_code,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												javax.swing.GroupLayout.DEFAULT_SIZE,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																										.addComponent(
																												jLabel2))
																						.addGroup(
																								jPanel1Layout
																										.createSequentialGroup()
																										.addComponent(
																												btn_code)
																										.addGap(18,
																												18,
																												18)
																										.addComponent(
																												lb_code,
																												javax.swing.GroupLayout.DEFAULT_SIZE,
																												javax.swing.GroupLayout.DEFAULT_SIZE,
																												Short.MAX_VALUE)))
																		.addGroup(
																				jPanel1Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addGroup(
																								jPanel1Layout
																										.createSequentialGroup()
																										.addGap(24,
																												24,
																												24)
																										.addComponent(
																												et_page,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												javax.swing.GroupLayout.DEFAULT_SIZE,
																												javax.swing.GroupLayout.PREFERRED_SIZE))
																						.addGroup(
																								jPanel1Layout
																										.createSequentialGroup()
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												btn_weixin))))
														.addGroup(
																jPanel1Layout
																		.createSequentialGroup()
																		.addGap(86,
																				86,
																				86)
																		.addComponent(
																				lb_result)))
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));
		jPanel1Layout
				.setVerticalGroup(jPanel1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel1Layout
										.createSequentialGroup()
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(jLabel1)
														.addComponent(
																et_code,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(jLabel2)
														.addComponent(
																et_page,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGap(11, 11, 11)
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(btn_code)
														.addComponent(
																lb_code,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																34,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																btn_weixin))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(lb_result)));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addComponent(jPanel1,
								javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
				javax.swing.GroupLayout.DEFAULT_SIZE,
				javax.swing.GroupLayout.PREFERRED_SIZE));

		pack();
	}// </editor-fold>
	//GEN-END:initComponents

	private void btn_weixinMouseClicked(java.awt.event.MouseEvent evt) {

		new Thread(new Runnable() {

			@Override
			public void run() {
				String code = et_code.getText();
				if (WbySpider.login(code)) {
					File file = new File(SFileUtil.getDataFile("data2.txt"));
					SFileUtil.readFileLine(file, new ReadListener() {
						int start_index = 20001;
						int end_index = 30000;
						@Override
						public void onRead(int index, String text) {
							if(index >= start_index && index <= end_index) {
								System.out.println("index = " + index + ", text = " + text);
								try {
									if(text.trim().length() > 0) {
										String url = WeiXinUtil.getSignFWby(text);
//										System.out.println("url:" + url);
										String biz = WeiXinUtil.getBizByWbyUrl(url);
										System.out.println("biz = " + biz);
									}
								} catch (AccountException e) {
									System.out.println("account not exist");
								} catch (IOException e) {
									System.out.println("io exception");
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
			}
		}).start();
	}

	private void btn_codeMouseClicked(java.awt.event.MouseEvent evt) {

		new Thread(new Runnable() {

			@Override
			public void run() {
				File file;
				try {
					file = WbySpider.getCode();
					lb_code.setIcon(new ImageIcon(file.getAbsolutePath()));
				} catch (Exception e) {
					lb_result.setText("我曹，获取验证码报错了");
				}
			}
		}).start();

	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new WeiXinUI().setVisible(true);
			}
		});
	}

	//GEN-BEGIN:variables
	// Variables declaration - do not modify
	private javax.swing.JButton btn_code;
	private javax.swing.JButton btn_weixin;
	private javax.swing.JTextField et_code;
	private javax.swing.JTextField et_page;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JLabel lb_code;
	private javax.swing.JLabel lb_result;
	// End of variables declaration//GEN-END:variables

}