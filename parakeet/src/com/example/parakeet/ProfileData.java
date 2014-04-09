package com.example.parakeet;

import java.util.Date;

public class ProfileData {
	
	private String imageURL;
	private String bannerURL;
	private String description;
	private Date date;
	private Long ID;
	private String url;
	private String screenName;
	
	
	
	public ProfileData() {
		
	}
	
	
	public void setScreenName(String screenName) {
		
		this.screenName = screenName;
	}
	
	public String getScreenName() {
		
		return this.screenName;
	}
	
	public void setIURL(String imageURL) {
	
	this.imageURL = imageURL;
	
	}
	
	public String getIURL() {
		
		return this.imageURL;
	}
	
	public void setBURL(String bannerURL) {
		
		this.bannerURL = bannerURL;
		
	}
	
	public String getBURL() {
		

		return this.bannerURL;
		
	}
	
	public void setDescription(String description) {
		
		this.description = description;
		
	}
	
	public String getDescription() {
		
		return this.description;
		
	}

	public void setDate(Date date) {
		
		this.date = date;
		
	}
	
	public Date getDate() {
		
		return this.date;
		
	}
	
	public void setID(Long ID) {
		
		this.ID = ID;
		
	}
	
	public Long getID() {
		
		return this.ID;
		
	}
	
	public void setURL(String url) {
		
		this.url = url;
		
	}
	
	public String getURL() {
		
		return this.url;
		
	}
	
	
}
