package com.example.parakeet;

import java.util.EventListener;

/**
 * 
 * @author Yoshimori
 *
 */
public interface DialogListener extends EventListener {

	//public void onPositiveClick();
	
	public void onPositiveClick(String listName, boolean isPublic, String discription);
	
	public void onPositiveClick(String newListName, boolean isPublic, String discription, long listId);
	
	public void onNegativeClick();
}
