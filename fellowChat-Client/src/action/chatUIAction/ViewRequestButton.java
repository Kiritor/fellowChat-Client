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
    	//�жϴ����Ƿ��
    	ViewUI viewUI = UIMap.viewUIMap.get(destination);
    	if(viewUI!=null){
    		JOptionPane.showMessageDialog(null, "��Ƶ�����Ѵ򿪣�");
    	}else{
    		//��������
            String request = "<type>viewRequest</type><sender>"+Loginner.loginner+"</sender><destination>"+destination+"</destination>";
            try{
            	Message.sendMsg(request, ManClient.client.getOutputStream());
            }catch(Exception ef){
                ef.printStackTrace();	
            }
            //��ʾ����֪ͨ
            UIMap.chatUIMap.get(destination).appendMsg(null,12,"��Ƶ�����ѷ���.......",true,"");
    	}
    }
}
