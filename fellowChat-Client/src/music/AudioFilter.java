/**
 * �ļ�������,���ڹ��˵���.MP3���ļ�
 */
package music;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * @author guanchun
 *
 */
public class AudioFilter extends FileFilter{

	public boolean accept(File f)
	{
		return f.getName().toLowerCase().endsWith(".mp3")||f.isDirectory();
	}
	public String getDescription()
	{
		return "*.mp3";
	}
}
