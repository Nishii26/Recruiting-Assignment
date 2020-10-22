package com.recruiting.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.recruiting.entity.Offer;
import com.recruiting.service.OfferService;
import com.recruiting.utils.ResponseBody;
import com.recruiting.utils.ResponseCode;
import com.recruiting.utils.ResponseMessage;

@Service
public class OfferServiceImpl implements OfferService {
	
	public static List<Offer> offersList = new ArrayList<>();
	private static Long offerId =1l;
	
	//Method to create job offer
	@Override
	public ResponseBody createOffer(Offer offer) {
		
		//Response to be returned
		ResponseBody response = new ResponseBody();
		
		//Check if offer exists with same job title
		Optional<Offer> existingJob = offersList.stream().filter(o->o.getJobTitle().equalsIgnoreCase(offer.getJobTitle())).findFirst();
		if(existingJob.isPresent()) {
			response.setResponseMessage(ResponseMessage.EXISTING_OFFER);
			response.setStatusCode(ResponseCode.RECORDS_EXISTS);
			return response;
		}else {
			
			//If offer doesn't exist create a new job offer
			Offer newOffer = new Offer();
			newOffer.setJobTitle(offer.getJobTitle());
			newOffer.setStartDate(offer.getStartDate());
			newOffer.setNoOfApplications(0);
			newOffer.setJobId(offerId);
			offersList.add(newOffer);
			
			//Set Response data
			response.setResponseMessage(ResponseMessage.SUCCESS);
			response.setStatusCode(ResponseCode.SUCCESS);
			response.setData(newOffer);
			
			//Increment offer id
			offerId++;
			
			//Return response
			return response;
		}
	}

	//Method to get all job offers
	@Override
	public ResponseBody getAllOffer() {

		//Response to be returned
		ResponseBody response = new ResponseBody();
		
		//check if offer list contains data or not
		if(offersList.isEmpty()) {
			response.setResponseMessage(ResponseMessage.NO_RECORD_FOUND);
			response.setStatusCode(ResponseCode.NO_RECORD_FOUND);
			return response;
		}else {
			//If list contains data then return the list
			response.setResponseMessage(ResponseMessage.SUCCESS);
			response.setStatusCode(ResponseCode.SUCCESS);
			response.setData(offersList);
			return response;
		}
	}

	//Method to find job offer by job title
	@Override
	public ResponseBody getByTitle(String jobTitle) {
		
		//Response to be returned
		ResponseBody response = new ResponseBody();
		
		//Check if job offer with provided job title is present or not
		Optional<Offer> existingJob = offersList.stream().filter(o->o.getJobTitle().equalsIgnoreCase(jobTitle)).findFirst();

		//If job offer exists return job offer details else return no data found
		if(existingJob.isPresent()) {
			response.setResponseMessage(ResponseMessage.SUCCESS);
			response.setStatusCode(ResponseCode.SUCCESS);
			response.setData(existingJob);
			return response;
		}else {
			response.setResponseMessage(ResponseMessage.NO_RECORD_FOUND);
			response.setStatusCode(ResponseCode.NO_RECORD_FOUND);
			return response;
		}
	}

}
