/**
 * 显示列表面板
 */
package music;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.media.*;
import javax.swing.*;

/**
 * @author guanchun
 *
 */
public class ListPanel extends JPanel implements ControllerListener{
	public ListPanel(MainPanel pane)
	{
		panel = pane;
		setLayout(new BorderLayout(2,2));
		northpanel = new JPanel();
		menupanel = new JPanel();
		leftpanel = new JPanel();
		rightpanel = new JPanel();
		companel = new JPanel();
		add(northpanel,BorderLayout.NORTH);
		add(leftpanel,BorderLayout.WEST);
		add(rightpanel,BorderLayout.CENTER);
		northpanel.setPreferredSize(new Dimension(300,50));
		leftpanel.setPreferredSize(new Dimension(100,330));
		
		menubar = new JMenuBar();
		filemenu = new JMenu("文件");
	
		listmenu = new JMenu("列表");
		modemenu = new JMenu("模式");
		menubar.add(filemenu);
		menubar.add(listmenu);
		menubar.add(modemenu);
		
		addfileItem = new JMenuItem("添加文件");
		addFileItem = new JMenuItem("添加文件夹");
		delfileItem = new JMenuItem("删除文件");
		filemenu.add(addfileItem);
		filemenu.add(addFileItem);
		filemenu.add(delfileItem);
		addlistItem = new JMenuItem("新建列表");
		dellistItem = new JMenuItem("删除列表");
		listmenu.add(addlistItem);
		listmenu.add(dellistItem);
		
		oneloopItem = new JMenuItem("单曲循环");
		
		orderlyItem = new JMenuItem("顺序播放");
		loopItem = new JMenuItem("循环播放");
		modemenu.add(oneloopItem);
		modemenu.add(orderlyItem);
		modemenu.add(loopItem);
		
		northpanel.setLayout(new BorderLayout());
		northpanel.add(menupanel,BorderLayout.SOUTH);
		menupanel.setPreferredSize(new Dimension(300,25));
		menupanel.setLayout(new BorderLayout());
		menupanel.add(menubar,BorderLayout.WEST);
		
		modelabel = new JLabel("顺序播放");
		menupanel.add(modelabel,BorderLayout.EAST);
		modelabel.setPreferredSize(new Dimension(60,25));
		modelabel.setFont(new Font("楷体",Font.PLAIN,15));
		
		northpanel.add(companel,BorderLayout.NORTH);
		companel.setPreferredSize(new Dimension(300,25));
		companel.setLayout(new BorderLayout());
		companel.setBackground(Color.lightGray);
		
		playlistdata = new PlayListData();
		leftlist = new JList();
		leftpanel.setLayout(new BorderLayout());
		leftpanel.add(new JScrollPane(leftlist),BorderLayout.CENTER);
		leftlist.setFont(new Font("宋体",Font.PLAIN,12));
		
		rightlist = new JList();
		rightpanel.setLayout(new BorderLayout());
		rightpanel.add(new JScrollPane(rightlist),BorderLayout.CENTER);
        rightlist.setFont(new Font("宋体",Font.PLAIN,12));
		setActionListener ();
		
	}
	
	//获得播放路径
	public String getPlaypath()
	{
		playindex = leftlist.getSelectedIndex();
		if(playindex!=-1)
		{
			if(nextORfront == null)
			{
		    	songindex = rightlist.getSelectedIndex();
		    	if(songindex!=-1)
		    	{
		     		songlistdata = playlistdata.getSongindex(playindex);
			    	playpath = songlistdata.getsongpath(songindex);
			    }
			}
			//下一曲
			else if(nextORfront.equals("next"))
			{
				songindex = rightlist.getSelectedIndex();
				if(songindex != -1)
				{
					songlistdata = playlistdata.getSongindex(playindex);
			      	if(songindex == songlistdata.getlength()-1)//列表中的最后一曲
			    	{
				    	if(playmode == 3) //循环播放
				    	{
					    	songindex = 0;
				    		rightlist.setSelectedIndex(songindex);//跳到第一曲
			    		}
			    		playpath = songlistdata.getsongpath(songindex);
			    	}
		    		else 
			    	{
		    			if(playmode == 1) {}
		    			else
				        	songindex++;
			    		rightlist.setSelectedIndex(songindex);
			    		playpath = songlistdata.getsongpath(songindex);
			    	}
				}
			}
			//上一曲
			else if(nextORfront.equals("front"))
			{
				songindex = rightlist.getSelectedIndex();
				if(songindex != -1)
				{
					songlistdata = playlistdata.getSongindex(playindex);
			      	if(songindex == 0)//列表中的第一曲
			    	{
				    	if(playmode == 3) //循环播放
				    	{
					    	songindex = songlistdata.getlength()-1;
				    		rightlist.setSelectedIndex(songindex);//跳到最后一曲
			    		}
			    		playpath = songlistdata.getsongpath(songindex);
			    	}
		    		else 
			    	{
		    			if(playmode == 1) {}
		    			else
				        	songindex--;
			    		rightlist.setSelectedIndex(songindex);
			    		playpath = songlistdata.getsongpath(songindex);
			    	}
				}
			}
		}
		else playpath = null;
		return playpath;
	}
	
	//播放
	public void startplay()
	{
		if(aplayer != null)
		{
			aplayer.start();
		}
		else 
		{
			if(getPlaypath() != null)
			{
				nextORfront = null;
	    		createplayer(getPlaypath());
	        }
			else { }
		}
	}
	
	//暂停
	public void puaseplay()
	{
		if(aplayer != null)
		{
			//state = 0;
			aplayer.stop();
		}
		else {}
	}
	
	//停止播放
	public void stopplay()
	{
		if(aplayer != null)
		{
			aplayer.close();
			aplayer = null;
		}
	}
	
	//下一曲
	public void nextplay()
	{
		if(aplayer != null)
		{
			nextORfront = "next";
			aplayer.close();
			aplayer = null;
			startplay();
		}
	}
	
	//上一曲
	public void frontplay()
	{
		if(aplayer != null)
		{
			nextORfront = "front";
			aplayer.close();
			aplayer = null;
			startplay();
		}
	}
	
	//保存列表数据
	public void saveList()
	{
		File file = new File("save/");
		File[] files= file.listFiles();
		for(int   i   =   0;   i   <   files.length;   i++) 
	    { 
	        files[i].delete();
        } 
		
		if(playlistdata.getLength() == 0) return;
        //创建save文件夹
		try
		{
			new File("save/").mkdir();
		}
		catch(SecurityException e)
		{
			JOptionPane.showMessageDialog(ListPanel.this, "创建文件夹失败！", "错误", JOptionPane.ERROR_MESSAGE);
			return;
		}
        
		Properties playlist = new Properties();
		if( playlistdata.getLength() == 0) return;
		//保存列表数目
		playlist.put("playlistnum",String.valueOf(playlistdata.getLength()));
		//保存上次退出时播放列表计数器
		playlist.put("playindex",String.valueOf(playindex)); 
		//保存上次退出时歌曲列表计数器
		playlist.put("songindex",String.valueOf(songindex));
		int i = 0;
		//保存列表名
		while( i < playlistdata.getLength())
		{
			playlist.put("listname"+i,playlistdata.getListname(i));
			i++;
		}
		
		try{
			FileOutputStream out = new FileOutputStream("save/playlist.properties");
			playlist.store(out, "save.properties");
			out.close();
		}catch(IOException ex){
			JOptionPane.showMessageDialog(ListPanel.this, "文件输出错误！", "错误", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		//保存歌曲列表
		for(i = 0; i < playlistdata.getLength(); i++)
		{
			Properties songlist = new Properties();
			//保存第i个播放列表的歌曲数
			int songnum = playlistdata.getSongindex(i).getlength();
			songlist.put("songnum", String.valueOf(songnum));
			for(int j = 0; j < songnum; j++)
			{
				songlist.put("songname"+j,playlistdata.getSongindex(i).getsongname(j));
				songlist.put("songpath"+j,playlistdata.getSongindex(i).getsongpath(j));
			}
			try{
				FileOutputStream out = new FileOutputStream("save/songlist"+i+".properties");
				songlist.store(out, "save/songlist"+i+".properties");
				out.close();
				}catch(IOException ex){
					JOptionPane.showMessageDialog(ListPanel.this, "文件输出错误！", "错误", JOptionPane.ERROR_MESSAGE);
					return;
				}
		}
    }
	
	//程序开始运行时获得以前保存的列表数据
	public void getData()
	{
		//读入播放列表数据
		Properties setplaylist = new Properties();
		try
		{
			FileInputStream in = new FileInputStream("save/playlist.properties");
			setplaylist.load(in);
			in.close();
		}catch(IOException ex){
			//JOptionPane.showMessageDialog(ListPanel.this, "文件读入错误！", "错误", JOptionPane.ERROR_MESSAGE);
			return;
		}
		int lists = Integer.parseInt(setplaylist.getProperty("playlistnum"));
		for(int i = 0; i<lists; i++)
		{
			playlistdata.setListname(setplaylist.getProperty("listname"+i));
		}
		//读入歌曲列表数据
		for(int i = 0; i < lists; i++)
		{
			Properties setsonglist = new Properties();
			try
			{
				FileInputStream in = new FileInputStream("save/songlist"+i+".properties");
				setsonglist.load(in);
				in.close();
			}catch(IOException ex){
				JOptionPane.showMessageDialog(ListPanel.this, "文件读入错误！", "错误", JOptionPane.ERROR_MESSAGE);
				return;
			}
			int songnum = Integer.parseInt(setsonglist.getProperty("songnum"));
			songfiles = songnum;
			for(int j = 0; j < songnum; j++)
			{
				songlistdata = playlistdata.getSongindex(i);
    			songlistdata.setsongname(setsonglist.getProperty("songname"+j), 
    					setsonglist.getProperty("songpath"+j));
			}
		}
		playindex = Integer.parseInt(setplaylist.getProperty("playindex"));
		songindex = Integer.parseInt(setplaylist.getProperty("songindex"));
		//初始化列表
		if(playindex > Integer.parseInt(setplaylist.getProperty("playlistnum")))
			playindex = Integer.parseInt(setplaylist.getProperty("playlistnum"));
		listnamevt = playlistdata.getListnamevt();
		leftlist.setListData(listnamevt);
		listnamevt = playlistdata.getSongindex(playindex).getsongnamevt();
		rightlist.setListData(listnamevt);
		leftlist.setSelectedIndex(playindex);
		rightlist.setSelectedIndex(songindex);
		if(aplayer != null)
		{
			aplayer.close();
			aplayer = null;
			
		}
		panel.setplaybt();
		startplay();
	}
	
    //	获得播放曲目名
	public String getPlayingSongname()
	{
		playindex = leftlist.getSelectedIndex();
		if(playindex != -1)
		{
			songindex = rightlist.getSelectedIndex();
			if(songindex!=-1)
	    	{
	     		songlistdata = playlistdata.getSongindex(playindex);
		    	playname = songlistdata.getsongname(songindex);
		    }
		}
		else playname = null;
		return playname;
	}
	
    //	创建一个播放器aplayer
	public void createplayer(String path)
	{
		try
		{
			playFile = new File(path);
			if(aplayer != null)
			{
				aplayer.stop();
				aplayer.close();
			}
			aplayer =  Manager.createPlayer(playFile.toURL());
			aplayer.addControllerListener(this);
			aplayer.start();
			panel.setsongname(getPlayingSongname());
			
         }catch(NoPlayerException e){
        	 e.printStackTrace();
         }
         catch(Exception e){
        	 e.printStackTrace();
         }
	}
	
    //	 player 事件监听器实现方法
	public synchronized void controllerUpdate(ControllerEvent e) {

    	if (e instanceof RealizeCompleteEvent) 
		{
    		if ((comp1 = aplayer.getVisualComponent()) != null)
                companel.add (comp1,BorderLayout.CENTER);
            if ((comp2 = aplayer.getControlPanelComponent()) != null)
                companel.add(comp2,BorderLayout.SOUTH);
            validate();
        }
    	if (e instanceof ControllerClosedEvent) 
    	{ 
    		if(comp1 !=null)
			{
				companel.remove(comp1);
			    comp1 = null;
			}
			if(comp2 != null)
			{
				companel.remove(comp2);
				comp2 = null;
			}
    	}
    	if (e instanceof EndOfMediaEvent)
    	{
    		nextplay();
    	}
    }
	//设置监听器
	private void setActionListener ()
	{
		ActionListener listener = new ItemAction();
		
	    addlistItem.addActionListener(listener);
	    dellistItem.addActionListener(listener);
	    addfileItem.addActionListener(listener);
	    addFileItem.addActionListener(listener);
	    delfileItem.addActionListener(listener);
	    oneloopItem.addActionListener(listener);
	    orderlyItem.addActionListener(listener);
	    loopItem.addActionListener(listener);
	    
	    leftlist.addMouseListener(new leftMouseHandler());
	    rightlist.addMouseListener(new rightMouseHandler());
	}
	
	//播放列表鼠标单击监听器
	private class leftMouseHandler extends MouseAdapter
	{
		public void mouseClicked(MouseEvent event)
		{
			if(event.getClickCount() == 1)
			{
				//显示相关的歌曲列表
				playindex = leftlist.getSelectedIndex();
	    		if(playindex!=-1)
	    		{
	    			songlistdata = playlistdata.getSongindex(playindex);
	    			listnamevt = songlistdata.getsongnamevt();
	    			rightlist.setListData(listnamevt);
	    		}
			}
		}
	}
	
	//歌曲列表鼠标双击监听器
	private class rightMouseHandler extends MouseAdapter
	{
		public void mouseClicked(MouseEvent event)
		{
			if(event.getClickCount() == 2)
			{
				//双击播放歌曲
				if(aplayer != null)
				{
					aplayer.close();
					aplayer = null;
					
				}
				panel.setplaybt();
				startplay();
			}
		}
	}
	//菜单按钮监听器
	private class ItemAction implements ActionListener{
		public void actionPerformed(ActionEvent e)
		{
			//新建列表
			if(e.getActionCommand().equals("新建列表"))
			{
				listna = JOptionPane.showInputDialog
				            (ListPanel.this,"请输入列表名","新建列表");
				playlistdata.setListname(listna);
				listnamevt = playlistdata.getListnamevt();
				leftlist.setListData(listnamevt);
				leftlist.setSelectedIndex(playlistdata.getLength()-1);
				listnamevt = playlistdata.getSongindex(playlistdata.getLength()-1).getsongnamevt();
				rightlist.setListData(listnamevt);
			}
			//删除列表
			else if(e.getActionCommand().equals("删除列表"))
			{
				playindex = leftlist.getSelectedIndex();
				if(playindex!=-1)
				{
					if(playindex!=0)
					{
						playlistdata.getSongindex(playindex).getsongnamevt().clear();
						playlistdata.delListname(playindex);
						playindex--;
				    	songlistdata = playlistdata.getSongindex(playindex);
				    	listnamevt = songlistdata.getsongnamevt();
					}
					else 
					{
						if(playlistdata.getLength() == 1)
					    {
							playlistdata.getSongindex(playindex).getsongnamevt().clear();
							playlistdata.delListname(playindex);
							listnamevt.clear();
					    }
						else
						{
							playlistdata.getSongindex(playindex).getsongnamevt().clear();
							playlistdata.delListname(playindex);
							songlistdata = playlistdata.getSongindex(playindex);
							listnamevt = songlistdata.getsongnamevt();
						}
					}
					rightlist.setListData(listnamevt);
					listnamevt = playlistdata.getListnamevt();
					leftlist.setListData(listnamevt);
					leftlist.setSelectedIndex(playindex);
				}
			}
			//添加单个文件
			else if(e.getActionCommand().equals("添加文件"))
			{
				chooser = new JFileChooser();
				chooser.setFileFilter(new AudioFilter());
				int flag = chooser.showOpenDialog(ListPanel.this);
				if(flag == JFileChooser.APPROVE_OPTION)
				{
					playindex = leftlist.getSelectedIndex();
		    		if(playindex!=-1)
		    		{
		    			filename = chooser.getSelectedFile().getName();
			    		filepath = chooser.getSelectedFile().getPath();
		    			songlistdata = playlistdata.getSongindex(playindex);
		    			songlistdata.setsongname(filename, filepath);
		    			listnamevt = songlistdata.getsongnamevt();
		    			rightlist.setListData(listnamevt);
		    		}
				}
				else if(flag == JFileChooser.CANCEL_OPTION)
				{}
			}
			//添加文件夹中的文件
			else if(e.getActionCommand().equals("添加文件夹"))
			{
				chooser = new JFileChooser();
				chooser.setMultiSelectionEnabled(true);
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setFileFilter(new AudioFilter());
				int flag = chooser.showOpenDialog(ListPanel.this);
				if(flag == JFileChooser.APPROVE_OPTION)
				{
					playindex = leftlist.getSelectedIndex();
	    	    	if(playindex!=-1)
	    	    	{
				    	file = chooser.getSelectedFile().listFiles();
					    int n = 0;
				       	while(n<file.length)
				     	{
					    	filename = file[n].getName();
		    	            filepath = file[n].getPath();
					    	if(file[n].getName().toLowerCase().endsWith(".mp3"))
					    	{
			    	            songlistdata = playlistdata.getSongindex(playindex);
			    		        songlistdata.setsongname(filename, filepath);
			    		        listnamevt = songlistdata.getsongnamevt();
			    		        rightlist.setListData(listnamevt);
					    	}
		    		        n++;
				    	}
	    	    	}
				}
				else if(flag == JFileChooser.CANCEL_OPTION)
				{}
			}
			//删除文件
			else if(e.getActionCommand().equals("删除文件"))
			{
				playindex = leftlist.getSelectedIndex();
				if(playindex!=-1)
				{
					songindex = rightlist.getSelectedIndex();
					if(songindex!=-1)
					{
						songlistdata = playlistdata.getSongindex(playindex);
						songlistdata.delsongname(songindex);
					    listnamevt = songlistdata.getsongnamevt();
			    		rightlist.setListData(listnamevt);
					}
				}
			}
			//单曲循环
			else if(e.getActionCommand().equals("单曲循环"))
			{
				playmode = 1;
				modelabel.setText("单曲循环");
			}
			//顺序播放
			else if(e.getActionCommand().equals("顺序播放"))
			{
				playmode = 2;
				modelabel.setText("顺序播放");
			}
			//循环播放
			else if(e.getActionCommand().equals("循环播放"))
			{
				playmode = 3;
				modelabel.setText("循环播放");
			}
		}
	}
	private JPanel northpanel;
	private JPanel menupanel;
	private JPanel leftpanel;
	private JPanel rightpanel;
	private JPanel companel;
	private JMenuBar menubar;
	private JMenu filemenu;
	private JMenu listmenu;
	private JMenu modemenu;
	private JMenuItem addlistItem;//添加列表
	private JMenuItem addfileItem;//添加文件到列表
	private JMenuItem addFileItem;//从文件夹添加文件到列表
	private JMenuItem delfileItem;//删除文件
	private JMenuItem dellistItem;//删除列表
	private JLabel modelabel;
	private JMenuItem oneloopItem; //单曲循环播放
	private JMenuItem orderlyItem; //顺序播放
	private JMenuItem loopItem; //循环播放
	
	private JList leftlist; //左边的列表
	private JList rightlist; //右边的列表
	private Vector listnamevt; //列表数据向量
	private String listna; 
	private String playpath; //播放路径
	private String playname; //播放歌曲名
	
	private PlayListData playlistdata;
	private SongListData songlistdata;
	private int playindex; //播放列表计数
	private int songindex; //歌曲列表计数
	
	private JFileChooser chooser;
	private String filename;
	private String filepath;
	private File [] file;
	
	private Player aplayer; //Player播放器
    private Component comp1;//Player 可视组件
    private Component comp2;//Player控制组件
    private File playFile;
    
    private int playmode = 2; //标记播放模式 1. 单曲播放 2.顺序播放 3.循环播放
    private String nextORfront = null; //
    private MainPanel panel;
    private int songfiles;
}
