package action.mainUIAction;

import util.tools.UIMap;
import allUI.MainUI;

public class FlashAction extends Thread{
	
	private boolean flag = true;

	public void run(){
		MainUI mainUI = (MainUI)UIMap.temporaryStorage.get("mainUI");
		while(flag){
			mainUI.flash2();
			try{
				Thread.sleep(500);
			}catch(Exception e){
				e.printStackTrace();
			}
			mainUI.flash1();
			try{
				Thread.sleep(500);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		mainUI.flash1();
	}
	
	public void stopFlashing(){
		flag = false;
	}
}
