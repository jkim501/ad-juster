package com.adjuster.service.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Campaign implements Comparable<Campaign> {

	@Id
	private long id;

	@OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL)
	private List<Creative> creatives;

	private Date startDate;
	private String cpm;
	private String name;
	
	public Campaign() {}
	
	public Campaign(long id, java.util.Date startDate, String cpm, String name) {
		this.id = id;
		this.startDate = new java.sql.Date(startDate.getTime());
		this.cpm = cpm;
		this.name = name;
		this.creatives = new ArrayList<>();
	}

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

	public List<Creative> getCreatives() {
		return creatives;
	}

	public void setCreatives(List<Creative> creatives) {
		this.creatives = creatives;
	}

	public String toString() {
		return "id : " + id + ", cpm : " + cpm + ", startDate : " + startDate + ", name : " + name;
	}


	@Override
	public int compareTo(Campaign campaign) {
		if(this.id > campaign.getId()) {
			return 1;
		}
		if(this.id < campaign.getId()) {
			return -1;
		}
		return 0;
	}

}
