package allUI.tooltip;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.JWindow;
import javax.swing.UIManager;

import com.sun.awt.AWTUtilities;

/**
 * ��˵����ShortPath ������
 * 
 * @author ����: LiuJunGuang
 * @version ����ʱ�䣺2010-5-29 ����02:15:05
 */
public class ShortPathFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = -1877582687108858783L;
	private Properties prop = null;
	private String begin = "Begin: ";// ʼ����
	private String end = "End: ";// Ŀ�ĵ�
	private String ok = "OK";// ȷ����
	private String shortPath = "ShortPath";// ����
	private String mouseLocation = "MouseLocation:";// ����λ��
	private String okText = "Show Path when OK is click.";

	private JPanel nothJP = null, shouthJP = null, centerJP = null;
	private JLabel beginJLabel = null, endJLabel = null;
	private JLabel AtoBPathJLabel = null, mouseJLabel = null;

	private JComboBox beginJComboBox = null, endJComboBox = null;// ��������ѡ���
	private JButton jButton = null;
	private JToolBar jToolBar = null;// ���幤����
	private JButton newNodeJButton = null, nodeLineJButton = null,
			nodeNameJButton = null, nodeSpaceJButton = null,
			clearJButton = null;// ���幤������ť

	

	public ShortPathFrame() {
		// TODO Auto-generated constructor stub
		
		JWindow window = new JWindow(this);
		window.setSize(500, 375);
		window.setLayout(new GridLayout(1, 1));// ���񲼾�
		/*ʹ����ʵ��Բ�ǵ�Ч��*/
		AWTUtilities.setWindowShape(window,  
			           new RoundRectangle2D.Double(0.0D, 0.0D, window.getWidth(),  
			               window.getHeight(), 18.0D, 18.0D));  
	
		properties();
		if (prop != null) {
			begin = prop.getProperty("begin");
			end = prop.getProperty("end");
			ok = prop.getProperty("ok");
			shortPath = prop.getProperty("shortPath");
			mouseLocation = prop.getProperty("mouseLocation");
			okText = prop.getProperty("okText");
		}
		this.setTitle(shortPath);
		// ʼ����
		nothJP = new JPanel();
		beginJLabel = new JLabel(begin);
		nothJP.add(beginJLabel);
		beginJComboBox = new JComboBox();
		beginJComboBox.setPreferredSize(new Dimension(150, 25));
		beginJComboBox.addActionListener(this);
		nothJP.add(beginJComboBox);
		nothJP.add(new JLabel("     "));// ��ӿձ�ǩʹ��beginJComboBox��endJLabel֮����һ���ļ�϶
		// Ŀ�ĵ�
		endJLabel = new JLabel(end);
		nothJP.add(endJLabel);
		endJComboBox = new JComboBox();
		endJComboBox.setPreferredSize(new Dimension(150, 25));
		endJComboBox.addActionListener(this);
		nothJP.add(endJComboBox);
		jButton = new JButton(ok);
		jButton.setToolTipText(okText);// ȷ�� ��ť�����ʾ�ı�
		jButton.addActionListener(this);
		nothJP.add(jButton);

		// �м��ͼ��
		centerJP = new JPanel();
		toolBar();
		centerJP.setLayout(new BorderLayout());
		centerJP.add(jToolBar, BorderLayout.NORTH);
	
	

		// ״̬��
		shouthJP = new JPanel();
		shouthJP.setBackground(Color.LIGHT_GRAY);
		AtoBPathJLabel = new JLabel();
		mouseJLabel = new JLabel(mouseLocation);
		shouthJP.add(mouseJLabel);
		shouthJP.add(new JLabel("      "));
		shouthJP.add(AtoBPathJLabel);

		this.add(nothJP, BorderLayout.NORTH);
		this.add(shouthJP, BorderLayout.SOUTH);
		this.add(centerJP, BorderLayout.CENTER);

		// ���������ֵ�����
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		flowLayout.setHgap(5);
		shouthJP.setLayout(flowLayout);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		Toolkit toolkit = getToolkit();
		Dimension dim = toolkit.getScreenSize();
		this.setLocation((dim.width - 500) / 2, (dim.height - 500) / 2);
		pack();
	}

	

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == newNodeJButton) {// �½����
			setAtoBPathJLabel("��ѡ�����½�Բ��㣡");
			
			
		} else if (e.getSource() == nodeNameJButton) {// �������
			setAtoBPathJLabel("��ѡ��������Բ�������ƣ�");
			JOptionPane.showMessageDialog(null, "�뵥����㣨ԲȦ����ȷ��Ҫ�������ֵĽ�㣡", "��ʾ",
					JOptionPane.INFORMATION_MESSAGE);
			
		} else if (e.getSource() == nodeLineJButton) {// �������
			setAtoBPathJLabel("��ѡ�����½�Բ���֮������ߣ�");
			
		} else if (e.getSource() == nodeSpaceJButton) {// ������
			setAtoBPathJLabel("��ѡ����������֮��ľ��룡");
			JOptionPane.showMessageDialog(null, "�뵥���߶��Ϸ���ȷ��Ҫ���������߶Σ�", "��ʾ",
					JOptionPane.INFORMATION_MESSAGE);
			
		} else if (e.getSource() == clearJButton) {// ���
			
		} else if (e.getSource() == jButton) {// ȷ��
			
			int x = 1, y = 2;
			try {
				
					
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(this, "������������ơ�", "����",
						JOptionPane.WARNING_MESSAGE);
			}
			
		} else if (e.getSource() == beginJComboBox) {
			String string = "";
			try {
				string = beginJComboBox.getSelectedItem().toString();
			} catch (Exception e1) {
				string = "";
			}
			
		} else if (e.getSource() == endJComboBox) {
			String s2 = "";
			try {
				s2 = endJComboBox.getSelectedItem().toString();
			} catch (Exception e1) {
				s2 = "";
			}
			
		}
	}

	// ��ȡ��Դ�ļ�
	private void properties() {
		InputStream fis = null;
		try {
			fis = ShortPathFrame.class.getClassLoader().getResourceAsStream(
					"shortPath.properties");
			prop = new Properties();// ���Լ��϶���
			prop.load(fis);// �������ļ���װ�ص�Properties������
			fis.close();// �ر���
		} catch (Exception e) {
			prop = null;
			JOptionPane.showMessageDialog(this, " ��Դ�ļ��򿪴���", "����",
					JOptionPane.WARNING_MESSAGE);
		}// �����ļ�������
	}

	// �������ĳ�ʼ��
	private void toolBar() {
		jToolBar = new JToolBar(JToolBar.HORIZONTAL);
		Icon newNodeIcon = null;
		Icon nodeNameIcon = null;
		Icon nodeLineIcon = null;
		Icon nodeSpaceIcon = null;
		Icon clearIcon = null;
		try {
			newNodeIcon = new ImageIcon(getClass().getResource(
					"images/newNode.jpg"));
			nodeNameIcon = new ImageIcon(getClass().getResource(
					"images/nodeName.jpg"));
			nodeLineIcon = new ImageIcon(getClass().getResource(
					"images/nodeLine.jpg"));
			nodeSpaceIcon = new ImageIcon(getClass().getResource(
					"images/nodeSpace.jpg"));
			clearIcon = new ImageIcon(getClass()
					.getResource("images/clear.jpg"));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, " ͼƬ��ȡʧ�ܣ�", "����",
					JOptionPane.WARNING_MESSAGE);
		}
		// ��ť��ʾ�ı�
		String nodeString = "New a node.";
		String nodeLineString = "New a line between node and node.";
		String nodeNameString = "Put into a node name.";
		String nodeSpaceString = "Put into space between node and node.";
		String clearString = "Clear all picture and data.";
		String nodeNew = "New";
		String nodeLine = "Node Line";
		String nodeName = "Node Name";
		String nodeSpace = "Node Space";
		String clear = "Clear";
		if (prop != null) {
			nodeString = prop.getProperty("nodeString");
			nodeLineString = prop.getProperty("nodeLineString");
			nodeNameString = prop.getProperty("nodeNameString");
			nodeSpaceString = prop.getProperty("nodeSpaceString");
			clearString = prop.getProperty("clearString");
			nodeNew = prop.getProperty("nodeNew");
			nodeLine = prop.getProperty("nodeLine");
			nodeName = prop.getProperty("nodeName");
			nodeSpace = prop.getProperty("nodeSpace");
			clear = prop.getProperty("clear");

		}
		newNodeJButton = creatJButton(nodeNew, nodeString, newNodeJButton,
				newNodeIcon);
		nodeNameJButton = creatJButton(nodeName, nodeNameString,
				nodeNameJButton, nodeNameIcon);
		nodeLineJButton = creatJButton(nodeLine, nodeLineString,
				nodeLineJButton, nodeLineIcon);
		nodeSpaceJButton = creatJButton(nodeSpace, nodeSpaceString,
				nodeSpaceJButton, nodeSpaceIcon);
		clearJButton = creatJButton(clear, clearString, clearJButton, clearIcon);
	}

	// ������������ť ��buttonName��ťҪ��ʾ���ı��� buttonText��ť����ʾ�ı���
	// button��ť���ƣ�buttonIcon ��ťͼ��
	public JButton creatJButton(String buttonName, String buttonText,
			JButton button, Icon buttonIcon) {
		button = new JButton(buttonName, buttonIcon);// ��ʼ����ť
		button.setToolTipText(buttonText);// ���ð�ť��ʾ�ı�
		button.setBackground(Color.orange);// ���ð�ť������ɫ
		button.addActionListener(this);
		jToolBar.add(button);
		return button;

	}

	public Properties getProp() {
		return prop;
	}

	public void addItem(String s) {// ���������������
		this.beginJComboBox.addItem(s);
		this.endJComboBox.addItem(s);
	}

	public void removeAllItems() {// �Ƴ��������е�������
		this.beginJComboBox.removeAllItems();
		this.endJComboBox.removeAllItems();
	}

	public void setStratBar(String s) {// ����״̬���������Ľӿ�
		mouseJLabel.setText(s);
	}

	public void setAtoBPathJLabel(String s) {// ����״̬�������Ľӿ�
		AtoBPathJLabel.setText(s);
	}
	public static void main(String[] args) {
		try {// ����������Ϊ��ǰwindows������
			UIManager
			.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
		}
		new ShortPathFrame();
		
	}

}
