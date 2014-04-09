package com.example.parakeet;

import java.io.Serializable;

import twitter4j.Status;

public class StatusSerial implements Serializable {

	private Status status;

	public StatusSerial(Status status) {

		this.status = status;

	}

	public Status getStatus() {

		return status;
	}

}
