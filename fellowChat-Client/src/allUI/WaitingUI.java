/*
 * �����Ļ������*/

package allUI;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;

import com.sun.awt.AWTUtilities;

public class WaitingUI {

	private ImageIcon image = new ImageIcon("image/connectionUI.jpg");
	
	private JWindow window;
	private JLabel label;
	private int xx;
	private int yy;
	private boolean isDraging;//�Ƿ��϶�
	
	public void showUI(){
		window = new JWindow();
		window.setSize(500, 400);
		window.setLayout(new GridLayout(1,1));
		/*ʹ����ʵ��Բ�ǵ�Ч��*/
		AWTUtilities.setWindowShape(window,  
			           new RoundRectangle2D.Double(0.0D, 0.0D, window.getWidth(),  
			        		   window.getHeight(), 26.0D, 26.0D));  
		label = new JLabel(image);
		window.add(label);
		//�Դ���ʵ�ּ���
		window.addMouseListener(new MouseAdapter() {      
            public void mousePressed(MouseEvent e) {
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
	
	public void closeUI(){
		window.dispose();
	}
	
    public static void main(String[] args) {
		new WaitingUI().showUI();
	}
}
