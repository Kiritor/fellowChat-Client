package util.tools;

import java.util.HashMap;

import action.mainUIAction.ShakeAction;


public class ActiveLabelTool {
    public static HashMap<String,ShakeAction> shakingLabel = new HashMap<String,ShakeAction>();
    
    public static void add(String userName,ShakeAction shake){
    	shakingLabel.put(userName, shake);
    }
    
    public static void remove(String userName){
    	shakingLabel.remove(userName);
    }
}
