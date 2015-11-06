package util.readtxt;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
public class TextConvertor {
 public static String convertingStr(String lineStr) {
  String retVal = "";
  return retVal;
 }
 
    private static String writeInfo="";
 /**
  * @param args
  * @throws IOException
  */
 
 public static String read(String filePath) throws IOException {
  File file = new File(filePath);
  if (!file.exists()) {
   System.out.println("File not exist!");
  return "";
  }
  else {
	  BufferedReader br = new BufferedReader(new FileReader(filePath));
	  
	  String temp = "";
	  while ((temp = br.readLine()) != null) {
	   System.out.println(temp);
	   int i = 0;
	   for (i = 0; i < temp.length(); i++) {
	    String subTemp = temp.substring(i, i + 1);
	    if (subTemp.matches("[^\\x00-\\xff]")) {
	     System.out.println(subTemp + ": true");
	     break;
	    } else
	     System.out.println(subTemp + ": false");
	   }
	   if(i == temp.length())
	      temp = temp.substring(0, i - 1) + "\t" + temp.substring(i);
	   else
	    temp += "\t";
	   writeInfo=writeInfo+temp+"\n";
	    
	  }
	 
	  br.close();
	  System.out.println(writeInfo);
	  return writeInfo;
}
  
  
 }
 
 public static void delete(String file)
 {
	 FileOutputStream testfile;
	try {
		testfile = new FileOutputStream(file);
		 try {
			testfile.write(new String("").getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
 }
 
 public static void main(String[] args) throws IOException {
	new TextConvertor().read("hositoryMsg/aaa.txt");
}
}