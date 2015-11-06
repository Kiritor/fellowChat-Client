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
			//���ͶϿ�����֪ͨ
			String msg = "<type>remoteBreak</type><sender>"+Loginner.loginner+"</sender><destination>"+destination+"</destination><from>client</from>";
			Message.sendMsg(msg, ManClient.client.getOutputStream());
			//�Ͽ�����
			MediaControlTools.remoteFinish(destination);
			//��������
			JOptionPane.showMessageDialog(null, "Զ�̼�������ѶϿ�������");
			UIMap.chatUIMap.get(destination).appendMsg(null,12,"����ȡ����Զ�̼��.....",true,"");
			UIMap.chatUIMap.get(destination).removeBreakButton();
			MediaControlTools.setRemoteControlState(false);
			
		}catch(Exception ef){
			ef.printStackTrace();
		}
	}
}
