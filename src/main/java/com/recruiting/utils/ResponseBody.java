package com.recruiting.utils;

import lombok.Data;

@Data
public class ResponseBody {
	
	private int statusCode;
	private String responseMessage;
	private Object data;

}