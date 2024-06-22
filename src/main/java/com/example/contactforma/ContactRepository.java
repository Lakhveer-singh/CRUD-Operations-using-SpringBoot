

package com.example.contactforma;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//import com.example.contactform.model.Contact;


@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    
}
