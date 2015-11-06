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
 * 类说明：ShortPath 界面类
 * 
 * @author 作者: LiuJunGuang
 * @version 创建时间：2010-5-29 下午02:15:05
 */
public class ShortPathFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = -1877582687108858783L;
	private Properties prop = null;
	private String begin = "Begin: ";// 始发地
	private String end = "End: ";// 目的地
	private String ok = "OK";// 确定键
	private String shortPath = "ShortPath";// 标题
	private String mouseLocation = "MouseLocation:";// 鼠标的位置
	private String okText = "Show Path when OK is click.";

	private JPanel nothJP = null, shouthJP = null, centerJP = null;
	private JLabel beginJLabel = null, endJLabel = null;
	private JLabel AtoBPathJLabel = null, mouseJLabel = null;

	private JComboBox beginJComboBox = null, endJComboBox = null;// 两个下拉选择框
	private JButton jButton = null;
	private JToolBar jToolBar = null;// 定义工具条
	private JButton newNodeJButton = null, nodeLineJButton = null,
			nodeNameJButton = null, nodeSpaceJButton = null,
			clearJButton = null;// 定义工具条按钮

	

	public ShortPathFrame() {
		// TODO Auto-generated constructor stub
		
		JWindow window = new JWindow(this);
		window.setSize(500, 375);
		window.setLayout(new GridLayout(1, 1));// 网格布局
		/*使窗体实现圆角的效果*/
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
		// 始发地
		nothJP = new JPanel();
		beginJLabel = new JLabel(begin);
		nothJP.add(beginJLabel);
		beginJComboBox = new JComboBox();
		beginJComboBox.setPreferredSize(new Dimension(150, 25));
		beginJComboBox.addActionListener(this);
		nothJP.add(beginJComboBox);
		nothJP.add(new JLabel("     "));// 添加空标签使得beginJComboBox与endJLabel之间有一定的间隙
		// 目的地
		endJLabel = new JLabel(end);
		nothJP.add(endJLabel);
		endJComboBox = new JComboBox();
		endJComboBox.setPreferredSize(new Dimension(150, 25));
		endJComboBox.addActionListener(this);
		nothJP.add(endJComboBox);
		jButton = new JButton(ok);
		jButton.setToolTipText(okText);// 确定 按钮添加提示文本
		jButton.addActionListener(this);
		nothJP.add(jButton);

		// 中间绘图区
		centerJP = new JPanel();
		toolBar();
		centerJP.setLayout(new BorderLayout());
		centerJP.add(jToolBar, BorderLayout.NORTH);
	
	

		// 状态栏
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

		// 设置流布局的流向
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
		if (e.getSource() == newNodeJButton) {// 新建结点
			setAtoBPathJLabel("你选择了新建圆结点！");
			
			
		} else if (e.getSource() == nodeNameJButton) {// 结点名称
			setAtoBPathJLabel("你选择了输入圆结点的名称！");
			JOptionPane.showMessageDialog(null, "请单击结点（圆圈）以确定要输入名字的结点！", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			
		} else if (e.getSource() == nodeLineJButton) {// 结点连线
			setAtoBPathJLabel("你选择了新建圆结点之间的连线！");
			
		} else if (e.getSource() == nodeSpaceJButton) {// 结点距离
			setAtoBPathJLabel("你选择了输入结点之间的距离！");
			JOptionPane.showMessageDialog(null, "请单击线段上方以确定要输入距离的线段！", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			
		} else if (e.getSource() == clearJButton) {// 清除
			
		} else if (e.getSource() == jButton) {// 确定
			
			int x = 1, y = 2;
			try {
				
					
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(this, "请输入结点的名称。", "警告",
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

	// 读取资源文件
	private void properties() {
		InputStream fis = null;
		try {
			fis = ShortPathFrame.class.getClassLoader().getResourceAsStream(
					"shortPath.properties");
			prop = new Properties();// 属性集合对象
			prop.load(fis);// 将属性文件流装载到Properties对象中
			fis.close();// 关闭流
		} catch (Exception e) {
			prop = null;
			JOptionPane.showMessageDialog(this, " 资源文件打开错误！", "警告",
					JOptionPane.WARNING_MESSAGE);
		}// 属性文件输入流
	}

	// 工具条的初始化
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
			JOptionPane.showMessageDialog(this, " 图片读取失败！", "警告",
					JOptionPane.WARNING_MESSAGE);
		}
		// 按钮提示文本
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

	// 创建工具条按钮 ，buttonName按钮要显示的文本， buttonText按钮的提示文本，
	// button按钮名称，buttonIcon 按钮图标
	public JButton creatJButton(String buttonName, String buttonText,
			JButton button, Icon buttonIcon) {
		button = new JButton(buttonName, buttonIcon);// 初始化按钮
		button.setToolTipText(buttonText);// 设置按钮提示文本
		button.setBackground(Color.orange);// 设置按钮背景颜色
		button.addActionListener(this);
		jToolBar.add(button);
		return button;

	}

	public Properties getProp() {
		return prop;
	}

	public void addItem(String s) {// 在下拉项中添加项
		this.beginJComboBox.addItem(s);
		this.endJComboBox.addItem(s);
	}

	public void removeAllItems() {// 移除下拉项中的所有项
		this.beginJComboBox.removeAllItems();
		this.endJComboBox.removeAllItems();
	}

	public void setStratBar(String s) {// 设置状态栏鼠标操作的接口
		mouseJLabel.setText(s);
	}

	public void setAtoBPathJLabel(String s) {// 设置状态栏操作的接口
		AtoBPathJLabel.setText(s);
	}
	public static void main(String[] args) {
		try {// 将界面设置为当前windows界面风格
			UIManager
			.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
		}
		new ShortPathFrame();
		
	}

}
