package action.mainUIAction;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import util.tools.ActiveLabelTool;
import util.tools.FriendList;
import util.tools.MessageBox;
import util.tools.UIMap;
import allUI.ChatUI;
import allUI.MainUI;

public class SpecificFlagAction extends MouseAdapter implements ActionListener{

	
	 private String destination;
	    private long click = 0;
	    
	    public void mouseReleased(MouseEvent e){
	    	if(e.getButton()==1){
	    		long clickII = System.currentTimeMillis();
	        	if(click==0){
	        		click = clickII;
	        	}else{
	        		if(clickII-click>500){
	        			click = clickII;
	        		}else{
	        			//双击事件触发
	        			JLabel label = (JLabel)e.getSource();
	        			 // new ToolTip(100,100).setToolTip(new ImageIcon("images/biaozhi.gif"),"“CoolBabY，QQ：291904818，只有交流才能进步。博客：http://blog.sina.com.cn/coolbabybing");
	        			destination = label.getText();
	        			openChatUI(destination);
	        			
	        		}
	        	}
	    	}
	    }

		public void actionPerformed(ActionEvent e) {
			destination = ((JPopupMenu)((JMenuItem)e.getSource()).getParent()).getLabel();
			openChatUI(destination);
		}
		
		public void openChatUI(String userName){
			OpenUI o = new OpenUI();
			o.setUserName(userName);
			o.start();
		}
		
		private class OpenUI extends Thread{
			private String userName;
			
			public void setUserName(String userName){
				this.userName = userName;
			}
			
			public void run(){
				ChatUI chatUI = UIMap.chatUIMap.get(userName);
				if(chatUI!=null){
					chatUI.area2RequestFocus();
				}else{
					//打开聊天界面
					chatUI = new ChatUI(userName);
					chatUI.showUI();
					//读取消息盒子里的信息
					ArrayList<String> msgList = MessageBox.msgBox.get(userName);
					if(msgList!=null){
						for(String msg : msgList){
							StringTokenizer token = new StringTokenizer(msg,"|#");
							chatUI.appendMsg(token.nextToken(),Integer.parseInt(token.nextToken()),token.nextToken(),true,"");
						}
						//读取完成后清空消息盒子
						MessageBox.msgBox.remove(userName);
					}
					UIMap.add(userName, chatUI);
					
					//如果抖动，停止抖动
					ShakeAction shake = ActiveLabelTool.shakingLabel.get(userName);
					if(shake!=null){
						shake.stopShake();
						ActiveLabelTool.remove(userName);
						//更新好友列表和主界面
						FriendList.removeFromShakingList(userName);
						((MainUI)UIMap.temporaryStorage.get("mainUI")).resetFriendPanel();
					}
				}
			}
		}


}
