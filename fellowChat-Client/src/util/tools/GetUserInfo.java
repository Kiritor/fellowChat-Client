package util.tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import util.pojo.UserInfo;
/*�����û��������ҵ��û�����ϸ��Ϣ*/
public class GetUserInfo {
	
	private String userName;
	public GetUserInfo(String userString)
	{
	   this.userName=userString;	
	}
	/*�õ��û���ϸ��Ϣ�ķ���*/
	
	
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
            	userInfo.setUserPwd(rt.getString(3));//����
            	userInfo.setUserSex(rt.getString(4));
            	userInfo.setUserAge(rt.getInt(5));
            	userInfo.setUserImage(rt.getString(6));
            	//��������������Ϣû�б�Ҫ
            }
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userInfo;
	}

}
