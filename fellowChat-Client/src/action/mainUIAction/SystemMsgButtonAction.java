package action.mainUIAction;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import util.tools.Loginner;
import util.tools.Message;
import util.tools.MessageBox;
import util.tools.UIMap;

import clientBase.ManClient;

public class SystemMsgButtonAction extends MouseAdapter{

	public void mouseReleased(MouseEvent e){
		String message = MessageBox.getSystemMsg();
		if(MessageBox.isSystemMsgEmpty()){
			if(UIMap.temporaryStorage.get("flash")!=null){
				((FlashAction)UIMap.temporaryStorage.get("flash")).stopFlashing();
				UIMap.temporaryStorage.remove("flash");
			}
		}
		if(message==null){
			JOptionPane.showMessageDialog(null, "����ʱû��ϵͳ��Ϣ������");
		}else{
			StringTokenizer token = new StringTokenizer(message,",");
			String type = token.nextToken();
			if(type.equals("addRequest")){
				String userName = token.nextToken();
				String tip = userName+"�������Ϊ���ѣ�ͬ��ô��";
				String msg = null;
				int option = JOptionPane.showOptionDialog(null, tip,"�����������", JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE, null, null, null);
				if(option==JOptionPane.YES_OPTION){
					msg = "<type>addResponse</type><sender>"+Loginner.loginner+"</sender><destination>"+userName+"</destination><resp>yes</resp>";
					try{
						Message.sendMsg(msg, ManClient.client.getOutputStream());
					}catch(Exception ef){
						ef.printStackTrace();
					}
				}else{
					msg = "<type>addResponse</type><sender>"+Loginner.loginner+"</sender><destination>"+userName+"</destination><resp>no</resp>";
					try{
						Message.sendMsg(msg, ManClient.client.getOutputStream());
					}catch(Exception ef){
						ef.printStackTrace();
					}
				}
			}else{
				String userName = token.nextToken();
				String resp = token.nextToken();
				if(resp.equals("yes")){
					JOptionPane.showMessageDialog(null, userName+"ͬ�������Ϊ���ѣ�");
				}else{
					JOptionPane.showMessageDialog(null, userName+"�ܾ������Ϊ���ѣ�");
				}
			}
		}
	}
}
