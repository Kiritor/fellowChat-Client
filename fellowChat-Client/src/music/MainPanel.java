/**
 * 主面板
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
	//设置播放播放 暂停按钮显示为暂停图标
	public void setplaybt()
	{
		getUpanel().setplaybt();
	}
	//设置歌曲名显示
	public void setsongname(String songname)
	{
		getUpanel().setsongname(songname);
	}
	//保存列表数据
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
