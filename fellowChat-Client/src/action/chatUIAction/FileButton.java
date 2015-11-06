package action.chatUIAction;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JFileChooser;

import util.tools.Loginner;
import util.tools.Message;
import util.tools.UIMap;

import clientBase.ManClient;

public class FileButton extends MouseAdapter{
    private String destination;
    
    public FileButton(String destination){
    	this.destination = destination;
    }
    
    public void mouseReleased(MouseEvent e){
    	JFileChooser fileChooser = new JFileChooser();
    	int option = fileChooser.showDialog(null, "ѡ��");
    	if(option==JFileChooser.APPROVE_OPTION){
    		File file = fileChooser.getSelectedFile();
    		String path = file.getAbsolutePath();
    		String fileName = file.getName();
    		String size = (float)(file.length()/1000)+"KB";
    		//�����ļ���������
    		String requestMsg = "<type>fileRequest</type><sender>"+Loginner.loginner+"</sender><destination>"+destination+"</destination><fileName>"+fileName+"</fileName><fileSize>"+size+"</fileSize><filePath>"+path+"</filePath>";
    		try{
    			Message.sendMsg(requestMsg, ManClient.client.getOutputStream());
    		}catch(Exception ef){
    			ef.printStackTrace();
    		}
    		//��ʾ������Ϣ
    		UIMap.chatUIMap.get(destination).appendMsg(null,12,"�ļ����������ѷ���.......",true,"");
    	}
    }
}
