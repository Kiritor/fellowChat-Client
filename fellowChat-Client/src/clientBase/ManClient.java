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
			RunningLog.record("客户端启动成功！！！");// 日志记录
			waiting.closeUI();// 关闭连接提示界面
			ReceiveControl rec = new ReceiveControl(ip, port);
			rec.start();
			RunningLog.record("接收线程启动成功！！"); 
		} catch (Exception e) {
			RunningLog.record("*****错误：客户端连接时出错！！！");
			JOptionPane.showMessageDialog(null, "连接超时");
			waiting.closeUI();
		}

	}

	public static void main(String args[]) {
		String[] data = Tools.getConfig();/* 注意逻辑 */
		if (data != null) {
			if (data[0] != null && data[0].length() > 0 && data[1] != null
					&& data[1].length() > 0) {
				startClient(data[0], Integer.parseInt(data[1]));
			} else {
				JOptionPane.showMessageDialog(null, "您的配置信息不正确！！！");
				Configure con = new Configure();
				con.showUI();
			}
		} else {
			JOptionPane.showMessageDialog(null, "您还没有对乡友进行配置！！！");
			Configure con = new Configure();
			con.showUI();
		}
	}
}
