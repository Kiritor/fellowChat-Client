package action.mainUIAction;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import util.tools.FriendList;
import util.tools.Loginner;
import util.tools.Message;

import clientBase.ManClient;

public class MainUICloseAction extends MouseAdapter{
	
    public void mouseReleased(MouseEvent e){
    	closeOpration();
    }
    public static void closeOpration(){
    	//���������ߺ��ѷ�������֪ͨ
    	String onlineFriends = "";
    	for(int i=0;i<FriendList.friendList.size();i++){
    		if(FriendList.friendList.get(i).getUserState().equals("b")){
    			onlineFriends = onlineFriends + FriendList.friendList.get(i).getUserName() + ",";
    		}
    	}
    	String leaveMsg = "<type>leave</type><sender>"+Loginner.loginner+"</sender><destinations>"+onlineFriends+"</destinations>";
    	try{
    		Message.sendMsg(leaveMsg, ManClient.client.getOutputStream());
    	}catch(Exception ef){
    		ef.printStackTrace();
    	}
    	//�˳�����
    	System.exit(0);
    }
}
