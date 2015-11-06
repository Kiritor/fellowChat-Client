package action.searchUIAction;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import util.tools.Loginner;
import util.tools.Message;

import allUI.SearchUI;

import clientBase.ManClient;

public class PagedownAction extends MouseAdapter{

	private SearchUI searchUI;
	private int index = 0;
	
	public PagedownAction(SearchUI searchUI){
		this.searchUI = searchUI;
	}
	
	public void mouseReleased(MouseEvent e){
		index = searchUI.getIndex();
		index += 5;
		String msg = "<type>randomSearch</type><sender>"+Loginner.loginner+"</sender><index>"+index+"</index>";
		try{
			Message.sendMsg(msg, ManClient.client.getOutputStream());
		}catch(Exception ef){
			ef.printStackTrace();
		}
		searchUI.setIndex(index);
	}
}
