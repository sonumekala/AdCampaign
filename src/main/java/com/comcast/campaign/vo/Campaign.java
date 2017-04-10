package com.comcast.campaign.vo;

/**
 * @author Sonu Mekala
 */
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement
public class Campaign {
	
	@JsonProperty("partner_id")
	private String partnerId;
	private String duration;
	@JsonProperty("ad_content")
	private String adContent;
	private Date timeStamp;
	
	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getAdContent() {
		return adContent;
	}
	public void setAdContent(String adContent) {
		this.adContent = adContent;
	}
	public Date getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	@Override
	public String toString() {
		return "Campaign [partnerId=" + partnerId + ", duration=" + duration
				+ ", adContent=" + adContent + "]";
	}

}
