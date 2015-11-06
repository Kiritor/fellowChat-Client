package action.chatUIAction;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.Socket;

import javax.swing.JOptionPane;

import util.tools.Loginner;
import util.tools.MediaControlTools;
import util.tools.Message;
import util.tools.UIMap;

import clientBase.ManClient;

public class RemoteBreakButton extends MouseAdapter{

	private String destination;
	
	public RemoteBreakButton(String destination){
		this.destination = destination;
	}
	
	public void mouseReleased(MouseEvent e){
		try{
			//发送断开连接通知
			String msg = "<type>remoteBreak</type><sender>"+Loginner.loginner+"</sender><destination>"+destination+"</destination><from>client</from>";
			Message.sendMsg(msg, ManClient.client.getOutputStream());
			//断开连接
			MediaControlTools.remoteFinish(destination);
			//后续操作
			JOptionPane.showMessageDialog(null, "远程监控连接已断开！！！");
			UIMap.chatUIMap.get(destination).appendMsg(null,12,"您以取消了远程监控.....",true,"");
			UIMap.chatUIMap.get(destination).removeBreakButton();
			MediaControlTools.setRemoteControlState(false);
			
		}catch(Exception ef){
			ef.printStackTrace();
		}
	}
}
