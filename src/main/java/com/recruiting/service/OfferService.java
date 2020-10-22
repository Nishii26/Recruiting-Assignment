package com.recruiting.service;

import com.recruiting.entity.Offer;
import com.recruiting.utils.ResponseBody;

public interface OfferService {

	ResponseBody createOffer(Offer offer);

	ResponseBody getAllOffer();

	ResponseBody getByTitle(String jobTitle);

}
