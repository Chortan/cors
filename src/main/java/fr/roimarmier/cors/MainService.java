package fr.roimarmier.cors;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Base64;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Service;


@Service
public class MainService {
	public String defaultApi(RequestApi url){
		HttpGet http = new HttpGet(url.getUrl());
		String credential = Base64.getEncoder().encodeToString(new String(url.getApikey()+":").getBytes());
				
		if(url.getApikey()!=null){
			if(!url.getApikey().isEmpty()){
				http.setHeader("Authorization", "Basic " + credential);
			}
		}
		return this.getResultHttp(http);
	}
	
	public String imagesApi(RequestApi url){
		HttpGet http = new HttpGet(url.getUrl());
		http.setHeader("Api-Key", url.getApikey());
		return this.getResultHttp(http);
	}
	
	public byte[] getImage(String name, String apikey, HttpServletResponse response){
		
		return null;
	}
	
	private String getResultHttp(HttpGet get){
		HttpClient client = HttpClientBuilder.create().build();
		System.out.println("executing request " + get.getRequestLine());
		StringBuilder apiResponse = new StringBuilder();
		try {
			HttpResponse response = client.execute(get);
			 System.out.println("Resposne :"+response.getStatusLine().getStatusCode()+" - "+response.getStatusLine().getReasonPhrase());
			 
			 BufferedReader rd = new BufferedReader(
				        new InputStreamReader(response.getEntity().getContent()));
			 String line;
			 while ((line = rd.readLine()) != null) {
			 	apiResponse.append(line);
			 }
		} catch (IOException e) {
			e.printStackTrace();
		}
		return apiResponse.toString();
	}
}
