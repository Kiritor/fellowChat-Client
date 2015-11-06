package action.chatUIAction;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JWindow;

import util.tools.UIMap;

import allUI.ChatUI;


public class CloseChatUIAction extends MouseAdapter{
    private ChatUI chatUI;
    private String destination;
    
    public CloseChatUIAction(ChatUI chatUI,String destination){
    	this.chatUI = chatUI;
    	this.destination = destination;
    }

	public void mouseReleased(MouseEvent e){
		//´ÓChatUiMapÖÐÒÆ³ý´ËchatUI
		UIMap.removeChatUI(destination);
		chatUI.closeUI();
	}
}
