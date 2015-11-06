package allUI;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayer;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JWindow;

import com.sun.awt.AWTUtilities;

import util.tools.Address;
import util.tools.GetUserInfo;
import util.tools.LoginLayerUI;
import util.tools.UIMap;

import action.regUIAction.CancelAction;
import action.regUIAction.SubmitAction;

public class RegUI {

	//这里改变一种策略，使用一个地址录入按钮
	 /*只有按钮被按下之后才会显示地址信息的录入的控件*/
	private String cityString ;
	private String areaString ; 
	private int id;
	
	public boolean isFlags() {
		return flags;
	}

	public void setFlags(boolean flags) {
		this.flags = flags;
	}

	public String getCityString() {
		return cityTextField.getText();
	}

	public void setCityString(String cityString) {
		this.cityString = cityString;
	}

	public String getAreaString() {
		return areaTextField.getText();
	}

	public void setAreaString(String areaString) {
		this.areaString = areaString;
	}

	public int getId() {
		return GetUserInfo.getMaxId();
	}

	public void setId(int id) {
		this.id = id;
	}

	private boolean flags=false;
	private JLabel addressButton = null;
	private ImageIcon addressIcon = new ImageIcon("image/address.png");
	private ImageIcon addressIcon2 = new ImageIcon("image/address_fake.png");
	
	/*在注册界面添加地址注册的信息*/
	private JPanel addressPanel = null;
	private JLabel addressInfoLabel = null;//地址的标签
	private JTextField cityTextField = null;//市、省级的输入框
	private JLabel cityInfo = null;//市、省级的说明框
	private JTextField areaTextField = null;//区，镇级的输入框
	private JLabel areaInfo = null;//区、镇级的说明框
	
	private ImageIcon addressBackground = new ImageIcon("image/addressBackground.png");
	
	
	
	/*聚光灯效果*/
	private LoginLayerUI layerUI ; 
	
	private JFrame frame;
	private JWindow window;
	private JPanel panel;
	private JTextField name;
	private JPasswordField pwd;
	private JPasswordField repwd;
	private JTextField age;
	private JRadioButton male;
	private JRadioButton female;
	private JComboBox imageBox;
	private JLabel submitButton;
	private JLabel cancelButton;
	private JLabel headImage;
	private JLabel nameTip;
	private JLabel pwdTip;
	private JLabel repwdTip;
	private JLabel ageTip;
	private JLabel waitingLabel;
	private JWindow waitingWindow;
	
	private ImageIcon background = new ImageIcon("image/regUI.jpg");
	private ImageIcon submit = new ImageIcon("image/submit.png");
	private ImageIcon submit_fake = new ImageIcon("image/submit_fake.png");
	private ImageIcon cancel = new ImageIcon("image/cancel.png");
	private ImageIcon cancel_fake = new ImageIcon("image/cancel_fake.png");
	private ImageIcon waiting = new ImageIcon("image/regUIwaiting.jpg");
	private ImageIcon image;
	
	private boolean flag = true;
	
	private boolean nameok = false;
	private boolean pwdok = false;
	private boolean repwdok = false;
	private boolean ageok = false;
	
	private int xx;
	private int yy;
	private boolean isDraging;
	
	public boolean isReady(){
		if(nameok&&pwdok&&repwdok&&ageok){
			return true;
		}else{
			return false;
		}
	}
	
	public String getUserName(){
		return name.getText();
	}
	
	public String getUserPwd(){
		return new String(pwd.getPassword());
	}
	
	public String getUserSex(){
		if(flag){
			return "男";
		}else{
			return "女";
		}
	}
	
	public String getUserAge(){
		return age.getText();
	}
	
	public String getUserImage(){
		return imageBox.getSelectedItem().toString();
	}
	
	public void closeUI(){
		window.dispose();
		waitingWindow.dispose();
		frame.dispose();
		UIMap.removeObj("reg");
	}
	
	public void waiting(){
		waitingWindow = new JWindow();
		waitingWindow.setSize(500, 400);
		waitingWindow.setLayout(new GridLayout(1,1));
		waitingLabel = new JLabel(waiting);
		waitingWindow.add(waitingLabel);
		waitingWindow.setVisible(true);
		waitingWindow.setLocation(window.getLocationOnScreen());
	}
	
	public void resubmitOpration(){
		waitingWindow.dispose();
	}
	
	public void showUI(){
		frame = new JFrame("乡友注册");
		window = new JWindow(frame);
		window.setSize(500,400);
		window.setLayout(new GridLayout(1,1));
		/*使窗体实现圆角的效果*/
		AWTUtilities.setWindowShape(window,  
			           new RoundRectangle2D.Double(0.0D, 0.0D, window.getWidth(),  
			               window.getHeight(), 26.0D, 26.0D));  
		//铺底板
		panel = new JPanel(){
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				g.drawImage(background.getImage(), 0, 0, null);
				this.setOpaque(false);
			}
		};
		
		/*这里添加地址的一些信息的控件*/
		addressPanel = new JPanel(){
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				g.drawImage(addressBackground.getImage(), 0, 0, null);
				this.setOpaque(false);
			}
		};
		
		addressPanel.setLayout(null);
		addressPanel.setBounds(20, 25,460, 50);
		addressInfoLabel = new JLabel("家乡地址");
		
		cityTextField = new JTextField();
		cityTextField.setBounds(124, 8, 50, 16);
		addressPanel.add(cityTextField);
		areaTextField = new JTextField();
		areaTextField.setBounds(261, 8,43, 16);
		addressPanel.add(areaTextField);
		//panel.add(addressPanel);
		
		
		
		layerUI = new LoginLayerUI();
		JLayer<JPanel> jlayer = new JLayer<JPanel>(panel, layerUI);
		//注意这个add的是JLayer<JPanel>的一个实例
		panel.setLayout(null);
		window.add(jlayer);
//		panel.addMouseListener(new MouseAdapter(){
//			boolean flag = true;
//			int x = 0;
//			int y = 0;
//			public void mouseReleased(MouseEvent e){
//				if(flag){
//					System.out.println(e.getX()+"---"+e.getY());
//					x = e.getX();
//					y = e.getY();
//					flag = false;
//				}else{
//					System.out.println((e.getX()-x)+"---"+(e.getY()-y));
//					flag = true;
//				}
//			}
//		});
		
		//用户名输入栏
		name = new JTextField();
		name.setOpaque(false);
		name.setBorder(BorderFactory.createEmptyBorder());
		name.setBounds(144, 70, 190, 16);
		panel.add(name);
		nameTip = new JLabel("输入长度为3到16位");
		nameTip.setForeground(Color.red);
		nameTip.setBounds(338, 64, 141, 30);
		panel.add(nameTip);
		name.addFocusListener(new FocusListener(){

			public void focusGained(FocusEvent e) {
				if(name.getText()==null){
					nameTip.setForeground(Color.red);
					nameTip.setText("输入长度为3到16位");
				}
			}
			public void focusLost(FocusEvent e) {
				String userName = name.getText();
				if(userName.length()<3||userName.length()>16){
					nameTip.setForeground(Color.red);
					nameTip.setText("长度不合法！");
					nameok = false;
				}else if(userName.contains(" ")){
					nameTip.setForeground(Color.red);
					nameTip.setText("用户名中不允许有空格！");
					nameok = false;
				}else{
					nameTip.setForeground(Color.green);
					nameTip.setText("格式正确，可以提交");
					nameok = true;
				}
			}
		});
		
		//密码输入栏
		pwd = new JPasswordField();
		pwd.setEchoChar('●');
		pwd.setOpaque(false);
		pwd.setBorder(BorderFactory.createEmptyBorder());
		pwd.setBounds(144, 112, 187, 16);
		panel.add(pwd);
		pwdTip = new JLabel();
		pwdTip.setBounds(338,102, 141, 30);
		panel.add(pwdTip);
		pwd.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent e){
				pwdTip.setForeground(Color.red);
				pwdTip.setText("输入长度为6到16位");
			}
			public void focusLost(FocusEvent e){
				String userPwd = new String(pwd.getPassword());
				if(userPwd.length()<6||userPwd.length()>16){
					pwdTip.setText("长度不合法！");
					pwdok = false;
				}else{
					pwdTip.setForeground(Color.green);
					pwdTip.setText("格式正确，可以提交");
					pwdok = true;
				}
			}
		});
		
		//确认密码输入栏
		repwd = new JPasswordField();
		repwd.setEchoChar('●');
		repwd.setOpaque(false);
		repwd.setBorder(BorderFactory.createEmptyBorder());
		repwd.setBounds(144, 158, 187, 16);
		panel.add(repwd);
		repwdTip = new JLabel();
		repwdTip.setBounds(338, 151, 141, 30);
		panel.add(repwdTip);
		repwd.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent e){
				repwdTip.setForeground(Color.red);
				repwdTip.setText("请再输入一次密码！");
			}
			public void focusLost(FocusEvent e){
				String rePwd = new String(repwd.getPassword());
				String userPwd = new String(pwd.getPassword());
				if(rePwd.equals(userPwd)){
					repwdTip.setForeground(Color.green);
					repwdTip.setText("核对正确");
					repwdok = true;
				}else{
					repwdTip.setText("两次输入的密码不一致！");
					repwdok = false;
				}
			}
		});
		
		//性别选择栏
		JPanel buttonPane = new JPanel(new FlowLayout());
		ButtonGroup group = new ButtonGroup();
		male = new JRadioButton("男");
		male.setSelected(true);
		male.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				flag = true;
				String path = "image/male/"+imageBox.getSelectedItem()+".jpg";
				image = new ImageIcon(path);
				headImage.setIcon(image);
			}
		});
		male.setOpaque(false);
		group.add(male);
		female = new JRadioButton("女");
		female.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				flag = false;
				String path = "image/female/"+imageBox.getSelectedItem()+".jpg";
				image = new ImageIcon(path);
				headImage.setIcon(image);
			}
		});
		female.setOpaque(false);
		group.add(female);
		buttonPane.add(male);
		buttonPane.add(female);
		buttonPane.setOpaque(false);
		buttonPane.setBounds(127, 194, 112, 30);
		panel.add(buttonPane);
		
		//年龄输入栏
		age = new JTextField();
		age.setOpaque(false);
		age.setBorder(BorderFactory.createEmptyBorder());
		age.setBounds(144, 236, 55, 16);
		panel.add(age);
		ageTip = new JLabel();
		ageTip.setBounds(206, 233, 274, 30);
		panel.add(ageTip);
		age.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent e){
				ageTip.setForeground(Color.red);
				ageTip.setText("请输入您的年龄！");
			}
			public void focusLost(FocusEvent e){
				String userAge = age.getText();
				try{
					int i = Integer.parseInt(userAge);
					if(i<0||i>150){
						ageTip.setText("您的年龄过于神奇!");
						ageok = false;
					}else{
						ageTip.setForeground(Color.green);
						ageTip.setText("格式正确，可以提交");
						ageok = true;
					}
				}catch(Exception ef){
					ageTip.setText("请输入数字！");
					ageok = false;
				}
			}
		});
		addressButton = new JLabel(addressIcon);
		addressButton.setBounds(308, 180, 60, 60);
	
		addressButton.addMouseListener(new MouseAdapter() {
		   @Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			addressButton.setIcon(addressIcon2);
		}
		   
		   @Override
			public void mouseExited(MouseEvent e) {
				addressButton.setIcon(addressIcon);
			}
		   
		   @Override
			public void mouseClicked(MouseEvent e) {
			    flags=true;
				panel.add(addressPanel);
				panel.updateUI();
			}
		});
		panel.add(addressButton);
		//头像选择栏
		image = new ImageIcon("image/male/1.jpg");
		headImage = new JLabel(image);
		headImage.setBounds(180, 287, 80, 80);
		panel.add(headImage);
		imageBox = new JComboBox();
		for(int i=0;i<5;i++){
			imageBox.addItem(i+1);
		}
		imageBox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String path = null;
				if(flag){
					path = "image/male/"+imageBox.getSelectedItem()+".jpg";
				}else{
					path = "image/female/"+imageBox.getSelectedItem()+".jpg";
				}
				image = new ImageIcon(path);
				headImage.setIcon(image);
			}
		});
		imageBox.setBounds(127, 290, 38, 25);
		panel.add(imageBox);
		
		
		
		//提交按钮
		submitButton = new JLabel(submit);
		submitButton.setBounds(300, 324, 87, 38);
		panel.add(submitButton);
		submitButton.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent e){
				submitButton.setIcon(submit_fake);
				submitButton.setBounds(300, 326, 87, 38);
			}
			public void mouseExited(MouseEvent e){
				submitButton.setIcon(submit);
				submitButton.setBounds(300,324,87,38);
			}
			public void mousePressed(MouseEvent e){
				submitButton.requestFocus();
			}
		
		});
		submitButton.addMouseListener(new SubmitAction(this));
		
		//取消按钮
		cancelButton = new JLabel(cancel);
		cancelButton.setBounds(390, 324, 87, 38);
		panel.add(cancelButton);
		cancelButton.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent e){
				cancelButton.setIcon(cancel_fake);
				cancelButton.setBounds(390, 326, 87, 38);
			}
			public void mouseExited(MouseEvent e){
				cancelButton.setIcon(cancel);
				cancelButton.setBounds(390, 324, 87, 38);
			}
			
			
		});
		cancelButton.addMouseListener(new CancelAction(this));
		
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
                    window.setLocation(left + e.getX() - xx, top + e.getY() - yy);      
                }      
            }      
        });
		
		frame.setUndecorated(true);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		frame.setVisible(true);
		
		name.requestFocus();
	}
	
//	public static void main(String args[]){
//		RegUI reg = new RegUI();
//		reg.showUI();
//	}
	
	
	
}
