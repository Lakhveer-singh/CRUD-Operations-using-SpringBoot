package com.example.contactforma;

//import com.example.contactform.model.Contact;
//import com.example.contactform.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.contactforma.ContactRepository;//import com.example.contactform.service.ContactService;
import com.example.contactforma.ResourceNotFoundException;
import org.springframework.http.HttpStatus;


import java.util.List;

@RestController
@RequestMapping("/api/contacts")// Base path for all handlers in this controller
@CrossOrigin(origins="*")
public class ContactController {
    private final ContactService contactService;
    private final ContactRepository contactRepository; 
    @Autowired
    public ContactController(ContactService contactService, ContactRepository contactRepository) 
    {
    	
        this.contactService = contactService;
        this.contactRepository = contactRepository;  // Initialize the repository
    }

    @PostMapping
    public Contact addContact(@RequestBody Contact contact) {
        return contactService.saveContact(contact);
    }
    @PostMapping("/contacts")
    public Contact createContact(@RequestBody Contact contact) {
        return contactRepository.save(contact);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveContact(@RequestBody Contact contact) {
        System.out.println(contact); // Log the contact to see what you receive
        Contact savedContact = contactRepository.save(contact); 
        return ResponseEntity.ok(contact);
    }   

   @GetMapping
    public List<Contact> getAllContacts() {
        return contactService.getAllContacts();
    }
   @PutMapping("/{id}")
   public ResponseEntity<Contact> updateContact(@PathVariable Long id, @RequestBody Contact contactDetails) {
       Contact contact = contactRepository.findById(id)
               .orElseThrow(() -> new ResourceNotFoundException("Contact not found for this id :: " + id));

       contact.setName(contactDetails.getName());
       contact.setEmail(contactDetails.getEmail());
       contact.setMessage(contactDetails.getMessage());
       final Contact updatedContact = contactRepository.save(contact);
       return ResponseEntity.ok(updatedContact);
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
       if (contactRepository.existsById(id)) {
           contactRepository.deleteById(id);
           return ResponseEntity.ok().build();
       } else {
           return ResponseEntity.notFound().build();
       }
   }
   
   
/*   @PutMapping("/{id}")
   public ResponseEntity<Contact> updateContact(@PathVariable Long id, @RequestBody Contact updatedContact) {
       return contactRepository.findById(id)
               .map(contact -> {
                   contact.setName(updatedContact.getName());
                   contact.setEmail(updatedContact.getEmail());
                   contact.setMessage(updatedContact.getMessage());
                   Contact savedContact = contactRepository.save(contact);
                   return ResponseEntity.ok(savedContact);
               })
               .orElseGet(() -> ResponseEntity.notFound().build());
   }*/
 
}


