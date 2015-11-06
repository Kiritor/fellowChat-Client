package allUI;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import com.sun.awt.AWTUtilities;

import util.tools.Loginner;
import util.tools.MediaControlTools;
import util.tools.Message;
import util.tools.UIMap;

import clientBase.ManClient;

public class RemoteControlUI {

	private JFrame frame;
	private JLabel label;
	private Socket serverII;
	private ImageIcon image = new ImageIcon("image/remoteControlUI.jpg");
	
	private boolean controlable = false;
	private String destination;
	
	public RemoteControlUI(String destination){
		this.destination = destination;
	}
	
	public void closeRemoteControlUI(){
		frame.dispose();
	}
	
	public void showUI(){
		frame = new JFrame("您正在监控 "+destination){
			public void dispose(){
				super.dispose();
				//发送断开通知
				String msg = "<type>remoteBreak</type><sender>"+Loginner.loginner+"</sender><destination>"+destination+"</destination><from>server</from>";
				try{
					Message.sendMsg(msg, ManClient.client.getOutputStream());
				}catch(Exception e){
					e.printStackTrace();
				}
				//关闭远程监控Socket
				MediaControlTools.remoteFinish(destination);
				if(UIMap.chatUIMap.get(destination)!=null){
					UIMap.chatUIMap.get(destination).appendMsg(null,12,"远程监控已断开！！！",true,"");
				}
			}
		};
		frame.setSize(564, 400);
		frame.setLayout(new GridLayout(1,1));
		/*使窗体实现圆角的效果*/
		AWTUtilities.setWindowShape(frame,  
			           new RoundRectangle2D.Double(0.0D, 0.0D, frame.getWidth(),  
			        		   frame.getHeight(), 26.0D, 26.0D));  
		label = new JLabel(image);
		label.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e){
				if(controlable){
					String controlMsg = "<type>click</type><x>"+e.getX()+"</x><y>"+e.getY()+"</y><button>"+e.getButton()+"</button>";
					try{
						Message.sendMsg(controlMsg, serverII.getOutputStream());
					}catch(Exception ef){
						ef.printStackTrace();
					}
				}
			}
		});
		label.addMouseMotionListener(new MouseAdapter(){
			public void mouseMoved(MouseEvent e){
				if(controlable){
					String controlMsg = "<type>move</type><x>"+e.getX()+"</x><y>"+e.getY()+"</y>";
					try{
						Message.sendMsg(controlMsg, serverII.getOutputStream());
					}catch(Exception ef){
						ef.printStackTrace();
					}
				}
			}
		});
		label.addKeyListener(new KeyAdapter(){
			public void keyReleased(KeyEvent e){
				if(controlable){
					String controlMsg = "<type>key</type><key>"+e.getKeyCode()+"</key>";
					try{
						Message.sendMsg(controlMsg, serverII.getOutputStream());
					}catch(Exception ef){
						ef.printStackTrace();
					}
				}
			}
		});
		JScrollPane scroll = new JScrollPane(label);
		frame.add(scroll);
		
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(2);
		
		label.requestFocus();
	}
	
	public void resizeEnable(){
		frame.setResizable(true);
	}
	
	public void changeImage(ImageIcon image){
		label.setIcon(image);
	}
	
	public void setSocket(Socket serverII){
		this.serverII = serverII;
		controlable = true;
		frame.setResizable(true);
	}
	
//	public static void main(String args[]){
//		RemoteControlUI r = new RemoteControlUI();
//		r.showUI();
//	}
}
