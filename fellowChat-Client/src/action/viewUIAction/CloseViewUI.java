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
    	//发送断开连接通知
    	String str = "<type>viewBreak</type><sender>"+Loginner.loginner+"</sender><destination>"+destination+"</destination>";
    	try{
    		Message.sendMsg(str, ManClient.client.getOutputStream());
    	}catch(Exception ef){
    		ef.printStackTrace();
    	}
    	
    	//关闭视频窗口
    	ViewUI viewUI = UIMap.viewUIMap.get(destination);
    	viewUI.closeViewUI();
    	//从ViewUIMap中移除对应记录
    	UIMap.removeViewUI(destination);
    	//关闭视频的发送和接收进程
    	MediaControlTools.videoFinish(destination);
    	//显示操作通知
    	if(UIMap.chatUIMap.get(destination)!=null){
    		UIMap.chatUIMap.get(destination).appendMsg(null,12,"视频已取消..........",true,"");
    	}
    }
}
