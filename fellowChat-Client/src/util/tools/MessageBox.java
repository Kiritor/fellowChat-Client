package util.tools;

import java.util.ArrayList;
import java.util.HashMap;

public class MessageBox {
    public static HashMap<String,ArrayList<String>> msgBox = new HashMap<String,ArrayList<String>>();
    public static ArrayList<String> systemMsg = new ArrayList<String>();
    
    public static void storeSystemMsg(String msg){
    	systemMsg.add(msg);
    }
    
    public static boolean isSystemMsgEmpty(){
    	if(systemMsg.size()>0){
    		return false;
    	}else{
    		return true;
    	}
    }
    
    public static String getSystemMsg(){
    	if(systemMsg.size()>0){
    		return systemMsg.remove(0);
    	}else{
    		return null;
    	}
    }
    
    public static void storeMsg(String sender,String msg){
    	ArrayList<String> msgList = msgBox.get(sender);
    	if(msgList==null){
    		msgList = new ArrayList<String>();
    		msgList.add(msg);
    		msgBox.put(sender, msgList);
    	}else{
    		msgList.add(msg);
    	}
    }
    
    public static void remove(String sender){
    	msgBox.remove(sender);
    }
}
