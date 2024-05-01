package com.sd.schedule.model.member;



public class MemberVO {
	
	private String member_name; // 멤버 이름
	
	
	

	public String getMember_name() {
		return member_name;
	}

	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}

	@Override
	public String toString() {
		return "MemberVO [member_name=" + member_name + ", getMember_name()=" + getMember_name() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	
	

}
