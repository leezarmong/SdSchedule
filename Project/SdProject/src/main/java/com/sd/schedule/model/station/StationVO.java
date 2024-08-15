package com.sd.schedule.model.station;



public class StationVO {
	
	private String frei;
	private String grill;
	private String make;
	private String expo;
	private String dish;
	
	
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
		return "StationVO [frei=" + frei + ", grill=" + grill + ", make=" + make + ", expo=" + expo + ", dish=" + dish
				+ ", getFrei()=" + getFrei() + ", getGrill()=" + getGrill() + ", getMake()=" + getMake()
				+ ", getExpo()=" + getExpo() + ", getDish()=" + getDish() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	
	
	

}
