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
	// �Լ���ӵ�
	private JLabel myCenterJLabel;

	private JTextField userName;
	private JPasswordField userPwd;

	private int xx;
	private int yy;
	private boolean isDraging;

	private Robot robot;

	public void showUI() {
		frame = new Frame("���ѵ�¼");
		window = new JWindow(frame);
		window.setSize(500, 375);
		window.setLayout(new GridLayout(1, 1));// ���񲼾�
		/*ʹ����ʵ��Բ�ǵ�Ч��*/
		AWTUtilities.setWindowShape(window,  
			           new RoundRectangle2D.Double(0.0D, 0.0D, window.getWidth(),  
			               window.getHeight(), 18.0D, 18.0D));  
		JPanel panel = new JPanel() {// ��ͼ����ͼ

			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(image.getImage(), 0, 0, null);
				this.setOpaque(false);
			}
		};
		
		layerUI = new LoginLayerUI();
		JLayer<JPanel> jlayer = new JLayer<JPanel>(panel, layerUI);
		//ע�����add����JLayer<JPanel>��һ��ʵ��
		//frame.add(jlayer);
		panel.setLayout(null);// ����ѡ��Ĭ�ϵĲ��ַ�ʽ
		window.add(jlayer);
		// �رհ�ť����
		closeLabel = new JLabel(new ImageIcon("image/closeButton.png"));

		/* �Լ���������ӵ����� */
	//	myCenterJLabel = new JLabel(new ImageIcon("image/enter.jpg"));
	//	myCenterJLabel.setBounds(380, 13, 40, 40);
		//
		closeLabel.setBounds(440, 8, 52, 38);
		panel.add(closeLabel);
		//
		//panel.add(myCenterJLabel);
		/* �رհ�ť����ļ����� */
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
		/* ����ռ�İ�ť������ */
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

		// ע�ᰴť����
		regButton = new JLabel(new ImageIcon("image/regButton.jpg"));
		regButton.setBounds(283, 295, 94, 43);
		panel.add(regButton);
		/*���ǰ�ťЧ���ļ���*/
		regButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				regButton.setIcon(new ImageIcon("image/regButton_fake.jpg"));
			}

			public void mouseExited(MouseEvent e) {
				regButton.setIcon(new ImageIcon("image/regButton.jpg"));
			}
		});
		//���ǰ�ť������jianting
		regButton.addMouseListener(new RegAction());
		// ��¼��ť����
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
		// �û�����������
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
								"��������û������������֤�������룡");
						userName.setText(userName.getText().substring(0,
								userName.getText().length() - 2));
					}
				}
			}
		});
		// ������������
		userPwd = new JPasswordField();
		userPwd.setBounds(300, 234, 162, 20);
		userPwd.setOpaque(false);
		userPwd.setBorder(BorderFactory.createEmptyBorder());
		userPwd.setEchoChar('��');// �����������Ļ����ַ�
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
						JOptionPane.showMessageDialog(null, "�������������������������룡");
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
		frame.setUndecorated(true);// ȥ������װ��
		frame.setLocationRelativeTo(null);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		frame.setVisible(true);
		userName.requestFocus();
		RunningLog.record("���������ɹ�������");
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
