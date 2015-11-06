/**
 * 歌曲列表数据
 */
package music;

import java.util.*;

/**
 * @author guanchun
 *
 */
public class SongListData {
    public SongListData()
    {
    	filenamevt = new Vector <String>(0,1);
    	filepathvt = new Vector <String>(0,1);
    }
    //歌名向量和歌曲路径向量添加元素
    public void setsongname(String name,String path)
    {
    	songname = name;
    	songpath = path;
    	filenamevt.addElement(songname);
    	filepathvt.addElement(songpath);
    }
    //删除元素
    public void delsongname(int sindex)
    {
    	songindex = sindex;
    	filenamevt.remove(songindex);
    	filepathvt.remove(songindex);
    }
    //获得指定的歌曲播放路径
    public String getsongpath(int index)
    {
    	return filepathvt.get(index);
    }
    //
    public Vector getsongnamevt()
    {
    	return filenamevt;
    }
    //获得元素个数
    public int getlength()
    {
    	return filepathvt.size();
    }
    //获得指定的歌曲名
    public String getsongname(int index)
    {
    	return filenamevt.get(index);
    }
    private Vector <String> filenamevt;
    private Vector <String> filepathvt;
    private String songname;
    private String songpath;
    private int songindex;
}
