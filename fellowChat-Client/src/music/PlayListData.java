/**
 * �����б�����
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
	
	//ɾ���б���ָ����Ԫ��
	public void delListname(int index)
	{
		playindex = index; 
		listnamevt.remove(playindex);
		listpathvt.remove(playindex);
	}
	//���Ԫ��
	public void setListname(String Listna)
	{
		listna = Listna;
		songlist = new SongListData();
		listnamevt.addElement(listna);
		listpathvt.addElement(songlist);
	}
	//����б�������
	public Vector getListnamevt()
	{
		return listnamevt;
	}
	//���ָ����SongListData����
	public SongListData getSongindex(int index)
	{
		return listpathvt.get(index);
	}
	//���Ԫ�ظ���
	public int getLength()
	{
		return listnamevt.size();
	}
	//���ָ����Ԫ��
	public String getListname(int index)
	{
		return listnamevt.get(index);
	}
	
	private Vector <String> listnamevt; //�б�������
	private Vector <SongListData> listpathvt; //SongList��������
	private SongListData songlist;
	private String listna;
    private int playindex;
}
