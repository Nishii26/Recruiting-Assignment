package com.recruiting.service;

import com.recruiting.entity.Application;
import com.recruiting.utils.ResponseBody;

public interface ApplicationService {

	ResponseBody apply(Application applicationDetails);

	ResponseBody getApplicationPerOffer(String jobTitle);

	ResponseBody getApplicationByApplicantDetails(String jobTitle,String candidateEmail);

}
