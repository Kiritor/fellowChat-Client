package clientBase;

import java.net.Socket;

import javax.swing.JOptionPane;

import util.runninglog.RunningLog;
import util.tools.Tools;
import allUI.WaitingUI;
import clinetConfigure.Configure;

public class ManClient {
	public static Socket client;
	public static void startClient(String ip, int port) {
		WaitingUI waiting = new WaitingUI();
		waiting.showUI();
		try {
			client = new Socket(ip, port);
			RunningLog.record("�ͻ��������ɹ�������");// ��־��¼
			waiting.closeUI();// �ر�������ʾ����
			ReceiveControl rec = new ReceiveControl(ip, port);
			rec.start();
			RunningLog.record("�����߳������ɹ�����"); 
		} catch (Exception e) {
			RunningLog.record("*****���󣺿ͻ�������ʱ��������");
			JOptionPane.showMessageDialog(null, "���ӳ�ʱ");
			waiting.closeUI();
		}

	}

	public static void main(String args[]) {
		String[] data = Tools.getConfig();/* ע���߼� */
		if (data != null) {
			if (data[0] != null && data[0].length() > 0 && data[1] != null
					&& data[1].length() > 0) {
				startClient(data[0], Integer.parseInt(data[1]));
			} else {
				JOptionPane.showMessageDialog(null, "����������Ϣ����ȷ������");
				Configure con = new Configure();
				con.showUI();
			}
		} else {
			JOptionPane.showMessageDialog(null, "����û�ж����ѽ������ã�����");
			Configure con = new Configure();
			con.showUI();
		}
	}
}
