package action.chatUIAction;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import util.tools.Loginner;
import util.tools.Message;
import util.tools.UIMap;

import clientBase.ManClient;

public class RemoteControlRequest extends MouseAdapter{
	
	private String destination;
	
	public RemoteControlRequest(String destination){
		this.destination = destination;
	}

	public void mouseReleased(MouseEvent e){
		if(UIMap.chatUIMap.get(destination).getRequestState()){
			//δ�ύ����״̬
			//��������������
			String request = "<type>remoteRequest</type><sender>"+Loginner.loginner+"</sender><destination>"+destination+"</destination>";
			try{
				Message.sendMsg(request, ManClient.client.getOutputStream());
			}catch(Exception ef){
				ef.printStackTrace();
			}
			//ת��remoteControlState
			UIMap.chatUIMap.get(destination).switchRequestState();
			//��ʾ����֪ͨ
			UIMap.chatUIMap.get(destination).appendMsg(null,12,"������"+destination+"��������Զ�̼�أ���ȴ���Ӧ......",true,"");
		}else{
			//���ύ����
			JOptionPane.showMessageDialog(null, "һ��ֻ���ύһ��Զ�̼������");
		}
	}
}
