/**
 * ”¶”√≥Ã–Ú
 */
package music;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JWindow;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.theme.SubstanceTerracottaTheme;

import com.jtattoo.plaf.mcwin.McWinLookAndFeel;

/**
 * @author guanchun
 *
 */
public class MainApplication {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		/*JFrame.setDefaultLookAndFeelDecorated(true);

		 try {

		 UIManager.setLookAndFeel(new McWinLookAndFeel());

		 } catch (UnsupportedLookAndFeelException e1) {

		 // TODO Auto-generated catch block

		 e1.printStackTrace();

		 }*/
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			
		} catch (Exception e) {
		}
       new MainFrame();
       
	}

}
