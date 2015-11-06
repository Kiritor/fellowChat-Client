package allUI.tooltip;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.geom.RoundRectangle2D;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JWindow;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;

import com.sun.awt.AWTUtilities;
public class ToolTip {
  
	
	
    boolean isCanTop = true; // �Ƿ�Ҫ��������jre1.5���ϰ汾����ִ�У���
   
    private int xx_Width, yy_Height;//JToolTip��ʾ��ĺ�����������ꣻ

   
    public ToolTip(int xx,int yy) {    
     this.xx_Width=xx;
     this.yy_Height=yy;
        isCanTop = true;
       
      
        try { // ͨ�����÷�����ǿ�ƻ�֪�Ƿ�֧���Զ������ö���
            JWindow.class.getMethod("setAlwaysOnTop",
                    new Class[] { Boolean.class });
            
            
        } catch (Exception e) {
            isCanTop = false;
        }

    }

   
    class ToolTipModel extends JWindow {
        private static final long serialVersionUID = 1L;

        private JLabel showImage_Label = null;//ͼƬ���壻

        private JTextArea showMessage_Texa = null;//�������壻
       
        private JPanel inner_Panel=null;//�ڲ�JPanel��
       
        private JPanel external_Panel=null;//�ⲿJPanel��
        private int height;
        private int width;
        public ToolTipModel() {
            initComponents();
            /*ʹ����ʵ��Բ�ǵ�Ч��*/
    		AWTUtilities.setWindowShape(this,  
    			           new RoundRectangle2D.Double(0.0D, 0.0D, this.getWidth(),  
    			               this.getHeight(), 12.0D, 12.0D));  
        }

        private void initComponents() {
        	
        //	JTextArea.getFontMetrics(JTextArea.getFont).getStringWidth(String str);

            this.setSize(180, 225);//JToolTip�Ĵ�С���ã��ɾ������ã�Ҳ�ɴ���������ã���    
            this.getContentPane().add(getExternal_Panel());
        }
       
      private JPanel getExternal_Panel(){//�����ⲿJPanel��
       if(external_Panel==null){
        external_Panel=new JPanel(new BorderLayout(1, 1));
        external_Panel.setBackground(new Color(238, 238, 238));
              EtchedBorder etchedBorder = (EtchedBorder) BorderFactory
                      .createEtchedBorder(); 
              external_Panel.setBorder(etchedBorder); // �趨�ⲿ������ݱ߿�Ϊ�绯Ч��            
              external_Panel.add(getInner_Panel());// �����ڲ����
       }           
       return external_Panel;
      }
       
        private JPanel getInner_Panel(){//�����ڲ�JPanel��
         if(inner_Panel==null){
          inner_Panel=new JPanel();
          inner_Panel.setLayout(null);
                inner_Panel.setBackground(new Color(238, 238, 238));
                inner_Panel.add(get_IconLabel(), null);
                inner_Panel.add(get_Message(),null);       
         }                 
         return inner_Panel;
        }
        private JTextArea get_Message(){
         if(showMessage_Texa==null){
        	
          showMessage_Texa=new JTextArea(){
        	  private ImageIcon imageIcon=new ImageIcon("imagess/textbg2.jpg");

			Image image = imageIcon.getImage();

        //  Image grayImage = GrayFilter.createDisabledImage(image);
          {
            setOpaque(false);
          } // instance initializer


          public void paint(Graphics g) {
            g.drawImage(image, 0, 0, this);
            super.paint(g);
          }
        };
          showMessage_Texa.setBackground(new Color(238, 238, 238));
                showMessage_Texa.setMargin(new Insets(1, 1, 1, 1));//��������ı߿�������ı�֮��Ŀհס�
                showMessage_Texa.setLineWrap(true);
               
                showMessage_Texa.setWrapStyleWord(true);
                showMessage_Texa.setForeground(new Color(106,23,229));
                showMessage_Texa.setBounds(10, 10, 100, 100);
                showMessage_Texa.setSize(150, 200);
                showMessage_Texa.setText("����������"+'\n');
                showMessage_Texa.append("\n"); 

                JScrollPane scrollPane = new JScrollPane(showMessage_Texa, 

                        ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,

                        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

         }
         return showMessage_Texa;
        }
       
        private JLabel get_IconLabel(){
         if(showImage_Label==null){
          showImage_Label=new JLabel();  
          showImage_Label.setBounds(20, 20, 140, 225);
         } 
         return showImage_Label;
        }

        public void animate() {
            new OperateToolTip(this,isCanTop,xx_Width,yy_Height);
            OperateToolTip.begin();
        }

    }
   
   
   
    public void setToolTip(Icon icon, String msg) {
        ToolTipModel single = new ToolTipModel();
        if (icon != null) {
            single.get_IconLabel().setIcon(icon);
        }
        single.showMessage_Texa.setText(msg);
        single.animate();
    } 
}
 


