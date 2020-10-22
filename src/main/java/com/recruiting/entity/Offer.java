package com.recruiting.entity;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class Offer {
	
	
	private Long jobId;
	@NotBlank(message="Job Title is mandatory field!")
	private String jobTitle;
	@NotBlank(message="start Date is mandatory field!")
	private Long startDate;	
	private Integer noOfApplications;

}