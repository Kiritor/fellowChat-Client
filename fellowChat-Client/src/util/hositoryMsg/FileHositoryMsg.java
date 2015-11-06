package util.hositoryMsg;

import java.io.FileOutputStream;

public class FileHositoryMsg {
   
    public static void record(String msg,String filenameString){
    	try{
    		FileOutputStream out = new FileOutputStream("hositoryMsg/"+filenameString+".txt",true);
    	
    		String runningMsg = msg+"\r\n";
    		out.write(runningMsg.getBytes());
    		out.close();
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
}
