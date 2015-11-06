package util.tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import sun.misc.OSEnvironment;

public class Address {
	public Address() {

	}

	public static void add(int id,String city,String area) {
		
		Connection connection = new DataBaseControl().buildConn("root", "root");
		ResultSet rt = null;
		try {

			String sqlString = "insert into address values(?,?,?)";
			

			// System.out.println("按照地址查询的sql语句是："+sqlString);
			// PreparedStatement
			// ps=connection.prepareStatement("update comment set comment_content="+"'"+text+"'"+"  "+"where userName="+"'"+this.userName+"'"+"  "+"and"+"  commenter="+"'"+this.commenter+"'");
			PreparedStatement ps = connection.prepareStatement(sqlString);
			ps.setInt(1, id);
			ps.setString(2, city);
			ps.setString(3, area);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
}
