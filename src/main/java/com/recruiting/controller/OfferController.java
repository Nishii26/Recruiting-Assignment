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

import com.recruiting.entity.Offer;
import com.recruiting.service.OfferService;
import com.recruiting.utils.ResponseBody;
import com.recruiting.utils.ResponseCode;
import com.recruiting.utils.ResponseMessage;

@RestController
@RequestMapping("/offer")
public class OfferController {
	
	@Autowired
	OfferService offerService;
	
	@RequestMapping(value="/create-offer",produces="application/json",method = {RequestMethod.POST})
	public ResponseEntity<ResponseBody> createOffer(@Valid @RequestBody Offer offer) {
		ResponseBody response = null;
		try {
		    	response = offerService.createOffer(offer);
				return new ResponseEntity<ResponseBody>(response,HttpStatus.CREATED);
			} catch (Exception e) {
				response = new ResponseBody();
				response.setResponseMessage(ResponseMessage.FAILED);
				response.setStatusCode(ResponseCode.FAILED);
				return new ResponseEntity<ResponseBody>(response,HttpStatus.OK);
			}
		}
	
	@RequestMapping(value="/get-all-offer",produces="application/json",method = {RequestMethod.GET})
	public ResponseEntity<ResponseBody> getAllOffer() {
		ResponseBody response = null;
		try {
		    	response = offerService.getAllOffer();
				return new ResponseEntity<ResponseBody>(response,HttpStatus.OK);
			} catch (Exception e) {
				response = new ResponseBody();
				response.setResponseMessage(ResponseMessage.FAILED);
				response.setStatusCode(ResponseCode.FAILED);
				return new ResponseEntity<ResponseBody>(response,HttpStatus.OK);
			}
		}
	
	@RequestMapping(value="/get-offer/{JobTitle}",produces="application/json",method = {RequestMethod.GET})
	public ResponseEntity<ResponseBody> getOfferBytitle(@PathVariable("JobTitle") String jobTitle) {
		ResponseBody response = null;
		try {
		    	response = offerService.getByTitle(jobTitle);
				return new ResponseEntity<ResponseBody>(response,HttpStatus.OK);
			} catch (Exception e) {
				response = new ResponseBody();
				response.setResponseMessage(ResponseMessage.FAILED);
				response.setStatusCode(ResponseCode.FAILED);
				return new ResponseEntity<ResponseBody>(response,HttpStatus.OK);
			}
		}

}

