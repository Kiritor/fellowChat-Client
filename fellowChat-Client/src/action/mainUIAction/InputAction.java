package action.mainUIAction;

import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import util.pojo.UserInfo;
import util.tools.FriendList;

public class InputAction implements CaretListener,FocusListener{
	
	private JTextField text;
	private JTable table;
	private JWindow window;
	private ArrayList<UserInfo> userList;
	private ResultModel model;
	
	public InputAction(JTextField text){
		this.text = text;
	}

	public void caretUpdate(CaretEvent e) {
		if(text.getText()!=null&&text.getText().length()>0){
			userList = getResult(text.getText());
			if(userList!=null&&userList.size()>0){
				if(!window.isShowing()){
					showResult();
				}
				updateResult();
			}else{
				if(window.isShowing()){
					closeResult();
				}
			}
		}
	}

	public void focusGained(FocusEvent e) {
		showResult();
	}

	public void focusLost(FocusEvent e) {
		closeResult();
	}
	
	private void closeResult(){
		window.dispose();
	}
	
	private void showResult(){
		window = new JWindow();
		window.setLayout(new GridLayout(1,1));
		
		table = new JTable();
        table.setShowVerticalLines(false);
		table.setRowHeight(40);
		table.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e){
				text.setText(userList.get(table.getSelectedRow()).getUserName());
				closeResult();
			}
		});
		window.add(table);
		
		window.pack();
		window.setVisible(true);
		window.setLocation(text.getLocationOnScreen().x,text.getLocationOnScreen().y+21);
	}
	
	private void updateResult(){
		model = new ResultModel(getDataSource(userList),userList.size(),2);
		table.setModel(model);
		table.updateUI();
		window.pack();
	}
	
	private ArrayList<UserInfo> getResult(String str){
		ArrayList<UserInfo> list = new ArrayList<UserInfo>();
		for(int i=0;i<FriendList.friendList.size();i++){
			if(FriendList.friendList.get(i).getUserName().contains(str)){
				list.add(FriendList.friendList.get(i));
			}
		}
		return list;
	}
	
	private Object[][] getDataSource(ArrayList<UserInfo> list){
		Object[][] obj = new Object[list.size()][2];
		ImageIcon image = null;
		String path = null;
		for(int i=0;i<list.size();i++){
			if(list.get(i).getUserSex().equals("ÄÐ")){
				path = "image/male/"+list.get(i).getUserImage()+"_mini.jpg";
			}else{
				path = "image/female/"+list.get(i).getUserImage()+"_mini.jpg";
			}
			image = new ImageIcon(path);
			obj[i][0] = image;
			obj[i][1] = list.get(i).getUserName();
		}
		return obj;
	}
	
	private class ResultModel implements TableModel{
		
		private Object[][] obj;
		private int row;
		private int col;
		
		public ResultModel(Object[][] obj,int row,int col){
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
}
