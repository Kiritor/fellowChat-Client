package util.tools;

import java.util.ArrayList;

import util.pojo.UserInfo;


public class FriendList {
    public static ArrayList<UserInfo> friendList = new ArrayList<UserInfo>();
    public static ArrayList<UserInfo> shakingList = new ArrayList<UserInfo>();
    public static ArrayList<UserInfo> onlineList = new ArrayList<UserInfo>();
    public static ArrayList<UserInfo> downlineList = new ArrayList<UserInfo>();

	public static ArrayList<UserInfo> getFriendList() {
		return friendList;
	}

	public static void setFriendList(ArrayList<UserInfo> friend) {
		for(int i=0;i<friend.size();i++){
			if(friend.get(i).getUserState().equals("b")){
				onlineList.add(friend.get(i));
			}else{
				downlineList.add(friend.get(i));
			}
		}
		friendList.addAll(onlineList);
		friendList.addAll(downlineList);
	}
	
	public static void addNewFriend(UserInfo user){
		if(user.getUserState().equals("b")){
			onlineList.add(user);
		}else{
			downlineList.add(user);
		}
		friendList = new ArrayList<UserInfo>();
		friendList.addAll(shakingList);
		friendList.addAll(onlineList);
		friendList.addAll(downlineList);
	}
	
	public static void deleteFriend(UserInfo user){
		boolean isDone = false;
		if(!isDone){
			for(UserInfo info : shakingList){
				if(info.getUserName().equals(user.getUserName())){
					shakingList.remove(info);
					isDone = true;
					break;
				}
			}
		}
		if(!isDone){
			for(UserInfo info : onlineList){
				if(info.getUserName().equals(user.getUserName())){
					onlineList.remove(info);
					isDone = true;
					break;
				}
			}
		}
		if(!isDone){
			for(UserInfo info : downlineList){
				if(info.getUserName().equals(user.getUserName())){
					downlineList.remove(info);
					isDone = true;
					break;
				}
			}
		}
		friendList = new ArrayList<UserInfo>();
		friendList.addAll(shakingList);
		friendList.addAll(onlineList);
		friendList.addAll(downlineList);
	}
	
	public static void joinOnline(String userName){
		UserInfo user = null;
		for(int i=0;i<downlineList.size();i++){
			if(downlineList.get(i).getUserName().equals(userName)){
				user = downlineList.remove(i);
				user.setUserState("b");
				onlineList.add(user);
				break;
			}
		}
		if(user==null){
			for(int i=0;i<shakingList.size();i++){
				if(shakingList.get(i).getUserName().equals(userName)){
					shakingList.get(i).setUserState("b");
					break;
				}
			}
		}
		friendList = new ArrayList<UserInfo>();
		friendList.addAll(shakingList);
		friendList.addAll(onlineList);
		friendList.addAll(downlineList);
	}
	
	public static void joinDownline(String userName){
		UserInfo user = null;
		for(int i=0;i<onlineList.size();i++){
			if(onlineList.get(i).getUserName().equals(userName)){
				user = onlineList.remove(i);
				user.setUserState("a");
				downlineList.add(user);
				break;
			}
		}
		if(user==null){
			for(int i=0;i<shakingList.size();i++){
				if(shakingList.get(i).getUserName().equals(userName)){
					shakingList.get(i).setUserState("a");
					break;
				}
			}
		}
		friendList = new ArrayList<UserInfo>();
		friendList.addAll(shakingList);
		friendList.addAll(onlineList);
		friendList.addAll(downlineList);
	}
	
	public static void joinShakingList(String userName){
		UserInfo user = null;
		for(int i=0;i<onlineList.size();i++){
			if(onlineList.get(i).getUserName().equals(userName)){
				user = onlineList.remove(i);
				break;
			}
		}
		if(user==null){
			for(int i=0;i<downlineList.size();i++){
				if(downlineList.get(i).getUserName().equals(userName)){
					user = downlineList.remove(i);
					break;
				}
			}
		}
		if(user!=null){
			shakingList.add(user);
			
			friendList = new ArrayList<UserInfo>();
			friendList.addAll(shakingList);
			friendList.addAll(onlineList);
			friendList.addAll(downlineList);
		}
	}
	
	public static void removeFromShakingList(String userName){
		UserInfo user = null;
		for(int i=0;i<shakingList.size();i++){
			if(shakingList.get(i).getUserName().equals(userName)){
				user = shakingList.remove(i);
				break;
			}
		}
		if(user!=null){
			if(user.getUserState().equals("a")){
				downlineList.add(user);
			}else{
				onlineList.add(user);
			}
		}
		friendList = new ArrayList<UserInfo>();
		friendList.addAll(shakingList);
		friendList.addAll(onlineList);
		friendList.addAll(downlineList);
	}
    
}
