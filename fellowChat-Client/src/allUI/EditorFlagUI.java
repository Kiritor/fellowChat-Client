package allUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.TextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
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

import util.pojo.CommentInfo;
import util.tools.AddComment;
import util.tools.DataBaseControl;
import util.tools.GetComment;
import util.tools.GetUserFlag;
import util.tools.Loginner;
import util.tools.UIMap;
import util.tools.UpdateUserFlag;

import com.jtattoo.plaf.mcwin.McWinLookAndFeel;
import com.sun.awt.AWTUtilities;

public class EditorFlagUI {

	private ImageIcon areaIcon = new ImageIcon("image/desk.jpg");
    private JLabel touLabel;
	private ImageIcon touxiangIcon;
	private String usernameString;//被留言的对象
	
	private JWindow window;
	//private JPanel panel;
	private JLabel closeButton;
	private JLabel okButton;
    
	//添加对修改操作的按钮
	private ImageIcon updateIcon=new ImageIcon("image/update.jpg");
	private ImageIcon updatefakIcon=new ImageIcon("image/updatefak.jpg");
	private JLabel updatelJLabel;
	
	private ImageIcon image = new ImageIcon("image/aboutUI.png");
	private ImageIcon close = new ImageIcon("image/closeButton.png");
	private ImageIcon close_fake = new ImageIcon("image/closeButton_fake.png");
	private ImageIcon liuyan = new ImageIcon("image/liuyan.jpg");
	private ImageIcon iuyan_fake = new ImageIcon("image/liuyanfakjpg.jpg");
	private ImageIcon ok = new ImageIcon("image/okButton.png");
	private ImageIcon ok_fake = new ImageIcon("image/okButton_fake.png");
	
	
	//添加一些详细信息
	private JLabel flagJLabel=null;//人生格言的标签
	private JTextArea flagArea=null;
	//private JLabel bgJLabel;
	//private JComboBox bgCombox=null;//背景选择列表
	private JPanel panel;
	private int xx;
	private int yy;
	private boolean isDraging;
	private ImageIcon background= new ImageIcon("image/background.jpg");
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
	public EditorFlagUI (String userString)
	{
	   this.usernameString=userString;	
	   
	}
	public void showUI(){
		
		//采用网格布局的方式，行数传参过来
		
		updatelJLabel = new JLabel(updateIcon);
		updatelJLabel.setBounds(92,150,50,20);

		
	
		final JFrame frame=new JFrame();
		//panel =new JPanel();
		panel  = new JPanel(){
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				g.drawImage(background.getImage(), 0, 0, null);
				this.setOpaque(false);
			}
		};
		panel.add(updatelJLabel);
		panel.setLayout(null);
		panel.setSize(300,300);
		updatelJLabel.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent e){
				updatelJLabel.setIcon(updatefakIcon);
			}
			public void mouseExited(MouseEvent e){
				updatelJLabel.setIcon(updateIcon);
			}
			public void mousePressed(MouseEvent e){
				
				
				new UpdateUserFlag(Loginner.loginner).updateUserFlagInfo(Loginner.loginner, flagArea.getText());
				
				frame.dispose();
				 try {
						
						UIManager
								.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
					} catch (Exception e1) {
					}
					
				new EditorFlagUI(Loginner.loginner).showUI();
				JFrame.setDefaultLookAndFeelDecorated(true);

				 try {

				 UIManager.setLookAndFeel(new McWinLookAndFeel());

				 } catch (UnsupportedLookAndFeelException e1) {

				 // TODO Auto-generated catch block

				 e1.printStackTrace();

				 }
			}
		});
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
	
	
		//this.add(updateButton);
		
		touxiangIcon=getImageIcon(this.usernameString);
		touLabel=new JLabel(touxiangIcon);
		touLabel.setBounds(28,180, 80, 80);
		panel.add(touLabel);
		flagJLabel=new JLabel("人生信条:");
		flagJLabel.setFont(new Font("华文楷体", 0, 13));
		//flagJLabel.setForeground(new Color(149,149,149));
		flagJLabel.setBounds(30, 0, 90,130);
		//flagJLabel.setLocation(new Point(30,100));
		
		flagArea.setSize(160, 80);
		flagArea.setEnabled(true);
		flagArea.setEditable(true);
		flagArea.setForeground(new Color(147,98,242));
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
