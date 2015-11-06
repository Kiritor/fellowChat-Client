package util.tools;

import java.util.ArrayList;

import javax.swing.JComboBox;

public class BindCombox {
   public static JComboBox<String> comboxBind(JComboBox<String> comboBox,ArrayList<String> list)
   {
	   for(int i=0;i<list.size();i++)
	   {
		   comboBox.addItem(list.get(i).toString());
	   }
	   
	   return comboBox;
   }
}
