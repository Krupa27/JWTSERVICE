package com.example.jwt.dao;

public class CohortDTO {
	private String cohortCode;
	   private String bussinessType;
	   private int gencCount;
	   private String location;
	   private String hr_id;
	   
	   
	   
	public CohortDTO(){}
	public String getCohortCode() {
		return cohortCode;
	}
	public void setCohortCode(String cohortCode) {
		this.cohortCode = cohortCode;
	}
	public String getBussinessType() {
		return bussinessType;
	}
	public void setBussinessType(String bussinessType) {
		this.bussinessType = bussinessType;
	}
	public int getGencCount() {
		return gencCount;
	}
	public void setGencCount(int gencCount) {
		this.gencCount = gencCount;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getHr_id() {
		return hr_id;
	}
	public void setHr_id(String hr_id) {
		this.hr_id = hr_id;
	}
	public CohortDTO(String cohortCode, String bussinessType, int gencCount, String location, String hr_id) {
		super();
		this.cohortCode = cohortCode;
		this.bussinessType = bussinessType;
		this.gencCount = gencCount;
		this.location = location;
		this.hr_id = hr_id;
	}
	@Override
	public String toString() {
		return "CohortDTO [cohortCode=" + cohortCode + ", bussinessType=" + bussinessType + ", gencCount=" + gencCount
				+ ", location=" + location + ", hr_id=" + hr_id + "]";
	}
	   
	   
}
