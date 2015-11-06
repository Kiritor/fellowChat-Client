package action.chatUIAction;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import util.tools.Tools;
import util.tools.Loginner;
import util.tools.Message;
import util.tools.TextStyle;
import util.tools.UIMap;
import clientBase.ManClient;

public class SendMsgButton extends MouseAdapter implements KeyListener {
	private JTextPane area1;
	private JTextPane area2;
	private String destination;
	private boolean shift = false;

	public SendMsgButton(JTextPane area1, JTextPane area2, String destination) {
		this.area1 = area1;
		this.area2 = area2;
		this.destination = destination;
	}

	private void sendMsg() {
		SendMsg send = new SendMsg();
		send.start();
	}

	public void mouseReleased(MouseEvent e) {
		sendMsg();
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode()==17) {
			shift = true;
		}

		if (shift && e.getKeyCode() == 10) {
			sendMsg();
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyLocation() == KeyEvent.CTRL_MASK) {
			shift = false;
		}
	}

	public void keyTyped(KeyEvent e) {

	}
	
	private class SendMsg extends Thread{
		public void run(){
			String msg = area2.getText();
			if (msg.length() > 0) {
				String str ="            "+ "<type>chat</type><sender>" + Loginner.loginner
						+ "</sender><destination>" + destination
						+ "</destination><fontFamily>" + TextStyle.fontFamily
						+ "</fontFamily><fontSize>" + TextStyle.fontSize
						+ "</fontSize><content>" + msg + "</content>";
				System.out.println("聊天的内容是："+str);
				try {
					Message.sendMsg(str, ManClient.client.getOutputStream());
					area2.setText("");
					msg = Loginner.loginner + " " + Tools.getDate() + "\r\n"
							+ msg;
					UIMap.chatUIMap.get(destination).appendMsg(
							TextStyle.fontFamily, TextStyle.fontSize, msg,true,"");
				} catch (Exception ef) {
					ef.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(null, "不能发送空信息！！！");
			}
		}
	}
}
