/**
 * ��ʾ�б����
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
		filemenu = new JMenu("�ļ�");
	
		listmenu = new JMenu("�б�");
		modemenu = new JMenu("ģʽ");
		menubar.add(filemenu);
		menubar.add(listmenu);
		menubar.add(modemenu);
		
		addfileItem = new JMenuItem("����ļ�");
		addFileItem = new JMenuItem("����ļ���");
		delfileItem = new JMenuItem("ɾ���ļ�");
		filemenu.add(addfileItem);
		filemenu.add(addFileItem);
		filemenu.add(delfileItem);
		addlistItem = new JMenuItem("�½��б�");
		dellistItem = new JMenuItem("ɾ���б�");
		listmenu.add(addlistItem);
		listmenu.add(dellistItem);
		
		oneloopItem = new JMenuItem("����ѭ��");
		
		orderlyItem = new JMenuItem("˳�򲥷�");
		loopItem = new JMenuItem("ѭ������");
		modemenu.add(oneloopItem);
		modemenu.add(orderlyItem);
		modemenu.add(loopItem);
		
		northpanel.setLayout(new BorderLayout());
		northpanel.add(menupanel,BorderLayout.SOUTH);
		menupanel.setPreferredSize(new Dimension(300,25));
		menupanel.setLayout(new BorderLayout());
		menupanel.add(menubar,BorderLayout.WEST);
		
		modelabel = new JLabel("˳�򲥷�");
		menupanel.add(modelabel,BorderLayout.EAST);
		modelabel.setPreferredSize(new Dimension(60,25));
		modelabel.setFont(new Font("����",Font.PLAIN,15));
		
		northpanel.add(companel,BorderLayout.NORTH);
		companel.setPreferredSize(new Dimension(300,25));
		companel.setLayout(new BorderLayout());
		companel.setBackground(Color.lightGray);
		
		playlistdata = new PlayListData();
		leftlist = new JList();
		leftpanel.setLayout(new BorderLayout());
		leftpanel.add(new JScrollPane(leftlist),BorderLayout.CENTER);
		leftlist.setFont(new Font("����",Font.PLAIN,12));
		
		rightlist = new JList();
		rightpanel.setLayout(new BorderLayout());
		rightpanel.add(new JScrollPane(rightlist),BorderLayout.CENTER);
        rightlist.setFont(new Font("����",Font.PLAIN,12));
		setActionListener ();
		
	}
	
	//��ò���·��
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
			//��һ��
			else if(nextORfront.equals("next"))
			{
				songindex = rightlist.getSelectedIndex();
				if(songindex != -1)
				{
					songlistdata = playlistdata.getSongindex(playindex);
			      	if(songindex == songlistdata.getlength()-1)//�б��е����һ��
			    	{
				    	if(playmode == 3) //ѭ������
				    	{
					    	songindex = 0;
				    		rightlist.setSelectedIndex(songindex);//������һ��
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
			//��һ��
			else if(nextORfront.equals("front"))
			{
				songindex = rightlist.getSelectedIndex();
				if(songindex != -1)
				{
					songlistdata = playlistdata.getSongindex(playindex);
			      	if(songindex == 0)//�б��еĵ�һ��
			    	{
				    	if(playmode == 3) //ѭ������
				    	{
					    	songindex = songlistdata.getlength()-1;
				    		rightlist.setSelectedIndex(songindex);//�������һ��
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
	
	//����
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
	
	//��ͣ
	public void puaseplay()
	{
		if(aplayer != null)
		{
			//state = 0;
			aplayer.stop();
		}
		else {}
	}
	
	//ֹͣ����
	public void stopplay()
	{
		if(aplayer != null)
		{
			aplayer.close();
			aplayer = null;
		}
	}
	
	//��һ��
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
	
	//��һ��
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
	
	//�����б�����
	public void saveList()
	{
		File file = new File("save/");
		File[] files= file.listFiles();
		for(int   i   =   0;   i   <   files.length;   i++) 
	    { 
	        files[i].delete();
        } 
		
		if(playlistdata.getLength() == 0) return;
        //����save�ļ���
		try
		{
			new File("save/").mkdir();
		}
		catch(SecurityException e)
		{
			JOptionPane.showMessageDialog(ListPanel.this, "�����ļ���ʧ�ܣ�", "����", JOptionPane.ERROR_MESSAGE);
			return;
		}
        
		Properties playlist = new Properties();
		if( playlistdata.getLength() == 0) return;
		//�����б���Ŀ
		playlist.put("playlistnum",String.valueOf(playlistdata.getLength()));
		//�����ϴ��˳�ʱ�����б������
		playlist.put("playindex",String.valueOf(playindex)); 
		//�����ϴ��˳�ʱ�����б������
		playlist.put("songindex",String.valueOf(songindex));
		int i = 0;
		//�����б���
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
			JOptionPane.showMessageDialog(ListPanel.this, "�ļ��������", "����", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		//��������б�
		for(i = 0; i < playlistdata.getLength(); i++)
		{
			Properties songlist = new Properties();
			//�����i�������б�ĸ�����
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
					JOptionPane.showMessageDialog(ListPanel.this, "�ļ��������", "����", JOptionPane.ERROR_MESSAGE);
					return;
				}
		}
    }
	
	//����ʼ����ʱ�����ǰ������б�����
	public void getData()
	{
		//���벥���б�����
		Properties setplaylist = new Properties();
		try
		{
			FileInputStream in = new FileInputStream("save/playlist.properties");
			setplaylist.load(in);
			in.close();
		}catch(IOException ex){
			//JOptionPane.showMessageDialog(ListPanel.this, "�ļ��������", "����", JOptionPane.ERROR_MESSAGE);
			return;
		}
		int lists = Integer.parseInt(setplaylist.getProperty("playlistnum"));
		for(int i = 0; i<lists; i++)
		{
			playlistdata.setListname(setplaylist.getProperty("listname"+i));
		}
		//��������б�����
		for(int i = 0; i < lists; i++)
		{
			Properties setsonglist = new Properties();
			try
			{
				FileInputStream in = new FileInputStream("save/songlist"+i+".properties");
				setsonglist.load(in);
				in.close();
			}catch(IOException ex){
				JOptionPane.showMessageDialog(ListPanel.this, "�ļ��������", "����", JOptionPane.ERROR_MESSAGE);
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
		//��ʼ���б�
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
	
    //	��ò�����Ŀ��
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
	
    //	����һ��������aplayer
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
	
    //	 player �¼�������ʵ�ַ���
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
	//���ü�����
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
	
	//�����б���굥��������
	private class leftMouseHandler extends MouseAdapter
	{
		public void mouseClicked(MouseEvent event)
		{
			if(event.getClickCount() == 1)
			{
				//��ʾ��صĸ����б�
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
	
	//�����б����˫��������
	private class rightMouseHandler extends MouseAdapter
	{
		public void mouseClicked(MouseEvent event)
		{
			if(event.getClickCount() == 2)
			{
				//˫�����Ÿ���
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
	//�˵���ť������
	private class ItemAction implements ActionListener{
		public void actionPerformed(ActionEvent e)
		{
			//�½��б�
			if(e.getActionCommand().equals("�½��б�"))
			{
				listna = JOptionPane.showInputDialog
				            (ListPanel.this,"�������б���","�½��б�");
				playlistdata.setListname(listna);
				listnamevt = playlistdata.getListnamevt();
				leftlist.setListData(listnamevt);
				leftlist.setSelectedIndex(playlistdata.getLength()-1);
				listnamevt = playlistdata.getSongindex(playlistdata.getLength()-1).getsongnamevt();
				rightlist.setListData(listnamevt);
			}
			//ɾ���б�
			else if(e.getActionCommand().equals("ɾ���б�"))
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
			//��ӵ����ļ�
			else if(e.getActionCommand().equals("����ļ�"))
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
			//����ļ����е��ļ�
			else if(e.getActionCommand().equals("����ļ���"))
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
			//ɾ���ļ�
			else if(e.getActionCommand().equals("ɾ���ļ�"))
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
			//����ѭ��
			else if(e.getActionCommand().equals("����ѭ��"))
			{
				playmode = 1;
				modelabel.setText("����ѭ��");
			}
			//˳�򲥷�
			else if(e.getActionCommand().equals("˳�򲥷�"))
			{
				playmode = 2;
				modelabel.setText("˳�򲥷�");
			}
			//ѭ������
			else if(e.getActionCommand().equals("ѭ������"))
			{
				playmode = 3;
				modelabel.setText("ѭ������");
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
	private JMenuItem addlistItem;//����б�
	private JMenuItem addfileItem;//����ļ����б�
	private JMenuItem addFileItem;//���ļ�������ļ����б�
	private JMenuItem delfileItem;//ɾ���ļ�
	private JMenuItem dellistItem;//ɾ���б�
	private JLabel modelabel;
	private JMenuItem oneloopItem; //����ѭ������
	private JMenuItem orderlyItem; //˳�򲥷�
	private JMenuItem loopItem; //ѭ������
	
	private JList leftlist; //��ߵ��б�
	private JList rightlist; //�ұߵ��б�
	private Vector listnamevt; //�б���������
	private String listna; 
	private String playpath; //����·��
	private String playname; //���Ÿ�����
	
	private PlayListData playlistdata;
	private SongListData songlistdata;
	private int playindex; //�����б����
	private int songindex; //�����б����
	
	private JFileChooser chooser;
	private String filename;
	private String filepath;
	private File [] file;
	
	private Player aplayer; //Player������
    private Component comp1;//Player �������
    private Component comp2;//Player�������
    private File playFile;
    
    private int playmode = 2; //��ǲ���ģʽ 1. �������� 2.˳�򲥷� 3.ѭ������
    private String nextORfront = null; //
    private MainPanel panel;
    private int songfiles;
}
