package util.tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;





public class GetUserFlag {
    private String userName;
    
    public GetUserFlag(String userNameString)
    {
    	this.userName=userNameString;
    }
    
    
    public String getUserFlagInfo(String userString)
    {
        String userInfoString = "该用户很懒,什么也没有留下......";
        Connection connection=new DataBaseControl().buildConn("root", "root");
        ResultSet rt=null;
        try {
			PreparedStatement ps=connection.prepareStatement("select specificFlag from specificFlag where userName="+"'"+userString+"'");
			rt=ps.executeQuery();
			while(rt.next())
			{
			userInfoString=rt.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return userInfoString;
    }
}
