package util.hositoryMsg;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HositoryMsg {
	public static boolean flag = false;
    public static void record(String msg){
    	try{
    		FileOutputStream out = new FileOutputStream("hositoryMsg/hository.txt",true);
    	
    		String runningMsg = msg+"\r\n";
    		out.write(runningMsg.getBytes());
    		flag = true;
    		out.close();
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
}
