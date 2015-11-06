package action.remoteControlAction;

import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import util.tools.MediaControlTools;

import allUI.RemoteControlUI;


public class Receive extends Thread{

	private RemoteControlUI remoteUI;
	private String destination;
	
	public Receive(RemoteControlUI remoteUI,String destination){
		this.remoteUI = remoteUI;
		this.destination = destination;
	}
	
	public void run(){
		try{
			ServerSocket ss = new ServerSocket(8860);
			
			Socket serverI = ss.accept();
			Socket serverII = ss.accept();
			
			MediaControlTools.storeRemoteSocket(destination, serverI, serverII);
			
			ReceiveImage r = new ReceiveImage(serverI);
			r.start();
			
			remoteUI.setSocket(serverII);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private class ReceiveImage extends Thread{
		private Socket serverI;
		private boolean flag = true;
		
		public ReceiveImage(Socket serverI){
			this.serverI = serverI;
		}
		
		public void run(){
			try{
				ObjectInputStream oin = new ObjectInputStream(serverI.getInputStream());
				while(flag){
					ImageIcon image = (ImageIcon)oin.readObject();
					remoteUI.changeImage(image);
				}
			}catch(Exception e){
				flag = false;
				JOptionPane.showMessageDialog(null, "ÍøÂç¹ÊÕÏ£¬Ô¶³Ì¿ØÖÆ¶Ï¿ª");
				remoteUI.closeRemoteControlUI();
			}
		}
	}
}
