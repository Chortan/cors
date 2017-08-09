package fr.roimarmier.cors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin
@RequestMapping("/")
public class MainController {
	
	@Autowired 
	private MainService service;
	
	@ResponseStatus(code=HttpStatus.ACCEPTED)
	@RequestMapping(value="",method=RequestMethod.GET)
	public String ckeck(){
		return "{}";
	}
	
	@RequestMapping(value="",method=RequestMethod.POST)
	public String defaultApi(@RequestBody RequestApi request){
		request.setUrl(request.getUrl().replaceAll("\\s", "%20"));
		return this.service.defaultApi(request);
	}
	
	@RequestMapping(value="images",method=RequestMethod.POST)
	public String imagesApi(@RequestBody RequestApi request){
		request.setUrl(request.getUrl().replaceAll("\\s", "%20"));
		return this.service.imagesApi(request);
	}
	
	@RequestMapping(value="images/{name}/{apikey}",method=RequestMethod.GET,produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getImage(@PathVariable("name") String name, @PathVariable("apikey") String apikey,HttpServletResponse response){
		return this.service.getImage(name,apikey,response);
	}
}
