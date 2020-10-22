package com.recruiting.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.recruiting.entity.Application;
import com.recruiting.service.ApplicationService;
import com.recruiting.utils.ResponseBody;
import com.recruiting.utils.ResponseCode;
import com.recruiting.utils.ResponseMessage;

@RestController
@RequestMapping("/application")
public class ApplicationController {
	
	@Autowired
	ApplicationService applicationService;
	
	@RequestMapping(value="/apply",produces="application/json",method = {RequestMethod.POST})
	public ResponseEntity<ResponseBody> createOffer(@Valid @RequestBody Application applicationDetails) {
		ResponseBody response = null;
		try {
		    	response = applicationService.apply(applicationDetails);
				return new ResponseEntity<ResponseBody>(response,HttpStatus.CREATED);
			} catch (Exception e) {
				response = new ResponseBody();
				response.setResponseMessage(ResponseMessage.FAILED);
				response.setStatusCode(ResponseCode.FAILED);
				return new ResponseEntity<ResponseBody>(response,HttpStatus.OK);
			}
		}
	
	@RequestMapping(value="/get-application/{jobTitle}",produces="application/json",method = {RequestMethod.GET})
	public ResponseEntity<ResponseBody> getApplicationPerOffer(@PathVariable("jobTitle") String jobTitle) {
		ResponseBody response = null;
		try {
		    	response = applicationService.getApplicationPerOffer(jobTitle);
				return new ResponseEntity<ResponseBody>(response,HttpStatus.OK);
			} catch (Exception e) {
				response = new ResponseBody();
				response.setResponseMessage(ResponseMessage.FAILED);
				response.setStatusCode(ResponseCode.FAILED);
				return new ResponseEntity<ResponseBody>(response,HttpStatus.OK);
			}
		}
	
	@RequestMapping(value="/get-application/{jobTitle}/{candidateEmail}",produces="application/json",method = {RequestMethod.GET})
	public ResponseEntity<ResponseBody> getApplicationByOfferAndApplicantEmail(@PathVariable("jobTitle") String jobTitle, @PathVariable("candidateEmail") String candidateEmail) {
		ResponseBody response = null;
		try {
		    	response = applicationService.getApplicationByApplicantDetails(jobTitle,candidateEmail);
				return new ResponseEntity<ResponseBody>(response,HttpStatus.OK);
			} catch (Exception e) {
				response = new ResponseBody();
				response.setResponseMessage(ResponseMessage.FAILED);
				response.setStatusCode(ResponseCode.FAILED);
				return new ResponseEntity<ResponseBody>(response,HttpStatus.OK);
			}
		}
	

}

