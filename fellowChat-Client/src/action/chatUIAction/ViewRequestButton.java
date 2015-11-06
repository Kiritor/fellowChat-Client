package action.chatUIAction;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import util.tools.Loginner;
import util.tools.Message;
import util.tools.UIMap;

import allUI.ViewUI;

import clientBase.ManClient;

public class ViewRequestButton extends MouseAdapter{
    private String destination;
    
    public ViewRequestButton(String destination){
    	this.destination = destination;
    }
    
    public void mouseReleased(MouseEvent e){
    	//判断窗口是否打开
    	ViewUI viewUI = UIMap.viewUIMap.get(destination);
    	if(viewUI!=null){
    		JOptionPane.showMessageDialog(null, "视频窗口已打开！");
    	}else{
    		//发送请求
            String request = "<type>viewRequest</type><sender>"+Loginner.loginner+"</sender><destination>"+destination+"</destination>";
            try{
            	Message.sendMsg(request, ManClient.client.getOutputStream());
            }catch(Exception ef){
                ef.printStackTrace();	
            }
            //显示操作通知
            UIMap.chatUIMap.get(destination).appendMsg(null,12,"视频请求已发送.......",true,"");
    	}
    }
}
