package com.comcast.project.controller;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.comcast.campaign.vo.Campaign;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AdCampaignControllerTest {

	static RestTemplate restTemplate = null;
	
	public static ObjectMapper objectMapper = null;
	
	@BeforeClass
	public static void setup(){
		restTemplate = new RestTemplate();
		objectMapper = new ObjectMapper();
	}
	
	@Test
	public void testAdd(){
		String status = null;
		ResponseEntity<Object> responseEntity;
		
		try{
			HttpEntity<String> firstRquest = createRequest("1", 30);
			responseEntity = hitPostService(firstRquest, String.class);
			if(responseEntity.getStatusCode().is2xxSuccessful()){
				status = (String)responseEntity.getBody();
				Assert.assertNotNull(status);
				Assert.assertEquals("Added store Object", status);
			}
			
			HttpEntity<String> secondRequest = createRequest("1",0);
			responseEntity = hitPostService(secondRequest, Error.class);
			
			if(responseEntity.getStatusCode().is2xxSuccessful()){
				Error error = (Error)responseEntity.getBody();
				Assert.assertNotNull(status);
				Assert.assertEquals("failed to store Object", status);
			}
		}catch(Exception e){
			
		}
	}
	
	@Test
	public void testAddAndGet(){
		try {
			HttpEntity<String> thirdRequest = createRequest("2", 60);
			hitPostService(thirdRequest,String.class);
			
			HttpEntity<String> fourthReq = createRequest("3",60);
			hitPostService(fourthReq, String.class);
			Campaign campaign = null;
			Object object = get("2", Campaign.class);
			if(object instanceof Campaign){
				campaign = (Campaign) object;
			}
			Assert.assertNotNull(campaign);
			Assert.assertEquals("2ad_content", campaign.getAdContent());
		
		} catch(Exception e){
			
		}
	}
	
	@Test
	public void testNoActiveCampaignException(){
		try {
			Error error = null;
			Thread.sleep(5000);
			Object object = get("1",Error.class);
			if(object instanceof Error){
				error = (Error)object;
			}
			Assert.assertNotNull(error);
			Assert.assertEquals("NoActiveCampaignException",error.getMessage());
		} catch(Exception e){
			
		}
	}
	
	public Object get(String partnerId, Class class1){
		Object object = null;
		ResponseEntity<Object> result = restTemplate.getForEntity("http://localhost:8080/get?partnerId=" +partnerId ,class1);
		if(result.getStatusCode().is2xxSuccessful()){
			object = result.getBody();
		}
		return object;
	}
	
	private ResponseEntity<Object> hitPostService(HttpEntity<String> entity, Class class1){
		return restTemplate.exchange("http://localhost:8080/add", HttpMethod.POST, entity, class1);
	}
	
	private HttpEntity<String> createRequest(String input , int duration){
		Map<String, Object> requestBody = new HashMap<String, Object>();
		requestBody.put("partner_id",input);
		requestBody.put("duration", duration);
		requestBody.put("ad_content", input + "ad_content");
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> httpEntity = null;
		try {
			httpEntity = new HttpEntity<String>(objectMapper.writeValueAsString(requestBody), requestHeaders);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return httpEntity;
	}
	
}
