package util.tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import util.pojo.CommentInfo;
import util.pojo.UserInfo;

public class GetUserList {
	private String address;

	public GetUserList(String address) {
		this.address = address;

	}

	public static ArrayList<UserInfo> getList(String address) {
		ArrayList<UserInfo> list = new ArrayList<UserInfo>();
		Connection connection = new DataBaseControl().buildConn("root", "root");
		ResultSet rt = null;
		try {

			String sqlString = "select *from userinfo where id in (select  id from address where area="
					+ "'" + address + "'" + ")";
System.out.println("按照地址查询的sql语句是："+sqlString);
			// PreparedStatement
			// ps=connection.prepareStatement("update comment set comment_content="+"'"+text+"'"+"  "+"where userName="+"'"+this.userName+"'"+"  "+"and"+"  commenter="+"'"+this.commenter+"'");
			PreparedStatement ps = connection.prepareStatement(sqlString);
			rt = ps.executeQuery();
			while (rt.next()) {
				UserInfo userInfo = new UserInfo();
				userInfo.setId(rt.getInt(1));
            	userInfo.setUserName(rt.getString(2));
            	userInfo.setUserPwd(rt.getString(3));//密码
            	userInfo.setUserSex(rt.getString(4));
            	userInfo.setUserAge(rt.getInt(5));
            	userInfo.setUserImage(rt.getString(6));
				list.add(userInfo);// 加入到list中去
			}

			System.out.println(list.size());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;

	}
}
