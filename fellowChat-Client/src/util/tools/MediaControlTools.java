package util.tools;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;

import action.mediaAction.ReceiveMedia;
import action.mediaAction.SendMedia;
import action.mediaAction.Transmition;
import action.mediaAction.VideoFinish;

public class MediaControlTools {
	
    public static HashMap<String,Object> viewStorage = new HashMap<String,Object>();
    public static HashMap<String,Object> remoteStorage = new HashMap<String,Object>();
	public static ArrayList<JPanel> fileViewList = new ArrayList<JPanel>();
    public static boolean fileServerState = false;
    public static boolean remoteControlState = false;
    
    public static void addFileView(JPanel fileView){
    	fileViewList.add(fileView);
    }
    
    public static void removeFileView(JPanel fileView){
    	fileViewList.remove(fileView);
    }
    
    public static void setRemoteControlState(boolean state){
    	remoteControlState = state;
    }
    
    public static boolean getRemoteControlState(){
    	return remoteControlState;
    }
    
    public static void fileServerOn(){
    	fileServerState = true;
    }
    
    public static void fileServerClose(){
    	fileServerState = false;
    }
    
    public static boolean isFileServerOn(){
    	return fileServerState;
    }
    
    public static void storeMediaSession(String userName,SendMedia sendVideo,SendMedia sendAudio,ReceiveMedia receiveMedia){
    	ArrayList<Transmition> mediaTra = new ArrayList<Transmition>();
    	mediaTra.add(sendVideo);
    	mediaTra.add(sendAudio);
    	mediaTra.add(receiveMedia);
    	viewStorage.put(userName, mediaTra);
    }
    
    public static void videoFinish(String userName){
//    	ArrayList<Transmition> mediaTra = (ArrayList<Transmition>)viewStorage.get(userName);
//    	for(Transmition tra : mediaTra){
//    		if(tra!=null){
//    			tra.closeTra();
//    		}
//    	}
//    	viewStorage.remove(userName);
    	VideoFinish v = new VideoFinish(userName);
    	v.start();
    }
    
    public static void storeRemoteSocket(String userName,Socket socketI,Socket socketII){
    	ArrayList<Socket> list = new ArrayList<Socket>();
    	list.add(socketI);
    	list.add(socketII);
    	remoteStorage.put(userName, list);
    }
    
    public static void remoteFinish(String userName){
    	ArrayList<Socket> list = (ArrayList<Socket>)remoteStorage.get(userName);
    	for(Socket s : list){
    		try{
    			s.close();
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    	}
    	remoteStorage.remove(userName);
    }
}
