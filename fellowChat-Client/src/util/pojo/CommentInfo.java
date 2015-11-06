package util.pojo;

public class CommentInfo {
	private String auther;
	private String comment_content;

	public String getAuther() {
		return auther;
	}

	public void setAuther(String auther) {
		this.auther = auther;
	}

	public String getComment_content() {
		return comment_content;
	}

	public void setComment_content(String commentContent) {
		comment_content = commentContent;
	}

	public String getCommenter() {
		return commenter;
	}

	public void setCommenter(String commenter) {
		this.commenter = commenter;
	}

	private String commenter;
}
