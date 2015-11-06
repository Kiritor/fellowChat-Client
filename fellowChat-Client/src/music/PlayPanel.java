/**
 * 播放控制面板
 */
package music;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.sun.org.apache.bcel.internal.generic.NEW;


/**
 * @author guanchun
 *
 */

public class PlayPanel extends JPanel {
	 /*最小化按钮的标签*/
	 private JLabel miniJLabel;
	 public JLabel getMiniJLabel() {
		return miniJLabel;
	}

	public void setMiniJLabel(JLabel miniJLabel) {
		this.miniJLabel = miniJLabel;
	}
	private ImageIcon miniIcon=new ImageIcon("images/mini.jpg");
	 private ImageIcon nimifakiIcon = new ImageIcon("images/minifak.jpg");
	 
	 /*关闭按钮的标签*/
	 private JLabel closeJLabel;
	 public JLabel getCloseJLabel() {
		return closeJLabel;
	}

	public void setCloseJLabel(JLabel closeJLabel) {
		this.closeJLabel = closeJLabel;
	}
	private ImageIcon colseIcon= new ImageIcon("images/close.jpg");
	 private ImageIcon closefakiIcon = new ImageIcon("images/closefak.jpg");
	 
	ImageIcon background=new ImageIcon("images/bgbiaozhi.jpg");
	public PlayPanel(ListPanel pan)
	{
		
		
		setLayout(new BorderLayout());
		panel1 = new JPanel();
		panel2  = new JPanel(){
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				g.drawImage(background.getImage(), 0, 0, null);
				this.setOpaque(false);
			}
		};
		miniJLabel = new JLabel(miniIcon);
		closeJLabel = new JLabel(colseIcon);
		closeJLabel.setBounds(290, 8, 19, 10);
		miniJLabel.setBounds(261, 8, 19, 10);
		miniJLabel.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent e){
				miniJLabel.setIcon(nimifakiIcon);
			}
			public void mouseExited(MouseEvent e){
				miniJLabel.setIcon(miniIcon);
			}
			public void mousePressed(MouseEvent e){
				System.out.println("保存列表失败");
				pane.saveList();
			}
			public void mouseReleased(MouseEvent e){
				miniJLabel.setIcon(miniIcon);
			}
		});
		closeJLabel.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent e){
				closeJLabel.setIcon(closefakiIcon);
			}
			public void mouseExited(MouseEvent e){
				closeJLabel.setIcon(colseIcon);
			}
			public void mousePressed(MouseEvent e){
				
			}
			public void mouseReleased(MouseEvent e){
				closeJLabel.setIcon(colseIcon);
			}
		});
		songnamelabel = new JLabel("基友音悦");
		songnamelabel.setBounds(20,5,300,20);
		panel1.setLayout(null);
		panel1.add(songnamelabel,JLabel.CENTER);
		//panel1.add(closeJLabel,JLabel.CENTER);
		//panel1.add(miniJLabel,JLabel.CENTER);
		songnamelabel.setFont(new Font("楷体",Font.PLAIN,16));
		add(panel1,BorderLayout.NORTH);
		Color color = new Color(214,217,233);
		panel1.setBackground(color);
		panel1.setPreferredSize(new Dimension(200,45));
		add(panel2,BorderLayout.SOUTH);
		panel2.setBackground(new Color(212,233,244));
				
		
		Box box = Box.createHorizontalBox();
		playbt = new JButton(new ImageIcon("images/play.png"));
		stopbt = new JButton(new ImageIcon("images/stop.png"));
		frontbt = new JButton(new ImageIcon("images/front.png"));
		nextbt = new JButton(new ImageIcon("images/next.png"));
		biaozhiJLabel=new JLabel(new ImageIcon("images/biaozhi.jpg"));
		biaozhiJLabel.setSize(40, 40);
		box.add(stopbt);
		box.add(playbt);
		box.add(frontbt);
		box.add(nextbt);
		//box.add(biaozhiJLabel);
		playbt.setBorderPainted(false);
		stopbt.setBorderPainted(false);
		frontbt.setBorderPainted(false);
		nextbt.setBorderPainted(false);
		playbt.setBackground(Color.DARK_GRAY);
		stopbt.setBackground(Color.DARK_GRAY);
		frontbt.setBackground(Color.DARK_GRAY);
		nextbt.setBackground(Color.DARK_GRAY);
		panel2.add(box);
        
        pane = pan;
        setBtlistener();
	}
	
	public void setplaybt()
	{
		if(pane.getPlaypath() != null)
		{
			state = 1;
		    playbt.setIcon(new ImageIcon("images/pause.png"));
		}
	}
	
	public void setsongname(String songname)
	{
		songnamelabel.setText(songname);
	}
	
	private void setBtlistener()
	{
		playbt.addActionListener(new Btlistener());
	    stopbt.addActionListener(new Btlistener());
	    frontbt.addActionListener(new Btlistener());
	    nextbt.addActionListener(new Btlistener());
	    
	}
	
	private class Btlistener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			// 播放 与 暂停 歌曲
			if(e.getSource() == playbt)
			{
				if(state == 0)
				{
					if(pane.getPlaypath() != null)
					{
						state = 1;
				    	playbt.setIcon(new ImageIcon("images/pause.png"));
				    	pane.startplay();
					}
					else {}
				}
				else 
				{
					state = 0;
					playbt.setIcon(new ImageIcon("images/play.png"));
					pane.puaseplay();
				}
			}
			//停止播放歌曲
			else if(e.getSource() == stopbt)
			{
				if(pane.getPlaypath() != null)
				{
		            state = 0;
		            playbt.setIcon(new ImageIcon("images/play.png"));
		            pane.stopplay();
		            songnamelabel.setText("MuPlayer 1.0");
				}
				else {}
			}
			//播放下一曲
			else if(e.getSource() == nextbt)
			{
				if(pane.getPlaypath() != null)
				{
		            pane.nextplay();
				}
				else {}
			}
			//播放下一曲
			else if(e.getSource() == frontbt)
			{
				if(pane.getPlaypath() != null)
				{
		            pane.frontplay();
				}
				else {}
			}
		}
		
	}
	//自己添加的内容
	private ImageIcon image;
	private JPanel panel1;
	private JPanel panel2;
    private JButton playbt;
    private JButton stopbt;
    private JButton frontbt;
    private JButton nextbt;
    private JLabel songnamelabel;
    private JLabel biaozhiJLabel;
    private final ListPanel pane;
    public ListPanel getPane() {
		return pane;
	}
	private int state = 0;
	
	/*添加一些窗口处理的信息*/
    
}
