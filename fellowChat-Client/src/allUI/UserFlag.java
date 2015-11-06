package allUI;
import   javax.swing.*;

import   java.awt.*;
import   java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import   javax.swing.event.*;

import com.jtattoo.plaf.mcwin.McWinLookAndFeel;
import com.sun.awt.AWTUtilities;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.org.apache.xml.internal.serializer.ElemDesc;

import util.pojo.CommentInfo;
import util.tools.DataBaseControl;
import util.tools.AddComment;
import util.tools.GetComment;
import util.tools.GetUserFlag;
import util.tools.Loginner;
import util.tools.UIMap;

public   class   UserFlag   extends   JFrame   {
    
	private JPanel liuyanlistpaJPanel;//存放留言信息的jpanel
	private ImageIcon areaIcon = new ImageIcon("image/desk.jpg");
	private TextField fileField;
    private JLabel touLabel;
	private ImageIcon touxiangIcon;
	private String usernameString;//被留言的对象
	private String commenterString;//留言的对象
	private JWindow window;
	//private JPanel panel;
	private JLabel closeButton;
	private JLabel okButton;
	private JLabel liuyanlLabel;
	
	private ImageIcon image = new ImageIcon("image/aboutUI.png");
	private ImageIcon close = new ImageIcon("image/closeButton.png");
	private ImageIcon close_fake = new ImageIcon("image/closeButton_fake.png");
	private ImageIcon liuyan = new ImageIcon("image/liuyan.jpg");
	private ImageIcon iuyan_fake = new ImageIcon("image/liuyanfakjpg.jpg");
	private ImageIcon ok = new ImageIcon("image/okButton.png");
	private ImageIcon ok_fake = new ImageIcon("image/okButton_fake.png");
	private ImageIcon background= new ImageIcon("image/background.jpg");
	
	//添加一些详细信息
	private JLabel flagJLabel=null;//人生格言的标签
	private JTextArea flagArea=null;
	//private JLabel bgJLabel;
	//private JComboBox bgCombox=null;//背景选择列表
	private JPanel panel;
	private int xx;
	private int yy;
	private boolean isDraging;
	public ImageIcon getImageIcon(String usernameString)
	{
		ImageIcon imageIcon=null;
		
		/*从数据库中得到信息*/
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
				  if(sex.equals("男"))
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
	public UserFlag (String userString,String commentString)
	{
	   this.usernameString=userString;	
	   this.commenterString=commentString;
	}
	public void showUI(){
		JScrollPane jp=new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		jp.setViewportView(liuyanlistpaJPanel);

		//liuyanlistpaJPanel = new JPanel();
		liuyanlistpaJPanel  = new JPanel(){
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				g.drawImage(areaIcon.getImage(), 0, 0, null);
				this.setOpaque(false);
			}
		};
		//采用网格布局的方式，行数传参过来
		GetComment comment=new GetComment(usernameString);
		System.out.println(comment.getCommentInfo(usernameString));
		ArrayList<CommentInfo> list=comment.getCommentInfo(usernameString);
		if(list==null)
		{
			JLabel jLabel=new JLabel("暂时没有留言呢");
			liuyanlistpaJPanel.add(jLabel,JLabel.CENTER);
		}
		else {
			if(list.size()>5)
			{
			liuyanlistpaJPanel.setLayout(new GridLayout(list.size(),1));
			}
			else {
				liuyanlistpaJPanel.setLayout(new GridLayout(5,1));
			}
			for(int i=0;i<list.size();i++)
			{
				JLabel jLabel=new JLabel();
				jLabel.setSize(80,15);
				 jLabel.setPreferredSize(new Dimension(80,15));  
				//jLabel.setHorizontalAlignment(JLabel.CENTER);
				jLabel.setText(list.get(i).getCommenter()+" 吐槽:  "+list.get(i).getComment_content());
				 liuyanlistpaJPanel.setPreferredSize(new Dimension(150,80)); 
				liuyanlistpaJPanel.add(jLabel,JLabel.CENTER_ALIGNMENT);
			}
		}
		
		
		liuyanlistpaJPanel.setBounds(128,180, 150, 80);
		 liuyanlistpaJPanel.setPreferredSize(new Dimension(150,80)); 
		 //liuyanlistpaJPanel.setForeground(new Color(255,255,255));
		fileField = new TextField();
		final JFrame frame=new JFrame();
		panel  = new JPanel(){
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				g.drawImage(background.getImage(), 0, 0, null);
				this.setOpaque(false);
			}
		};
		panel.setLayout(null);
		frame.add(liuyanlistpaJPanel);
		panel.setSize(300,300);
		flagArea=new JTextArea(){
      	  

			Image image = areaIcon.getImage();

      //  Image grayImage = GrayFilter.createDisabledImage(image);
        {
          setOpaque(false);
        } // instance initializer


        public void paint(Graphics g) {
          g.drawImage(image, 0, 0, this);
          super.paint(g);
        }
      };
		flagArea.setLineWrap(true);
	//	window=new JWindow();
		frame.setUndecorated(true);
		frame.setSize(300,300);
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
		liuyanlLabel=new JLabel(liuyan);
		liuyanlLabel.setSize(50, 20);
		liuyanlLabel.setLocation(92, 150);
		fileField.setBounds(150,150,100,20);
		panel.add(fileField);
		//this.add(updateButton);
		panel.add(liuyanlLabel);
		touxiangIcon=getImageIcon(this.usernameString);
		touLabel=new JLabel(touxiangIcon);
		touLabel.setBounds(28,180, 80, 80);
		panel.add(touLabel);
		flagJLabel=new JLabel("人生信条:");
		flagJLabel.setFont(new Font("华文楷体", 0, 14));
		flagJLabel.setForeground(new Color(209,64,205));
		flagJLabel.setBounds(30, 0, 90,130);
		//flagJLabel.setLocation(new Point(30,100));
		//flagArea.setForeground(new Color(147,98,242));
		flagArea.setSize(160, 60);
		flagArea.setEnabled(true);
		flagArea.setEditable(false);
		//flagArea.setColumns(10);
		//flagArea.set;

		GetUserFlag getUserFlag=new GetUserFlag(usernameString);
        String userStringinfo=getUserFlag.getUserFlagInfo(usernameString);
        flagArea.setText(userStringinfo);
		
		JScrollPane scrollPane = new JScrollPane(flagArea, 

                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,

                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		 
		
		//flagArea.setSize(80, 80);
		flagArea.setLocation(new Point(92,60));
		panel.add(flagArea);
		
		//window.add(panel);
		
		closeButton = new JLabel(close);
		closeButton.setBounds(246, 10, 48, 33);
		panel.add(closeButton);
		panel.add(flagJLabel);
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
				closeButton.setBounds(246, 10, 48, 33);
				frame.dispose();
				if(UIMap.temporaryStorage.get("aboutUI")!=null){
					UIMap.temporaryStorage.remove("aboutUI");
				}
			}
			public void mousePressed(MouseEvent e){
				closeButton.setBounds(246, 10, 48, 33);
			}
		});
		
		liuyanlLabel.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent e){
				liuyanlLabel.setIcon(iuyan_fake);
			}
			public void mouseExited(MouseEvent e){
				liuyanlLabel.setIcon(liuyan);
			}
			public void mouseReleased(MouseEvent e){
				liuyanlLabel.setBounds(92, 150, 50, 20);
				
				if(UIMap.temporaryStorage.get("aboutUI")!=null){
					UIMap.temporaryStorage.remove("aboutUI");
				}
			}
			public void mousePressed(MouseEvent e){
				liuyanlLabel.setBounds(92, 150, 50, 20);
				if(fileField.getText().equals(""))
				{
                try {
						
						UIManager
								.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
					} catch (Exception e1) {
					}
					JOptionPane.showMessageDialog(null, "嘿，好歹说点什么嘛！");
					
					JFrame.setDefaultLookAndFeelDecorated(true);

					 try {

					 UIManager.setLookAndFeel(new McWinLookAndFeel());

					 } catch (UnsupportedLookAndFeelException e1) {

					 // TODO Auto-generated catch block

					 e1.printStackTrace();

					 }
				}else {
					new  AddComment(usernameString, commenterString).addCommentInfo(fileField.getText());
					frame.dispose();
					try {
						
						UIManager
								.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
					} catch (Exception e1) {
					}
				
					
					new UserFlag(usernameString, commenterString).showUI();
					
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
		
		okButton = new JLabel(ok);
		okButton.setBounds(200, 250, 87, 38);
		panel.add(okButton);
		okButton.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				okButton.setBounds(200, 252, 87, 38);
				frame.dispose();
			}
			public void mouseReleased(MouseEvent e){
				okButton.setBounds(200, 250, 87, 38);
			     
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
    
}