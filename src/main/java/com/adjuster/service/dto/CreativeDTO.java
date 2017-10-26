package com.adjuster.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreativeDTO {

	private long id;
	private long parentId;
	private long clicks;
	private long views;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
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
		return "id : " + id + ", parentId : " + parentId + ", clicks : " + clicks + ", views : " + views;
	}

}
