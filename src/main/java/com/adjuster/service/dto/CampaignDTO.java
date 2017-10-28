package com.adjuster.service.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CampaignDTO {
	private long id;
	private Date startDate;
	private String cpm;
	private String name;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getCpm() {
		return cpm;
	}

	public void setCpm(String cpm) {
		this.cpm = cpm;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		return "id : " +  id + ", cpm : " + cpm + ", startDate : " + startDate + ", name : " + name; 
	}

}
