package com.adjuster.service.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Creative implements Comparable<Creative> {

	@Id
	private long id;

	@ManyToOne
	private Campaign campaign;

	private long clicks;
	private long views;
	
	public Creative() {}
	
	public Creative(long id, long clicks, long views, Campaign campaign) {
		this.id = id;
		this.clicks = clicks;
		this.views = views;
		this.campaign = campaign;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Campaign getCampaign() {
		return campaign;
	}

	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}

	public long getClicks() {
		return clicks;
	}

	public void setClicks(long clicks) {
		this.clicks = clicks;
	}

	public long getViews() {
		return views;
	}

	public void setViews(long views) {
		this.views = views;
	}

	public String toString() {
		return "id : " + id + ", clicks : " + clicks + ", views : " + views + ", campaign : " + campaign.toString();
	}

	@Override
	public int compareTo(Creative arg0) {
		if(this.id > campaign.getId()) {
			return 1;
		}
		if(this.id < campaign.getId()) {
			return -1;
		}
		return 0;
	}

}
