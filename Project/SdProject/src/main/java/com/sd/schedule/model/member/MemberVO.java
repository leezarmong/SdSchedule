package com.sd.schedule.model.member;



public class MemberVO {
	
	
	private int member_no; // 멤버 번호
	private String user_id; // 접속 매장
	private String member_name; // 멤버 이름
	private String member_grade; // 직위
	
	
	

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getMember_grade() {
		return member_grade;
	}

	public void setMember_grade(String member_grade) {
		this.member_grade = member_grade;
	}

	public int getMember_no() {
		return member_no;
	}

	public void setMember_no(int member_no) {
		this.member_no = member_no;
	}

	public String getMember_name() {
		return member_name;
	}

	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}

	@Override
	public String toString() {
		return "MemberVO [member_no=" + member_no + ", user_id=" + user_id + ", member_name=" + member_name
				+ ", member_grade=" + member_grade + ", getUser_id()=" + getUser_id() + ", getMember_grade()="
				+ getMember_grade() + ", getMember_no()=" + getMember_no() + ", getMember_name()=" + getMember_name()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}

	

	

	
	
	

}
