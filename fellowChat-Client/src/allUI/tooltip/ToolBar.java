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
		button = new JButton(buttonName, buttonIcon);// ��ʼ����ť
		button.setToolTipText(buttonText);// ���ð�ť��ʾ�ı�
		button.setBackground(Color.orange);// ���ð�ť������ɫ
		jToolBar.add(button);
		return button;

	}

}
