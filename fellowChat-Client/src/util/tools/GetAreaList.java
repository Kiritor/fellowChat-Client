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
			
				
//System.out.println("���յ�ַ��ѯ��sql����ǣ�"+sqlString);
			// PreparedStatement
			// ps=connection.prepareStatement("update comment set comment_content="+"'"+text+"'"+"  "+"where userName="+"'"+this.userName+"'"+"  "+"and"+"  commenter="+"'"+this.commenter+"'");
			PreparedStatement ps = connection.prepareStatement(sqlString);
			rt = ps.executeQuery();
			while (rt.next()) {
				
				String addressString=rt.getString(1);
				System.out.println("�õ���������ʡ����ַ��Ϣ�ǣ�"+addressString);
				list.add(addressString);// ���뵽list��ȥ
			}

			System.out.println(list.size());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;

	}
	/*���Ƚ��в���*/
	public static void main(String[] args) {
		new GetCityList().getCityList();
		//���Խ��ͨ����֤
	}
}
