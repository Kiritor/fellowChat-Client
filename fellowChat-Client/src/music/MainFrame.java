/**
 * 主框架
 */
package music;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

import javax.swing.*;

import com.sun.awt.AWTUtilities;
import com.sun.org.apache.bcel.internal.generic.Select;

import sun.net.www.content.image.jpeg;
import util.tools.UIMap;
import action.loginUIAction.RegAction;
import action.mainUIAction.MainUICloseAction;
import allUI.AboutUI;
import allUI.MainUI;

/**
 * @author guanchun
 *
 */
public class MainFrame extends JFrame{
	 private int xx;
	 private  int yy;
	 boolean isDraging = false;
	 /*最小化按钮的标签*/
	 private JLabel miniJLabel;
	 private ImageIcon miniIcon=new ImageIcon("images/mini.gif");
	 private ImageIcon nimifakiIcon = new ImageIcon("images/minifak.jpg");
	 
	 /*关闭按钮的标签*/
	 private JLabel closeJLabel;
	 private ImageIcon colseIcon= new ImageIcon("images/close.gif");
	 private ImageIcon closefakiIcon = new ImageIcon("images/closefak.jpg");
	 
	 private JPanel optionJPanel;
	public MainFrame ()
	{
		
		optionJPanel = new JPanel();
		//optionJPanel.setLayout(null);
		optionJPanel.setBounds(0,0,320,20);
		optionJPanel.setBackground(new Color(214,217,233));
		miniJLabel = new JLabel(miniIcon);
		miniJLabel.setBounds(270,3, 19 , 10);
		closeJLabel = new JLabel(colseIcon);
		closeJLabel.setBounds(315, 3, 19, 10);
		miniJLabel.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent e){
				miniJLabel.setIcon(nimifakiIcon);
			}
			public void mouseExited(MouseEvent e){
				miniJLabel.setIcon(miniIcon);
			}
			public void mousePressed(MouseEvent e){
				setExtendedState(JFrame.ICONIFIED);
				
			}
			public void mouseReleased(MouseEvent e){
				miniJLabel.setIcon(miniIcon);
			}
		});
		closeJLabel.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent e){
				closeJLabel.setIcon(closefakiIcon);
			}
			public void mouseExited(MouseEvent e){
				closeJLabel.setIcon(colseIcon);
			}
			public void mousePressed(MouseEvent e){
				if(tray!=null)
				{
					tray.remove(icon);
					showTray();
					dispose();
					closeJLabel.setIcon(colseIcon);
				}else {

					showTray();
					 dispose();
					 closeJLabel.setIcon(colseIcon);
				}
				
				
				
			}
			public void mouseReleased(MouseEvent e){
				closeJLabel.setIcon(colseIcon);
			}
		});
		optionJPanel.add(new JLabel("                                 "));
		optionJPanel.add(new JLabel("                                          "));
		optionJPanel.add(miniJLabel,JPanel.RIGHT_ALIGNMENT);
		optionJPanel.add(closeJLabel,JPanel.RIGHT_ALIGNMENT);
		this.setUndecorated(true);
		this.setLayout(null);
		setLocation(100,50);
		//setTitle("MuPlayer 1.0");
	//	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        Dimension size = new Dimension(320,396);
        setMinimumSize(size);
        panel = new MainPanel();
        panel.setBounds(0,16,320,380);
        this.add(optionJPanel);
        add(panel);
     //   panel.updateUI();
        AWTUtilities.setWindowShape(this,  
		           new RoundRectangle2D.Double(0.0D, 0.0D, this.getWidth(),  
		        		   this.getHeight(), 18.0D, 18.0D)); 
        this.addMouseListener(new MouseAdapter() {
			   public void mousePressed(MouseEvent e) {
			    isDraging = true;
			     xx = e.getX();
			     yy = e.getY();
			   }

			   public void mouseReleased(MouseEvent e) {
			     isDraging = false;
			   }
			  });
		this.addMouseMotionListener(new MouseMotionAdapter() {
			   public void mouseDragged(MouseEvent e) {
			    if (isDraging) { 
			     int left = getLocation().x;
			     int top = getLocation().y;
			     setLocation(left + e.getX() - xx, top + e.getY() - yy);
			    } 
			   }
			  });  
        
      addWindowListener(new WindowListener() {
		
		@Override
		public void windowOpened(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void windowIconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void windowDeiconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void windowDeactivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void windowClosing(WindowEvent e) {
			//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	//System.exit(0);
		//	dispose();
		//setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); 
			if(tray==null)
			{
				 showTray();
			}
			 
		}
		
		@Override
		public void windowClosed(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void windowActivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
	});
	}
	
	public void showTray(){
		if(SystemTray.isSupported()){
			 tray = SystemTray.getSystemTray();
			
			//托盘图片
			
			
			 icon = new TrayIcon(image,"基友音乐");
			icon.setImageAutoSize(true);
			icon.addMouseListener(new MouseAdapter(){
				public void mouseReleased(MouseEvent e){
					
				}
				
		            public void mouseClicked(MouseEvent e) {
		                
		            	if((e.getModifiers() & InputEvent.BUTTON1_MASK) != 0)
		            	{
		                	//tray.remove(icon); // 从系统的托盘实例中移除托盘图标
		                    setVisible(true); // 显示窗口
		            	}
		            }
		            
		       
			});
			//添加弹出菜单
			PopupMenu popup = new PopupMenu();
			MenuItem show = new MenuItem("显示");
			show.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
				setVisible(true);
				}
			});
			popup.add(show);
			//MenuItem reg = new MenuItem("注册");
			//reg.addActionListener(new RegAction());
		//	popup.add(reg);
			MenuItem about = new MenuItem("关于");
			about.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					JOptionPane.showMessageDialog(null, "简单的音乐播放器");
				}
			});
			popup.add(about);
			MenuItem exit = new MenuItem("退出");
			exit.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					tray.remove(icon); 
					panel.getUpanel().getPane().stopplay();
					dispose();
				}
			});
			popup.add(exit);
			
			icon.setPopupMenu(popup);
			try{
				tray.add(icon);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		//isTrayShowed = true;
	}
	Image image = Toolkit.getDefaultToolkit().createImage("images/music.jpg");
	TrayIcon  icon = new TrayIcon(image,"基友音乐"); ;
	private SystemTray tray;
	private MainPanel panel;
}
