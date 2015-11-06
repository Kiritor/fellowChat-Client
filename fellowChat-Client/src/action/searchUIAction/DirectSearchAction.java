package action.searchUIAction;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import util.tools.Loginner;
import util.tools.Message;

import allUI.SearchUI;

import clientBase.ManClient;

public class DirectSearchAction extends MouseAdapter{

	private SearchUI searchUI;
	
	public DirectSearchAction(SearchUI searchUI){
		this.searchUI = searchUI;
	}
	
	public void mouseReleased(MouseEvent e){
		String userName = searchUI.getUserName();
		if(userName.length()<3||userName.length()>16||userName.contains(" ")){
			JOptionPane.showMessageDialog(null, "请输入合法帐号！！！");
		}else{
			String searchMsg = "<type>directSearch</type><sender>"+Loginner.loginner+"</sender><userName>"+userName+"</userName>";
			try{
				Message.sendMsg(searchMsg, ManClient.client.getOutputStream());
			}catch(Exception ef){
				ef.printStackTrace();
				JOptionPane.showMessageDialog(null, "信息提交失败，请检查您的网络连接！！！");
			}
		}
	}
}
