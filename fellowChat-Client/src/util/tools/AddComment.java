package util.tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddComment {
	   private String userName;
	   private  String commenter;
	    public AddComment(String userNameString,String commenter)
	    {
	    	this.userName=userNameString;
	    	this.commenter=commenter;
	    }
	    
	    
	    public void addCommentInfo(String text)
	    {
	        String commenterString = "该用户菜鸟,啥留言都没有......";
	        Connection connection=new DataBaseControl().buildConn("root", "root");
	        ResultSet rt=null;
	        try {
	        	String sqlString="insert into comment values("+"'"+this.userName+"'"+","+"'"+text+"'"+","+"'"+this.commenter+"'"+")";
				//PreparedStatement ps=connection.prepareStatement("update comment set comment_content="+"'"+text+"'"+"  "+"where userName="+"'"+this.userName+"'"+"  "+"and"+"  commenter="+"'"+this.commenter+"'");
                PreparedStatement ps=connection.prepareStatement(sqlString);
	        	ps.execute();
                
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	       
	    }
}
