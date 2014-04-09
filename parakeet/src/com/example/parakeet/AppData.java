package com.example.parakeet;

public class AppData {

	private String screenName = null;
	private String url = null;
	private boolean isChecked = false;
	private String id = null;

	public String getScreenName() {
		return screenName;
	}

	public String getUrl() {
		return url;
	}

	public boolean getIsChecked() {
		return isChecked;
	}
	
	public String getId() {
		return id;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setIsChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	
	public void setId(String id) {
		this.id = id;
	}
}
