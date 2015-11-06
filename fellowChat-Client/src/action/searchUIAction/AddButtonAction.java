package action.searchUIAction;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import util.pojo.UserInfo;
import util.tools.FriendList;
import util.tools.Loginner;
import util.tools.Message;

import allUI.SearchUI;

import clientBase.ManClient;

public class AddButtonAction extends MouseAdapter{

	private SearchUI searchUI;
	
	public AddButtonAction(SearchUI searchUI){
		this.searchUI = searchUI;
	}
	
	public void mouseReleased(MouseEvent e){
		ArrayList<UserInfo> list = searchUI.getInfoList();
		String msg = "<type>addRequest</type><sender>"+Loginner.loginner+"</sender>";
		if(list==null){
			//精确查找中的添加
			UserInfo user = searchUI.getDetectedUser();
			if(user!=null){
				if(isExist(user.getUserName())){
					JOptionPane.showMessageDialog(null, "该用户已经是您的好友！！！");
					return;
				}else{
					msg += "<destination>"+user.getUserName()+"</destination>";
				}
			}
		}else{
			//看谁在线的添加
			int selection = searchUI.getSelection();
			if(selection==-1){
				//没有选中任何对象
				JOptionPane.showMessageDialog(null, "请先选择您要添加的好友！");
				return;
			}else{
				UserInfo user = list.get(selection);
				if(user!=null){
					if(isExist(user.getUserName())){
						JOptionPane.showMessageDialog(null, "该用户已经是您的好友！！！");
						return;
					}else{
						if(user.getUserName().equals(Loginner.loginner)){
							JOptionPane.showMessageDialog(null, "孩子，你都孤独成这样了吖......");
							return;
						}else{
							msg += "<destination>"+user.getUserName()+"</destination>";
						}
					}
				}
			}
		}
		try{
			Message.sendMsg(msg, ManClient.client.getOutputStream());
			JOptionPane.showMessageDialog(null, "已向对方发送请求，请等待回应.....");
		}catch(Exception ef){
			ef.printStackTrace();
		}
	}
	
	private boolean isExist(String userName){
		for(UserInfo user : FriendList.friendList){
			if(user.getUserName().equals(userName)){
				return true;
			}
		}
		return false;
	}
}
