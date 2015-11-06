package util.tools;

import java.util.HashMap;

import allUI.ChatUI;
import allUI.RemoteControlUI;
import allUI.ViewUI;


public class UIMap {
    public static HashMap<String,ChatUI> chatUIMap = new HashMap<String,ChatUI>();
    public static HashMap<String,ViewUI> viewUIMap = new HashMap<String,ViewUI>();
    public static HashMap<String,RemoteControlUI> remoteControlUIMap = new HashMap<String,RemoteControlUI>();
    public static HashMap<String,Object> temporaryStorage = new HashMap<String,Object>();
    public static boolean isConnected = true;
    
    public static void storeObj(String name,Object obj){
    	temporaryStorage.put(name, obj);
    }
    
    public static void removeObj(String name){
    	temporaryStorage.remove(name);
    }
    
    public static void add(String userName,RemoteControlUI remoteControlUI){
    	remoteControlUIMap.put(userName, remoteControlUI);
    }
    
    public static void removeRemoteControlUI(String userName){
    	remoteControlUIMap.remove(userName);
    }
    
    public static void add(String userName,ViewUI viewUI){
    	viewUIMap.put(userName, viewUI);
    }
    
    public static void removeViewUI(String userName){
    	viewUIMap.remove(userName);
    }
    
    public static void add(String userName,ChatUI chatUI){
    	chatUIMap.put(userName, chatUI);
    }
    public static void removeChatUI(String userName){
    	chatUIMap.remove(userName);
    }
}
