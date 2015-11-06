package allUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JWindow;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import com.jtattoo.plaf.mcwin.McWinLookAndFeel;
import com.sun.awt.AWTUtilities;
import com.sun.org.apache.bcel.internal.generic.NEW;

import util.hositoryMsg.FileHositoryMsg;
import util.hositoryMsg.HositoryMsg;
import util.tools.MediaControlTools;
import util.tools.TextStyle;
import action.chatUIAction.CloseChatUIAction;
import action.chatUIAction.FileButton;
import action.chatUIAction.IconAction;
import action.chatUIAction.RemoteBreakButton;
import action.chatUIAction.RemoteControlRequest;
import action.chatUIAction.SendMsgButton;
import action.chatUIAction.TextListener;
import action.chatUIAction.ViewRequestButton;

public class ChatUI {
	
	/*增加消息记录的组件*/
    private JLabel hository;//消息历史记录标签
    private ImageIcon hositoryIcon= new ImageIcon("image/message_hository.jpg");
    private ImageIcon hositoryFakeIcon = new ImageIcon("image/message_hository_fake.jpg");
	
	private String destination;
	
	private boolean isDraging;
	private int xx;
	private int yy;
	
	private ImageIcon image = new ImageIcon("image/chatUI.jpg");
	private ImageIcon view = new ImageIcon("image/viewButton.jpg");
	private ImageIcon view_fake = new ImageIcon("image/viewButton_fake.jpg");
	private ImageIcon send = new ImageIcon("image/sendButton.jpg");
	private ImageIcon send_fake = new ImageIcon("image/sendButton_fake.jpg");
	private ImageIcon remote = new ImageIcon("image/remoteButton.jpg");
	private ImageIcon remote_fake = new ImageIcon("image/remoteButton_fake.jpg");
	private ImageIcon file = new ImageIcon("image/fileButton.jpg");
	private ImageIcon file_fake = new ImageIcon("image/fileButton_fake.jpg");
	private ImageIcon close = new ImageIcon("image/chatUIcloseButton.jpg");
	private ImageIcon close_fake = new ImageIcon("image/chatUIcloseButton_fake.jpg");
	private ImageIcon remoteBreak = new ImageIcon("image/breakButton.jpg");
	private ImageIcon remoteBreak_fake = new ImageIcon("image/breakButton_fake.jpg");
	private ImageIcon close2 = new ImageIcon("image/closeButton.png");
	private ImageIcon close2_fake = new ImageIcon("image/closeButton_fake.png");
	private ImageIcon imageIcon = new ImageIcon("image/imageIcon.png");
	private ImageIcon imageIcon_fake = new ImageIcon("image/imageIcon_fake.png");
	private ImageIcon empty = new ImageIcon("image/empty.png");
	
	private JFrame frame;
	private JWindow window;
	private JPanel panel;
	private JLabel headLine;
	private JScrollPane scroll1;
	private JScrollPane scroll2;
	private JScrollPane scroll3;
	private JPanel fileBasePanel;
	private JPanel filePanel;
	private JTextPane area1;
	private JTextPane area2;
	private JLabel viewButton;
	private JLabel sendButton;
	private JLabel remoteButton;
	private JLabel fileButton;
	private JLabel closeButton;
	private JLabel remoteBreakButton;
	private JLabel closeButton2;
	private JLabel imageIconButton;
	
	private StyledDocument doc1;
	private StyledDocument doc2;
	private Style defaultStyle = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
	
	private boolean requestNotSend = true;
	
	public ChatUI(String destination){
		this.destination = destination;
	}
	
	public JLabel getImageIconButton(){
		return imageIconButton;
	}
	
	/*这里就得到了消息的列表了*/
	private ArrayList<String> getValue(String msg){
		int start = 0;
		int end = 0;
		int location = 0;
		ArrayList<String> list = new ArrayList<String>();
		while(location<msg.length()){
    		start = msg.indexOf("<-",location);
        	if(start!=-1){
        		end = msg.indexOf("->",start);
        		if(end!=-1){
        			list.add(msg.substring(location,start));
        			list.add(msg.substring(start,end+2));
        			location = end+2;
        		}else{
        			list.add(msg);
        			break;
        		}
        	}else{
        		list.add(msg.substring(location));
        		break;
        	}
    	}
		return list;
	}
	
	public void appendMsg(String fontFamily,int fontSize,String msg,boolean flag,String sender){
		Style style = doc1.addStyle("text", defaultStyle);
		if(fontFamily==null){
			StyleConstants.setFontFamily(style, "SansSerif");
		}else{
			StyleConstants.setFontFamily(style, fontFamily);
		}
		StyleConstants.setBold(style, true);
		StyleConstants.setFontSize(style, fontSize);
		StyleConstants.setForeground(style, Color.gray);
		
		ArrayList<String> list = getValue(msg+"\r\n \r\n");
		try{
			for(String str : list){
				if(str.startsWith("<-")&&str.endsWith("->")){
					ImageIcon img = new ImageIcon("image/chatIcon/"+str.substring(2,str.length()-2)+".png");
					style = doc1.addStyle("icon", defaultStyle);
					StyleConstants.setIcon(style, img);
					doc1.insertString(doc1.getLength(), str, doc1.getStyle("icon"));
				}else{
					doc1.insertString(doc1.getLength(), str, doc1.getStyle("text"));
				}
				String msgString=doc1.getText(0, doc1.getLength());
				if(!flag)
				{
				 FileHositoryMsg.record(msgString,sender);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			Thread.sleep(80);
		}catch(Exception e){
			e.printStackTrace();
		}
		scroll1.getVerticalScrollBar().setValue(scroll1.getVerticalScrollBar().getMaximum());
	}
	
	public void area2InsertIcon(int index){
		ImageIcon img = new ImageIcon("image/chatIcon/"+index+".png");
		if(img!=null){
			try{
				Style style = doc2.addStyle("icon", defaultStyle);
				StyleConstants.setIcon(style, empty);
				doc2.insertString(doc2.getLength(), "<-"+index+"->", doc2.getStyle("icon"));
				area2.insertIcon(img);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public void area2RequestFocus(){
		area2.requestFocus();
	}
	
	public void addFileView(JPanel fileView){
		MediaControlTools.addFileView(fileView);
		resetFileBasePanel();
	}
	
	public void removeFileView(JPanel fileView){
		MediaControlTools.removeFileView(fileView);
		resetFileBasePanel();
	}
	
	public void closeUI(){
		window.dispose();
		frame.dispose();
	}
	
	public void resetFileBasePanel(){
		fileBasePanel.removeAll();
		filePanel = new JPanel(new FlowLayout());
		filePanel.setPreferredSize(new Dimension(190,80*MediaControlTools.fileViewList.size()));
		filePanel.setOpaque(false);
		
		for(JPanel p : MediaControlTools.fileViewList){
			filePanel.add(p);
		}
		scroll3 = new JScrollPane(filePanel);
		scroll3.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll3.setBorder(BorderFactory.createEmptyBorder());
		scroll3.getViewport().setOpaque(false);
		scroll3.setOpaque(false);
		scroll3.getVerticalScrollBar().setValue(scroll3.getVerticalScrollBar().getMaximum());
		fileBasePanel.add(scroll3);
		fileBasePanel.updateUI();
	}
	
	public boolean getRequestState(){
		return requestNotSend;
	}
	
	public void switchRequestState(){
		requestNotSend = !requestNotSend;
	}
	
	public void setBreakButton(){
		remoteBreakButton = new JLabel(remoteBreak);
		remoteBreakButton.setBounds(400, 303, 76, 28);
		panel.add(remoteBreakButton);
		panel.updateUI();
		remoteBreakButton.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent e){
				remoteBreakButton.setIcon(remoteBreak_fake);
				remoteBreakButton.setBounds(400, 304, 76, 28);
			}
			public void mouseExited(MouseEvent e){
				remoteBreakButton.setIcon(remoteBreak);
				remoteBreakButton.setBounds(400, 303, 76, 28);
			}
			public void mousePressed(MouseEvent e){
				remoteBreakButton.setBounds(400, 308, 76, 28);
			}
			public void mouseReleased(MouseEvent e){
				remoteBreakButton.setBounds(400, 303, 76, 28);
			}
		});
		remoteBreakButton.addMouseListener(new RemoteBreakButton(destination));
	}
	
	public void removeBreakButton(){
			remoteBreakButton.setIcon(null);
			panel.remove(remoteBreakButton);
			panel.updateUI();
	}
    
	public void showUI(){
		frame = new JFrame("与 "+destination+" 聊天中");
		window = new JWindow(frame);
		window.setSize(600, 450);
		window.setLayout(new GridLayout(1,1));
		/*使窗体实现圆角的效果*/
		AWTUtilities.setWindowShape(window,  
			           new RoundRectangle2D.Double(0.0D, 0.0D, window.getWidth(),  
			               window.getHeight(), 26.0D, 26.0D));  
		//铺底板
		panel = new JPanel(){
		    public void paintComponent(Graphics g){
		    	super.paintComponent(g);
		    	g.drawImage(image.getImage(), 0, 0, null);
		    	this.setOpaque(false);
		    }
		};
		panel.setLayout(null);
		window.add(panel);
//		panel.addMouseListener(new MouseAdapter(){
//			public void mouseReleased(MouseEvent e){
//				System.out.println(e.getX()+"---"+e.getY());
//			}
//		});
		//标题区
		headLine = new JLabel("与 "+destination+" 聊天中");
		headLine.setBounds(56, 35, 200, 20);
		headLine.setForeground(Color.gray);
		panel.add(headLine);
		//历史消息按钮区
		hository = new JLabel(hositoryIcon);
		panel.add(hository);
		hository.setBounds(290,25, 80, 26);
		hository.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				try {
					UIManager
							.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
					
				} catch (Exception e3) {
				}
				new HositoryMsgUI().showUI(destination);
				JFrame.setDefaultLookAndFeelDecorated(true);

				 try {

				 UIManager.setLookAndFeel(new McWinLookAndFeel());

				 } catch (UnsupportedLookAndFeelException e1) {

				 // TODO Auto-generated catch block

				 e1.printStackTrace();

				 }
			}
			public void mouseReleased(MouseEvent e){
				
			}
			public void mouseEntered(MouseEvent e){
				hository.setIcon(hositoryFakeIcon);
			}
			public void mouseExited(MouseEvent e){
				hository.setIcon(hositoryIcon);
			}
		});
		
		
		//聊天区
		area1 = new JTextPane();
		doc1 = area1.getStyledDocument();
		area1.setEditable(false);
		area1.setOpaque(false);
		area1.setBorder(BorderFactory.createEmptyBorder());
		scroll1 = new JScrollPane(area1);
		scroll1.setBorder(BorderFactory.createEmptyBorder());
		scroll1.setOpaque(false);
		scroll1.getViewport().setOpaque(false);
		scroll1.setBounds(70, 54, 330, 224);
		panel.add(scroll1);
		
		//工具按钮栏
		imageIconButton = new JLabel(imageIcon);
		imageIconButton.setBounds(56, 270, 80, 26);
		panel.add(imageIconButton);
		imageIconButton.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				imageIconButton.setBounds(56, 284, 80, 26);
			}
			public void mouseReleased(MouseEvent e){
				imageIconButton.setBounds(56, 284, 80, 26);
				imageIconButton.requestFocus();
			}
			public void mouseEntered(MouseEvent e){
				imageIconButton.setIcon(imageIcon_fake);
			}
			public void mouseExited(MouseEvent e){
				imageIconButton.setIcon(imageIcon);
				imageIconButton.setBounds(56, 284, 80, 26);
			}
		});
		imageIconButton.addFocusListener(new IconAction(this));
		
		//消息编辑发送区
		area2 = new JTextPane();
		doc2 = area2.getStyledDocument();
		Style style = doc2.addStyle("text", defaultStyle);
		StyleConstants.setFontFamily(style, TextStyle.fontFamily);
		StyleConstants.setFontSize(style, TextStyle.fontSize);
		StyleConstants.setBold(style, true);
		StyleConstants.setForeground(style, Color.gray);
		doc2.setLogicalStyle(0, style);
		area2.setEditable(true);
		area2.setOpaque(false);
		area2.setBorder(BorderFactory.createEmptyBorder());
		area2.addKeyListener(new SendMsgButton(area1,area2,destination));
//		area2.addKeyListener(new TextListener(area2,doc2,style));
		scroll2 = new JScrollPane(area2);
		scroll2.setBorder(BorderFactory.createEmptyBorder());
		scroll2.setOpaque(false);
		scroll2.getViewport().setOpaque(false);
		scroll2.setBounds(74, 310, 300, 96);
		panel.add(scroll2);
		
		//文件发送区
		fileBasePanel = new JPanel(new GridLayout(1,1));
		fileBasePanel.setOpaque(false);
//		filePanel = new JPanel(new FlowLayout());
//		filePanel.setPreferredSize(new Dimension(190,10));
//		filePanel.setOpaque(false);
//		scroll3 = new JScrollPane(filePanel);
//		scroll3.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
//		scroll3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
//		scroll3.setBorder(BorderFactory.createEmptyBorder());
//		scroll3.getViewport().setOpaque(false);
//		scroll3.setOpaque(false);
//		fileBasePanel.add(scroll3);
		fileBasePanel.setBounds(392, 40, 190, 230);
		panel.add(fileBasePanel);
		
		//远程断开按钮区
		if(MediaControlTools.getRemoteControlState()){
			setBreakButton();
		}
		
		//叉叉按钮
		closeButton2 = new JLabel(close2);
		closeButton2.setBounds(540, 5, 48, 33);
		panel.add(closeButton2);
		closeButton2.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent e){
				closeButton2.setIcon(close2_fake);
				closeButton2.setBounds(540, 6, 48, 33);
			}
			public void mouseExited(MouseEvent e){
				closeButton2.setIcon(close2);
				closeButton2.setBounds(540, 5, 48, 33);
			}
		});
		closeButton2.addMouseListener(new CloseChatUIAction(this,destination));
		
		//视频按钮区
		viewButton = new JLabel(view);
		viewButton.setBounds(472, 293, 76, 28);
		panel.add(viewButton);
		viewButton.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent e){
				viewButton.setIcon(view_fake);
				viewButton.setBounds(472, 294, 76, 28);
			}
			public void mouseExited(MouseEvent e){
				viewButton.setIcon(view);
				viewButton.setBounds(472, 293, 76, 28);
			}
		});
		viewButton.addMouseListener(new ViewRequestButton(destination));
		
		//发送按钮区
		sendButton = new JLabel(send);
		sendButton.setBounds(380, 330, 76, 28);
		sendButton.setToolTipText("按住ctrl+enter发送");
		panel.add(sendButton);
		sendButton.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent e){
				sendButton.setIcon(send_fake);
				sendButton.setBounds(380, 331, 76, 28);
			}
			public void mouseExited(MouseEvent e){
				sendButton.setIcon(send);
				sendButton.setBounds(380, 330, 76, 28);
			}
			public void mousePressed(MouseEvent e){
				sendButton.setBounds(380, 335, 76, 28);
			}
			public void mouseReleased(MouseEvent e){
				sendButton.setBounds(380, 330, 76, 28);
			}
		});
		sendButton.addMouseListener(new SendMsgButton(area1,area2,destination));
		
		//远程控制按钮区
		remoteButton = new JLabel(remote);
		remoteButton.setBounds(472, 332, 76, 28);
		panel.add(remoteButton);
		remoteButton.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent e){
				remoteButton.setIcon(remote_fake);
				remoteButton.setBounds(472,333,76,28);
			}
			public void mouseExited(MouseEvent e){
				remoteButton.setIcon(remote);
				remoteButton.setBounds(472, 332, 76, 28);
			}
			public void mousePressed(MouseEvent e){
				remoteButton.setBounds(472, 334, 76, 28);
			}
			public void mouseReleased(MouseEvent e){
				remoteButton.setBounds(472, 332, 76, 28);
			}
		});
		remoteButton.addMouseListener(new RemoteControlRequest(destination));
		
		//文件传输按钮区
		fileButton = new JLabel(file);
		fileButton.setBounds(380, 376, 76, 28);
		panel.add(fileButton);
		fileButton.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent e){
				fileButton.setIcon(file_fake);
				fileButton.setBounds(380, 377, 76, 28);
			}
			public void mouseExited(MouseEvent e){
				fileButton.setIcon(file);
				fileButton.setBounds(380, 376, 76, 28);
			}
			public void mousePressed(MouseEvent e){
				fileButton.setBounds(380, 374, 76, 28);
			}
			public void mouseReleased(MouseEvent e){
				fileButton.setBounds(380, 377, 76, 28);
			}
		});
		fileButton.addMouseListener(new FileButton(destination));
		
		//关闭按钮区
		closeButton = new JLabel(close);
		closeButton.setBounds(472, 376, 76, 28);
		panel.add(closeButton);
		closeButton.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent e){
				closeButton.setIcon(close_fake);
				closeButton.setBounds(472, 377, 76, 28);
			}
			public void mouseExited(MouseEvent e){
				closeButton.setIcon(close);
				closeButton.setBounds(472,376,76,28);
			}
			public void mousePressed(MouseEvent e){
				closeButton.setBounds(472, 371, 76, 28);
			}
			public void mouseReleased(MouseEvent e){
				closeButton.setBounds(472, 376, 76, 28);
			}
		});
		closeButton.addMouseListener(new CloseChatUIAction(this,destination));
		
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
                    window.setLocation(left + e.getX() - xx, top + e.getY() - yy);      
                }      
            }      
        });
		
		frame.setUndecorated(true);
		window.setLocation(370, 50);
		window.setVisible(true);
		frame.setVisible(true);
		
		area2.requestFocus();
	}
	
//	public static void main(String args[]){
//		ChatUI ui = new ChatUI("sss");
//		ui.showUI();
//	}
}
