package util.tools;

/**
 * 得到某个用户的所有留言，返回的是一个结果集*/
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import util.pojo.CommentInfo;

public class GetComment {
	   private String userName;
	 
	    public GetComment(String userNameString)
	    {
	    	this.userName=userNameString;
	    	
	    }
	    
	    
	    public ArrayList<CommentInfo> getCommentInfo(String username)
	    {
	    	ArrayList<CommentInfo> list=new ArrayList<CommentInfo>();
	        Connection connection=new DataBaseControl().buildConn("root", "root");
	        ResultSet rt=null;
	        try {
	        	String sqlString="select comment_content,commenter from comment where auther="+"'"+username+"'";
				//PreparedStatement ps=connection.prepareStatement("update comment set comment_content="+"'"+text+"'"+"  "+"where userName="+"'"+this.userName+"'"+"  "+"and"+"  commenter="+"'"+this.commenter+"'");
                PreparedStatement ps=connection.prepareStatement(sqlString);
	        	rt=ps.executeQuery();
	        	while(rt.next())
	        	{
	        		CommentInfo commentInfo =new CommentInfo();
	        		commentInfo.setComment_content(rt.getString(1));
	        		commentInfo.setCommenter(rt.getString(2));
	        		list.add(commentInfo);//加入到list中去
	        	}
              
				System.out.println(list.size());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
			return list;
	       
	    }
}
