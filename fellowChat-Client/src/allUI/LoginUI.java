package allUI;

import java.awt.Desktop;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayer;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JWindow;

import com.sun.awt.AWTUtilities;

import clientBase.ManClient;

import util.runninglog.RunningLog;
import util.tools.LoginLayerUI;
import util.tools.UIMap;

import action.loginUIAction.LoginAction;
import action.loginUIAction.RegAction;

public class LoginUI {
	private ImageIcon image = new ImageIcon("image/login.jpg");
	private LoginLayerUI layerUI ;
	private JWindow window;
	private Frame frame;
	private JLabel closeLabel;
	private JLabel regButton;
	private JLabel loginButton;
	// 自己添加的
	private JLabel myCenterJLabel;

	private JTextField userName;
	private JPasswordField userPwd;

	private int xx;
	private int yy;
	private boolean isDraging;

	private Robot robot;

	public void showUI() {
		frame = new Frame("基友登录");
		window = new JWindow(frame);
		window.setSize(500, 375);
		window.setLayout(new GridLayout(1, 1));// 网格布局
		/*使窗体实现圆角的效果*/
		AWTUtilities.setWindowShape(window,  
			           new RoundRectangle2D.Double(0.0D, 0.0D, window.getWidth(),  
			               window.getHeight(), 18.0D, 18.0D));  
		JPanel panel = new JPanel() {// 画图背景图

			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(image.getImage(), 0, 0, null);
				this.setOpaque(false);
			}
		};
		
		layerUI = new LoginLayerUI();
		JLayer<JPanel> jlayer = new JLayer<JPanel>(panel, layerUI);
		//注意这个add的是JLayer<JPanel>的一个实例
		//frame.add(jlayer);
		panel.setLayout(null);// 容器选择默认的布局方式
		window.add(jlayer);
		// 关闭按钮区域
		closeLabel = new JLabel(new ImageIcon("image/closeButton.png"));

		/* 自己尝试着添加的内容 */
	//	myCenterJLabel = new JLabel(new ImageIcon("image/enter.jpg"));
	//	myCenterJLabel.setBounds(380, 13, 40, 40);
		//
		closeLabel.setBounds(440, 8, 52, 38);
		panel.add(closeLabel);
		//
		//panel.add(myCenterJLabel);
		/* 关闭按钮区域的监听器 */
		closeLabel.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				closeLabel.setIcon(new ImageIcon("image/closeButton_fake.png"));
			}

			public void mouseExited(MouseEvent e) {
				closeLabel.setIcon(new ImageIcon("image/closeButton.png"));
			}

			public void mouseReleased(MouseEvent e) {
				System.exit(0);
			}
		});
		/* 进入空间的按钮监听器 */
		/*myCenterJLabel.addMouseListener(new MouseAdapter() {
			
        public void mouseReleased(MouseEvent e) {
        	try {
				Desktop.getDesktop().browse(new java.net.URI("http://www.zhiwenweb.cn"));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			}
			public void mouseExited(MouseEvent e) {
				myCenterJLabel.setIcon(new ImageIcon("image/enter.jpg"));
			}

			public void mouseEntered(MouseEvent e) {
				myCenterJLabel.setIcon(new ImageIcon("image/enter_enter.jpg"));
			}

		});*/

		// 注册按钮区域
		regButton = new JLabel(new ImageIcon("image/regButton.jpg"));
		regButton.setBounds(283, 295, 94, 43);
		panel.add(regButton);
		/*这是按钮效果的监听*/
		regButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				regButton.setIcon(new ImageIcon("image/regButton_fake.jpg"));
			}

			public void mouseExited(MouseEvent e) {
				regButton.setIcon(new ImageIcon("image/regButton.jpg"));
			}
		});
		//这是按钮动作的jianting
		regButton.addMouseListener(new RegAction());
		// 登录按钮区域
		loginButton = new JLabel(new ImageIcon("image/loginButton.jpg"));
		loginButton.setBounds(374, 295, 91, 42);
		panel.add(loginButton);
		loginButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				loginButton
						.setIcon(new ImageIcon("image/loginButton_fake.jpg"));
			}

			public void mouseExited(MouseEvent e) {
				loginButton.setIcon(new ImageIcon("image/loginButton.jpg"));
			}

			public void mouseReleased(MouseEvent e) {

			}
		});
		// 用户名输入区域
		userName = new JTextField();
		userName.setBounds(300, 168, 162, 20);
		userName.setOpaque(false);
		userName.setBorder(BorderFactory.createEmptyBorder());
		panel.add(userName);
		userName.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10) {
					try {
						robot = new Robot();
						Point p = loginButton.getLocationOnScreen();
						robot.mouseMove(p.x + 20, p.y + 15);
						robot.mouseRelease(MouseEvent.BUTTON1_MASK);
					} catch (Exception ef) {
						ef.printStackTrace();
					}
				} else {
					if (userName.getText().length() > 30) {
						JOptionPane.showMessageDialog(null,
								"您输入的用户名过长，请查证后再输入！");
						userName.setText(userName.getText().substring(0,
								userName.getText().length() - 2));
					}
				}
			}
		});
		// 密码输入区域
		userPwd = new JPasswordField();
		userPwd.setBounds(300, 234, 162, 20);
		userPwd.setOpaque(false);
		userPwd.setBorder(BorderFactory.createEmptyBorder());
		userPwd.setEchoChar('●');// 设置密码栏的回显字符
		panel.add(userPwd);
		userPwd.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10) {
					try {
						robot = new Robot();
						Point p = loginButton.getLocationOnScreen();
						robot.mouseMove(p.x + 20, p.y + 15);
						robot.mouseRelease(MouseEvent.BUTTON1_MASK);
					} catch (Exception ef) {
						ef.printStackTrace();
					}
				} else {
					if (userPwd.getPassword().length > 30) {
						JOptionPane.showMessageDialog(null, "您输入的密码过长，请重新输入！");
						userPwd.setText("");
					}
				}
			}
		});

		window.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				frame.requestFocus();
				window.requestFocus();
				isDraging = true;
				xx = e.getX();
				yy = e.getY();
			}

			public void mouseReleased(MouseEvent e) {
				isDraging = false;
			}
		});
		window.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				if (isDraging) {
					int left = window.getLocation().x;
					int top = window.getLocation().y;
					window.setLocation(left + e.getX() - xx, top + e.getY()
							- yy);
				}
			}
		});

		frame.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10) {
					try {
						robot = new Robot();
						Point p = loginButton.getLocationOnScreen();
						robot.mouseMove(p.x + 20, p.y + 15);
						robot.mouseRelease(MouseEvent.BUTTON1_MASK);
					} catch (Exception ef) {
						ef.printStackTrace();
					}
				}
			}
		});

		// panel.addMouseListener(new MouseAdapter(){
		// public void mouseReleased(MouseEvent e){
		// System.out.println(e.getX()+"---"+e.getY());
		// }
		// });
		frame.setUndecorated(true);// 去掉窗口装饰
		frame.setLocationRelativeTo(null);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		frame.setVisible(true);
		userName.requestFocus();
		RunningLog.record("界面启动成功！！！");
	}

	public void closeWindow() {
		window.dispose();
		frame.dispose();
	}

	public void setLoginListener(LoginAction login) {
		loginButton.addMouseListener(login);
	}

	public void setRegListener(RegAction reg) {
		regButton.addMouseListener(reg);
	}

	public void setFocusInUserName() {
		userName.requestFocus();
	}

	public JTextField getUserName() {
		return userName;
	}

	public JPasswordField getUserPwd() {
		return userPwd;
	}

	public static void main(String args[]) {
		LoginUI login = new LoginUI();
		login.showUI();
	}
}
