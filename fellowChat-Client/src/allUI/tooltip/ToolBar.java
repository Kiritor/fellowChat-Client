package allUI.tooltip;

import java.awt.Color;
import java.util.Properties;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;

public class ToolBar extends JToolBar{
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

	private void toolBar() {
		JToolBar jToolBar= new JToolBar(JToolBar.HORIZONTAL);
		
		Icon newNodeIcon = null;
		Icon nodeNameIcon = null;
		Icon nodeLineIcon = null;
		Icon nodeSpaceIcon = null;
		Icon clearIcon = null;
		try {
			newNodeIcon = new ImageIcon("iamgess/nodeName.jpg");
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
	public JButton creatJButton(String buttonName, String buttonText,
			JButton button, Icon buttonIcon) {
		button = new JButton(buttonName, buttonIcon);// 初始化按钮
		button.setToolTipText(buttonText);// 设置按钮提示文本
		button.setBackground(Color.orange);// 设置按钮背景颜色
		jToolBar.add(button);
		return button;

	}

}
