package action.loginUIAction;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.Socket;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import util.tools.Message;


public class LoginAction extends MouseAdapter {
	
	private JTextField userName;
	private JPasswordField userPwd;
	private Socket client;
	
	public LoginAction(JTextField userName,JPasswordField userPwd,Socket client){
		this.userName = userName;
		this.userPwd = userPwd;
		this.client = client;
	}

	public void mouseReleased(MouseEvent e) {
		String name = userName.getText();
		String pwd = new String(userPwd.getPassword());
		//判断用户名密码是否为空
		if(name==null||pwd==null){
			JOptionPane.showMessageDialog(null, "用户名和密码不能为空！");
		}else{
			String loginMsg = "<type>login</type><userName>"+name+"</userName><userPwd>"+pwd+"</userPwd>";
			try{
				Message.sendMsg(loginMsg, client.getOutputStream());
			}catch(Exception ef){
				ef.printStackTrace();
			}
		}
	}

}
