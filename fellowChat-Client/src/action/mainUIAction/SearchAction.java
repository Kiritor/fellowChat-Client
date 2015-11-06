package action.mainUIAction;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import util.pojo.UserInfo;
import util.tools.FriendList;

public class SearchAction extends MouseAdapter{
	
	private JTextField text;
	
	public SearchAction(JTextField text){
		this.text = text;
	}

	public void mouseReleased(MouseEvent e){
		String userName = text.getText();
		if(userName!=null&&userName.length()>0){
			for(UserInfo user : FriendList.friendList){
				if(user.getUserName().equals(userName)){
					OpenChatUIAction open = new OpenChatUIAction();
					open.openChatUI(userName);
					return;
				}
			}
			JOptionPane.showMessageDialog(null, "��û��һ���� "+userName+" �ĺ��ѣ�");
		}else{
			JOptionPane.showMessageDialog(null, "������Ϣ����Ϊ�գ�����");
		}
	}
}
