package com.sd.schedule.model.user;




public class UserVO {
	
	private String user_id;		// 접속 아이디
	private String user_pass;	// 접속 비밀번호
	
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_pass() {
		return user_pass;
	}
	public void setUser_pass(String user_pass) {
		this.user_pass = user_pass;
	}
	
	
	@Override
	public String toString() {
		return "UserVO [user_id=" + user_id + ", user_pass=" + user_pass + ", getUser_id()=" + getUser_id()
				+ ", getUser_pass()=" + getUser_pass() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	
	
	
	
	


}
