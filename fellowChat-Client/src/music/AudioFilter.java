/**
 * 文件过滤器,用于过滤掉非.MP3的文件
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
