/**
 * �����б�����
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
    //���������͸���·���������Ԫ��
    public void setsongname(String name,String path)
    {
    	songname = name;
    	songpath = path;
    	filenamevt.addElement(songname);
    	filepathvt.addElement(songpath);
    }
    //ɾ��Ԫ��
    public void delsongname(int sindex)
    {
    	songindex = sindex;
    	filenamevt.remove(songindex);
    	filepathvt.remove(songindex);
    }
    //���ָ���ĸ�������·��
    public String getsongpath(int index)
    {
    	return filepathvt.get(index);
    }
    //
    public Vector getsongnamevt()
    {
    	return filenamevt;
    }
    //���Ԫ�ظ���
    public int getlength()
    {
    	return filepathvt.size();
    }
    //���ָ���ĸ�����
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
