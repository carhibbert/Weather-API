package com.tts.weatherapp.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.tts.weatherapp.models.Response;


// Utility class, reading from API 


@Service
public class WeatherService {
	@Value("${api_key}")
	private String apiKey = "1e875ffebb4fb9ae4ed58207016d59ee";
	
	public Response getForecast(String zipCode) {
		UriComponents uri =
			UriComponentsBuilder.newInstance()
				.scheme("https")
				.host("api.openweathermap.org")				
				.path("/data/2.5/weather")
				.queryParam("zip", zipCode+",us")
				.queryParam("units", "imperial")
				.queryParam("appid", apiKey)
				.build();
		
		String url = uri.encode().toUriString();
		System.out.println(url);
		
		RestTemplate restTemplate = new RestTemplate();
		Response response;
		try {
			response = restTemplate.getForObject(url, Response.class);
		} 
		catch (HttpClientErrorException e) {
			response = new Response();
			response.setName("error");
		}
		return response;			
	}
}
