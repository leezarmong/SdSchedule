package com.sd.schedule.model.station;



public class StationVO {
	
	private String frei;
	private String grill;
	private String make;
	private String expo;
	private String dish;
	private String member_name;
	private String user_id;
	private int member_no; // 멤버 번호
	
	
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
	public String getMember_name() {
		return member_name;
	}
	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public int getMember_no() {
		return member_no;
	}
	public void setMember_no(int member_no) {
		this.member_no = member_no;
	}
	@Override
	public String toString() {
		return "StationVO [frei=" + frei + ", grill=" + grill + ", make=" + make + ", expo=" + expo + ", dish=" + dish
				+ ", member_name=" + member_name + ", user_id=" + user_id + ", member_no=" + member_no + ", getFrei()="
				+ getFrei() + ", getGrill()=" + getGrill() + ", getMake()=" + getMake() + ", getExpo()=" + getExpo()
				+ ", getDish()=" + getDish() + ", getMember_name()=" + getMember_name() + ", getUser_id()="
				+ getUser_id() + ", getMember_no()=" + getMember_no() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	
	
	
}
