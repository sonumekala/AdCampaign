package com.comcast.campaign.utill;

/**
 *  @author Sonu Mekala
 */
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

public class CommonUtils {

	private static final Logger LOGGER = Logger.getLogger(CommonUtils.class);
	
	public static Date getEndTime(int seconds) {

		LOGGER.trace("*** In getEndTime *****");
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		cal.add(Calendar.SECOND, seconds);
		return cal.getTime();
	}

	public static boolean compareDate(Date endTime) {
		
		LOGGER.trace("**** In compare Date Method*****");
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		LOGGER.debug("CurrentTime==>["+cal.getTime()+"]");
		return endTime.after(cal.getTime());

	}
}
