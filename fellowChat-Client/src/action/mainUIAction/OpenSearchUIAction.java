package action.mainUIAction;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import util.tools.UIMap;

import allUI.SearchUI;


public class OpenSearchUIAction extends MouseAdapter{

	public void mouseReleased(MouseEvent e){
		SearchUI searchUI = (SearchUI)UIMap.temporaryStorage.get("search");
		if(searchUI!=null){
			JOptionPane.showMessageDialog(null, "窗口已打开！！！");
		}else{
			searchUI = new SearchUI();
			searchUI.showUI();
			UIMap.storeObj("search", searchUI);
		}
	}
}
