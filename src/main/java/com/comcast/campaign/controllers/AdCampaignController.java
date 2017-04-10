package com.comcast.campaign.controllers;

/**
 *  @author Sonu Mekala
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.comcast.campaign.exception.DuplicateKeyException;
import com.comcast.campaign.exception.NoActiveCampaignException;
import com.comcast.campaign.utill.CommonUtils;
import com.comcast.campaign.vo.Campaign;
import com.comcast.campaign.vo.Error;

@Controller
public class AdCampaignController {
	
	static Map<String, Campaign> storeObject = null;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> registerAdd(@RequestBody Campaign campaign,HttpServletRequest request) 
			throws DuplicateKeyException {
		String status = null;
		//Now add end time to the campaign object
		campaign.setTimeStamp(CommonUtils.getEndTime(Integer.parseInt(campaign.getDuration())));
		
		if (storeObject == null)
			storeObject = new HashMap<String, Campaign>();
		if (storeObject.containsKey(campaign.getPartnerId())) {
			//now compare end time
			Campaign oldCampaign = storeObject.get(campaign.getPartnerId());
			
			if (CommonUtils.compareDate(oldCampaign.getTimeStamp()))  {
				status = "failed to store Object";
				//LOGGER.debug("I can't store the object");
				throw new DuplicateKeyException("Already one active Ad campaign is playing for this partner id");
			} else {
				//System.out.println("In Else block");
				status = "updated store Object";
				storeObject.put(campaign.getPartnerId(), campaign);
			}
		}else{
			
			//System.out.println("In Else block");
			storeObject.put(campaign.getPartnerId(), campaign);
			status = "Added store Object";
		}
		return new ResponseEntity<String>(status,HttpStatus.OK);
	}

	
	@RequestMapping(value = "/get", method = RequestMethod.GET, produces = { "application/json" })
	@ResponseBody
	public ResponseEntity<Campaign> getCampaign(@RequestParam(value = "partnerId", required = true) String partnerId) throws NoActiveCampaignException {
		//LOGGER.debug("Inside getCampaign method");
		Campaign campaign = null;
		if (storeObject != null) {
			campaign = storeObject.get(partnerId);
			if(campaign != null){
				if (!CommonUtils.compareDate(campaign.getTimeStamp())) {
					throw new NoActiveCampaignException("No Active Campaign Exist");
				} 
			}else{
				throw new NoActiveCampaignException("No Active Campaign Exist");
			}
		}
		return new ResponseEntity<Campaign>(campaign,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = { "application/json" })
	@ResponseBody
	public ResponseEntity<List<Campaign>> getCampaignList() throws Exception {
		List<Campaign> list = new ArrayList<Campaign>();
		Campaign campaign = null;
		if (storeObject != null) {
			Set<Map.Entry<String, Campaign>> set = storeObject.entrySet();
			Iterator<Entry<String, Campaign>> iterator = set.iterator();
			while(iterator.hasNext()){
				Entry<String,Campaign> entry = iterator.next();
				campaign = entry.getValue();
				list.add(campaign);
			}
		}
		return new ResponseEntity<List<Campaign>>(list,HttpStatus.OK);
	}
	
	/**
	 * We can use @ControllerAdvice to handle global exceptions
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(NoActiveCampaignException.class)
    public ResponseEntity<Error> noActiveCampaignHandler(Exception ex) {
		Error error = new Error();
        error.setErrorCode(HttpStatus.PRECONDITION_FAILED.value());
        error.setMessage(ex.getMessage());
        return new ResponseEntity<Error>(error, HttpStatus.OK);
    }
	
	@ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<Error> duplicateKeyExceptionHandler(Exception ex) {
		Error error = new Error();
        error.setErrorCode(HttpStatus.PRECONDITION_FAILED.value());
        error.setMessage(ex.getMessage());
        return new ResponseEntity<Error>(error, HttpStatus.OK);
    }

}
