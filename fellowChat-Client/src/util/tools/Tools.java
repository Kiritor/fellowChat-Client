package util.tools;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

public class Tools {
    
	public static String getDate(){
    	Date date = new Date();
    	SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	return f.format(date);
    }
    
	public static String[] getConfig(){
		String[] str = new String[2];
		File file = new File("manConfigure.cfg");
		if(file.exists()){
			try{
				FileInputStream fin = new FileInputStream(file);
				BufferedInputStream bin = new BufferedInputStream(fin);
				int length = bin.available();
				if(length>0){
					byte[] data = new byte[length];
					bin.read(data);
					
					String s = new String(data);
					StringTokenizer token = new StringTokenizer(s," ");
					str[0] = token.nextToken();
					str[1] = token.nextToken();
					
					return str;
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return null;
	}
}
