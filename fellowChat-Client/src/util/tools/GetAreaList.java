package util.tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GetAreaList {

	public GetAreaList( ) {
		
	}

	public static ArrayList<String> getAreaList(String city) {
		ArrayList<String> list = new ArrayList<String>();
		Connection connection = new DataBaseControl().buildConn("root", "root");
		ResultSet rt = null;
		try {

			String sqlString = "select  distinct   area from address where city="+"'"+city+"'";
			
				
//System.out.println("按照地址查询的sql语句是："+sqlString);
			// PreparedStatement
			// ps=connection.prepareStatement("update comment set comment_content="+"'"+text+"'"+"  "+"where userName="+"'"+this.userName+"'"+"  "+"and"+"  commenter="+"'"+this.commenter+"'");
			PreparedStatement ps = connection.prepareStatement(sqlString);
			rt = ps.executeQuery();
			while (rt.next()) {
				
				String addressString=rt.getString(1);
				System.out.println("得到的区级、省级地址信息是："+addressString);
				list.add(addressString);// 加入到list中去
			}

			System.out.println(list.size());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;

	}
	/*首先进行测试*/
	public static void main(String[] args) {
		new GetCityList().getCityList();
		//测试结果通过验证
	}
}
