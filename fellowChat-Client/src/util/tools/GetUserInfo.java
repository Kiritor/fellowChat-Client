package util.tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import util.pojo.UserInfo;
/*根据用户的姓名找到用户的详细信息*/
public class GetUserInfo {
	
	private String userName;
	public GetUserInfo(String userString)
	{
	   this.userName=userString;	
	}
	/*得到用户详细信息的方法*/
	
	
	public static int getMaxId()
	{
		int result=0;
		Connection connection=new DataBaseControl().buildConn("root", "root");
        ResultSet rt=null;
        String sqlString="select max(id ) from  userinfo";
     
        try {
        	
            PreparedStatement psStatement=connection.prepareStatement(sqlString);
         rt=psStatement.executeQuery();
         while(rt.next())
         {
        	 result = rt.getInt(1);
         }
           
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	
	}
	
	public UserInfo getUserInfo()
	{
		UserInfo userInfo=new UserInfo();
		Connection connection=new DataBaseControl().buildConn("root", "root");
        ResultSet rt=null;
        String sqlString="select * from userinfo where username="+"'"+this.userName+"'";
        try {
        	
            PreparedStatement psStatement=connection.prepareStatement(sqlString);
            rt=psStatement.executeQuery();
            while(rt.next())
            {
            	userInfo.setId(rt.getInt(1));
            	userInfo.setUserName(rt.getString(2));
            	userInfo.setUserPwd(rt.getString(3));//密码
            	userInfo.setUserSex(rt.getString(4));
            	userInfo.setUserAge(rt.getInt(5));
            	userInfo.setUserImage(rt.getString(6));
            	//至于其他两项信息没有必要
            }
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userInfo;
	}

}
