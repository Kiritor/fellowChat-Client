package action.searchUIAction;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import allUI.SearchUI;


public class CloseSearchUIAction extends MouseAdapter{
	
	private SearchUI searchUI;
	
	public CloseSearchUIAction(SearchUI searchUI){
		this.searchUI = searchUI;
	}

	public void mouseReleased(MouseEvent e){
		searchUI.closeUI();
	}
}
