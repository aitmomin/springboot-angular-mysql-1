package com.ang.repository;

//import org.assertj.core.data.Percentage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ang.entities.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long>{
	
	@Query(value="select * from contact where nom like ?1  order by ?#{#pageable}",
			countQuery = "select * from contact where nom like ?1  order by ?#{#pageable}",
			nativeQuery=true)
	public Page<Contact> chercher(String mc, Pageable pageable);	
	
}
