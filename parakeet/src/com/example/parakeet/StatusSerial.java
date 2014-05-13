package com.example.parakeet;

import java.io.Serializable;

import twitter4j.Status;

public class StatusSerial implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Status status;

	public void setStatus(Status status) {
		this.status = status;
	}

	public Status getStatus() {

		return status;
	}

}
