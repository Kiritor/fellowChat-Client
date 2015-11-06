package action.chatUIAction;

import java.awt.Robot;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.Style;
import javax.swing.text.StyledDocument;

public class TextListener extends KeyAdapter implements CaretListener{

	private JTextPane text;
	private boolean isTraggered = false;
	private int length = 0;
	private int location = 0;
	private StyledDocument doc;
	private Style style;
	
	public TextListener(JTextPane text,StyledDocument doc,Style style){
		this.text = text;
		this.doc = doc;
		this.style = style;
	}

	public void keyPressed(KeyEvent e){
		if(e.getKeyCode()==8&&isTraggered){
			try{
				Robot r = new Robot();
				for(int i=0;i<length-1;i++){
					r.keyPress(8);
					r.keyRelease(8);
					doc.insertString(location, ""+e.getKeyChar(), style);
				}
				
			}catch(Exception ef){
				ef.printStackTrace();
			}
			
		}
	}
	
	public void caretUpdate(CaretEvent e) {
		String str = text.getText();
		if(str.length()>4&&str.length()<6){
			if(str.contains("<-")&&str.contains("->")&&str.charAt(e.getDot()-1)=='>'){
				isTraggered = true;
				length = 5;
				location = e.getDot();
			}
		}
		if(str.length()>=6){
			String s = str.substring(e.getDot()-7,e.getDot()-1);
			if(s.contains("<-")&&s.contains("->")&&str.charAt(e.getDot()-1)=='>'){
				isTraggered = true;
				length = s.indexOf("->")+2-s.indexOf("<-");
				location = e.getDot();
			}
		}
	}
}
