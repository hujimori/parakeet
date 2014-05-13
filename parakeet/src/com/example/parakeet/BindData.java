package com.example.parakeet;

public class BindData {

	private String text;
	private String id;
	private String count;
	private int resourceId;
	private boolean ischecked;

	public BindData(String text, int resourceId) {

		this.text = text;
		this.resourceId = resourceId;

	}
	
	public BindData(String text, String count) {
		this.text = text;
		this.count = count;
	}

	public BindData(String text, boolean isChecked, String id) {

		this.text = text;
		this.resourceId = resourceId;
		this.ischecked = isChecked;
		this.id = id;

	}

	public void setIsChecked(boolean isChecked) {

		this.ischecked = isChecked;

	}

	public String getText() {

		return text;

	}

	public String getCount() {
		return count;
	}
	public String getId() {

		return id;

	}

	public boolean isChecked() {

		return ischecked;

	}

	public int getResourceId() {

		return resourceId;

	}
}