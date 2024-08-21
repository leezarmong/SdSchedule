package com.sd.schedule.model.member;



public class MemberVO {
	
	
	private int member_no; // 멤버 번호
	private String user_id; // 접속 매장
	private String member_name; // 멤버 이름
	private String member_grade; // 직위
	
	private String frei;
	private String grill;
	private String make;
	private String expo;
	private String dish;
	
	
	
	

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
	
	

	public String getFrei() {
		return frei;
	}

	public void setFrei(String frei) {
		this.frei = frei;
	}

	public String getGrill() {
		return grill;
	}

	public void setGrill(String grill) {
		this.grill = grill;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getExpo() {
		return expo;
	}

	public void setExpo(String expo) {
		this.expo = expo;
	}

	public String getDish() {
		return dish;
	}

	public void setDish(String dish) {
		this.dish = dish;
	}

	@Override
	public String toString() {
		return "MemberVO [member_no=" + member_no + ", user_id=" + user_id + ", member_name=" + member_name
				+ ", member_grade=" + member_grade + ", frei=" + frei + ", grill=" + grill + ", make=" + make
				+ ", expo=" + expo + ", dish=" + dish + ", getUser_id()=" + getUser_id() + ", getMember_grade()="
				+ getMember_grade() + ", getMember_no()=" + getMember_no() + ", getMember_name()=" + getMember_name()
				+ ", getFrei()=" + getFrei() + ", getGrill()=" + getGrill() + ", getMake()=" + getMake()
				+ ", getExpo()=" + getExpo() + ", getDish()=" + getDish() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}


}
