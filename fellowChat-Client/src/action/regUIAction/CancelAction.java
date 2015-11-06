package action.regUIAction;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JWindow;

import util.tools.UIMap;

import allUI.RegUI;


public class CancelAction extends MouseAdapter{

	private RegUI reg;
	
	public CancelAction(RegUI reg){
		this.reg = reg;
	}
	
	public void mouseReleased(MouseEvent e){
		reg.closeUI();
	}
}
