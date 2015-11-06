package allUI;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

import com.sun.awt.AWTUtilities;

import action.viewUIAction.CloseViewUI;

public class ViewUI extends Thread{
	private ImageIcon image = new ImageIcon("image/ViewUI.jpg");
	private ImageIcon close = new ImageIcon("image/chatUIcloseButton.jpg");
	private ImageIcon close_fake = new ImageIcon("image/chatUIcloseButton_fake.jpg");
	
	private String destination;
	
	private JLabel label;
	private JLabel closeButton;
	private JFrame frame;
	private JWindow window;
	private JPanel panel;
    
    private boolean localPlay = false;
    private boolean remotePlay = false;
    
    private boolean isViewRun = true;
    private boolean isShow = true;
    private boolean go = true;
    private int xx;
    private int yy;
    private boolean isDraging;
    
    public ViewUI(String destination){
    	this.destination = destination;
    }
    
    public void run(){
    	frame = new JFrame("与 "+destination+"视频中");
    	window = new JWindow(frame);
    	window.setSize(340, 555);
    	window.setLayout(new GridLayout(1,1));
    	/*使窗体实现圆角的效果*/
		AWTUtilities.setWindowShape(frame,  
			           new RoundRectangle2D.Double(0.0D, 0.0D, frame.getWidth(),  
			        		   frame.getHeight(), 26.0D, 26.0D));  
    	//铺底板
    	panel = new JPanel(){
    		public void paintComponent(Graphics g){
    			super.paintComponent(g);
    			g.drawImage(image.getImage(), 0, 0, null);
    			this.setOpaque(false);
    		}
    	};
    	window.add(panel);
    	panel.setLayout(null);
    	
    	//关闭按钮
    	closeButton = new JLabel(close);
    	closeButton.setBounds(135, 513, 76, 28);
    	panel.add(closeButton);
    	closeButton.addMouseListener(new MouseAdapter(){
    		public void mouseExited(MouseEvent e){
    			closeButton.setIcon(close);
    		}
    		public void mouseEntered(MouseEvent e){
    			closeButton.setIcon(close_fake);
    		}
    	});
    	closeButton.addMouseListener(new CloseViewUI(destination));
    	
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
    	window.setLocation(970, 30);
    	
    	//等待视频显示组件就绪
    	int count = 0;
    	while(!localPlay&&!remotePlay){
    		try{
    			if(!go){
    				return;
    			}
    			Thread.sleep(1000);
    			count++;
    			if(count>7){
    				break;
    			}
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    	}
    	
    	window.setVisible(true);
    	frame.setVisible(true);
    }
    
    public void addLocalPlayWindow(Component c){
    	c.setBounds(10, 270, 321, 241);
    	panel.add(c);
    }
    
    public void addRemotePlayWindow(Component c){
    	c.setBounds(10, 10, 321, 241);
    	panel.add(c);
    }
	
	public void closeViewUI(){
		isShow = false;
		window.dispose();
		frame.dispose();
		go = false;
	}
	
	public boolean isViewRunning(){
		return isViewRun;
	}
	
	public boolean isShowing(){
		return isShow;
	}
	
	public void localReady(){
		localPlay = true;
	}
	
	public void remoteReady(){
		remotePlay = true;
	}
	
	
    
//	public static void main(String args[]){
//		ViewUI v = new ViewUI();
//		v.play();
//	}
}
