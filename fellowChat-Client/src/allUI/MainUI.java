package allUI;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JWindow;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import music.MainFrame;
import util.pojo.CommentInfo;
import util.pojo.UserInfo;
import util.tools.ActiveLabelTool;
import util.tools.FriendList;
import util.tools.GetComment;
import util.tools.GetUserFlag;
import util.tools.Loginner;
import util.tools.MyToolTip;
import util.tools.ToolsBack;
import util.tools.UIMap;
import action.loginUIAction.RegAction;
import action.mainUIAction.DeleteAction;
import action.mainUIAction.InputAction;
import action.mainUIAction.MainUICloseAction;
import action.mainUIAction.OpenChatUIAction;
import action.mainUIAction.OpenSearchUIAction;
import action.mainUIAction.SearchAction;
import action.mainUIAction.ShakeAction;
import action.mainUIAction.SpecificFlagAction;
import action.mainUIAction.SystemMsgButtonAction;
import allUI.tooltip.OperateToolTip;
import allUI.tooltip.ToolTip;

import com.jtattoo.plaf.mcwin.McWinLookAndFeel;
import com.sun.awt.AWTUtilities;

public class MainUI {

	
	
	/*这里添加进入西大老乡荟的按钮图标*/
	
    private JLabel myZoneButton  = null;
    private  ImageIcon myZoneIcon = new ImageIcon("image/myZone.png");
    private  ImageIcon myZoneIcon2 = new ImageIcon("image/myZone_fake.png");
    
	
	
	/* 客户端的个性化设置 */

	private JLabel shezhiJLabel;
	private ImageIcon shezhiIcon = new ImageIcon("image/shezhi.jpg");
	private ImageIcon sehizhifak = new ImageIcon("image/shezhifak.jpg");

	// 用户的特别标签
	private String SpecificFlag = "该用户很懒,什么也没有留下.......";
	private boolean isDraging = true;
	private int xx;
	private int yy;
	// 自己添加的一些信息
	private ImageIcon pifuIcon = new ImageIcon("image/pifu.jpg");// 皮肤图片
	private ImageIcon pifufak = new ImageIcon("image/pifufak.jpg");
	private ImageIcon musIcon = new ImageIcon("image/music.jpg");
	//
	private ImageIcon editor = new ImageIcon("image/enter.jpg");
	private ImageIcon image = new ImageIcon("image/MainUIback.jpg");
	private ImageIcon close = new ImageIcon("image/closeButton.png");
	private ImageIcon close_fake = new ImageIcon("image/closeButton_fake.png");
	private ImageIcon hide = new ImageIcon("image/hideButton.png");
	private ImageIcon hide_fake = new ImageIcon("image/hideButton_fake.png");
	private ImageIcon add = new ImageIcon("image/add.png");
	private ImageIcon add_on = new ImageIcon("image/add_on.png");
	private ImageIcon add_press = new ImageIcon("image/add_press.png");
	private ImageIcon systemMsg = new ImageIcon("image/systemMsg.png");
	private ImageIcon systemMsg_fake = new ImageIcon("image/systemMsg_fake.png");
	private ImageIcon systemMsg_on = new ImageIcon("image/systemMsg_on.png");
	private ImageIcon search = new ImageIcon("image/searchTextField.png");
	private ImageIcon friendSearch = new ImageIcon(
			"image/friendSearchButton.png");
	private ImageIcon friendSearch_on = new ImageIcon(
			"image/friendSearchButton_on.png");
	private ImageIcon friendSearch_press = new ImageIcon(
			"image/friendSearchButton_press");

	private JWindow window;
	private JPanel panel;

	public JPanel getPanel() {
		return panel;
	}

	public void setPanel(JPanel panel) {
		panel = panel;
	}

	private JPanel friendPanel;
	private JLabel addButton;

	// 自己添加的一些代码
	private JLabel pifuJLabel;// 皮肤图标的图片标签
	private JLabel musicJLabel;// 音乐图标的图片标签
	// 编辑图片的标签
	private JLabel editorlJLabel;
	private JLabel addButton1;
	private JLabel hideButton;
	private JLabel closeButton;
	private JLabel systemMsgButton;
	private JFrame frame;
	private JScrollPane scroll;
	private JTextPane textPane;
	private JLabel searchZone;
	private JTextField searchField;
	private JLabel friendSearchButton;
	private JButton Media = null;
	private MyToolTip tip;
	private boolean isTrayShowed = false;

	public Point getWindowLocation() {
		return window.getLocationOnScreen();
	}

	public JFrame getFrame() {
		return frame;
	}

	public JWindow getWindow() {
		return window;
	}

	public void resetFriendPanel() {
		panel.remove(scroll);
		createFriendPanel();
		panel.updateUI();
		// 同步抖动
		try {
			Thread.sleep(100);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 先停掉原来的，然后重新启动
		Set<String> set = ActiveLabelTool.shakingLabel.keySet();
		Iterator<String> i = set.iterator();
		while (i.hasNext()) {
			String userName = i.next();
			ActiveLabelTool.shakingLabel.get(userName).stopShake();
			i.remove();
		}
		for (int j = 0; j < FriendList.shakingList.size(); j++) {
			ShakeAction shake = new ShakeAction(FriendList.shakingList.get(j)
					.getUserName());
			shake.start();
			ActiveLabelTool.add(FriendList.shakingList.get(j).getUserName(),
					shake);
		}
	}

	public void flash1() {
		systemMsgButton.setIcon(systemMsg);
		panel.updateUI();
	}

	public void flash2() {
		systemMsgButton.setIcon(systemMsg_fake);
		panel.updateUI();
	}

	public void closeUI() {
		window.dispose();
		frame.dispose();
	}

	public void showUI(Point p) {
		// 工具条尝试

		frame = new JFrame(Loginner.loginner + "的漫聊");

		window = new JWindow(frame);
		window.setSize(800, 225);
		window.setLayout(new GridLayout(1, 1));
		/* 使窗体实现圆角的效果 */
		AWTUtilities.setWindowShape(window, new RoundRectangle2D.Double(0.0D,
				0.0D, window.getWidth(), window.getHeight(), 18.0D, 18.0D));
		// 铺底板
		image = new ImageIcon("background/" + ToolsBack.getConfig()[0] + ".jpg");
		System.out.println(ToolsBack.getConfig());
		panel = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(image.getImage(), 0, 0, null);
				this.setOpaque(false);
			}
		};

		window.add(panel);
		/*
		 * SwingUtilities.invokeLater(new Runnable() { public void run() {
		 * Window w = window; w.setVisible(true);
		 * com.sun.awt.AWTUtilities.setWindowOpacity(w, 0.5f); } });
		 * window.setLocationRelativeTo(null);
		 */
		panel.setLayout(null);
		// panel.addMouseListener(new MouseAdapter(){
		// public void mouseReleased(MouseEvent e){
		// System.out.println(e.getX()+"---"+e.getY());
		// }
		// });

		/**
		 * 头菜单栏
		 */
		JPanel headPanel = new JPanel();
		headPanel.setBorder(BorderFactory.createEmptyBorder());
		headPanel.setLayout(null);
		headPanel.setOpaque(false);
		headPanel.setBounds(7, 5, 785, 68);
		panel.add(headPanel);
		// headPanel.addMouseListener(new MouseAdapter(){
		// public void mouseReleased(MouseEvent e){
		// System.out.println(e.getX()+"---"+e.getY());
		// }
		// });
		// 添加好友按钮
		addButton = new JLabel(add);
	//	addButton.setToolTipText("添加老乡朋友");
		addButton.setBounds(30, 0, 80, 71);
		addButton.addMouseListener(new MouseAdapter() {
		

			public void mouseEntered(MouseEvent e) {
				addButton.setIcon(add_on);
				tip = new MyToolTip(window.getLayeredPane(), "添加老乡朋友");

				tip.setLocation(addButton.getLocation().x  - 30,
						addButton.getLocation().y + 30 - tip.getHeight() + 15);
				tip.setVisible(true);
			}

			public void mouseExited(MouseEvent e) {
				addButton.setIcon(add);
				tip.setVisible(false);
			}

			public void mousePressed(MouseEvent e) {
				addButton.setIcon(add_press);
			}

			public void mouseReleased(MouseEvent e) {
				addButton.setIcon(add);
			}
		});
		addButton.addMouseListener(new OpenSearchUIAction());
		headPanel.add(addButton);

		// 系统消息按钮
		systemMsgButton = new JLabel(systemMsg);
		//systemMsgButton.setToolTipText("查看您的系统消息");
		systemMsgButton.setBounds(120, 0, 80, 71);
		systemMsgButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				systemMsgButton.setBounds(120, 3, 80, 71);
				
			}

			public void mouseReleased(MouseEvent e) {
				systemMsgButton.setBounds(120, 0, 80, 71);
				systemMsgButton.setIcon(systemMsg);
			}

			public void mouseEntered(MouseEvent e) {
				systemMsgButton.setIcon(systemMsg_on);
				tip = new MyToolTip(window.getLayeredPane(), "查看系统信息!");

				tip.setLocation(systemMsgButton.getLocation().x  - 30,
						systemMsgButton.getLocation().y + 30 - tip.getHeight() + 15);
				tip.setVisible(true);
			}

			public void mouseExited(MouseEvent e) {
				systemMsgButton.setIcon(systemMsg);
				tip.setVisible(false);
			}
		});
		systemMsgButton.addMouseListener(new SystemMsgButtonAction());
		headPanel.add(systemMsgButton);
		// 搜索栏
		searchZone = new JLabel(search);
		searchZone.setBounds(205, 10, 189, 50);

		searchField = new JTextField();
		searchField.setOpaque(false);
		searchField.setBorder(BorderFactory.createEmptyBorder());
		searchField.setBounds(90, 18, 85, 16);
		InputAction s = new InputAction(searchField);
		searchField.addCaretListener(s);
		searchField.addFocusListener(s);

		searchZone.add(searchField);

		friendSearchButton = new JLabel(friendSearch);
		friendSearchButton.setBounds(388, 22, 40, 30);
		friendSearchButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				friendSearchButton.setIcon(friendSearch_on);
			}

			public void mouseExited(MouseEvent e) {
				friendSearchButton.setIcon(friendSearch);
			}

			public void mousePressed(MouseEvent e) {
				friendSearchButton.setIcon(friendSearch_press);
			}

			public void mouseReleased(MouseEvent e) {
				friendSearchButton.setIcon(friendSearch);
			}
		});
		friendSearchButton.addMouseListener(new SearchAction(searchField));
		headPanel.add(friendSearchButton);

		/* 客户端的个性化设置 */
		shezhiJLabel = new JLabel(shezhiIcon);
		shezhiJLabel.setBounds(660, 16, 40, 40);
		//shezhiJLabel.setToolTipText("编辑个性化设置");
		headPanel.add(shezhiJLabel);
		shezhiJLabel.addMouseListener(new MouseAdapter() {

			public void mouseExited(MouseEvent e) {
				shezhiJLabel.setIcon(new ImageIcon("image/shezhi.jpg"));
				tip.setVisible(false);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				shezhiJLabel.setIcon(new ImageIcon("image/shezhifak.jpg"));
				tip = new MyToolTip(window.getLayeredPane(), "编辑个性化设置");

				tip.setLocation(shezhiJLabel.getLocation().x  - 70,
						shezhiJLabel.getLocation().y + 30 - tip.getHeight() - 5);
				tip.setVisible(true);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					UIManager
							.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				new ClientSetUI().showUI();
				JFrame.setDefaultLookAndFeelDecorated(true);

				try {

					UIManager.setLookAndFeel(new McWinLookAndFeel());

				} catch (UnsupportedLookAndFeelException e1) {

					// TODO Auto-generated catch block

					e1.printStackTrace();

				}
			}

		});

		/* 编辑 */
		editorlJLabel = new JLabel(editor);
		editorlJLabel.setBounds(610, 16, 40, 40);
		headPanel.add(editorlJLabel);
	//	editorlJLabel.setToolTipText("秀我个性");

		// 监听
		editorlJLabel.addMouseListener(new MouseAdapter() {

			public void mouseExited(MouseEvent e) {
				editorlJLabel.setIcon(new ImageIcon("image/enter.jpg"));
				tip.setVisible(false);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				editorlJLabel.setIcon(new ImageIcon("image/enter_enter.jpg"));
				tip = new MyToolTip(window.getLayeredPane(), "秀我个性");

				tip.setLocation(editorlJLabel.getLocation().x  - 50,
						editorlJLabel.getLocation().y + 30 - tip.getHeight() -5);
				tip.setVisible(true);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					UIManager
							.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				new EditorFlagUI(Loginner.loginner).showUI();
				JFrame.setDefaultLookAndFeelDecorated(true);

				try {

					UIManager.setLookAndFeel(new McWinLookAndFeel());

				} catch (UnsupportedLookAndFeelException e1) {

					// TODO Auto-generated catch block

					e1.printStackTrace();

				}
			}

		});

		/* 音乐 */
		musicJLabel = new JLabel(musIcon);
		musicJLabel.setBounds(560, 16, 40, 40);
		headPanel.add(musicJLabel);
		//musicJLabel.setToolTipText("享受音乐");
		// 监听
		musicJLabel.addMouseListener(new MouseAdapter() {

			public void mouseExited(MouseEvent e) {
				musicJLabel.setIcon(new ImageIcon("image/music.jpg"));
				tip.setVisible(false);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				musicJLabel.setIcon(new ImageIcon("image/music_fake.jpg"));
				tip = new MyToolTip(window.getLayeredPane(), "享受音乐");

				tip.setLocation(musicJLabel.getLocation().x  - 50,
						musicJLabel.getLocation().y + 30 - tip.getHeight() -5);
				tip.setVisible(true);
			}

			@Override
			public void mouseClicked(MouseEvent e) {

				try {
					UIManager
							.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

				} catch (Exception e1) {
				}

				new MainFrame();
				JFrame.setDefaultLookAndFeelDecorated(true);

				try {

					UIManager.setLookAndFeel(new McWinLookAndFeel());

				} catch (UnsupportedLookAndFeelException e1) {

					// TODO Auto-generated catch block

					e1.printStackTrace();

				}
			}

		});

		// 欢迎辞
		textPane = new JTextPane();
		textPane.setOpaque(false);
		textPane.setBorder(BorderFactory.createEmptyBorder());
		textPane.setBounds(440, 25, 190, 40);
		headPanel.add(textPane);
		StyledDocument doc = textPane.getStyledDocument();
		Style style = textPane.addStyle("text", StyleContext
				.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE));
		StyleConstants.setBold(style, true);
		StyleConstants.setItalic(style, true);
		// StyleConstants.setForeground(new , Color.blue);
		StyleConstants.setFontSize(style, 17);
		try {
			doc.insertString(0, "你好，" + Loginner.loginner + "~~~", style);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ///////////////////////////

		Media = new JButton("音乐");
		// ///////////////////////////
		headPanel.add(searchZone);
		panel.add(Media);// 添加无效啊
		// /添加音乐按钮

		addButton1 = new JLabel(add);
		addButton1.setToolTipText("添加新漫友");
		addButton1.setBounds(30, 0, 80, 71);
		addButton1.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				addButton1.setIcon(add_on);
			}

			public void mouseExited(MouseEvent e) {
				addButton1.setIcon(add);
			}

			public void mousePressed(MouseEvent e) {
				addButton1.setIcon(add_press);
			}

			public void mouseReleased(MouseEvent e) {
				addButton1.setIcon(add);
			}
		});
		// addButton1.addMouseListener(new OpenSearchUIAction());
		headPanel.add(addButton1);
		// 最小化按钮
		hideButton = new JLabel(hide);
		hideButton.setToolTipText("最小化到托盘");
		hideButton.setBounds(695, 4, 47, 33);
		hideButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				hideButton.setIcon(hide_fake);
			}

			public void mouseExited(MouseEvent e) {
				hideButton.setIcon(hide);
			}

			public void mouseReleased(MouseEvent e) {
				window.dispose();
				frame.dispose();
			}
		});
		headPanel.add(hideButton);

		pifuJLabel = new JLabel(pifuIcon);
		pifuJLabel.setToolTipText("亲!换套风格吧~~~");
		pifuJLabel.setBounds(725, 38, 20, 20);
		pifuJLabel.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				pifuJLabel.setIcon(pifufak);
				
			}

			public void mouseExited(MouseEvent e) {
				pifuJLabel.setIcon(pifuIcon);
				
			}

			public void mouseClicked(MouseEvent e) {
				int random = (int) (Math.random() * 6);
				// final ImageIcon icon=new
				// ImageIcon("background/MainUIback"+3+".jpg");
				image = new ImageIcon("background/MainUIback" + random + ".jpg");
				panel = new JPanel() {
					public void paintComponent(Graphics g) {
						super.paintComponent(g);
						g.drawImage(image.getImage(), 0, 0, null);
						this.setOpaque(false);

					}
				};
				panel.repaint();
				window.dispose();
				frame.dispose();
				window.setVisible(true);
				frame.setVisible(true);
				pifuJLabel.setIcon(pifuIcon);

			}
		});
		headPanel.add(pifuJLabel);
		
		/*添加进入西南大学老乡荟的按钮*/
		
		myZoneButton = new JLabel(myZoneIcon);
		myZoneButton.setBounds(753, 38, 20, 20);
		myZoneButton.setToolTipText("进入社区");
		myZoneButton.addMouseListener(new MouseAdapter() {
		
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				myZoneButton.setIcon(myZoneIcon2);
			}
			
		
			public void mouseExited(MouseEvent e) {
				myZoneButton.setIcon(myZoneIcon);
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				/*这里实现跳转到社区的按钮*/
				
				
				/*这种方式只有jdk1.6及以上的版本才能实现，
				 * 好处是不用自己指定浏览器
				 * 而是调用用户的默认的浏览器*/
				Desktop desktop = Desktop.getDesktop();  
				try {
					desktop.browse(new URI("http://localhost:8080/HomecomingXD/index.jsp"));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
			}
		});
		
		headPanel.add(myZoneButton);
		
		
		
		
		
		
		// 关闭按钮

		
		
		
		
		
		closeButton = new JLabel(close);
		closeButton.setToolTipText("退出漫聊");
		closeButton.setBounds(740, 3, 50, 37);
		closeButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				closeButton.setIcon(close_fake);
			}

			public void mouseExited(MouseEvent e) {
				closeButton.setIcon(close);
			}
		});
		closeButton.addMouseListener(new MainUICloseAction());
		headPanel.add(closeButton);

		/**
		 * 好友列表栏
		 */
		createFriendPanel();

		window.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				frame.requestFocus();
				window.requestFocus();
				isDraging = true;
				xx = e.getX();
				yy = e.getY();
			}

			public void mouseReleased(MouseEvent e) {
				isDraging = false;
			}
		});
		window.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				if (isDraging) {
					int left = window.getLocation().x;
					int top = window.getLocation().y;
					window.setLocation(left + e.getX() - xx, top + e.getY()
							- yy);
				}
			}
		});

		frame.setUndecorated(true);
		window.setLocation(p);
		window.setVisible(true);
		frame.setVisible(true);

		if (!isTrayShowed) {
			showTray();
		}
	}

	public void createFriendPanel() {
		friendPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 3));
		friendPanel.setBorder(BorderFactory.createEmptyBorder());
		friendPanel.setOpaque(false);

		scroll = new JScrollPane(friendPanel);
		scroll.setBorder(BorderFactory.createEmptyBorder());
		scroll.setOpaque(false);
		scroll.getViewport().setOpaque(false);
		scroll
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setBounds(5, 75, 780, 130);
		// scroll.getViewport().setViewPosition(new Point(0,0));//到顶

	/*	scroll.getVerticalScrollBar().addAdjustmentListener(
				new AdjustmentListener() {

					public void adjustmentValueChanged(AdjustmentEvent evt) {
						int isNeedBottom=0;
						if (evt.getAdjustmentType() == AdjustmentEvent.TRACK
								&& isNeedBottom <= 3) {
							scroll.getVerticalScrollBar().setValue(
									scroll.getVerticalScrollBar()
											.getModel().getMaximum()
											- scroll
													.getVerticalScrollBar()
													.getModel().getExtent());
							isNeedBottom++;
						}
					}
				});*/

		// 添加个性标签项的监听器
		SpecificFlagAction specificFlagAction = new SpecificFlagAction();
		OpenChatUIAction openAction = new OpenChatUIAction();
		DeleteAction deleteAction = new DeleteAction();

		for (int i = 0; i < FriendList.getFriendList().size(); i++) {
			UserInfo user = FriendList.getFriendList().get(i);
			final String userName = user.getUserName();
			String image = user.getUserImage();
			String userSex = user.getUserSex();
			String userState = user.getUserState();

			ImageIcon headImage;

			if (userSex.equals("男")) {
				if (userState.equals("a")) {
					headImage = new ImageIcon("image/male/" + image
							+ "_fake.jpg");
				} else {
					headImage = new ImageIcon("image/male/" + image + ".jpg");
				}
			} else {
				if (userState.equals("a")) {
					headImage = new ImageIcon("image/female/" + image
							+ "_fake.jpg");
				} else {
					headImage = new ImageIcon("image/female/" + image + ".jpg");
				}
			}
			JLabel label = new JLabel(userName, headImage, 0);
			label.setVerticalAlignment(3);
			label.setHorizontalTextPosition(0);
			label.setVerticalTextPosition(3);
			// 给用户头像添加监听器
			label.addMouseListener(openAction);
			// 给用户头像添加弹出菜单

			JPopupMenu popup = new JPopupMenu(userName);
			JMenuItem itemMe = new JMenuItem("个性标签");
			// itemMe.addActionListener(specificFlagAction);
			itemMe.addMouseListener(new MouseListener() {

				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					try {

						UIManager
								.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
					} catch (Exception e1) {
					}
					new UserFlag(userName, Loginner.loginner).showUI();
					JFrame.setDefaultLookAndFeelDecorated(true);

					try {

						UIManager.setLookAndFeel(new McWinLookAndFeel());

					} catch (UnsupportedLookAndFeelException e1) {

						// TODO Auto-generated catch block

						e1.printStackTrace();

					}

				}

				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub

				}
			});
			popup.add(itemMe);
			JMenuItem item1 = new JMenuItem("聊天");
			item1.addActionListener(openAction);
			popup.add(item1);
			JMenuItem item2 = new JMenuItem("删除");
			item2.addActionListener(deleteAction);
			popup.add(item2);
			label.setComponentPopupMenu(popup);
			// 给用户头像添加ToolTip
		//	label.setToolTipText("双击该头像与该用户对话~！");
			label.addMouseListener(new MouseListener() {

				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					OperateToolTip.close();
					tip.setVisible(false);
				}

				@Override
				public void mouseEntered(MouseEvent e) {

					// 得到头像标签的位置信息
					JLabel label = (JLabel) e.getSource();

					tip = new MyToolTip(window.getLayeredPane(), "双击头像对话~~~");

					tip.setLocation(label.getLocation().x  - 70,
							label.getLocation().y + 30 - tip.getHeight() + 20+60);
					tip.setVisible(true);
					// 获取屏幕的高度与宽度的相关信息
					Dimension screenSize = Toolkit.getDefaultToolkit()
							.getScreenSize();
					int a = screenSize.width;// 宽度
					int b = screenSize.height;// 高度

					/*
					 * if((window.getLocation().x+label.getLocation().x)>(301+label
					 * .getLocation().x)){ new
					 * ToolTip(window.getLocation().x-301
					 * ,window.getLocation().y+
					 * label.getLocation().y).setToolTip(new
					 * ImageIcon(""),"这里是用户的签名档，以及个性面板,留待以后连接数据库取得信息"); }else{
					 * new
					 * ToolTip(window.getLocation().x+(int)window.getWidth(),
					 * window
					 * .getLocation().y+label.getLocation().y).setToolTip(new
					 * ImageIcon(""),"这里是用户的签名档，以及个性面板,留待以后连接数据库取得信息"); }
					 */

					/*
					 * 处理越界问题，这里只是处理了一部分，后续有待进一步的完善 较为容易
					 */

					GetUserFlag getUserFlag = new GetUserFlag(label.getText());
					String userString = getUserFlag.getUserFlagInfo(label
							.getText());
					// System.out.println(userString);
					// if((window.getLocation().y+window.getHeight())>(b))
					// {
					// System.out.println("超出屏幕了");
					// new
					// ToolTip(label.getLocation().x+40+200,window.getLocation().y-100).setToolTip(new
					// ImageIcon(""),"人生信条:"+userString);
					// }else if (window.getLocation().y<=200) {
					// new
					// ToolTip(label.getLocation().x+40+200,window.getLocation().y+window.getHeight()-60).setToolTip(new
					// ImageIcon(""),"人生信条:"+userString);

					// }

					// else
					GetComment comment = new GetComment(userName);
					System.out.println(comment.getCommentInfo(userName));
					ArrayList<CommentInfo> list = comment
							.getCommentInfo(userName);
					String liuyanString = "";
					for (int i = 0; i < list.size(); i++) {
						liuyanString = liuyanString
								+ list.get(i).getCommenter() + " : "
								+ list.get(i).getComment_content() + "\n";
					}
					new ToolTip(window.getLocation().x + window.getWidth(),
							window.getLocation().y).setToolTip(
							new ImageIcon(""), "   人生信条:\n" + userString + "\n"
									+ "            用户留言\n" + liuyanString);
					// }
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub

				}

			});

			friendPanel.add(label);

			// 保存好友头像标签
			Loginner.add(userName, label);
		}
		panel.add(scroll);
	}

	public void showTray() {
		if (SystemTray.isSupported()) {
			SystemTray tray = SystemTray.getSystemTray();

			// 托盘图片
			Image image = Toolkit.getDefaultToolkit().createImage(
					"image/manTray.png");

			TrayIcon icon = new TrayIcon(image, "我的漫聊");
			icon.setImageAutoSize(true);
			icon.addMouseListener(new MouseAdapter() {
				public void mouseReleased(MouseEvent e) {
					if (e.getButton() == MouseEvent.BUTTON1) {
						if (!UIMap.isConnected) {
							JOptionPane.showMessageDialog(null,
									"您现在处于离线状态，正在尝试自动连接.....");
							return;
						}
						MainUI mainUI = (MainUI) UIMap.temporaryStorage
								.get("mainUI");
						if (mainUI != null) {
							if (mainUI.getFrame().isVisible()) {
								mainUI.closeUI();
							} else {
								mainUI.getFrame().setVisible(true);
								mainUI.getWindow().setVisible(true);
							}
						}
					}
				}
			});
			// 添加弹出菜单
			PopupMenu popup = new PopupMenu();
			MenuItem show = new MenuItem("显示");
			show.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (!UIMap.isConnected) {
						JOptionPane.showMessageDialog(null,
								"您现在处于离线状态，正在尝试自动连接.....");
						return;
					}
					MainUI mainUI = (MainUI) UIMap.temporaryStorage
							.get("mainUI");
					if (mainUI != null) {
						if (mainUI.getFrame().isVisible()) {
							mainUI.getFrame().requestFocus();
							mainUI.getWindow().requestFocus();
						} else {
							mainUI.getWindow().setVisible(true);
							mainUI.getFrame().setVisible(true);
						}
					}
				}
			});
			popup.add(show);
			MenuItem reg = new MenuItem("注册");
			reg.addActionListener(new RegAction());
			popup.add(reg);
			MenuItem about = new MenuItem("关于");
			about.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					AboutUI aboutUI = (AboutUI) UIMap.temporaryStorage
							.get("aboutUI");
					if (aboutUI != null) {
						JOptionPane.showMessageDialog(null, "窗口已打开！");
					} else {
						aboutUI = new AboutUI();
						aboutUI.showUI();
						UIMap.storeObj("aboutUI", aboutUI);
					}
				}
			});
			popup.add(about);
			MenuItem exit = new MenuItem("退出");
			exit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					MainUICloseAction.closeOpration();
				}
			});
			popup.add(exit);

			icon.setPopupMenu(popup);
			try {
				tray.add(icon);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		isTrayShowed = true;
	}

	// public static void main(String args[]){
	// MainUI m = new MainUI("111");
	// m.showUI();
	// }
}
