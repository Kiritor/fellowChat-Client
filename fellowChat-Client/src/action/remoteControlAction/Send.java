package action.remoteControlAction;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import util.tools.MediaControlTools;
import util.tools.Message;
import util.tools.UIMap;


public class Send extends Thread{

	private String ip;
	private Robot robot;
	private ImageIcon image;
	private String destination;
	
	public Send(String ip,String destination){
		this.ip = ip;
		this.destination = destination;
	}
	
	public void run(){
		try{
			robot = new Robot();
			
			Socket clientI = new Socket(ip,8860);
			Socket clientII = new Socket(ip,8860);
			
			MediaControlTools.storeRemoteSocket(destination, clientI, clientII);
			
			SendImage sendImage = new SendImage(clientI);
			sendImage.start();
			ReceiveControlInfo receiveControlInfo = new ReceiveControlInfo(clientII);
			receiveControlInfo.start();
			
			UIMap.chatUIMap.get(destination).setBreakButton();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private class SendImage extends Thread{
		private Socket clientI;
		private boolean flag = true;
		
		public SendImage(Socket clientI){
			this.clientI = clientI;
		}
		
		public void run(){
			try{
				ObjectOutputStream out = new ObjectOutputStream(clientI.getOutputStream());
				while(flag){
					BufferedImage bimage = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
					image = new ImageIcon(bimage);
					out.writeObject(image);
					out.reset();
					Thread.sleep(80);
				}
				
			}catch(Exception e){
				JOptionPane.showMessageDialog(null, "ÍøÂç¹ÊÕÏ£¬Ô¶³Ì¼à¿Ø¶Ï¿ª£¡£¡£¡");
				MediaControlTools.setRemoteControlState(false);
				flag = false;
			}
		}
	}
	
	private class ReceiveControlInfo extends Thread{
		private Socket clientII;
		private boolean flag = true;
		
		public ReceiveControlInfo(Socket clientII){
			this.clientII = clientII;
		}
		
		public void run(){
			String controlInfo = null;
			String type = null;
			try{
				while(flag){
					controlInfo = Message.readString(clientII.getInputStream());
					type = Message.getXMLValue("type", controlInfo);
					if(type.equals("click")){
						String x = Message.getXMLValue("x", controlInfo);
						String y = Message.getXMLValue("y", controlInfo);
						String button = Message.getXMLValue("button", controlInfo);
						robot.mouseMove(Integer.parseInt(x), Integer.parseInt(y));
						if(button.equals("1")){
							robot.mousePress(InputEvent.BUTTON1_MASK);
							robot.mouseRelease(InputEvent.BUTTON1_MASK);
						}
						if(button.equals("2")){
							robot.mousePress(InputEvent.BUTTON2_MASK);
							robot.mouseRelease(InputEvent.BUTTON2_MASK);
						}
						if(button.equals("3")){
							robot.mousePress(InputEvent.BUTTON3_MASK);
							robot.mouseRelease(InputEvent.BUTTON3_MASK);
						}
					}
					if(type.equals("move")){
						String x = Message.getXMLValue("x", controlInfo);
						String y = Message.getXMLValue("y", controlInfo);
						robot.mouseMove(Integer.parseInt(x), Integer.parseInt(y));
					}
					if(type.equals("key")){
						String key = Message.getXMLValue("key", controlInfo);
						robot.keyPress(Integer.parseInt(key));
						robot.keyRelease(Integer.parseInt(key));
					}
				}
			}catch(Exception e){
				flag = false;
			}
		}
	}
}
