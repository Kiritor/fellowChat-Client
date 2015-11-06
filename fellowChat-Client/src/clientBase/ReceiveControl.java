/*��Ϣ�Ŀ�����*/

package clientBase;

import java.awt.Point;
import java.io.File;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.media.CaptureDeviceInfo;
import javax.media.cdm.CaptureDeviceManager;
import javax.media.format.AudioFormat;
import javax.media.format.VideoFormat;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.jtattoo.plaf.mcwin.McWinLookAndFeel;

import util.hositoryMsg.FileHositoryMsg;
import util.hositoryMsg.HositoryMsg;
import util.pojo.UserInfo;
import util.runninglog.RunningLog;
import util.tools.ActiveLabelTool;
import util.tools.FileSearcher;
import util.tools.Tools;
import util.tools.FriendList;
import util.tools.Loginner;
import util.tools.MediaControlTools;
import util.tools.Message;
import util.tools.MessageBox;
import util.tools.UIMap;

import action.fileAction.ProgressView;
import action.fileAction.ReceiveFile;
import action.fileAction.ReceiveProgress;
import action.fileAction.SendFile;
import action.fileAction.SendProgress;
import action.loginUIAction.LoginAction;
import action.mainUIAction.FlashAction;
import action.mainUIAction.OpenChatUIAction;
import action.mainUIAction.ShakeAction;
import action.mediaAction.ReceiveMedia;
import action.mediaAction.SendMedia;
import action.remoteControlAction.Receive;
import action.remoteControlAction.Send;
import allUI.ChatUI;
import allUI.LoginUI;
import allUI.MainUI;
import allUI.RegUI;
import allUI.RemoteControlUI;
import allUI.SearchUI;
import allUI.ViewUI;

public class ReceiveControl extends Thread {
	
	private String ip;
	private int port;
	private Point point = new Point(290, 500);
	
	private boolean flag = true;
	
	private OpenChatUIAction open = new OpenChatUIAction();

	private ReceiveFile receiveFile = null;
	private LoginUI loginUI = null;
	
	public ReceiveControl(String ip,int port){
		this.ip = ip;
		this.port = port;
	}

	public void run() {
		receiveFunction();
	}
	
	private void receiveFunction(){
		if(UIMap.temporaryStorage.get("mainUI")==null){
			// ������¼����,��Ӻü�����
			JFrame.setDefaultLookAndFeelDecorated(true);

			 try {

			 UIManager.setLookAndFeel(new McWinLookAndFeel());

			 } catch (UnsupportedLookAndFeelException e1) {

			 // TODO Auto-generated catch block

			 e1.printStackTrace();

			 }
			
			loginUI = new LoginUI();
			loginUI.showUI();
			UIMap.storeObj("loginUI", loginUI);
			LoginAction loginAction = new LoginAction(loginUI.getUserName(),
					loginUI.getUserPwd(), ManClient.client);
			loginUI.setLoginListener(loginAction);
		}

		// ��ʼ������Ϣ�����봦�����
		try {
			while (flag) {
				String message = Message.readString(ManClient.client
						.getInputStream());
				// ������Ϣ���ͣ�������������Ӧ����
				String type = Message.getXMLValue("type", message);
				System.out.println("�����������ǣ�"+type);
				try {
					/**
					 * �Ե�¼�ظ��Ĳ���
					 */
					if (type.equals("loginresp")) {
						String resp = Message.getXMLValue("resp", message);

						if ("yes".equals(resp)) {
							// ��¼�ɹ�
							// �رյ�¼����
							if(UIMap.temporaryStorage.get("loginUI")!=null){
								loginUI.closeWindow();
								UIMap.removeObj("loginUI");
							}
							// ��¼��ǰ��¼����Ϣ
							String loginner = Message.getXMLValue("loginner",message);
							Loginner.setLoginner(loginner);
							String loginPwd = Message.getXMLValue("loginPwd", message);
							Loginner.setLoginPwd(loginPwd);
							// �õ������б���������
							ObjectInputStream in = new ObjectInputStream(
									ManClient.client.getInputStream());
							ArrayList<UserInfo> friendList = (ArrayList<UserInfo>) Message
									.getObject(in);
							FriendList.setFriendList(friendList);
							MainUI mainUI = (MainUI)UIMap.temporaryStorage.get("mainUI");
							if(mainUI==null){
								mainUI = new MainUI();
								UIMap.storeObj("mainUI", mainUI);
							}else{
								mainUI.resetFriendPanel();
							}
							mainUI.showUI(point);
						}
						if(resp.equals("no")){
							/**
							 * ��¼ʧ��ִ�еĲ���
							 */
							JOptionPane.showMessageDialog(null,
									"�û���������������������룡");
							loginUI.setFocusInUserName();
						}
						if(resp.equals("online")){
							/**
							 * �ѵ�¼
							 */
							JOptionPane.showMessageDialog(null, "���û������ߣ���ֹ�ظ���¼������");
							loginUI.setFocusInUserName();
						}
					}
					/**
					 * ��ע��Ӧ��Ĳ���
					 */
					if (type.equals("regResp")) {
						String regResp = Message.getXMLValue("resp", message);
						if ("yes".equals(regResp)) {
							// ע��ɹ�
							JOptionPane.showMessageDialog(null,
									"��ϲ��ע�����ųɹ�����ǰ����¼ҳ���¼������");
							RegUI reg = (RegUI) UIMap.temporaryStorage
									.get("reg");
							if (reg != null) {
								reg.closeUI();
							}
						} else {
							// ע��ʧ��
							String reason = Message.getXMLValue("reason",
									message);
							if ("writeError".equals(reason)) {
								JOptionPane.showMessageDialog(null,
										"ע��ʧ�ܣ��볢�������ύע����Ϣ������");
							} else {
								JOptionPane.showMessageDialog(null,
										"���д��û���,�����!!!");
							}
							RegUI reg = (RegUI) UIMap.temporaryStorage
									.get("reg");
							if (reg != null) {
								reg.resubmitOpration();
							}
						}
					}
					/**
					 * ��������Ϣ�Ĳ���
					 */
					if (type.equals("chat")) {
						String sender = Message.getXMLValue("sender", message);
						String destination=Message.getXMLValue("destination", message);
						System.out.println(destination);
						String fontFamily = Message.getXMLValue("fontFamily", message);
						int fontSize = Integer.parseInt(Message.getXMLValue("fontSize", message));
						String content = Message
								.getXMLValue("content", message);
						content = sender + " " + Tools.getDate() + "\r\n"
								+ content;

						// �ж����촰����û�д�
						ChatUI chatUI = UIMap.chatUIMap.get(sender);
						System.out.println(chatUI);
						if (chatUI != null) {
							// ����Ǵ򿪵ģ�ֱ��append
						//	chatUI.appendMsg(fontFamily,fontSize,content,false,sender);
							/*����ͨ�����ѵ����ֲ�һ��������ͬ���ļ�*/
							if(FileSearcher.fileExite(sender))
							{
								//���ڸ��ļ�ֱ�����ļ�ͷ�����뼴��
								chatUI.appendMsg(fontFamily,fontSize,content,false,sender);
								FileHositoryMsg.record(content, sender);
								FileHositoryMsg.record(content, destination);
							}
							else {
								//�����ڸ��ļ����½��ļ��ٴ�ͷ������
								chatUI.appendMsg(fontFamily,fontSize,content,false,sender);
								FileHositoryMsg.record(content, destination);
								FileHositoryMsg.record(content, sender);
							}
							
							
							
						} else {
							// ������Ǵ򿪵ģ��ȸ��º����б��������
							
							FriendList.joinShakingList(sender);
							((MainUI)UIMap.temporaryStorage.get("mainUI")).resetFriendPanel();
							//������Ϣ���ӣ���ͷ��֮ǰû�ж����򶶶�
							System.out.println(content+sender);
							//chatUI.appendMsg(fontFamily,fontSize,content,true,sender);
							FileHositoryMsg.record(content,sender);
							FileHositoryMsg.record(content, destination);
							content = fontFamily+"|#"+fontSize+"|#"+content;
							MessageBox.storeMsg(sender, content);
							ShakeAction shake = ActiveLabelTool.shakingLabel
									.get(sender);
							if (shake == null) {
								shake = new ShakeAction(sender);
								shake.start();
								ActiveLabelTool.add(sender, shake);
							}
						}
					}
					/**
					 * ����Ƶ����Ĳ���
					 */
					if (type.equals("viewRequest")) {
						String sender = Message.getXMLValue("sender", message);
						int option = JOptionPane.showOptionDialog(null, sender
								+ "���������Ƶ���죬ͬ��ô��", "��Ƶ����", 0, 1, null, null,
								null);
						if (option == 0) {
							// ȷ��
							// ���������ߵ�IP��ַ
							String senderIP = Message
									.getXMLValue("ip", message);
							// ���ͻ�ִ
							String viewResp = "<type>viewResponse</type><sender>"
									+ Loginner.loginner
									+ "</sender><destination>"
									+ sender
									+ "</destination><resp>yes</resp>";
							Message.sendMsg(viewResp, ManClient.client
									.getOutputStream());
							// ��ʾ����֪ͨ
							open.openChatUI(sender);
							ChatUI chatUI = UIMap.chatUIMap.get(sender);
							if (chatUI != null) {
								chatUI.appendMsg(null,12,"����ͬ��Է�����Ƶ������Ƶ���������......",true,sender);
							} else {
								chatUI = new ChatUI(sender);
								chatUI.showUI();
								chatUI.appendMsg(null,12,"����ͬ��Է�����Ƶ������Ƶ���������......",true,sender);
								UIMap.add(sender, chatUI);
							}
							// ������Ƶ���棨δ��ʾ���ȴ�Player���������
							ViewUI viewUI = new ViewUI(sender);
							viewUI.start();
							UIMap.add(sender, viewUI);
							// ������Ƶ�ķ���������߳�
							SendMedia sendVideo = null;
							SendMedia sendAudio = null;
							ReceiveMedia receiveMedia = null;
							// �������Ͳ���
							Vector video = CaptureDeviceManager
									.getDeviceList(new VideoFormat(null));
							Vector audio = CaptureDeviceManager
									.getDeviceList(new AudioFormat(
											AudioFormat.LINEAR, 44100, 16, 2));
							if (video!= null&&video.size()>0) {
								sendVideo = new SendMedia(
										(CaptureDeviceInfo) video.get(0),
										senderIP, "8810", null, sender);
								if (sendVideo.start() != null) {
									RunningLog.record("*****������Ƶ������������");
								}
							} else {
								// ֪ͨ�û�û����Ƶ�豸
								UIMap.chatUIMap.get(sender).appendMsg(null,12,
										"******����û�м�⵽������Ƶ�豸������",true,sender);
							}
							if (audio != null&&audio.size()>0) {
								sendAudio = new SendMedia(
										(CaptureDeviceInfo) audio.get(0),
										senderIP, "8820", null, sender);
								if (sendAudio.start() != null) {
									RunningLog.record("*****������Ƶ������������");
								}
							} else {
								// ֪ͨ�û�û����Ƶ�豸
								UIMap.chatUIMap.get(sender).appendMsg(null,12,
										"******����û�м�⵽������Ƶ�豸������",true,sender);
							}
							// �������ղ���
							String[] session = new String[2];
							session[0] = senderIP + "/8830/1";
							session[1] = senderIP + "/8840/1";
							receiveMedia = new ReceiveMedia(session, sender);
							if (!receiveMedia.start()) {
								RunningLog.record("******������Ƶ���ճ�������");
							}
							// ��¼���Ͻ����Ա����ͳһ�Ĺرղ���
							MediaControlTools.storeMediaSession(sender,
									sendVideo, sendAudio, receiveMedia);

						} else {
							// ȡ��
							String viewResp = "<type>viewResponse</type><sender>"
									+ Loginner.loginner
									+ "</sender><destination>"
									+ sender
									+ "</destination><resp>no</resp>";
							Message.sendMsg(viewResp, ManClient.client
									.getOutputStream());
							if (UIMap.chatUIMap.get(sender) != null) {
								UIMap.chatUIMap.get(sender).appendMsg(null,12,
										"���ܾ��˶Է�����Ƶ���󣡣���",true,sender);
							}
						}
					}
					/**
					 * ����ƵӦ��Ĳ���
					 */
					if (type.equals("viewResponse")) {
						String viewResp = Message.getXMLValue("resp", message);
						String sender = Message.getXMLValue("sender", message);
						if (viewResp.equals("notOnline")) {
							JOptionPane.showMessageDialog(null, "�Է�û�����ߣ�");
							UIMap.chatUIMap.get(sender).appendMsg(null,12,
									"�Է��������������ܽ�����Ƶ���죡����",true,sender);
						}
						if (viewResp.equals("yes")) {
							// �������Է���IP
							String senderIP = Message
									.getXMLValue("ip", message);
							// ��ʾ֪ͨ
							UIMap.chatUIMap.get(sender).appendMsg(null,12,
									"�Է�ͬ����������Ƶ������Ƶ���������.......",true,sender);
							// ������Ƶ���棨δ��ʾ���ȴ�Player���������
							ViewUI viewUI = new ViewUI(sender);
							viewUI.start();
							UIMap.add(sender, viewUI);
							// ������Ƶ���ͺͽ����߳�
							SendMedia sendVideo = null;
							SendMedia sendAudio = null;
							ReceiveMedia receiveMedia = null;
							// �������Ͳ���
							Vector video = CaptureDeviceManager
									.getDeviceList(new VideoFormat(null));
							Vector audio = CaptureDeviceManager
									.getDeviceList(new AudioFormat(
											AudioFormat.LINEAR, 44100, 16, 2));
							if (video!= null&&video.size()>0) {
								sendVideo = new SendMedia(
										(CaptureDeviceInfo) video.get(0),
										senderIP, "8830", null, sender);
								if (sendVideo.start() != null) {
									RunningLog.record("******������Ƶ������������");
								}
							} else {
								// ֪ͨ�û�û����Ƶ�豸
								UIMap.chatUIMap.get(sender).appendMsg(null,12,
										"*****�����Ҳ���������Ƶ�豸������",true,sender);
							}
							if (audio != null&&audio.size()>0) {
								sendAudio = new SendMedia(
										(CaptureDeviceInfo) audio.get(0),
										senderIP, "8840", null, sender);
								if (sendAudio.start() != null) {
									RunningLog.record("******������Ƶ������������");
								}
							} else {
								// ֪ͨ�û�û����Ƶ�豸
								UIMap.chatUIMap.get(sender).appendMsg(null,12,
										"*****�����Ҳ���������Ƶ�豸������",true,sender);
							}
							// �������ղ���
							String[] session = new String[2];
							session[0] = senderIP + "/8810/1";
							session[1] = senderIP + "/8820/1";
							receiveMedia = new ReceiveMedia(session, sender);
							if (!receiveMedia.start()) {
								RunningLog.record("******������Ƶ���ճ�������");
							}
							// ��¼���Ͻ����Ա����ͳһ�رղ���
							MediaControlTools.storeMediaSession(sender,
									sendVideo, sendAudio, receiveMedia);
						}
						if (viewResp.equals("no")) {
							JOptionPane.showMessageDialog(null, "�Է��ܾ���������Ƶ����");
						}
					}
					/**
					 * ����Ƶ�Ͽ�֪ͨ�Ĳ���
					 */
					if (type.equals("viewBreak")) {
						// ��������Դ�û�
						String sender = Message.getXMLValue("sender", message);
						// �ر���Ƶ����
						UIMap.viewUIMap.get(sender).closeViewUI();
						// ��ViewUIMap���Ƴ���Ӧ��¼
						UIMap.removeViewUI(sender);
						// �ر���Ƶ�Ľ��պͷ��ͽ���
						MediaControlTools.videoFinish(sender);
						// ��ʾ֪ͨ
						if (UIMap.chatUIMap.get(sender) != null) {
							UIMap.chatUIMap.get(sender).appendMsg(null,12,
									"��Ƶ��ȡ��..........",true,sender);
						}
					}
					/**
					 * ���ļ���������Ĳ���
					 */
					if (type.equals("fileRequest")) {
						// ������Ϣ
						String sender = Message.getXMLValue("sender", message);
						String fileName = Message.getXMLValue("fileName",
								message);
						String fileSize = Message.getXMLValue("fileSize",
								message);
						String filePath = Message.getXMLValue("filePath",
								message);
						// �����ļ������������ʾ��
						String tip = sender + " �������������ļ�:\r\n�ļ���: " + fileName
								+ "\r\n��С: " + fileSize;
						int option = JOptionPane.showOptionDialog(null, tip,
								"�ļ���������", JOptionPane.YES_NO_OPTION,
								JOptionPane.INFORMATION_MESSAGE, null, null,
								null);
						if (option == JOptionPane.YES_OPTION) {
							// ȷ�����յĲ���
							open.openChatUI(sender);
							JFileChooser fileChooser = new JFileChooser();
							int reOption = fileChooser.showSaveDialog(null);
							if (reOption == JFileChooser.APPROVE_OPTION) {
								File file = fileChooser.getSelectedFile();
								// �õ��ļ���С
								long size = (long) ((Float.parseFloat(fileSize
										.substring(0, fileSize.length() - 2)) * 1000) - 4);
								ReceiveProgress rpro = new ReceiveProgress(
										file, size);
								// ��ʾ�ļ����ս���
								ProgressView pview = new ProgressView(file,
										rpro, "receive", sender);
								pview.initialize();
								UIMap.chatUIMap.get(sender).addFileView(pview);
								// �����ļ������߳�
								if (!MediaControlTools.isFileServerOn()) {
									receiveFile = new ReceiveFile(file, sender);
									receiveFile.start();
									MediaControlTools.fileServerOn();
								} else {
									receiveFile.setFile(file);
								}
								// ����ͬ����յ�Ӧ��
								String fileResp = "<type>fileResponse</type><sender>"
										+ Loginner.loginner
										+ "</sender><destination>"
										+ sender
										+ "</destination><resp>yes</resp><filePath>"
										+ filePath + "</filePath>";
								Message.sendMsg(fileResp, ManClient.client
										.getOutputStream());
								// ��ʾ������¼
								UIMap.chatUIMap.get(sender).appendMsg(null,12,
										"��ͬ������ļ�........",true,sender);
							} else {
								// ���Ͳ����յ�Ӧ��
								String fileResp = "<type>fileResponse</type><sender>"
										+ Loginner.loginner
										+ "</sender><destination>"
										+ sender
										+ "</destination><resp>no</resp>";
								Message.sendMsg(fileResp, ManClient.client
										.getOutputStream());
								UIMap.chatUIMap.get(sender).appendMsg(null,12,
										"���ܾ������ļ�........",true,sender);
							}
						} else {
							// �����յĲ���
							String fileResp = "<type>fileResponse</type><sender>"
									+ Loginner.loginner
									+ "</sender><destination>"
									+ sender
									+ "</destination><resp>no</resp>";
							Message.sendMsg(fileResp, ManClient.client
									.getOutputStream());
							if (UIMap.chatUIMap.get(sender) != null) {
								UIMap.chatUIMap.get(sender).appendMsg(null,12,
										"���ܾ������ļ�........",true,sender);
							}
						}
					}
					/**
					 * ���ļ�����Ӧ��Ĳ���
					 */
					if (type.equals("fileResponse")) {
						String sender = Message.getXMLValue("sender", message);
						String fileResp = Message.getXMLValue("resp", message);
						if (fileResp.equals("yes")) {
							// �Է�ͬ�����
							UIMap.chatUIMap.get(sender).appendMsg(null,12,
									"�Է�ͬ������ļ�.........",true,sender);

							String ip = Message.getXMLValue("ip", message);
							String filePath = Message.getXMLValue("filePath",
									message);
							File file = new File(filePath);
							// ���������߳�
							SendFile sendFile = new SendFile(sender, ip, file);
							sendFile.start();
							// ��ʾ�ļ����ͽ���
							SendProgress spro = new SendProgress(sendFile,
									sendFile.getFileSize());
							ProgressView pview = new ProgressView(file, spro,
									"send", sender);
							pview.initialize();
							UIMap.chatUIMap.get(sender).addFileView(pview);

						}
						if (fileResp.equals("no")) {
							// �Է��ܾ�����
							JOptionPane
									.showMessageDialog(null, "�Է��ܾ������ļ�.....");
							if (UIMap.chatUIMap.get(sender) != null) {
								UIMap.chatUIMap.get(sender).appendMsg(null,12,
										"�Է��ܾ������ļ�..........",true,sender);
							}
						}
						if (fileResp.equals("notOnline")) {
							// �Է�������
							JOptionPane.showMessageDialog(null, "�Է������ߣ�");
						}
					}

					/**
					 * ��Զ�̼������Ĳ���
					 */
					if (type.equals("remoteRequest")) {
						String sender = Message.getXMLValue("sender", message);
						String tip = sender + " ��������Զ��Э����ͬ����";
						int option = JOptionPane.showOptionDialog(null, tip,
								"�ļ���������", JOptionPane.YES_NO_OPTION,
								JOptionPane.INFORMATION_MESSAGE, null, null,
								null);
						if (option == JOptionPane.YES_OPTION) {
							// ͬ����
							open.openChatUI(sender);
							UIMap.chatUIMap.get(sender).appendMsg(null,12,"��ͬ���˶Է�������",true,sender);
							// �򿪼�ؽ���
							RemoteControlUI remote = new RemoteControlUI(sender);
							remote.showUI();
							UIMap.add(sender, remote);
							// �������շ�����
							Receive rec = new Receive(remote, sender);
							rec.start();
							// ����Ӧ��
							String remoteResp = "<type>remoteResponse</type><sender>"
									+ Loginner.loginner
									+ "</sender><destination>"
									+ sender
									+ "</destination><resp>yes</resp>";
							Message.sendMsg(remoteResp, ManClient.client
									.getOutputStream());
						} else {
							// ��ͬ����
							String remoteResp = "<type>remoteResponse</type><sender>"
									+ Loginner.loginner
									+ "</sender><destination>"
									+ sender
									+ "</destination><resp>no</resp>";
							Message.sendMsg(remoteResp, ManClient.client
									.getOutputStream());
							if (UIMap.chatUIMap.get(sender) != null) {
								UIMap.chatUIMap.get(sender).appendMsg(null,12,
										"��ȡ���˶Է�������......",true,sender);
							}
						}
					}

					/**
					 * ��Զ�̼��Ӧ��Ĳ���
					 */
					if (type.equals("remoteResponse")) {
						String sender = Message.getXMLValue("sender", message);
						String remoteResp = Message
								.getXMLValue("resp", message);
						if (remoteResp.equals("yes")) {
							// �Է�ͬ������
							UIMap.chatUIMap.get(sender).appendMsg(null,12,
									sender + " ͬ�����������󣬶Է���ȡ�ÿ���Ȩ��",true,sender);
							// �������Է�IP
							String senderIP = Message
									.getXMLValue("ip", message);
							// �������ݷ����߳�
							Send send = new Send(senderIP, sender);
							send.start();

							UIMap.chatUIMap.get(sender).switchRequestState();
							MediaControlTools.setRemoteControlState(true);
						}
						if (remoteResp.equals("no")) {
							// �Է��ܾ�����
							JOptionPane.showMessageDialog(null, sender
									+ " �ܾ�����������......");
							UIMap.chatUIMap.get(sender).switchRequestState();
						}
						if (remoteResp.equals("notOnline")) {
							// �Է�������
							JOptionPane.showMessageDialog(null, "�Է�������!!!");
							UIMap.chatUIMap.get(sender).switchRequestState();
						}
					}

					/**
					 * ��Զ�̼�ضϿ��Ĳ���
					 */
					if (type.equals("remoteBreak")) {
						String sender = Message.getXMLValue("sender", message);
						String from = Message.getXMLValue("from", message);
						if (from.equals("client")) {
							// �ӱ������߷����ĶϿ�֪ͨ
							// �رռ�ش���
							UIMap.remoteControlUIMap.get(sender)
									.closeRemoteControlUI();
							JOptionPane.showMessageDialog(null, "�Է��ѶϿ�Զ�����ӣ�����");
						} else {
							// �ӿ����߷����ĶϿ�֪ͨ
							// ���жϿ�����
							if (MediaControlTools.remoteStorage.get(sender) != null) {
								MediaControlTools.remoteFinish(sender);
								JOptionPane.showMessageDialog(null,
										"�Է�ȡ���˶����ļ�أ�����");
								if (UIMap.chatUIMap.get(sender) != null) {
									UIMap.chatUIMap.get(sender)
											.removeBreakButton();
									UIMap.chatUIMap.get(sender).appendMsg(null,12,
											"Զ�������ѶϿ�������",true,sender);
								}
							}
						}
						MediaControlTools.setRemoteControlState(false);
					}
					/**
					 * �Ծ�ȷ���ҽ���Ĳ���
					 */
					if (type.equals("directSearchResult")) {
						String result = Message.getXMLValue("result", message);
						System.out.println("result"+result);
						if ("no".equals(result)) {
							JOptionPane.showMessageDialog(null,
									"�Բ���û�и��û�......");
						} else {
							String userName = Message.getXMLValue("userName",
									message);
							String userSex = Message.getXMLValue("userSex",
									message);
							String userAge = Message.getXMLValue("userAge",
									message);
							String userImage = Message.getXMLValue("userImage",
									message);
							UserInfo user = new UserInfo();
							user.setUserName(userName);
							user.setUserSex(userSex);
							user.setUserAge(Integer.parseInt(userAge));
							user.setUserImage(userImage);
							SearchUI searchUI = (SearchUI) UIMap.temporaryStorage
									.get("search");
							if (searchUI != null) {
								searchUI.showDirectResult(user);
							}
						}
					}
					
					/**�԰��յ�����ҵĲ���*/
					
				
					
					/**
					 * ��������ҽ���Ĳ���
					 */
					if (type.equals("randomSearchResult")) {
				        //System.out.println("�漴�ĳ���");
						String result = Message.getXMLValue("result", message);
						ArrayList<UserInfo> list = new ArrayList<UserInfo>();
						if (result.equals("yes")) {
							String infomation = Message.getXMLValue("info",
									message);
							StringTokenizer token = new StringTokenizer(
									infomation, "|");
							while (token.hasMoreTokens()) {
								String info = token.nextToken();
								UserInfo user = new UserInfo();
								int i = 1;
								StringTokenizer t = new StringTokenizer(info,
										",");
								while (t.hasMoreTokens()) {
									if (i == 1) {
										user.setUserImage(t.nextToken());
									}
									if (i == 2) {
										user.setUserName(t.nextToken());
									}
									if (i == 3) {
										user.setUserSex(t.nextToken());
									}
									if (i == 4) {
										user.setUserAge(Integer.parseInt(t
												.nextToken()));
									}
									i++;
								}
								list.add(user);
							}
						} else {
							SearchUI searchUI = (SearchUI) UIMap.temporaryStorage
									.get("search");
							int index = searchUI.getIndex();
							searchUI.setIndex(index - 5);
							JOptionPane
									.showMessageDialog(null, "û�и���������û��ˣ�����");
						}

						SearchUI searchUI = (SearchUI) UIMap.temporaryStorage
								.get("search");
						if (searchUI != null) {
							searchUI.showRandomResult(list);
						}
					}
					/**
					 * �ԼӺ�����Ϣ�Ĵ���
					 */
					if (type.equals("addRequest")) {
						String sender = Message.getXMLValue("sender", message);
						MessageBox.storeSystemMsg("addRequest," + sender);
						// �簴ťû�ж��������������߳�
						FlashAction flash = (FlashAction) UIMap.temporaryStorage
								.get("flash");
						if (flash == null) {
							flash = new FlashAction();
							flash.start();
							UIMap.storeObj("flash", flash);
						}
					}
					/**
					 * �ԼӺ��ѻظ���Ϣ�Ĵ���
					 */
					if (type.equals("addResponse")) {
						String sender = Message.getXMLValue("sender", message);
						String addResp = Message.getXMLValue("resp", message);
						if (addResp.equals("yes")) {
							MessageBox.storeSystemMsg("addResponse," + sender
									+ ",yes");
							String info = Message.getXMLValue("info", message);
							if (info.length() > 0) {
								UserInfo user = new UserInfo();
								StringTokenizer token = new StringTokenizer(
										info, ",");
								if (token.hasMoreTokens()) {
									user.setUserName(token.nextToken());
									user.setUserImage(token.nextToken());
									user.setUserSex(token.nextToken());
									user.setUserState(token.nextToken());
								}

								FriendList.addNewFriend(user);
								((MainUI) UIMap.temporaryStorage.get("mainUI"))
										.resetFriendPanel();
							}
						} else {
							MessageBox.storeSystemMsg("addResponse," + sender
									+ ",no");
						}
						// �簴ťû����˸��������˸�߳�
						FlashAction flash = (FlashAction) UIMap.temporaryStorage
								.get("flash");
						if (flash == null) {
							flash = new FlashAction();
							flash.start();
							UIMap.storeObj("flash", flash);
						}
					}
					/**
					 * ��ɾ����Ϣ�Ĳ���
					 */
					if (type.equals("delete")) {
						String sender = Message.getXMLValue("sender", message);
						// �Ӻ����б����Ƴ��ö���
						for (UserInfo user : FriendList.friendList) {
							if (user.getUserName().equals(sender)) {
								FriendList.deleteFriend(user);
								break;
							}
						}
						((MainUI) UIMap.temporaryStorage.get("mainUI"))
								.resetFriendPanel();
					}
					/**
					 * ���û����ߵĲ���
					 */
					if (type.equals("online")) {
						String sender = Message.getXMLValue("sender", message);
						//���º����б�
						FriendList.joinOnline(sender);
						//����������
						((MainUI)UIMap.temporaryStorage.get("mainUI")).resetFriendPanel();
					}
					/**
					 * ���û����ߵĲ���
					 */
					if (type.equals("leave")) {
						String sender = Message.getXMLValue("sender", message);
						//���º����б�
                        FriendList.joinDownline(sender);
                        //����������
                        ((MainUI)UIMap.temporaryStorage.get("mainUI")).resetFriendPanel();
					}
				} catch (Exception e) {
					e.printStackTrace();
					RunningLog.record("*****����ReceiveControl�е�run������������");
				}
			}
		} catch (Exception e) {
			//��������Ͽ�����ʱ�Ĵ���
			LoginUI l = (LoginUI)UIMap.temporaryStorage.get("loginUI");
			if(l!=null){
				//��¼ǰ�Ͽ�
				JOptionPane.showMessageDialog(null, "����ʧ�ܣ����������Ƿ�������Ȼ�����µ�¼��");
				System.exit(-1);
			}else{
				//��¼��Ͽ�
				JOptionPane.showMessageDialog(null, "����������ӶϿ������ڳ�����������......");
				UIMap.isConnected = false;
				//�ر��Ѵ򿪵��������
				Set<String> chatUISet = UIMap.chatUIMap.keySet();
				Iterator<String> chatIte = chatUISet.iterator();
				while(chatIte.hasNext()){
					String userName = chatIte.next();
					UIMap.chatUIMap.remove(userName).closeUI();
				}
				//�ر��Ѵ򿪵���Ƶ����
				Set<String> viewUISet = UIMap.viewUIMap.keySet();
				Iterator<String> viewIte = viewUISet.iterator();
				while(viewIte.hasNext()){
					String userName = viewIte.next();
					UIMap.viewUIMap.remove(userName).closeViewUI();
					MediaControlTools.videoFinish(userName);
				}
				//�ر��Ѵ򿪵Ĳ��ҽ���
				SearchUI searchUI = (SearchUI)UIMap.temporaryStorage.get("search");
				if(searchUI!=null){
					searchUI.closeUI();
				}
				//�ر��Ѵ򿪵�ע�����
				RegUI reg = (RegUI)UIMap.temporaryStorage.get("reg");
				if(reg!=null){
					reg.closeUI();
				}
				//�ر��Ѵ򿪵�������
				MainUI mainUI = (MainUI)UIMap.temporaryStorage.get("mainUI");
				if(mainUI!=null){
					point = mainUI.getWindowLocation();
					mainUI.closeUI();
				}
				//ÿ��5����һ�Σ�����һ����
				int count = 0;
				while(count<12){
					try{
						//����5��
						Thread.sleep(3000);
						// ��������
						ManClient.client = new Socket(ip,port);
						UIMap.isConnected = true;
						//�����ӳɹ�,����һ����¼��Ϣ
						String loginMsg = "<type>login</type><userName>"+Loginner.loginner+"</userName><userPwd>"+Loginner.loginPwd+"</userPwd>";
						Message.sendMsg(loginMsg, ManClient.client.getOutputStream());
						receiveFunction();
					}catch(Exception ef){
						count++;
						if(count==20){
							JOptionPane.showMessageDialog(null, "������ϣ������������������Ƿ�������Ȼ�����µ�¼������");
							System.exit(0);
						}
					}
				}
			}
		}
	}
}
