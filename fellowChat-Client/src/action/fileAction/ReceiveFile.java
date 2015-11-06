package action.fileAction;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import util.tools.MediaControlTools;
import util.tools.UIMap;


public class ReceiveFile extends Thread{
    private File file;
    private String userName;
    private boolean flag = true;
    
    public ReceiveFile(File file,String userName){
    	this.file = file;
    	this.userName = userName;
    }
    
    public void setFile(File file){
    	this.file = file;
    }
    
    public void run(){
    	try{
    		ServerSocket ss = new ServerSocket(8850);
    		
    		while(flag){
    			Socket server = ss.accept();
        		Receive r = new Receive(server);
        		r.start();
    		}
    		
    	}catch(Exception e){
    		flag = false;
    		e.printStackTrace();
    	}
    }
    
    private class Receive extends Thread{
    	
    	private Socket server;
    	private FileOutputStream fout;
    	
    	public Receive(Socket server){
    		this.server = server;
    	}  	
    	
    	public void run(){
    		try{
    			fout = new FileOutputStream(file);
        		BufferedOutputStream bout = new BufferedOutputStream(fout);
        		BufferedInputStream bin = new BufferedInputStream(server.getInputStream());
        		
        		int i = 0;
        		UIMap.chatUIMap.get(userName).appendMsg(null,12,"��ʼ�����ļ�.....",true,"");
        		while(true){
        			i = bin.read();
        			if(i==-1){
        				break;
        			}
        			bout.write(i);
        		}
        		UIMap.chatUIMap.get(userName).appendMsg(null,12,"�ļ��������.....",true,"");
        		bin.close();
        		bout.flush();
        		bout.close();
        		server.close();
    		}catch(Exception e){
    			try{
    				fout.close();
    				server.close();
    			}catch(Exception ef){
    				ef.printStackTrace();
    			}
    			JOptionPane.showMessageDialog(null, "���ӶϿ����ļ�����ȡ����");
    			for(JPanel fileView : MediaControlTools.fileViewList){
    				if(((ProgressView)fileView).getUserName().equals(userName)){
    					UIMap.chatUIMap.get(userName).removeFileView(fileView);
    					break;
    				}
    			}
    			file.delete();
    			if(UIMap.chatUIMap.get(userName)!=null){
    				UIMap.chatUIMap.get(userName).appendMsg(null,12,"�ļ�����ʧ�ܣ���ɾ���������ļ�.......",true,"");
    			}
    		}
    	}
    }
}
