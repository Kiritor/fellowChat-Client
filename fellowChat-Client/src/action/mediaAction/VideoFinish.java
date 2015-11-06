package action.mediaAction;

import java.util.ArrayList;

import util.tools.MediaControlTools;


public class VideoFinish extends Thread{

	private String userName;
	
	public VideoFinish(String userName){
		this.userName = userName;
	}
	
	public void run(){
		ArrayList<Transmition> mediaTra = (ArrayList<Transmition>)MediaControlTools.viewStorage.get(userName);
    	for(Transmition tra : mediaTra){
    		if(tra!=null){
    			tra.closeTra();
    		}
    	}
    	MediaControlTools.viewStorage.remove(userName);
	}
}
