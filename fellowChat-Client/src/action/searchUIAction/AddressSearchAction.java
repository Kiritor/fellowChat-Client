package action.searchUIAction;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import util.tools.Loginner;
import util.tools.Message;

import allUI.SearchUI;

import clientBase.ManClient;

public class AddressSearchAction extends MouseAdapter{

	private SearchUI searchUI;
	
	public AddressSearchAction(SearchUI searchUI){
		this.searchUI = searchUI;
	}
	
	public void mouseReleased(MouseEvent e){
		String userName = searchUI.getAddress();
		System.out.println("得到的地址信息是："+userName);
		if(userName.length()<3||userName.length()>16||userName.contains(" ")){
			JOptionPane.showMessageDialog(null, "请输入合法地址！！！");
		}else{
			
			String searchMsg = "<type>addressSearch</type><sender>"+Loginner.loginner+"</sender><userName>"+userName+"</userName>";
			System.out.println("发送的信息是：："+searchMsg+userName);
			try{
				Message.sendMsg(searchMsg, ManClient.client.getOutputStream());
			}catch(Exception ef){
				ef.printStackTrace();
				JOptionPane.showMessageDialog(null, "信息提交失败，请检查您的网络连接！！！");
			}
		}
	}
}
