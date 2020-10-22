package com.recruiting.serviceImpl;

import static com.recruiting.serviceImpl.OfferServiceImpl.offersList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.recruiting.entity.Application;
import com.recruiting.entity.Offer;
import com.recruiting.service.ApplicationService;
import com.recruiting.utils.ApplicationType;
import com.recruiting.utils.Constants;
import com.recruiting.utils.ResponseBody;
import com.recruiting.utils.ResponseCode;
import com.recruiting.utils.ResponseMessage;
import com.recruiting.utils.Utils;

@Service
public class ApplicationServiceImpl implements ApplicationService {
	
	public static List<Application> applicationsList = new ArrayList<>();
	private static Long applicationId =1l;

	//Method to fetch aPPlication details by candidate email and job title
	@Override
	public ResponseBody apply(Application applicationDetails) {
		
		//Response to be returned
		ResponseBody response = new ResponseBody();
		
		//Check if email is a valid email
		boolean isValidEmail = Utils.validate(applicationDetails.getCandidateEmail(), Constants.EMAIL_PATTERN);
		
		//Check if job title or candidate email is not a blank string
		if(applicationDetails.getJobTitle().trim().isEmpty() || applicationDetails.getCandidateEmail().trim().isEmpty() || !isValidEmail) {
			response.setResponseMessage(ResponseMessage.JOB_TITLE_OR_EMAIL);
			response.setStatusCode(ResponseCode.FAILED);
			return response;
		}else {
			Application application = new Application();
			
			//Check the job Title send by user is a valid job offer by checking in list 
			Optional<Offer> existingJobtTitle = offersList.stream().filter(j->j.getJobTitle().equalsIgnoreCase(applicationDetails.getJobTitle())).findFirst();
			
			//If job does not exist return response 
			if(!existingJobtTitle.isPresent()) {
				response.setResponseMessage(ResponseMessage.JOB_TITLE_OR_EMAIL);
				response.setStatusCode(ResponseCode.FAILED);
				return response;
			}
			
			//Check if user email has already been use for the provided email
			Optional<Application> existingEmail = applicationsList.stream().filter(a->a.getJobTitle().equalsIgnoreCase(existingJobtTitle.get().getJobTitle()) && a.getCandidateEmail().equalsIgnoreCase(applicationDetails.getCandidateEmail())).findFirst();
			if(existingEmail.isPresent()) {
				response.setResponseMessage(ResponseMessage.EXISTING_EMAIL);
				response.setStatusCode(ResponseCode.RECORDS_EXISTS);
				return response;
			}
			
			//create application record and add in list
			application.setRelatedOffer(existingJobtTitle.get().getJobId());
			application.setJobTitle(existingJobtTitle.get().getJobTitle());
			application.setCandidateEmail(applicationDetails.getCandidateEmail().trim());
			application.setApplicationStatus(ApplicationType.APPLIED);
			application.setResumeText(applicationDetails.getResumeText().trim());
			application.setId(applicationId);
			applicationsList.add(application);
			
			
			//Increment the application id 
			applicationId++;
			for(Offer offer : offersList) {
				if(offer.getJobId().equals(existingJobtTitle.get().getJobId())) {
					Integer noOfApplicants = offer.getNoOfApplications();
					noOfApplicants++;
					offer.setNoOfApplications(noOfApplicants);
					break;
				}
			}
			//Return success response
			response.setResponseMessage(ResponseMessage.SUCCESS);
			response.setStatusCode(ResponseCode.SUCCESS);
			response.setData(application);
			return response;
		}
	}

	//Method to fetch all aPPlication details by job title
	@Override
	public ResponseBody getApplicationPerOffer(String jobTitle) {
		
		//Response to be returned
		ResponseBody response = new ResponseBody();
		
		//Check if the job offer is blank
		if(jobTitle==null || jobTitle.trim().length()==0) {
			response.setResponseMessage(ResponseMessage.JOB_TITLE_OR_EMAIL);
			response.setStatusCode(ResponseCode.FAILED);
			return response;
		}
		//Check if the provided job title exists in our list
		Optional<Offer> existingJobtTitle = offersList.stream().filter(j->j.getJobTitle().equalsIgnoreCase(jobTitle)).findFirst();
		if(!existingJobtTitle.isPresent()) {
			response.setResponseMessage(ResponseMessage.JOB_TITLE_OR_EMAIL);
			response.setStatusCode(ResponseCode.FAILED);
			return response;
		}else {
			
			//If provided job title is a valid job title find all the relevant applications and return
			List<Application> applicationListByOfferName = applicationsList.stream().filter(a->a.getRelatedOffer().equals(existingJobtTitle.get().getJobId())).collect(Collectors.toList());
			if(applicationListByOfferName.isEmpty()) {
				response.setResponseMessage(ResponseMessage.NO_RECORD_FOUND);
				response.setStatusCode(ResponseCode.NO_RECORD_FOUND);
				return response;
			}
			else {
				response.setResponseMessage(ResponseMessage.SUCCESS);
				response.setStatusCode(ResponseCode.SUCCESS);
				response.setData(applicationListByOfferName);
				return response;
			}
		}
	}

	//Method to fetch aPPlication details by candidate email and job title
	@Override
	public ResponseBody getApplicationByApplicantDetails(String jobTitle,String candidateEmail) {
		
		//Response to be returned
		ResponseBody response = new ResponseBody();
		
		//Check if email is a valid email
		boolean isValidEmail = Utils.validate(candidateEmail, Constants.EMAIL_PATTERN);
		
		//Check if the job offer or candidate email is blank
		if(candidateEmail.trim().length()==0 || jobTitle.trim().length()==0 || !isValidEmail) {
			response.setResponseMessage(ResponseMessage.JOB_TITLE_OR_EMAIL);
			response.setStatusCode(ResponseCode.FAILED);
			return response;
		}
		
			//Check if the job offer and candidate email matches any application data
			Optional<Application> application = applicationsList.stream().filter(a->a.getJobTitle().equalsIgnoreCase(jobTitle) && a.getCandidateEmail().equalsIgnoreCase(candidateEmail)).findFirst();
			if(application.isPresent()) {
				response.setResponseMessage(ResponseMessage.NO_RECORD_FOUND);
				response.setStatusCode(ResponseCode.NO_RECORD_FOUND);
				return response;
			}else {
				response.setResponseMessage(ResponseMessage.SUCCESS);
				response.setStatusCode(ResponseCode.SUCCESS);
				response.setData(application);
				return response;
			}
	}

}
