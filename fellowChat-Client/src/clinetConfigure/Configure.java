package clinetConfigure;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.jtattoo.plaf.mcwin.McWinLookAndFeel;

import util.tools.Tools;

public class Configure {

	private JFrame frame;
	private JLabel ipLabel;
	private JTextField ipText;
	private JLabel portLabel;
	private JTextField portText;
	private JButton button;
	
	public void showUI(){
		frame = new JFrame("乡友配置");
		frame.setLayout(new FlowLayout());
		
		String[] data = Tools.getConfig();
		
		ipLabel = new JLabel("服务器ip:");
		frame.add(ipLabel);
		ipText = new JTextField(15);
		if(data!=null&&data[0]!=null&&data[0].length()>0){
			ipText.setText(data[0]);
		}
		frame.add(ipText);
		portLabel = new JLabel("服务器端口:");
		frame.add(portLabel);
		portText = new JTextField(5);
		if(data!=null&&data[1]!=null&&data[1].length()>0){
			portText.setText(data[1]);
		}
		frame.add(portText);
		button = new JButton("确认");
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String ip = ipText.getText();
				String port = portText.getText();
				if(ip!=null&&port!=null&&ip.length()>4&&port.length()>0){
					ExecuteCfg execute = new ExecuteCfg(ip,port);
					execute.start();
				}else{
					JOptionPane.showMessageDialog(null, "您的输入有误！！！");
				}
			}
		});
		frame.add(button);
		
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(3);
	}
	
	private class ExecuteCfg extends Thread{
		private String ip;
		private String port;
		private File file = new File("manConfigure.cfg");
		
		public ExecuteCfg(String ip,String port){
			this.ip = ip;
			this.port = port;
		}
		
		public void run(){
			try{
				FileOutputStream fout = new FileOutputStream(file);
				BufferedOutputStream bout = new BufferedOutputStream(fout);
				String str = ip+" "+port;
				bout.write(str.getBytes());
				bout.flush();
				bout.close();
				
				JOptionPane.showMessageDialog(null, "配置成功！！！");
				frame.dispose();
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String args[]){
		JFrame.setDefaultLookAndFeelDecorated(true);

		 try {

		 UIManager.setLookAndFeel(new McWinLookAndFeel());

		 } catch (UnsupportedLookAndFeelException e1) {

		 // TODO Auto-generated catch block

		 e1.printStackTrace();

		 }
		Configure con = new Configure();
		con.showUI();
	}
}
