package com.example.parakeet;

/**
 * 
 * @author Yoshimori
 *
 */
public class BindData {

	// ---------------------------------------------------------------------------------------------
	// instance field 
	// ---------------------------------------------------------------------------------------------
	private String text;
	private String id;
	private int resourceId;
	private boolean ischecked;

	/**
	 * 
	 * @param text
	 * @param resourceId
	 */
	public BindData(String text, int resourceId) {

		this.text = text;
		this.resourceId = resourceId;

	}
	

	/**
	 * 
	 * @param text
	 * @param isChecked
	 * @param id
	 */
	public BindData(String text, boolean isChecked, String id) {

		this.text = text;
		this.ischecked = isChecked;
		this.id = id;

	}

	/**
	 * 
	 * @param isChecked
	 */
	public void setIsChecked(boolean isChecked) {

		this.ischecked = isChecked;

	}

	/**
	 * 
	 * @return
	 */
	public String getText() {

		return text;

	}

	/**
	 * 
	 * @return
	 */
	public int getCount() {
		return resourceId;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getId() {

		return id;

	}

	/**
	 * 
	 * @return
	 */
	public boolean isChecked() {

		return ischecked;

	}

	/**
	 * 
	 * @return
	 */
	public int getResourceId() {

		return resourceId;

	}
}