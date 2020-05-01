package com.ang.web;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ang.entities.Contact;
import com.ang.repository.ContactRepository;
import com.fasterxml.jackson.annotation.JsonView;
import com.ang.entities.View;

@RestController
@CrossOrigin("*")
public class ContactRest {
	@Autowired
	private ContactRepository contactRepository;
	
	@JsonView(View.FileInfo.class)
	@RequestMapping(value="/contacts", method=RequestMethod.GET)
	public List<Contact> getContacts(){
		return contactRepository.findAll();
	}
	
	@RequestMapping(value="/chercher", method=RequestMethod.GET)
	public Page<Contact> chercher(
			@RequestParam(name="mc", defaultValue="") String mc,
			@RequestParam(name="p", defaultValue="0") int p,
			@RequestParam(name="s", defaultValue="5") int s){
		return contactRepository.chercher("%"+mc+"%", new PageRequest(p,s));
	}
	
	@RequestMapping(value="/contact/{id}", method=RequestMethod.GET)
	public Contact getContact(@PathVariable long id){
		return contactRepository.findOne(id);
	}
	
	//@RequestMapping(value="/add/contact", method=RequestMethod.POST)
	@PostMapping("/add/contact")
	public Contact save(	@RequestParam("file") MultipartFile file,
						@RequestParam("nom") String nom,
						@RequestParam("prenom") String prenom,
						@RequestParam("date") String date) throws ParseException{
		try {
			System.out.println("hello !!!!!!!  ");
			SimpleDateFormat dt = new SimpleDateFormat("yyyyy-mm-dd"); 
			Date date2 = dt.parse(date); 
			Contact c = new Contact(nom, prenom, date2, "", 0, file.getBytes());
			c.setImg(file.getOriginalFilename());
			return contactRepository.save(c);
		} catch (	Exception e) {
			return null;
		} 
		//System.out.println(c.getDateNaissance());
		//return contactRepository.save(c);
	}
	
	@RequestMapping(value="/contacts/{id}", method=RequestMethod.DELETE)
	public boolean delete(@PathVariable long id){
		contactRepository.delete(id);
		return true;
	}
	
	
	//@PutMapping("/contact/update2/{id}")
	@RequestMapping(value="/contact/update2/{id}", method=RequestMethod.PUT)
	public Contact update2(@RequestParam("ufile") MultipartFile file,
						@RequestParam("cle") String id) throws ParseException{
		try {
			System.out.println(id + " update img !!!!!!!  ");
			Contact c = contactRepository.findOne(Long.parseLong(id));
			c.setImg(file.getOriginalFilename());
			c.setPhoto(file.getBytes());
			System.out.println(file.getOriginalFilename()+ " fin img !!!!!!!  ");
			return contactRepository.save(c);
		} catch (	Exception e) {
			return null;
		} 
	}
	
	@RequestMapping(value="/contact/update1/{id}", method=RequestMethod.PUT)
	public Contact update1(@PathVariable long id, @RequestBody Contact c){
		c.setId(id);
		return contactRepository.save(c);
	}
	
	 
    /*
     * Download Files
     */
	@GetMapping("/api/file/{id}")
	public ResponseEntity<byte[]> getFile(@PathVariable Long id) {
		Optional<Contact> fileOptional = Optional.of(contactRepository.findOne(id));
		
		if(fileOptional.isPresent()) {
			Contact file = fileOptional.get();
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getImg() + "\"")
					.body(file.getPhoto());	
		}
		
		return ResponseEntity.status(404).body(null);
	}
}
