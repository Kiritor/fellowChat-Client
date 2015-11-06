/**
 * �����
 */
package music;

import java.awt.*;

import javax.swing.*;

/**
 * @author guanchun
 *
 */
public class MainPanel extends JPanel{
	public MainPanel()
	{
		//setMinimumSize(new Dimension(300,600));
        setLayout(new BorderLayout(5,5));
        dpanel = new ListPanel(this);
        setUpanel(new PlayPanel(dpanel));
        add(getUpanel(),BorderLayout.NORTH);
        //upanel.setPreferredSize(new Dimension(200,75));
        add(dpanel,BorderLayout.CENTER);
        dpanel.getData();
        updateUI();
	}
	//���ò��Ų��� ��ͣ��ť��ʾΪ��ͣͼ��
	public void setplaybt()
	{
		getUpanel().setplaybt();
	}
	//���ø�������ʾ
	public void setsongname(String songname)
	{
		getUpanel().setsongname(songname);
	}
	//�����б�����
	public void saveplaylist()
	{
		dpanel.saveList();
	}
	
	public void setUpanel(PlayPanel upanel) {
		this.upanel = upanel;
	}
	public PlayPanel getUpanel() {
		return upanel;
	}

	private PlayPanel upanel;
	private ListPanel dpanel;

}
