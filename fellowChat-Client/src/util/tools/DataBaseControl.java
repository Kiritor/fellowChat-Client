/*�������ݿ��һЩ��������*/

package util.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;


import util.pojo.UserInfo;
import util.runninglog.RunningLog;


public class DataBaseControl {
	
	public static Connection conn;
    
	//�������ݿ������
	public static Connection buildConn(String name,String pwd){
		
	    try{
	    	Class.forName("com.mysql.jdbc.Driver");
	    	String url = "jdbc:mysql://localhost:3306/manchat";
	    	conn = DriverManager.getConnection(url,name,pwd);
	    	RunningLog.record("���ݿ����ӳɹ�������");
	    	return conn;
	    	
	    }catch(Exception e){
	    	return null;
	    }
		
	}
}

