package util.pojo;

import java.io.Serializable;

public class UserInfo implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 5030995490978231561L;
	private int id;
	private String userName;
    private String userPwd;
    private String userSex;
    private int userAge;
    private String userImage;
    private String userDownlineMsg;
    private String userState;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
    public String getUserState() {
		return userState;
	}
	public void setUserState(String userState) {
		this.userState = userState;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public String getUserSex() {
		return userSex;
	}
	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}
	public int getUserAge() {
		return userAge;
	}
	public void setUserAge(int userAge) {
		this.userAge = userAge;
	}
	public String getUserImage() {
		return userImage;
	}
	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}
	public String getUserDownlineMsg() {
		return userDownlineMsg;
	}
	public void setUserDownlineMsg(String userDownlineMsg) {
		this.userDownlineMsg = userDownlineMsg;
	}
}
