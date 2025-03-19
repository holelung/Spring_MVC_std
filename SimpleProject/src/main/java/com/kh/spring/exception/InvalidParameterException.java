package com.kh.spring.exception.controller;

public class InvalidParameterException extends RuntimeException {

	public InvalidParameterException(String message){
		super(message);
	}
}
