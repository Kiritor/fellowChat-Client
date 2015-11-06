package action.chatUIAction;

import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.JWindow;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import allUI.ChatUI;

public class IconAction implements FocusListener{
	
	private JWindow window;
	private JTable table;
	private ChatUI chatUI;
	
	public IconAction(ChatUI chatUI){
		this.chatUI = chatUI;
	}

	public void focusGained(FocusEvent e) {
		showIconUI();
	}

	public void focusLost(FocusEvent e) {
		closeUI();
	}
	
	private void closeUI(){
		window.dispose();
	}
	
	private void showIconUI(){
		window = new JWindow();
		window.setSize(320, 230);
		window.setLayout(new GridLayout(1,1));
		
		IconModel model = new IconModel();
		table = new JTable(model);
		table.setRowHeight(46);
		table.setColumnSelectionAllowed(true);
		table.setShowGrid(false);
		table.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e){
				int index = 5*table.getSelectedRow()+table.getSelectedColumn()+1;
				chatUI.area2InsertIcon(index);
				chatUI.area2RequestFocus();
			}
		});
		window.add(table);
		
		window.setVisible(true);
		window.setLocationRelativeTo(chatUI.getImageIconButton());
	}
	
	private class IconModel implements TableModel{
		private ImageIcon[][] icon = new ImageIcon[5][5];
		private int row = 5;
		private int col = 5;
		
		public IconModel(){
			String index = null;
			for(int i=0;i<5;i++){
				for(int j=0;j<5;j++){
					ImageIcon image = new ImageIcon("image/chatIcon/"+(i*5+j+1)+".png");
					if(image!=null){
						icon[i][j] = image;
					}
				}
			}
		}

		public void addTableModelListener(TableModelListener l) {
			
		}

		public Class<?> getColumnClass(int columnIndex) {
			
			return ImageIcon.class;
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
			
			return icon[rowIndex][columnIndex];
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
