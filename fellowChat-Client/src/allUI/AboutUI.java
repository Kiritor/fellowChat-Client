package allUI;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JWindow;

import com.sun.awt.AWTUtilities;

import util.tools.UIMap;

public class AboutUI {

	private JWindow window;
	private JPanel panel;
	private JLabel closeButton;
	private JLabel okButton;
	
	private ImageIcon image = new ImageIcon("image/aboutUI.jpg");
	private ImageIcon close = new ImageIcon("image/closeButton.png");
	private ImageIcon close_fake = new ImageIcon("image/closeButton_fake.png");
	private ImageIcon ok = new ImageIcon("image/okButton.png");
	private ImageIcon ok_fake = new ImageIcon("image/okButton_fake.png");
	
	private int xx;
	private int yy;
	private boolean isDraging;
	
	public void showUI(){
		window = new JWindow();
		window.setSize(500,400);
		window.setLayout(new GridLayout(1,1));
		/*使窗体实现圆角的效果*/
		AWTUtilities.setWindowShape(window,  
			           new RoundRectangle2D.Double(0.0D, 0.0D, window.getWidth(),  
			               window.getHeight(), 18.0D, 18.0D));  
		panel = new JPanel(){
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				g.drawImage(image.getImage(), 0, 0, null);
				this.setOpaque(false);
			}
		};
		panel.setLayout(null);
		JTextPane j = new JTextPane();
		j.setBounds(10,10,48,33);
		//panel.add(j);
		window.add(panel);
		
		closeButton = new JLabel(close);
		closeButton.setBounds(446, 10, 48, 33);
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
				window.dispose();
				if(UIMap.temporaryStorage.get("aboutUI")!=null){
					UIMap.temporaryStorage.remove("aboutUI");
				}
			}
			public void mousePressed(MouseEvent e){
				closeButton.setBounds(446, 12, 48, 33);
			}
		});
		
		okButton = new JLabel(ok);
		okButton.setBounds(400, 350, 87, 38);
		panel.add(okButton);
		okButton.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				okButton.setBounds(400, 352, 87, 38);
			}
			public void mouseReleased(MouseEvent e){
				okButton.setBounds(400, 350, 87, 38);
				window.dispose();
				if(UIMap.temporaryStorage.get("aboutUI")!=null){
					UIMap.temporaryStorage.remove("aboutUI");
				}
			}
			public void mouseEntered(MouseEvent e){
				okButton.setIcon(ok_fake);
			}
			public void mouseExited(MouseEvent e){
				okButton.setIcon(ok);
			}
		});
		
		window.addMouseListener(new MouseAdapter() {      
            public void mousePressed(MouseEvent e) {
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
		
		window.setVisible(true);
		window.setLocationRelativeTo(null);
	}
	
	public static void main(String[] args) {
		new AboutUI().showUI();
	}
}
