/**
 * @author Safikur Khan
 * 
 * This is the REST controller for home page personalization
 */
package com.walgreens.dae.hpp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.walgreens.dae.hpp.exception.HppException;
import com.walgreens.dae.hpp.hystrix.commands.DefaultOfferHystrixCommands;
import com.walgreens.dae.hpp.hystrix.commands.LoyaltyMemberHystrixCommands;
import com.walgreens.dae.hpp.hystrix.commands.PrintVersionHystrixCommands;
import com.walgreens.dae.hpp.hystrix.commands.UniqueIdHystrixCommands;
import com.walgreens.dae.hpp.model.ErrorMessage;
import com.walgreens.dae.hpp.model.PersonalizedOfferRequest;
import com.walgreens.dae.hpp.model.PersonalizedOfferResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value="/offers")
@Api(value="personalize offers")
public class PersonalizeOfferController {
	
	private static final Logger log = LoggerFactory.getLogger(PersonalizeOfferController.class);
	
	@Autowired
	private PrintVersionHystrixCommands printVersionHystrixCommands;
	
	@Autowired
	private LoyaltyMemberHystrixCommands loyaltyMemberHystrixCommands;
	
	@Autowired
	private UniqueIdHystrixCommands uniqueIdHystrixCommands;
	
	@Autowired
	private DefaultOfferHystrixCommands defaultOfferHystrixCommands;
	
	@ApiOperation(value="Get Personalize Offers", consumes="application/Json", response=PersonalizedOfferResponse.class)
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
	@RequestMapping(value="/v1", 
					method = RequestMethod.POST, 
					consumes = { "application/json" }, 
					produces = { "application/json" })
	public @ResponseBody PersonalizedOfferResponse getPersonalizedOffers(@RequestBody PersonalizedOfferRequest request) throws Exception{
		log.debug("Entering getPersonalizedOffers()");
		
		// validate request pay load
		// if not validated, send a error response
		try{
			validateRequestJson(request);
		} catch (Exception e) {
			log.error("Request payload can not be validated");
			PersonalizedOfferResponse errorResponse = new PersonalizedOfferResponse();
			ErrorMessage error = new ErrorMessage();
			error.setDescription(e.getMessage());
			error.setErrorSrc("request payload");
			errorResponse.setError(error);
			return errorResponse;
		}
		
		PersonalizedOfferResponse response = new PersonalizedOfferResponse();
		String memberId = request.getLoyaltyMemberId();
		try{
			List<String> uniqueKeys = new ArrayList<>();
			// get the print version number
			Future<String> storePrintVersionNbr = printVersionHystrixCommands.getPrintVersionNbrAsync(Integer.valueOf(request.getLocationNbr()));
			
			// get the 7partKeys for the loyalty member
			Future<List<String>> future7PartKeys = loyaltyMemberHystrixCommands.get7PartKeysByMemberId(memberId);
			
			String versionNbr = storePrintVersionNbr.get();
			List<String> sevenPartKeys = future7PartKeys.get();
			
			// get the unique Ids for each 7partKey concurrently
			ExecutorService exec = Executors.newFixedThreadPool(sevenPartKeys.size());
			List<Callable<String>> asyncTasks = new ArrayList<>();
			sevenPartKeys.forEach(sevenPartKey -> asyncTasks.add(() -> uniqueIdHystrixCommands.getUniqueIdBySevenPartKey(sevenPartKey, versionNbr)));
			try{
				exec.invokeAll(asyncTasks)
					.stream()
					.map(this::getFutureObject)
					.forEach(uniqueKeys::add);
			} catch(Exception e){
				log.error("Exception while getting unique keys -->"+e);
				throw new RuntimeException(e);
			} finally {
				if(!exec.isTerminated())
					exec.shutdown();
			}
			response.setUniqueKeys(uniqueKeys);
		} catch (Exception e) {
			log.error("Exception; fallback to default offer -->"+e);
			log.error("getting default offer");
			// any Exception while getting the personalized offers, fallback to the default offers
			try{
				List<String> defaultOffers = defaultOfferHystrixCommands.getDefaultOffers();
				response.setUniqueKeys(defaultOffers);
			} catch (Exception e1) {
				ErrorMessage error = new ErrorMessage();
				error.setErrorSrc("DEFAULT OFFER SERVICE");
				error.setDescription(e1.getMessage());
				response.setError(error);
			}
		}
		
		log.debug("Exiting getPersonalizedOffers()");
		return response;
	}
	
	private void validateRequestJson(PersonalizedOfferRequest request) throws HppException{
		log.debug("Entering validateRequestJson()");
		if(null == request)
			throw new HppException("request can't be null");
		if(null == request.getLoyaltyMemberId())
			throw new HppException("loyalty member Id can't be null");
		log.debug("Exiting validateRequestJson()");
	}
	
	/**
	 * Generic method to return the future object
	 * @param future <T>
	 * @return Type T object
	 */
	private <T> T getFutureObject(Future<T> future) {
		try {
			return future.get();
		} catch (InterruptedException | ExecutionException e) {
			throw new IllegalStateException(e);
		}
	}
	
	
	// TODO delete the below code
	
	@RequestMapping(value="/testoffers", method = RequestMethod.GET)
	public @ResponseBody PersonalizedOfferResponse getTestOffers() throws Exception{
		PersonalizedOfferRequest request = new PersonalizedOfferRequest();
		request.setLocationNbr("12314");
		request.setLoyaltyMemberId("1234234923874");
		return getPersonalizedOffers(request);
	}
}
