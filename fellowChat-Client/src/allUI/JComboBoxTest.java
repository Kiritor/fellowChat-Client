package allUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
public class JComboBoxTest extends JFrame {
    Container con;
    JComboBox jcb;
    JPanel p;
    public JComboBoxTest(){
        con=this.getContentPane();
        p=new JPanel();
        String []stra={"kelsen","kelsen"};
        jcb=new JComboBox();
        jcb.addItem("kelsen1");
        jcb.addItem("kelsen2");
        jcb.addItem("kelsen3");
        jcb.addItem(stra);
        jcb.setEditable(true);
        p.add(jcb);
        con.add(p);
        this.setSize(500,500);
        this.setUndecorated(true);   //去除JFrame的边框就跟JWindow一样
        this.setVisible(true);
        jcb.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                System.out.println(jcb.getSelectedItem());
            }
            
        });
        jcb.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				jcb.addItem(new JComboBox());
			}
		});
    }
    public static void main(String args[]){
        JComboBoxTest jcbt=new JComboBoxTest();
        //jcbt.setUndecorated(false);
    }
}