package action.fileAction;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import util.tools.MediaControlTools;
import util.tools.UIMap;


public class SendFile extends Thread{
    private String ip;
    private String userName;
    private long sendedSize = 0;
    private long fileSize;
    private File file;
    private ProgressView pview;
    private Socket client;
    
    public SendFile(String userName,String ip,File file){
    	this.userName = userName;
    	this.ip = ip;
		this.file = file;
		this.fileSize = file.length();
    }
    
    public long getSendedSize(){
    	return sendedSize;
    }
    
    public long getFileSize(){
    	return fileSize;
    }
    
    public void run(){
    	try{
    		client = new Socket(ip,8850);
    		
    		FileInputStream fin = new FileInputStream(file);
    		BufferedInputStream bin = new BufferedInputStream(fin);
    		BufferedOutputStream bout = new BufferedOutputStream(client.getOutputStream());
    		
    		int i = 0;
    		UIMap.chatUIMap.get(userName).appendMsg(null,12,"文件开始传输.....",true,"");
    		while(true){
    			i = bin.read();
    			if(i==-1){
    				bout.write(-1);
    				break;
    			}
    			bout.write(i);
    			sendedSize++;
    		}
    		UIMap.chatUIMap.get(userName).appendMsg(null,12,"文件传输完毕.....",true,"");
    		bin.close();
    		bout.flush();
    		bout.close();
    		client.close();
    		
    	}catch(Exception e){
    		try{
    			client.close();
    		}catch(Exception ef){
    			ef.printStackTrace();
    		}
    		JOptionPane.showMessageDialog(null, "连接断开，文件传输取消！");
    		for(JPanel fileView : MediaControlTools.fileViewList){
    			if(((ProgressView)fileView).getUserName().equals(userName)){
    				UIMap.chatUIMap.get(userName).removeFileView(fileView);
    				break;
    			}
    		}
    		if(UIMap.chatUIMap.get(userName)!=null){
    			UIMap.chatUIMap.get(userName).appendMsg(null,12,"文件传输失败......",true,"");
    		}
    	}
    }
}
