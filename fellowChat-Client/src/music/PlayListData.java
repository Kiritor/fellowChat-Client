/**
 * 播放列表数据
 */
package music;

import java.util.*;

/**
 * @author guanchun
 *
 */
public class PlayListData {
	public PlayListData()
	{   
		listnamevt = new Vector <String> (0,1);
		listpathvt = new Vector <SongListData> (0,1);
	}
	
	//删除列表中指定的元素
	public void delListname(int index)
	{
		playindex = index; 
		listnamevt.remove(playindex);
		listpathvt.remove(playindex);
	}
	//添加元素
	public void setListname(String Listna)
	{
		listna = Listna;
		songlist = new SongListData();
		listnamevt.addElement(listna);
		listpathvt.addElement(songlist);
	}
	//获得列表名向量
	public Vector getListnamevt()
	{
		return listnamevt;
	}
	//获得指定的SongListData对象
	public SongListData getSongindex(int index)
	{
		return listpathvt.get(index);
	}
	//获得元素个数
	public int getLength()
	{
		return listnamevt.size();
	}
	//获得指定的元素
	public String getListname(int index)
	{
		return listnamevt.get(index);
	}
	
	private Vector <String> listnamevt; //列表名向量
	private Vector <SongListData> listpathvt; //SongList对象向量
	private SongListData songlist;
	private String listna;
    private int playindex;
}
