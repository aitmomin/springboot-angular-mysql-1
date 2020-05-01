package com.ang;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ang.entities.Contact;
import com.ang.repository.ContactRepository;

@SpringBootApplication
public class Angular4Application implements CommandLineRunner{
	@Autowired
	private ContactRepository contactRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(Angular4Application.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {		
		/*DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		
		contactRepository.save(new Contact("aitmomin","abdo",df.parse("20/11/1995"),"ait@gmail.com",0626103101,"test1.png"));
		contactRepository.save(new Contact("kozi","dodo",df.parse("20/04/1996"),"dodo@gmail.com",0665616101,"test2.png"));
		contactRepository.save(new Contact("koki","zizo",df.parse("20/04/1999"),"zizo@gmail.com",0612141516,"test3.png"));
		contactRepository.findAll().forEach(p->System.out.println(p.getNom()+" "+p.getPrenom()));
*/	}
	
	
}
