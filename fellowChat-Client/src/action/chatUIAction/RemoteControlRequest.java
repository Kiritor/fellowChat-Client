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
			//未提交申请状态
			//发送申请监控请求
			String request = "<type>remoteRequest</type><sender>"+Loginner.loginner+"</sender><destination>"+destination+"</destination>";
			try{
				Message.sendMsg(request, ManClient.client.getOutputStream());
			}catch(Exception ef){
				ef.printStackTrace();
			}
			//转换remoteControlState
			UIMap.chatUIMap.get(destination).switchRequestState();
			//显示操作通知
			UIMap.chatUIMap.get(destination).appendMsg(null,12,"您请求"+destination+"对您进行远程监控，请等待回应......",true,"");
		}else{
			//已提交申请
			JOptionPane.showMessageDialog(null, "一次只能提交一个远程监控请求！");
		}
	}
}
