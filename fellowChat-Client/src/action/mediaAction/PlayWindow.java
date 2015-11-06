package action.mediaAction;

import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.Manager;
import javax.media.Player;
import javax.media.PrefetchCompleteEvent;
import javax.media.RealizeCompleteEvent;
import javax.media.protocol.DataSource;
import javax.swing.JWindow;

import util.tools.UIMap;


public class PlayWindow extends Thread implements ControllerListener {
	
	private JWindow window;
	private DataSource data;
	private Player player;
	private String destination;
	private String type;
	
	public PlayWindow(DataSource data,String destination,String type){
		this.data = data;
		this.destination = destination;
		this.type = type;
	}
	
	public void run(){
		try{
			player = Manager.createPlayer(data);
			player.addControllerListener(this);
			player.realize();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void stopPlayer(){
		player.close();
	}
	
	public void controllerUpdate(ControllerEvent e){
		if(e instanceof RealizeCompleteEvent){
			if(player.getVisualComponent()!=null){
				if(type.equals("local")){
					UIMap.viewUIMap.get(destination).addLocalPlayWindow(player.getVisualComponent());
				}else{
					UIMap.viewUIMap.get(destination).addRemotePlayWindow(player.getVisualComponent());
				}
			}
			player.prefetch();
		}
		if(e instanceof PrefetchCompleteEvent){
			player.start();
			if(type.equals("local")){
				UIMap.viewUIMap.get(destination).localReady();
			}else{
				UIMap.viewUIMap.get(destination).remoteReady();
			}
		}
	}
}
