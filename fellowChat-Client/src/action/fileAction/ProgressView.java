package action.fileAction;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import util.tools.UIMap;


public class ProgressView extends JPanel implements PropertyChangeListener{

	private JLabel fileIcon;
	private JLabel nameIcon;
	private JLabel openFile;
	private JLabel openDirector;
	private JPanel panel;
	private JProgressBar progressBar;
	
	private ImageIcon icon;
	
	private File file;
	private SwingWorker<Void,Void> progress;
	private String type;
	private String userName;
	
	public ProgressView(File file,SwingWorker<Void,Void> progress,String type,String userName){
		this.file = file;
		this.progress = progress;
		this.type = type;
		this.userName = userName;
		panel = this;
	}
	
	public String getUserName(){
		return userName;
	}
	
	public void initialize(){
		this.setPreferredSize(new Dimension(190,70));
		this.setLayout(null);
		this.setOpaque(false);
		//图标区
		if(type.equals("receive")){
			icon = new ImageIcon("image/receiveFile.jpg");
		}else{
			icon = new ImageIcon("image/sendFile.jpg");
		}
		fileIcon = new JLabel(icon);
		fileIcon.setBounds(2, 2, 29, 44);
		this.add(fileIcon);
		//文本区
		nameIcon = new JLabel(file.getName());
		nameIcon.setForeground(Color.red);
		nameIcon.setBounds(33, 2, 160, 44);
		this.add(nameIcon);
		//进度条
		progressBar = new JProgressBar(0,100);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		progressBar.setBorderPainted(false);
		progressBar.setBounds(3, 49, 180, 15);
		this.add(progressBar);
		//添加进度监听器兵启动progress
		progress.addPropertyChangeListener(this);
		progress.execute();
	}

	public void propertyChange(PropertyChangeEvent evt) {
		if("progress".equals(evt.getPropertyName())){
			int i = (Integer)evt.getNewValue();
			progressBar.setValue(i);
			if(i==100){
				transmitionDone();
			}
		}
	}
	
	public void transmitionDone(){
		if(type.equals("receive")){
			this.remove(progressBar);
			
			openFile = new JLabel("打开文件");
			openFile.setBounds(3, 50, 90, 15);
			this.add(openFile);
			openFile.addMouseListener(new MouseAdapter(){
				
				public void mouseEntered(MouseEvent e){
					openFile.setForeground(Color.red);
				}
				public void mouseExited(MouseEvent e){
					openFile.setForeground(Color.black);
				}
				public void mouseReleased(MouseEvent e){
					if(Desktop.isDesktopSupported()){
						Desktop desktop = Desktop.getDesktop();
						try{
							desktop.open(file);
							UIMap.chatUIMap.get(userName).removeFileView(panel);
						}catch(Exception ef){
							JOptionPane.showMessageDialog(null, "文件格式未知，无法打开！！！");
						}
					}
				}
			});
			
			openDirector = new JLabel("打开目录");
			openDirector.setBounds(93, 50, 90, 15);
			this.add(openDirector);
			openDirector.addMouseListener(new MouseAdapter(){
				public void mouseEntered(MouseEvent e){
					openDirector.setForeground(Color.red);
				}
				public void mouseExited(MouseEvent e){
					openDirector.setForeground(Color.black);
				}
				public void mouseReleased(MouseEvent e){
					File director = new File(file.getAbsolutePath().substring(0,file.getAbsolutePath().lastIndexOf("\\")));
					if(Desktop.isDesktopSupported()){
						Desktop desktop = Desktop.getDesktop();
						try{
							desktop.open(director);
						}catch(Exception ef){
							ef.printStackTrace();
						}
						UIMap.chatUIMap.get(userName).removeFileView(panel);
					}
				}
			});
		}else{
			UIMap.chatUIMap.get(userName).removeFileView(this);
		}
		this.updateUI();
	}
}
