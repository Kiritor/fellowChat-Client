/*信息的控制类*/

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
			// 启动登录界面,添加好监听器
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

		// 开始进行消息接收与处理操作
		try {
			while (flag) {
				String message = Message.readString(ManClient.client
						.getInputStream());
				// 解析消息类型，根据类型作相应处理
				String type = Message.getXMLValue("type", message);
				System.out.println("操作的类型是："+type);
				try {
					/**
					 * 对登录回复的操作
					 */
					if (type.equals("loginresp")) {
						String resp = Message.getXMLValue("resp", message);

						if ("yes".equals(resp)) {
							// 登录成功
							// 关闭登录界面
							if(UIMap.temporaryStorage.get("loginUI")!=null){
								loginUI.closeWindow();
								UIMap.removeObj("loginUI");
							}
							// 记录当前登录者信息
							String loginner = Message.getXMLValue("loginner",message);
							Loginner.setLoginner(loginner);
							String loginPwd = Message.getXMLValue("loginPwd", message);
							Loginner.setLoginPwd(loginPwd);
							// 得到好友列表，打开主界面
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
							 * 登录失败执行的操作
							 */
							JOptionPane.showMessageDialog(null,
									"用户名或密码错误，请重新输入！");
							loginUI.setFocusInUserName();
						}
						if(resp.equals("online")){
							/**
							 * 已登录
							 */
							JOptionPane.showMessageDialog(null, "该用户已在线，禁止重复登录！！！");
							loginUI.setFocusInUserName();
						}
					}
					/**
					 * 对注册应答的操作
					 */
					if (type.equals("regResp")) {
						String regResp = Message.getXMLValue("resp", message);
						if ("yes".equals(regResp)) {
							// 注册成功
							JOptionPane.showMessageDialog(null,
									"恭喜您注册漫号成功，请前往登录页面登录！！！");
							RegUI reg = (RegUI) UIMap.temporaryStorage
									.get("reg");
							if (reg != null) {
								reg.closeUI();
							}
						} else {
							// 注册失败
							String reason = Message.getXMLValue("reason",
									message);
							if ("writeError".equals(reason)) {
								JOptionPane.showMessageDialog(null,
										"注册失败，请尝试重新提交注册信息！！！");
							} else {
								JOptionPane.showMessageDialog(null,
										"已有此用户名,请更改!!!");
							}
							RegUI reg = (RegUI) UIMap.temporaryStorage
									.get("reg");
							if (reg != null) {
								reg.resubmitOpration();
							}
						}
					}
					/**
					 * 对聊天信息的操作
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

						// 判断聊天窗口有没有打开
						ChatUI chatUI = UIMap.chatUIMap.get(sender);
						System.out.println(chatUI);
						if (chatUI != null) {
							// 如果是打开的，直接append
						//	chatUI.appendMsg(fontFamily,fontSize,content,false,sender);
							/*这里通过好友的名字不一样建立不同的文件*/
							if(FileSearcher.fileExite(sender))
							{
								//存在该文件直接向文件头部插入即可
								chatUI.appendMsg(fontFamily,fontSize,content,false,sender);
								FileHositoryMsg.record(content, sender);
								FileHositoryMsg.record(content, destination);
							}
							else {
								//不存在该文件，新建文件再从头部插入
								chatUI.appendMsg(fontFamily,fontSize,content,false,sender);
								FileHositoryMsg.record(content, destination);
								FileHositoryMsg.record(content, sender);
							}
							
							
							
						} else {
							// 如果不是打开的，先更新好友列表和主界面
							
							FriendList.joinShakingList(sender);
							((MainUI)UIMap.temporaryStorage.get("mainUI")).resetFriendPanel();
							//存入消息盒子，若头像之前没有抖动则抖动
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
					 * 对视频请求的操作
					 */
					if (type.equals("viewRequest")) {
						String sender = Message.getXMLValue("sender", message);
						int option = JOptionPane.showOptionDialog(null, sender
								+ "请求和您视频聊天，同意么？", "视频请求", 0, 1, null, null,
								null);
						if (option == 0) {
							// 确定
							// 解析请求者的IP地址
							String senderIP = Message
									.getXMLValue("ip", message);
							// 发送回执
							String viewResp = "<type>viewResponse</type><sender>"
									+ Loginner.loginner
									+ "</sender><destination>"
									+ sender
									+ "</destination><resp>yes</resp>";
							Message.sendMsg(viewResp, ManClient.client
									.getOutputStream());
							// 显示操作通知
							open.openChatUI(sender);
							ChatUI chatUI = UIMap.chatUIMap.get(sender);
							if (chatUI != null) {
								chatUI.appendMsg(null,12,"您已同意对方的视频请求，视频组件启动中......",true,sender);
							} else {
								chatUI = new ChatUI(sender);
								chatUI.showUI();
								chatUI.appendMsg(null,12,"您已同意对方的视频请求，视频组件启动中......",true,sender);
								UIMap.add(sender, chatUI);
							}
							// 启动视频界面（未显示，等待Player组件就绪）
							ViewUI viewUI = new ViewUI(sender);
							viewUI.start();
							UIMap.add(sender, viewUI);
							// 启动视频的发送与接收线程
							SendMedia sendVideo = null;
							SendMedia sendAudio = null;
							ReceiveMedia receiveMedia = null;
							// 启动发送部分
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
									RunningLog.record("*****错误：视频启动出错！！！");
								}
							} else {
								// 通知用户没有视频设备
								UIMap.chatUIMap.get(sender).appendMsg(null,12,
										"******错误：没有检测到您的视频设备！！！",true,sender);
							}
							if (audio != null&&audio.size()>0) {
								sendAudio = new SendMedia(
										(CaptureDeviceInfo) audio.get(0),
										senderIP, "8820", null, sender);
								if (sendAudio.start() != null) {
									RunningLog.record("*****错误：音频启动出错！！！");
								}
							} else {
								// 通知用户没有音频设备
								UIMap.chatUIMap.get(sender).appendMsg(null,12,
										"******错误：没有检测到您的音频设备！！！",true,sender);
							}
							// 启动接收部分
							String[] session = new String[2];
							session[0] = senderIP + "/8830/1";
							session[1] = senderIP + "/8840/1";
							receiveMedia = new ReceiveMedia(session, sender);
							if (!receiveMedia.start()) {
								RunningLog.record("******错误：视频接收出错！！！");
							}
							// 记录以上进程以便进行统一的关闭操作
							MediaControlTools.storeMediaSession(sender,
									sendVideo, sendAudio, receiveMedia);

						} else {
							// 取消
							String viewResp = "<type>viewResponse</type><sender>"
									+ Loginner.loginner
									+ "</sender><destination>"
									+ sender
									+ "</destination><resp>no</resp>";
							Message.sendMsg(viewResp, ManClient.client
									.getOutputStream());
							if (UIMap.chatUIMap.get(sender) != null) {
								UIMap.chatUIMap.get(sender).appendMsg(null,12,
										"您拒绝了对方的视频请求！！！",true,sender);
							}
						}
					}
					/**
					 * 对视频应答的操作
					 */
					if (type.equals("viewResponse")) {
						String viewResp = Message.getXMLValue("resp", message);
						String sender = Message.getXMLValue("sender", message);
						if (viewResp.equals("notOnline")) {
							JOptionPane.showMessageDialog(null, "对方没有在线！");
							UIMap.chatUIMap.get(sender).appendMsg(null,12,
									"对方必需在线您才能进行视频聊天！！！",true,sender);
						}
						if (viewResp.equals("yes")) {
							// 解析出对方的IP
							String senderIP = Message
									.getXMLValue("ip", message);
							// 显示通知
							UIMap.chatUIMap.get(sender).appendMsg(null,12,
									"对方同意了您的视频请求，视频组件启动中.......",true,sender);
							// 启动视频界面（未显示，等待Player组件就绪）
							ViewUI viewUI = new ViewUI(sender);
							viewUI.start();
							UIMap.add(sender, viewUI);
							// 启动视频发送和接收线程
							SendMedia sendVideo = null;
							SendMedia sendAudio = null;
							ReceiveMedia receiveMedia = null;
							// 启动发送部分
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
									RunningLog.record("******错误：视频启动出错！！！");
								}
							} else {
								// 通知用户没有视频设备
								UIMap.chatUIMap.get(sender).appendMsg(null,12,
										"*****错误：找不到您的视频设备！！！",true,sender);
							}
							if (audio != null&&audio.size()>0) {
								sendAudio = new SendMedia(
										(CaptureDeviceInfo) audio.get(0),
										senderIP, "8840", null, sender);
								if (sendAudio.start() != null) {
									RunningLog.record("******错误：音频启动出错！！！");
								}
							} else {
								// 通知用户没有音频设备
								UIMap.chatUIMap.get(sender).appendMsg(null,12,
										"*****错误：找不到您的音频设备！！！",true,sender);
							}
							// 启动接收部分
							String[] session = new String[2];
							session[0] = senderIP + "/8810/1";
							session[1] = senderIP + "/8820/1";
							receiveMedia = new ReceiveMedia(session, sender);
							if (!receiveMedia.start()) {
								RunningLog.record("******错误：视频接收出错！！！");
							}
							// 记录以上进程以便进行统一关闭操作
							MediaControlTools.storeMediaSession(sender,
									sendVideo, sendAudio, receiveMedia);
						}
						if (viewResp.equals("no")) {
							JOptionPane.showMessageDialog(null, "对方拒绝了您的视频请求！");
						}
					}
					/**
					 * 对视频断开通知的操作
					 */
					if (type.equals("viewBreak")) {
						// 解析出来源用户
						String sender = Message.getXMLValue("sender", message);
						// 关闭视频窗口
						UIMap.viewUIMap.get(sender).closeViewUI();
						// 从ViewUIMap中移除对应记录
						UIMap.removeViewUI(sender);
						// 关闭视频的接收和发送进程
						MediaControlTools.videoFinish(sender);
						// 显示通知
						if (UIMap.chatUIMap.get(sender) != null) {
							UIMap.chatUIMap.get(sender).appendMsg(null,12,
									"视频已取消..........",true,sender);
						}
					}
					/**
					 * 对文件传输请求的操作
					 */
					if (type.equals("fileRequest")) {
						// 解析消息
						String sender = Message.getXMLValue("sender", message);
						String fileName = Message.getXMLValue("fileName",
								message);
						String fileSize = Message.getXMLValue("fileSize",
								message);
						String filePath = Message.getXMLValue("filePath",
								message);
						// 弹出文件传输请求的提示框
						String tip = sender + " 请求向您传送文件:\r\n文件名: " + fileName
								+ "\r\n大小: " + fileSize;
						int option = JOptionPane.showOptionDialog(null, tip,
								"文件传输请求", JOptionPane.YES_NO_OPTION,
								JOptionPane.INFORMATION_MESSAGE, null, null,
								null);
						if (option == JOptionPane.YES_OPTION) {
							// 确定接收的操作
							open.openChatUI(sender);
							JFileChooser fileChooser = new JFileChooser();
							int reOption = fileChooser.showSaveDialog(null);
							if (reOption == JFileChooser.APPROVE_OPTION) {
								File file = fileChooser.getSelectedFile();
								// 得到文件大小
								long size = (long) ((Float.parseFloat(fileSize
										.substring(0, fileSize.length() - 2)) * 1000) - 4);
								ReceiveProgress rpro = new ReceiveProgress(
										file, size);
								// 显示文件接收进度
								ProgressView pview = new ProgressView(file,
										rpro, "receive", sender);
								pview.initialize();
								UIMap.chatUIMap.get(sender).addFileView(pview);
								// 启动文件接收线程
								if (!MediaControlTools.isFileServerOn()) {
									receiveFile = new ReceiveFile(file, sender);
									receiveFile.start();
									MediaControlTools.fileServerOn();
								} else {
									receiveFile.setFile(file);
								}
								// 发送同意接收的应答
								String fileResp = "<type>fileResponse</type><sender>"
										+ Loginner.loginner
										+ "</sender><destination>"
										+ sender
										+ "</destination><resp>yes</resp><filePath>"
										+ filePath + "</filePath>";
								Message.sendMsg(fileResp, ManClient.client
										.getOutputStream());
								// 显示操作记录
								UIMap.chatUIMap.get(sender).appendMsg(null,12,
										"您同意接收文件........",true,sender);
							} else {
								// 发送不接收的应答
								String fileResp = "<type>fileResponse</type><sender>"
										+ Loginner.loginner
										+ "</sender><destination>"
										+ sender
										+ "</destination><resp>no</resp>";
								Message.sendMsg(fileResp, ManClient.client
										.getOutputStream());
								UIMap.chatUIMap.get(sender).appendMsg(null,12,
										"您拒绝接收文件........",true,sender);
							}
						} else {
							// 不接收的操作
							String fileResp = "<type>fileResponse</type><sender>"
									+ Loginner.loginner
									+ "</sender><destination>"
									+ sender
									+ "</destination><resp>no</resp>";
							Message.sendMsg(fileResp, ManClient.client
									.getOutputStream());
							if (UIMap.chatUIMap.get(sender) != null) {
								UIMap.chatUIMap.get(sender).appendMsg(null,12,
										"您拒绝接收文件........",true,sender);
							}
						}
					}
					/**
					 * 对文件传输应答的操作
					 */
					if (type.equals("fileResponse")) {
						String sender = Message.getXMLValue("sender", message);
						String fileResp = Message.getXMLValue("resp", message);
						if (fileResp.equals("yes")) {
							// 对方同意接收
							UIMap.chatUIMap.get(sender).appendMsg(null,12,
									"对方同意接收文件.........",true,sender);

							String ip = Message.getXMLValue("ip", message);
							String filePath = Message.getXMLValue("filePath",
									message);
							File file = new File(filePath);
							// 启动发送线程
							SendFile sendFile = new SendFile(sender, ip, file);
							sendFile.start();
							// 显示文件发送进度
							SendProgress spro = new SendProgress(sendFile,
									sendFile.getFileSize());
							ProgressView pview = new ProgressView(file, spro,
									"send", sender);
							pview.initialize();
							UIMap.chatUIMap.get(sender).addFileView(pview);

						}
						if (fileResp.equals("no")) {
							// 对方拒绝接收
							JOptionPane
									.showMessageDialog(null, "对方拒绝接收文件.....");
							if (UIMap.chatUIMap.get(sender) != null) {
								UIMap.chatUIMap.get(sender).appendMsg(null,12,
										"对方拒绝接收文件..........",true,sender);
							}
						}
						if (fileResp.equals("notOnline")) {
							// 对方不在线
							JOptionPane.showMessageDialog(null, "对方不在线！");
						}
					}

					/**
					 * 对远程监控请求的操作
					 */
					if (type.equals("remoteRequest")) {
						String sender = Message.getXMLValue("sender", message);
						String tip = sender + " 请求您的远程协助，同意吗？";
						int option = JOptionPane.showOptionDialog(null, tip,
								"文件传输请求", JOptionPane.YES_NO_OPTION,
								JOptionPane.INFORMATION_MESSAGE, null, null,
								null);
						if (option == JOptionPane.YES_OPTION) {
							// 同意监控
							open.openChatUI(sender);
							UIMap.chatUIMap.get(sender).appendMsg(null,12,"您同意了对方的请求！",true,sender);
							// 打开监控界面
							RemoteControlUI remote = new RemoteControlUI(sender);
							remote.showUI();
							UIMap.add(sender, remote);
							// 启动接收服务器
							Receive rec = new Receive(remote, sender);
							rec.start();
							// 发送应答
							String remoteResp = "<type>remoteResponse</type><sender>"
									+ Loginner.loginner
									+ "</sender><destination>"
									+ sender
									+ "</destination><resp>yes</resp>";
							Message.sendMsg(remoteResp, ManClient.client
									.getOutputStream());
						} else {
							// 不同意监控
							String remoteResp = "<type>remoteResponse</type><sender>"
									+ Loginner.loginner
									+ "</sender><destination>"
									+ sender
									+ "</destination><resp>no</resp>";
							Message.sendMsg(remoteResp, ManClient.client
									.getOutputStream());
							if (UIMap.chatUIMap.get(sender) != null) {
								UIMap.chatUIMap.get(sender).appendMsg(null,12,
										"您取消了对方的请求......",true,sender);
							}
						}
					}

					/**
					 * 对远程监控应答的操作
					 */
					if (type.equals("remoteResponse")) {
						String sender = Message.getXMLValue("sender", message);
						String remoteResp = Message
								.getXMLValue("resp", message);
						if (remoteResp.equals("yes")) {
							// 对方同意请求
							UIMap.chatUIMap.get(sender).appendMsg(null,12,
									sender + " 同意了您的请求，对方已取得控制权！",true,sender);
							// 解析出对方IP
							String senderIP = Message
									.getXMLValue("ip", message);
							// 启动数据发送线程
							Send send = new Send(senderIP, sender);
							send.start();

							UIMap.chatUIMap.get(sender).switchRequestState();
							MediaControlTools.setRemoteControlState(true);
						}
						if (remoteResp.equals("no")) {
							// 对方拒绝请求
							JOptionPane.showMessageDialog(null, sender
									+ " 拒绝了您的请求......");
							UIMap.chatUIMap.get(sender).switchRequestState();
						}
						if (remoteResp.equals("notOnline")) {
							// 对方不在线
							JOptionPane.showMessageDialog(null, "对方不在线!!!");
							UIMap.chatUIMap.get(sender).switchRequestState();
						}
					}

					/**
					 * 对远程监控断开的操作
					 */
					if (type.equals("remoteBreak")) {
						String sender = Message.getXMLValue("sender", message);
						String from = Message.getXMLValue("from", message);
						if (from.equals("client")) {
							// 从被控制者发来的断开通知
							// 关闭监控窗口
							UIMap.remoteControlUIMap.get(sender)
									.closeRemoteControlUI();
							JOptionPane.showMessageDialog(null, "对方已断开远程连接！！！");
						} else {
							// 从控制者发来的断开通知
							// 进行断开操作
							if (MediaControlTools.remoteStorage.get(sender) != null) {
								MediaControlTools.remoteFinish(sender);
								JOptionPane.showMessageDialog(null,
										"对方取消了对您的监控！！！");
								if (UIMap.chatUIMap.get(sender) != null) {
									UIMap.chatUIMap.get(sender)
											.removeBreakButton();
									UIMap.chatUIMap.get(sender).appendMsg(null,12,
											"远程连接已断开！！！",true,sender);
								}
							}
						}
						MediaControlTools.setRemoteControlState(false);
					}
					/**
					 * 对精确查找结果的操作
					 */
					if (type.equals("directSearchResult")) {
						String result = Message.getXMLValue("result", message);
						System.out.println("result"+result);
						if ("no".equals(result)) {
							JOptionPane.showMessageDialog(null,
									"对不起，没有该用户......");
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
					
					/**对按照地域查找的操作*/
					
				
					
					/**
					 * 对随机查找结果的操作
					 */
					if (type.equals("randomSearchResult")) {
				        //System.out.println("随即的抄造");
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
									.showMessageDialog(null, "没有更多的在线用户了！！！");
						}

						SearchUI searchUI = (SearchUI) UIMap.temporaryStorage
								.get("search");
						if (searchUI != null) {
							searchUI.showRandomResult(list);
						}
					}
					/**
					 * 对加好友信息的处理
					 */
					if (type.equals("addRequest")) {
						String sender = Message.getXMLValue("sender", message);
						MessageBox.storeSystemMsg("addRequest," + sender);
						// 如按钮没有抖动则启动抖动线程
						FlashAction flash = (FlashAction) UIMap.temporaryStorage
								.get("flash");
						if (flash == null) {
							flash = new FlashAction();
							flash.start();
							UIMap.storeObj("flash", flash);
						}
					}
					/**
					 * 对加好友回复信息的处理
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
						// 如按钮没有闪烁则启动闪烁线程
						FlashAction flash = (FlashAction) UIMap.temporaryStorage
								.get("flash");
						if (flash == null) {
							flash = new FlashAction();
							flash.start();
							UIMap.storeObj("flash", flash);
						}
					}
					/**
					 * 对删除消息的操作
					 */
					if (type.equals("delete")) {
						String sender = Message.getXMLValue("sender", message);
						// 从好友列表中移除该对象
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
					 * 对用户上线的操作
					 */
					if (type.equals("online")) {
						String sender = Message.getXMLValue("sender", message);
						//更新好友列表
						FriendList.joinOnline(sender);
						//更新主界面
						((MainUI)UIMap.temporaryStorage.get("mainUI")).resetFriendPanel();
					}
					/**
					 * 对用户离线的操作
					 */
					if (type.equals("leave")) {
						String sender = Message.getXMLValue("sender", message);
						//更新好友列表
                        FriendList.joinDownline(sender);
                        //更新主界面
                        ((MainUI)UIMap.temporaryStorage.get("mainUI")).resetFriendPanel();
					}
				} catch (Exception e) {
					e.printStackTrace();
					RunningLog.record("*****错误：ReceiveControl中的run方法出错！！！");
				}
			}
		} catch (Exception e) {
			//与服务器断开连接时的处理
			LoginUI l = (LoginUI)UIMap.temporaryStorage.get("loginUI");
			if(l!=null){
				//登录前断开
				JOptionPane.showMessageDialog(null, "连接失败，请检查网络是否正常，然后重新登录！");
				System.exit(-1);
			}else{
				//登录后断开
				JOptionPane.showMessageDialog(null, "与服务器连接断开，正在尝试重新连接......");
				UIMap.isConnected = false;
				//关闭已打开的聊天界面
				Set<String> chatUISet = UIMap.chatUIMap.keySet();
				Iterator<String> chatIte = chatUISet.iterator();
				while(chatIte.hasNext()){
					String userName = chatIte.next();
					UIMap.chatUIMap.remove(userName).closeUI();
				}
				//关闭已打开的视频界面
				Set<String> viewUISet = UIMap.viewUIMap.keySet();
				Iterator<String> viewIte = viewUISet.iterator();
				while(viewIte.hasNext()){
					String userName = viewIte.next();
					UIMap.viewUIMap.remove(userName).closeViewUI();
					MediaControlTools.videoFinish(userName);
				}
				//关闭已打开的查找界面
				SearchUI searchUI = (SearchUI)UIMap.temporaryStorage.get("search");
				if(searchUI!=null){
					searchUI.closeUI();
				}
				//关闭已打开的注册界面
				RegUI reg = (RegUI)UIMap.temporaryStorage.get("reg");
				if(reg!=null){
					reg.closeUI();
				}
				//关闭已打开的主界面
				MainUI mainUI = (MainUI)UIMap.temporaryStorage.get("mainUI");
				if(mainUI!=null){
					point = mainUI.getWindowLocation();
					mainUI.closeUI();
				}
				//每隔5秒连一次，持续一分钟
				int count = 0;
				while(count<12){
					try{
						//休眠5秒
						Thread.sleep(3000);
						// 尝试连接
						ManClient.client = new Socket(ip,port);
						UIMap.isConnected = true;
						//若连接成功,发送一条登录消息
						String loginMsg = "<type>login</type><userName>"+Loginner.loginner+"</userName><userPwd>"+Loginner.loginPwd+"</userPwd>";
						Message.sendMsg(loginMsg, ManClient.client.getOutputStream());
						receiveFunction();
					}catch(Exception ef){
						count++;
						if(count==20){
							JOptionPane.showMessageDialog(null, "网络故障，请检查您的网络连接是否正常，然后重新登录！！！");
							System.exit(0);
						}
					}
				}
			}
		}
	}
}
