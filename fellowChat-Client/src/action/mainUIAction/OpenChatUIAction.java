package action.mainUIAction;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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


public class OpenChatUIAction extends MouseAdapter implements ActionListener{
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
						String contentString;
						String familyString;
						int fontString;
					
						StringTokenizer token = new StringTokenizer(msg,"|#");
						familyString=token.nextToken();
						fontString=Integer.parseInt(token.nextToken());
						contentString=token.nextToken();
						
						/*这里必须要做处理否者会出现记录消息的重复记录，通过传参实现是否读入*/
						chatUI.appendMsg(familyString,fontString,contentString,true,"");
						/*通过这里写入消息文件中，信息的收集准备*/
						System.out.println(contentString);
						//HositoryMsg.record(contentString+"\r\n");
					}
					//读取完成后清空消息盒子,这里可以考虑一下是否可以将读取到的消息记录到指定的消息文件中
					//tips：文件中如何组织消息的内容，消息的分类信息，发送者（这是个问题啊）
					//感觉上还是使用数据库技术实现更好
					
					MessageBox.msgBox.remove(userName);
				}
				UIMap.add(userName, chatUI);
				//System.out.println("这个用户到底是谁？"+userName);
				
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
