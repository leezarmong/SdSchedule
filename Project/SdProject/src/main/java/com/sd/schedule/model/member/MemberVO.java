package com.sd.schedule.model.member;



public class MemberVO {
	
	private String member_name; // 멤버 이름
	private int member_no; // 멤버 번호
	
	
	

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
		return "MemberVO [member_name=" + member_name + ", member_no=" + member_no + ", getMember_no()="
				+ getMember_no() + ", getMember_name()=" + getMember_name() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

	
	
	

}
