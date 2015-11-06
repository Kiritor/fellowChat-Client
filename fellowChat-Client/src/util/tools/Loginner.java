package util.tools;

import java.util.HashMap;

import javax.swing.JLabel;

import util.pojo.UserInfo;


public class Loginner {
    public static String loginner;
    public static String loginPwd;
    public static HashMap<String,JLabel> friendMap = new HashMap<String,JLabel>();
    
    public static void setLoginner(String user){
    	loginner = user;
    }
    public static void setLoginPwd(String pwd){
    	loginPwd = pwd;
    }
    public static void add(String sender,JLabel label){
    	friendMap.put(sender, label);
    }
    public static void remove(String sender){
    	friendMap.remove(sender);
    }
}
