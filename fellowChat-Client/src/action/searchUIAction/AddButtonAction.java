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
			//��ȷ�����е����
			UserInfo user = searchUI.getDetectedUser();
			if(user!=null){
				if(isExist(user.getUserName())){
					JOptionPane.showMessageDialog(null, "���û��Ѿ������ĺ��ѣ�����");
					return;
				}else{
					msg += "<destination>"+user.getUserName()+"</destination>";
				}
			}
		}else{
			//��˭���ߵ����
			int selection = searchUI.getSelection();
			if(selection==-1){
				//û��ѡ���κζ���
				JOptionPane.showMessageDialog(null, "����ѡ����Ҫ��ӵĺ��ѣ�");
				return;
			}else{
				UserInfo user = list.get(selection);
				if(user!=null){
					if(isExist(user.getUserName())){
						JOptionPane.showMessageDialog(null, "���û��Ѿ������ĺ��ѣ�����");
						return;
					}else{
						if(user.getUserName().equals(Loginner.loginner)){
							JOptionPane.showMessageDialog(null, "���ӣ��㶼�¶���������߹......");
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
			JOptionPane.showMessageDialog(null, "����Է�����������ȴ���Ӧ.....");
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
