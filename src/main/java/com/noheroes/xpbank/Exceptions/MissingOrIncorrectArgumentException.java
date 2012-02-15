/*
 * Copyright (C) 2011 No Heroes.
 * See readme for license details.
 */
package com.noheroes.xpbank.Exceptions;

public class MissingOrIncorrectArgumentException extends Exception {
	private final static long serialVersionUID = 1l;
	private String message;
	
	/*
	 * Setting the message of the Exception to a new String
	 */
	public MissingOrIncorrectArgumentException () {
		this.message = "Missing or Incorrect Arguments. See /xpb help.";
	}
	
	/*
	 * Setting the message of the Exception to the given message
	 */
	public MissingOrIncorrectArgumentException (String message) {
		this.message = message;
	}
	
	/*
	 * returns the message
	 */
	@Override
	public String getMessage () {
		return this.message;
	}
}
