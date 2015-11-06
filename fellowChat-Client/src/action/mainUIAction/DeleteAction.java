package action.mainUIAction;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import util.pojo.UserInfo;
import util.tools.FriendList;
import util.tools.Loginner;
import util.tools.Message;
import util.tools.UIMap;

import allUI.MainUI;

import clientBase.ManClient;

public class DeleteAction implements ActionListener{
	
	private String destination;

	public void actionPerformed(ActionEvent e) {
		destination = ((JPopupMenu)((JMenuItem)e.getSource()).getParent()).getLabel();
		String tip = "您确定要删除好友"+destination+"吗？一旦删除的话对方也将联系不到您！";
		int option = JOptionPane.showOptionDialog(null, tip, null, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
		if(option==0){
			//确定删除
			String msg = "<type>delete</type><sender>"+Loginner.loginner+"</sender><destination>"+destination+"</destination>";
			try{
				Message.sendMsg(msg, ManClient.client.getOutputStream());
				
				for(UserInfo user : FriendList.friendList){
					if(user.getUserName().equals(destination)){
						FriendList.friendList.remove(user);
						break;
					}
				}
				((MainUI)UIMap.temporaryStorage.get("mainUI")).resetFriendPanel();
				
				JOptionPane.showMessageDialog(null, "您已成功删除好友"+destination+"!!!");
				
			}catch(Exception ef){
				ef.printStackTrace();
			}
		}
	}
}
