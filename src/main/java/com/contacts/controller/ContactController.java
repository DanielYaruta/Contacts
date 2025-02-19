package com.contacts.controller;

import com.contacts.model.Contact;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/contacts")
class ContactController {
    private final Map<Long, Contact> contacts = new HashMap<>();
    private long currentId = 1;

    @GetMapping
    public List<Contact> getAllContacts() {
        return new ArrayList<>(contacts.values());
    }

    @GetMapping("/{contactId}")
    public ResponseEntity<Contact> getContact(@PathVariable Long contactId) {
        Contact contact = contacts.get(contactId);
        return contact != null ? ResponseEntity.ok(contact) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Contact> addContact(@RequestBody Contact contact) {
        contact.setId(currentId++);
        contacts.put(contact.getId(), contact);
        return ResponseEntity.status(HttpStatus.CREATED).body(contact);
    }

    @PutMapping("/{contactId}")
    public ResponseEntity<Contact> updateContact(@PathVariable Long contactId, @RequestBody Contact updatedContact) {
        if (!contacts.containsKey(contactId)) {
            return ResponseEntity.notFound().build();
        }
        updatedContact.setId(contactId);
        contacts.put(contactId, updatedContact);
        return ResponseEntity.ok(updatedContact);
    }
}
