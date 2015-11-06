package allUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JWindow;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import util.pojo.UserInfo;
import util.tools.DataBaseControl;
import util.tools.GetIntValue;
import util.tools.GetUserFlag;
import util.tools.GetUserInfo;
import util.tools.Loginner;
import util.tools.ToolsBack;
import util.tools.UIMap;
import util.tools.UpdateUserFlag;
import util.tools.UpdateUserInfo;

import com.ibm.media.bean.multiplayer.ImageLabel;
import com.jtattoo.plaf.mcwin.McWinLookAndFeel;
import com.sun.awt.AWTUtilities;

public class ClientSetUI {
    //���ý����������
	private JLabel setlaJLabel;
	//������
	private JPanel leftPanel;//���������������
	private ImageIcon leftPanelImageIcon=new ImageIcon("image/leftPanel.jpg");
	private JLabel touxianJLabel;
	private JComboBox touxianlist;//ͷ��ѡ�������б��
	private ImageIcon touimageIcon;//ͷ��ͼƬ
	private JLabel imageJLabel;
	
	
	//�Ҳ����
	private JPanel rightPanel;
	private JLabel bgJLabel;
	private JComboBox bglist;
	private ImageIcon bgIcon ;
	private JLabel imagebgJLabel;
	//*********************
	//��Ӷ��޸Ĳ����İ�ť
	private ImageIcon leftupdateIcon=new ImageIcon("image/update.jpg");
	private ImageIcon leftupdatefakIcon=new ImageIcon("image/updatefak.jpg");
	private JLabel leftupdatelJLabel;
	
	private ImageIcon rightupdateIcon=new ImageIcon("image/update.jpg");
	private ImageIcon rightupdatefakIcon=new ImageIcon("image/updatefak.jpg");
	private JLabel rightupdatelJLabel;
	
	
	private JWindow window;
	//private JPanel panel;
	private JLabel closeButton;
	private JLabel okButton;
    
	
	/*�հ״����������*/
	private  ImageIcon paIcon=new ImageIcon("image/panel.jpg");
	private ImageIcon image = new ImageIcon("image/aboutUI.png");
	private ImageIcon close = new ImageIcon("image/closeButton.png");
	private ImageIcon close_fake = new ImageIcon("image/closeButton_fake.png");
	private ImageIcon liuyan = new ImageIcon("image/liuyan.jpg");
	private ImageIcon iuyan_fake = new ImageIcon("image/liuyanfakjpg.jpg");
	private ImageIcon ok = new ImageIcon("image/okButton.png");
	private ImageIcon ok_fake = new ImageIcon("image/okButton_fake.png");
	
	
	private JPanel panel;
	private int xx;
	private int yy;
	private boolean isDraging;
	private ImageIcon background= new ImageIcon("image/background1.jpg");
	public ImageIcon getImageIcon(String usernameString)
	{
		ImageIcon imageIcon=null;
		
		/*�����ݿ��еõ���Ϣ*/
		 Connection connection=new DataBaseControl().buildConn("root", "root");
	        ResultSet rt=null;
	        String userInfoString;
	        try {
				PreparedStatement ps=connection.prepareStatement("select userimage,usersex from userinfo where userName="+"'"+usernameString+"'");
				rt=ps.executeQuery();
				while(rt.next())
				{
				 userInfoString=rt.getString(1);
				  String sex=rt.getString(2);
				  if(sex.equals("��"))
				  {
				    imageIcon= new ImageIcon("image/male/"+userInfoString+".jpg");
				  }else {
					  imageIcon= new ImageIcon("image/female/"+userInfoString+".jpg");
				}
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		return imageIcon;
	}
	public void showUI(){
		final JFrame frame=new JFrame();
		
		/*panel = new JPanel(){
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				g.drawImage(paIcon.getImage(), 0, 0, null);
				this.setOpaque(false);
			}
		};*/
		
		panel  = new JPanel(){
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				g.drawImage(background.getImage(), 0, 0, null);
				this.setOpaque(false);
			}
		};
		panel.setLayout(null);//���ɲ��ֵķ�ʽ
		panel.setSize(350,400);
		


		setlaJLabel = new JLabel("�����ԡﻯ�ﶨ����");
		setlaJLabel.setBounds(90, 30, 140, 60);
        setlaJLabel.setFont(new java.awt.Font("���Ĳ���", 0, 15));
        setlaJLabel.setForeground(new Color(180, 93, 237));
		panel.add(setlaJLabel);
		//����ʼ��:
		leftPanel  = new JPanel(){
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				g.drawImage(paIcon.getImage(), 0, 0, null);
				this.setOpaque(false);
			}
		};
		leftPanel.setLayout(null);//���ɲ��ַ�ʽ
		leftPanel.setBounds(20,100,140,255);
		touxianJLabel = new JLabel("ͷ��");
		touxianJLabel.setSize(40, 40);
		touxianJLabel.setFont(new Font("���Ŀ���", 0, 18));
		touxianJLabel.setLocation((leftPanel.getWidth()-touxianJLabel.getWidth())/2, 5);
		touxianlist =new JComboBox();
		touxianlist.setSize(60, 23);
		touxianJLabel.setForeground(new Color(106, 18, 198));
		touxianlist.setLocation((leftPanel.getWidth()-touxianlist.getWidth())/2, 50);
		/*��ʼͷ����Ҫ�������ݿ�ģ�������Ҫ�ĵ��Ա�*/
		GetUserInfo getUserInfo=new GetUserInfo(Loginner.loginner);
		UserInfo userInfo=(UserInfo)getUserInfo.getUserInfo();
		final String sex=userInfo.getUserSex();
		if(sex.equals("��"))
		{
		touimageIcon = new ImageIcon("image\\male\\"+userInfo.getUserImage()+".jpg");
		}else {
			touimageIcon = new ImageIcon("image\\female\\"+userInfo.getUserImage()+".jpg");
		}
		imageJLabel = new JLabel(touimageIcon);
		imageJLabel.setSize(90,90);
		imageJLabel.setLocation((leftPanel.getWidth()-imageJLabel.getWidth())/2,110);
		for(int i=0;i<5;i++){
			touxianlist.addItem(i+1);
		}
		touxianlist.setSelectedIndex(Integer.parseInt(userInfo.getUserImage())-1);
		touxianlist.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String path = null;
				boolean flag = true;
				if(sex.equals("��")){
					path = "image/male/"+touxianlist.getSelectedItem()+".jpg";
				}else{
					path = "image/female/"+touxianlist.getSelectedItem()+".jpg";
				}
				touimageIcon = new ImageIcon(path);
				imageJLabel.setIcon(touimageIcon);
			}
		});
		leftupdatelJLabel = new JLabel(leftupdateIcon);
		leftupdatelJLabel.setBounds(87,210,50,20);
		leftupdatelJLabel.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent e){
				leftupdatelJLabel.setIcon(leftupdatefakIcon);
			}
			public void mouseExited(MouseEvent e){
				leftupdatelJLabel.setIcon(leftupdateIcon);
			}
			public void mousePressed(MouseEvent e){
				
			UpdateUserInfo updateUserInfo=	new UpdateUserInfo(Loginner.loginner,  touxianlist.getSelectedItem().toString());
				if(updateUserInfo.updateUserImage()==1)
				{
					 try {
							
							UIManager
									.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
						} catch (Exception e1) {
						}
						
					JOptionPane.showMessageDialog(null, "���óɹ�!\n���䡿�пɲ鿴");
					JFrame.setDefaultLookAndFeelDecorated(true);

					 try {

					 UIManager.setLookAndFeel(new McWinLookAndFeel());

					 } catch (UnsupportedLookAndFeelException e1) {

					 // TODO Auto-generated catch block

					 e1.printStackTrace();

					 }
				}else {
					try {
						
						UIManager
								.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
						
					} catch (Exception e1) {
					}
					JOptionPane.showMessageDialog(null, "����ʧ��");
					JFrame.setDefaultLookAndFeelDecorated(true);

					 try {

					 UIManager.setLookAndFeel(new McWinLookAndFeel());

					 } catch (UnsupportedLookAndFeelException e1) {

					 // TODO Auto-generated catch block

					 e1.printStackTrace();

					 }
				}
			}
		});
		leftPanel.add(leftupdatelJLabel);
		
		leftPanel.add(imageJLabel);
		leftPanel.add(touxianJLabel);
		leftPanel.add(touxianlist);
		panel.add(leftPanel);
		
		//����Ѿ�������
	    //panel.setLayout(null);
		
		/*����Ҳ�Ĳ���*/
		
		rightPanel = new JPanel(){
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.drawImage(paIcon.getImage(), 0, 0, null);
			this.setOpaque(false);
		}
	};
		
		rightPanel.setLayout(null);//���ɲ��ַ�ʽ
		rightPanel.setBounds(180,100,130,255);
		bgJLabel = new JLabel("����");
		bgJLabel.setSize(40, 40);
		bgJLabel.setFont(new Font("���Ŀ���", 0, 18));
		bgJLabel.setForeground(new Color(106, 18, 198));
		bgJLabel.setLocation((rightPanel.getWidth()-bgJLabel.getWidth())/2, 5);
		rightPanel.add(bgJLabel);
		
		bgIcon = new ImageIcon("background/bg/MainUIback1.jpg");
		imagebgJLabel = new JLabel(bgIcon);
		imagebgJLabel.setSize(90,90);
		imagebgJLabel.setLocation((rightPanel.getWidth()-imagebgJLabel.getWidth())/2,110);
		rightPanel.add(imagebgJLabel);
		bglist =new JComboBox();
	  
		bglist.setSize(60, 23);
		bglist.setToolTipText("��!�´���������ЧŶo(�Rv�Q)o~~");
		bglist.setLocation((rightPanel.getWidth()-bglist.getWidth())/2, 50);
		for(int i=0;i<5;i++){
			bglist.addItem(i+1);
		}
		System.out.print(GetIntValue.getIntValue(ToolsBack.getConfig().toString()));
		 bglist.setSelectedIndex(GetIntValue.getIntValue(ToolsBack.getConfig().toString()));
		bglist.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String path = null;
				
				
					path = "background/bg/MainUIback"+bglist.getSelectedItem()+".jpg";
				
				bgIcon = new ImageIcon(path);
				imagebgJLabel.setIcon(bgIcon);
			}
		});
		rightupdatelJLabel = new JLabel(rightupdateIcon);
		rightupdatelJLabel.setBounds(23,210,50,20);
		rightupdatelJLabel.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent e){
				rightupdatelJLabel.setIcon(rightupdatefakIcon);
			}
			public void mouseExited(MouseEvent e){
				rightupdatelJLabel.setIcon(rightupdateIcon);
			}
			public void mousePressed(MouseEvent e){
				
				//�����ʱ�����¸�������:���ò�Ʋ���ʵ�ְ�
				/*��һ�����µ�¼��ʵ�ְ�*/
				String text="MainUIback"+bglist.getSelectedItem().toString();//����ò�������Ⱑ
				ToolsBack.setConfig(text);
				frame.dispose();
			
			}
		});
		rightPanel.add(rightupdatelJLabel);
		rightPanel.add(imagebgJLabel);
		rightPanel.add(bgJLabel);
		rightPanel.add(bglist);
		panel.add(rightPanel);
	
		
		
	

	
	//	window=new JWindow();
		frame.setUndecorated(true);
		frame.setSize(350,400);
		frame.setLayout(null);
		frame.setEnabled(true);
        frame.add(panel);
		
		AWTUtilities.setWindowShape(frame,  
		           new RoundRectangle2D.Double(0.0D, 0.0D, frame.getWidth(),  
		        		   frame.getHeight(), 18.0D, 18.0D));  
		//panel = new JPanel();
	   //panel.setLayout(null);
		
		//updateButton.setSize(50, 20);
		//updateButton.setLocation(92, 150);
	
	
		//this.add(updateButton);
		
		
		

		
		
		//window.add(panel);
		
		closeButton = new JLabel(close);
		closeButton.setBounds(286, 10, 48, 33);
		panel.add(closeButton);
	
	//	panel.add(bgJLabel);
	//	panel.add(flagArea);
		closeButton.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent e){
				closeButton.setIcon(close_fake);
			}
			public void mouseExited(MouseEvent e){
				closeButton.setIcon(close);
			}
			public void mouseReleased(MouseEvent e){
				closeButton.setBounds(286, 10, 48, 33);
				frame.dispose();
				if(UIMap.temporaryStorage.get("aboutUI")!=null){
					UIMap.temporaryStorage.remove("aboutUI");
				}
			}
			public void mousePressed(MouseEvent e){
				closeButton.setBounds(286, 10, 48, 33);
			}
		});
		
	
		
		okButton = new JLabel(ok);
		okButton.setBounds(245, 352, 87, 38);
		panel.add(okButton);
		okButton.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				okButton.setBounds(245, 352, 87, 38);
				frame.dispose();
			}
			public void mouseReleased(MouseEvent e){
				okButton.setBounds(245, 352, 87, 38);
			     
				if(UIMap.temporaryStorage.get("aboutUI")!=null){
					UIMap.temporaryStorage.remove("aboutUI");
				}
			}
			public void mouseEntered(MouseEvent e){
				okButton.setIcon(ok_fake);
			}
			public void mouseExited(MouseEvent e){
				okButton.setIcon(ok);
			}
		});
		
		frame.addMouseListener(new MouseAdapter() {
			   public void mousePressed(MouseEvent e) {
			    isDraging = true;
			    xx = e.getX();
			    yy = e.getY();
			   }

			   public void mouseReleased(MouseEvent e) {
			    isDraging = false;
			   }
			  });
		frame.addMouseMotionListener(new MouseMotionAdapter() {
			   public void mouseDragged(MouseEvent e) {
			    if (isDraging) { 
			     int left = frame.getLocation().x;
			     int top = frame.getLocation().y;
			     frame.setLocation(left + e.getX() - xx, top + e.getY() - yy);
			    } 
			   }
			  });  
		
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
    
	public static void main(String[] args) {
		try {
			UIManager
			.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		new ClientSetUI().showUI();
	}
}
