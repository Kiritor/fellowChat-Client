package action.loginUIAction;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import util.tools.UIMap;

import allUI.MainUI;
import allUI.RegUI;

public class RegAction extends MouseAdapter implements ActionListener {

	public void mouseReleased(MouseEvent e) {
		showRegUI();
	}

	public void actionPerformed(ActionEvent e) {
		showRegUI();
	}

	private void showRegUI() {
		if (!UIMap.isConnected) {
			JOptionPane.showMessageDialog(null, "�����ڴ�������״̬�����ڳ����Զ�����.....");
			return;
		}
		RegUI reg = (RegUI) UIMap.temporaryStorage.get("reg");
		if (reg == null) {
			reg = new RegUI();
			reg.showUI();
			UIMap.storeObj("reg", reg);
		} else {
			JOptionPane.showMessageDialog(null, "ע��ҳ���Ѵ򿪣�����");
		}

	}
}
