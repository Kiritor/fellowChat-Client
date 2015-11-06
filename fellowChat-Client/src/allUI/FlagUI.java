package allUI;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
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
import javax.swing.event.ChangeListener;

import com.jtattoo.plaf.mcwin.McWinLookAndFeel;
import com.sun.awt.AWTUtilities;
import com.sun.java.swing.plaf.windows.resources.windows;

import util.tools.DataBaseControl;
import util.tools.GetUserFlag;
import util.tools.UIMap;

public class FlagUI {
	private TextField fileField;
    private JLabel touLabel;
	private ImageIcon touxiangIcon;
	private String usernameString;
	private JWindow window;
	//private JPanel panel;
	private JLabel closeButton;
	private JLabel okButton;
	private JButton updateButton;
	private ImageIcon image = new ImageIcon("image/aboutUI.png");
	private ImageIcon close = new ImageIcon("image/closeButton.png");
	private ImageIcon close_fake = new ImageIcon("image/closeButton_fake.png");
	private ImageIcon ok = new ImageIcon("image/okButton.png");
	private ImageIcon ok_fake = new ImageIcon("image/okButton_fake.png");
	private ImageIcon background= new ImageIcon("image/background.jpg");
	
	//添加一些详细信息
	private JLabel flagJLabel=null;//人生格言的标签
	private JTextArea flagArea=null;
	//private JLabel bgJLabel;
	//private JComboBox bgCombox=null;//背景选择列表
	
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
	public FlagUI (String userString)
	{
	   this.usernameString=userString;	
	}
	public void showUI(){
		fileField = new TextField();
		
		flagArea=new JTextArea();
		flagArea.setLineWrap(true);
		window=new JWindow();
		window  = new JWindow(){
			public void paintComponent(Graphics g){
				//super.paintComponent(g);
				g.drawImage(background.getImage(), 0, 0, null);
				//this.setOpaque(false);
			}
		};
		window.setSize(300,300);
		window.setLayout(null);
		window.setEnabled(true);
		
		AWTUtilities.setWindowShape(window,  
		           new RoundRectangle2D.Double(0.0D, 0.0D, window.getWidth(),  
		               window.getHeight(), 18.0D, 18.0D));  
		//panel = new JPanel();
	   //panel.setLayout(null);
		updateButton =new JButton("留言");
		updateButton.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				//new UserFlag(usernameString).showUI();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		updateButton.setSize(50, 20);
		updateButton.setLocation(92, 150);
		fileField.setBounds(150,150,100,20);
		window.add(fileField);
		window.add(updateButton);
		touxiangIcon=getImageIcon(this.usernameString);
		touLabel=new JLabel(touxiangIcon);
		touLabel.setBounds(62,180, 80, 80);
		window.add(touLabel);
		flagJLabel=new JLabel("人生信条");
		flagJLabel.setBounds(30, 0, 90,130);
		//flagJLabel.setLocation(new Point(30,100));
		
		flagArea.setSize(160, 80);
		flagArea.setEnabled(true);
		flagArea.setEditable(true);
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
		window.add(flagArea);
		
		//window.add(panel);
		
		closeButton = new JLabel(close);
		closeButton.setBounds(246, 10, 48, 33);
		window.add(closeButton);
		window.add(flagJLabel);
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
				window.dispose();
				if(UIMap.temporaryStorage.get("aboutUI")!=null){
					UIMap.temporaryStorage.remove("aboutUI");
				}
			}
			public void mousePressed(MouseEvent e){
				closeButton.setBounds(246, 12, 48, 33);
			}
		});
		
		okButton = new JLabel(ok);
		okButton.setBounds(200, 250, 87, 38);
		window.add(okButton);
		okButton.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				okButton.setBounds(200, 252, 87, 38);
			}
			public void mouseReleased(MouseEvent e){
				okButton.setBounds(200, 250, 87, 38);
				window.dispose();
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
		
		window.addMouseListener(new MouseAdapter() {      
            public void mousePressed(MouseEvent e) {
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
		
		window.setVisible(true);
		window.setLocationRelativeTo(null);
	}

}
