package com.recruiting.entity;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class Application {
	
	private Long Id;
	
	private Long relatedOffer;
	
	@NotBlank(message="Email is mandatory field!")
	private String candidateEmail;
	
	@NotBlank(message="Resume text is mandatory field!")
	private String resumeText;
	
	private String applicationStatus;
	
	@NotBlank(message="Job Title is mandatory field!")
	private String jobTitle;

}
