package allUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JWindow;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import util.readtxt.TextConvertor;
import util.tools.UIMap;

import com.jtattoo.plaf.mcwin.McWinLookAndFeel;
import com.sun.awt.AWTUtilities;

public class HositoryMsgUI {
	private JWindow window;
	private JPanel panel;
	private JLabel closeButton;
	private JLabel okButton;
	
	//添加清空消息按钮的标签
	private JLabel deleteJLabel;
	private ImageIcon deleteImageIcon=new ImageIcon("image/delete.gif");
	private ImageIcon deleteFakeIcon= new ImageIcon("image/deletefake.gif");
	
	/*添加一些信息*/
	private JLabel head;
	private ImageIcon headimaIcon=new ImageIcon("image/hositoryHead.gif");
	//添加文本域
	private JTextArea textArea;
	private ImageIcon areaIcon= new ImageIcon("image/hositoryArea.jpg");
	private ImageIcon image = new ImageIcon("image/hository.jpg");
	private ImageIcon close = new ImageIcon("image/closeButton.png");
	private ImageIcon close_fake = new ImageIcon("image/closeButton_fake.png");
	private ImageIcon ok = new ImageIcon("image/okButton.png");
	private ImageIcon ok_fake = new ImageIcon("image/okButton_fake.png");
	
	private int xx;
	private int yy;
	private boolean isDraging;
	
	public void showUI(String destination){
		final String md=destination;
		//添加清空消息记录的标签
		deleteJLabel = new JLabel(deleteFakeIcon);
		deleteJLabel.setSize(80, 30);
		deleteJLabel.setLocation(60, 380);
		
		
		/*处理头部的标签*/
		final JFrame frame=new JFrame();
		head=new JLabel("消★息★记★录",JLabel.CENTER);
		head.setFont(new Font("华文行楷", 0, 22));
		head.setBounds(10, 50, 320, 34);
		//添加文本域
		
		textArea=new JTextArea(){
	      	  

			Image image = areaIcon.getImage();

      //  Image grayImage = GrayFilter.createDisabledImage(image);
        {
          setOpaque(false);
        } // instance initializer


        public void paint(Graphics g) {
          g.drawImage(image, 0, 0, this);
          super.paint(g);
        }
      };
		textArea.setBounds(20,90, 310, 280);
		textArea.setFont(new Font("华文楷体", 0, 14));
		textArea.setForeground(new Color(33,14,7));
		textArea.setEditable(false);
		 textArea.setCaretColor(Color.red); //
		try {
			textArea.setText(TextConvertor.read("hositoryMsg/"+destination+".txt"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		JScrollPane scrollPane = new JScrollPane(textArea, 

                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,

                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(20,90, 310, 280);

		//window = new JWindow();
		//window.setSize(350,450);
		//window.setLayout(new GridLayout(1,1));
		frame.setSize(350,450);
		frame.setLayout(new GridLayout(1,1));
		frame.setUndecorated(true);
		/*使窗体实现圆角的效果*/
		AWTUtilities.setWindowShape(frame,  
			           new RoundRectangle2D.Double(0.0D, 0.0D, frame.getWidth(),  
			        		   frame.getHeight(), 18.0D, 18.0D));  
		panel = new JPanel(){
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				g.drawImage(image.getImage(), 0, 0, null);
				this.setOpaque(false);
			}
		};
		panel.add(scrollPane,JPanel.CENTER_ALIGNMENT);
		panel.add(head);
		panel.add(deleteJLabel);
		panel.setLayout(null);
		frame.add(panel);
		
		closeButton = new JLabel(close);
		closeButton.setBounds(300, 10, 48, 33);
		panel.add(closeButton);
		closeButton.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent e){
				closeButton.setIcon(close_fake);
			}
			public void mouseExited(MouseEvent e){
				closeButton.setIcon(close);
			}
			public void mouseReleased(MouseEvent e){
				closeButton.setBounds(446, 10, 48, 33);
				frame.dispose();
			    
			}
			public void mousePressed(MouseEvent e){
				closeButton.setBounds(446, 12, 48, 33);
			
			}
		});
		deleteJLabel.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent e){
				deleteJLabel.setIcon(deleteImageIcon);
			}
			public void mouseExited(MouseEvent e){
				deleteJLabel.setIcon(deleteFakeIcon);
			}
			public void mouseReleased(MouseEvent e){
				
			}
			public void mousePressed(MouseEvent e){
				if(md.endsWith(".txt"))
				{
					TextConvertor.delete(md);
					
					
					textArea.setText("");
					frame.dispose();
					
					frame.setVisible(true);
					textArea.setText("");
					
				}
				else {
					  TextConvertor.delete("hositoryMsg/"+md+".txt");
					  
					
						textArea.setText("");
						frame.dispose();
						
						frame.setVisible(true);
						textArea.setText("");
				}
			
			}
		});
		okButton = new JLabel(ok);
		okButton.setBounds(260, 380, 87, 38);
		panel.add(okButton);
		okButton.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				okButton.setBounds(400, 352, 87, 38);
			}
			public void mouseReleased(MouseEvent e){
				okButton.setBounds(400, 350, 87, 38);
				frame.dispose();
				
			}
			public void mouseEntered(MouseEvent e){
				okButton.setIcon(ok_fake);
			}
			public void mouseExited(MouseEvent e){
				okButton.setIcon(ok);
			}
		});
		
		frame.addMouseListener(new MouseAdapter() {      
            public void mousePressed(MouseEvent e) {
            	frame.requestFocus();
                isDraging = true;      
                xx = e.getX();      
                yy = e.getY();      
            }      
     
            public void mouseReleased(MouseEvent e) {      
                isDraging = false;      
            }      
        });      
		frame.addMouseMotionListener(new MouseMotionAdapter() {      
            public void mouseDragged(MouseEvent e) {      
                if (isDraging) {      
                    int left = frame.getLocation().x;      
                    int top = frame.getLocation().y;      
                    frame.setLocation(left + e.getX() - xx, top + e.getY() - yy);      
                }      
            }      
        });
		
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
	
	public static void main(String[] args) {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			
		} catch (Exception e) {
		}

		new HositoryMsgUI().showUI("aaa");
	}
}
