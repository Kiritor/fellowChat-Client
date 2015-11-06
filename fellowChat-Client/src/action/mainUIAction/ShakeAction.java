package action.mainUIAction;

import java.awt.Point;

import javax.swing.JLabel;

import util.tools.Loginner;


public class ShakeAction extends Thread{
    private String sender;
    private boolean shake = true;
    
    private JLabel label;
    
    public ShakeAction(String sender){
    	this.sender = sender;
    }
    
    public void stopShake(){
    	shake = false;
    }
    
    public void run(){
    	label = Loginner.friendMap.get(sender);
    	Point p = label.getLocation();
    	int x = p.x;
    	int y = p.y;
    	int param = 0;
    	try{
    		
    		if(shake)
    		{
    			new PlaySound("images/msg.wav");
    		}
    		while(shake){
    			
        		x+=2;
        		y+=2;
        		label.setLocation(x, y);
        		param++;
        		if(param>4){
        			while(shake){
        				x-=2;
        				y-=2;
        				label.setLocation(x, y);
        				param--;
        				
        				Thread.sleep(80);
        				if(param<1||!shake){
        					break;
        				}
        			}
        		}
        		Thread.sleep(80);
        		if(!shake){
        			break;
        		}
        		
        	}
    		
    		label.setLocation(p);
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
}
