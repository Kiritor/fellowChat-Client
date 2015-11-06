package allUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Robot;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import com.sun.awt.AWTUtilities;

import util.pojo.UserInfo;
import util.tools.BindCombox;
import util.tools.GetAreaList;
import util.tools.GetCityList;
import util.tools.GetUserList;
import util.tools.UIMap;
import action.searchUIAction.AddButtonAction;
import action.searchUIAction.AddressSearchAction;
import action.searchUIAction.CloseSearchUIAction;
import action.searchUIAction.DirectSearchAction;
import action.searchUIAction.PagedownAction;
import action.searchUIAction.PageupAction;
import action.searchUIAction.RandomSearchAction;

public class SearchUI {

	/* 添加按地址搜索的功能 */

	private JPanel addressPanel = null;
	private JLabel addressLabel = null;
	private JTextField addressTextField = null;// 地址
	private JLabel addressButton = null;
	private ImageIcon addressButton_icon = new ImageIcon(
			"image/searchButton.png");
	private ImageIcon addressButton_icon_fakeIcon = new ImageIcon(
			"image/searchButton_fake.png");

	private JLabel cityJLabel = null;// 地址市、省级的标签
	private JLabel areaJLabel = null;// 地址区、镇的标签
	private JComboBox<String> cityBox = null;// 地址市、省级的下拉列表
	private JComboBox<String> areaBox = null;// 地址区、镇的标签

	private ImageIcon searchUI = new ImageIcon("image/searchUI.jpg");
	private ImageIcon direct = new ImageIcon("image/directSearch.png");
	private ImageIcon direct_fake = new ImageIcon("image/directSearch_fake.png");
	private ImageIcon random = new ImageIcon("image/randomSearch.png");
	private ImageIcon random_fake = new ImageIcon("image/randomSearch_fake.png");
	private ImageIcon search = new ImageIcon("image/searchButton.png");
	private ImageIcon search_fake = new ImageIcon("image/searchButton_fake.png");
	private ImageIcon add = new ImageIcon("image/addButton.png");
	private ImageIcon add_on = new ImageIcon("image/addButton_on.png");
	private ImageIcon add_press = new ImageIcon("image/addButton_press.png");
	private ImageIcon close = new ImageIcon("image/closeButton.png");
	private ImageIcon close_fake = new ImageIcon("image/closeButton_fake.png");
	private ImageIcon front = new ImageIcon("image/front.png");
	private ImageIcon front_fake = new ImageIcon("image/front_fake.png");
	private ImageIcon next = new ImageIcon("image/next.png");
	private ImageIcon next_fake = new ImageIcon("image/next_fake.png");
	private ImageIcon headImage = null;

	// 添加按照安地域查找的选项按钮
	private ImageIcon addressIcon = new ImageIcon("image/address.png");
	private ImageIcon address_fakeIcon = new ImageIcon("image/address_fake.png");
	private JLabel addressJLabel;

	private JFrame frame;
	private JWindow window;
	private JPanel panel;
	private JLabel closeButton;
	private JLabel directButton;
	private JLabel randomButton;
	private JPanel searchPanel;
	private JTextField input;
	private JLabel userName;
	private JLabel searchButton;
	private JLabel resultImage;
	private JLabel resultName;
	private JLabel resultSex;
	private JLabel resultAge;
	private JLabel addButton;
	private JLabel frontButton;
	private JLabel nextButton;
	private JTable table;
	private int xx;
	private int yy;
	private boolean isDraging;

	private int index = 0;
	private ArrayList<UserInfo> infoList;
	private UserInfo userInfo;

	public void closeUI() {
		window.dispose();
		frame.dispose();
		UIMap.removeObj("search");
	}

	public void showUI() {
		frame = new JFrame("添加新乡友");
		window = new JWindow(frame);
		window.setSize(500, 378);
		window.setLayout(new GridLayout(1, 1));
		/* 使窗体实现圆角的效果 */
		AWTUtilities.setWindowShape(window, new RoundRectangle2D.Double(0.0D,
				0.0D, window.getWidth(), window.getHeight(), 26.0D, 26.0D));
		// 铺底板
		panel = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(searchUI.getImage(), 0, 0, null);
				this.setOpaque(false);
			}
		};
		panel.setLayout(null);
		window.add(panel);
		// panel.addMouseListener(new MouseAdapter(){
		// public void mouseReleased(MouseEvent e){
		// System.out.println(e.getX()+"---"+e.getY());
		// }
		// });
		// 关闭按钮区
		closeButton = new JLabel(close);
		closeButton.setBounds(435, 5, 48, 33);
		panel.add(closeButton);
		closeButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				closeButton.setIcon(close_fake);
			}

			public void mouseExited(MouseEvent e) {
				closeButton.setIcon(close);
			}
		});
		closeButton.addMouseListener(new CloseSearchUIAction(this));

		// 按钮区
		directButton = new JLabel(direct);
		directButton.setBounds(30, 80, 110, 45);

		panel.add(directButton);
		directButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				directButton.setBounds(30, 85, 110, 45);
			}

			public void mouseReleased(MouseEvent e) {
				directButton.setBounds(30, 80, 110, 45);
				directType();
			}

			public void mouseEntered(MouseEvent e) {
				directButton.setIcon(direct_fake);
			}

			public void mouseExited(MouseEvent e) {
				directButton.setIcon(direct);
			}
		});

		addressJLabel = new JLabel(addressIcon);
		addressJLabel.setBounds(320, 85, 60, 60);

		addressJLabel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				addressJLabel.setIcon(address_fakeIcon);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				addressJLabel.setIcon(addressIcon);
			}

			@Override
			public void mouseClicked(MouseEvent e) {

				addressType();

			}
		});
		addressPanel = new JPanel();
		addressPanel.setLayout(null);
		addressPanel.setOpaque(false);
		addressPanel.setBounds(0, 120, 500, 258);
		panel.add(addressJLabel);
		randomButton = new JLabel(random);
		randomButton.setBounds(145, 80, 110, 45);

		panel.add(randomButton);
		randomButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				randomButton.setBounds(145, 85, 110, 45);
			}

			public void mouseReleased(MouseEvent e) {
				randomButton.setBounds(145, 80, 110, 45);
				randomType();
			}

			public void mouseEntered(MouseEvent e) {
				randomButton.setIcon(random_fake);
			}

			public void mouseExited(MouseEvent e) {
				randomButton.setIcon(random);
			}
		});
		randomButton.addMouseListener(new RandomSearchAction(this));

		/* 这里定义一个地址选择的容器用来实现查找某个地区的人得信息的功能 */

		// 通过地址查找区域

		// 精确查找区
		searchPanel = new JPanel();
		searchPanel.setLayout(null);
		searchPanel.setOpaque(false);
		searchPanel.setBounds(0, 120, 500, 258);
		panel.add(searchPanel);

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
					window.setLocation(left + e.getX() - xx, top + e.getY()
							- yy);
				}
			}
		});

		directType();

		frame.setUndecorated(true);
		frame.setVisible(true);
		window.setVisible(true);
		window.setLocationRelativeTo(null);
	}

	public void showDirectResult(UserInfo user) {
		userInfo = user;
		String path = null;
		if (user.getUserSex().equals("男")) {
			path = "image/male/" + user.getUserImage() + ".jpg";
		} else {
			path = "image/female/" + user.getUserImage() + ".jpg";
		}
		headImage = new ImageIcon(path);
		resultImage.setIcon(headImage);
		String name = "姓  名 ：  " + user.getUserName();
		resultName.setText(name);
		String sex = "性  别 ：  " + user.getUserSex();
		resultSex.setText(sex);
		String age = "年  龄 ：  " + user.getUserAge();
		resultAge.setText(age);
		searchPanel.updateUI();

		addButton.setIcon(add);
		addButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				addButton.setIcon(add_press);
				addButton.setBounds(350, 105, 86, 86);
			}

			public void mouseReleased(MouseEvent e) {
				addButton.setIcon(add_on);
				addButton.setBounds(350, 100, 86, 86);
			}

			public void mouseEntered(MouseEvent e) {
				addButton.setIcon(add_on);
			}

			public void mouseExited(MouseEvent e) {
				addButton.setIcon(add);
			}
		});
		addButton.addMouseListener(new AddButtonAction(this));

		panel.updateUI();
	}

	public void directType() {
		infoList = null;
		// 清空searchPanel
		searchPanel.removeAll();

		userName = new JLabel("请输入对方账号：");
		userName.setBounds(30, 50, 120, 20);
		searchPanel.add(userName);

		input = new JTextField();
		input.setBounds(155, 45, 150, 25);
		searchPanel.add(input);
		input.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10) {
					try {
						Robot r = new Robot();
						r.mouseMove(searchButton.getLocationOnScreen().x + 30,
								searchButton.getLocationOnScreen().y + 15);
						r.mouseRelease(MouseEvent.BUTTON1_MASK);
					} catch (Exception ef) {
						ef.printStackTrace();
					}
				}
			}
		});

		searchButton = new JLabel(search);
		searchButton.setBounds(310, 40, 87, 38);
		searchPanel.add(searchButton);
		searchButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				searchButton.setBounds(310, 45, 87, 38);
			}

			public void mouseReleased(MouseEvent e) {
				searchButton.setBounds(310, 40, 87, 38);
			}

			public void mouseEntered(MouseEvent e) {
				searchButton.setIcon(search_fake);
			}

			public void mouseExited(MouseEvent e) {
				searchButton.setIcon(search);
			}
		});
		searchButton.addMouseListener(new DirectSearchAction(this));
		// 头像区
		resultImage = new JLabel();
		resultImage.setBounds(100, 100, 80, 80);
		searchPanel.add(resultImage);
		// 基本信息区
		resultName = new JLabel();
		resultName.setBounds(200, 100, 200, 25);
		searchPanel.add(resultName);

		resultSex = new JLabel();
		resultSex.setBounds(200, 130, 100, 25);
		searchPanel.add(resultSex);

		resultAge = new JLabel();
		resultAge.setBounds(200, 160, 100, 25);
		searchPanel.add(resultAge);
		// 添加按钮区
		addButton = new JLabel();
		addButton.setBounds(350, 100, 86, 86);
		searchPanel.add(addButton);

		panel.updateUI();
	}

	public void addressType() {
		infoList = null;
		// 清空searchPanel
		searchPanel.removeAll();

		addressLabel = new JLabel("请选择地址：");
		addressLabel.setBounds(50, 50, 80, 20);
		searchPanel.add(addressLabel);
		// 这里将输入框改为，下拉列表的选择框

		cityBox = new JComboBox<String>();
		cityBox.setBounds(133, 47, 55, 25);
		cityBox = BindCombox.comboxBind(cityBox, GetCityList.getCityList());
		// tips:以后这些数据从数据库中的到，绑定

		searchPanel.add(cityBox);
		cityJLabel = new JLabel("市、省");
		cityJLabel.setBounds(194, 50, 40, 20);
		searchPanel.add(cityJLabel);
		areaBox = new JComboBox<String>();
		areaBox.setBounds(240, 47, 55, 25);
		BindCombox.comboxBind(areaBox,
				GetAreaList.getAreaList(cityBox.getSelectedItem().toString()));
		searchPanel.add(areaBox);
		cityBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				areaBox.removeAllItems();
				BindCombox.comboxBind(areaBox, GetAreaList.getAreaList(cityBox
						.getSelectedItem().toString()));

			}
		});
		areaJLabel = new JLabel("区、镇");
		areaJLabel.setBounds(300, 50, 40, 20);
		searchPanel.add(areaJLabel);
		// addressTextField = new JTextField();
		// addressTextField.setBounds(155, 45, 150, 25);
		// searchPanel.add(addressTextField);
		/*
		 * addressTextField.addKeyListener(new KeyAdapter() { public void
		 * keyPressed(KeyEvent e) { if (e.getKeyCode() == 10) { try { Robot r =
		 * new Robot(); r.mouseMove(addressButton.getLocationOnScreen().x + 30,
		 * addressButton.getLocationOnScreen().y + 15);
		 * r.mouseRelease(MouseEvent.BUTTON1_MASK); } catch (Exception ef) {
		 * ef.printStackTrace(); } } } });
		 */

		addressButton = new JLabel(addressButton_icon);
		addressButton.setBounds(350, 40, 87, 38);
		searchPanel.add(addressButton);
		addressButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				addressButton.setBounds(350, 45, 87, 38);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				randomType();
				System.out.println("得到的地址信息是：" + getAddress());
				ArrayList<UserInfo> list = GetUserList.getList(getAddress());
				addressResult(list);
				panel.updateUI();
			}

			public void mouseReleased(MouseEvent e) {
				addressButton.setBounds(350, 40, 87, 38);
			}

			public void mouseEntered(MouseEvent e) {
				addressButton.setIcon(addressButton_icon_fakeIcon);
			}

			public void mouseExited(MouseEvent e) {
				addressButton.setIcon(addressButton_icon);
			}
		});
		// searchButton.addMouseListener(new DirectSearchAction(this));
		// 头像区
		resultImage = new JLabel();
		resultImage.setBounds(100, 100, 80, 80);
		searchPanel.add(resultImage);
		// 基本信息区
		resultName = new JLabel();
		resultName.setBounds(200, 100, 200, 25);
		searchPanel.add(resultName);

		resultSex = new JLabel();
		resultSex.setBounds(200, 130, 100, 25);
		searchPanel.add(resultSex);

		resultAge = new JLabel();
		resultAge.setBounds(200, 160, 100, 25);
		searchPanel.add(resultAge);
		// 添加按钮区
		addButton = new JLabel();
		addButton.setBounds(350, 100, 86, 86);
		searchPanel.add(addButton);

		panel.updateUI();

	}

	public void showRandomResult(ArrayList<UserInfo> list) {
		userInfo = null;

		infoList = list;
		int row = list.size();
		int col = 4;
		String path = null;
		ImageIcon icon = null;
		Object[][] obj = new Object[row][col];
		for (int i = 0; i < row; i++) {
			UserInfo user = list.get(i);
			for (int j = 0; j < col; j++) {
				if (j == 0) {
					if (user.getUserSex().equals("男")) {
						path = "image/male/" + user.getUserImage()
								+ "_mini.jpg";
						System.out.println("路径是：" + path);
					} else {
						path = "image/female/" + user.getUserImage()
								+ "_mini.jpg";
						System.out.println("路径是：" + path);
					}
					icon = new ImageIcon(path);
					obj[i][j] = icon;
				}
				if (j == 1) {
					obj[i][j] = user.getUserName();
					System.out.println("" + user.getUserName());
				}
				if (j == 2) {
					obj[i][j] = user.getUserSex();
				}
				if (j == 3) {
					obj[i][j] = user.getUserAge();
				}
			}
		}
		Model m = new Model(obj, row, col);
		table.setModel(m);

		panel.updateUI();
	}

	public void addressResult(ArrayList<UserInfo> list) {
		userInfo = null;

		infoList = list;
		int row = list.size();
		int col = 4;
		String path = null;
		ImageIcon icon = null;
		Object[][] obj = new Object[row][col];
		for (int i = 0; i < row; i++) {
			UserInfo user = list.get(i);
			for (int j = 0; j < col; j++) {
				if (j == 0) {
					if (user.getUserSex().equals("男")) {
						path = "image/male/" + user.getUserImage()
								+ "_mini.jpg";
						System.out.println("路径是：" + path);
					} else {
						path = "image/female/" + user.getUserImage()
								+ "_mini.jpg";
						System.out.println("路径是：" + path);
					}
					icon = new ImageIcon(path);
					obj[i][j] = icon;
				}
				if (j == 1) {
					obj[i][j] = user.getUserName();
					System.out.println("" + user.getUserName());
				}
				if (j == 2) {
					obj[i][j] = user.getUserSex();
				}
				if (j == 3) {
					obj[i][j] = user.getUserAge();
				}
			}
		}
		Model m = new Model(obj, row, col);
		table.setModel(m);

		panel.updateUI();
		System.out.println("更新组件");
	}

	public void randomType() {
		// 清空searchPanel
		searchPanel.removeAll();
		// 表格区
		table = new JTable();
		table.setBounds(20, 30, 320,200);
		table.setRowHeight(40);
		table.setShowGrid(false);
		table.setShowHorizontalLines(false);
		table.setShowVerticalLines(false);
		table.setSelectionBackground(new Color(189, 189, 188));
		TableColumnModel colModel = table.getColumnModel();
		if (table.getColumnCount() > 0) {
			colModel.getColumn(0).setPreferredWidth(45);
		}
		searchPanel.add(table);
		// 按钮区
		addButton = new JLabel(add);
		addButton.setBounds(370, 80, 86, 86);
		searchPanel.add(addButton);
		addButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				addButton.setIcon(add_press);
				addButton.setBounds(370, 85, 86, 86);
			}

			public void mouseReleased(MouseEvent e) {
				addButton.setIcon(add_on);
				addButton.setBounds(370, 80, 86, 86);
			}

			public void mouseEntered(MouseEvent e) {
				addButton.setIcon(add_on);
			}

			public void mouseExited(MouseEvent e) {
				addButton.setIcon(add);
			}
		});
		addButton.addMouseListener(new AddButtonAction(this));

		frontButton = new JLabel(front);
		frontButton.setBounds(370, 30, 87, 38);
		searchPanel.add(frontButton);
		frontButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				frontButton.setBounds(370, 35, 87, 38);
			}

			public void mouseReleased(MouseEvent e) {
				frontButton.setBounds(370, 30, 87, 38);
			}

			public void mouseEntered(MouseEvent e) {
				frontButton.setIcon(front_fake);
			}

			public void mouseExited(MouseEvent e) {
				frontButton.setIcon(front);
			}
		});
		frontButton.addMouseListener(new PageupAction(this));

		nextButton = new JLabel(next);
		nextButton.setBounds(370, 178, 87, 38);
		searchPanel.add(nextButton);
		nextButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				nextButton.setBounds(370, 183, 87, 38);
			}

			public void mouseReleased(MouseEvent e) {
				nextButton.setBounds(370, 178, 87, 38);
			}

			public void mouseEntered(MouseEvent e) {
				nextButton.setIcon(next_fake);
			}

			public void mouseExited(MouseEvent e) {
				nextButton.setIcon(next);
			}
		});
		nextButton.addMouseListener(new PagedownAction(this));

		panel.updateUI();
	}

	public ArrayList<UserInfo> getInfoList() {
		return infoList;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int i) {
		index = i;
	}

	public String getUserName() {
		return input.getText();
	}

	public String getAddress() {
		return areaBox.getSelectedItem().toString();
	}

	public UserInfo getDetectedUser() {
		return userInfo;
	}

	public int getSelection() {
		return table.getSelectedRow();
	}

	/**
	 * 内部类：表格的model
	 * 
	 * @author asus
	 * 
	 */
	private class Model implements TableModel {

		private Object[][] obj;
		private int row;
		private int col;

		public Model(Object[][] obj, int row, int col) {
			this.obj = obj;
			this.row = row;
			this.col = col;
		}

		public void addTableModelListener(TableModelListener l) {

		}

		public Class<?> getColumnClass(int columnIndex) {

			return obj[0][columnIndex].getClass();
		}

		public int getColumnCount() {

			return col;
		}

		public String getColumnName(int columnIndex) {

			return null;
		}

		public int getRowCount() {

			return row;
		}

		public Object getValueAt(int rowIndex, int columnIndex) {

			return obj[rowIndex][columnIndex];
		}

		public boolean isCellEditable(int rowIndex, int columnIndex) {

			return false;
		}

		public void removeTableModelListener(TableModelListener l) {

		}

		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

		}
	}

	// public static void main(String args[]){
	// SearchUI s = new SearchUI();
	// s.showUI();
	// }
}
