package util.tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateUserInfo {
	private String userName;
	private String imagerPath;//需要更新的头像名字
    public UpdateUserInfo(String userName,String imagePath)
    {
    	this.userName=userName;
    	this.imagerPath=imagePath;
    }
    
    public int updateUserImage()
    {
    	int result=0;
    	Connection connection=new DataBaseControl().buildConn("root", "root");
        ResultSet rt=null;
        String sqlString="update userinfo set userimage="+"'"+this.imagerPath+"'" +" where username="+"'"+this.userName+"'";
        try {
        	PreparedStatement pStatement=connection.prepareStatement(sqlString);
        	result=pStatement.executeUpdate();
            
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result; 
    }
}
