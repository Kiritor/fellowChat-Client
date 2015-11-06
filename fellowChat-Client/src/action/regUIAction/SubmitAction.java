package action.regUIAction;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import util.tools.Address;
import util.tools.Message;

import allUI.RegUI;

import clientBase.ManClient;

public class SubmitAction extends MouseAdapter{

	private RegUI reg;
	
	public SubmitAction(RegUI reg){
		this.reg = reg;
	}
	
	public void mouseReleased(MouseEvent e){
		if(reg.isReady()){
			String userName = reg.getUserName();
			String userPwd = reg.getUserPwd();
			String userSex = reg.getUserSex();
			String userAge = reg.getUserAge();
			String userImage = reg.getUserImage();
		   if(reg.isFlags())
		   {
			   /*在这里得到地址信息一并写入数据库中*/
				String city = reg.getCityString();
				String area = reg.getAreaString();
				int id = reg.getId()+1;
				Address.add(id, city, area);
				System.out.println("地址信息录入成功");
		   }
			
			
		
			String regMsg = "<type>reg</type><userName>"+userName+"</userName><userPwd>"+userPwd+"</userPwd><userSex>"+userSex+"</userSex><userAge>"+userAge+"</userAge><userImage>"+userImage+"</userImage>";
			try{
				reg.waiting();
				Message.sendMsg(regMsg, ManClient.client.getOutputStream());
			}catch(Exception ef){
				ef.printStackTrace();
			}
		}else{
			JOptionPane.showMessageDialog(null, "您的信息还不完整，无法提交！！！");
		}
	}
}
