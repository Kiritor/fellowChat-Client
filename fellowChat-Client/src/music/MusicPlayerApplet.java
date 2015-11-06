/**
 * Applet µœ÷
 */
package music;


import javax.swing.*;


/**
 * @author guanchun
 *
 */
public class MusicPlayerApplet extends JApplet{
	public void init()
	{
		mainpanel = new MainPanel();
        add(mainpanel);
	
	}
	public void stop()
	{
		mainpanel.saveplaylist();
	}
	private MainPanel mainpanel;
}
