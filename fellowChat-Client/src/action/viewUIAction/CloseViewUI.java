package action.viewUIAction;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import util.tools.Loginner;
import util.tools.MediaControlTools;
import util.tools.Message;
import util.tools.UIMap;

import allUI.ViewUI;

import clientBase.ManClient;

public class CloseViewUI extends MouseAdapter{
	private String destination;
	
	public CloseViewUI(String destination){
		this.destination = destination;
	}
	
    public void mouseReleased(MouseEvent e){
    	//���ͶϿ�����֪ͨ
    	String str = "<type>viewBreak</type><sender>"+Loginner.loginner+"</sender><destination>"+destination+"</destination>";
    	try{
    		Message.sendMsg(str, ManClient.client.getOutputStream());
    	}catch(Exception ef){
    		ef.printStackTrace();
    	}
    	
    	//�ر���Ƶ����
    	ViewUI viewUI = UIMap.viewUIMap.get(destination);
    	viewUI.closeViewUI();
    	//��ViewUIMap���Ƴ���Ӧ��¼
    	UIMap.removeViewUI(destination);
    	//�ر���Ƶ�ķ��ͺͽ��ս���
    	MediaControlTools.videoFinish(destination);
    	//��ʾ����֪ͨ
    	if(UIMap.chatUIMap.get(destination)!=null){
    		UIMap.chatUIMap.get(destination).appendMsg(null,12,"��Ƶ��ȡ��..........",true,"");
    	}
    }
}
