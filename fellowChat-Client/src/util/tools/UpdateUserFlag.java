package util.tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateUserFlag {
	 private String userName;
	    
	    public UpdateUserFlag(String userNameString)
	    {
	    	this.userName=userNameString;
	    }
	    
	    
	    public void updateUserFlagInfo(String userString,String textString )
	    {
	       
	        Connection connection=new DataBaseControl().buildConn("root", "root");
	        ResultSet rt=null;
	        try {

	        	//�����û��Ѿ��й�������ֱ���޸ģ���һ��Ҳû��д������Ҫ����
	        	String sqlStringF="select *from specificFlag where userName="+"'"+userString+"'";
	            PreparedStatement ppsPreparedStatement=connection.prepareStatement(sqlStringF);
	            rt=ppsPreparedStatement.executeQuery();
	            if(rt.next())
	            {
	            	String sqlString=  "UPDATE specificFlag SET specificFlag="+"'"+textString+"'"+" WHERE userName="+"'"+userString+"'";
					PreparedStatement ps=connection.prepareStatement(sqlString);
	                ps.executeUpdate();
	            }else {
	            	String sqlString="insert into specificFlag values("+"'"+userString+"'"+","+"'"+textString+"'"+")";
					PreparedStatement ps=connection.prepareStatement(sqlString);
              ps.execute();
				}
	        	
	        	
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	       
	    }
}
