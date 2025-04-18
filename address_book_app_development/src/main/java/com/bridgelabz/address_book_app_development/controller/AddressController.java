package com.bridgelabz.address_book_app_development.controller;

import com.bridgelabz.address_book_app_development.dto.AddressDTO;
import com.bridgelabz.address_book_app_development.model.AddressModel;
import com.bridgelabz.address_book_app_development.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/addressbook")
public class AddressController {
    @Autowired
    private AddressService addressService;
    // Get all contacts
    @GetMapping("/all")
    public ResponseEntity<List<AddressModel>> getAllContacts() {
        return ResponseEntity.ok(addressService.getAllContacts());
    }

    // Create a new contact
    @PostMapping("/add")
    public ResponseEntity<AddressModel> addContact(@Valid @RequestBody AddressDTO contactDTO) {
        return ResponseEntity.ok(addressService.addContact(contactDTO));
    }


    // Get contact by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getContactById(@PathVariable Long id) {
        Optional<AddressModel> contact = addressService.getContactById(id);
        return contact.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update contact by ID
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateContact(@PathVariable Long id, @Valid @RequestBody AddressDTO contactDTO) {
        try {
            AddressModel updatedContact = addressService.updateContact(id, contactDTO);
            return ResponseEntity.ok(updatedContact);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete contact by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteContact(@PathVariable Long id) {
        boolean isDeleted = addressService.deleteContact(id);
        if (isDeleted) {
            return ResponseEntity.ok("Contact deleted successfully.");
        } else {
            return ResponseEntity.status(404).body("Contact not found.");
        }
    }
}
